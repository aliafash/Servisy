package com.example.data

import kotlinx.coroutines.flow.Flow

// 1. Categories (e.g. Plumbing, Electricity, Maintenance)
data class CategoryEntity(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val iconName: String, // Icon ID or Name
    val description: String
)

// 2. Service Providers / Professionals
data class ProviderEntity(
    val id: String,
    val name: String,
    val phone: String,
    val categoryId: String,
    val area: String,
    val isAvailable: Boolean = true,
    val ratingSum: Int = 0,
    val ratingCount: Int = 0,
    val isVip: Boolean = false,
    val basePrice: Double = 0.0
)

// 3. Administrative and UI Theme Synchronization Settings
data class AdminSettingsEntity(
    val id: String = "SINGLETON_SETTINGS",
    val appName: String = "كل خدمات اليمن",
    val footerMessage: String = "يربطك بأفضل المهنيين الفنيين واليدويين في اليمن دقة وأمان وسرعة",
    val activeThemeId: String = "EMERALD_YEMEN", // Themes: EMERALD_YEMEN, COSMIC_SILVER, ACCENT_ORANGE
    val showVipOnly: Boolean = false,
    val syncTimestamp: Long = System.currentTimeMillis()
)

// ------ Unified Repository / DAO Interface ------
interface AppDao {
    fun getCategoriesFlow(): Flow<List<CategoryEntity>>
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun deleteCategory(id: String)

    fun getProvidersFlow(): Flow<List<ProviderEntity>>
    suspend fun insertProvider(provider: ProviderEntity)
    suspend fun deleteProvider(id: String)
    suspend fun rateProvider(id: String, rating: Int)

    fun getSettingsFlow(): Flow<AdminSettingsEntity>
    suspend fun getSettings(): AdminSettingsEntity
    suspend fun saveSettings(settings: AdminSettingsEntity)
}
