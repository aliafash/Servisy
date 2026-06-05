package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CategoryEntity
import com.example.data.ProviderEntity
import com.example.data.AdminSettingsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    // Raw streams from database
    val categories: StateFlow<List<CategoryEntity>> = db.getCategoriesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val providers: StateFlow<List<ProviderEntity>> = db.getProvidersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val settings: StateFlow<AdminSettingsEntity> = db.getSettingsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AdminSettingsEntity())

    // UI interactive controllers & state holders
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterVipOnly = MutableStateFlow(false)
    val filterVipOnly: StateFlow<Boolean> = _filterVipOnly.asStateFlow()

    private val _filterAvailableOnly = MutableStateFlow(false)
    val filterAvailableOnly: StateFlow<Boolean> = _filterAvailableOnly.asStateFlow()

    // Notification HUD or feedback events
    private val _toastFlow = MutableStateFlow<String?>(null)
    val toastFlow: StateFlow<String?> = _toastFlow.asStateFlow()

    // Screen navigation controller states: "USER_BROWSE", "ADMIN_PANEL"
    private val _currentScreen = MutableStateFlow("USER_BROWSE")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Combined filtered list of service providers based on active options
    val filteredProviders: StateFlow<List<ProviderEntity>> = combine(
        providers,
        _selectedCategoryId,
        _searchQuery,
        _filterVipOnly,
        _filterAvailableOnly
    ) { providersList, categoryId, query, vipOnly, availableOnly ->
        providersList.filter { provider ->
            val matchesCategory = categoryId == null || provider.categoryId == categoryId
            val matchesSearch = query.isEmpty() || provider.name.contains(query, ignoreCase = true) ||
                    provider.area.contains(query, ignoreCase = true)
            val matchesVip = !vipOnly || provider.isVip
            val matchesAvailable = !availableOnly || provider.isAvailable

            matchesCategory && matchesSearch && matchesVip && matchesAvailable
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectCategory(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleVipFilter() {
        _filterVipOnly.value = !_filterVipOnly.value
    }

    fun toggleAvailableFilter() {
        _filterAvailableOnly.value = !_filterAvailableOnly.value
    }

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
    }

    fun clearNotification() {
        _toastFlow.value = null
    }

    fun triggerNotification(msg: String) {
        _toastFlow.value = msg
    }

    // --- Admin Operations (CRUD / Theme Synchronization) ---
    
    fun updateTheme(themeId: String) {
        viewModelScope.launch {
            val currentSettings = settings.value
            val newSettings = currentSettings.copy(
                activeThemeId = themeId,
                syncTimestamp = System.currentTimeMillis()
            )
            db.saveSettings(newSettings)
            triggerNotification("🎨 تم تعديل المظهر والسمة بنجاح!")
        }
    }

    fun updateAdminSettings(appName: String, footerMessage: String, showVipOnly: Boolean) {
        viewModelScope.launch {
            val currentSettings = settings.value
            val newSettings = currentSettings.copy(
                appName = appName,
                footerMessage = footerMessage,
                showVipOnly = showVipOnly,
                syncTimestamp = System.currentTimeMillis()
            )
            db.saveSettings(newSettings)
            // also sync screen local filter with overall settings if required
            _filterVipOnly.value = showVipOnly
            triggerNotification("🛡️ تم حفظ الإعدادات الإدارية والمزامنة بنجاح!")
        }
    }

    fun addNewCategory(nameAr: String, nameEn: String, iconName: String, description: String) {
        if (nameAr.isBlank() || nameEn.isBlank()) {
            triggerNotification("⚠️ يرجى تعبئة الحقول المطلوبة للتصنيف")
            return
        }
        viewModelScope.launch {
            val id = "cat_" + UUID.randomUUID().toString().take(6)
            val newCat = CategoryEntity(id, nameAr, nameEn, iconName, description)
            db.insertCategory(newCat)
            triggerNotification("✅ تم إضافة التصنيف [ $nameAr ]")
        }
    }

    fun removeCategory(id: String) {
        viewModelScope.launch {
            db.deleteCategory(id)
            if (_selectedCategoryId.value == id) {
                _selectedCategoryId.value = null
            }
            triggerNotification("🗑️ تم حذف التصنيف بنجاح")
        }
    }

    fun addNewProvider(name: String, phone: String, categoryId: String, area: String, isVip: Boolean, basePrice: Double) {
        if (name.isBlank() || phone.isBlank() || categoryId.isBlank() || area.isBlank()) {
            triggerNotification("⚠️ يرجى ملء كافة تفاصيل المهني")
            return
        }
        viewModelScope.launch {
            val id = "prov_" + UUID.randomUUID().toString().take(6)
            val provider = ProviderEntity(
                id = id,
                name = name,
                phone = phone,
                categoryId = categoryId,
                area = area,
                isAvailable = true,
                ratingSum = 5,
                ratingCount = 1,
                isVip = isVip,
                basePrice = basePrice
            )
            db.insertProvider(provider)
            triggerNotification("✅ تم إضافة المهني [ $name ] بنجاح")
        }
    }

    fun removeProvider(id: String) {
        viewModelScope.launch {
            db.deleteProvider(id)
            triggerNotification("🗑️ تم إزالة المهني من الدليل")
        }
    }

    fun submitRating(id: String, rating: Int) {
        viewModelScope.launch {
            db.rateProvider(id, rating)
            triggerNotification("⭐ شكراً لتقييمك! تم احتساب التقييم")
        }
    }

    fun toggleProviderStatus(provider: ProviderEntity) {
        viewModelScope.launch {
            val updated = provider.copy(isAvailable = !provider.isAvailable)
            db.insertProvider(updated)
            triggerNotification(
                if (updated.isAvailable) "🟢 المهني متاح للعمل الآن" 
                else "🔴 المهني غير متاح حالياً"
            )
        }
    }
}
