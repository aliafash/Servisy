package com.example.data

import kotlinx.coroutines.flow.Flow

// 1. Categories (e.g. Plumbing, Electricity, Maintenance)
data class CategoryEntity(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val iconName: String, // Icon ID or Name
    val description: String,
    val displayOrder: Int = 0,
    val parentId: String = ""
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
    val basePrice: Double = 0.0,
    val isPinned: Boolean = false,
    val isRecommended: Boolean = false,
    val isVerified: Boolean = false,
    val subscriptionStatus: String = "NONE", // NONE, PENDING, APPROVED, EXPIRED
    val loyaltyPoints: Int = 0,
    val latitude: Double = 15.3694, // Standard default for Yemen (Sanaa)
    val longitude: Double = 44.1910,
    val photoUri: String = "",
    val idCardUri: String = "",
    val supportText: String = ""
)

// 3. Pending Application Requests
data class PendingProviderEntity(
    val id: String,
    val name: String,
    val phone: String,
    val categoryId: String,
    val area: String,
    val localNeighborhood: String = "",
    val coords: String = "",
    val photoUri: String = "",
    val idCardUri: String = "",
    val submitDate: Long = System.currentTimeMillis(),
    val rejectionReason: String = "",
    val status: String = "PENDING" // PENDING, APPROVED, REJECTED
)

// 4. Banners
data class BannerEntity(
    val id: String,
    val title: String,
    val imageUrl: String = "",
    val redirectUrl: String = "",
    val displayType: String = "IMAGE", // IMAGE, VIDEO, TEXT
    val bannerSize: String = "MEDIUM", // SMALL, MEDIUM, LARGE
    val durationSeconds: Int = 10,
    val isActive: Boolean = true
)

// 5. Customer Complaints and Reports
data class ReportEntity(
    val id: String,
    val providerId: String,
    val providerName: String = "",
    val reporterName: String = "",
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "PENDING"
)

// 6. Whitelisted Devices (Owner/Admins Whitelist)
data class DeviceWhitelistEntity(
    val id: String,
    val deviceName: String,
    val isAllowed: Boolean = true
)

// 7. Admin Action History Log (Owner Panel Auditing)
data class ActivityLogEntity(
    val id: String,
    val adminName: String,
    val actionDesc: String,
    val timestamp: Long = System.currentTimeMillis()
)

// 8. Interactive Real-Time Chat messages
data class ChatMessageEntity(
    val id: String,
    val senderId: String,
    val receiverId: String,
    val senderName: String,
    val receiverName: String,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val isOfflineSent: Boolean = false
)

// 9. Cities Managed by Admins
data class CityEntity(
    val id: String,
    val nameAr: String,
    val nameEn: String
)

// 10. Administrative and Advanced UI Custom Theme settings
data class AdminSettingsEntity(
    val id: String = "SINGLETON_SETTINGS",
    val appName: String = "كل خدمات اليمن",
    val welcomeMessage: String = "أهلاً ومرحباً بكم مع تطبيق كل خدمات اليمن - دقة، أمان وسرعة فائقة",
    val footerMessage: String = "MAW 777644670",
    val activeThemeId: String = "EMERALD_YEMEN", // EMERALD_YEMEN, COSMIC_SILVER, ACCENT_ORANGE, CUSTOM_THEME
    val showVipOnly: Boolean = false,
    val syncTimestamp: Long = System.currentTimeMillis(),
    
    // Support Contacts
    val supportPhone: String = "777644670",
    val supportEmail: String = "support@mawservices.com",
    val supportWhatsapp: String = "777644670",
    val appVersion: String = "V2.6.2026",
    
    // Passwords
    val adminPassword: String = "WAM2026:maher736462", // stored as credentials
    val hidePromoFooter: Boolean = false,
    
    // Smart Assistant Controls
    val assistantHidden: Boolean = false,
    val assistantSize: Int = 54, // size in dp
    val assistantIcon: String = "smart_bot",
    val assistantIconEffects: String = "none", // none, pulse, glow, rotate, glitch
    val assistantXPercent: Float = 0.9f,
    val assistantYPercent: Float = 0.85f,
    
    // Chat Floating Icon Controls
    val chatHidden: Boolean = false,
    val chatSize: Int = 54,
    val chatIcon: String = "chat_default",
    val chatIconEffects: String = "none", // none, pulse, glow, rotate, glitch
    val chatXPercent: Float = 0.9f,
    val chatYPercent: Float = 0.70f,
    
    // FCM Channels active status json/csv toggles
    val fcmChannelsJson: String = "join_requests:true,reports:true,memberships:true",
    
    // Theme Custom Colors (hexadecimal)
    val customPrimaryHex: String = "#047857",
    val customSecondaryHex: String = "#064E3B",
    val fontName: String = "DefaultBold",
    val fontColorHex: String = "#FFFFFF",
    
    // Operations & Search configs
    val isMaintenanceActive: Boolean = false,
    val isSpeechSearchEnabled: Boolean = true,
    val maxSearchRadiusKm: Int = 20,
    val isDataSaverActive: Boolean = false,
    val imageQualityPercent: Int = 75,
    val showSubscriptionsFeature: Boolean = true,
    val showLoyaltyFeature: Boolean = false,
    val showAddFirestoreDirectly: Boolean = false
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
    fun updateCategoryFilter(categoryId: String?)

    fun getSettingsFlow(): Flow<AdminSettingsEntity>
    suspend fun getSettings(): AdminSettingsEntity
    suspend fun saveSettings(settings: AdminSettingsEntity)

    // Pending Requests
    fun getPendingProvidersFlow(): Flow<List<PendingProviderEntity>>
    suspend fun insertPendingProvider(pending: PendingProviderEntity)
    suspend fun deletePendingProvider(id: String)
    suspend fun updatePendingStatus(id: String, status: String, rejectionReason: String = "")

    // Banners
    fun getBannersFlow(): Flow<List<BannerEntity>>
    suspend fun insertBanner(banner: BannerEntity)
    suspend fun deleteBanner(id: String)

    // Reports
    fun getReportsFlow(): Flow<List<ReportEntity>>
    suspend fun insertReport(report: ReportEntity)
    suspend fun deleteReport(id: String)

    // Logs
    fun getActivityLogsFlow(): Flow<List<ActivityLogEntity>>
    suspend fun insertActivityLog(log: ActivityLogEntity)

    // Devices
    fun getWhitelistDevicesFlow(): Flow<List<DeviceWhitelistEntity>>
    suspend fun insertDevice(device: DeviceWhitelistEntity)
    suspend fun deleteDevice(id: String)

    // Chat Messages
    fun getChatMessagesFlow(userId1: String, userId2: String): Flow<List<ChatMessageEntity>>
    fun getAllChatMessagesFlow(): Flow<List<ChatMessageEntity>>
    suspend fun insertChatMessage(msg: ChatMessageEntity)
    suspend fun clearAllChatMessages()

    // Cities
    fun getCitiesFlow(): Flow<List<CityEntity>>
    suspend fun insertCity(city: CityEntity)
    suspend fun deleteCity(id: String)

    // Supervisors
    fun getSupervisorsFlow(): Flow<List<SupervisorEntity>>
    suspend fun insertSupervisor(supervisor: SupervisorEntity)
    suspend fun deleteSupervisor(id: String)
}

// 11. Custom Supervisors (مشرفين)
data class SupervisorEntity(
    val id: String,
    val username: String,
    val password: String,
    val canAcceptRejectRequests: Boolean = true,
    val canManageCategories: Boolean = false,
    val canManageBanners: Boolean = false,
    val canDeleteProviders: Boolean = false,
    val canViewReports: Boolean = true
)

