package com.maw

import kotlinx.serialization.Serializable

@Serializable
data class PresetPalette(
    val accentHex: String = "",
    val bgHex: String = "",
    val name: String = "",
    val primaryHex: String = "",
    val surfaceHex: String = ""
)

@Serializable
data class FaqItem(
    val answer: String = "",
    val question: String = ""
)

@Serializable
data class AppSettings(
    val aboutEmail: String = "",
    val aboutEmailVisible: Boolean = true,
    val aboutImageUrl: String = "",
    val aboutImageVisible: Boolean = true,
    val aboutPhone: String = "",
    val aboutPhoneVisible: Boolean = true,
    val aboutSecurityLabel: String = "",
    val aboutSecurityValue: String = "",
    val aboutSecurityVisible: Boolean = true,
    val aboutShareUrl: String = "",
    val aboutShareUrlVisible: Boolean = true,
    val aboutTitleText: String = "",
    val aboutVersionLabel: String = "",
    val aboutVersionValue: String = "",
    val aboutVersionVisible: Boolean = true,
    val aboutWhatsapp: String = "",
    val aboutWhatsappVisible: Boolean = true,
    val accentColorHex: String = "#FFD700",
    val adminPassword: String = "admin",
    val appLogoText: String = "",
    val appLogoUrl: String = "",
    val appNameAr: String = "كل خدمات اليمن",
    val approvedProviderSortingMethod: String = "priority",
    val assistantIconColorHex: String = "#FFD700",
    val assistantIconHidden: Boolean = false,
    val assistantIconSize: Int = 48,
    val assistantIconSizePercent: Int = 100,
    val assistantIconType: String = "robot",
    val assistantIconXOffset: Int = 16,
    val assistantIconYOffset: Int = 16,
    val autoCleanupDays: Int = 30,
    val autocompleteLocationsEnabled: Boolean = true,
    val autocompleteNamesEnabled: Boolean = true,
    val autocompletePhonesEnabled: Boolean = true,
    val bgColorHex: String = "#132326",
    val blockedKeywords: List<String> = emptyList(),
    val bookingsRoutingMode: String = "admin",
    val chatDisabledMessage: String = "الدردشة معطلة حالياً",
    val chatIconColorHex: String = "#FFD700",
    val chatIconHidden: Boolean = false,
    val chatIconSize: Int = 48,
    val chatIconSizePercent: Int = 100,
    val colorsPresetsList: List<PresetPalette> = emptyList(),
    val downloadUrl: String = "",
    val faqList: List<FaqItem> = emptyList(),
    val fontColorHex: String = "#FFFFFF",
    val footerFontSize: Int = 11,
    val footerFontSizePercent: Int = 100,
    val footerOpacity: Float = 0.8f,
    val footerText: String = "حقوق الطبع محفوظة 2026",
    val footerTextVisible: Boolean = true,
    val geminiApiKey: String = "",
    val initiativeSupportNumber: String = "",
    val isBookingsEnabled: Boolean = true,
    val isChatEnabled: Boolean = true,
    val isGeoSearchEnabled: Boolean = true,
    val isPortfolioFeatureGloballyEnabled: Boolean = true,
    val isPortfolioUploadGloballyAllowed: Boolean = true,
    val isWebSpeechEnabled: Boolean = true,
    val maxPortfolioImages: Int = 10,
    val notificationsEnabled: Boolean = true,
    val primaryColorHex: String = "#132326",
    val radiusSearchLimitKm: Int = 30,
    val regAreaRequired: Boolean = true,
    val regAreaVisible: Boolean = true,
    val regCategoryRequired: Boolean = true,
    val regCategoryVisible: Boolean = true,
    val regChipBgColorsList: List<String> = emptyList(),
    val regDescRequired: Boolean = true,
    val regDescVisible: Boolean = true,
    val regIdCardRequired: Boolean = true,
    val regIdCardVisible: Boolean = true,
    val regNameRequired: Boolean = true,
    val regNameVisible: Boolean = true,
    val regPhoneRequired: Boolean = true,
    val regPhoneVisible: Boolean = true,
    val regSelfieRequired: Boolean = true,
    val regSelfieVisible: Boolean = true,
    val registrationChipColorHex: String = "#FFD700",
    val registrationRulesList: List<String> = emptyList(),
    val reviewSystemEnabled: Boolean = true,
    val searchBarVisible: Boolean = true,
    val searchMatchingMethodHex: String = "#FFD700",
    val searchRatingWeight: Float = 0.5f,
    val selectedFontName: String = "Cairo",
    val surfaceColorHex: String = "#1E3539",
    val welcomeMessage: String = "أهلاً بك في خدمات اليمن"
)

@Serializable
data class Provider(
    val allowedImageCount: Int = 5,
    val area: String = "",
    val category: String = "",
    val city: String = "",
    val description: String = "",
    val deviceId: String = "",
    val id: String = "",
    val imageUrl: String = "",
    val isPinned: Boolean = false,
    val isPortfolioEnabled: Boolean = true,
    val isPortfolioUploadEnabled: Boolean = true,
    val isRecommended: Boolean = false,
    val isSubscribed: Boolean = false,
    val isVerified: Boolean = false,
    val name: String = "",
    val nationalIdImageBase64: String = "",
    val orderPriority: Int = 0,
    val phone: String = "",
    val portfolioImages: List<String> = emptyList(),
    val rating: Double = 5.0,
    val skills: String = ""
)

@Serializable
data class PendingProvider(
    val area: String = "",
    val category: String = "",
    val city: String = "",
    val description: String = "",
    val deviceId: String = "",
    val id: String = "",
    val isFemale: Boolean = false,
    val name: String = "",
    val nationalIdImageBase64: String = "",
    val orderPriority: Int = 0,
    val phone: String = "",
    val portfolioImages: List<String> = emptyList(),
    val selfieImageBase64: String = "",
    val skills: String = ""
)

@Serializable
data class Category(
    val description: String = "",
    val iconUrl: String = "",
    val id: String = "",
    val isPinned: Boolean = false,
    val isPublished: Boolean = true,
    val nameAr: String = "",
    val nameEn: String = "",
    val order: Int = 0,
    val parentId: String = ""
)

@Serializable
data class City(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = ""
)

@Serializable
data class Review(
    val comment: String = "",
    val id: String = "",
    val providerId: String = "",
    val rating: Int = 5,
    val timestamp: Long = 0L,
    val userName: String = ""
)

@Serializable
data class Chat(
    val id: String = "",
    val lastMessage: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val timestamp: Long = 0L,
    val userName: String = ""
)

@Serializable
data class ChatMessage(
    val chatId: String = "",
    val id: String = "",
    val message: String = "",
    val senderName: String = "",
    val senderType: String = "",
    val timestamp: Long = 0L
)

@Serializable
data class Appointment(
    val clientName: String = "",
    val clientPhone: String = "",
    val destinationEntity: String = "",
    val details: String = "",
    val id: String = "",
    val preferredTime: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val reachesProvider: Boolean = false,
    val status: String = "",
    val timestamp: Long = 0L
)

@Serializable
data class Booking(
    val details: String = "",
    val id: String = "",
    val preferredTime: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val status: String = "",
    val timestamp: Long = 0L,
    val userId: String = "",
    val userName: String = ""
)

@Serializable
data class Report(
    val id: String = "",
    val issue: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val reporterName: String = "",
    val timestamp: Long = 0L
)

@Serializable
data class UserNotification(
    val body: String = "",
    val category: String = "",
    val id: String = "",
    val isRead: Boolean = false,
    val recipientId: String = "",
    val statusType: String = "",
    val time: String = "",
    val timestamp: Long = 0L,
    val title: String = ""
)

@Serializable
data class AdminAccount(
    val canApproveRequests: Boolean = false,
    val canDeleteActiveProviders: Boolean = false,
    val canManageBanners: Boolean = false,
    val canManageCategories: Boolean = false,
    val canSeeReports: Boolean = false,
    val passwordHash: String = "",
    val username: String = ""
)

@Serializable
data class AuditLog(
    val action: String = "",
    val adminName: String = "",
    val id: String = "",
    val timestamp: Long = 0L
)

@Serializable
data class Banner(
    val actionUrl: String = "",
    val contentType: String = "",
    val description: String = "",
    val durationSeconds: Int = 5,
    val id: String = "",
    val imageUrl: String = "",
    val size: Int = 0,
    val targetCategory: String = ""
)

@Serializable
data class ProviderCategoryRelation(
    val categoryId: String = "",
    val id: String = "",
    val providerId: String = ""
)

@Serializable
data class Part(
    val text: String = ""
)

@Serializable
data class Candidate(
    val content: Content? = null
)

@Serializable
data class Content(
    val parts: List<Part> = emptyList(),
    val role: String = ""
)

@Serializable
data class GenerateContentRequest(
    val contents: List<Content> = emptyList()
)

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate> = emptyList()
)
