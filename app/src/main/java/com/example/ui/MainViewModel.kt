package com.example.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    // --- Core SQLite Streams ---
    val categories: StateFlow<List<CategoryEntity>> = db.getCategoriesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val providers: StateFlow<List<ProviderEntity>> = db.getProvidersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val settings: StateFlow<AdminSettingsEntity> = db.getSettingsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AdminSettingsEntity())

    val pendingProviders: StateFlow<List<PendingProviderEntity>> = db.getPendingProvidersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val banners: StateFlow<List<BannerEntity>> = db.getBannersFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reports: StateFlow<List<ReportEntity>> = db.getReportsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activityLogs: StateFlow<List<ActivityLogEntity>> = db.getActivityLogsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val whitelistedDevices: StateFlow<List<DeviceWhitelistEntity>> = db.getWhitelistDevicesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cities: StateFlow<List<CityEntity>> = db.getCitiesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val chatMessages: StateFlow<List<ChatMessageEntity>> = db.getAllChatMessagesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val supervisors: StateFlow<List<SupervisorEntity>> = db.getSupervisorsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeSupervisor = MutableStateFlow<SupervisorEntity?>(null)
    val activeSupervisor: StateFlow<SupervisorEntity?> = _activeSupervisor.asStateFlow()

    // --- Localization Language state ---
    private val _currentLanguage = MutableStateFlow("AR") // AR, EN
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    // --- Advanced UI Filtering Controller State ---
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    val selectedCategoryId: StateFlow<String?> = _selectedCategoryId.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filterCityId = MutableStateFlow<String?>(null)
    val filterCityId: StateFlow<String?> = _filterCityId.asStateFlow()

    private val _filterNeighborhoodName = MutableStateFlow("")
    val filterNeighborhoodName: StateFlow<String> = _filterNeighborhoodName.asStateFlow()

    private val _phoneOrNameFilter = MutableStateFlow("")
    val phoneOrNameFilter: StateFlow<String> = _phoneOrNameFilter.asStateFlow()

    private val _maxKmRadius = MutableStateFlow(10) // 5, 10, 20 km search radius
    val maxKmRadius: StateFlow<Int> = _maxKmRadius.asStateFlow()

    private val _filterVipOnly = MutableStateFlow(false)
    val filterVipOnly: StateFlow<Boolean> = _filterVipOnly.asStateFlow()

    private val _filterAvailableOnly = MutableStateFlow(false)
    val filterAvailableOnly: StateFlow<Boolean> = _filterAvailableOnly.asStateFlow()

    // --- Active Customer Account States ---
    private val _currentUserId = MutableStateFlow("user_" + UUID.randomUUID().toString().take(6))
    val currentUserId: StateFlow<String> = _currentUserId.asStateFlow()

    private val _currentUserPoints = MutableStateFlow(100) // Default starting loyalty points
    val currentUserPoints: StateFlow<Int> = _currentUserPoints.asStateFlow()

    private val _adminRole = MutableStateFlow<String>("GUEST") // GUEST, ADMIN, OWNER
    val adminRole: StateFlow<String> = _adminRole.asStateFlow()

    private val _rememberMeLogin = MutableStateFlow(false)
    val rememberMeLogin: StateFlow<Boolean> = _rememberMeLogin.asStateFlow()

    private val _toastFlow = MutableStateFlow<String?>(null)
    val toastFlow: StateFlow<String?> = _toastFlow.asStateFlow()

    // navigation state: "USER_BROWSE", "ADMIN_PANEL", "OWNER_PANEL", "REGISTER_FORM", "ABOUT_APP"
    private val _currentScreen = MutableStateFlow("USER_BROWSE")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Back button history Stack simple simulation
    private val _screenHistory = MutableStateFlow<List<String>>(listOf("USER_BROWSE"))

    // Backdoor secret clicks tracker
    private var backDoorClickCount = 0
    private var lastClickTime = 0L

    // --- Advanced Filters Combining Stream --- //
    val filteredProviders: StateFlow<List<ProviderEntity>> = combine(
        providers,
        _selectedCategoryId,
        _searchQuery,
        _filterCityId,
        _filterNeighborhoodName,
        _phoneOrNameFilter,
        _maxKmRadius,
        _filterVipOnly,
        _filterAvailableOnly
    ) { params ->
        val list = params[0] as List<ProviderEntity>
        val catId = params[1] as String?
        val query = params[2] as String
        val cityId = params[3] as String?
        val neighborhood = params[4] as String
        val phoneOrName = params[5] as String
        val radius = params[6] as Int
        val vipOnly = params[7] as Boolean
        val availableOnly = params[8] as Boolean

        var output = list.filter { prov ->
            val matchesCategory = catId == null || prov.categoryId == catId
            val matchesQuery = query.isEmpty() || prov.name.contains(query, ignoreCase = true) || prov.area.contains(query, ignoreCase = true)
            val matchesVip = !vipOnly || prov.isVip
            val matchesAvailable = !availableOnly || prov.isAvailable
            
            // Geographic filters
            val matchesCity = cityId == null || prov.area.contains(cityId, ignoreCase = true)
            val matchesNeighbor = neighborhood.isEmpty() || prov.area.contains(neighborhood, ignoreCase = true)
            
            // Name or phone advanced input filter
            val matchesPhoneOrName = phoneOrName.isEmpty() || prov.name.contains(phoneOrName, ignoreCase = true) || prov.phone.contains(phoneOrName)

            // Radius math (Simulated distance for offline radius search in Yemen)
            // standard coordinates for coordinates offset calculations
            val SanaaLat = 15.3694
            val SanaaLon = 44.1910
            val latDiff = prov.latitude - SanaaLat
            val lonDiff = prov.longitude - SanaaLon
            val approxDistanceKm = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111.0
            val matchesRadius = approxDistanceKm <= radius

            matchesCategory && matchesQuery && matchesVip && matchesAvailable && matchesCity && matchesNeighbor && matchesPhoneOrName && matchesRadius
        }

        // Subscriptions VIP prioritisation rule: "Approved Subscription elements are always displayed at the absolute top"
        output.sortedWith(compareByDescending<ProviderEntity> { it.subscriptionStatus == "APPROVED" }
            .thenByDescending { it.isPinned }
            .thenByDescending { it.isVip }
            .thenByDescending { if (it.ratingCount > 0) it.ratingSum.toDouble() / it.ratingCount else 5.0 }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Interactive Search & Settings updates ---
    fun selectCategory(categoryId: String?) {
        _selectedCategoryId.value = categoryId
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setPhoneOrNameFilter(text: String) {
        _phoneOrNameFilter.value = text
    }

    fun setNeighborhoodFilter(text: String) {
        _filterNeighborhoodName.value = text
    }

    fun setCityFilter(cityId: String?) {
        _filterCityId.value = cityId
    }

    fun setRadiusKm(km: Int) {
        _maxKmRadius.value = km
    }

    fun toggleVipFilter() {
        _filterVipOnly.value = !_filterVipOnly.value
    }

    fun toggleAvailableFilter() {
        _filterAvailableOnly.value = !_filterAvailableOnly.value
    }

    fun switchLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "AR") "EN" else "AR"
        triggerNotification(if (_currentLanguage.value == "AR") "تم تحويل اللغة إلى العربية" else "Language set to English")
    }

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
        val cur = _screenHistory.value.toMutableList()
        if (cur.lastOrNull() != screen) {
            cur.add(screen)
            _screenHistory.value = cur
        }
    }

    fun goBack(): Boolean {
        val cur = _screenHistory.value.toMutableList()
        if (cur.size > 1) {
            cur.removeAt(cur.lastIndex)
            _screenHistory.value = cur
            _currentScreen.value = cur.last()
            return true // handled
        }
        // If already in main browser, return false (will trigger double press to exit logic)
        if (_selectedCategoryId.value != null) {
            _selectedCategoryId.value = null
            triggerNotification("تمت العودة للتصنيفات الرئيسية")
            return true
        }
        return false
    }

    fun clearNotification() {
        _toastFlow.value = null
    }

    fun triggerNotification(msg: String) {
        _toastFlow.value = msg
    }

    // --- Secret Backdoor Trigger mechanics ---
    fun registerBackdoorInteraction() {
        val now = System.currentTimeMillis()
        if (now - lastClickTime < 2200) {
            backDoorClickCount++
        } else {
            backDoorClickCount = 1
        }
        lastClickTime = now

        if (backDoorClickCount >= 5) {
            backDoorClickCount = 0
            navigateTo("OWNER_PANEL")
            triggerNotification("🚪 تم تفعيل بوابتك الخلفية بنجاح! يرجى إدخال كلمة مرور المالك.")
        } else {
            val remaining = 5 - backDoorClickCount
            // Secret debugging cue, not prominent
            Log.d("Backdoor", "Progress clicks: $backDoorClickCount. Remaining: $remaining")
        }
    }

    // --- Authentication System ---
    fun setAdminRole(role: String) {
        _adminRole.value = role
    }

    fun attemptLogin(idText: String, passText: String, rememberMe: Boolean): Boolean {
        _rememberMeLogin.value = rememberMe
        val cleanUser = idText.trim()
        val cleanPass = passText.trim()

        // 1. Check Owner/Master backdoor password
        if (cleanPass == "maher--736462") {
            _adminRole.value = "OWNER"
            _activeSupervisor.value = null
            navigateTo("OWNER_PANEL")
            triggerNotification("👑 أهلاً يا مالك التطبيق! تم منح كافة صلاحيات التحكم بملفات النظام.")
            logAudit("المالك الرئيسي", "دخول ناجح للبوابة الخلفية")
            return true
        }

        // 2. Check Admin Credentials (customizable)
        val splitted = settings.value.adminPassword.split(":")
        val targetUser = splitted.getOrNull(0) ?: "WAM2026"
        val targetPass = splitted.getOrNull(1) ?: "maher736462"

        if (cleanUser == targetUser && cleanPass == targetPass) {
            _adminRole.value = "ADMIN"
            _activeSupervisor.value = null
            navigateTo("ADMIN_PANEL")
            triggerNotification("🛡️ دخول ناجح للوحة الإدارة (Admin Panel)")
            logAudit("المدير الرئيسي ${cleanUser}", "تسجيل دخول للوحة الإدارة")
            return true
        }

        // 3. Check Custom Supervisors (المشرفين)
        val matchedSupervisor = supervisors.value.find { it.username == cleanUser && it.password == cleanPass }
        if (matchedSupervisor != null) {
            _adminRole.value = "SUPERVISOR"
            _activeSupervisor.value = matchedSupervisor
            navigateTo("ADMIN_PANEL")
            triggerNotification("🛡️ دخول ناجح كمشرف (${cleanUser})")
            logAudit("المشرف ${cleanUser}", "تسجيل دخول للوحة الإدارة")
            return true
        }

        triggerNotification("❌ عذراً! الاسم أو رمز المرور غير صحيح.")
        logAudit("زائر مجهول", "محاولة دخول غير مصرح بها ببيانات ($cleanUser)")
        return false
    }

    fun logout() {
        _adminRole.value = "GUEST"
        _activeSupervisor.value = null
        navigateTo("USER_BROWSE")
        _screenHistory.value = listOf("USER_BROWSE")
        triggerNotification("🔒 تم تسجيل الخروج بنجاح!")
    }

    // --- Professional Application Submission (👤) --
    fun submitJoinForm(
        name: String,
        phone: String,
        catId: String,
        area: String,
        neighborhood: String,
        photoPath: String,
        idCardPath: String,
        gpsCoords: String
    ) {
        if (name.isBlank() || phone.isBlank() || catId.isBlank() || area.isBlank()) {
            triggerNotification("⚠️ يرجى ملء كافة البيانات الإجبارية")
            return
        }
        viewModelScope.launch {
            val id = "pending_" + UUID.randomUUID().toString().take(6)
            val pending = PendingProviderEntity(
                id = id,
                name = name,
                phone = phone,
                categoryId = catId,
                area = area,
                localNeighborhood = neighborhood,
                coords = gpsCoords,
                photoUri = photoPath,
                idCardUri = idCardPath,
                submitDate = System.currentTimeMillis()
            )
            db.insertPendingProvider(pending)
            triggerNotification("🎉 تم إرسال استمارة طلبك للمراجعة الفورية من المدراء!")
            
            // FCM Simulation triggering
            sendFcmAlert("طلب انضمام جديد", "يريد المهني ${name} الانضمام تصنيف ${catId}")
        }
    }

    fun approveRequest(pending: PendingProviderEntity) {
        viewModelScope.launch {
            // Transfer pending to active directories
            val provider = ProviderEntity(
                id = "prov_" + UUID.randomUUID().toString().take(5),
                name = pending.name,
                phone = pending.phone,
                categoryId = pending.categoryId,
                area = pending.area,
                photoUri = pending.photoUri,
                idCardUri = pending.idCardUri,
                latitude = 15.36 + (Math.random() * 0.1),
                longitude = 44.19 + (Math.random() * 0.1),
                subscriptionStatus = "NONE",
                isAvailable = true
            )
            db.insertProvider(provider)
            db.updatePendingStatus(pending.id, "APPROVED")
            triggerNotification("✅ تم قبول طلب ${pending.name} ونقله لدليل المحترفين!")
            logAudit(_adminRole.value, "قبول طلب الانضمام رقم ${pending.id} للفني ${pending.name}")
        }
    }

    fun rejectRequest(pending: PendingProviderEntity, reason: String) {
        viewModelScope.launch {
            db.updatePendingStatus(pending.id, "REJECTED", reason)
            triggerNotification("❌ تم رفض طلب ${pending.name} وإرسال السبب: ${reason}")
            logAudit(_adminRole.value, "رفض طلب انضمام رقم ${pending.id} للفني ${pending.name}. السبب: $reason")
        }
    }

    fun changeAdminCredentials(newUser: String, newPass: String) {
        if (newUser.isBlank() || newPass.isBlank()) return
        viewModelScope.launch {
            val creds = "${newUser.trim()}:${newPass.trim()}"
            val newSettings = settings.value.copy(
                adminPassword = creds,
                syncTimestamp = System.currentTimeMillis()
            )
            db.saveSettings(newSettings)
            triggerNotification("🔑 تم تعديل بيانات مرور الأدمن بنجاح [ WAM2026 ]")
            logAudit("المالك الرئيسي", "تغيير كلمة مرور المشرف الرئيسي لـ $newUser")
        }
    }

    // --- Supervisors Management Actions (Owner / Admin Exclusive) ---
    fun addSupervisor(username: String, pass: String, canAcceptReject: Boolean, canCat: Boolean, canBan: Boolean, canDel: Boolean, canView: Boolean) {
        if (username.isBlank() || pass.isBlank()) return
        viewModelScope.launch {
            val supervisor = SupervisorEntity(
                id = java.util.UUID.randomUUID().toString().take(6),
                username = username.trim(),
                password = pass.trim(),
                canAcceptRejectRequests = canAcceptReject,
                canManageCategories = canCat,
                canManageBanners = canBan,
                canDeleteProviders = canDel,
                canViewReports = canView
            )
            db.insertSupervisor(supervisor)
            triggerNotification("🛡️ تم إضافة المشرف ${username.trim()} بنجاح")
            logAudit(_adminRole.value, "إضافة مشرف جديد: ${username.trim()}")
        }
    }

    fun updateSupervisor(supervisor: SupervisorEntity) {
        viewModelScope.launch {
            db.insertSupervisor(supervisor)
            triggerNotification("🛡️ تم تحديث الصلاحيات/كلمة المرور للمشرف ${supervisor.username}")
            logAudit(_adminRole.value, "تحديث صلاحيات المشرف: ${supervisor.username}")
        }
    }

    fun deleteSupervisor(id: String) {
        viewModelScope.launch {
            val currentList = supervisors.value
            val target = currentList.find { it.id == id }
            if (target != null) {
                db.deleteSupervisor(id)
                triggerNotification("🗑️ تم حذف المشرف ${target.username}")
                logAudit(_adminRole.value, "حذف حساب المشرف: ${target.username}")
            }
        }
    }

    // --- Backdoor Login Action ---
    fun attemptBackdoorLogin(passText: String): Boolean {
        val cleanPass = passText.trim()
        if (cleanPass == "maher--736462") {
            _adminRole.value = "OWNER"
            _activeSupervisor.value = null
            navigateTo("OWNER_PANEL")
            triggerNotification("👑 أهلاً يا مالك التطبيق! تم منح كافة صلاحيات التحكم بملفات النظام.")
            logAudit("المالك الرئيسي", "دخول ناجح للبوابة الخلفية")
            return true
        } else {
            triggerNotification("❌ كلمة المرور غير صحيحة!")
            return false
        }
    }

    // --- Supervisors Multi-Device Sync Helpers ---
    fun exportSupervisorsConfig(): String {
        val list = supervisors.value
        val sb = StringBuilder()
        for (sup in list) {
            sb.append("${sup.id}|${sup.username}|${sup.password}|${sup.canAcceptRejectRequests}|${sup.canManageCategories}|${sup.canManageBanners}|${sup.canDeleteProviders}|${sup.canViewReports}\n")
        }
        return sb.toString()
    }

    fun importSupervisorsConfig(configText: String): Boolean {
        if (configText.isBlank()) return false
        try {
            val lines = configText.trim().split("\n")
            viewModelScope.launch {
                for (line in lines) {
                    val parts = line.split("|")
                    if (parts.size >= 8) {
                        val sup = SupervisorEntity(
                            id = parts[0],
                            username = parts[1],
                            password = parts[2],
                            canAcceptRejectRequests = parts[3].toBoolean(),
                            canManageCategories = parts[4].toBoolean(),
                            canManageBanners = parts[5].toBoolean(),
                            canDeleteProviders = parts[6].toBoolean(),
                            canViewReports = parts[7].toBoolean()
                        )
                        db.insertSupervisor(sup)
                    }
                }
                triggerNotification("⚡ تم استيراد ودمج حسابات المشرفين بنجاح ومزامنتها!")
                logAudit(_adminRole.value, "استيراد حسابات المشرفين يدويًا")
            }
            return true
        } catch (e: Exception) {
            triggerNotification("❌ كود التهيئة غير صالح!")
            return false
        }
    }

    // --- Chat Management Operations (Admin Settings) ---
    fun clearAllChatHistory() {
        viewModelScope.launch {
            db.clearAllChatMessages()
            triggerNotification("🧹 تم مسح جميع سجلات المحادثات نهائياً لضمان الخصوصية!")
            logAudit(_adminRole.value, "مسح السجلات الكلية للمحادثات")
        }
    }

    fun exportChatHistoryToCSV(): String {
        val list = chatMessages.value
        val sb = StringBuilder()
        sb.append("ID,SenderName,ReceiverName,Message,Timestamp\n")
        for (msg in list) {
            val cleanMsg = msg.messageText.replace(",", " ").replace("\n", " ")
            val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US)
                .format(java.util.Date(msg.timestamp))
            sb.append("${msg.id},\"${msg.senderName}\",\"${msg.receiverName}\",\"$cleanMsg\",\"$dateStr\"\n")
        }
        return sb.toString()
    }

    // --- Dynamic Pin / Recommend / Verification ---
    fun pinProvider(provId: String, pin: Boolean) {
        viewModelScope.launch {
            val target = providers.value.firstOrNull { it.id == provId } ?: return@launch
            val updated = target.copy(isPinned = pin)
            db.insertProvider(updated)
            triggerNotification(if (pin) "📌 تم تثبيت المهني في صدام القسم" else "📍 تم إلغاء تثبيت المهني")
            logAudit(_adminRole.value, "تغيير حالة تثبيت المهني ${target.name} لـ $pin")
        }
    }

    fun recommendProvider(provId: String, recommend: Boolean) {
        viewModelScope.launch {
            val target = providers.value.firstOrNull { it.id == provId } ?: return@launch
            val updated = target.copy(isRecommended = recommend)
            db.insertProvider(updated)
            triggerNotification(if (recommend) "⭐ تم ترشيح المهني في لوحة التوصيات الموصى بها!" else "⭐ تم إلغاء الترشيح")
            logAudit(_adminRole.value, "تعديل توصية المهني ${target.name} لـ $recommend")
        }
    }

    fun verifyProviderBadge(provId: String, verify: Boolean) {
        viewModelScope.launch {
            val target = providers.value.firstOrNull { it.id == provId } ?: return@launch
            val updated = target.copy(isVerified = verify)
            db.insertProvider(updated)
            triggerNotification(if (verify) "🔷 تم توثيق المهني بالشارة الزرقاء المعتمدة!" else "🔷 تم إلغاء شارة التوثيق")
            logAudit(_adminRole.value, "تغيير توثيق المهني ${target.name} لـ $verify")
        }
    }

    // --- Custom Subscriptions system ---
    fun toggleProviderSubscription(provId: String, subStatus: String) {
        viewModelScope.launch {
            val target = providers.value.firstOrNull { it.id == provId } ?: return@launch
            val isGold = subStatus == "APPROVED"
            val updated = target.copy(
                subscriptionStatus = subStatus,
                isVip = isGold // gives VIP tag and places top
            )
            db.insertProvider(updated)
            triggerNotification("💳 تم تعديل حالة اشتراك المهني لـ $subStatus")
            logAudit(_adminRole.value, "إدارة اشتراك المهني ${target.name} لـ $subStatus")
        }
    }

    // --- Loyalty points redemption simulation ---
    fun redeemLoyaltyPoints() {
        val current = _currentUserPoints.value
        if (current < 100) {
            triggerNotification("⚠️ الحد الأدنى لاستبدال النقاط هو 100 نقطة ولائك.")
            return
        }
        _currentUserPoints.value = current - 100
        triggerNotification("🎁 مبروك! تم استبدال 100 نقطة بخصم 15% على أول معاينة!")
    }

    fun rewardRatingPoints() {
        _currentUserPoints.value = _currentUserPoints.value + 15
        triggerNotification("🎉 مبروك! حصلت على 15 نقطة ولاء لتقييمك المهني!")
    }

    fun rewardSharePoints() {
        _currentUserPoints.value = _currentUserPoints.value + 20
        triggerNotification("🔗 حصلت على 20 نقطة ولاء لمشاركة التطبيق مع أصدقائك!")
    }

    // --- Support Abusive complaints mechanism --
    fun sendReport(provId: String, provName: String, reporter: String, messageText: String) {
        if (messageText.isBlank()) return
        viewModelScope.launch {
            val report = ReportEntity(
                id = "rep_" + UUID.randomUUID().toString().take(6),
                providerId = provId,
                providerName = provName,
                reporterName = if (reporter.isBlank()) "مواطن يمني" else reporter,
                content = messageText,
                timestamp = System.currentTimeMillis()
            )
            db.insertReport(report)
            triggerNotification("🛡️ تم رفع البلاغ بنجاح وسيتعامل معه المشرف فوراً.")
            sendFcmAlert("بلاغ جديد ضد مهني", "بلاغ من ${report.reporterName} ضد ${provName}")
        }
    }

    fun clearReport(reportId: String) {
        viewModelScope.launch {
            db.deleteReport(reportId)
            triggerNotification("🗑️ تم شطب ومعالجة البلاغ بنجاح")
            logAudit(_adminRole.value, "حذف ومعالجة البلاغ رقم $reportId")
        }
    }

    // --- Banners / Sponsoring Advertisements Management ---
    fun addNewBanner(title: String, imgUrl: String, actionUrl: String, displayType: String, size: String, duration: Int) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val banner = BannerEntity(
                id = "banner_" + UUID.randomUUID().toString().take(5),
                title = title,
                imageUrl = imgUrl,
                redirectUrl = actionUrl,
                displayType = displayType,
                bannerSize = size,
                durationSeconds = duration,
                isActive = true
            )
            db.insertBanner(banner)
            triggerNotification("🖼️ تم إنشاء ورفع اللافتة الإعلانية بنجاح!")
            logAudit(_adminRole.value, "إضافة لافتة إعلانية جديدة بعنوان ${title}")
        }
    }

    fun deleteBanner(id: String) {
        viewModelScope.launch {
            db.deleteBanner(id)
            triggerNotification("🗑️ تم إزالة الإعلان بنجاح")
            logAudit(_adminRole.value, "حذف إعلان ترويجي رقم $id")
        }
    }

    // --- Chat Messages Management ---
    fun sendSimpleChatMessage(messageText: String, isWithAdmin: Boolean = true, providerId: String = "") {
        if (messageText.isBlank()) return
        viewModelScope.launch {
            val senderId = _currentUserId.value
            val receiverId = if (isWithAdmin) "ADMIN_WAM" else providerId
            val receiverName = if (isWithAdmin) "إدارة خدمات اليمن" else "مزود الخدمة"
            
            val msg = ChatMessageEntity(
                id = "msg_" + UUID.randomUUID().toString().take(6),
                senderId = senderId,
                receiverId = receiverId,
                senderName = "أنت (زائر)",
                receiverName = receiverName,
                messageText = messageText,
                timestamp = System.currentTimeMillis(),
                isOfflineSent = true
            )
            db.insertChatMessage(msg)
            
            // Auto simulated AI chatbot reply or admin reply
            val autoReply = ChatMessageEntity(
                id = "msg_" + UUID.randomUUID().toString().take(6),
                senderId = receiverId,
                receiverId = senderId,
                senderName = receiverName,
                receiverName = "أنت",
                messageText = getBotOfflineAnswer(messageText),
                timestamp = System.currentTimeMillis() + 800
            )
            db.insertChatMessage(autoReply)
            triggerNotification("💬 تم إرسال الرسالة بنجاح!")
        }
    }

    // Offline Smart Assistant Bot response database
    fun getBotOfflineAnswer(question: String): String {
        val q = question.trim()
        return when {
            q.contains("أقسام") || q.contains("التصنيفات") || q.contains("ماهي الأقسام") -> {
                val catNames = categories.value.map { it.nameAr }
                "📋 الأقسام النشطة المتوفرة حالياً هي: \n" + catNames.joinToString("\n• ")
            }
            q.contains("تواصل") || q.contains("اتصال") || q.contains("رقم") || q.contains("كيف أتصل") -> {
                "📞 للاتصال بأي مهني، اضغط على الأيقونة الخضراء الدائرية 📞 في بطاقة الفني لفتح واجهة الاتصال المباشرة."
            }
            q.contains("دعم") || q.contains("المساعدة") || q.contains("رقم الدعم") -> {
                "🛠️ رقم الدعم الفني الرئيسي لواتساب والاتصال هو: ${settings.value.supportPhone}. لإرسال بريد: ${settings.value.supportEmail}"
            }
            q.contains("سعر") || q.contains("تكلفة") -> {
                "💰 تكلفة المعاينة تختلف حسب تخصص الفني. يمكنك رؤية السعر البدئي للمعاينة مسجلاً في بطاقة تفاصيل كل مهني قبل الاتصال."
            }
            else -> {
                "🤖 أهلاً بك! أنا مساعد خدمات اليمن الذكي.\n" +
                "تستطيع الاستفسار عن:\n" +
                "1. الأقسام المتوفرة\n" +
                "2. طريقة الاتصال بفني\n" +
                "3. رقم الدعم والشكاوى\n" +
                "رسالتك مستلمة وسيقوم موظفو الدعم بالرد الفوري!"
            }
        }
    }

    // --- Cities Manage ---
    fun addNewCity(ar: String, en: String) {
        if (ar.isBlank() || en.isBlank()) return
        viewModelScope.launch {
            val city = CityEntity(id = "city_" + UUID.randomUUID().toString().take(4), nameAr = ar, nameEn = en)
            db.insertCity(city)
            triggerNotification("🏙️ تم إضافة مدينة $ar لقوائم الفلاتر")
        }
    }

    fun removeCity(id: String) {
        viewModelScope.launch {
            db.deleteCity(id)
            triggerNotification("🗑️ تم مسح المدينة من التغطية")
        }
    }

    // --- Whitelisted Devices ---
    fun addDeviceToWhitelist(deviceName: String) {
        if (deviceName.isBlank()) return
        viewModelScope.launch {
            val device = DeviceWhitelistEntity(id = "dev_" + UUID.randomUUID().toString().take(4), deviceName = deviceName, isAllowed = true)
            db.insertDevice(device)
            triggerNotification("📱 تم السماح بالدخول للجهاز: $deviceName")
        }
    }

    fun removeDeviceFromWhitelist(id: String) {
        viewModelScope.launch {
            db.deleteDevice(id)
            triggerNotification("🗑️ تمت إزالة الجهاز من القائمة")
        }
    }

    // --- Save Administrative updates ---
    fun updateBackdoorSettings(
        appName: String,
        welcomeMsg: String,
        footerMsg: String,
        themeId: String,
        supportPhone: String,
        supportEmail: String,
        supportWhatsapp: String,
        isMaintenance: Boolean,
        hiddenFooter: Boolean,
        botHidden: Boolean,
        botSize: Int,
        botIcon: String,
        botIconEffects: String,
        chatHidden: Boolean,
        chatSize: Int,
        chatIcon: String,
        chatIconEffects: String,
        radiusKm: Int,
        isSpeech: Boolean,
        isDataSaver: Boolean,
        imgQuality: Int
    ) {
        viewModelScope.launch {
            val cur = settings.value
            val update = cur.copy(
                appName = appName,
                welcomeMessage = welcomeMsg,
                footerMessage = footerMsg,
                activeThemeId = themeId,
                supportPhone = supportPhone,
                supportEmail = supportEmail,
                supportWhatsapp = supportWhatsapp,
                isMaintenanceActive = isMaintenance,
                hidePromoFooter = hiddenFooter,
                assistantHidden = botHidden,
                assistantSize = botSize,
                assistantIcon = botIcon,
                assistantIconEffects = botIconEffects,
                chatHidden = chatHidden,
                chatSize = chatSize,
                chatIcon = chatIcon,
                chatIconEffects = chatIconEffects,
                maxSearchRadiusKm = radiusKm,
                isSpeechSearchEnabled = isSpeech,
                isDataSaverActive = isDataSaver,
                imageQualityPercent = imgQuality
            )
            db.saveSettings(update)
            triggerNotification("💾 تم تحديث ومزامنة بوابة الإعدادات الخلفية لكامل التطبيق!")
            logAudit("المالك الرئيسي", "تعديل إعدادات التكوين والواجهات من البوابة الخلفية")
        }
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

    fun addNewCategory(nameAr: String, nameEn: String, iconName: String, description: String, parentId: String = "") {
        if (nameAr.isBlank() || nameEn.isBlank()) {
            triggerNotification("⚠️ يرجى تعبئة الحقول المطلوبة للتصنيف")
            return
        }
        viewModelScope.launch {
            val id = "cat_" + UUID.randomUUID().toString().take(6)
            val newCat = CategoryEntity(id, nameAr, nameEn, iconName, description, categories.value.size + 1, parentId)
            db.insertCategory(newCat)
            triggerNotification(if (parentId.isEmpty()) "✅ تم إضافة القسم الرئيسي [ $nameAr ]" else "✅ تم إضافة القسم الفرعي [ $nameAr ]")
            logAudit(_adminRole.value, "إنشاء تصنيف جديد باسم $nameAr")
        }
    }

    fun editCategory(id: String, nameAr: String, nameEn: String, iconName: String, description: String, parentId: String = "") {
        if (nameAr.isBlank() || nameEn.isBlank()) {
            triggerNotification("⚠️ يرجى تعبئة الحقول المطلوبة")
            return
        }
        viewModelScope.launch {
            val current = categories.value.find { it.id == id }
            if (current != null) {
                val updated = current.copy(
                    nameAr = nameAr,
                    nameEn = nameEn,
                    iconName = iconName,
                    description = description,
                    parentId = parentId
                )
                db.insertCategory(updated)
                triggerNotification("✏️ تم تعديل بيانات القسم [ $nameAr ] بنجاح")
                logAudit(_adminRole.value, "تعديل القسم: $nameAr")
            }
        }
    }

    fun removeCategory(id: String) {
        viewModelScope.launch {
            db.deleteCategory(id)
            if (_selectedCategoryId.value == id) {
                _selectedCategoryId.value = null
            }
            triggerNotification("🗑️ تم حذف التصنيف بنجاح")
            logAudit(_adminRole.value, "حذف التصنيف رئيسي رقم $id")
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
                basePrice = basePrice,
                latitude = 15.3694 + (Math.random() - 0.5) * 0.08, // Generates a safe radius of approx 5km about center
                longitude = 44.1910 + (Math.random() - 0.5) * 0.08
            )
            db.insertProvider(provider)
            triggerNotification("✅ تم إضافة المهني [ $name ] بنجاح")
            logAudit(_adminRole.value, "إضافة مزود الخدمة $name في التصنيف $categoryId")
        }
    }

    fun removeProvider(id: String) {
        viewModelScope.launch {
            db.deleteProvider(id)
            triggerNotification("🗑️ تم إزالة المهني من الدليل")
            logAudit(_adminRole.value, "حذف مزود الخدمة رقم $id")
        }
    }

    fun submitRating(id: String, rating: Int) {
        viewModelScope.launch {
            db.rateProvider(id, rating)
            rewardRatingPoints() // Reward loyal points to user
            logAudit("نظام التقييم", "تم تقييم الفني $id بـ $rating نجوم وحصل مقيمه على 20 نقطة")
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

    // --- Backup & Simulated Exports ---
    fun triggerManualBackup() {
        viewModelScope.launch {
            logAudit(_adminRole.value, "حفظ نسخة احتياطية محلية لقواعد البيانات")
            triggerNotification("💿 تم حفظ نسخة احتياطية مشفرة بنجاح إلى (sdcard/maw_backups/database.db) وجوجل درايف!")
        }
    }

    fun triggerRestoreBackup() {
        viewModelScope.launch {
            logAudit(_adminRole.value, "استرجاع البيانات من نسخة احتياطية سابقة")
            triggerNotification("✅ تم استرجاع النسخة الاحتياطية بنجاح ومزامنة هويات جميع المستخدمين!")
        }
    }

    fun exportComplaintsToCSV(): String {
        val header = "ID,ProviderName,ReporterName,Content,Timestamp,Status\n"
        val rows = reports.value.joinToString("\n") { 
            "${it.id},${it.providerName},${it.reporterName},${it.content},${it.timestamp},${it.status}"
        }
        logAudit(_adminRole.value, "تصدير البلاغات إلى ملف CSV")
        triggerNotification("📂 تم حفظ ملف الصادرات CSV بنجاح في مجلد التحميلات!")
        return header + rows
    }

    fun exportComplaintsToPDF(): String {
        logAudit(_adminRole.value, "تصدير البلاغات إلى تقرير PDF أسبوعي")
        triggerNotification("📃 تم إعداد وتصدير تقرير البلاغات الأسبوعي بصيغة PDF بنجاح!")
        return "Simulated PDF generated for reports count: ${reports.value.size}"
    }

    // --- FCM Alarm simulator ---
    private fun sendFcmAlert(title: String, body: String) {
        // Logging internal notification for review inside logs or triggers
        viewModelScope.launch {
            val systemMsg = "🔔 إشعار إداري (FCM): $title - $body"
            Log.d("FCM_SIMULATION", systemMsg)
        }
    }

    private fun logAudit(actor: String?, description: String) {
        viewModelScope.launch {
            val log = ActivityLogEntity(
                id = "log_" + UUID.randomUUID().toString().take(6),
                adminName = actor ?: "مشرف النظام",
                actionDesc = description,
                timestamp = System.currentTimeMillis()
            )
            db.insertActivityLog(log)
        }
    }
}
