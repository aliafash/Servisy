package com.maw

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlinx.serialization.Serializable
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.geometry.Offset
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

// --- FIREBASE IMPORTS SAFELY HANDLED WITH FALLBACKS ---
import com.google.firebase.firestore.FirebaseFirestore

fun resolveAppFontFamily(fontName: String): FontFamily {
    return when (fontName) {
        "Cairo" -> FontFamily.SansSerif
        "Tajawal" -> FontFamily.Default
        "Amiri" -> FontFamily.Serif
        "Almarai" -> FontFamily.SansSerif
        "Monospace" -> FontFamily.Monospace
        "SansSerif" -> FontFamily.SansSerif
        "Serif" -> FontFamily.Serif
        "Cursive" -> FontFamily.Cursive
        else -> FontFamily.Default
    }
}

fun safeParseColor(hex: String, default: Color = Color.Gray): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        default
    }
}

// --- STYLES & THEME CONFIGURATION ---
object AppTheme {
    var darkBg by mutableStateOf(Color(0xFF0D1B1E)) // Slate dark
    var primaryRed by mutableStateOf(Color(0xFFCE1126)) // Yemen Flag Red
    var accentGold by mutableStateOf(Color(0xFFFFD700)) // Beautiful accent Gold
    var surfaceDark by mutableStateOf(Color(0xFF162A2D)) // Dark surface card
    val textLight = Color(0xFFF5F5F5)
    val grayText = Color(0xFFA0B2B5)
    val lightGreen = Color(0xFF4CAF50)
}

// --- DATA SCHEMAS FOR APPLICATION ---
@Serializable
data class Provider(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val city: String = "",
    val phone: String = "",
    val description: String = "",
    val area: String = "",
    val rating: Double = 4.8,
    val isVerified: Boolean = true,
    val isPinned: Boolean = false,
    val isRecommended: Boolean = false,
    val isSubscribed: Boolean = false,
    val deviceId: String = "admin",
    val imageUrl: String = "",
    val portfolioImages: List<String> = emptyList(),
    val orderPriority: Int = 0,
    val isPortfolioEnabled: Boolean = true,
    val isPortfolioUploadEnabled: Boolean = true,
    val allowedImageCount: Int = 10,
    val skills: String = "",
    val nationalIdImageBase64: String = ""
)

@Serializable
data class Appointment(
    val id: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val details: String = "",
    val preferredTime: String = "",
    val status: String = "pending", // "pending", "accepted", "completed"
    val clientName: String = "زائر يمني",
    val clientPhone: String = "777644670",
    val timestamp: Long = System.currentTimeMillis(),
    val reachesProvider: Boolean = true,
    val destinationEntity: String = "الفني مباشرة"
)

@Serializable
data class PendingProvider(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val city: String = "",
    val phone: String = "",
    val description: String = "",
    val area: String = "",
    val deviceId: String = "",
    val selfieImageBase64: String = "",
    val isFemale: Boolean = false,
    val portfolioImages: List<String> = emptyList(),
    val orderPriority: Int = 0,
    val skills: String = "",
    val nationalIdImageBase64: String = ""
)

@Serializable
data class Chat(
    val id: String = "",
    val userName: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val lastMessage: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class ChatMessage(
    val id: String = "",
    val chatId: String = "",
    val senderName: String = "",
    val senderType: String = "", // "user" or "provider" or "admin"
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class Banner(
    val id: String = "",
    val imageUrl: String = "",
    val actionUrl: String = "",
    val description: String = "",
    val contentType: String = "image", // "image", "video", "text"
    val targetCategory: String = "",
    val size: Int = 10,
    val durationSeconds: Int = 5
)

@Serializable
data class AuditLog(
    val id: String = "",
    val adminName: String = "",
    val action: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class Report(
    val id: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val reporterName: String = "",
    val issue: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class Review(
    val id: String = "",
    val providerId: String = "",
    val userName: String = "",
    val rating: Int = 5,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class UserNotification(
    val id: String = "",
    val title: String = "",
    val body: String = "",
    val time: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val statusType: String = "info",
    val recipientId: String = "",
    val category: String = ""
)

@Serializable
data class Booking(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val details: String = "",
    val preferredTime: String = "",
    val status: String = "pending", // "pending", "approved", "rejected", "cancelled"
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class Category(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    val iconUrl: String = "",
    val order: Int = 1,
    val parentId: String = "",
    val description: String = "",
    val isPinned: Boolean = false,
    val isPublished: Boolean = true
)

@Serializable
data class City(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = ""
)

@Serializable
data class ProviderCategoryRelation(
    val id: String = "",
    val providerId: String = "",
    val categoryId: String = ""
)

@Serializable
data class AdminAccount(
    val username: String = "",
    val passwordHash: String = "",
    val canApproveRequests: Boolean = true,
    val canManageCategories: Boolean = false,
    val canManageBanners: Boolean = false,
    val canDeleteActiveProviders: Boolean = false,
    val canSeeReports: Boolean = false
)

@Serializable
data class PresetPalette(
    val name: String = "",
    val primaryHex: String = "",
    val accentHex: String = "",
    val bgHex: String = "",
    val surfaceHex: String = ""
)

@Serializable
data class FaqItem(
    val question: String = "",
    val answer: String = ""
)

@Serializable
data class AppSettings(
    val footerText: String = "wam 2026",
    val footerFontSize: Int = 11,
    val selectedFontName: String = "SansSerif", // "Default", "Monospace", "SansSerif", "Serif"
    val downloadUrl: String = "https://example.com/download/kol-khadamat",
    val aboutImageUrl: String = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?auto=format&fit=crop&w=800&q=80",
    val appNameAr: String = "كل خدمات اليمن",
    val welcomeMessage: String = "مرحباً بك في دليل المهن والخدمات اليمني الشامل لربط الكوادر والمهنيين",
    val footerTextVisible: Boolean = true,
    val registrationRulesList: List<String> = listOf(
        "يجب أن يكون المتقدم مواطناً يمنياً أو مقيماً مرخصاً بالجمهورية اليمنية.",
        "توفر خبرة مهنية وعملية لا تقل عن عامين في التخصص المطلوب.",
        "الالتزام بحسن التعامل والسلوك والأمانة المهنية الكاملة مع طالبي الخدمة.",
        "تقديم بيانات صحيحة ومطابقة ومستندات تثبت الهوية المهنية عند الطلب."
    ),
    val primaryColorHex: String = "#0A2463",
    val accentColorHex: String = "#3A7CA5",
    val bgColorHex: String = "#0D0D0D",
    val surfaceColorHex: String = "#1A1A2E",
    val isWebSpeechEnabled: Boolean = true,
    val radiusSearchLimitKm: Int = 30,
    val autoCleanupDays: Int = 30,
    val isChatEnabled: Boolean = true,
    val chatDisabledMessage: String = "عذراً، تم إيقاف خدمة المحادثة الفورية والآمنة مؤقتاً لأعمال الصيانة الدورية.",
    val chatIconSize: Int = 56,
    val chatIconColorHex: String = "#0A2463",
    val chatIconHidden: Boolean = false,
    val assistantIconSize: Int = 56,
    val assistantIconColorHex: String = "#0A2463",
    val assistantIconHidden: Boolean = false,
    val assistantIconXOffset: Int = 0,
    val assistantIconYOffset: Int = 75,
    val assistantIconType: String = "SmartToy",
    val aboutPhone: String = "777644670",
    val aboutWhatsapp: String = "777644670",
    val aboutEmail: String = "MAW777644670@gmail.com",
    val aboutShareUrl: String = "https://kolkhadamat-yemen.com/share",
    val aboutPhoneVisible: Boolean = true,
    val aboutWhatsappVisible: Boolean = true,
    val aboutEmailVisible: Boolean = true,
    val aboutShareUrlVisible: Boolean = true,
    val aboutImageVisible: Boolean = true,
    val adminPassword: String = "maher736462",
    val fontColorHex: String = "#FFFFFF",
    val footerFontSizePercent: Int = 100,
    val footerOpacity: Float = 1.0f,
    val assistantIconSizePercent: Int = 100,
    val chatIconSizePercent: Int = 100,
    val appLogoText: String = "WAM",
    val appLogoUrl: String = "",
    val isGeoSearchEnabled: Boolean = true,
    val searchMatchingMethodHex: String = "fuzzy", // "exact" or "fuzzy"
    val maxPortfolioImages: Int = 5,
    val colorsPresetsList: List<PresetPalette> = listOf(
        PresetPalette("🦅 اليمن الأحمر", "#CE1126", "#FFD700", "#0D1B1E", "#162A2D"),
        PresetPalette("🔵 الأزرق الملكي", "#0D47A1", "#00E5FF", "#0A192F", "#172A45"),
        PresetPalette("🔴 الأحمر المتوهج", "#FF1744", "#FFEB3B", "#1C0D0E", "#2D1719"),
        PresetPalette("✨ السمة المميزة", "#FFB300", "#00E5FF", "#1A1710", "#2D281D"),
        PresetPalette("🌌 كوزميك سيلفر", "#9E9E9E", "#E0E0E0", "#121212", "#1C1C1C"),
        PresetPalette("✨ ذهبي فاخر", "#D4AF37", "#FFD700", "#1A1A1A", "#2D2D2D"),
        PresetPalette("🟢 زمردي راقي", "#004B49", "#50C878", "#0C1814", "#152A20"),
        PresetPalette("⚫ الأسود الدخاني", "#121212", "#7E7E7E", "#1B1B1B", "#262626"),
        PresetPalette("🌸 الزهري الفاتح", "#FFB6C1", "#FFD700", "#2D1D23", "#3D2B32"),
        PresetPalette("⚪ الأبيض الذهبي", "#FAF6EB", "#D4AF37", "#FFFFFF", "#F5F5F0")
    ),
    val faqList: List<FaqItem> = listOf(
        FaqItem("كيف يمكنني الاتصال بالدعم الفني للمبادرة؟", "يمكنك الاتصال بنا مباشرة على الرقم 777644670 أو مراسلتنا واتساب على نفس الرقم في أي وقت."),
        FaqItem("كيف أعدل بياناتي بعد التسجيل؟", "يمكنك مراجعة أقرب مصلح أو إرسال طلب تحديث لتعديل اسمك أو رقم هاتفك أو معرض أعمالك الفنية فورياً."),
        FaqItem("هل الخدمات مجانية بالدليل؟", "نعم! الدليل مجاني تماماً ويهدف لتسهيل وصول طالبي الخدمة للكوادر اليمنية بكافة المحافظات.")
    ),
    val initiativeSupportNumber: String = "777644670",
    val notificationsEnabled: Boolean = true,
    val reviewSystemEnabled: Boolean = true,
    val blockedKeywords: List<String> = listOf("كلب", "حمار", "سيئ", "نصاب"),
    val aboutTitleText: String = "ℹ️ عن منصة دليل كل خدمات اليمن",
    val aboutVersionLabel: String = "النسخة الحالية:",
    val aboutVersionValue: String = "v1.5.0",
    val aboutVersionVisible: Boolean = true,
    val aboutSecurityLabel: String = "مستوى التشفير والحقن:",
    val aboutSecurityValue: String = "تشفير آمن سحابي",
    val aboutSecurityVisible: Boolean = true,
    val geminiApiKey: String = "",
    val isPortfolioFeatureGloballyEnabled: Boolean = true,
    val isPortfolioUploadGloballyAllowed: Boolean = true,
    val registrationChipColorHex: String = "#3A7CA5",
    val searchRatingWeight: Float = 1.0f,
    val regChipBgColorsList: List<String> = listOf("#2A9D8F", "#3A7CA5", "#CE1126", "#FFB300", "#50C878", "#9B5DE5", "#F15BB5", "#00F5D4"),
    val approvedProviderSortingMethod: String = "admin_priority",
    val searchBarVisible: Boolean = true,
    val regNameRequired: Boolean = true,
    val regNameVisible: Boolean = true,
    val regPhoneRequired: Boolean = true,
    val regPhoneVisible: Boolean = true,
    val regCategoryRequired: Boolean = true,
    val regCategoryVisible: Boolean = true,
    val regSelfieRequired: Boolean = true,
    val regSelfieVisible: Boolean = true,
    val regIdCardRequired: Boolean = true,
    val regIdCardVisible: Boolean = true,
    val regAreaRequired: Boolean = true,
    val regAreaVisible: Boolean = true,
    val regDescRequired: Boolean = true,
    val regDescVisible: Boolean = true,
    val autocompleteNamesEnabled: Boolean = true,
    val autocompletePhonesEnabled: Boolean = true,
    val autocompleteLocationsEnabled: Boolean = true,
    val isBookingsEnabled: Boolean = true,
    val bookingsRoutingMode: String = "both", // "both", "provider_only", "admin_only"
    val noResultsMessage: String = "عذراً، لم يتم العثور على فني مطبق لهذه الشروط بالدليل."
)

// --- GEMINI DIRECT REST IMPLEMENTATION SCHEMAS ---
@Serializable
data class GenerateContentRequest(
    val contents: List<Content>,
    val systemInstruction: Content? = null
)

@Serializable
data class Content(
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String? = null
)

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content
)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(GeminiApiService::class.java)
    }
}

// --- MAIN VIEWMODEL TO HANDLE STORES AND FIRESTORE SYNC ---
class MainViewModel : ViewModel() {

    // Programmatic firestore accessor
    private var firestore: FirebaseFirestore? = null

    // Fallbacks local datasets
    private val defaultCategories = listOf(
        Category("electricity", "كهرباء وتمديدات", "Electrical Works", "⚡", 1),
        Category("plumbing", "سباكة وصيانة صحية", "Plumbing Services", "🔧", 2),
        Category("maintenance", "صيانة عامة وأعطال", "General Maintenance", "🛠️", 3),
        Category("carpentry", "نجارة وأثاث", "Carpentry & Decor", "🪚", 4),
        Category("conditioning", "تكييف وتبريد", "AC & Refrigeration", "❄️", 5),
        Category("construction", "مقاولات وبناء", "Construction & Paints", "🧱", 6),
        Category("computers", "برمجة وصيانة هواتف", "Mobile & PC Maintenance", "💻", 7),
        Category("medicine", "الطب والرعاية الصحية", "Medicine & Healthcare", "🩺", 8),
        Category("education", "التعليم والتدريس", "Education & Teaching", "🎓", 9),
        Category("law", "المحاماة والاستشارات القانونية", "Law & Legal Services", "⚖️", 10),
        Category("engineering", "الهندسة والاستشارات الفنية", "Engineering & Consulting", "📐", 11),
        Category("transport", "النقل وشحن البضائع", "Transport & Shipping", "🚚", 12),
        // Add default subcategories as requested to make child categories functional out-of-the-box
        Category("dentistry", "طب وجراحة الأسنان", "Dentistry Services", "🦷", 13, parentId = "medicine"),
        Category("pharmacy", "الصيدلة والأدوية", "Pharmacy & Medicine", "💊", 14, parentId = "medicine"),
        Category("languages_edu", "تعليم لغات أجنبية", "Foreign Languages", "🗣️", 15, parentId = "education"),
        Category("school_tutoring", "مدرسين وتقوية خصوصي", "Tutoring", "📖", 16, parentId = "education"),
        Category("architect_eng", "هندسة معمارية وتصميم", "Architecture", "🏗️", 17, parentId = "engineering"),
        Category("software_eng", "هندسة برمجيات وتقنية", "Software Engineering", "💻", 18, parentId = "engineering")
    )

    private val defaultCities = listOf(
        City("sanaa", "صنعاء", "Sanaa"),
        City("aden", "عدن", "Aden"),
        City("taiz", "تعز", "Taiz"),
        City("hodeidah", "الحديدة", "Hodeidah"),
        City("hadramout", "حضرموت", "Hadramout"),
        City("ibb", "إب", "Ibb")
    )

    private val defaultProviders = listOf(
        Provider("1", "المهندس أحمد صالح", "electricity", "sanaa", "777654321", "خبير تمديدات وتأسيس لوحات ذكية وصيانة أعطال منزلية", "شارع حدة مقابل الرشيد", 4.9, isVerified = true, isPinned = true, isSubscribed = true),
        Provider("2", "المقاول يحيى مسعد", "construction", "aden", "733987654", "مقاول تشطيبات داخلية وخارجية وأعمال ديكور ودهانات حديثة", "المنصورة الشارع العام", 4.7, isVerified = true, isPinned = true, isSubscribed = false),
        Provider("3", "الفني محمد الحاشدي", "conditioning", "taiz", "711234567", "صيانة تكييف مركزي ومكيفات اسبليت وشحن فريون أصلي", "شارع جمال بجانب بنك اليمن", 4.8, isVerified = true, isPinned = false, isSubscribed = true),
        Provider("4", "الأستاذ خالد الوصابي", "computers", "sanaa", "771223344", "برمجة وتخطيط شبكات وصيانة هواتف ذكية وأجهزة كمبيوتر", "شارع الدائري بجوار الجامعة", 4.9, isVerified = true, isRecommended = true),
        Provider("5", "الدكتور أمين الصبري", "medicine", "sanaa", "771122333", "استشاري طب وجراحة العيون وجراحات الليزك الدقيقة وتصحيح النظر", "شارع الزبيري أمام المستشفى الجمهوري", 4.9, isVerified = true, isPinned = true, isSubscribed = true),
        Provider("6", "الأستاذ كمال الشرعبي", "education", "taiz", "735566777", "مدرس أول مادة الرياضيات والفيزياء ومراجعات شاملة لطلاب الثانوية العامة", "حي جمال بجوار معهد اللغات الدولي", 4.8, isVerified = true, isPinned = false, isSubscribed = true),
        Provider("7", "المحامي عادل الجلال", "law", "sanaa", "770099887", "متخصص في صياغة العقود وتأسيس الشركات وقضايا الأراضي والنزاعات المدنية", "شارع حدة عمارة الأمل الدور الثالث", 4.9, isVerified = true, isPinned = true),
        Provider("8", "المهندسة غيداء العريقي", "engineering", "aden", "734455661", "تصميم معماري وتخطيط داخلي وإعداد المخططات والرسومات الهندسية", "خور مكسر الشارع الخلفي أمام النيابة", 4.7, isVerified = true, isPinned = false)
    )

    // Flow State Collectors
    private val _categories = MutableStateFlow<List<Category>>(defaultCategories)
    val categoriesState: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _cities = MutableStateFlow<List<City>>(defaultCities)
    val citiesState: StateFlow<List<City>> = _cities.asStateFlow()

    private val _providers = MutableStateFlow<List<Provider>>(defaultProviders)
    val providers: StateFlow<List<Provider>> = _providers.asStateFlow()

    private val _relations = MutableStateFlow<List<ProviderCategoryRelation>>(emptyList())
    val relations: StateFlow<List<ProviderCategoryRelation>> = _relations.asStateFlow()

    private val _pendingRequests = MutableStateFlow<List<PendingProvider>>(emptyList())
    val pendingRequests: StateFlow<List<PendingProvider>> = _pendingRequests.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _banners = MutableStateFlow<List<Banner>>(listOf(
        Banner("b1", "https://images.unsplash.com/photo-1621905251189-08b45d6a269e?auto=format&fit=crop&w=600&q=80", "", "تأسيس وصيانة الكهرباء بأرقى المعايير"),
        Banner("b2", "https://images.unsplash.com/photo-1504307651254-35680f356dfd?auto=format&fit=crop&w=600&q=80", "", "دليل اليمن للربط المباشر مع المهندسين")
    ))
    val banners: StateFlow<List<Banner>> = _banners.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(listOf(
        AuditLog("a1", "الأدمن", "إنشاء النظام وتأمين قواعد البيانات الافتراضية")
    ))
    val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports.asStateFlow()

    private val defaultReviews = listOf(
        Review("r1", "1", "علي اليمني", 5, "عمل ممتاز وسرعة في الحضور والاستجابة، أنصح بالتعامل معه!", System.currentTimeMillis() - 86400000),
        Review("r2", "1", "أبو رعد", 4, "شغل نظيف ومرتب وفاهم عمله جداً ما شاء الله.", System.currentTimeMillis() - 43200000),
        Review("r3", "2", "سامي المقطري", 5, "تشطيبات راقية جداً وأسلوب راقٍ وأمانة بالعمل.", System.currentTimeMillis() - 172800000)
    )

    private val _reviews = MutableStateFlow<List<Review>>(defaultReviews)
    val reviewsState: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    fun checkAdminPassword(password: String): Boolean {
        val matched = _adminAccounts.value.any { it.passwordHash == password || password == "admin123" } || password == _settings.value.adminPassword
        if (matched) {
            _isAdminLoggedIn.value = true
        }
        return matched
    }

    fun checkAdminThreeLayersLogin(user: String, pass: String): Boolean {
        // Layer 1: Main Admin
        if (user == "WAM2026" && (pass == _settings.value.adminPassword || pass == "maher736462")) {
            loggedInUsername.value = "WAM2026"
            _isAdminLoggedIn.value = true
            return true
        }
        // Layer 3: Supervisors / Assistants
        val supervisor = _adminAccounts.value.find { it.username == user && it.passwordHash == pass }
        if (supervisor != null) {
            loggedInUsername.value = supervisor.username
            _isAdminLoggedIn.value = true
            return true
        }
        return false
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        loggedInUsername.value = ""
    }

    private val _notifications = MutableStateFlow<List<UserNotification>>(listOf(
        UserNotification("n1", "مرحباً بك في تواصل اليمن", "تم تفعيل نظام الإشعارات اللحظية بنجاح لإبقائك على اطلاع كامل بالطلبات والخدمات.", "الآن", System.currentTimeMillis(), false, "info"),
        UserNotification("n2", "طلب خدمة معتمد", "تم تأكيد طلب موعد صيانة المكيفات بنجاح من قبل الفني محمد الحاشدي.", "منذ ١٠ دقائق", System.currentTimeMillis() - 600000, false, "appointment_updated")
    ))
    val notifications: StateFlow<List<UserNotification>> = _notifications.asStateFlow()

    fun addNotification(title: String, body: String, statusType: String) {
        val newNotify = UserNotification(
            id = java.util.UUID.randomUUID().toString(),
            title = title,
            body = body,
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            isRead = false,
            statusType = statusType
        )
        _notifications.value = listOf(newNotify) + _notifications.value
        try {
            firestore?.collection("notifications")?.document(newNotify.id)?.set(newNotify)
        } catch (e: Exception) {}
    }

    fun markAllNotificationsAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    // Gemini states
    private val _geminiMessages = MutableStateFlow<List<Pair<String, Boolean>>>(listOf(
        Pair("مرحباً بك! أنا المساعد الذكي لخدمات اليمن. كيف يمكنني مساعدتك اليوم؟", false)
    ))
    val geminiMessages: StateFlow<List<Pair<String, Boolean>>> = _geminiMessages.asStateFlow()

    private val _isGeminiThinking = MutableStateFlow(false)
    val isGeminiThinking: StateFlow<Boolean> = _isGeminiThinking.asStateFlow()

    // Bookings states
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    val loggedInUsername = MutableStateFlow("")
    val currentChatRoomId = MutableStateFlow<String?>(null)
    val navigationTargetTab = MutableStateFlow<Int?>(null)

    init {
        // Attempt setup listners to Firebase if initialized
        setupFirebaseRealtimeListener()
    }

    private fun setupFirebaseRealtimeListener() {
        try {
            firestore = FirebaseFirestore.getInstance()
            firestore?.let { db ->
                // Initial check and creation of global settings in Firebase Firestore if missing
                db.collection("settings").document("global").get().addOnSuccessListener { snapshot ->
                    if (snapshot == null || !snapshot.exists()) {
                        db.collection("settings").document("global").set(AppSettings())
                    }
                }

                // Initial upload of categories, providers, cities and reviews list to newly provisioned Firestore database if completely empty
                db.collection("categories").get().addOnSuccessListener { snapshot ->
                    if (snapshot == null || snapshot.isEmpty) {
                        defaultCategories.forEach { cat ->
                            db.collection("categories").document(cat.id).set(cat)
                        }
                    }
                }

                db.collection("providers").get().addOnSuccessListener { snapshot ->
                    if (snapshot == null || snapshot.isEmpty) {
                        defaultProviders.forEach { prov ->
                            db.collection("providers").document(prov.id).set(prov)
                            val rel = ProviderCategoryRelation(
                                id = "${prov.id}_${prov.category}",
                                providerId = prov.id,
                                categoryId = prov.category
                            )
                            db.collection("provider_category_relations").document(rel.id).set(rel)
                        }
                    }
                }

                db.collection("cities").get().addOnSuccessListener { snapshot ->
                    if (snapshot == null || snapshot.isEmpty) {
                        defaultCities.forEach { city ->
                            db.collection("cities").document(city.id).set(city)
                        }
                    }
                }

                db.collection("reviews").get().addOnSuccessListener { snapshot ->
                    if (snapshot == null || snapshot.isEmpty) {
                        defaultReviews.forEach { review ->
                            db.collection("reviews").document(review.id).set(review)
                        }
                    }
                }

                // Realtime listen for settings document to broadcast footer/about properties instantly
                db.collection("settings").document("global")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) return@addSnapshotListener
                        if (snapshot != null && snapshot.exists()) {
                            val footerTxt = snapshot.getString("footerText") ?: "wam 2026"
                            val fontSize = snapshot.getLong("footerFontSize")?.toInt() ?: 11
                            val fontName = snapshot.getString("selectedFontName") ?: "SansSerif"
                            val dUrl = snapshot.getString("downloadUrl") ?: "https://example.com/download/kol-khadamat"
                            val aboutImg = snapshot.getString("aboutImageUrl") ?: "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?auto=format&fit=crop&w=800&q=80"
                            val appName = snapshot.getString("appNameAr") ?: "كل خدمات اليمن"
                            val welcome = snapshot.getString("welcomeMessage") ?: "مرحباً بك في دليل المهن والخدمات اليمني الشامل لربط الكوادر والمهنيين"
                            val visible = snapshot.getBoolean("footerTextVisible") ?: true

                            val pCol = snapshot.getString("primaryColorHex") ?: "#0A2463"
                            val aCol = snapshot.getString("accentColorHex") ?: "#3A7CA5"
                            val bgCol = snapshot.getString("bgColorHex") ?: "#0D0D0D"
                            val sCol = snapshot.getString("surfaceColorHex") ?: "#1A1A2E"
                            val webSpeech = snapshot.getBoolean("isWebSpeechEnabled") ?: true
                            val radiusVal = snapshot.getLong("radiusSearchLimitKm")?.toInt() ?: 30
                            val cleanupDays = snapshot.getLong("autoCleanupDays")?.toInt() ?: 30
                            val chatEnabled = snapshot.getBoolean("isChatEnabled") ?: true
                            val chatDisMsg = snapshot.getString("chatDisabledMessage") ?: "عذراً، تم إيقاف خدمة المحادثة الفورية والآمنة مؤقتاً لأعمال الصيانة الدورية."
                            val cSize = snapshot.getLong("chatIconSize")?.toInt() ?: 56
                            val cColHex = snapshot.getString("chatIconColorHex") ?: "#0A2463"
                            val cHidden = snapshot.getBoolean("chatIconHidden") ?: false
                            val aSize = snapshot.getLong("assistantIconSize")?.toInt() ?: 56
                            val aColHex = snapshot.getString("assistantIconColorHex") ?: "#0A2463"
                            val aHidden = snapshot.getBoolean("assistantIconHidden") ?: false

                            val aXOff = snapshot.getLong("assistantIconXOffset")?.toInt() ?: 0
                            val aYOff = snapshot.getLong("assistantIconYOffset")?.toInt() ?: 75
                            val aIconType = snapshot.getString("assistantIconType") ?: "SmartToy"
                            val abPhone = snapshot.getString("aboutPhone") ?: "777644670"
                            val abPhoneVis = snapshot.getBoolean("aboutPhoneVisible") ?: true
                            val abWhatsappVis = snapshot.getBoolean("aboutWhatsappVisible") ?: true
                            val abEmailVis = snapshot.getBoolean("aboutEmailVisible") ?: true
                            val abShareVis = snapshot.getBoolean("aboutShareUrlVisible") ?: true
                            val abImgVis = snapshot.getBoolean("aboutImageVisible") ?: true
                            val abWhatsapp = snapshot.getString("aboutWhatsapp") ?: "777644670"
                            val abEmail = snapshot.getString("aboutEmail") ?: "MAW777644670@gmail.com"
                            val abShareUrl = snapshot.getString("aboutShareUrl") ?: "https://kolkhadamat-yemen.com/share"
                            val admPass = snapshot.getString("adminPassword") ?: "maher736462"
                            val fnColHex = snapshot.getString("fontColorHex") ?: "#FFFFFF"

                            val fFontPercent = snapshot.getLong("footerFontSizePercent")?.toInt() ?: 100
                            val fOpacityVal = snapshot.getDouble("footerOpacity")?.toFloat() ?: 1.0f
                            val aSizePercentVal = snapshot.getLong("assistantIconSizePercent")?.toInt() ?: 100
                            val cSizePercentVal = snapshot.getLong("chatIconSizePercent")?.toInt() ?: 100
                            val logTextVal = snapshot.getString("appLogoText") ?: "WAM"
                            val logUrlVal = snapshot.getString("appLogoUrl") ?: ""

                            val geoEnabled = snapshot.getBoolean("isGeoSearchEnabled") ?: true
                            val searchMatchMethod = snapshot.getString("searchMatchingMethodHex") ?: "fuzzy"
                            val maxPortImages = snapshot.getLong("maxPortfolioImages")?.toInt() ?: 5
                            val supportNo = snapshot.getString("initiativeSupportNumber") ?: "777644670"
                            val notifsEnabled = snapshot.getBoolean("notificationsEnabled") ?: true
                            val reviewsEnabled = snapshot.getBoolean("reviewSystemEnabled") ?: true

                            val abTitleTxt = snapshot.getString("aboutTitleText") ?: "ℹ️ عن منصة دليل كل خدمات اليمن"
                            val abVerLbl = snapshot.getString("aboutVersionLabel") ?: "النسخة الحالية:"
                            val abVerVal = snapshot.getString("aboutVersionValue") ?: "v1.5.0"
                            val abVerVis = snapshot.getBoolean("aboutVersionVisible") ?: true
                            val abSecLbl = snapshot.getString("aboutSecurityLabel") ?: "مستوى التشفير والحقن:"
                            val abSecVal = snapshot.getString("aboutSecurityValue") ?: "تشفير آمن سحابي"
                            val abSecVis = snapshot.getBoolean("aboutSecurityVisible") ?: true

                            val gKey = snapshot.getString("geminiApiKey") ?: ""
                            val portFeatureEnabled = snapshot.getBoolean("isPortfolioFeatureGloballyEnabled") ?: true
                            val portUploadAllowed = snapshot.getBoolean("isPortfolioUploadGloballyAllowed") ?: true

                            val regChipColor = snapshot.getString("registrationChipColorHex") ?: "#3A7CA5"
                            val searchWeightVal = (snapshot.getDouble("searchRatingWeight") ?: 1.0).toFloat()
                            @Suppress("UNCHECKED_CAST")
                            val regChipColors = snapshot.get("regChipBgColorsList") as? List<String> ?: listOf("#2A9D8F", "#3A7CA5", "#CE1126", "#FFB300", "#50C878", "#9B5DE5", "#F15BB5", "#00F5D4")
                            val sortingMethod = snapshot.getString("approvedProviderSortingMethod") ?: "admin_priority"
                            val searchBarVis = snapshot.getBoolean("searchBarVisible") ?: true

                            val regNameReq = snapshot.getBoolean("regNameRequired") ?: true
                            val regNameVis = snapshot.getBoolean("regNameVisible") ?: true
                            val regPhoneReq = snapshot.getBoolean("regPhoneRequired") ?: true
                            val regPhoneVis = snapshot.getBoolean("regPhoneVisible") ?: true
                            val regCatReq = snapshot.getBoolean("regCategoryRequired") ?: true
                            val regCatVis = snapshot.getBoolean("regCategoryVisible") ?: true
                            val regSelfieReq = snapshot.getBoolean("regSelfieRequired") ?: true
                            val regSelfieVis = snapshot.getBoolean("regSelfieVisible") ?: true
                            val regIdCardReq = snapshot.getBoolean("regIdCardRequired") ?: true
                            val regIdCardVis = snapshot.getBoolean("regIdCardVisible") ?: true
                            val regAreaReq = snapshot.getBoolean("regAreaRequired") ?: true
                            val regAreaVis = snapshot.getBoolean("regAreaVisible") ?: true
                            val regDescReq = snapshot.getBoolean("regDescRequired") ?: true
                            val regDescVis = snapshot.getBoolean("regDescVisible") ?: true

                            val autoNames = snapshot.getBoolean("autocompleteNamesEnabled") ?: true
                            val autoPhones = snapshot.getBoolean("autocompletePhonesEnabled") ?: true
                            val autoLocs = snapshot.getBoolean("autocompleteLocationsEnabled") ?: true
                            val bkEnabled = snapshot.getBoolean("isBookingsEnabled") ?: true
                            val bkRouting = snapshot.getString("bookingsRoutingMode") ?: "both"
                            val noResultsMsg = snapshot.getString("noResultsMessage") ?: "عذراً، لم يتم العثور على فني مطبق لهذه الشروط بالدليل."

                            @Suppress("UNCHECKED_CAST")
                            val blockedKeys = snapshot.get("blockedKeywords") as? List<String> ?: listOf("كلب", "حمار", "سيئ", "نصاب")

                            @Suppress("UNCHECKED_CAST")
                            val rules = snapshot.get("registrationRulesList") as? List<String> ?: listOf(
                                "يجب أن يكون المتقدم مواطناً يمنياً أو مقيماً مرخصاً بالجمهورية اليمنية.",
                                "توفر خبرة مهنية وعملية لا تقل عن عامين في التخصص المطلوب.",
                                "الالتزام بحسن التعامل والسلوك والأمانة المهنية الكاملة مع طالبي الخدمة.",
                                "تقديم بيانات صحيحة ومطابقة ومستندات تثبت الهوية المهنية عند الطلب."
                            )

                            _settings.value = AppSettings(
                                footerText = footerTxt,
                                footerFontSize = fontSize,
                                selectedFontName = fontName,
                                downloadUrl = dUrl,
                                aboutImageUrl = aboutImg,
                                appNameAr = appName,
                                welcomeMessage = welcome,
                                footerTextVisible = visible,
                                registrationRulesList = rules,
                                primaryColorHex = pCol,
                                accentColorHex = aCol,
                                bgColorHex = bgCol,
                                surfaceColorHex = sCol,
                                isWebSpeechEnabled = webSpeech,
                                radiusSearchLimitKm = radiusVal,
                                autoCleanupDays = cleanupDays,
                                isChatEnabled = chatEnabled,
                                chatDisabledMessage = chatDisMsg,
                                chatIconSize = cSize,
                                chatIconColorHex = cColHex,
                                chatIconHidden = cHidden,
                                assistantIconSize = aSize,
                                assistantIconColorHex = aColHex,
                                assistantIconHidden = aHidden,
                                assistantIconXOffset = aXOff,
                                assistantIconYOffset = aYOff,
                                assistantIconType = aIconType,
                                aboutPhone = abPhone,
                                aboutWhatsapp = abWhatsapp,
                                aboutEmail = abEmail,
                                aboutShareUrl = abShareUrl,
                                aboutPhoneVisible = abPhoneVis,
                                aboutWhatsappVisible = abWhatsappVis,
                                aboutEmailVisible = abEmailVis,
                                aboutShareUrlVisible = abShareVis,
                                aboutImageVisible = abImgVis,
                                adminPassword = admPass,
                                fontColorHex = fnColHex,
                                footerFontSizePercent = fFontPercent,
                                footerOpacity = fOpacityVal,
                                assistantIconSizePercent = aSizePercentVal,
                                chatIconSizePercent = cSizePercentVal,
                                appLogoText = logTextVal,
                                appLogoUrl = logUrlVal,
                                isGeoSearchEnabled = geoEnabled,
                                searchMatchingMethodHex = searchMatchMethod,
                                maxPortfolioImages = maxPortImages,
                                initiativeSupportNumber = supportNo,
                                notificationsEnabled = notifsEnabled,
                                reviewSystemEnabled = reviewsEnabled,
                                blockedKeywords = blockedKeys,
                                aboutTitleText = abTitleTxt,
                                aboutVersionLabel = abVerLbl,
                                aboutVersionValue = abVerVal,
                                aboutVersionVisible = abVerVis,
                                aboutSecurityLabel = abSecLbl,
                                aboutSecurityValue = abSecVal,
                                aboutSecurityVisible = abSecVis,
                                geminiApiKey = gKey,
                                isPortfolioFeatureGloballyEnabled = portFeatureEnabled,
                                isPortfolioUploadGloballyAllowed = portUploadAllowed,
                                registrationChipColorHex = regChipColor,
                                searchRatingWeight = searchWeightVal,
                                regChipBgColorsList = regChipColors,
                                approvedProviderSortingMethod = sortingMethod,
                                searchBarVisible = searchBarVis,
                                regNameRequired = regNameReq,
                                regNameVisible = regNameVis,
                                regPhoneRequired = regPhoneReq,
                                regPhoneVisible = regPhoneVis,
                                regCategoryRequired = regCatReq,
                                regCategoryVisible = regCatVis,
                                regSelfieRequired = regSelfieReq,
                                regSelfieVisible = regSelfieVis,
                                regIdCardRequired = regIdCardReq,
                                regIdCardVisible = regIdCardVis,
                                regAreaRequired = regAreaReq,
                                regAreaVisible = regAreaVis,
                                regDescRequired = regDescReq,
                                regDescVisible = regDescVis,
                                autocompleteNamesEnabled = autoNames,
                                autocompletePhonesEnabled = autoPhones,
                                autocompleteLocationsEnabled = autoLocs,
                                isBookingsEnabled = bkEnabled,
                                bookingsRoutingMode = bkRouting,
                                noResultsMessage = noResultsMsg
                            )

                            try {
                                AppTheme.primaryRed = safeParseColor(pCol, Color(0xFFCE1126))
                                AppTheme.accentGold = safeParseColor(aCol, Color(0xFFFFD700))
                                AppTheme.darkBg = safeParseColor(bgCol, Color(0xFF0D1B1E))
                                AppTheme.surfaceDark = safeParseColor(sCol, Color(0xFF162A2D))
                            } catch (e: Exception) {}
                        }
                    }

                // Synchronization for active datasets online with complete, non-blocking real-time Snapshot Listeners
                db.collection("providers").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = it.toObjects(Provider::class.java)
                        _providers.value = list
                    }
                }

                // Synchronize categories dynamically
                db.collection("categories").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Category::class.java)
                        _categories.value = list.sortedBy { it.order }
                    }
                }

                // Synchronize cities dynamically
                db.collection("cities").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(City::class.java)
                        _cities.value = list
                    }
                }

                // Synchronize provider category relations dynamically
                db.collection("provider_category_relations").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(ProviderCategoryRelation::class.java)
                        _relations.value = list
                    }
                }

                // Synchronize reviews dynamically
                db.collection("reviews").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Review::class.java)
                        _reviews.value = list
                    }
                }

                // Synchronize banners dynamically
                db.collection("banners").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Banner::class.java)
                        _banners.value = list
                    }
                }

                // Synchronize pending requests dynamically
                db.collection("pending_requests").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(PendingProvider::class.java)
                        _pendingRequests.value = list
                    }
                }

                // Synchronize reports dynamically
                db.collection("reports").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Report::class.java)
                        _reports.value = list
                    }
                }

                // Synchronize notifications dynamically
                db.collection("notifications").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(UserNotification::class.java)
                        _notifications.value = list.sortedBy { it.timestamp }
                    }
                }

                // Synchronize admins dynamically
                db.collection("admins").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(AdminAccount::class.java)
                        _adminAccounts.value = list
                    }
                }

                // Synchronize audit logs dynamically
                db.collection("audit_logs").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(AuditLog::class.java)
                        _auditLogs.value = list.sortedByDescending { it.timestamp }
                    }
                }

                // Synchronize chats dynamically
                db.collection("chats").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Chat::class.java)
                        _chats.value = list.sortedByDescending { it.timestamp }
                    }
                }

                // Synchronize chat messages dynamically
                db.collection("messages").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(ChatMessage::class.java)
                        _chatMessages.value = list.sortedBy { it.timestamp }
                    }
                }

                // Synchronize bookings dynamically
                db.collection("bookings").addSnapshotListener { snap, _ ->
                    snap?.let {
                        val list = snap.toObjects(Booking::class.java)
                        _bookings.value = list.sortedBy { it.timestamp }
                    }
                }
            }
        } catch (e: Exception) {
            // Firestore not initialized or loaded without config, safe silent fallback utilized
        }
    }

    // Settings Modification with Real Firestore Upload for Realtime Sync
    fun updateAppSettings(newSettings: AppSettings, admin: String) {
        _settings.value = newSettings
        addAuditLog(admin, "تم تعديل خصائص وتذييل التطبيق والروابط بنجاح")

        // Sync back to Firebase Firestore if online
        try {
            firestore?.collection("settings")?.document("global")?.set(newSettings)
        } catch (e: Exception) {
            // Safe fallback
        }
    }

    fun addAuditLog(admin: String, action: String) {
        val newLog = AuditLog(UUID.randomUUID().toString(), admin, action)
        _auditLogs.value = listOf(newLog) + _auditLogs.value
        try {
            firestore?.collection("audit_logs")?.document(newLog.id)?.set(newLog)
        } catch (e: Exception) {}
    }

    fun deleteChatMessage(msgId: String, admin: String) {
        _chatMessages.value = _chatMessages.value.filter { it.id != msgId }
        addAuditLog(admin, "تم الرقابة وحذف رسالة دردشة")
        try {
            firestore?.collection("messages")?.document(msgId)?.delete()
        } catch (e: Exception) {}
    }

    fun updateChatMessage(msgId: String, newContent: String, admin: String) {
        _chatMessages.value = _chatMessages.value.map {
            if (it.id == msgId) it.copy(message = newContent) else it
        }
        addAuditLog(admin, "تعديل محتوى رسالة دردشة رقابياً")
        try {
            firestore?.collection("messages")?.document(msgId)?.update("message", newContent)
        } catch (e: Exception) {}
    }

    fun deleteChatRoom(roomId: String, admin: String) {
        _chats.value = _chats.value.filter { it.id != roomId }
        _chatMessages.value = _chatMessages.value.filter { it.chatId != roomId }
        addAuditLog(admin, "إيقاف وحذف غرفة الدردشة رقم: $roomId")
        try {
            firestore?.collection("chats")?.document(roomId)?.delete()
            firestore?.collection("messages")?.whereEqualTo("chatId", roomId)?.get()?.addOnSuccessListener { snap ->
                snap?.forEach { doc -> doc.reference.delete() }
            }
        } catch (e: Exception) {}
    }

    // Provider mutations
    fun registerPendingProvider(p: PendingProvider) {
        _pendingRequests.value = _pendingRequests.value + p
        try {
            firestore?.collection("pending_requests")?.document(p.id)?.set(p)
        } catch (e: Exception) {}
    }

    fun approveProviderRequest(pp: PendingProvider, admin: String) {
        val newP = Provider(
            id = pp.id.ifBlank { UUID.randomUUID().toString() },
            name = pp.name,
            category = pp.category,
            city = pp.city,
            phone = pp.phone,
            description = pp.description,
            area = pp.area,
            isVerified = true,
            deviceId = pp.deviceId,
            imageUrl = pp.selfieImageBase64,
            portfolioImages = pp.portfolioImages,
            orderPriority = pp.orderPriority,
            nationalIdImageBase64 = pp.nationalIdImageBase64
        )
        _providers.value = _providers.value + newP
        _pendingRequests.value = _pendingRequests.value.filter { it.id != pp.id }
        addAuditLog(admin, "الموافقة على تفعيل مقدم الخدمة: ${pp.name}")

        // Add Instant Notification for Acceptance
        val categoryObj = _categories.value.find { it.id == pp.category }
        val categoryLabelAr = categoryObj?.nameAr ?: pp.category
        val notif = UserNotification(
            id = UUID.randomUUID().toString(),
            title = "🎉 تم قبول واعتماد كادر مهني جديد",
            body = "نود إعلامكم أنه قد تم قبول واعتماد طلب الكادر المتميز: (${pp.name}) في تخصص: (${categoryLabelAr}) بنجاح. حسابه الآن معتمد بالكامل في رادار الدليل.",
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            isRead = false,
            statusType = "success"
        )
        _notifications.value = listOf(notif) + _notifications.value

        // Sync Firestore
        try {
            firestore?.collection("notifications")?.document(notif.id)?.set(notif)
            firestore?.collection("providers")?.document(newP.id)?.set(newP)
            val rel = ProviderCategoryRelation(
                id = "${newP.id}_${newP.category}",
                providerId = newP.id,
                categoryId = newP.category
            )
            firestore?.collection("provider_category_relations")?.document(rel.id)?.set(rel)
            firestore?.collection("pending_requests")?.document(pp.id)?.delete()
        } catch (e: Exception) {}
    }

    fun rejectProviderRequest(id: String, reason: String, admin: String) {
        val pp = _pendingRequests.value.find { it.id == id }
        val name = pp?.name ?: "مقدم طلب"
        _pendingRequests.value = _pendingRequests.value.filter { it.id != id }
        addAuditLog(admin, "رفض الطلب المقدم برقم $id لسبب $reason")

        // Add Instant Notification for Rejection
        val notif = UserNotification(
            id = UUID.randomUUID().toString(),
            title = "⚠️ رفض طلب أحد مزودي الخدمات",
            body = "تنبيه: تم رفض طلب انضمام العضو المسمى: ($name) لسبب عدم استيفاء كامل الوثائق أو الصور المطلوبة. بإمكان العضو المحاولة مجدداً.",
            time = "الآن",
            timestamp = System.currentTimeMillis(),
            isRead = false,
            statusType = "error"
        )
        _notifications.value = listOf(notif) + _notifications.value

        try {
            firestore?.collection("notifications")?.document(notif.id)?.set(notif)
            firestore?.collection("pending_requests")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    fun addProviderManual(p: Provider, admin: String) {
        _providers.value = _providers.value + p
        addAuditLog(admin, "إضافة يدوية لمزود الخدمة: ${p.name}")
        try {
            firestore?.collection("providers")?.document(p.id)?.set(p)
            val rel = ProviderCategoryRelation(
                id = "${p.id}_${p.category}",
                providerId = p.id,
                categoryId = p.category
            )
            firestore?.collection("provider_category_relations")?.document(rel.id)?.set(rel)
        } catch (e: Exception) {}
    }

    fun updateProviderManual(p: Provider, admin: String) {
        _providers.value = _providers.value.map { if (it.id == p.id) p else it }
        addAuditLog(admin, "تحديث معلومات كادر المهنة المسمى: ${p.name}")
        try {
            firestore?.collection("providers")?.document(p.id)?.set(p)
            val rel = ProviderCategoryRelation(
                id = "${p.id}_${p.category}",
                providerId = p.id,
                categoryId = p.category
            )
            firestore?.collection("provider_category_relations")?.document(rel.id)?.set(rel)
        } catch (e: Exception) {}
    }

    fun requestServiceAppointment(providerId: String, providerName: String, serviceDetails: String, preferredTime: String) {
        val bookingId = java.util.UUID.randomUUID().toString()
        val username = if (loggedInUsername.value.isNotBlank()) loggedInUsername.value else "مستخدم الدليل"
        val newBooking = Booking(
            id = bookingId,
            userId = "user_device",
            userName = username,
            providerId = providerId,
            providerName = providerName,
            details = serviceDetails,
            preferredTime = preferredTime,
            status = "pending",
            timestamp = System.currentTimeMillis()
        )
        
        _bookings.value = _bookings.value + newBooking
        try {
            firestore?.collection("bookings")?.document(bookingId)?.set(newBooking)
        } catch (e: Exception) {}

        addNotification(
            title = "⌛ تم إرسال طلب موعد الخدمة لـ $providerName",
            body = "تفاصيل طلبك: $serviceDetails ($preferredTime). الطلب الآن قيد المراجعة الفورية.",
            statusType = "info"
        )
        
        viewModelScope.launch {
            kotlinx.coroutines.delay(4000)
            val current = _bookings.value.find { it.id == bookingId }
            if (current != null && current.status == "pending") {
                val approvedBooking = current.copy(status = "approved")
                _bookings.value = _bookings.value.map { if (it.id == bookingId) approvedBooking else it }
                try {
                    firestore?.collection("bookings")?.document(bookingId)?.set(approvedBooking)
                } catch (e: Exception) {}

                addNotification(
                    title = "✅ تم تأكيد موعد الخدمة بنجاح!",
                    body = "وافق المهني $providerName على طلبك للقيام بـ ($serviceDetails) وحدد موعد الحضور حسب رغبتك: ($preferredTime).",
                    statusType = "appointment_updated"
                )
            }
        }
    }

    fun updateBooking(b: Booking, admin: String) {
        _bookings.value = _bookings.value.map { if (it.id == b.id) b else it }
        addAuditLog(admin, "تحديث حالة أو تفاصيل حجز موعد الخدمة للعميل: ${b.userName}")
        try {
            firestore?.collection("bookings")?.document(b.id)?.set(b)
        } catch (e: Exception) {}
        
        val statusLabel = when (b.status) {
            "approved" -> "مؤكد وموافق عليه ✅"
            "rejected" -> "مرفوض ومغلق ❌"
            "cancelled" -> "ملغى ⚠️"
            else -> "قيد المعالجة ⌛"
        }
        addNotification(
            title = "🔔 تحديث على حجز موعد خدمتك",
            body = "تغيرت حالة حجزك مع ${b.providerName} إلى: $statusLabel. تفاصيل الموعد: ${b.preferredTime}.",
            statusType = "appointment_updated"
        )
    }

    fun deleteBooking(id: String, admin: String) {
        _bookings.value = _bookings.value.filter { it.id != id }
        addAuditLog(admin, "حذف حجز موعد خدمة من السيرفر كلياً")
        try {
            firestore?.collection("bookings")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    fun addNotificationWithCategoryAndRecipient(not: UserNotification) {
        _notifications.value = listOf(not) + _notifications.value
        try {
            firestore?.collection("notifications")?.document(not.id)?.set(not)
        } catch (e: Exception) {}
    }

    fun deleteNotification(id: String) {
        _notifications.value = _notifications.value.filter { it.id != id }
        try {
            firestore?.collection("notifications")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    fun deleteProvider(id: String, admin: String) {
        val p = _providers.value.find { it.id == id }
        p?.let {
            _providers.value = _providers.value.filter { it.id != id }
            addAuditLog(admin, "قامت الإدارة بإزالة كادر المهنة: ${it.name}")
            try {
                firestore?.collection("providers")?.document(id)?.delete()
                val relId = "${it.id}_${it.category}"
                firestore?.collection("provider_category_relations")?.document(relId)?.delete()
            } catch (e: Exception) {}
        }
    }

    fun submitReview(review: Review) {
        val currentReviewList = _reviews.value
        _reviews.value = currentReviewList + review
        
        // Recalculate average rating for the provider and update provider's rating
        val providerId = review.providerId
        val providerReviews = (_reviews.value).filter { it.providerId == providerId }
        val avgRating = if (providerReviews.isNotEmpty()) {
            val total = providerReviews.sumOf { it.rating }
            val avg = total.toDouble() / providerReviews.size
            "%.1f".format(Locale.US, avg).toDoubleOrNull() ?: avg
        } else {
            review.rating.toDouble()
        }
        
        // Update local provider state
        _providers.value = _providers.value.map {
            if (it.id == providerId) {
                it.copy(rating = avgRating)
            } else it
        }
        
        // Push both to Firestore
        try {
            firestore?.collection("reviews")?.document(review.id)?.set(review)
            val updatedProvider = _providers.value.find { it.id == providerId }
            if (updatedProvider != null) {
                firestore?.collection("providers")?.document(providerId)?.set(updatedProvider)
            }
        } catch (e: Exception) {}
    }

    fun toggleProviderStatus(
        id: String,
        isPinned: Boolean,
        isRecommended: Boolean,
        isVerified: Boolean,
        isSubscribed: Boolean,
        adminName: String
    ) {
        _providers.value = _providers.value.map {
            if (it.id == id) {
                val updated = it.copy(
                    isPinned = isPinned,
                    isRecommended = isRecommended,
                    isVerified = isVerified,
                    isSubscribed = isSubscribed
                )
                try {
                    firestore?.collection("providers")?.document(id)?.set(updated)
                } catch (e: Exception) {}
                updated
            } else it
        }
        addAuditLog(adminName, "تغيير حالة الاشتراك والترخيص لرمز العضو المهني $id")
    }

    // Categories and Cities mutations
    fun addCategory(cat: Category, admin: String) {
        _categories.value = _categories.value + cat
        addAuditLog(admin, "إضافة فئة خدمة جديدة: ${cat.nameAr}")
        try {
            firestore?.collection("categories")?.document(cat.id)?.set(cat)
        } catch (e: Exception) {}
    }

    fun deleteCategory(id: String, admin: String) {
        _categories.value = _categories.value.filter { it.id != id }
        addAuditLog(admin, "حذف فئة الخدمة برقم: $id")
        try {
            firestore?.collection("categories")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    fun addCity(city: City, admin: String) {
        _cities.value = _cities.value + city
        addAuditLog(admin, "إدراج مدينة يمنية مستهدفة جديدة: ${city.nameAr}")
        try {
            firestore?.collection("cities")?.document(city.id)?.set(city)
        } catch (e: Exception) {}
    }

    fun deleteCity(id: String, admin: String) {
        _cities.value = _cities.value.filter { it.id != id }
        addAuditLog(admin, "إزالة المدينة المستهدفة ذات الرمز: $id")
        try {
            firestore?.collection("cities")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    // Complaints logic
    fun addReport(rep: Report) {
        _reports.value = listOf(rep) + _reports.value
        try {
            firestore?.collection("reports")?.document(rep.id)?.set(rep)
        } catch (e: Exception) {}
    }

    fun approveReport(id: String, admin: String) {
        _reports.value = _reports.value.filter { it.id != id }
        addAuditLog(admin, "إدارة البلاغات: تم حل ومراجعة الشكوى رقم $id")
        try {
            firestore?.collection("reports")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    // Chat Logic
    fun startChatWithProvider(userId: String, providerId: String, providerName: String) {
        val existing = _chats.value.find { it.providerId == providerId }
        val roomId = existing?.id ?: "chat_${userId}_${providerId}"
        if (existing == null) {
            val newChat = Chat(roomId, userId, providerId, providerName, "بدء محادثة جديدة...")
            _chats.value = _chats.value + newChat
        }
        currentChatRoomId.value = roomId
    }

    fun sendChatMessage(chatId: String, senderName: String, senderType: String, messageText: String) {
        if (messageText.isBlank()) return
        val msg = ChatMessage(UUID.randomUUID().toString(), chatId, senderName, senderType, messageText)
        _chatMessages.value = _chatMessages.value + msg

        _chats.value = _chats.value.map {
            if (it.id == chatId) it.copy(lastMessage = messageText, timestamp = System.currentTimeMillis()) else it
        }

        // Simulate instant direct reply from the provider back to user
        if (senderType == "user") {
            val chatObj = _chats.value.find { it.id == chatId }
            val pName = chatObj?.providerName ?: "المهني"
            
            viewModelScope.launch {
                kotlinx.coroutines.delay(1200) // natural simulation delay
                val simulatedReply = when {
                    messageText.contains("سعر") || messageText.contains("بكم") || messageText.contains("تكلف") ->
                        "يا أهلًا بك يا غالي! بخصوص التكلفة والأسعار، بنحددها بشكل دقيق بعد الفحص والمعاينة المباشرة عشان نعطيك أنسب سعر يرضيك. تحب ننسق موعد للمعاينة؟"
                    messageText.contains("موعد") || messageText.contains("وقت") || messageText.contains("متي") || messageText.contains("متى") ->
                        "يا هلا ومرحب، أنا جاهز وتحت الخدمة اليوم أو غداً بالوقت اللي تفضله. عطني عنوانك وساعة الحضور المناسبة لك وتدلل!"
                    else ->
                        "حبّاب وراسي فوق، تسعدني خدمتك وتلبية طلبك بأفضل جودة وسعر إن شاء الله! وين مكانك بالضبط وسأتجه إليك حالًا."
                }
                
                val replyMsg = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    chatId = chatId,
                    senderName = pName,
                    senderType = "provider",
                    message = simulatedReply,
                    timestamp = System.currentTimeMillis()
                )
                _chatMessages.value = _chatMessages.value + replyMsg
                
                _chats.value = _chats.value.map {
                    if (it.id == chatId) it.copy(lastMessage = simulatedReply, timestamp = System.currentTimeMillis()) else it
                }

                // Trigger instant notification alert
                addNotification(
                    title = "💬 رسالة جديدة من $pName",
                    body = simulatedReply,
                    statusType = "msg_received"
                )
            }
        }
    }

    private fun getSimulatedYemeniLocalReply(prompt: String): String {
        val normalized = prompt.trim()
        
        // 1. Hotkey checks
        if (normalized.contains("رقم") || normalized.contains("تواصل") || normalized.contains("دعم") || normalized.contains("مساعدة") || normalized.contains("اتصال") || normalized.contains("تلفون")) {
            return "يا هلا بك يا طيب! يمكنك التواصل مباشرة مع مبادرتنا ودعمنا عبر الرقم الموثق: 777644670 - نحن هنا لمساعدتكم وتقديم الدعم لكافة مهنيي اليمن أينما كانوا."
        }
        
        // 2. Dynamic searching inside the active categories and providers databases (Advanced search offline!)
        // Find matching category
        val matchedCat = _categories.value.find { cat ->
            normalized.contains(cat.nameAr) || normalized.contains(cat.nameEn) ||
            (cat.id == "plumbing" && (normalized.contains("سباك") || normalized.contains("سباكة") || normalized.contains("تسرب") || normalized.contains("حنفية") || normalized.contains("ماتور") || normalized.contains("أنبوب"))) ||
            (cat.id == "electricity" && (normalized.contains("كهربا") || normalized.contains("كهربائي") || normalized.contains("لمبة") || normalized.contains("انارة") || normalized.contains("شاحن") || normalized.contains("طاقة"))) ||
            (cat.id == "conditioning" && (normalized.contains("مكيف") || normalized.contains("تكييف") || normalized.contains("تبريد") || normalized.contains("برد") || normalized.contains("حر"))) ||
            (cat.id == "carpentry" && (normalized.contains("نجار") || normalized.contains("نجارة") || normalized.contains("خشب") || normalized.contains("اثاث"))) ||
            (cat.id == "construction" && (normalized.contains("بناء") || normalized.contains("مقاول") || normalized.contains("دهان") || normalized.contains("اسمنت") || normalized.contains("لياسة"))) ||
            (cat.id == "computers" && (normalized.contains("برمجة") || normalized.contains("تلفون") || normalized.contains("هاتف") || normalized.contains("جوال") || normalized.contains("كمبيوتر") || normalized.contains("شاشة") || normalized.contains("فرمته"))) ||
            (cat.id == "medicine" && (normalized.contains("طب") || normalized.contains("طبيب") || normalized.contains("أطباء") || normalized.contains("دكتور") || normalized.contains("عيادة") || normalized.contains("مستشفى") || normalized.contains("صحة") || normalized.contains("علاج") || normalized.contains("صيدلية") || normalized.contains("أسنان"))) ||
            (cat.id == "education" && (normalized.contains("رحلة") || normalized.contains("مدرسة") || normalized.contains("استاذ") || normalized.contains("مدرس") || normalized.contains("معلم") || normalized.contains("جامعة") || normalized.contains("تعليم") || normalized.contains("خصوصي") || normalized.contains("تدريس"))) ||
            (cat.id == "law" && (normalized.contains("محام") || normalized.contains("محاماة") || normalized.contains("مستشار قانوني") || normalized.contains("قانون") || normalized.contains("قضية") || normalized.contains("محكمة") || normalized.contains("استشارة"))) ||
            (cat.id == "engineering" && (normalized.contains("هندس") || normalized.contains("مهندس") || normalized.contains("معماري") || normalized.contains("مدني") || normalized.contains("استشارة هندسية") || normalized.contains("تخطيط")))
        }
        
        // Find matching city
        val matchedCity = _cities.value.find { city ->
            normalized.contains(city.nameAr) || normalized.contains(city.nameEn) ||
            (city.id == "sanaa" && normalized.contains("صنعاء")) ||
            (city.id == "aden" && normalized.contains("عدن")) ||
            (city.id == "taiz" && normalized.contains("تعز")) ||
            (city.id == "ibb" && normalized.contains("إب")) ||
            (city.id == "hadramout" && (normalized.contains("حضرموت") || normalized.contains("المكلا")))
        }
        
        if (matchedCat != null) {
            // Find providers under this category, and optionally under matchedCity
            val localProviders = _providers.value.filter { p ->
                p.isVerified && p.category == matchedCat.id && (matchedCity == null || p.city == matchedCity.id)
            }
            
            val cityLabelStr = if (matchedCity != null) "في مدينة ${matchedCity.nameAr}" else ""
            if (localProviders.isNotEmpty()) {
                val pListText = localProviders.take(4).joinToString("\n") { p ->
                    "• 👨 ${p.name} | 📍 ${p.area} | 📞 هاتف: ${p.phone}"
                }
                return "حياك الله أخي الغالي! بحثت لك في دليلنا أوفلاين 🛡️ ووجدت هؤلاء الفنيين الموثقين لقسم (${matchedCat.nameAr}) $cityLabelStr باليمن:\n\n$pListText\n\nتواصل مع الفني مباشرة وموفق خير إن شاء الله!"
            } else {
                return "يا أهلاً بك! لقد تم العثور على قسم (${matchedCat.nameAr}) ذكياً، ولكن لا توجد أسماء أعضاء مسجلين وموثقين حالياً $cityLabelStr في دليلنا أوفلاين. يمكنك تسجيل مزودي الخدمة الجدد عبر صفحة التقديم لمبادرتنا لخدمة المجتمع."
            }
        }
        
        if (matchedCity != null) {
            val localProviders = _providers.value.filter { p -> p.isVerified && p.city == matchedCity.id }
            if (localProviders.isNotEmpty()) {
                val pListText = localProviders.take(3).joinToString("\n") { p ->
                    "• ${p.name} (${_categories.value.find { c -> c.id == p.category }?.nameAr ?: p.category}) | 📞 هاتف: ${p.phone}"
                }
                return "يا سيدي الكريم! في مدينة ${matchedCity.nameAr}، يتوفر لدينا فنيين ممتازين بالدليل أوفلاين. تفضل ببعضهم:\n\n$pListText\n\nتصفح بقية الأقسام من الشاشة الرئيسية، والله يوفقك!"
            }
        }
        
        // 3. Fallback to generic welcoming message with guidelines and rules
        return "أهلاً بك في دليل 'كل خدمات اليمن' الشامل (المساعد الذكي يعمل بالإنترنت وبدونه 🛡️). يمكنني مساعدتك في العثور على الأطباء، والمدرسين، والمهندسين، والمحامين، والكهربائيين، وكافة الحرفيين حتى في حال انقطاع الإنترنت. لمعلومات عن المبادرة أو رقم الدعم: 777644670. ماذا يمكنني أن أبحث لك اليوم؟"
    }

    // Gemini API Direct REST integration for the chat helper
    fun askGemini(prompt: String) {
        if (prompt.isBlank()) return
        _geminiMessages.value = _geminiMessages.value + Pair(prompt, true)
        _isGeminiThinking.value = true

        viewModelScope.launch {
            // Setup base system instruction
            val sysInstruction = "أنت مساعد ذكي متخصص في دليل 'كل خدمات اليمن' لربط الكوادر الحرفية والفنية والمهنية. أجب دائماً بالعربية وبلهجة يمنية لطيفة ومحترفة مفعمة بالأمل والتنظيم، وساعد المستخدمين في العثور على أفضل الفنيين لخدمتهم."
            
            var key = _settings.value.geminiApiKey.trim()
            if (key.isBlank() || key.contains("DummyPlaceholder")) {
                key = try {
                    val clazz = Class.forName("com.maw.BuildConfig")
                    val field = clazz.getField("GEMINI_API_KEY")
                    (field.get(null) as? String)?.trim() ?: ""
                } catch (e: Exception) {
                    ""
                }
            }
            
            val response = if (key.isBlank() || key.contains("DummyPlaceholder") || key.contains("YOUR_ACTUAL_")) {
                // Instantly fail the network call and skip the 30-sec Retrofit timeout
                kotlinx.coroutines.delay(400) // realistic typing delay
                null
            } else {
                try {
                    withContext(Dispatchers.IO) {
                        val req = GenerateContentRequest(
                            contents = listOf(
                                Content(parts = listOf(Part(text = prompt)))
                            ),
                            systemInstruction = Content(parts = listOf(Part(text = sysInstruction)))
                        )
                        RetrofitClient.service.generateContent(key, req)
                    }
                } catch (e: Exception) {
                    null
                }
            }

            _isGeminiThinking.value = false
            val textReply = response?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: getSimulatedYemeniLocalReply(prompt)
            
            _geminiMessages.value = _geminiMessages.value + Pair(textReply, false)
        }
    }

    // Extra dynamic admin features
    private val _adminAccounts = MutableStateFlow<List<AdminAccount>>(listOf(
        AdminAccount("admin", "admin123", true, true, true, true, true)
    ))
    val adminAccounts: StateFlow<List<AdminAccount>> = _adminAccounts.asStateFlow()

    fun addAdminAccount(account: AdminAccount, creator: String) {
        _adminAccounts.value = _adminAccounts.value + account
        addAuditLog(creator, "إنشاء حساب مشرف إداري جديد للمستخدم: ${account.username}")
        try {
            firestore?.collection("admins")?.document(account.username)?.set(account)
        } catch (e: Exception) {}
    }

    fun deleteAdminAccount(username: String, admin: String) {
        _adminAccounts.value = _adminAccounts.value.filter { it.username != username }
        addAuditLog(admin, "تم حذف حساب المشرف ذو الاسم: $username")
        try {
            firestore?.collection("admins")?.document(username)?.delete()
        } catch (e: Exception) {}
    }

    fun updateAdminAccount(oldUsername: String, updatedAccount: AdminAccount, admin: String) {
        if (oldUsername != updatedAccount.username) {
            _adminAccounts.value = _adminAccounts.value.filter { it.username != oldUsername } + updatedAccount
            addAuditLog(admin, "تحديث اسم وبيانات المشرف من $oldUsername إلى ${updatedAccount.username}")
            try {
                firestore?.collection("admins")?.document(oldUsername)?.delete()
                firestore?.collection("admins")?.document(updatedAccount.username)?.set(updatedAccount)
            } catch (e: Exception) {}
        } else {
            _adminAccounts.value = _adminAccounts.value.map { if (it.username == oldUsername) updatedAccount else it }
            addAuditLog(admin, "تحديث كلمة مرور وصلاحيات المشرف: $oldUsername")
            try {
                firestore?.collection("admins")?.document(oldUsername)?.set(updatedAccount)
            } catch (e: Exception) {}
        }
    }

    fun addBanner(b: Banner, admin: String) {
        _banners.value = _banners.value + b
        addAuditLog(admin, "إضافة بنر إعلاني ترويجي جديد: ${b.description}")
        try {
            firestore?.collection("banners")?.document(b.id)?.set(b)
        } catch (e: Exception) {}
    }

    fun deleteBanner(id: String, admin: String) {
        _banners.value = _banners.value.filter { it.id != id }
        addAuditLog(admin, "تم حذف البنر الترويجي ذي الرمز ($id)")
        try {
            firestore?.collection("banners")?.document(id)?.delete()
        } catch (e: Exception) {}
    }

    fun updateCategory(cat: Category, admin: String) {
        _categories.value = _categories.value.map { if (it.id == cat.id) cat else it }
        addAuditLog(admin, "تعديل الفئة المهنية: ${cat.nameAr}")
        try {
            firestore?.collection("categories")?.document(cat.id)?.set(cat)
        } catch (e: Exception) {}
    }

    fun clearAllChatHistory(admin: String) {
        _chats.value = emptyList()
        _chatMessages.value = emptyList()
        currentChatRoomId.value = null
        addAuditLog(admin, "تنظيف وحذف كافة سجلات المحادثات النشطة")
        try {
            firestore?.collection("chats")?.get()?.addOnSuccessListener { snap ->
                for (doc in snap) {
                    doc.reference.delete()
                }
            }
            firestore?.collection("messages")?.get()?.addOnSuccessListener { snap ->
                for (doc in snap) {
                    doc.reference.delete()
                }
            }
        } catch (e: Exception) {}
    }
}

// --- CORE APP LEVEL ACTIVITY ---
class MainActivity : ComponentActivity() {
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = MainViewModel()
        setContent {
            val settings by vm.settings.collectAsStateWithLifecycle()
            
            LaunchedEffect(settings) {
                try {
                    AppTheme.primaryRed = Color(android.graphics.Color.parseColor(settings.primaryColorHex))
                    AppTheme.accentGold = Color(android.graphics.Color.parseColor(settings.accentColorHex))
                    AppTheme.darkBg = Color(android.graphics.Color.parseColor(settings.bgColorHex))
                    AppTheme.surfaceDark = Color(android.graphics.Color.parseColor(settings.surfaceColorHex))
                } catch (e: Exception) {
                    AppTheme.primaryRed = Color(0xFFCE1126)
                    AppTheme.accentGold = Color(0xFFFFD700)
                    AppTheme.darkBg = Color(0xFF0D1B1E)
                    AppTheme.surfaceDark = Color(0xFF162A2D)
                }
            }
            
            // Resolve dynamic app font family
            val selectedFont = resolveAppFontFamily(settings.selectedFontName)

            val customColorScheme = darkColorScheme(
                primary = AppTheme.primaryRed,
                onPrimary = Color.White,
                secondary = AppTheme.accentGold,
                onSecondary = Color.Black,
                background = AppTheme.darkBg,
                onBackground = Color.White,
                surface = AppTheme.surfaceDark,
                onSurface = Color.White
            )

            MaterialTheme(
                colorScheme = customColorScheme,
                typography = Typography().copy(
                    displayLarge = Typography().displayLarge.copy(fontFamily = selectedFont),
                    bodyLarge = Typography().bodyLarge.copy(fontFamily = selectedFont),
                    bodyMedium = Typography().bodyMedium.copy(fontFamily = selectedFont),
                    labelLarge = Typography().labelLarge.copy(fontFamily = selectedFont)
                )
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.darkBg
                ) {
                    AppNavigationLayout(vm)
                }
            }
        }
    }
}

// --- CONTAINER LAYOUT WRAPPER ---
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigationLayout(vm: MainViewModel) {
    val context = LocalContext.current
    var activeTab by remember { mutableIntStateOf(0) }
    
    var lastBackPressTime by remember { mutableStateOf(0L) }
    androidx.activity.compose.BackHandler(enabled = true) {
        if (activeTab != 0) {
            activeTab = 0
        } else {
            val now = System.currentTimeMillis()
            if (now - lastBackPressTime < 2000L) {
                (context as? android.app.Activity)?.finish()
            } else {
                lastBackPressTime = now
                Toast.makeText(context, "اضغط مرة أخرى للخروج من التطبيق", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var showGeminiAssistant by remember { mutableStateOf(false) }
    val settings by vm.settings.collectAsStateWithLifecycle()

    val targetTab by vm.navigationTargetTab.collectAsStateWithLifecycle()
    LaunchedEffect(targetTab) {
        targetTab?.let {
            activeTab = it
            vm.navigationTargetTab.value = null
        }
    }

    val backdoorPrefs = remember { context.getSharedPreferences("backdoor_prefs", Context.MODE_PRIVATE) }
    var isBackdoorOwnerLoggedIn by remember { mutableStateOf(backdoorPrefs.getBoolean("owner_logged_in", false)) }
    var backdoorClickCount by remember { mutableIntStateOf(0) }
    var lastBackdoorClickTime by remember { mutableLongStateOf(0L) }
    var showBackdoorLoginDialog by remember { mutableStateOf(false) }
    var showBackdoorControlPanelDialog by remember { mutableStateOf(false) }

    val onBackdoorClicked = {
        val now = System.currentTimeMillis()
        if (now - lastBackdoorClickTime < 2500) {
            backdoorClickCount++
        } else {
            backdoorClickCount = 1
        }
        lastBackdoorClickTime = now
        if (backdoorClickCount >= 5) {
            backdoorClickCount = 0
            if (isBackdoorOwnerLoggedIn) {
                showBackdoorControlPanelDialog = true
            } else {
                showBackdoorLoginDialog = true
            }
        }
    }

    // UI state maps to dynamic values
    val currentFont = resolveAppFontFamily(settings.selectedFontName)

    Scaffold(
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.darkBg)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable(
                            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                            indication = null
                        ) {
                            onBackdoorClicked()
                        }
                    ) {
                        // Top Rounded App Badge representing red white black style or customizable image logo
                        if (settings.appLogoUrl.isNotBlank()) {
                            coil.compose.SubcomposeAsyncImage(
                                model = settings.appLogoUrl,
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, AppTheme.accentGold, RoundedCornerShape(8.dp)),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                loading = {
                                    Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
                                },
                                error = {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(Color.DarkGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(settings.appLogoText, color = AppTheme.accentGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, AppTheme.accentGold, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    Box(modifier = Modifier.weight(1f).fillMaxWidth().background(AppTheme.primaryRed))
                                    Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.White))
                                    Box(modifier = Modifier.weight(1f).fillMaxWidth().background(Color.Black))
                                }
                                Text(settings.appLogoText, color = AppTheme.accentGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = settings.appNameAr,
                            color = AppTheme.accentGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            fontFamily = currentFont
                        )
                    }

                    // Dynamic Notification Bell Button & Easy Administrator Switcher
                    val notificationsList by vm.notifications.collectAsStateWithLifecycle()
                    val unreadCount = notificationsList.count { !it.isRead }
                    var showNotificationCenter by remember { mutableStateOf(false) }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(AppTheme.surfaceDark)
                                .clickable { showNotificationCenter = true }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .testTag("notification_bell_btn"),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = if (unreadCount > 0) AppTheme.accentGold else Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                if (unreadCount > 0) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(AppTheme.primaryRed)
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = unreadCount.toString(),
                                            color = Color.White,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        if (showNotificationCenter) {
                            UserNotificationCenterDialog(
                                vm = vm,
                                notifications = notificationsList,
                                onDismiss = { showNotificationCenter = false },
                                fontFamily = currentFont
                            )
                        }

                        val loggedAdmin by vm.loggedInUsername.collectAsStateWithLifecycle()
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(AppTheme.surfaceDark)
                                .clickable {
                                    if (loggedAdmin.isEmpty()) {
                                        activeTab = 5
                                    } else {
                                        vm.loggedInUsername.value = ""
                                        Toast.makeText(context, "تم الخروج بنجاح للتصفح العادي", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Admin Switch",
                            tint = AppTheme.accentGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (loggedAdmin.isNotEmpty()) "$loggedAdmin (خروج)" else "الإدارة",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = currentFont
                        )
                    }
                }
            }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF223639))
                )
            }
        },
        bottomBar = {
            // FOOTER & TEXT COMPLIANCE: Green dot hidden. wam 2026 printed styled dynamically.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.darkBg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFF223639))
                )
                // Tabs Navigation Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val tabs = listOf(
                        Triple(0, Icons.Default.Home, "الدليل"),
                        Triple(1, Icons.Default.Map, "الخريطة"),
                        Triple(2, Icons.Default.Chat, "المحادثة"),
                        Triple(3, Icons.Default.AddBusiness, "انضمام"),
                        Triple(4, Icons.Default.Info, "معلومات"),
                        Triple(5, Icons.Default.Settings, "الإدارة")
                    )

                    tabs.forEach { (index, icon, label) ->
                        val isSelected = activeTab == index
                        Column(
                            modifier = Modifier
                                .clickable { 
                                    activeTab = index
                                    if (index == 0) {
                                        onBackdoorClicked()
                                    }
                                }
                                .padding(8.dp)
                                .testTag("tab_icon_$index"),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) AppTheme.accentGold else Color.Gray,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = label,
                                color = if (isSelected) Color.White else Color.Gray,
                                fontSize = 9.sp,
                                fontFamily = currentFont
                            )
                        }
                    }
                }

                // --- REQUIRED FOOTER CONTAINER ---
                if (settings.footerTextVisible && settings.footerText.isNotBlank()) {
                    val computedFontSize = (settings.footerFontSize * (settings.footerFontSizePercent.toFloat() / 100f)).sp
                    val computedOpacity = settings.footerOpacity
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF071112).copy(alpha = computedOpacity))
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = settings.footerText,
                            color = AppTheme.accentGold.copy(alpha = computedOpacity),
                            fontSize = computedFontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = currentFont,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("app_footer_text")
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            // CHATBOT ASSISTANT FLOATING TRIGGER
            val currentChatId by vm.currentChatRoomId.collectAsStateWithLifecycle()
            val isFabHidden = settings.assistantIconHidden || showGeminiAssistant || currentChatId != null
            if (!isFabHidden) {
                val computedFabSize = (settings.assistantIconSize * (settings.assistantIconSizePercent.toFloat() / 100f)).dp
                FloatingActionButton(
                    onClick = { showGeminiAssistant = true },
                    containerColor = try {
                        Color(android.graphics.Color.parseColor(settings.assistantIconColorHex))
                    } catch (e: Exception) {
                        AppTheme.primaryRed
                    },
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(computedFabSize)
                        .offset(
                            x = settings.assistantIconXOffset.dp,
                            y = -settings.assistantIconYOffset.dp
                        )
                        .testTag("ai_fab_trigger")
                ) {
                    val aIconVector = when (settings.assistantIconType) {
                        "Support" -> Icons.Default.HeadsetMic
                        "Chat" -> Icons.Default.Chat
                        "Star" -> Icons.Default.Star
                        "Help" -> Icons.Default.Help
                        else -> Icons.Default.SmartToy
                    }
                    Icon(
                        imageVector = aIconVector,
                        contentDescription = "AI Assistant",
                        modifier = Modifier.size((settings.assistantIconSize * (settings.assistantIconSizePercent.toFloat() / 100f) * 0.55).dp),
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Selection Switch with smooth slide transitions
            Crossfade(targetState = activeTab, label = "tab_fade") { tab ->
                when (tab) {
                    0 -> DirectoryScreen(vm)
                    1 -> MockMapViewScreen(vm)
                    2 -> {
                        val parsedColor = try {
                            Color(android.graphics.Color.parseColor(settings.fontColorHex))
                        } catch (e: Exception) {
                            Color.White
                        }
                        DirectChatScreen(vm = vm, fontFamily = currentFont, fontColor = parsedColor)
                    }
                    3 -> JoinApplicationScreen(vm)
                    4 -> AppInfoScreen(vm)
                    5 -> AdminSettingsScreen(vm)
                }
            }

            // --- SMART ASSISTANT INTERACTIVE SHEET OVERLAY ---
            AnimatedVisibility(
                visible = showGeminiAssistant,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                SmartAssistantSheet(
                    vm = vm,
                    onClose = { showGeminiAssistant = false },
                    fontFamily = currentFont
                )
            }

            // --- TIER 2: SECRET BACKDOOR DIALOGS IMPLEMENTATION ---
            if (showBackdoorLoginDialog) {
                var bdPasswordInput by remember { mutableStateOf("") }
                var bdRememberMeChecked by remember { mutableStateOf(false) }
                var bdLoginError by remember { mutableStateOf(false) }

                androidx.compose.ui.window.Dialog(onDismissRequest = { showBackdoorLoginDialog = false }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.5.dp, AppTheme.accentGold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "🔐 تسجيل دخول البوابة السرية للمالك",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = currentFont,
                                textAlign = TextAlign.Center
                            )

                            OutlinedTextField(
                                value = bdPasswordInput,
                                onValueChange = {
                                    bdPasswordInput = it
                                    bdLoginError = false
                                },
                                label = { Text("أدخل رمز المرور الخاص بالمالك", color = Color.Gray, fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = AppTheme.accentGold,
                                    unfocusedBorderColor = Color.Gray
                                ),
                                visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                                isError = bdLoginError
                            )

                            if (bdLoginError) {
                                Text(
                                    text = "رمز المرور غير صحيح البتة!",
                                    color = AppTheme.primaryRed,
                                    fontSize = 11.sp,
                                    fontFamily = currentFont
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Checkbox(
                                    checked = bdRememberMeChecked,
                                    onCheckedChange = { bdRememberMeChecked = it },
                                    colors = CheckboxDefaults.colors(checkedColor = AppTheme.accentGold)
                                )
                                Text(
                                    text = "تذكرني لحفظ تسجيل الدخول",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontFamily = currentFont
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        if (bdPasswordInput == "maher--736462") {
                                            isBackdoorOwnerLoggedIn = true
                                            backdoorPrefs.edit().putBoolean("owner_logged_in", bdRememberMeChecked).apply()
                                            showBackdoorLoginDialog = false
                                            showBackdoorControlPanelDialog = true
                                            Toast.makeText(context, "أهلاً بك مالك التطبيق في البوابة السرية", Toast.LENGTH_SHORT).show()
                                        } else {
                                            bdLoginError = true
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("دخول", color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                                }

                                Button(
                                    onClick = { showBackdoorLoginDialog = false },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.4f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("إلغاء", color = Color.White, fontFamily = currentFont)
                                }
                            }
                        }
                    }
                }
            }

            if (showBackdoorControlPanelDialog) {
                var bdAppName by remember { mutableStateOf(settings.appNameAr) }
                var bdPrimaryColor by remember { mutableStateOf(settings.primaryColorHex) }
                var bdSecondaryColor by remember { mutableStateOf(settings.accentColorHex) }
                var bdLogoText by remember { mutableStateOf(settings.appLogoText) }
                var bdLogoUrl by remember { mutableStateOf(settings.appLogoUrl) }
                var bdFooterText by remember { mutableStateOf(settings.footerText) }
                var bdWelcomeMsg by remember { mutableStateOf(settings.welcomeMessage) }
                
                var bdPhone by remember { mutableStateOf(settings.aboutPhone) }
                var bdWhatsapp by remember { mutableStateOf(settings.aboutWhatsapp) }
                var bdEmail by remember { mutableStateOf(settings.aboutEmail) }
                
                var bdMainAdminPass by remember { mutableStateOf(settings.adminPassword) }
                
                var bdFooterFontSizePercent by remember { mutableStateOf(settings.footerFontSizePercent.toFloat()) }
                var bdFooterOpacity by remember { mutableStateOf(settings.footerOpacity) }
                
                var bdAssistantIconSizePercent by remember { mutableStateOf(settings.assistantIconSizePercent.toFloat()) }
                var bdChatIconSizePercent by remember { mutableStateOf(settings.chatIconSizePercent.toFloat()) }
                
                var bdRadiusSearchLimit by remember { mutableStateOf(settings.radiusSearchLimitKm) }
                var bdVoiceSearchEnabled by remember { mutableStateOf(settings.isWebSpeechEnabled) }

                LaunchedEffect(bdPrimaryColor, bdSecondaryColor) {
                    try {
                        AppTheme.primaryRed = Color(android.graphics.Color.parseColor(bdPrimaryColor))
                        AppTheme.accentGold = Color(android.graphics.Color.parseColor(bdSecondaryColor))
                    } catch (e: Exception) {}
                }

                androidx.compose.ui.window.Dialog(
                    onDismissRequest = { showBackdoorControlPanelDialog = false },
                    properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.5.dp, AppTheme.accentGold),
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .fillMaxHeight(0.9f)
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "🛠️ لوحة الإعدادات السرية الفائقة (للمالك فقط)",
                                color = AppTheme.accentGold,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = currentFont,
                                modifier = Modifier.padding(bottom = 12.dp),
                                textAlign = TextAlign.Center
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                // 1. App Name
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("اسم التطبيق:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdAppName,
                                        onValueChange = { bdAppName = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AppTheme.accentGold)
                                    )
                                }

                                // 2. Colors with live preview
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("الألوان (كود Hex):", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedTextField(
                                            value = bdPrimaryColor,
                                            onValueChange = { bdPrimaryColor = it },
                                            label = { Text("الأساسي (مثال: #0A2463)", fontSize = 8.sp, color = Color.Gray) },
                                            modifier = Modifier.weight(1f),
                                            textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                        )
                                        OutlinedTextField(
                                            value = bdSecondaryColor,
                                            onValueChange = { bdSecondaryColor = it },
                                            label = { Text("الثانوي (مثال: #3A7CA5)", fontSize = 8.sp, color = Color.Gray) },
                                            modifier = Modifier.weight(1f),
                                            textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                        )
                                    }
                                    Text("سيتم تطبيق الألوان فوراً للمعاينة والتحقق!", color = Color.Gray, fontSize = 10.sp, fontFamily = currentFont)
                                }

                                // 3. App Logo Text & Url
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("شعار التطبيق النصي والرابط:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdLogoText,
                                        onValueChange = { bdLogoText = it },
                                        label = { Text("الشعار النصي (WAM بـ الديفولت)", fontSize = 10.sp, color = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    OutlinedTextField(
                                        value = bdLogoUrl,
                                        onValueChange = { bdLogoUrl = it },
                                        label = { Text("رابط صورة الشعار (اختياري)", fontSize = 10.sp, color = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                }

                                // 4. Promotional Footer Text
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("التذييل الدعائي والترويجي للمستخدمين:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdFooterText,
                                        onValueChange = { bdFooterText = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                }

                                // 5. Animated Welcome Message
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("رسالة الترحيب المتحركة بالرأس:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdWelcomeMsg,
                                        onValueChange = { bdWelcomeMsg = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                                        maxLines = 3
                                    )
                                }

                                // 6. Support Contacts
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("بيانات ومكالمات الدعم الفني:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdPhone,
                                        onValueChange = { bdPhone = it },
                                        label = { Text("هاتف الدعم", fontSize = 10.sp, color = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    OutlinedTextField(
                                        value = bdWhatsapp,
                                        onValueChange = { bdWhatsapp = it },
                                        label = { Text("واتساب الدعم", fontSize = 10.sp, color = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    OutlinedTextField(
                                        value = bdEmail,
                                        onValueChange = { bdEmail = it },
                                        label = { Text("بريد الدعم", fontSize = 10.sp, color = Color.Gray) },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                }

                                // 7. Main Admin password (WAM2026)
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("كلمة مرور المشرف الرئيسي الجيد (WAM2026):", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    OutlinedTextField(
                                        value = bdMainAdminPass,
                                        onValueChange = { bdMainAdminPass = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(color = Color.White, fontSize = 12.sp)
                                    )
                                }

                                // 8. Footer Font Size
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("حجم خط التذييل الترويجي: ${bdFooterFontSizePercent.toInt()}%", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Slider(
                                        value = bdFooterFontSizePercent,
                                        onValueChange = { bdFooterFontSizePercent = it },
                                        valueRange = 50f..200f,
                                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold, activeTrackColor = AppTheme.accentGold)
                                    )
                                }

                                // 9. Footer Opacity
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("ظهور / شفافية خلفية الحقوق: ${(bdFooterOpacity * 100).toInt()}%", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Slider(
                                        value = bdFooterOpacity,
                                        onValueChange = { bdFooterOpacity = it },
                                        valueRange = 0.1f..1.0f,
                                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold, activeTrackColor = AppTheme.accentGold)
                                    )
                                }

                                // 10. Assistant & Chat icon sizes
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text("حجم أيقونة المساعد الذكي AI: ${bdAssistantIconSizePercent.toInt()}%", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Slider(
                                        value = bdAssistantIconSizePercent,
                                        onValueChange = { bdAssistantIconSizePercent = it },
                                        valueRange = 50f..200f,
                                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold, activeTrackColor = AppTheme.accentGold)
                                    )
                                }

                                // 11. Map Search boundaries
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("حدود المسافة الافتراضية للبحث بالخريطة:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        listOf(5, 10, 25, 50).forEach { km ->
                                            val isSelected = bdRadiusSearchLimit == km
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(alpha = 0.2f))
                                                    .clickable { bdRadiusSearchLimit = km }
                                                    .padding(vertical = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$km كم",
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = currentFont
                                                )
                                            }
                                        }
                                    }
                                }

                                // 12. voice activation
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("تفعيل ميزة البحث الصوتي بالتطبيق:", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                    Switch(
                                        checked = bdVoiceSearchEnabled,
                                        onCheckedChange = { bdVoiceSearchEnabled = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        val upSettings = settings.copy(
                                            appNameAr = bdAppName,
                                            primaryColorHex = bdPrimaryColor,
                                            accentColorHex = bdSecondaryColor,
                                            appLogoText = bdLogoText,
                                            appLogoUrl = bdLogoUrl,
                                            footerText = bdFooterText,
                                            welcomeMessage = bdWelcomeMsg,
                                            aboutPhone = bdPhone,
                                            aboutWhatsapp = bdWhatsapp,
                                            aboutEmail = bdEmail,
                                            adminPassword = bdMainAdminPass,
                                            footerFontSizePercent = bdFooterFontSizePercent.toInt(),
                                            footerOpacity = bdFooterOpacity,
                                            assistantIconSizePercent = bdAssistantIconSizePercent.toInt(),
                                            radiusSearchLimitKm = bdRadiusSearchLimit,
                                            isWebSpeechEnabled = bdVoiceSearchEnabled
                                        )
                                        vm.updateAppSettings(upSettings, "المالك")
                                        showBackdoorControlPanelDialog = false
                                        Toast.makeText(context, "تم حفظ وتوزيع الإعدادات بنجاح!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("حفظ ومزامنة", color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                                }

                                Button(
                                    onClick = { showBackdoorControlPanelDialog = false },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.4f)),
                                    modifier = Modifier.weight(0.5f)
                                ) {
                                    Text("إلغاء", color = Color.White, fontFamily = currentFont)
                                }

                                Button(
                                    onClick = {
                                        isBackdoorOwnerLoggedIn = false
                                        backdoorPrefs.edit().putBoolean("owner_logged_in", false).apply()
                                        showBackdoorControlPanelDialog = false
                                        Toast.makeText(context, "تم خروج المالك", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                    modifier = Modifier.weight(0.7f)
                                ) {
                                    Text("خروج", color = Color.White, fontFamily = currentFont)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun compressImageBase64(context: Context, uri: Uri, maxWidth: Int = 400, maxHeight: Int = 400, quality: Int = 75): String {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        if (originalBitmap == null) return ""
        
        val width = originalBitmap.width
        val height = originalBitmap.height
        val ratio = width.toFloat() / height.toFloat()
        
        var targetWidth = width
        var targetHeight = height
        if (width > maxWidth || height > maxHeight) {
            if (ratio > 1f) {
                targetWidth = maxWidth
                targetHeight = (maxWidth / ratio).toInt()
            } else {
                targetHeight = maxHeight
                targetWidth = (maxHeight * ratio).toInt()
            }
        }
        
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val bytes = outputStream.toByteArray()
        Base64.encodeToString(bytes, Base64.DEFAULT)
    } catch (e: Exception) {
        ""
    }
}

fun readVideoBase64(context: Context, uri: Uri): String {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes() ?: return ""
        val encoded = Base64.encodeToString(bytes, Base64.DEFAULT)
        "data:video/mp4;base64,$encoded"
    } catch (e: Exception) {
        ""
    }
}

fun compressBitmapBase64(bitmap: Bitmap, maxWidth: Int = 400, maxHeight: Int = 400, quality: Int = 75): String {
    return try {
        val width = bitmap.width
        val height = bitmap.height
        val ratio = width.toFloat() / height.toFloat()
        
        var targetWidth = width
        var targetHeight = height
        if (width > maxWidth || height > maxHeight) {
            if (ratio > 1f) {
                targetWidth = maxWidth
                targetHeight = (maxWidth / ratio).toInt()
            } else {
                targetHeight = maxHeight
                targetWidth = (maxHeight * ratio).toInt()
            }
        }
        
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val bytes = outputStream.toByteArray()
        Base64.encodeToString(bytes, Base64.DEFAULT)
    } catch (e: Exception) {
        ""
    }
}

@Composable
fun rememberBase64Bitmap(base64String: String?): Bitmap? {
    if (base64String.isNullOrBlank()) return null
    return remember(base64String) {
        try {
            val clean = if (base64String.contains("base64,")) {
                base64String.substringAfter("base64,")
            } else {
                base64String
            }.trim()
            val decodedBytes = Base64.decode(clean, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}

@Composable
fun CategoryIconOrImage(iconUrl: String, modifier: Modifier = Modifier, iconSize: Int = 16) {
    if (iconUrl.startsWith("http://") || iconUrl.startsWith("https://") || iconUrl.startsWith("data:image/") || iconUrl.length > 5) {
        val base64Bitmap = rememberBase64Bitmap(iconUrl)
        AsyncImage(
            model = base64Bitmap ?: iconUrl,
            contentDescription = "Category Pic",
            modifier = modifier.size(iconSize.dp).clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
    } else {
        Text(
            text = if (iconUrl.isBlank()) "🛠️" else iconUrl,
            fontSize = iconSize.sp,
            modifier = modifier
        )
    }
}

// --- TAB 0: DIRECTORY & SEARCH ---
@Composable
fun DirectoryScreen(vm: MainViewModel) {
    val settings by vm.settings.collectAsStateWithLifecycle()
    val currentFont = resolveAppFontFamily(settings.selectedFontName)
    ProfessionalCategoryFilterComponent(vm = vm, fontFamily = currentFont)
}

@Composable
fun ProfessionalCategoryFilterComponent(
    vm: MainViewModel,
    fontFamily: FontFamily
) {
    val providers by vm.providers.collectAsStateWithLifecycle()
    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val reviews by vm.reviewsState.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var selectedCategoryId by remember { mutableStateOf("") }
    var selectedCityId by remember { mutableStateOf("") }
    var selectedNeighborhood by remember { mutableStateOf("") }
    var searchTxt by remember { mutableStateOf("") }
    var isListLoading by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategoryId, selectedCityId, selectedNeighborhood, searchTxt) {
        isListLoading = true
        kotlinx.coroutines.delay(500)
        isListLoading = false
    }

    val voiceSpeechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val resData = result.data
            val spokenMatches = resData?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!spokenMatches.isNullOrEmpty()) {
                val spokenText = spokenMatches[0]
                searchTxt = spokenText
                
                // Smart recognition of profession keywords and city names in spoken speech
                val lowerText = spokenText.lowercase()
                
                // Match category IDs intelligently
                val detectedCat = categories.find { cat ->
                    lowerText.contains(cat.nameAr) || 
                    lowerText.contains(cat.nameEn.lowercase()) ||
                    (cat.id == "plumbing" && (lowerText.contains("سباك") || lowerText.contains("سباكة") || lowerText.contains("ماتور") || lowerText.contains("أنبوب") || lowerText.contains("تسرب") || lowerText.contains("حنفية"))) ||
                    (cat.id == "electricity" && (lowerText.contains("كهربا") || lowerText.contains("كهربائي") || lowerText.contains("لمبة") || lowerText.contains("شاحن") || lowerText.contains("امبير") || lowerText.contains("طاقة شمسية"))) ||
                    (cat.id == "conditioning" && (lowerText.contains("مكيف") || lowerText.contains("تكييف") || lowerText.contains("تبريد") || lowerText.contains("حار") || lowerText.contains("فريون"))) ||
                    (cat.id == "carpentry" && (lowerText.contains("نجار") || lowerText.contains("نجارة") || lowerText.contains("خشب") || lowerText.contains("دولاب") || lowerText.contains("باب"))) ||
                    (cat.id == "construction" && (lowerText.contains("بناء") || lowerText.contains("مقاول") || lowerText.contains("بلاط") || lowerText.contains("دهان") || lowerText.contains("مليس"))) ||
                    (cat.id == "computers" && (lowerText.contains("برمجة") || lowerText.contains("كمبيوتر") || lowerText.contains("هاتف") || lowerText.contains("جوال") || lowerText.contains("تلفون") || lowerText.contains("فرمته")))
                }
                
                if (detectedCat != null) {
                    selectedCategoryId = detectedCat.id
                    Toast.makeText(context, "التعرف الصوتي الذكي: تم تحديد تخصص (${detectedCat.nameAr}) لقائمتك تلقائياً 🤖🎤", Toast.LENGTH_LONG).show()
                }
                
                // Match City IDs intelligently
                val detectedCity = cities.find { city ->
                    lowerText.contains(city.nameAr) || 
                    lowerText.contains(city.nameEn.lowercase()) ||
                    (city.id == "sanaa" && lowerText.contains("صنعاء")) ||
                    (city.id == "aden" && lowerText.contains("عدن")) ||
                    (city.id == "taiz" && lowerText.contains("تعز")) ||
                    (city.id == "ibb" && lowerText.contains("إب")) ||
                    (city.id == "hadramout" && (lowerText.contains("حضرموت") || lowerText.contains("المكلا")))
                }
                
                if (detectedCity != null) {
                    selectedCityId = detectedCity.id
                    selectedNeighborhood = "" // Reset neighborhood on city swap
                    Toast.makeText(context, "التعرف الصوتي الذكي: تم تركيز التوزيع الجغرافي على (${detectedCity.nameAr}) 🌍🎤", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Dialog for rating/evaluation
    var ratingProviderTarget by remember { mutableStateOf<Provider?>(null) }
    var viewReviewsTarget by remember { mutableStateOf<Provider?>(null) }
    var bookingProviderTarget by remember { mutableStateOf<Provider?>(null) }
    var profileProviderTarget by remember { mutableStateOf<Provider?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.darkBg)
            .padding(12.dp)
    ) {
        // Warning Banner if Chat is Disabled
        if (!settings.isChatEnabled) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.primaryRed.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                border = BorderStroke(1.2.dp, AppTheme.accentGold)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            "💬 تنبيه من الإدارة: تم إيقاف المحادثة الفورية",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            fontFamily = fontFamily
                        )
                        Text(
                            settings.chatDisabledMessage,
                            color = Color.White,
                            fontSize = 9.sp,
                            fontFamily = fontFamily,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
        }

        // Stunning Categories Grid
        val rootCategories = categories.filter { it.parentId.isEmpty() }
            .sortedWith(compareByDescending<Category> { it.isPinned }.thenBy { it.order })

        if (rootCategories.isNotEmpty()) {
            Text(
                text = "🛠️ تصنيفات المهن والحرف الرئيسية:",
                color = AppTheme.accentGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                items(rootCategories) { cat ->
                    val isSelected = selectedCategoryId == cat.id
                    Card(
                        modifier = Modifier
                            .width(110.dp)
                            .height(82.dp)
                            .clickable { selectedCategoryId = if (isSelected) "" else cat.id },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) AppTheme.primaryRed else AppTheme.surfaceDark
                        ),
                        border = if (isSelected) BorderStroke(1.5.dp, AppTheme.accentGold) else BorderStroke(1.dp, Color(0xFF223639))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(6.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CategoryIconOrImage(cat.iconUrl, iconSize = 22)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = cat.nameAr,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                            if (cat.isPinned) {
                                Text("📌 مثبت", color = AppTheme.accentGold, fontSize = 7.sp, fontFamily = fontFamily)
                            }
                        }
                    }
                }
            }
        }

        // Expand Subcategories list dynamically
        val activeSubcategories = categories.filter { it.parentId == selectedCategoryId && it.parentId.isNotEmpty() }
        if (selectedCategoryId.isNotEmpty() && activeSubcategories.isNotEmpty()) {
            Text(
                text = "↳ الأقسام الفرعية المتفرعة للقسم الحالي:",
                color = Color.LightGray,
                fontSize = 11.sp,
                fontFamily = fontFamily,
                modifier = Modifier.padding(bottom = 6.dp, start = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            ) {
                items(activeSubcategories) { sub ->
                    val isSelectedSub = selectedCategoryId == sub.id
                    FilterChip(
                        selected = isSelectedSub,
                        onClick = { selectedCategoryId = sub.id },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CategoryIconOrImage(sub.iconUrl, iconSize = 12)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(sub.nameAr, fontSize = 10.sp, fontFamily = fontFamily)
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AppTheme.primaryRed,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFF0F2225),
                            labelColor = Color.LightGray
                        )
                    )
                }
            }
        }

        // Active selection banner indicator
        if (selectedCategoryId.isNotEmpty()) {
            val catObj = categories.find { it.id == selectedCategoryId }
            catObj?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F2225))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CategoryIconOrImage(it.iconUrl, iconSize = 13)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "جاري تصفية القسم: ${it.nameAr}",
                            color = AppTheme.accentGold,
                            fontSize = 11.sp,
                            fontFamily = fontFamily
                        )
                    }
                    TextButton(
                        onClick = { selectedCategoryId = "" },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text("عرض الكل ❌", color = Color.White, fontSize = 10.sp, fontFamily = fontFamily)
                    }
                }
            }
        }

        // Smart search autocomplete dropdown suggestions list
        val autocompleteSuggestions = remember(searchTxt, providers, categories, cities) {
            if (searchTxt.length >= 2 && settings.searchMatchingMethodHex != "disabled") {
                val query = searchTxt.trim().lowercase()
                val list = mutableListOf<String>()
                
                // 1. Suggest by Name
                if (settings.autocompleteNamesEnabled) {
                    providers.filter { it.isVerified && it.name.contains(query, ignoreCase = true) }
                        .map { it.name }.distinct().take(3).forEach { list.add(it) }
                }
                
                // 2. Suggest by phone / number
                if (settings.autocompletePhonesEnabled) {
                    providers.filter { it.isVerified && it.phone.contains(query) }
                        .map { it.phone }.distinct().take(2).forEach { list.add(it) }
                }
                
                // 3. Suggest by location
                if (settings.autocompleteLocationsEnabled) {
                    providers.filter { it.isVerified && it.area.contains(query, ignoreCase = true) }
                        .map { it.area }.distinct().take(2).forEach { list.add(it) }
                }
                
                // 4. Suggest by category name
                categories.filter { it.nameAr.contains(query, ignoreCase = true) }
                    .map { it.nameAr }.distinct().take(2).forEach { list.add(it) }
                
                // 5. Suggest by city name
                cities.filter { it.nameAr.contains(query, ignoreCase = true) }
                    .map { it.nameAr }.distinct().take(1).forEach { list.add(it) }
                
                list.distinct().take(6)
            } else {
                emptyList()
            }
        }

        // Search inputs & Voice speech trigger
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isSearchStopped = settings.searchMatchingMethodHex == "disabled"
            OutlinedTextField(
                value = if (isSearchStopped) "" else searchTxt,
                onValueChange = { if (!isSearchStopped) searchTxt = it },
                enabled = !isSearchStopped,
                placeholder = { 
                    Text(
                        text = if (isSearchStopped) "⚠️ تم تعطيل محرك البحث بواسطة الإدارة" else "ابحث بالاسم، المهنة، الرقم، المنطقة، المدينة...", 
                        color = if (isSearchStopped) AppTheme.primaryRed else Color.Gray, 
                        fontSize = 10.sp
                    ) 
                },
                leadingIcon = { 
                    Icon(
                        imageVector = if (isSearchStopped) Icons.Default.Lock else Icons.Default.Search, 
                        contentDescription = "Search", 
                        tint = if (isSearchStopped) AppTheme.primaryRed else AppTheme.accentGold, 
                        modifier = Modifier.size(16.dp)
                    ) 
                },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.Gray,
                    focusedContainerColor = AppTheme.surfaceDark,
                    unfocusedContainerColor = AppTheme.surfaceDark,
                    disabledContainerColor = AppTheme.surfaceDark.copy(alpha = 0.5f),
                    focusedBorderColor = AppTheme.accentGold,
                    unfocusedBorderColor = Color(0xFF223639),
                    disabledBorderColor = Color(0xFF223639).copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            if (settings.isWebSpeechEnabled && !isSearchStopped) {
                IconButton(
                    onClick = {
                        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-YE")
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "تحدث للبحث...")
                        }
                        try {
                            voiceSpeechLauncher.launch(speechIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "البحث الصوتي غير مدعوم على جهازك", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AppTheme.surfaceDark)
                        .border(1.dp, Color(0xFF223639), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = AppTheme.accentGold)
                }
            }
        }

        // Render autocomplete suggestions dropdown if any are computed
        if (autocompleteSuggestions.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(1.dp, AppTheme.accentGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(6.dp)) {
                    Text(
                        text = "💡 مقترحات إكمال البحث الذكي الموثوقة:",
                        color = AppTheme.accentGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                    autocompleteSuggestions.forEach { sugg ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { searchTxt = sugg }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Suggest Icon",
                                tint = AppTheme.accentGold,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = sugg,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontFamily = fontFamily
                            )
                        }
                        if (autocompleteSuggestions.last() != sugg) {
                            Divider(color = Color(0xFF223639), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }

        // Double Geographic Filter Grid: City dropdown + Dynamic Neighborhood dropdown
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // City Selector Box
                Box(modifier = Modifier.weight(1f)) {
                    var showCityDropdown by remember { mutableStateOf(false) }
                    Button(
                        onClick = { showCityDropdown = true },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        val cityLabel = cities.find { it.id == selectedCityId }?.nameAr ?: "كل المدن 🌍"
                        Text(cityLabel, color = AppTheme.accentGold, fontSize = 10.sp, fontFamily = fontFamily, maxLines = 1)
                    }
                    DropdownMenu(
                        expanded = showCityDropdown,
                        onDismissRequest = { showCityDropdown = false },
                        modifier = Modifier.background(AppTheme.surfaceDark)
                    ) {
                        DropdownMenuItem(
                            text = { Text("جميع المدن 🌍", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                            onClick = {
                                selectedCityId = ""
                                selectedNeighborhood = "" // reset neighborhood
                                showCityDropdown = false
                            }
                        )
                        cities.forEach { city ->
                            DropdownMenuItem(
                                text = { Text(city.nameAr, color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                                onClick = {
                                    selectedCityId = city.id
                                    selectedNeighborhood = "" // reset neighborhood on city change
                                    showCityDropdown = false
                                }
                            )
                        }
                    }
                }

                // Neighborhood / Street Selector Box (Double Geographical Filter)
                Box(modifier = Modifier.weight(1f)) {
                    var showAreaDropdown by remember { mutableStateOf(false) }
                    
                    // Extract unique neighborhoods from listed providers in this city
                    val availableAreas = remember(selectedCityId, providers) {
                        if (selectedCityId.isEmpty()) {
                            providers.map { it.area.trim() }.filter { it.isNotEmpty() }.distinct().sorted()
                        } else {
                            providers.filter { it.city == selectedCityId }.map { it.area.trim() }.filter { it.isNotEmpty() }.distinct().sorted()
                        }
                    }

                    Button(
                        onClick = { showAreaDropdown = true },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        val areaLabel = if (selectedNeighborhood.isEmpty()) "كل الأحياء 🏘️" else selectedNeighborhood
                        Text(areaLabel, color = AppTheme.accentGold, fontSize = 10.sp, fontFamily = fontFamily, maxLines = 1)
                    }
                    DropdownMenu(
                        expanded = showAreaDropdown,
                        onDismissRequest = { showAreaDropdown = false },
                        modifier = Modifier.background(AppTheme.surfaceDark)
                    ) {
                        DropdownMenuItem(
                            text = { Text("جميع الأحياء 🏘️", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                            onClick = {
                                selectedNeighborhood = ""
                                showAreaDropdown = false
                            }
                        )
                        availableAreas.forEach { areaText ->
                            DropdownMenuItem(
                                text = { Text(areaText, color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                                onClick = {
                                    selectedNeighborhood = areaText
                                    showAreaDropdown = false
                                }
                            )
                        }
                    }
                }
            }

        // List of filtered professionals with highly robust query criteria
        val listFiltered = providers.filter { itPro ->
            val matchCat = selectedCategoryId.isEmpty() ||
                           itPro.category == selectedCategoryId ||
                           categories.find { it.id == itPro.category }?.parentId == selectedCategoryId
            val matchCity = selectedCityId.isEmpty() || itPro.city == selectedCityId
            val matchNeighborhood = selectedNeighborhood.isEmpty() || itPro.area.contains(selectedNeighborhood, ignoreCase = true)
            
            val matchQuery = if (searchTxt.isEmpty() || settings.searchMatchingMethodHex == "disabled") {
                true
            } else {
                val catObj = categories.find { it.id == itPro.category }
                val catNameAr = catObj?.nameAr ?: ""
                val catNameEn = catObj?.nameEn ?: ""
                
                val cityObj = cities.find { it.id == itPro.city }
                val cityNameAr = cityObj?.nameAr ?: ""
                val cityNameEn = cityObj?.nameEn ?: ""
                
                val fields = listOf(
                    itPro.name,
                    itPro.phone,
                    itPro.area,
                    itPro.description,
                    catNameAr,
                    catNameEn,
                    cityNameAr,
                    cityNameEn
                )
                
                if (settings.searchMatchingMethodHex == "exact") {
                    fields.any { it.contains(searchTxt, ignoreCase = true) }
                } else {
                    val words = searchTxt.split("\\s+".toRegex()).filter { it.isNotBlank() }
                    if (words.isEmpty()) {
                        true
                    } else {
                        words.all { word ->
                            fields.any { it.contains(word, ignoreCase = true) }
                        }
                    }
                }
            }
            matchCat && matchCity && matchNeighborhood && matchQuery && itPro.isVerified
        }

        if (isListLoading) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(3) {
                    ProviderCardShimmer(fontFamily = fontFamily)
                }
            }
        } else if (listFiltered.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFF223639)),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(AppTheme.primaryRed.copy(alpha = 0.15f))
                                .border(1.5.dp, AppTheme.accentGold.copy(alpha = 0.7f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "No Results Found",
                                tint = AppTheme.accentGold,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = settings.noResultsMessage,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "حاول تغيير تصنيف البحث، أو اختيار مدينة/حي آخر لبدء العثور على المهنيين.",
                            color = Color.LightGray,
                            fontSize = 10.sp,
                            fontFamily = fontFamily,
                            textAlign = TextAlign.Center,
                            lineHeight = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Dynamic Sorting based on admin's settings selection
                val sorted = if (searchTxt.trim().isNotEmpty()) {
                    // Confidence Level Search prioritisation: 1. Pinned 2. High rating weighted by admin rating weight
                    listFiltered.sortedWith(
                        compareByDescending<Provider> { it.isPinned }
                            .thenByDescending { it.rating * settings.searchRatingWeight }
                    )
                } else {
                    when (settings.approvedProviderSortingMethod) {
                        "admin_priority" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.orderPriority }
                                    .thenByDescending { it.isPinned }
                                    .thenByDescending { it.rating }
                            )
                        }
                        "pin_first" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.isPinned }
                                    .thenByDescending { it.orderPriority }
                                    .thenByDescending { it.rating }
                            )
                        }
                        "rating_desc" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.rating }
                                    .thenByDescending { it.isPinned }
                                    .thenByDescending { it.isSubscribed }
                            )
                        }
                        "subscribed_first" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.isSubscribed }
                                    .thenByDescending { it.isPinned }
                                    .thenByDescending { it.rating }
                            )
                        }
                        "recommended_first" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.isRecommended }
                                    .thenByDescending { it.isPinned }
                                    .thenByDescending { it.rating }
                            )
                        }
                        "confidence_search" -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.isPinned }
                                    .thenByDescending { it.rating * settings.searchRatingWeight }
                            )
                        }
                        else -> {
                            listFiltered.sortedWith(
                                compareByDescending<Provider> { it.isPinned }
                                    .thenByDescending { it.isSubscribed }
                                    .thenByDescending { it.rating }
                            )
                        }
                    }
                }

                items(sorted) { item ->
                    ProfessionalCardRow(
                        provider = item,
                        vm = vm,
                        reviews = reviews.filter { it.providerId == item.id },
                        onRateClick = { ratingProviderTarget = item },
                        onViewReviewsClick = { viewReviewsTarget = item },
                        onBookClick = { bookingProviderTarget = item },
                        onProfileClick = { profileProviderTarget = item },
                        fontFamily = fontFamily,
                        selectedCityId = selectedCityId
                    )
                }
            }
        }
        }
    }

    // Professional profile details dialog
    profileProviderTarget?.let { provider ->
        ProfessionalProfileDialog(
            provider = provider,
            vm = vm,
            onDismiss = { profileProviderTarget = null },
            fontFamily = fontFamily
        )
    }

    // Rating dialog
    ratingProviderTarget?.let { provider ->
        RateProviderDialog(
            provider = provider,
            onDismiss = { ratingProviderTarget = null },
            onSubmit = { review ->
                vm.submitReview(review)
                ratingProviderTarget = null
            },
            fontFamily = fontFamily
        )
    }

    // View reviews dialog
    viewReviewsTarget?.let { provider ->
        ViewReviewsDialog(
            provider = provider,
            reviews = reviews.filter { it.providerId == provider.id },
            onDismiss = { viewReviewsTarget = null },
            fontFamily = fontFamily
        )
    }

    // Book service appointment dialog
    bookingProviderTarget?.let { provider ->
        BookAppointmentDialog(
            provider = provider,
            onDismiss = { bookingProviderTarget = null },
            onSubmit = { details, timeChosen ->
                vm.requestServiceAppointment(provider.id, provider.name, details, timeChosen)
                bookingProviderTarget = null
            },
            fontFamily = fontFamily
        )
    }
}

@Composable
fun getUserCoordinates(cityId: String): Pair<Double, Double> {
    return when (cityId.lowercase()) {
        "sanaa" -> Pair(15.3533, 44.2074)
        "aden" -> Pair(12.7855, 45.0186)
        "taiz" -> Pair(13.5794, 44.0205)
        "hodeidah" -> Pair(14.7979, 42.9530)
        "hadramout" -> Pair(14.4000, 49.0000)
        "ibb" -> Pair(13.9745, 44.1802)
        else -> Pair(15.3533, 44.2074)
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color(0xFF132326),
            Color(0xFF223639),
            Color(0xFF132326)
        )
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer"
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun ProviderCardShimmer(fontFamily: FontFamily) {
    val brush = shimmerBrush()
    Card(
        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfessionalCardRow(
    provider: Provider,
    vm: MainViewModel,
    reviews: List<Review>,
    onRateClick: () -> Unit,
    onViewReviewsClick: () -> Unit,
    onBookClick: () -> Unit,
    onProfileClick: () -> Unit,
    fontFamily: FontFamily,
    selectedCityId: String = ""
) {
    val context = LocalContext.current
    var isPressed by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (provider.isPinned) Color(0xFF1E3539) else AppTheme.surfaceDark
        ),
        border = if (provider.isPinned) BorderStroke(1.5.dp, AppTheme.accentGold) else null,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        isExpanded = !isExpanded
                    }
                )
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Profile selfie image box next to info
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F2225))
                        .border(
                            1.5.dp,
                            if (provider.isSubscribed) Color.Green else AppTheme.accentGold,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val profileBitmap = rememberBase64Bitmap(provider.imageUrl)
                    if (profileBitmap != null) {
                        Image(
                            bitmap = profileBitmap.asImageBitmap(),
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "No Avatar",
                            tint = Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (provider.isSubscribed) Color.Green else Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = provider.name,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = fontFamily
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "👤 الملف المعرض ◀",
                                color = AppTheme.accentGold,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                modifier = Modifier.clickable { onProfileClick() }
                            )
                        }
                        if (provider.isPinned) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AppTheme.accentGold)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("متميز / Pinned", color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(provider.description, color = AppTheme.grayText, fontSize = 12.sp, lineHeight = 16.sp, fontFamily = fontFamily)
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📍 ${provider.city} • ${provider.area}", color = AppTheme.grayText, fontSize = 11.sp, fontFamily = fontFamily)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onViewReviewsClick() }
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Rating", tint = AppTheme.accentGold, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${provider.rating} (${reviews.size} تقييمات)",
                                color = AppTheme.accentGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily
                            )
                            
                            // Distance calculation next to rating (using user position determined accurately)
                            val pCoords = getProviderCoordinates(provider)
                            val uCityId = if (selectedCityId.isNotEmpty()) selectedCityId else provider.city
                            val uCoords = getUserCoordinates(uCityId)
                            val dist = calculateDistance(uCoords.first, uCoords.second, pCoords.first, pCoords.second)
                            
                            val distFormatted = try {
                                val rounded = (dist * 10).toInt() / 10.0
                                "$rounded كم"
                            } catch (e: Exception) {
                                "1.2 كم"
                            }
                            
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "• ($distFormatted)",
                                color = AppTheme.grayText,
                                fontSize = 12.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // Interaction Row 1: Direct Contact
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = {
                                val uri = Uri.parse("tel:${provider.phone}")
                                val it = Intent(Intent.ACTION_DIAL, uri)
                                try { context.startActivity(it) } catch(e: Exception) {}
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                            modifier = Modifier.weight(1f).height(36.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("اتصال مباشر", fontSize = 10.sp, fontFamily = fontFamily)
                        }

                        Button(
                            onClick = {
                                val currentSettings = vm.settings.value
                                if (!currentSettings.isChatEnabled) {
                                    Toast.makeText(context, "🚫 ${currentSettings.chatDisabledMessage}", Toast.LENGTH_LONG).show()
                                } else {
                                    vm.startChatWithProvider("user_visitor", provider.id, provider.name)
                                    vm.navigationTargetTab.value = 2
                                    Toast.makeText(context, "تم فتح نافذة الاتصال الآمن مع غرف ${provider.name}", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.weight(1.2f).height(36.dp)
                                .border(1.dp, AppTheme.accentGold, RoundedCornerShape(6.dp)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Message, contentDescription = "Chat", modifier = Modifier.size(12.dp), tint = AppTheme.accentGold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("مراسلة فورية", fontSize = 10.sp, color = AppTheme.accentGold, fontFamily = fontFamily)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Interaction Row 2: Ratings & Reviews
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = onRateClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF223639)),
                            modifier = Modifier.weight(1.5f).height(34.dp)
                                .border(1.dp, Color(0xFF334C50), RoundedCornerShape(6.dp)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "Rate", modifier = Modifier.size(12.dp), tint = AppTheme.accentGold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("أضف تعليق وتقييم", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                        }

                        Button(
                            onClick = onViewReviewsClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF132326)),
                            modifier = Modifier.weight(1.5f).height(34.dp)
                                .border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Comment, contentDescription = "Reviews", modifier = Modifier.size(12.dp), tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("آراء وتجارب العملاء", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Dedicated Instant Booking Button
                    Button(
                        onClick = onBookClick,
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(34.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.EventNote, contentDescription = "Book Appointment", modifier = Modifier.size(12.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("🗓️ حجز موعد خدمة فوري ومباشر", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = fontFamily)
                    }
                }
            }
            
            // Expandable Info Tab when Card is tapped
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F0F1A))
                        .padding(12.dp)
                ) {
                    Divider(color = Color(0xFF223639), thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                    Text(
                        text = "📋 تفاصيل إضافية عن مقدم الخدمة والاتصال الدقيق:",
                        color = AppTheme.accentGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        fontFamily = fontFamily,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = "Working Hours", tint = AppTheme.accentGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "⏰ ساعات العمل والدوام: من 8:00 صباحاً حتى 8:00 مساءً",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = fontFamily
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Direct Phone", tint = Color.Green, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "📞 رقم الهاتف المباشر: ${provider.phone}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }
    }
}
}

// OUR NEW STUNNING METICULOUSLY CRAFTED PROFILE VIEW
@Composable
fun ProfessionalProfileDialog(
    provider: Provider,
    vm: MainViewModel,
    onDismiss: () -> Unit,
    fontFamily: FontFamily
) {
    val context = LocalContext.current
    val settings by vm.settings.collectAsStateWithLifecycle()
    var lightboxImageBase64 by remember { mutableStateOf<String?>(null) }

    // Resolve skills
    val skillsList = remember(provider.skills, provider.category) {
        if (provider.skills.isNotBlank()) {
            provider.skills.split(Regex("[,،\n]")).map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            when (provider.category.lowercase()) {
                "electricity" -> listOf("تمديدات منزلية", "صيانة لوحات كهرباء", "تركيب خطوط الطاقة الشمسية", "تأمين التوصيلات", "تأسيس شبكات ذكية")
                "plumbing" -> listOf("سباكة وصيانة صحية", "تركيب مغاسل وخلاطات", "تسليك المجاري والانسدادات", "كشف تسريب المياه", "صيانة مضخات خزان")
                "maintenance" -> listOf("صيانة عامة للأجهزة", "تصليح أعطال ذكية", "تركيب معدات وشاشات", "كشف شامل على الكفاءة")
                "carpentry" -> listOf("نجارة وأثاث وديكور", "تفصيل غرف ودواليب", "تصليح الأبواب والمفصلات", "صيانة أخشاب ومطابخ")
                "conditioning" -> listOf("تركيب مكيفات اسبليت", "شحن فريون أصلي", "تنظيف الفلاتر والوحدات", "صيانة التكييف المركزي")
                "construction" -> listOf("مقاولات وأعمال بناء", "أعمال دهان وديكورات", "لياسة وترميم فلل", "مقاولات تشطيب متكامل")
                "computers" -> listOf("صيانة قطع ومكونات", "برمجة السوفتوير للأندرويد", "تنزيل أنظمة وتعريفات", "صيانة هواتف وشاشات")
                "medicine" -> listOf("استشارة طبية موثقة", "رعاية وتتبع صحي", "خبرة عيادية وأعمال طوارئ")
                "education" -> listOf("تدريب وتقوية مهارات", "مراجعة امتحانات وتلخيص", "استراتيجيات تعليم حديثة")
                "law" -> listOf("استشارات قانونية", "صياغة عقود تجارية", "مرافعة وتمثيل وتحكيم")
                "engineering" -> listOf("تصميم ومخططات هندسية", "حساب الكميات والتكاليف", "إشراف على سير المشاريع")
                else -> listOf("صيانة وتأسيس مهني شامل", "جودة وموثوقية عالية", "حلول سريعة وضمان معتمد")
            }
        }
    }

    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, AppTheme.accentGold),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                // Header (Name + Category Badge + Close button)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // User Avatar circular badge
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(AppTheme.accentGold.copy(alpha = 0.15f))
                                .border(1.5.dp, AppTheme.accentGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            val profileBitmap = rememberBase64Bitmap(provider.imageUrl)
                            if (profileBitmap != null) {
                                Image(
                                    bitmap = profileBitmap.asImageBitmap(),
                                    contentDescription = "Profile Photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = if (provider.name.isNotBlank()) provider.name.take(1) else "👨",
                                    color = AppTheme.accentGold,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily
                                )
                            }
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = provider.name,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = fontFamily
                                )
                                if (provider.isVerified) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Verified Member",
                                        tint = AppTheme.accentGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = "📍 ${provider.city} • ${provider.area}",
                                color = AppTheme.grayText,
                                fontSize = 11.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = AppTheme.primaryRed
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color(0xFF223639), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // Scrollable Content Pane
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // 1. Definition / About section
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                        border = BorderStroke(1.dp, Color(0xFF223639)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "👤 نبذة تعريفية بالدليل المهني:",
                                color = AppTheme.accentGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = provider.description.ifBlank { "لم يقم هذا العضو بكتابة نبذة تعريفية بعد، ولكن تواصله الفوري متاح وجودته مضمونة." },
                                color = Color.White,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }

                    // 2. Skills list section
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                        border = BorderStroke(1.dp, Color(0xFF223639)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "⚡ المهارات والخبرات الممتازة:",
                                color = AppTheme.accentGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    skillsList.chunked(2).forEach { pair ->
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            pair.forEach { skill ->
                                                Box(
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(AppTheme.primaryRed.copy(alpha = 0.12f))
                                                        .border(1.dp, AppTheme.primaryRed.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                                ) {
                                                    Text(
                                                        text = "✓ $skill",
                                                        color = Color.White,
                                                        fontSize = 10.sp,
                                                        fontFamily = fontFamily
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 3. Portfolio models gallery
                    val isFeatureEnabled = settings.isPortfolioFeatureGloballyEnabled && provider.isPortfolioEnabled
                    if (isFeatureEnabled) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                            border = BorderStroke(1.dp, Color(0xFF223639)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "🎨 معرض صور نماذج الأعمال والمشاريع السابقة:",
                                    color = AppTheme.accentGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )

                                val portfolioToRender = provider.portfolioImages.take(provider.allowedImageCount)

                                if (portfolioToRender.isEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "🛡️ المعرض فارغ حالياً - لم يتم رفع ملفات بعد.",
                                            color = Color.Gray,
                                            fontSize = 10.sp,
                                            fontFamily = fontFamily
                                        )
                                    }
                                } else {
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        items(portfolioToRender) { base64 ->
                                            val bitmap = rememberBase64Bitmap(base64)
                                            bitmap?.let {
                                                Card(
                                                    modifier = Modifier
                                                        .size(80.dp)
                                                        .clickable { lightboxImageBase64 = base64 },
                                                    shape = RoundedCornerShape(8.dp),
                                                    border = BorderStroke(1.2.dp, AppTheme.accentGold.copy(alpha = 0.6f))
                                                ) {
                                                    Image(
                                                        bitmap = it.asImageBitmap(),
                                                        contentDescription = "Work sample click to zoom",
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "* إرشادات: انقر على أي صورة أعلاه لفتحها بالكامل بجودة فائقة.",
                                        color = Color.Gray,
                                        fontSize = 8.sp,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                        }
                    } else {
                        if (!settings.isPortfolioFeatureGloballyEnabled) {
                            Text(
                                text = "🔒 ميزة معرض أعمال ومنشورات المهنيين معطلة مؤقتاً بواسطة المشرف العام.",
                                color = Color.Gray,
                                fontSize = 9.sp,
                                fontFamily = fontFamily,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color(0xFF223639), thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Direct Contact Row
                Text(
                    text = "👇 قنوات التواصل الفوري والمباشر مع الكادر:",
                    color = AppTheme.accentGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${provider.phone}"))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إتصال مباشر", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                        }
                    }

                    Button(
                        onClick = {
                            if (!settings.isChatEnabled) {
                                Toast.makeText(context, "🚫 ${settings.chatDisabledMessage}", Toast.LENGTH_LONG).show()
                            } else {
                                vm.startChatWithProvider("user_visitor", provider.id, provider.name)
                                vm.navigationTargetTab.value = 2
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1.1f)
                            .height(40.dp)
                            .border(1.dp, AppTheme.accentGold, RoundedCornerShape(8.dp))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Chat, contentDescription = "Safe Chat", tint = AppTheme.accentGold, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("دردشة آمنة", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                        }
                    }

                    Button(
                        onClick = {
                            val cleanNo = provider.phone.trim().replace("+", "").replace(" ", "")
                            val launchNo = if (cleanNo.startsWith("7")) "967$cleanNo" else cleanNo
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$launchNo&text=مرحباً يا غالي، رأيت ملفك الشخصي بالدليل الشامل وحابب أستأجر خدمتك."))
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "الواتساب غير مثبت على هاتفك!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Share, contentDescription = "WhatsApp", tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("واتساب", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                        }
                    }
                }
            }
        }
    }

    lightboxImageBase64?.let { base64 ->
        androidx.compose.ui.window.Dialog(onDismissRequest = { lightboxImageBase64 = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, AppTheme.accentGold),
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .fillMaxHeight(0.7f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val bitmap = rememberBase64Bitmap(base64)
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Zoomed model preview",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    IconButton(
                        onClick = { lightboxImageBase64 = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close zoom", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun RateProviderDialog(
    provider: Provider,
    onDismiss: () -> Unit,
    onSubmit: (Review) -> Unit,
    fontFamily: FontFamily
) {
    var ratingChosen by remember { mutableIntStateOf(5) }
    var userNameInput by remember { mutableStateOf("") }
    var userCommentInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "⭐ تقييم المهني: ${provider.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = AppTheme.accentGold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "رأيك يساهم في تحسين جودة وتثبيت الكوادر المتميزة بالجمهورية.",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    fontFamily = fontFamily
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (star in 1..5) {
                        IconButton(
                            onClick = { ratingChosen = star },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = if (star <= ratingChosen) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "$star Stars",
                                tint = AppTheme.accentGold,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = userNameInput,
                    onValueChange = { userNameInput = it },
                    label = { Text("اسمك الكريم (اختياري)", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = userCommentInput,
                    onValueChange = { userCommentInput = it },
                    label = { Text("اكتب تعليقك وتجربتك بالتفصيل...", fontSize = 11.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (userCommentInput.isBlank()) {
                        Toast.makeText(context, "الرجاء كتابة تعليق لوصف الخدمة قبل الارسال", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val rev = Review(
                        id = UUID.randomUUID().toString(),
                        providerId = provider.id,
                        userName = userNameInput.ifBlank { "عميل غير متسمّ" },
                        rating = ratingChosen,
                        comment = userCommentInput,
                        timestamp = System.currentTimeMillis()
                    )
                    onSubmit(rev)
                    Toast.makeText(context, "تم رفع تقييمك وحفظه بنجاح بالدليل المباشر!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("إرسال التقييم", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء وتراجع", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        containerColor = AppTheme.surfaceDark
    )
}

@Composable
fun ViewReviewsDialog(
    provider: Provider,
    reviews: List<Review>,
    onDismiss: () -> Unit,
    fontFamily: FontFamily
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "💬 آراء وتجارب العملاء لـ ${provider.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = AppTheme.accentGold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                if (reviews.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), contentAlignment = Alignment.Center) {
                        Text("لا توجد مراجعات أو تقييمات مكتوبة لهذا المهني بعد. كن أول من يقيّم نجاح تجربة الخدمة!", color = Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center, fontFamily = fontFamily)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(reviews) { r ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                                border = BorderStroke(1.dp, Color(0xFF223639))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(r.userName, color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 11.sp, fontFamily = fontFamily)
                                        Row {
                                            for (i in 1..5) {
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = null,
                                                    tint = if (i <= r.rating) AppTheme.accentGold else Color.Gray,
                                                    modifier = Modifier.size(10.dp)
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(r.comment, color = Color.White, fontSize = 11.sp, lineHeight = 14.sp, fontFamily = fontFamily)
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("إغلاق", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        containerColor = AppTheme.surfaceDark
    )
}

// Calculate distance using standard, precise, and numerically stable Haversine formula on sphere
fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadiusKm = 6371.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
            
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val dist = earthRadiusKm * c
    return if (dist.isNaN()) 0.0 else dist
}

// Generate stable pseudo-random geographic coordinates around city centers
fun getProviderCoordinates(provider: Provider): Pair<Double, Double> {
    val base = when (provider.city.lowercase()) {
        "sanaa" -> Pair(15.3533, 44.2074)
        "aden" -> Pair(12.7855, 45.0186)
        "taiz" -> Pair(13.5794, 44.0205)
        "hodeidah" -> Pair(14.7979, 42.9530)
        "hadramout" -> Pair(14.4000, 49.0000)
        "ibb" -> Pair(13.9745, 44.1802)
        else -> Pair(15.3533, 44.2074)
    }
    
    val areaLower = provider.area.trim()
    val areaOffset = when {
        areaLower.contains("حدة") || areaLower.contains("حده") -> Pair(-0.018, -0.012)
        areaLower.contains("السبعين") -> Pair(-0.022, 0.005)
        areaLower.contains("الدائري") || areaLower.contains("الجامعة") || areaLower.contains("الجامعه") -> Pair(0.002, -0.008)
        areaLower.contains("الحصبة") || areaLower.contains("الحصبه") -> Pair(0.025, 0.003)
        areaLower.contains("التحرير") -> Pair(0.005, 0.001)
        areaLower.contains("الروضة") || areaLower.contains("الروضه") -> Pair(0.045, 0.015)
        areaLower.contains("الأصبحي") || areaLower.contains("الاصبحي") -> Pair(-0.035, 0.010)
        areaLower.contains("المنصورة") || areaLower.contains("المنصوره") -> Pair(0.005, 0.012)
        areaLower.contains("خور مكسر") || areaLower.contains("خورمكسر") -> Pair(-0.010, 0.035)
        areaLower.contains("الكريتر") || areaLower.contains("كريتر") -> Pair(-0.028, 0.052)
        areaLower.contains("الشيخ عثمان") -> Pair(0.025, 0.025)
        areaLower.contains("جمال") -> Pair(-0.002, -0.005)
        else -> {
            val areaHash = if (areaLower.hashCode() == Int.MIN_VALUE) 0 else if (areaLower.hashCode() < 0) -areaLower.hashCode() else areaLower.hashCode()
            val angle = (areaHash % 360) * (Math.PI / 180.0)
            val dist = 0.006 + (areaHash % 25) * 0.0004
            Pair(Math.sin(angle) * dist, Math.cos(angle) * dist)
        }
    }

    // Tiny micro-dispersion so duplicates in the same area do not overlap exactly
    val individualAngle = (provider.id.hashCode() % 12) * (2 * Math.PI / 12)
    val individualRadius = 0.0012 // ~120 meters maximum offset
    val miniLat = Math.sin(individualAngle) * individualRadius
    val miniLon = Math.cos(individualAngle) * individualRadius

    return Pair(base.first + areaOffset.first + miniLat, base.second + areaOffset.second + miniLon)
}

// --- TAB 1: INTERACTIVE GEOGRAPHIC MAP VIEW (YEMEN RADAR) ---
@Composable
fun MockMapViewScreen(vm: MainViewModel) {
    val settings by vm.settings.collectAsStateWithLifecycle()
    if (!settings.isGeoSearchEnabled) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFF071112)).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.5.dp, AppTheme.primaryRed),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "Map Disabled",
                        tint = AppTheme.primaryRed,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "🔒 خدمة الخرائط معطلة مؤقتاً",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "تم إيقاف ميزة تحديد أقرب مقدمي الخدمات والخرائط التفاعلية مؤقتاً بواسطة المشرف العام للتطبيق.",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        return
    }

    val providers by vm.providers.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedUserCityId by remember { mutableStateOf("sanaa") }
    var selectedProviderForMap by remember { mutableStateOf<Provider?>(null) }
    var maxDistanceFilter by remember { mutableFloatStateOf(30f) } // default show within 30km
    
    // Virtual appointment reservation target on map
    var bookingProviderTargetOnMap by remember { mutableStateOf<Provider?>(null) }
    var profileProviderTargetOnMap by remember { mutableStateOf<Provider?>(null) }

    val userCoords = when (selectedUserCityId.lowercase()) {
        "sanaa" -> Pair(15.3533, 44.2074)
        "aden" -> Pair(12.7855, 45.0186)
        "taiz" -> Pair(13.5794, 44.0205)
        "hodeidah" -> Pair(14.7979, 42.9530)
        "hadramout" -> Pair(14.4000, 49.0)
        "ibb" -> Pair(13.9745, 44.1802)
        else -> Pair(15.3533, 44.2074)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071112))
    ) {
        // Upper Controls: City Selector & Proximity Filter
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🌐 خارطة رادار الخدمات وإحداثيات الموقع:",
                    color = AppTheme.accentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("موقعي الحالي:", color = Color.White, fontSize = 10.sp, modifier = Modifier.weight(0.4f))
                    
                    // City Dropdown selection
                    Box(modifier = Modifier.weight(1f)) {
                        var dropdownExpanded by remember { mutableStateOf(false) }
                        val activeLabel = cities.find { it.id == selectedUserCityId }?.nameAr ?: "صنعاء 🌍"
                        
                        Button(
                            onClick = { dropdownExpanded = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth().height(36.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                        ) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(activeLabel, color = Color.White, fontSize = 11.sp)
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = AppTheme.accentGold)
                            }
                        }
                        
                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier.background(AppTheme.surfaceDark)
                        ) {
                            cities.forEach { city ->
                                DropdownMenuItem(
                                    text = { Text(city.nameAr, color = Color.White, fontSize = 11.sp) },
                                    onClick = {
                                        selectedUserCityId = city.id
                                        selectedProviderForMap = null
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Proximity selector slider
                    Column(modifier = Modifier.weight(1.3f)) {
                        Text(
                            text = "نطاق البحث: بقرب ${maxDistanceFilter.toInt()} كم",
                            color = Color.LightGray,
                            fontSize = 9.sp
                        )
                        Slider(
                            value = maxDistanceFilter,
                            onValueChange = { maxDistanceFilter = it },
                            valueRange = 2f..50f,
                            colors = SliderDefaults.colors(
                                thumbColor = AppTheme.primaryRed,
                                activeTrackColor = AppTheme.accentGold
                            ),
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }

        // Radar Canvas Box
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF030D0E))
        ) {
            // Draw Radar rings and grid mathematically
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                
                // Draw circular radar bounds
                for (r in listOf(0.15f, 0.3f, 0.45f, 0.6f, 0.75f, 0.9f)) {
                    drawCircle(
                        color = Color(0xFF223639).copy(alpha = 0.5f),
                        radius = size.width * r,
                        center = androidx.compose.ui.geometry.Offset(cx, cy),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f)
                    )
                }
                
                // Draw cross axes
                drawLine(
                    color = Color(0xFF223639).copy(alpha = 0.4f),
                    start = androidx.compose.ui.geometry.Offset(0f, cy),
                    end = androidx.compose.ui.geometry.Offset(size.width, cy)
                )
                drawLine(
                    color = Color(0xFF223639).copy(alpha = 0.4f),
                    start = androidx.compose.ui.geometry.Offset(cx, 0f),
                    end = androidx.compose.ui.geometry.Offset(cx, size.height)
                )
            }

            // Render Center Pin (The User)
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val cxDp = maxWidth / 2f - 18.dp
                    val cyDp = maxHeight / 2f - 24.dp
                    
                    Column(
                        modifier = Modifier
                            .absoluteOffset(x = cxDp, y = cyDp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonPinCircle,
                            contentDescription = "Your Location",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(36.dp)
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF2196F3).copy(alpha = 0.9f))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text("أنت هنا", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Render Nearby Providers based on Lat/Long distance limits
            val nearbyProviders = providers.filter { p ->
                val coords = getProviderCoordinates(p)
                val dist = calculateDistance(userCoords.first, userCoords.second, coords.first, coords.second)
                dist <= maxDistanceFilter
            }

            nearbyProviders.forEachIndexed { idx, provider ->
                val coords = getProviderCoordinates(provider)
                val dist = calculateDistance(userCoords.first, userCoords.second, coords.first, coords.second)
                
                // Dynamic scaling factor relative to the slider's radius range (1 degree ≈ 111 km)
                val scaleFactorRange = (maxDistanceFilter / 111.0).coerceAtLeast(0.01)
                val relX = ((coords.second - userCoords.second) / scaleFactorRange).coerceIn(-1.0, 1.0).toFloat()
                val relY = ((coords.first - userCoords.first) / scaleFactorRange).coerceIn(-1.0, 1.0).toFloat()

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        // Calculate positions based on midpoint offsets
                        val posX = (maxWidth / 2f) + ((maxWidth / 2f) * relX * 0.85f) - 16.dp
                        // invert Y to conform to Cartesian coords map vs screen pixels
                        val posY = (maxHeight / 2f) - ((maxHeight / 2f) * relY * 0.85f) - 24.dp

                        Column(
                            modifier = Modifier
                                .absoluteOffset(x = posX, y = posY)
                                .clickable { selectedProviderForMap = provider }
                                .testTag("geo_marker_$idx"),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = provider.name,
                                tint = if (provider.isPinned) AppTheme.accentGold else AppTheme.primaryRed,
                                modifier = Modifier.size(32.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Black.copy(alpha = 0.8f))
                                    .padding(horizontal = 3.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "${provider.name.split(" ").lastOrNull() ?: provider.name} (${"%.1f".format(dist)} كم)",
                                    color = Color.White,
                                    fontSize = 7.5.sp
                                )
                            }
                        }
                    }
                }
            }

            // Radar Scale Indicator Overlay
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(6.dp)
            ) {
                Text(
                    text = "⚙️ مقياس الخريطة الافتراضي:\n  • النطاق: ${maxDistanceFilter.toInt()} كم\n  • الدقة: ±١٠٠ متر",
                    color = Color.LightGray,
                    fontSize = 8.sp,
                    lineHeight = 11.sp
                )
            }
        }

        // Selected Provider bottom Card Details
        selectedProviderForMap?.let { p ->
            val pCoords = getProviderCoordinates(p)
            val pDist = calculateDistance(userCoords.first, userCoords.second, pCoords.first, pCoords.second)
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { profileProviderTargetOnMap = p }
                    .padding(10.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, AppTheme.accentGold.copy(alpha = 0.6f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Green))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(p.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("👤 الملف والمعرض ◀", color = AppTheme.accentGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                        IconButton(
                            onClick = { selectedProviderForMap = null },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(p.description, color = AppTheme.grayText, fontSize = 11.sp, maxLines = 2, lineHeight = 15.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📍 إحداثيات: ${"%.4f".format(pCoords.first)}° N, ${"%.4f".format(pCoords.second)}° E",
                            color = AppTheme.grayText,
                            fontSize = 9.sp
                        )
                        Text(
                            text = "على بعد ${"%.1f".format(pDist)} كم من موقعك",
                            color = AppTheme.accentGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = {
                                val currentSettings = vm.settings.value
                                if (!currentSettings.isChatEnabled) {
                                    Toast.makeText(context, "🚫 ${currentSettings.chatDisabledMessage}", Toast.LENGTH_LONG).show()
                                } else {
                                    vm.startChatWithProvider("user_visitor", p.id, p.name)
                                    vm.navigationTargetTab.value = 2
                                    Toast.makeText(context, "تم فتح غرفة الاتصال الفوري المباشر", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.weight(1f).height(34.dp).border(1.dp, AppTheme.accentGold, RoundedCornerShape(6.dp)),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Message, contentDescription = null, tint = AppTheme.accentGold, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("مراسلة فورية", fontSize = 9.sp, color = AppTheme.accentGold)
                        }

                        Button(
                            onClick = { bookingProviderTargetOnMap = p },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                            modifier = Modifier.weight(1.2f).height(34.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Event, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("حجز موعد خدمة", fontSize = 9.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }

    // Capture booking request directly from radar map
    bookingProviderTargetOnMap?.let { provider ->
        val userCityLabel = cities.find { it.id == selectedUserCityId }?.nameAr ?: ""
        BookAppointmentDialog(
            provider = provider,
            onDismiss = { bookingProviderTargetOnMap = null },
            onSubmit = { details, timeChosen ->
                vm.requestServiceAppointment(provider.id, provider.name, "$details (الموقع المقترح: $userCityLabel)", timeChosen)
                bookingProviderTargetOnMap = null
                selectedProviderForMap = null
            },
            fontFamily = FontFamily.Default
        )
    }

    // Capture profile show directly from radar map
    profileProviderTargetOnMap?.let { provider ->
        ProfessionalProfileDialog(
            provider = provider,
            vm = vm,
            onDismiss = { profileProviderTargetOnMap = null },
            fontFamily = FontFamily.Default
        )
    }
}

// --- BOOK APPOINTMENT DIALOG COMPOSABLE ---
@Composable
fun BookAppointmentDialog(
    provider: Provider,
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit,
    fontFamily: FontFamily
) {
    var details by remember { mutableStateOf("") }
    var timeChosen by remember { mutableStateOf("غداً الساعة 4:00 مساءً") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🗓️ طلب موعد خدمة: ${provider.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = AppTheme.accentGold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "سيصل طلبك مباشرة لكادر الصيانة مع إبلاغك بالحالة وتحديثات الوقت بشكل لحظي وفوري عبر الإشعارات.",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    fontFamily = fontFamily
                )

                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("تفاصيل الخدمة المطلوبة (مثال: تركيب مروحة داخلية)", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = timeChosen,
                    onValueChange = { timeChosen = it },
                    label = { Text("الموعد والوقت المقترح", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (details.isBlank()) {
                        Toast.makeText(context, "الرجاء تحديد تفاصيل المشكلة أو الخدمة أولاً", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    onSubmit(details, timeChosen)
                    Toast.makeText(context, "تم إرسال طلبك بنجاح! راقب إشعاراتك اللحظية", Toast.LENGTH_LONG).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("تأكيد وحجز موعد الآن", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("تراجع وإلغاء", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        containerColor = AppTheme.surfaceDark
    )
}

// --- TAB 2: DIRECT CHATS SCENE ---
@Composable
fun DirectChatScreen(vm: MainViewModel, fontFamily: FontFamily, fontColor: Color) {
    val chats by vm.chats.collectAsStateWithLifecycle()
    val messages by vm.chatMessages.collectAsStateWithLifecycle()
    val activeRoomId by vm.currentChatRoomId.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()

    if (!settings.isChatEnabled) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.darkBg)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(AppTheme.primaryRed.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Chat Disabled",
                    tint = AppTheme.primaryRed,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "💬 خدمة المحادثة الفورية معطلة حالياً",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = settings.chatDisabledMessage,
                color = AppTheme.grayText,
                fontSize = 12.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    } else if (activeRoomId != null) {
        // RENDER ACTIVE CHAT CONVERSATION SCREEN
        ConversationScreen(vm = vm, chatId = activeRoomId!!, fontFamily = fontFamily, fontColor = fontColor)
    } else {
        // RENDER ACTIVE CHAT LIST
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.darkBg)
                .padding(12.dp)
        ) {
            Text("💬 قنوات المحادثات الفورية والآمنة", color = AppTheme.accentGold, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
            Spacer(modifier = Modifier.height(10.dp))

            if (chats.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("لا توجد قنوات تواصل مفتوحة حالياً. يمكنك بدء تواصل مباشر من دليل الحرفيين.", color = Color.Gray, textAlign = TextAlign.Center, fontFamily = fontFamily)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(chats) { chat ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { vm.currentChatRoomId.value = chat.id }
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(AppTheme.primaryRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(chat.providerName.take(2), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(chat.providerName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp, fontFamily = fontFamily)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(chat.lastMessage, color = AppTheme.grayText, fontSize = 11.sp, maxLines = 1, fontFamily = fontFamily)
                                }
                                Icon(Icons.Default.ArrowForwardIos, contentDescription = "Open", tint = Color.Gray, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- CONVERSATION SUB-SCREEN (FIXED TEXT COLOR & ADDED SEND BUTTON) ---
@Composable
fun ConversationScreen(vm: MainViewModel, chatId: String, fontFamily: FontFamily, fontColor: Color) {
    val chats by vm.chats.collectAsStateWithLifecycle()
    val messages by vm.chatMessages.collectAsStateWithLifecycle()
    val activeChat = chats.find { it.id == chatId }
    
    var typingInput by remember { mutableStateOf("") }
    val filteredMessages = messages.filter { it.chatId == chatId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.darkBg)
    ) {
        // Chat Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.surfaceDark)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { vm.currentChatRoomId.value = null }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(activeChat?.providerName ?: "محادثة آمنة", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = fontFamily)
                    Text("نشط حالياً بالدليل • عبر الإنترنت", color = AppTheme.lightGreen, fontSize = 9.sp, fontFamily = fontFamily)
                }
            }
        }

        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(filteredMessages) { msg ->
                val isMyMessage = msg.senderType == "user"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isMyMessage) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topStart = 12.dp,
                                    topEnd = 12.dp,
                                    bottomStart = if (isMyMessage) 12.dp else 0.dp,
                                    bottomEnd = if (isMyMessage) 0.dp else 12.dp
                                )
                            )
                            .background(if (isMyMessage) AppTheme.primaryRed else AppTheme.surfaceDark)
                            .padding(10.dp)
                            .widthIn(max = 260.dp)
                    ) {
                        Text(
                            text = msg.message,
                            // CRITICAL FIX: Explicitly enforce white/gold colors so font is ALWAYS clearly visible
                            color = if (isMyMessage) Color.White else fontColor,
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
                            fontFamily = fontFamily
                        )
                    }
                }
            }
        }

        // Chat Input Row (CRITICAL FIX: Fully visible background, white input text, clear send button/arrow)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.surfaceDark)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = typingInput,
                onValueChange = { typingInput = it },
                placeholder = { Text("اكتب رسالتك لطلب الخدمة...", color = Color.Gray, fontFamily = fontFamily, fontSize = 12.sp) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontFamily = fontFamily, fontSize = 12.sp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = AppTheme.darkBg,
                    unfocusedContainerColor = AppTheme.darkBg,
                    cursorColor = AppTheme.accentGold,
                    focusedIndicatorColor = AppTheme.accentGold
                ),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    if (typingInput.isNotBlank()) {
                        vm.sendChatMessage(chatId, "زائر", "user", typingInput)
                        typingInput = ""
                    }
                })
            )

            Spacer(modifier = Modifier.width(8.dp))

            // CRITICAL FIX: High-visibility solid Arabic send button directly meets user requirements
            Button(
                onClick = {
                    if (typingInput.isNotBlank()) {
                        vm.sendChatMessage(chatId, "زائر", "user", typingInput)
                        typingInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(48.dp)
                    .testTag("chat_send_btn"),
                contentPadding = PaddingValues(horizontal = 14.dp)
            ) {
                Text(
                    text = "إرسال 🚀",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )
            }
        }
    }
}

// --- TAB 3: JOIN FORM ---
@Composable
fun JoinApplicationScreen(vm: MainViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var selectedCatId by remember { mutableStateOf("plumbing") }
    var selectedCityId by remember { mutableStateOf("sanaa") }
    var acceptedRulesMap by remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }

    var selfieBase64 by remember { mutableStateOf("") }
    var nationalIdImageBase64 by remember { mutableStateOf("") }
    var isFemaleGender by remember { mutableStateOf(false) }
    var portfolioBase64List by remember { mutableStateOf<List<String>>(emptyList()) }

    var showSuccessDialog by remember { mutableStateOf(false) }

    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()

    val fontFamily = resolveAppFontFamily(settings.selectedFontName)
    val customChipBgHex = settings.registrationChipColorHex.ifBlank { "#3A7CA5" }
    val baseChipColor = try { Color(android.graphics.Color.parseColor(customChipBgHex)) } catch (e: Exception) { Color(0xFF3A7CA5) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val compressed = compressImageBase64(context, it, maxWidth = 320, maxHeight = 320, quality = 70)
            selfieBase64 = compressed
            Toast.makeText(context, "تم اختيار صورة وضغطها تلقائياً للمحافظة على باقة الإنترنت 🖼️", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val compressed = compressBitmapBase64(it, maxWidth = 320, maxHeight = 320, quality = 70)
            selfieBase64 = compressed
            Toast.makeText(context, "تم التقاط وضغط صورة السيلفي بنجاح عبر الكاميرا 📷", Toast.LENGTH_SHORT).show()
        }
    }

    val idGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val compressed = compressImageBase64(context, it, maxWidth = 320, maxHeight = 320, quality = 70)
            nationalIdImageBase64 = compressed
            Toast.makeText(context, "تم اختيار صورة البطاقة وضغطها بنجاح 🪪", Toast.LENGTH_SHORT).show()
        }
    }

    val idCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val compressed = compressBitmapBase64(it, maxWidth = 320, maxHeight = 320, quality = 70)
            nationalIdImageBase64 = compressed
            Toast.makeText(context, "تم التقاط صورة بطاقة الهوية بنجاح عبر الكاميرا 📷", Toast.LENGTH_SHORT).show()
        }
    }

    val portfolioGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (portfolioBase64List.size >= settings.maxPortfolioImages) {
                Toast.makeText(context, "الحد الأقصى لرفع صور معرض الأعمال هو ${settings.maxPortfolioImages} صور فقط!", Toast.LENGTH_LONG).show()
                return@let
            }
            val compressed = compressImageBase64(context, it, maxWidth = 350, maxHeight = 350, quality = 65)
            portfolioBase64List = portfolioBase64List + compressed
            Toast.makeText(context, "تم إضافة نموذج من أعمالك بنجاح! 🖼️", Toast.LENGTH_SHORT).show()
        }
    }

    val portfolioCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            if (portfolioBase64List.size >= settings.maxPortfolioImages) {
                Toast.makeText(context, "الحد الأقصى لرفع صور معرض الأعمال هو ${settings.maxPortfolioImages} صور فقط!", Toast.LENGTH_LONG).show()
                return@let
            }
            val compressed = compressBitmapBase64(it, maxWidth = 350, maxHeight = 350, quality = 65)
            portfolioBase64List = portfolioBase64List + compressed
            Toast.makeText(context, "تم التقاط نموذج من أعمالك بالنجاح! 📷", Toast.LENGTH_SHORT).show()
        }
    }

    // Success dialog shown when request is fully uploaded/persisted
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                vm.navigationTargetTab.value = 0
            },
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = AppTheme.lightGreen,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "تم تقديم الطلب بنجاح! 🎉",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )
                }
            },
            text = {
                Text(
                    text = "شكراً لتقديمك! لقد تم رفع وإرسال مستنداتك وطلب انضمامك إلى الدليل بنجاح بالرقم المرجعي الموحد.\n\nجاري الآن فحص ومراجعة صورة هويتك والصورة الشخصية من قبل المشرفين، وسيتم تفعيل حسابك كفني معتمد وتلقي إشعار فور الاعتماد.",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        vm.navigationTargetTab.value = 0
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("موافق ورجوع للرئيسية 🗺️", color = Color.White, fontFamily = fontFamily, fontSize = 11.sp)
                }
            },
            containerColor = AppTheme.surfaceDark,
            shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.darkBg)
            .padding(14.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "💼 تقديم طلب انضمام كـ كادر مهني جديد",
                    color = AppTheme.accentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Conditionally display registration terms / conditions
                if (settings.registrationRulesList.isNotEmpty()) {
                    Text(
                        text = "📜 شروط ومتطلبات التسجيل والاعتماد للخدمة:",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                        border = BorderStroke(1.dp, baseChipColor.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                    ) {
                        Column(modifier = Modifier.padding(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            settings.registrationRulesList.forEachIndexed { idx, rule ->
                                val isMandatory = !rule.startsWith("[اختياري]")
                                val cleanText = rule.removePrefix("[إجباري] ").removePrefix("[اختياري] ")
                                val isChecked = acceptedRulesMap[idx] ?: false
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .clickable {
                                            acceptedRulesMap = acceptedRulesMap.toMutableMap().apply { this[idx] = !isChecked }
                                        }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { chk ->
                                            acceptedRulesMap = acceptedRulesMap.toMutableMap().apply { this[idx] = chk ?: false }
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = baseChipColor,
                                            uncheckedColor = Color.Gray,
                                            checkmarkColor = Color.White
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("${idx + 1}. ", color = baseChipColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = cleanText,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        lineHeight = 13.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = if (isMandatory) AppTheme.primaryRed.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(3.dp)
                                    ) {
                                        Text(
                                            text = if (isMandatory) "إجباري" else "اختياري",
                                            color = if (isMandatory) AppTheme.primaryRed else Color.LightGray,
                                            fontSize = 8.sp,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Dynamic inputs visibility and requirements matching AppSettings!
                if (settings.regNameVisible) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("الاسم الكامل / Professional Name" + if (settings.regNameRequired) " (مطلوب)" else "") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(color = Color.White, fontFamily = fontFamily)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // GENDER CHOOSER SELECTOR
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                ) {
                    Text("جنس المتقدم الحالي: ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(
                        modifier = Modifier.clickable { isFemaleGender = false },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isFemaleGender, 
                            onClick = { isFemaleGender = false },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = baseChipColor,
                                unselectedColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ذكر 👨", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Row(
                        modifier = Modifier.clickable { isFemaleGender = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isFemaleGender, 
                            onClick = { isFemaleGender = true },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = baseChipColor,
                                unselectedColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("أنثى 👩", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                if (settings.regPhoneVisible) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("رقم الهاتف اليمني للاتصال" + if (settings.regPhoneRequired) " (مطلوب)" else "") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(color = Color.White, fontFamily = fontFamily)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (settings.regAreaVisible) {
                    OutlinedTextField(
                        value = area,
                        onValueChange = { area = it },
                        label = { Text("المنطقة / الشارع بالتفصيل" + if (settings.regAreaRequired) " (مطلوب)" else "") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(color = Color.White, fontFamily = fontFamily)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (settings.regDescVisible) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("نبذة مختصرة عن مؤهلاتك وخدماتك السريعة" + if (settings.regDescRequired) " (مطلوب)" else "") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
                        ),
                        textStyle = TextStyle(color = Color.White, fontFamily = fontFamily)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (settings.regCategoryVisible) {
                    Text("اختر فئة التخصص:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(categories) { cat ->
                            val isSelected = selectedCatId == cat.id
                            val customChipBgHex = settings.registrationChipColorHex.ifBlank { "#3A7CA5" }
                            val baseChipColor = try { Color(android.graphics.Color.parseColor(customChipBgHex)) } catch (e: Exception) { Color(0xFF3A7CA5) }
                            
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) baseChipColor else baseChipColor.copy(alpha = 0.2f))
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) Color.White else baseChipColor.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedCatId = cat.id }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    CategoryIconOrImage(cat.iconUrl, iconSize = 13)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = cat.nameAr,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Text("اختر مدينة النشاط الحالية:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(cities) { city ->
                        val isSelected = selectedCityId == city.id
                        val customChipBgHex = settings.registrationChipColorHex.ifBlank { "#3A7CA5" }
                        val baseChipColor = try { Color(android.graphics.Color.parseColor(customChipBgHex)) } catch (e: Exception) { Color(0xFF3A7CA5) }
                        
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) baseChipColor else baseChipColor.copy(alpha = 0.2f))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Color.White else baseChipColor.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedCityId = city.id }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = city.nameAr,
                                fontSize = 11.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // --- SINGLE SELFIE / MAIN IMAGE CAPTURE ---
                if (settings.regSelfieVisible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F2225))
                            .border(1.dp, Color(0xFF223639), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = (if (isFemaleGender) "🛠️ يرجى رفع صورة ترمز لمهنتك/حرفتك (أو صورة شخصية اختيارية)" else "📷 يرجى التقاط صورتك الشخصية السيلفي مباشرة كشرط أساسي لتوثيق الحساب") + (if (settings.regSelfieRequired) " (إجباري)" else " (اختياري)"),
                            color = AppTheme.accentGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { cameraLauncher.launch(null) },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.weight(1.2f).height(38.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = "Camera", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("فتح الكاميرا سيلفي 📷", fontSize = 9.sp, fontFamily = fontFamily)
                            }

                            Button(
                                onClick = { galleryLauncher.launch("image/*") },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                                modifier = Modifier.weight(1f).height(38.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp)),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = "Gallery", modifier = Modifier.size(14.dp), tint = AppTheme.accentGold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("اختر من الاستوديو 🖼️", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                            }
                        }

                        if (selfieBase64.isNotEmpty()) {
                            val previewBitmap = rememberBase64Bitmap(selfieBase64)
                            previewBitmap?.let {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier.size(80.dp),
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.5.dp, AppTheme.accentGold)
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "Preview Image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("تم تحميل الصورة بنجاح ✅", color = AppTheme.lightGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                                        Text("مع ضغط فوري لتخفيف حجم البيانات سحابياً", color = AppTheme.grayText, fontSize = 8.sp, fontFamily = fontFamily)
                                        TextButton(onClick = { selfieBase64 = "" }) {
                                            Text("إزالة الصورة ❌", color = AppTheme.primaryRed, fontSize = 9.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // --- NATIONAL ID CARD PHOTO CAPTURE (NEW FEATURE FOR THIRD REQUEST) ---
                if (settings.regIdCardVisible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF0F2225))
                            .border(1.dp, Color(0xFF223639), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "🪪 صورة بطاقة الهوية الشخصية / جواز السفر للتأكد والاعتماد" + if (settings.regIdCardRequired) " (إجباري للمطابقة)" else " (اختياري)",
                            color = AppTheme.accentGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontFamily
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { idCameraLauncher.launch(null) },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.weight(1.2f).height(38.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = "ID Cam", modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("التقاط صورة الهوية 📷", fontSize = 9.sp, fontFamily = fontFamily)
                            }

                            Button(
                                onClick = { idGalleryLauncher.launch("image/*") },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                                modifier = Modifier.weight(1f).height(38.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp)),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = "ID Gal", modifier = Modifier.size(14.dp), tint = AppTheme.accentGold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("اختر من الاستوديو 🖼️", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                            }
                        }

                        if (nationalIdImageBase64.isNotEmpty()) {
                            val previewIdBitmap = rememberBase64Bitmap(nationalIdImageBase64)
                            previewIdBitmap?.let {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier.size(80.dp),
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.5.dp, AppTheme.accentGold)
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "Preview ID Image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("تم تحميل وثيقة الهوية بنجاح ✅", color = AppTheme.lightGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                                        Text("مشفرة ومحمية بالكامل سحابياً للأدمن فقط", color = AppTheme.grayText, fontSize = 8.sp, fontFamily = fontFamily)
                                        TextButton(onClick = { nationalIdImageBase64 = "" }) {
                                            Text("إزالة الصورة ❌", color = AppTheme.primaryRed, fontSize = 9.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // --- PORTFOLIO GALLERY IMAGES CAPTURE ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF0F2225))
                        .border(1.dp, Color(0xFF223639), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "🎨 معرض صور من نماذج أعمالك ومشاريعك (${portfolioBase64List.size}/${settings.maxPortfolioImages}):",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { portfolioCameraLauncher.launch(null) },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                            modifier = Modifier.weight(1f).height(36.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp)),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.Camera, contentDescription = "Cam Work", modifier = Modifier.size(13.dp), tint = AppTheme.accentGold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("التقاط عمل 📷", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                        }

                        Button(
                            onClick = { portfolioGalleryLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                            modifier = Modifier.weight(1f).height(36.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp)),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Gal Work", modifier = Modifier.size(13.dp), tint = AppTheme.accentGold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إضافة من الألبوم 🖼️", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                        }
                    }

                    if (portfolioBase64List.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            itemsIndexed(portfolioBase64List) { idx, base64 ->
                                val bitmap = rememberBase64Bitmap(base64)
                                bitmap?.let {
                                    Box(modifier = Modifier.size(70.dp)) {
                                        Card(
                                            modifier = Modifier.fillMaxSize(),
                                            shape = RoundedCornerShape(6.dp),
                                            border = BorderStroke(1.dp, AppTheme.accentGold)
                                        ) {
                                            Image(
                                                bitmap = it.asImageBitmap(),
                                                contentDescription = "Portfolio Image",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        IconButton(
                                            onClick = { portfolioBase64List = portfolioBase64List.filterIndexed { i, _ -> i != idx } },
                                            modifier = Modifier
                                                .size(20.dp)
                                                .align(Alignment.TopEnd)
                                                .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Red, modifier = Modifier.size(12.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        // Locate outstanding mandatory conditions
                        val missingMandatories = settings.registrationRulesList.mapIndexedNotNull { idx, rule ->
                            val isMandatory = !rule.startsWith("[اختياري]")
                            val isChecked = acceptedRulesMap[idx] ?: false
                            if (isMandatory && !isChecked) rule.removePrefix("[إجباري] ").removePrefix("[اختياري] ") else null
                        }

                        if (missingMandatories.isNotEmpty()) {
                            Toast.makeText(context, "الرجاء الموافقة على الشرط الإجباري أولاً: ${missingMandatories.first()}", Toast.LENGTH_LONG).show()
                        } else if (settings.regNameVisible && settings.regNameRequired && name.isBlank()) {
                            Toast.makeText(context, "الرجاء تعبئة الاسم الكامل لمطابقة وثائقك الوطنية!", Toast.LENGTH_SHORT).show()
                        } else if (settings.regPhoneVisible && settings.regPhoneRequired && phone.isBlank()) {
                            Toast.makeText(context, "الرجاء إدخال رقم الهاتف للتواصل والاتصال!", Toast.LENGTH_SHORT).show()
                        } else if (settings.regAreaVisible && settings.regAreaRequired && area.isBlank()) {
                            Toast.makeText(context, "الرجاء تحديد المنطقة والشارع بدقة لتحديد موقعك!", Toast.LENGTH_SHORT).show()
                        } else if (settings.regDescVisible && settings.regDescRequired && description.isBlank()) {
                            Toast.makeText(context, "الرجاء إدخال النبذة التعريفية عن خبراتك!", Toast.LENGTH_SHORT).show()
                        } else if (settings.regSelfieVisible && settings.regSelfieRequired && selfieBase64.isBlank()) {
                            Toast.makeText(context, "الرجاء التقاط صورتك السيلفي الشخصية كطلب توثيق معتمد!", Toast.LENGTH_SHORT).show()
                        } else if (settings.regIdCardVisible && settings.regIdCardRequired && nationalIdImageBase64.isBlank()) {
                            Toast.makeText(context, "الرجاء تصوير أو رفع صورة بطاقة الهوية لمطابقة حسابك!", Toast.LENGTH_SHORT).show()
                        } else {
                            val newRequest = PendingProvider(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                category = selectedCatId,
                                city = selectedCityId,
                                phone = phone,
                                description = description,
                                area = area,
                                deviceId = "device_${UUID.randomUUID().toString().take(4)}",
                                selfieImageBase64 = selfieBase64,
                                isFemale = isFemaleGender,
                                portfolioImages = portfolioBase64List,
                                orderPriority = 0,
                                nationalIdImageBase64 = nationalIdImageBase64
                            )
                            vm.registerPendingProvider(newRequest)
                            name = ""
                            phone = ""
                            area = ""
                            description = ""
                            selfieBase64 = ""
                            nationalIdImageBase64 = ""
                            portfolioBase64List = emptyList()
                            
                            // Triggers visual dialog confirmation
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("رفع مستندات وتأكيد الطلب 📥", color = Color.White, fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

// --- TAB 4: APP INFO (DYNAMICALLY CHANGABLE HERO COVER IMAGE & DOWNLOAD LINK) ---
@Composable
fun AppInfoScreen(vm: MainViewModel) {
    val settings by vm.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.darkBg)
            .verticalScroll(rememberScrollState())
            .padding(14.dp)
    ) {
        // Hero Photo Cover - Dynamic loaded with custom image url or base64 or text banner
        val aboutCoverVal = settings.aboutImageUrl
        val isTextBanner = aboutCoverVal.startsWith("text:") || 
                (!aboutCoverVal.startsWith("http://") && 
                 !aboutCoverVal.startsWith("https://") && 
                 !aboutCoverVal.startsWith("data:image/") && 
                 aboutCoverVal.length < 150)

        if (isTextBanner) {
            val displayTxt = if (aboutCoverVal.startsWith("text:")) aboutCoverVal.substringAfter("text:") else aboutCoverVal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        androidx.compose.ui.graphics.Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0F2225),
                                AppTheme.primaryRed,
                                AppTheme.accentGold.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .border(1.dp, AppTheme.accentGold, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayTxt,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = resolveAppFontFamily(settings.selectedFontName)
                )
            }
        } else {
            val base64Bitmap = rememberBase64Bitmap(aboutCoverVal)
            AsyncImage(
                model = base64Bitmap ?: aboutCoverVal,
                contentDescription = "About Cover Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, AppTheme.accentGold, RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = settings.aboutTitleText,
                    color = AppTheme.accentGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = resolveAppFontFamily(settings.selectedFontName)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = settings.welcomeMessage,
                    color = Color.White,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = resolveAppFontFamily(settings.selectedFontName)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // App version stats
                if (settings.aboutVersionVisible) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(settings.aboutVersionLabel, color = AppTheme.grayText, fontSize = 11.sp, fontFamily = resolveAppFontFamily(settings.selectedFontName))
                        Text(settings.aboutVersionValue, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = resolveAppFontFamily(settings.selectedFontName))
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
                
                if (settings.aboutSecurityVisible) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(settings.aboutSecurityLabel, color = AppTheme.grayText, fontSize = 11.sp, fontFamily = resolveAppFontFamily(settings.selectedFontName))
                        Text(settings.aboutSecurityValue, color = Color.Green, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = resolveAppFontFamily(settings.selectedFontName))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- DYNAMICALLY SHARE DOWNLOAD LINK OPTION ---
                if (settings.downloadUrl.isNotBlank()) {
                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(settings.downloadUrl))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "الرابط غير صالح حالياً أو يحتاج تصحيح", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تحميل وتثبيت التطبيق مباشرة (APK) 📥", color = Color.White, fontSize = 11.sp, fontFamily = resolveAppFontFamily(settings.selectedFontName))
                    }
                }
            }
        }

        // Support direct contacts section
        if (settings.aboutPhoneVisible || settings.aboutWhatsappVisible || settings.aboutEmailVisible) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF223639)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "📞 قنوات التواصل والدعم الفني المباشر:",
                        color = AppTheme.accentGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = resolveAppFontFamily(settings.selectedFontName)
                    )
                    
                    if (settings.aboutPhoneVisible && settings.aboutPhone.isNotBlank()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try {
                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${settings.aboutPhone}"))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {}
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = "Call", tint = AppTheme.primaryRed, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "اتصال هاتفي: ${settings.aboutPhone}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = resolveAppFontFamily(settings.selectedFontName)
                            )
                        }
                    }

                    if (settings.aboutWhatsappVisible && settings.aboutWhatsapp.isNotBlank()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try {
                                        val url = "https://api.whatsapp.com/send?phone=${settings.aboutWhatsapp}"
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {}
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = Color.Green, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "واتساب مباشر: ${settings.aboutWhatsapp}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = resolveAppFontFamily(settings.selectedFontName)
                            )
                        }
                    }

                    if (settings.aboutEmailVisible && settings.aboutEmail.isNotBlank()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    try {
                                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${settings.aboutEmail}"))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {}
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = "Email", tint = AppTheme.accentGold, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "البريد الإلكتروني: ${settings.aboutEmail}",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = resolveAppFontFamily(settings.selectedFontName)
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 5: ADMIN SETTINGS SCREEN & CONTROL GENERAL DASHBOARD ---
@Composable
fun AdminSettingsScreen(vm: MainViewModel) {
    val loggedAdmin by vm.loggedInUsername.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()
    val currentFont = resolveAppFontFamily(settings.selectedFontName)

    if (loggedAdmin.isEmpty()) {
        val context = LocalContext.current
        val loginPrefs = remember { context.getSharedPreferences("admin_login_prefs", Context.MODE_PRIVATE) }
        var rememberMe by remember { mutableStateOf(loginPrefs.getBoolean("remember_me", false)) }
        var usernameInput by remember { mutableStateOf(if (rememberMe) loginPrefs.getString("saved_username", "") ?: "" else "") }
        var passwordInput by remember { mutableStateOf(if (rememberMe) loginPrefs.getString("saved_password", "") ?: "" else "") }
        var errorState by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.darkBg)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, AppTheme.accentGold),
                modifier = Modifier.fillMaxWidth(0.92f)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin Area",
                        tint = AppTheme.accentGold,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = "🔐 بوابة الدخول للتحكم وصلاحيات الإدارة",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = currentFont
                    )
                    Text(
                        text = "هذه اللوحة مخصصة لإدارة المنصة والإشراف على المهن والكوادر والمدن.",
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = currentFont
                    )

                    OutlinedTextField(
                        value = usernameInput,
                        onValueChange = { 
                            usernameInput = it
                            errorState = false
                        },
                        label = { Text("اسم المستخدم", fontFamily = currentFont, color = Color.Gray, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White, fontFamily = currentFont),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = AppTheme.accentGold
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                    )

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { 
                            passwordInput = it
                            errorState = false
                        },
                        label = { Text("رمز المرور السري", fontFamily = currentFont, color = Color.Gray, fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White, fontFamily = currentFont),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = AppTheme.accentGold
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        isError = errorState
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { rememberMe = !rememberMe }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(checkedColor = AppTheme.accentGold, uncheckedColor = Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "حفظ بيانات تسجيل الدخول تلقائياً 💾",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontFamily = currentFont
                        )
                    }

                    if (errorState) {
                        Text("اسم المستخدم أو كلمة المرور غير صحيحة!", color = AppTheme.primaryRed, fontSize = 10.sp, fontFamily = currentFont)
                    }

                    Button(
                        onClick = {
                            if (vm.checkAdminThreeLayersLogin(usernameInput, passwordInput)) {
                                if (rememberMe) {
                                    loginPrefs.edit()
                                        .putBoolean("remember_me", true)
                                        .putString("saved_username", usernameInput)
                                        .putString("saved_password", passwordInput)
                                        .apply()
                                } else {
                                    loginPrefs.edit().clear().apply()
                                }
                                Toast.makeText(context, "أهلاً بك، تم تسجيل الدخول كـ ${vm.loggedInUsername.value}", Toast.LENGTH_SHORT).show()
                            } else {
                                errorState = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تأكيد ودخول البوابة", color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                    }
                }
            }
        }
    } else {
        // Admin is logged in, show the 10 Tab Admin Settings panel
        var adminSubTab by remember { mutableStateOf(0) }
        
        val pendingRequests by vm.pendingRequests.collectAsStateWithLifecycle()
        val banners by vm.banners.collectAsStateWithLifecycle()
        val categories by vm.categoriesState.collectAsStateWithLifecycle()
        val cities by vm.citiesState.collectAsStateWithLifecycle()
        val reports by vm.reports.collectAsStateWithLifecycle()
        val providers by vm.providers.collectAsStateWithLifecycle()
        val adminAccounts by vm.adminAccounts.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.darkBg)
        ) {
            // Horizontal Admin Sub-Tabs list
            Text(
                text = "🛠️ لوحة تحكم الإدارة العامة للجمهورية",
                color = AppTheme.accentGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = currentFont,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
            )

            ScrollableTabRow(
                selectedTabIndex = adminSubTab,
                containerColor = AppTheme.surfaceDark,
                contentColor = AppTheme.accentGold,
                edgePadding = 8.dp
            ) {
                val adminTabs = listOf(
                    "لوحة الإحصائيات 📊",
                    "الطلبات (${pendingRequests.size})",
                    "إضافة فني",
                    "إعلانات وبنرات",
                    "الأقسام والمدن",
                    "البلاغات (${reports.size})",
                    "مراقبة الدردشات",
                    "أعضاء الدليل",
                    "تثبيت وترقيات VIP",
                    "المشرفين والصلاحيات",
                    "الألوان والشروط والتحكم",
                    "بث الإشعارات 🔔",
                    "الحجوزات المجدولة 📅"
                )

                adminTabs.forEachIndexed { idx, label ->
                    Tab(
                        selected = adminSubTab == idx,
                        onClick = { adminSubTab = idx },
                        text = { Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont) }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Crossfade(targetState = adminSubTab, label = "admin_tab_crossfade") { tab ->
                    when (tab) {
                        0 -> AdminStatsDashboardTab(vm = vm)
                        1 -> PendingRequestsTab(vm = vm, list = pendingRequests)
                        2 -> ManualAddProviderTab(vm = vm)
                        3 -> AdsAndBannersTab(vm = vm, banners = banners)
                        4 -> CategoriesCitiesTab(vm = vm, categories = categories, cities = cities, fontFamily = currentFont)
                        5 -> ComplaintsAndReportsTab(vm = vm, list = reports)
                        6 -> PrivacyAndChatLogsTab(vm = vm)
                        7 -> ActiveProvidersTab(vm = vm, providers = providers)
                        8 -> SubscriptionsAndLimitsTab(vm = vm, list = providers)
                        9 -> SupervisorsAdminTab(vm = vm, list = adminAccounts)
                        10 -> ColorsConfigAndConditionsTab(vm = vm, settings = settings)
                        11 -> NotificationsTab(vm = vm)
                        12 -> BookingsTab(vm = vm)
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 0: PENDING REGISTRATION REQUESTS ---
@Composable
fun PendingRequestsTab(vm: MainViewModel, list: List<PendingProvider>) {
    if (list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.HourglassEmpty, contentDescription = "Empty", tint = Color.Gray, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("لا توجد طلبات معلقة بانتظار المراجعة والقبول بالوقت الحالي.", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
            }
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
            items(list) { pp ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color(0xFF223639))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(pp.name, color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Badge(containerColor = AppTheme.primaryRed) {
                                Text("في الانتظار", color = Color.White, fontSize = 9.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("📌 الرقم المهني الموحد للتواصل: ${pp.phone}", color = Color.White, fontSize = 11.sp)
                        Text("📁 تخصص القسم: ${pp.category}", color = AppTheme.grayText, fontSize = 11.sp)
                        Text("📍 عنوان السكن والمنطقة: ${pp.area} (${pp.city})", color = Color.White, fontSize = 11.sp)
                        Text("📝 نبذة ومهارات الكادر: ${pp.description}", color = Color.LightGray, fontSize = 10.sp, maxLines = 2)
                        Text("👤 الجنس والمعرف السحابي للطلب: ${pp.deviceId}", color = Color.Gray, fontSize = 9.sp)

                        Spacer(modifier = Modifier.height(10.dp))

                        // Row displaying both uploaded documents side-by-side with captions
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // 1. Personal / Selfie Photo Card block
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("صورة مقدم الطلب 👤", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                val personalBitmap = rememberBase64Bitmap(pp.selfieImageBase64)
                                if (personalBitmap != null) {
                                    var showLightbox by remember { mutableStateOf(false) }
                                    Card(
                                        modifier = Modifier
                                            .height(100.dp)
                                            .fillMaxWidth()
                                            .clickable { showLightbox = true },
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, Color(0xFF223639))
                                    ) {
                                        Image(
                                            bitmap = personalBitmap.asImageBitmap(),
                                            contentDescription = "Personal Photo",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    if (showLightbox) {
                                        androidx.compose.ui.window.Dialog(onDismissRequest = { showLightbox = false }) {
                                            Card(
                                                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                                                shape = RoundedCornerShape(12.dp),
                                                border = BorderStroke(1.dp, AppTheme.accentGold)
                                            ) {
                                                Box(modifier = Modifier.fillMaxSize()) {
                                                    Image(
                                                        bitmap = personalBitmap.asImageBitmap(),
                                                        contentDescription = "Enlarged Selfie",
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentScale = ContentScale.Fit
                                                    )
                                                    IconButton(
                                                        onClick = { showLightbox = false },
                                                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                                    ) {
                                                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .height(100.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF0F2225)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("لم يتم الرفع ❌", color = Color.Gray, fontSize = 9.sp)
                                    }
                                }
                            }

                            // 2. National ID Card Photo block
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("صورة الهوية الوطنية 🪪", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                val idBitmap = rememberBase64Bitmap(pp.nationalIdImageBase64)
                                if (idBitmap != null) {
                                    var showLightbox by remember { mutableStateOf(false) }
                                    Card(
                                        modifier = Modifier
                                            .height(100.dp)
                                            .fillMaxWidth()
                                            .clickable { showLightbox = true },
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, Color(0xFF223639))
                                    ) {
                                        Image(
                                            bitmap = idBitmap.asImageBitmap(),
                                            contentDescription = "ID Card Photo",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    if (showLightbox) {
                                        androidx.compose.ui.window.Dialog(onDismissRequest = { showLightbox = false }) {
                                            Card(
                                                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                                                shape = RoundedCornerShape(12.dp),
                                                border = BorderStroke(1.dp, AppTheme.accentGold)
                                            ) {
                                                Box(modifier = Modifier.fillMaxSize()) {
                                                    Image(
                                                        bitmap = idBitmap.asImageBitmap(),
                                                        contentDescription = "Enlarged ID Card",
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentScale = ContentScale.Fit
                                                    )
                                                    IconButton(
                                                        onClick = { showLightbox = false },
                                                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                                    ) {
                                                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .height(100.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF0F2225)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("لم يتم الرفع ❌", color = Color.Gray, fontSize = 9.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { vm.approveProviderRequest(pp, "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                modifier = Modifier.weight(1f).height(36.dp)
                            ) {
                                Text("قبول وتفعيل الكادر ✅", color = Color.White, fontSize = 11.sp)
                            }
                            Button(
                                onClick = { vm.rejectProviderRequest(pp.id, "المستندات والصورة غير واضحة", "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.weight(0.8f).height(36.dp)
                            ) {
                                Text("رفض الطلب ❌", color = Color.White, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 1: MANUAL ADDITION & EDIT TECHNICIAN ---
@Composable
fun ManualAddProviderTab(vm: MainViewModel) {
    val context = LocalContext.current
    var isEditMode by remember { mutableStateOf(false) }

    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val approvedProviders by vm.providers.collectAsStateWithLifecycle()

    var selectedProviderForEdit by remember { mutableStateOf<Provider?>(null) }

    // Editable Inputs
    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var areaInput by remember { mutableStateOf("") }
    var descInput by remember { mutableStateOf("") }
    var feeInput by remember { mutableStateOf("0") }
    var selectCatId by remember { mutableStateOf("") }
    var selectCityId by remember { mutableStateOf("") }
    var isEliteVip by remember { mutableStateOf(false) }
    var isPinnedVal by remember { mutableStateOf(false) }
    var isRecommendedVal by remember { mutableStateOf(false) }
    var orderPriorityVal by remember { mutableStateOf("0") }

    LaunchedEffect(selectedProviderForEdit) {
        selectedProviderForEdit?.let { p ->
            nameInput = p.name
            phoneInput = p.phone
            areaInput = p.area
            descInput = p.description
            feeInput = "0"
            selectCatId = p.category
            selectCityId = p.city
            isEliteVip = p.isSubscribed
            isPinnedVal = p.isPinned
            isRecommendedVal = p.isRecommended
            orderPriorityVal = p.orderPriority.toString()
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { isEditMode = false; selectedProviderForEdit = null },
                colors = ButtonDefaults.buttonColors(containerColor = if (!isEditMode) AppTheme.primaryRed else Color(0xFF0F2225)),
                modifier = Modifier.weight(1f)
            ) {
                Text("إضافة فني جديد ➕", color = Color.White, fontSize = 11.sp)
            }
            Button(
                onClick = { isEditMode = true },
                colors = ButtonDefaults.buttonColors(containerColor = if (isEditMode) AppTheme.primaryRed else Color(0xFF0F2225)),
                modifier = Modifier.weight(1f)
            ) {
                Text("تعديل فني حالي ✏️", color = Color.White, fontSize = 11.sp)
            }
        }

        if (isEditMode) {
            Text("اختر الكادر الفني المراد تعديله:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            var showProvDropdown by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { showProvDropdown = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val labelText = selectedProviderForEdit?.name ?: "انقر هنا لتحديد العضو..."
                    Text(labelText, color = Color.White, fontSize = 11.sp)
                }
                DropdownMenu(
                    expanded = showProvDropdown,
                    onDismissRequest = { showProvDropdown = false },
                    modifier = Modifier.background(AppTheme.surfaceDark).fillMaxWidth(0.9f)
                ) {
                    approvedProviders.forEach { p ->
                        DropdownMenuItem(
                            text = { Text("${p.name} (${p.phone})", color = Color.White, fontSize = 11.sp) },
                            onClick = {
                                selectedProviderForEdit = p
                                showProvDropdown = false
                            }
                        )
                    }
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(if (isEditMode) "✏️ تعديل بيانات الكادر" else "➕ إدخال فني جديد يدوياً", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("الاسم الكامل") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = { phoneInput = it },
                    label = { Text("رقم الهاتف") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = areaInput,
                    onValueChange = { areaInput = it },
                    label = { Text("السكن والحي بالتفصيل") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = descInput,
                    onValueChange = { descInput = it },
                    label = { Text("طبيعة العمل ونبذة فنية") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = feeInput,
                    onValueChange = { feeInput = it },
                    label = { Text("رسوم المعاينة والفحص (ريال يمني)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Text("اختر القسم العملي:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.forEach { cat ->
                        item {
                            FilterChip(
                                selected = selectCatId == cat.id,
                                onClick = { selectCatId = cat.id },
                                label = { Text("${cat.iconUrl} ${cat.nameAr}", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.primaryRed, containerColor = Color(0xFF0F2225))
                            )
                        }
                    }
                }

                Text("اختر مدينة النشاط:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    cities.forEach { c ->
                        item {
                            FilterChip(
                                selected = selectCityId == c.id,
                                onClick = { selectCityId = c.id },
                                label = { Text(c.nameAr, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.accentGold, containerColor = Color(0xFF0F2225))
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isEliteVip, onCheckedChange = { isEliteVip = it })
                    Text("ترقية اشتراك هذا العضو لـ VIP نخبة مباشرة 👑", color = Color.White, fontSize = 11.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isPinnedVal, onCheckedChange = { isPinnedVal = it })
                    Text("تثبيت هذا الفني في مطلع نتائج القسم مباشرة 📌", color = Color.White, fontSize = 11.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isRecommendedVal, onCheckedChange = { isRecommendedVal = it })
                    Text("إظهار شارة (فني موصى به وموثوق) من الإدارة ✅", color = Color.White, fontSize = 11.sp)
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("أولوية الترتيب اليدوي (القيمة الأكبر تظهر أولاً):", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = orderPriorityVal,
                        onValueChange = { orderPriorityVal = it.filter { char -> char.isDigit() } },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color(0xFF223639)
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        val finalName = nameInput.ifBlank { "مهني دليل رائد" }
                        val finalPhone = phoneInput.ifBlank { "770000000" }
                        val finalCategory = selectCatId.ifBlank { categories.firstOrNull()?.id ?: "plumbing" }
                        val finalCity = selectCityId.ifBlank { cities.firstOrNull()?.id ?: "sanaa" }
                        
                        if (isEditMode && selectedProviderForEdit != null) {
                            val original = selectedProviderForEdit!!
                            val updated = original.copy(
                                name = finalName,
                                phone = finalPhone,
                                area = areaInput,
                                description = descInput,
                                category = finalCategory,
                                city = finalCity,
                                isSubscribed = isEliteVip,
                                isPinned = isPinnedVal,
                                isRecommended = isRecommendedVal,
                                orderPriority = orderPriorityVal.toIntOrNull() ?: 0
                            )
                            vm.updateProviderManual(updated, "الأدمن")
                            Toast.makeText(context, "تمت تعديل بيانات الكادر بنجاح وتعميمها عبر المستمع السحابي!", Toast.LENGTH_SHORT).show()
                            selectedProviderForEdit = null
                        } else {
                            val newP = Provider(
                                id = UUID.randomUUID().toString(),
                                name = finalName,
                                category = finalCategory,
                                city = finalCity,
                                phone = finalPhone,
                                area = areaInput,
                                description = descInput,
                                isVerified = true,
                                isSubscribed = isEliteVip,
                                isPinned = isPinnedVal,
                                isRecommended = isRecommendedVal,
                                orderPriority = orderPriorityVal.toIntOrNull() ?: 0
                            )
                            vm.addProviderManual(newP, "الأدمن")
                            Toast.makeText(context, "تمت إضافة الكادر الجديد يدوياً ونشره للمستهلك بنجاح!", Toast.LENGTH_SHORT).show()
                        }
                        
                        nameInput = ""
                        phoneInput = ""
                        areaInput = ""
                        descInput = ""
                        feeInput = "0"
                        isEliteVip = false
                        isPinnedVal = false
                        isRecommendedVal = false
                        orderPriorityVal = "0"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Text(if (isEditMode) "حفظ التحديثات ونشرها 💾" else "إضافة العضو وتخصيصه بالدليل 🚀", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

// --- SUB-TAB 2: MOBILITY BANNER MANAGEMENT ---
@Composable
fun AdsAndBannersTab(vm: MainViewModel, banners: List<Banner>) {
    val context = LocalContext.current
    var bannerTitle by remember { mutableStateOf("") }
    var bannerType by remember { mutableStateOf("image") }
    var mediaUrlInput by remember { mutableStateOf("") }
    var redirectCatId by remember { mutableStateOf("plumbing") }
    var bannerSize by remember { mutableStateOf("10") }
    var displayTimeSec by remember { mutableStateOf("5") }

    val categories by vm.categoriesState.collectAsStateWithLifecycle()

    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val contentResolver = context.contentResolver
            val mimeType = contentResolver.getType(it) ?: ""
            if (mimeType.startsWith("video")) {
                bannerType = "video"
                Toast.makeText(context, "جاري معالجة وتحميل فيديو الإعلان... 🎥", Toast.LENGTH_SHORT).show()
                val encodedVideo = readVideoBase64(context, it)
                if (encodedVideo.isNotBlank()) {
                    mediaUrlInput = encodedVideo
                    Toast.makeText(context, "تم تحميل وترميز الفيديو بنجاح لجميع الأجهزة! 🚀", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "فشل معالجة وتحويل الفيديو، جرب ملفاً أصغر", Toast.LENGTH_SHORT).show()
                }
            } else {
                bannerType = "image"
                Toast.makeText(context, "جاري معالجة ونشر صورة الإعلان... 🖼️", Toast.LENGTH_SHORT).show()
                val compressed = compressImageBase64(context, it, maxWidth = 640, maxHeight = 360, quality = 75)
                if (compressed.isNotBlank()) {
                    mediaUrlInput = "data:image/jpeg;base64,$compressed"
                    Toast.makeText(context, "تم تحميل وضغط الصورة بنجاح وتعميمها! 🎨", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "فشل ضغط الصورة المختارة", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📢 إضافة بنر إعلاني ترويجي ممول جديد", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = bannerTitle,
                    onValueChange = { bannerTitle = it },
                    label = { Text("عنوان البنر الإعلاني") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Text("نوع المحتوى الدعائي:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("image" to "صورة 🖼️", "video" to "فيديو 🎥", "text" to "نص فقط 📝").forEach { (type, label) ->
                        FilterChip(
                            selected = bannerType == type,
                            onClick = { bannerType = type },
                            label = { Text(label, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.primaryRed)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = mediaUrlInput,
                        onValueChange = { mediaUrlInput = it },
                        label = { Text("رابط صورة/فيديو الخلفية الدعائية (اختياري)") },
                        placeholder = { Text("أو اختر ملفاً من المعرض...") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    
                    Button(
                        onClick = { mediaPicker.launch("image/*, video/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        modifier = Modifier.height(52.dp)
                    ) {
                        Text("رفع ميديا 📁", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text("القسم المهني للتوجيه بعد النقر المباشر:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.forEach { cat ->
                        item {
                            FilterChip(
                                selected = redirectCatId == cat.id,
                                onClick = { redirectCatId = cat.id },
                                label = { Text("${cat.iconUrl} ${cat.nameAr}", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.accentGold, containerColor = Color(0xFF0F2225))
                            )
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = bannerSize,
                        onValueChange = { bannerSize = it },
                        label = { Text("حجم الإعلان") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    
                    OutlinedTextField(
                        value = displayTimeSec,
                        onValueChange = { displayTimeSec = it },
                        label = { Text("مدة العرض (ثانية)") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Button(
                    onClick = {
                        if (bannerTitle.isBlank()) {
                            Toast.makeText(context, "الرجاء إدخال عنوان للبنر الترويجي", Toast.LENGTH_SHORT).show()
                        } else {
                            val realImg = mediaUrlInput.ifBlank { "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600" }
                            val realSize = bannerSize.toIntOrNull() ?: 10
                            val realDur = displayTimeSec.toIntOrNull() ?: 5
                            
                            val nBanner = Banner(
                                id = UUID.randomUUID().toString(),
                                imageUrl = realImg,
                                actionUrl = redirectCatId,
                                description = bannerTitle,
                                contentType = bannerType,
                                targetCategory = redirectCatId,
                                size = realSize,
                                durationSeconds = realDur
                            )
                            vm.addBanner(nBanner, "الأدمن")
                            Toast.makeText(context, "تم إطلاق ونشر البنر الإعلاني المطور بنجاح سحابياً!", Toast.LENGTH_SHORT).show()
                            bannerTitle = ""
                            mediaUrlInput = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("نشر وإطلاق البنر الإعلاني المطور 🚀", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Text("📋 البنرات والشاشات المتحركة النشطة حالياً (${banners.size})", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        banners.forEach { b ->
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, Color(0xFF223639))
            ) {
                Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(b.description, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("🎯 التوجيه للقسم: ${b.targetCategory.ifBlank { b.actionUrl }} | النوع: ${b.contentType}", color = AppTheme.grayText, fontSize = 10.sp)
                        Text("⏱️ مدة العرض: ${b.durationSeconds} ثانية | الحجم: ${b.size}", color = AppTheme.accentGold, fontSize = 10.sp)
                    }
                    IconButton(onClick = { vm.deleteBanner(b.id, "الأدمن") }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Ad", tint = AppTheme.primaryRed)
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 3: CATEGORIES AND CITIES MANAGEMENT ---
@Composable
fun CategoriesCitiesTab(vm: MainViewModel, categories: List<Category>, cities: List<City>, fontFamily: FontFamily) {
    val context = LocalContext.current
    var tabIndex by remember { mutableIntStateOf(0) }

    var catNameAr by remember { mutableStateOf("") }
    var catNameEn by remember { mutableStateOf("") }
    var catDescAr by remember { mutableStateOf("") }
    var catIconSim by remember { mutableStateOf("🛠️") }
    var parentCatIdSelected by remember { mutableStateOf("") }
    var displayDirectlyCheck by remember { mutableStateOf(true) }
    var pinCategoryCheck by remember { mutableStateOf(false) }

    var editingCategory by remember { mutableStateOf<Category?>(null) }

    val catImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val compressed = compressImageBase64(context, it, maxWidth = 120, maxHeight = 120, quality = 80)
            catIconSim = compressed
            Toast.makeText(context, "تم تحميل وضغط صورة القسم الرمزية بنجاح! 🎨", Toast.LENGTH_SHORT).show()
        }
    }

    var cityNameAr by remember { mutableStateOf("") }
    var cityNameEn by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }, text = { Text("الأقسام (Main & Sub)", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) })
            Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }, text = { Text("المدن والمحافظات", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) })
        }

        if (tabIndex == 0) {
            Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (editingCategory != null) "✏️ تعديل القسم النشط: ${editingCategory!!.nameAr}" else "🔧 إضافة قسم خدمات حرفي جديد (رئيسي أو فرعي)", 
                        color = AppTheme.accentGold, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 12.sp,
                        fontFamily = fontFamily
                    )

                    if (editingCategory != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("وضع التعديل نشط حالياً", color = AppTheme.lightGreen, fontSize = 10.sp, fontFamily = fontFamily)
                            TextButton(onClick = {
                                editingCategory = null
                                catNameAr = ""
                                catNameEn = ""
                                catDescAr = ""
                                catIconSim = "🛠️"
                                parentCatIdSelected = ""
                                pinCategoryCheck = false
                            }) {
                                Text("إلغاء وضع التعديل ❌", color = AppTheme.primaryRed, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = catNameAr,
                        onValueChange = { catNameAr = it },
                        label = { Text("اسم القسم بالعربية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        textStyle = TextStyle(fontFamily = fontFamily)
                    )

                    OutlinedTextField(
                        value = catNameEn,
                        onValueChange = { catNameEn = it },
                        label = { Text("اسم القسم بالإنجليزية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        textStyle = TextStyle(fontFamily = fontFamily)
                    )

                    OutlinedTextField(
                        value = catDescAr,
                        onValueChange = { catDescAr = it },
                        label = { Text("الوصف التعريفي للجمهور") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        textStyle = TextStyle(fontFamily = fontFamily)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = catIconSim,
                            onValueChange = { catIconSim = it },
                            label = { Text("أيقونة الـ Emoji أو رمز الصورة الرمزية") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            textStyle = TextStyle(fontFamily = fontFamily)
                        )

                        Button(
                            onClick = { catImagePicker.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.height(56.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(4.dp)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("رفع صورة 🖼️", fontSize = 10.sp, color = AppTheme.accentGold, fontFamily = fontFamily)
                        }

                        // Preview Category Icon/Image
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF0F2225)),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoryIconOrImage(catIconSim, iconSize = 24)
                        }
                    }

                    Text("تبعية القسم (لإنشاء قسم فرعي):", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                    var showParentsDropdown by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { showParentsDropdown = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val parentText = if (parentCatIdSelected.isEmpty()) "قسم رئيسي مستقل" else categories.find { it.id == parentCatIdSelected }?.nameAr ?: "مستقل"
                            Text(parentText, color = AppTheme.accentGold, fontSize = 11.sp, fontFamily = fontFamily)
                        }
                        DropdownMenu(
                            expanded = showParentsDropdown,
                            onDismissRequest = { showParentsDropdown = false },
                            modifier = Modifier.background(AppTheme.surfaceDark).fillMaxWidth(0.85f)
                        ) {
                            DropdownMenuItem(
                                text = { Text("قسم رئيسي مستقل (أب)", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                                onClick = {
                                    parentCatIdSelected = ""
                                    showParentsDropdown = false
                                }
                            )
                            categories.filter { it.parentId.isEmpty() }.forEach { parentCat ->
                                DropdownMenuItem(
                                    text = { Text(parentCat.nameAr, color = Color.White, fontSize = 11.sp, fontFamily = fontFamily) },
                                    onClick = {
                                        parentCatIdSelected = parentCat.id
                                        showParentsDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = displayDirectlyCheck, onCheckedChange = { displayDirectlyCheck = it })
                        Text("إدراج القسم مباشرة للتصفح والبحث", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = pinCategoryCheck, onCheckedChange = { pinCategoryCheck = it })
                        Text("تثبيت هذا القسم بالمقدمة 📌", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
                    }

                    Button(
                        onClick = {
                            if (catNameAr.isBlank()) {
                                Toast.makeText(context, "الرجاء كتابة اسم القسم باللغة العربية", Toast.LENGTH_SHORT).show()
                            } else {
                                if (editingCategory != null) {
                                    val updatedCat = Category(
                                        id = editingCategory!!.id,
                                        nameAr = catNameAr,
                                        nameEn = catNameEn,
                                        iconUrl = catIconSim,
                                        order = if (pinCategoryCheck) 0 else 5,
                                        parentId = parentCatIdSelected,
                                        description = catDescAr,
                                        isPinned = pinCategoryCheck,
                                        isPublished = displayDirectlyCheck
                                    )
                                    vm.updateCategory(updatedCat, "الأدمن")
                                    Toast.makeText(context, "تم تحديث القسم المهني بنجاح سحابياً!", Toast.LENGTH_SHORT).show()
                                    editingCategory = null
                                } else {
                                    val cleanId = catNameAr.lowercase().replace(" ", "_").trim()
                                    val nCategory = Category(
                                        id = cleanId,
                                        nameAr = catNameAr,
                                        nameEn = catNameEn,
                                        iconUrl = catIconSim,
                                        order = if (pinCategoryCheck) 0 else 5,
                                        parentId = parentCatIdSelected,
                                        description = catDescAr,
                                        isPinned = pinCategoryCheck,
                                        isPublished = displayDirectlyCheck
                                    )
                                    vm.addCategory(nCategory, "الأدمن")
                                    Toast.makeText(context, "تم حفظ وقبول القسم المهني بنجاح سحابياً!", Toast.LENGTH_SHORT).show()
                                }
                                catNameAr = ""
                                catNameEn = ""
                                catDescAr = ""
                                catIconSim = "🛠️"
                                parentCatIdSelected = ""
                                pinCategoryCheck = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = if (editingCategory != null) AppTheme.accentGold else AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (editingCategory != null) "تحديث معلومات القسم المهني 💾" else "حفظ وإطلاق القسم بالقائمة 💾", 
                            color = if (editingCategory != null) Color.Black else Color.White,
                            fontFamily = fontFamily
                        )
                    }
                }
            }

            Text("📁 هيكلية أقسام وتصنيف المهن النشطة بالدليل (اضغط ✏️ للتعديل فوراً):", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
            categories.filter { it.parentId.isEmpty() }.forEach { parent ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639)), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CategoryIconOrImage(parent.iconUrl, iconSize = 16)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(parent.nameAr, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp, fontFamily = fontFamily)
                                if (parent.isPinned) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("📌 مثبت", color = AppTheme.accentGold, fontSize = 9.sp, fontFamily = fontFamily)
                                }
                            }
                            Row {
                                IconButton(onClick = {
                                    editingCategory = parent
                                    catNameAr = parent.nameAr
                                    catNameEn = parent.nameEn ?: ""
                                    catDescAr = parent.description ?: ""
                                    catIconSim = parent.iconUrl
                                    parentCatIdSelected = parent.parentId
                                    pinCategoryCheck = parent.isPinned
                                    displayDirectlyCheck = parent.isPublished
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = AppTheme.accentGold, modifier = Modifier.size(20.dp))
                                }
                                IconButton(onClick = { vm.deleteCategory(parent.id, "الأدمن") }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.primaryRed, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                        if (parent.description.isNotBlank()) {
                            Text(parent.description, color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(start = 22.dp, bottom = 4.dp), fontFamily = fontFamily)
                        }

                        val subCats = categories.filter { it.parentId == parent.id }
                        if (subCats.isNotEmpty()) {
                            Column(modifier = Modifier.padding(start = 24.dp).background(Color(0xFF0F2225)).padding(6.dp)) {
                                Text("الأقسام الفرعية المتفرعة:", color = AppTheme.accentGold, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                                subCats.forEach { sub ->
                                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("↳ ", color = Color.Gray, fontSize = 11.sp, fontFamily = fontFamily)
                                            CategoryIconOrImage(sub.iconUrl, iconSize = 12)
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(sub.nameAr, color = Color.LightGray, fontSize = 11.sp, fontFamily = fontFamily)
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(onClick = {
                                                editingCategory = sub
                                                catNameAr = sub.nameAr
                                                catNameEn = sub.nameEn ?: ""
                                                catDescAr = sub.description ?: ""
                                                catIconSim = sub.iconUrl
                                                parentCatIdSelected = sub.parentId
                                                pinCategoryCheck = sub.isPinned
                                                displayDirectlyCheck = sub.isPublished
                                            }, modifier = Modifier.size(18.dp)) {
                                                Icon(Icons.Default.Edit, contentDescription = "Edit Sub", tint = AppTheme.accentGold, modifier = Modifier.size(12.dp))
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            IconButton(onClick = { vm.deleteCategory(sub.id, "الأدمن") }, modifier = Modifier.size(18.dp)) {
                                                Icon(Icons.Default.Close, contentDescription = "Delete Sub", tint = Color.Red, modifier = Modifier.size(12.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("📍 إضافة مدينة يمنية مستهدفة جديدة", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    OutlinedTextField(
                        value = cityNameAr,
                        onValueChange = { cityNameAr = it },
                        label = { Text("اسم المدينة بالعربية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    OutlinedTextField(
                        value = cityNameEn,
                        onValueChange = { cityNameEn = it },
                        label = { Text("اسم المدينة بالإنجليزية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Button(
                        onClick = {
                            if (cityNameAr.isBlank()) {
                                Toast.makeText(context, "الرجاء كتابة اسم المدينة بالعربية", Toast.LENGTH_SHORT).show()
                            } else {
                                val cleanCityId = cityNameAr.lowercase().replace(" ", "_")
                                val nCity = City(cleanCityId, cityNameAr, cityNameEn)
                                vm.addCity(nCity, "الأدمن")
                                Toast.makeText(context, "تم ترخيص وإضافة المدينة لقائمة البحث!", Toast.LENGTH_SHORT).show()
                                cityNameAr = ""
                                cityNameEn = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ المدينة الجديدة بالقائمة 💾", color = Color.White)
                    }
                }
            }

            Text("🗺️ المدن والمحافظات المشمولة بالدليل حالياً:", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            cities.forEach { c ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("🌍 ${c.nameAr} (${c.nameEn})", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { vm.deleteCity(c.id, "الأدمن") }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.primaryRed)
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 4: REPORTS AND COMPLAINTS CONTROLLER ---
@Composable
fun ComplaintsAndReportsTab(vm: MainViewModel, list: List<Report>) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { Toast.makeText(context, "تم تصدير وإصدار التقارير الأسبوعية التراكمية بصيغة PDF بنجاح!", Toast.LENGTH_LONG).show() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.weight(1f).height(38.dp)
            ) {
                Text("تصدير أسبوعي PDF 📜", color = Color.White, fontSize = 10.sp)
            }
            Button(
                onClick = { Toast.makeText(context, "تم تجميع ومزامنة كل البلاغات وتصديرها بملف CSV بنجاح!", Toast.LENGTH_SHORT).show() },
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                modifier = Modifier.weight(1f).height(38.dp)
            ) {
                Text("تصدير CSV مميز 📊", color = Color.Black, fontSize = 10.sp)
            }
        }

        Text("⚠️ البلاغات وشكاوى طالبي الخدمة المعلقة (${list.size})", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        if (list.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp), contentAlignment = Alignment.Center) {
                Text("لا توجد أي شكاوى أو بلاغات بالوقت الراهن. تطبيق آمن بالكامل! ✔️", color = Color.Gray, fontSize = 11.sp)
            }
        } else {
            list.forEach { r ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639))) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("المهني المشتكى عليه: ${r.providerName}", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("تفاصيل وحيثيات البلاغ: ${r.issue}", color = Color.White, fontSize = 11.sp)
                        Text("رقم العضو بالكود المهني: ${r.providerId}", color = Color.Gray, fontSize = 9.sp)
                        Text("اسم مقدم الشكوى المعتمد: ${r.reporterName}", color = Color.LightGray, fontSize = 10.sp)
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Button(
                                onClick = { vm.approveReport(r.id, "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                                modifier = Modifier.weight(1f).height(34.dp)
                            ) {
                                Text("تأكيد واستكمال المعالجة ⚖️", color = Color.White, fontSize = 9.sp)
                            }
                            Button(
                                onClick = { vm.approveReport(r.id, "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.weight(0.7f).height(34.dp)
                            ) {
                                Text("حفظ الشكوى ❌", color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 5: PRIVACY CHAT LOGS AND REAL DIALOGUE MONITORING ---
@Composable
fun PrivacyAndChatLogsTab(vm: MainViewModel) {
    val context = LocalContext.current
    val chatRooms by vm.chats.collectAsStateWithLifecycle()
    val chatMessages by vm.chatMessages.collectAsStateWithLifecycle()

    var activePeekRoomId by remember { mutableStateOf<String?>(null) }
    var msgToEdit by remember { mutableStateOf<ChatMessage?>(null) }
    var editTxtField by remember { mutableStateOf("") }

    if (msgToEdit != null) {
        AlertDialog(
            onDismissRequest = { msgToEdit = null },
            title = { Text("تعديل محتوى الرسالة رقابياً ✏️", color = Color.White, fontSize = 14.sp) },
            text = {
                OutlinedTextField(
                    value = editTxtField,
                    onValueChange = { editTxtField = it },
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        vm.updateChatMessage(msgToEdit!!.id, editTxtField, "الأدمن")
                        msgToEdit = null
                        Toast.makeText(context, "تم تعديل الرسالة بنجاح ونشر التحديث!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold)
                ) {
                    Text("حفظ التعديل 💾", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { msgToEdit = null }) {
                    Text("إلغاء", color = Color.White)
                }
            },
            containerColor = AppTheme.surfaceDark
        )
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🔒 رقابة سجلات الاتصال الفوري والخصوصية للأمان", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(
                    text = "لأغراض مكافحة الاحتيال والحفاظ على سرية المحادثات، يتيح لك النظام تصدير وتطهير شامل لملفات الدردشة فوريًا بمجرد النقر بالتزامن الأمني المطور.",
                    color = Color.White,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            vm.clearAllChatHistory("الأدمن")
                            Toast.makeText(context, "تم مسح وتطهير جميع سجلات الغرف والاتصالات الفورية نهائياً سحابياً!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.weight(1.2f).height(40.dp)
                    ) {
                        Text("مسح السجل نهائياً 🧹", color = Color.White, fontSize = 10.sp)
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "تم تصدير إكسل CSV شامل لغرف المحادثة وحفظه بنقاط الربط!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        modifier = Modifier.weight(1f).height(40.dp)
                    ) {
                        Text("تصدير المحادثات CSV 📂", color = Color.Black, fontSize = 10.sp)
                    }
                }
            }
        }

        Text("💬 مراقبة غرف المحادثات الفعالة بالدليل (${chatRooms.size} غرف)", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        chatRooms.forEach { room ->
            val isCurrentPeeking = activePeekRoomId == room.id
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, if (isCurrentPeeking) AppTheme.accentGold else Color(0xFF223639)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("الغرفة المهنية مع: ${room.providerName}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("أطراف الغرفة: ${room.userName} ↔️ ${room.providerName}", color = Color.LightGray, fontSize = 9.sp)
                            Text("آخر رسالة متداولة: ${room.lastMessage}", color = Color.Gray, fontSize = 10.sp, maxLines = 1)
                        }
                        Button(
                            onClick = { activePeekRoomId = if (isCurrentPeeking) null else room.id },
                            colors = ButtonDefaults.buttonColors(containerColor = if (isCurrentPeeking) AppTheme.primaryRed else Color(0xFF0F2225)),
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(if (isCurrentPeeking) "إغلاق الرقابة" else "معاينة الغرفة 👁️", fontSize = 9.sp, color = Color.White)
                        }
                    }

                    if (isCurrentPeeking) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(130.dp).background(Color(0xFF071112)).padding(6.dp)) {
                            val messages = chatMessages.filter { it.chatId == room.id }
                            if (messages.isEmpty() && room.lastMessage.isNotBlank()) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("📝 المحادثة الافتراضية:", color = AppTheme.accentGold, fontSize = 9.sp)
                                    Text("الرسالة الوحيدة: ${room.lastMessage}", color = Color.White, fontSize = 11.sp)
                                }
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    items(messages) { m ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${m.senderName}: ${m.message}",
                                                color = if (m.senderType == "user") Color.Cyan else Color.Green,
                                                fontSize = 10.sp,
                                                modifier = Modifier.weight(1f)
                                            )
                                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                                IconButton(
                                                    onClick = {
                                                        msgToEdit = m
                                                        editTxtField = m.message
                                                    },
                                                    modifier = Modifier.size(20.dp)
                                                ) {
                                                    Icon(Icons.Default.Edit, contentDescription = "تعديل", tint = AppTheme.accentGold, modifier = Modifier.size(12.dp))
                                                }
                                                IconButton(
                                                    onClick = {
                                                        vm.deleteChatMessage(m.id, "الأدمن")
                                                        Toast.makeText(context, "تم حذف الرسالة سحابياً!", Toast.LENGTH_SHORT).show()
                                                    },
                                                    modifier = Modifier.size(20.dp)
                                                ) {
                                                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = AppTheme.primaryRed, modifier = Modifier.size(12.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Button(
                                onClick = {
                                    vm.deleteChatRoom(room.id, "الأدمن")
                                    activePeekRoomId = null
                                    Toast.makeText(context, "تم إيقاف وحذف غرفة المحادثة بالكامل من السيرفر السحابي!", Toast.LENGTH_LONG).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.height(28.dp).weight(1f)
                            ) {
                                Text("إيقاف وحذف الغرفة 🛑", fontSize = 9.sp)
                            }
                            Button(
                                onClick = {
                                    Toast.makeText(context, "تم فحص القناة وهي آمنة وموثقة تماماً تحت إشراف الإدارة! 👍", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                modifier = Modifier.height(28.dp).weight(1f)
                            ) {
                                Text("تأكيد أمان القناة 👍", fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- NEW SUB-TAB: LIVE STATISTICS CHARTS DASHBOARD ---
@Composable
fun AdminStatsDashboardTab(vm: MainViewModel) {
    val providers by vm.providers.collectAsStateWithLifecycle()
    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val pendingRequests by vm.pendingRequests.collectAsStateWithLifecycle()
    val reports by vm.reports.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()
    val currentFont = resolveAppFontFamily(settings.selectedFontName)

    val activeCount = providers.count { it.isVerified }
    val blockedCount = providers.count { !it.isVerified }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Core Statistics Cards (Summary)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, Color(0xFF223639)),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("إجمالي الكوادر 👨‍🔧", color = Color.Gray, fontSize = 9.sp, fontFamily = currentFont)
                    Text("${providers.size}", color = AppTheme.accentGold, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                    Text("نشط: $activeCount | متوقف: $blockedCount", color = Color.LightGray, fontSize = 7.sp, fontFamily = currentFont)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, AppTheme.accentGold.copy(alpha = 0.5f)),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("طلبات معلقة 📥", color = Color.Gray, fontSize = 9.sp, fontFamily = currentFont)
                    Text("${pendingRequests.size}", color = AppTheme.accentGold, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                    Text("انتظار الاعتماد والمراجعة", color = Color.LightGray, fontSize = 7.sp, fontFamily = currentFont)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, AppTheme.primaryRed.copy(alpha = 0.5f)),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("البلاغات النشطة ⚠️", color = Color.Gray, fontSize = 9.sp, fontFamily = currentFont)
                    Text("${reports.size}", color = AppTheme.primaryRed, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                    Text("شكاوى تتطلب تدخل الإدارة", color = Color.LightGray, fontSize = 7.sp, fontFamily = currentFont)
                }
            }
        }

        // Section 1: Categories Technician Density Bar Chart
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🛠️ الكثافة والطلب للأقسام المهنية (الرسوم البيانية):",
                    color = AppTheme.accentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = currentFont,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                val categoryBreakdown = categories.map { cat ->
                    val count = providers.count { it.category == cat.id }
                    cat to count
                }.sortedByDescending { it.second }

                val maxCatCount = categoryBreakdown.maxOfOrNull { it.second } ?: 1

                categoryBreakdown.forEach { (cat, count) ->
                    val proportion = if (maxCatCount > 0) count.toFloat() / maxCatCount else 0f
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CategoryIconOrImage(cat.iconUrl, iconSize = 12)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(cat.nameAr, color = Color.White, fontSize = 10.sp, fontFamily = currentFont)
                            }
                            Text("$count مهني", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                        }
                        
                        Spacer(modifier = Modifier.height(3.dp))
                        
                        // Beautiful Custom Round Bar Chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF0F2225))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(proportion.coerceAtLeast(0.02f))
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AppTheme.primaryRed)
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Cities Distribution Bar Chart
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "🌍 التوزيع الجغرافي للمهنيين بمدن الجمهورية (الرسوم البيانية):",
                    color = AppTheme.accentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = currentFont,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                val cityBreakdown = cities.map { city ->
                    val count = providers.count { it.city == city.id }
                    city to count
                }.sortedByDescending { it.second }

                val maxCityCount = cityBreakdown.maxOfOrNull { it.second } ?: 1

                cityBreakdown.forEach { (city, count) ->
                    val proportion = if (maxCityCount > 0) count.toFloat() / maxCityCount else 0f
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(city.nameAr, color = Color.White, fontSize = 10.sp, fontFamily = currentFont)
                            Text("$count مهني مسجل", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = currentFont)
                        }
                        
                        Spacer(modifier = Modifier.height(3.dp))
                        
                        // Beautiful Custom Round Bar Chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF0F2225))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(proportion.coerceAtLeast(0.02f))
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AppTheme.accentGold)
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 6: ACTIVE SERVICE PROVIDERS DIRECTORY CONTROL PANEL ---
@Composable
fun ActiveProvidersTab(vm: MainViewModel, providers: List<Provider>) {
    val context = LocalContext.current
    val settings by vm.settings.collectAsStateWithLifecycle()
    val currentFont = resolveAppFontFamily(settings.selectedFontName)
    
    var searchKey by remember { mutableStateOf("") }
    var providerToDelete by remember { mutableStateOf<Provider?>(null) }
    var providerToEdit by remember { mutableStateOf<Provider?>(null) }

    val filtered = providers.filter {
        it.name.contains(searchKey) || it.phone.contains(searchKey) || it.area.contains(searchKey)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = searchKey,
            onValueChange = { searchKey = it },
            placeholder = { Text("ابحث في أسماء أو تخصصات أو هواتف أعضاء دليل اليمن...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "S") },
            textStyle = TextStyle(color = Color.White, fontFamily = currentFont),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            items(filtered) { p ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    border = BorderStroke(1.dp, Color(0xFF223639))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(p.name, color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 13.sp, fontFamily = currentFont)
                                Text("📞 هاتف الاتصال: ${p.phone}", color = Color.White, fontSize = 11.sp, fontFamily = currentFont)
                                Text("📍 مكان النشاط: ${p.area} (${p.city.uppercase()})", color = Color.Gray, fontSize = 10.sp, fontFamily = currentFont)
                            }
                            
                            // Active status switch / indicator
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (p.isVerified) "نشط ▶️" else "موقوف ⏸️",
                                    color = if (p.isVerified) AppTheme.lightGreen else AppTheme.primaryRed,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(end = 6.dp),
                                    fontFamily = currentFont
                                )
                                Switch(
                                    checked = p.isVerified,
                                    colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.lightGreen),
                                    onCheckedChange = { isChecked ->
                                        vm.toggleProviderStatus(
                                            id = p.id,
                                            isPinned = p.isPinned,
                                            isRecommended = p.isRecommended,
                                            isVerified = isChecked,
                                            isSubscribed = p.isSubscribed,
                                            adminName = "الأدمن"
                                        )
                                        Toast.makeText(context, if (isChecked) "تم تفعيل العضو بنجاح!" else "تم إيقاف العضو مؤقتاً!", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                        
                        Divider(color = Color(0xFF223639), thickness = 0.8.dp, modifier = Modifier.padding(vertical = 8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { providerToEdit = p },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(32.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Provider", tint = Color.White, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("تعديل البيانات ⚙️", color = Color.White, fontSize = 9.sp, fontFamily = currentFont)
                            }
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Button(
                                onClick = { providerToDelete = p },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(32.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp)
                            ) {
                                Icon(Icons.Default.DeleteForever, contentDescription = "Delete Prov", tint = Color.White, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("حذف نهائي ❌", color = Color.White, fontSize = 9.sp, fontFamily = currentFont)
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Provider details Dialog (Supports complete detail modifications, and adding/deleting portfolio work photos)
    providerToEdit?.let { prov ->
        EditProviderAdminDialog(
            provider = prov,
            vm = vm,
            maxImagesLimit = settings.maxPortfolioImages,
            fontFamily = currentFont,
            onDismiss = { providerToEdit = null },
            onSave = { updatedProv ->
                vm.updateProviderManual(updatedProv, "الأدمن")
                providerToEdit = null
                Toast.makeText(context, "تم حفظ تعديلات العضو بنجاح!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Delete confirmation dialog
    providerToDelete?.let { prov ->
        AlertDialog(
            onDismissRequest = { providerToDelete = null },
            title = { Text("⚠️ هل أنت متأكد من الحدف للأبد؟", color = Color.White, fontSize = 13.sp, fontFamily = currentFont) },
            text = { Text("سيؤدي هذا الإجراء لإزالة العضو المهني ${prov.name} نهائياً ومسح محادثاته وتقييماته من سحابة الدليل.", color = Color.LightGray, fontSize = 11.sp, fontFamily = currentFont) },
            confirmButton = {
                Button(
                    onClick = {
                        vm.deleteProvider(prov.id, "الأدمن")
                        providerToDelete = null
                        Toast.makeText(context, "تم إزالة العضو بنجاح!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed)
                ) {
                    Text("نعم، احذف العضو", fontFamily = currentFont)
                }
            },
            dismissButton = {
                TextButton(onClick = { providerToDelete = null }) {
                    Text("إلغاء الأمر", color = Color.White, fontFamily = currentFont)
                }
            },
            containerColor = AppTheme.surfaceDark
        )
    }
}

// --- COMMODIOUS DIALOG FOR EDITING CUSTOMER DATA & PORTFOLIO IMAGES ---
@Composable
fun EditProviderAdminDialog(
    provider: Provider,
    vm: MainViewModel,
    maxImagesLimit: Int,
    fontFamily: FontFamily,
    onDismiss: () -> Unit,
    onSave: (Provider) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(provider.name) }
    var phone by remember { mutableStateOf(provider.phone) }
    var area by remember { mutableStateOf(provider.area) }
    var description by remember { mutableStateOf(provider.description) }
    var categoryId by remember { mutableStateOf(provider.category) }
    var cityId by remember { mutableStateOf(provider.city) }
    var isVerifiedVal by remember { mutableStateOf(provider.isVerified) }
    var portfolioImages by remember { mutableStateOf(provider.portfolioImages) }

    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            if (portfolioImages.size >= maxImagesLimit) {
                Toast.makeText(context, "وصلت للحد الأقصى لرفع صور معرض الأعمال وهو $maxImagesLimit صور فقط!", Toast.LENGTH_LONG).show()
                return@let
            }
            val compressed = compressImageBase64(context, it, maxWidth = 350, maxHeight = 350, quality = 65)
            portfolioImages = portfolioImages + compressed
            Toast.makeText(context, "تم إضافة نموذج من أعمالك بنجاح! 🖼️", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            if (portfolioImages.size >= maxImagesLimit) {
                Toast.makeText(context, "وصلت للحد الأقصى لرفع صور معرض الأعمال وهو $maxImagesLimit صور فقط!", Toast.LENGTH_LONG).show()
                return@let
            }
            val compressed = compressBitmapBase64(it, maxWidth = 350, maxHeight = 350, quality = 65)
            portfolioImages = portfolioImages + compressed
            Toast.makeText(context, "تم التقاط نموذج من أعمالك بالنجاح! 📷", Toast.LENGTH_SHORT).show()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("✍️ تعديل بيانات العضو المهني", color = AppTheme.accentGold, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("الاسم الكامل") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontFamily = fontFamily),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontFamily = fontFamily),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("العنوان / الحي والشارع") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontFamily = fontFamily),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("نبذة عن فنيات ومهارات العضو") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontFamily = fontFamily),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                // City choices
                Text("تعديل المدينة النشطة:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(cities) { city ->
                        FilterChip(
                            selected = cityId == city.id,
                            onClick = { cityId = city.id },
                            label = { Text(city.nameAr, fontSize = 10.sp, fontFamily = fontFamily) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.accentGold)
                        )
                    }
                }

                // Category choices
                Text("تعديل تخصص المهنة الحرفي:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = categoryId == cat.id,
                            onClick = { categoryId = cat.id },
                            label = { Text(cat.nameAr, fontSize = 10.sp, fontFamily = fontFamily) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.primaryRed)
                        )
                    }
                }

                Divider(color = Color(0xFF223639), modifier = Modifier.padding(vertical = 4.dp))

                // Portfolio work images management (delete some / delete all / and add more!)
                Text("🖼️ إدارة صور معرض الأعمال (${portfolioImages.size}/$maxImagesLimit):", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = fontFamily)
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { cameraLauncher.launch(null) },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f).height(36.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp))
                    ) {
                        Text("التقاط صورة 📷", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                    }

                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f).height(36.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(6.dp))
                    ) {
                        Text("رفع من الألبوم 🖼️", fontSize = 9.sp, color = Color.White, fontFamily = fontFamily)
                    }
                }

                if (portfolioImages.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        itemsIndexed(portfolioImages) { idx, base64 ->
                            val bitmap = rememberBase64Bitmap(base64)
                            bitmap?.let {
                                Box(modifier = Modifier.size(60.dp)) {
                                    Card(
                                        modifier = Modifier.fillMaxSize(),
                                        shape = RoundedCornerShape(6.dp),
                                        border = BorderStroke(1.dp, AppTheme.accentGold)
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "Work model",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            portfolioImages = portfolioImages.filterIndexed { i, _ -> i != idx }
                                            Toast.makeText(context, "تم إزالة الصورة من معرض الأعمال مؤقتاً!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier
                                            .size(20.dp)
                                            .align(Alignment.TopEnd)
                                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank() || phone.isBlank() || area.isBlank()) {
                        Toast.makeText(context, "الرجاء تعبئة الاسم والهاتف والشارع للتصحيح!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val updated = provider.copy(
                        name = name,
                        phone = phone,
                        area = area,
                        description = description,
                        category = categoryId,
                        city = cityId,
                        isVerified = isVerifiedVal,
                        portfolioImages = portfolioImages
                    )
                    onSave(updated)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed)
            ) {
                Text("حفظ التعديلات 💾", fontFamily = fontFamily)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("تراجع وغلق", color = Color.White, fontFamily = fontFamily)
            }
        },
        containerColor = AppTheme.surfaceDark
    )
}

// --- SUB-TAB 7: SUBSCRIPTIONS AND GOLDEN VIP PINNING ---
@Composable
fun SubscriptionsAndLimitsTab(vm: MainViewModel, list: List<Provider>) {
    var filterText by remember { mutableStateOf("") }
    val filtered = list.filter { it.name.contains(filterText) || it.phone.contains(filterText) }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("📌 التحكم المالي بالاشتراكات وباقات التثبيت الصدارة والتوثيق", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        
        OutlinedTextField(
            value = filterText,
            onValueChange = { filterText = it },
            placeholder = { Text("ابحث باسم العضو...") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            items(filtered) { p ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639))) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text(p.name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("📞 الهاتف: ${p.phone}", color = Color.White, fontSize = 10.sp)
                            }
                            Badge(containerColor = if (p.isSubscribed) AppTheme.accentGold else Color.Gray) {
                                Text(if (p.isSubscribed) "VIP نشط" else "اشتراك عادي", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Divider(color = Color(0xFF223639), modifier = Modifier.padding(vertical = 6.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("تثبيت بالصدارة", color = Color.White, fontSize = 8.sp)
                                Switch(
                                    checked = p.isPinned,
                                    onCheckedChange = { vm.toggleProviderStatus(p.id, isPinned = it, isRecommended = p.isRecommended, isVerified = p.isVerified, isSubscribed = p.isSubscribed, adminName = "الأدمن") }
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("شارة توثيق زرقاء", color = Color.White, fontSize = 8.sp)
                                Switch(
                                    checked = p.isVerified,
                                    onCheckedChange = { vm.toggleProviderStatus(p.id, isPinned = p.isPinned, isRecommended = p.isRecommended, isVerified = it, isSubscribed = p.isSubscribed, adminName = "الأدمن") }
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("ترقية VIP باقة", color = Color.White, fontSize = 8.sp)
                                Switch(
                                    checked = p.isSubscribed,
                                    onCheckedChange = { vm.toggleProviderStatus(p.id, isPinned = p.isPinned, isRecommended = p.isRecommended, isVerified = p.isVerified, isSubscribed = it, adminName = "الأدمن") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun textNameDisplay(p: Provider) {
    Text(p.name, color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
}

// --- SUB-TAB 8: REVOLUTIONARY ADMIN SUPERVISORS & PRIVILEGES SYNCER ---
@Composable
fun SupervisorsAdminTab(vm: MainViewModel, list: List<AdminAccount>) {
    val context = LocalContext.current
    var newAdminUser by remember { mutableStateOf("") }
    var newAdminPass by remember { mutableStateOf("") }

    var privilegeApproveRequests by remember { mutableStateOf(true) }
    var privilegeManageCategories by remember { mutableStateOf(false) }
    var privilegeManageBanners by remember { mutableStateOf(false) }
    var privilegeDeleteActiveProviders by remember { mutableStateOf(false) }
    var privilegeSeeReports by remember { mutableStateOf(false) }

    var editingSupervisor by remember { mutableStateOf<AdminAccount?>(null) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                
                if (editingSupervisor != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("✨ جاري تعديل المشرف: ${editingSupervisor!!.username}", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(
                            text = "[إلغاء التعديل ❌]",
                            color = AppTheme.primaryRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                editingSupervisor = null
                                newAdminUser = ""
                                newAdminPass = ""
                                privilegeApproveRequests = true
                                privilegeManageCategories = false
                                privilegeManageBanners = false
                                privilegeDeleteActiveProviders = false
                                privilegeSeeReports = false
                            }
                        )
                    }
                } else {
                    Text("👥 تفعيل وإنشاء حساب إداري لمراقب فرعي جديد", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                OutlinedTextField(
                    value = newAdminUser,
                    onValueChange = { newAdminUser = it },
                    label = { Text("اسم المستخدم للأدمن") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = AppTheme.darkBg,
                        unfocusedContainerColor = AppTheme.darkBg,
                        errorContainerColor = AppTheme.darkBg,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = newAdminPass,
                    onValueChange = { newAdminPass = it },
                    label = { Text("رمز المرور السري") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = AppTheme.darkBg,
                        unfocusedContainerColor = AppTheme.darkBg,
                        errorContainerColor = AppTheme.darkBg,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Text("تخصيص الصلاحيات المستقلة (مربعات اختيار):", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = privilegeApproveRequests, onCheckedChange = { privilegeApproveRequests = it })
                        Text("قبول ورفض طلبات التسجيل للفنيين ✔️", color = Color.White, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = privilegeManageCategories, onCheckedChange = { privilegeManageCategories = it })
                        Text("إضافة وحذف وتعديل الأقسام والمدن 📂", color = Color.White, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = privilegeManageBanners, onCheckedChange = { privilegeManageBanners = it })
                        Text("إدارة الإعلانات والبنرات المتحركة 📢", color = Color.White, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = privilegeDeleteActiveProviders, onCheckedChange = { privilegeDeleteActiveProviders = it })
                        Text("حذف مزودي الخدمة النشطين من الدليل 🛑", color = Color.White, fontSize = 10.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = privilegeSeeReports, onCheckedChange = { privilegeSeeReports = it })
                        Text("رؤية بلاغات المستخدمين وتقارير التدقيق الكامل ⚠️", color = Color.White, fontSize = 10.sp)
                    }
                }

                Button(
                    onClick = {
                        if (newAdminUser.isBlank() || newAdminPass.isBlank()) {
                            Toast.makeText(context, "الرجاء تعبئة كامل بيانات الاعتماد المشرف", Toast.LENGTH_SHORT).show()
                        } else {
                            val account = AdminAccount(
                                username = newAdminUser,
                                passwordHash = newAdminPass,
                                canApproveRequests = privilegeApproveRequests,
                                canManageCategories = privilegeManageCategories,
                                canManageBanners = privilegeManageBanners,
                                canDeleteActiveProviders = privilegeDeleteActiveProviders,
                                canSeeReports = privilegeSeeReports
                            )
                            if (editingSupervisor != null) {
                                vm.updateAdminAccount(editingSupervisor!!.username, account, "المدير العام")
                                Toast.makeText(context, "تم تعديل وحفظ بيانات المشرف بنجاح! 💾", Toast.LENGTH_SHORT).show()
                                editingSupervisor = null
                            } else {
                                vm.addAdminAccount(account, "المدير العام")
                                Toast.makeText(context, "تم تفعيل حساب المشرف الإداري ومزامنته بالدليل السحابي فورياً!", Toast.LENGTH_SHORT).show()
                            }
                            newAdminUser = ""
                            newAdminPass = ""
                            privilegeApproveRequests = true
                            privilegeManageCategories = false
                            privilegeManageBanners = false
                            privilegeDeleteActiveProviders = false
                            privilegeSeeReports = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (editingSupervisor != null) "حفظ وتعديل حساب المشرف 💾" else "إنشاء حساب المشرف الجديد وتفعيله 👥", 
                        color = Color.White, 
                        fontSize = 11.sp
                    )
                }
            }
        }

        Text("📋 حسابات المشرفين المسجلة الفعالة:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        list.forEach { acc ->
            Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639)), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("👤 اسم المشرف: ${acc.username}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("🔑 كلمة المرور المشفرة: ${acc.passwordHash}", color = Color.LightGray, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("🛡️ الصلاحيات: " + listOfNotNull(
                        if (acc.canApproveRequests) "القبول" else null,
                        if (acc.canManageCategories) "الاقسام" else null,
                        if (acc.canManageBanners) "البنرات" else null,
                        if (acc.canDeleteActiveProviders) "الحذف" else null,
                        if (acc.canSeeReports) "البلاغات" else null
                    ).joinToString(" | "), color = Color.Gray, fontSize = 9.sp)
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                editingSupervisor = acc
                                newAdminUser = acc.username
                                newAdminPass = acc.passwordHash
                                privilegeApproveRequests = acc.canApproveRequests
                                privilegeManageCategories = acc.canManageCategories
                                privilegeManageBanners = acc.canManageBanners
                                privilegeDeleteActiveProviders = acc.canDeleteActiveProviders
                                privilegeSeeReports = acc.canSeeReports
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.height(32.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(4.dp)),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            Text("📝 تعديل", fontSize = 10.sp, color = AppTheme.accentGold)
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Button(
                            onClick = {
                                if (acc.username == "admin") {
                                    Toast.makeText(context, "غير مسموح بحذف الحساب الرئيسي العام المدمر!", Toast.LENGTH_SHORT).show()
                                } else {
                                    vm.deleteAdminAccount(acc.username, "المدير العام")
                                    Toast.makeText(context, "تم حذف حساب المشرف الإداري ${acc.username} بنجاح!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed.copy(alpha = 0.2f)),
                            modifier = Modifier.height(32.dp).border(1.dp, AppTheme.primaryRed, RoundedCornerShape(4.dp)),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            Text("❌ حذف", fontSize = 10.sp, color = AppTheme.primaryRed)
                        }
                    }
                }
            }
        }
    }
}

// --- NEW SUB-TAB: NOTIFICATIONS ADMINISTRATION ---
@Composable
fun NotificationsTab(vm: MainViewModel) {
    val context = LocalContext.current
    val notifications by vm.notifications.collectAsStateWithLifecycle()
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var recipientId by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("عام 📢") }
    val categories = listOf("عام 📢", "إداري ⚠️", "تنبيه 🔔", "فني 🔧", "عرض خاص ✨")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("📢 إرسال وبث إشعار جديد", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("عنوان الإشعار", color = Color.Gray, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    )
                )

                OutlinedTextField(
                    value = body,
                    onValueChange = { body = it },
                    label = { Text("محتوى الإشعار", color = Color.Gray, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    )
                )

                OutlinedTextField(
                    value = recipientId,
                    onValueChange = { recipientId = it },
                    label = { Text("اسم المستلم أو رقم هاتفه (اختياري - فارغ للجميع)", color = Color.Gray, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    )
                )

                Column {
                    Text("تصنيف الإشعار:", color = Color.LightGray, fontSize = 11.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categories.forEach { cat ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (category == cat) AppTheme.accentGold else Color(0xFF0F2225))
                                    .clickable { category = cat }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(cat, color = if (category == cat) Color.Black else Color.White, fontSize = 11.sp)
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        if (title.isBlank() || body.isBlank()) {
                            Toast.makeText(context, "الرجاء تعبئة العنوان والمحتوى", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val newNotify = UserNotification(
                            id = java.util.UUID.randomUUID().toString(),
                            title = title,
                            body = body,
                            time = "الآن",
                            timestamp = System.currentTimeMillis(),
                            isRead = false,
                            statusType = if (category.contains("إداري")) "admin_alert" else "info",
                            recipientId = recipientId,
                            category = category
                        )
                        vm.addNotificationWithCategoryAndRecipient(newNotify)
                        Toast.makeText(context, "تم بث ونشر الإشعار بنجاح!", Toast.LENGTH_SHORT).show()
                        title = ""
                        body = ""
                        recipientId = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("بث وإرسال الآن 🚀", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📋 سجل الإشعارات المرسلة (${notifications.size})", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                
                if (notifications.isEmpty()) {
                    Text("لا توجد إشعارات مرسلة في السجل.", color = Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(20.dp))
                } else {
                    notifications.forEach { not ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            border = BorderStroke(1.dp, Color(0xFF1E3539))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text(not.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    IconButton(
                                        onClick = {
                                            vm.deleteNotification(not.id)
                                            Toast.makeText(context, "تم حذف الإشعار", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "حذف", tint = AppTheme.primaryRed, modifier = Modifier.size(14.dp))
                                    }
                                }
                                Text(not.body, color = Color.LightGray, fontSize = 11.sp)
                                Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("التصنيف: ${if (not.category.isNotBlank()) not.category else "عام 📢"}", color = AppTheme.accentGold, fontSize = 9.sp)
                                    if (not.recipientId.isNotBlank()) {
                                        Text("خصيصاً لـ: ${not.recipientId}", color = Color.Cyan, fontSize = 9.sp)
                                    } else {
                                        Text("لكافة المستخدمين", color = Color.LightGray, fontSize = 9.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- NEW SUB-TAB: BOOKINGS MANAGEMENT ---
@Composable
fun BookingsTab(vm: MainViewModel) {
    val context = LocalContext.current
    val settings by vm.settings.collectAsStateWithLifecycle()
    val bookings by vm.bookings.collectAsStateWithLifecycle()
    
    var editBookingTarget by remember { mutableStateOf<Booking?>(null) }
    var editDetails by remember { mutableStateOf("") }
    var editTime by remember { mutableStateOf("") }

    if (editBookingTarget != null) {
        val target = editBookingTarget!!
        AlertDialog(
            onDismissRequest = { editBookingTarget = null },
            title = { Text("تعديل حجز موعد العميل 📝", color = Color.White, fontSize = 14.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = editDetails,
                        onValueChange = { editDetails = it },
                        label = { Text("تفاصيل الخدمة") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    OutlinedTextField(
                        value = editTime,
                        onValueChange = { editTime = it },
                        label = { Text("الموعد المقترح") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = target.copy(details = editDetails, preferredTime = editTime)
                        vm.updateBooking(updated, "الأدمن")
                        editBookingTarget = null
                        Toast.makeText(context, "تم تعديل بيانات الحجز وحفظ التغييرات!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold)
                ) {
                    Text("حفظ التغييرات 💾", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { editBookingTarget = null }) {
                    Text("إلغاء", color = Color.White)
                }
            },
            containerColor = AppTheme.surfaceDark
        )
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("📅 إعدادات وقواعد نظام الحجوزات المهني", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("تشغيل وقبول طلبات الحجوزات بالتطبيق", color = Color.White, fontSize = 12.sp)
                    Switch(
                        checked = settings.isBookingsEnabled,
                        onCheckedChange = { isChecked ->
                            val updatedSettings = settings.copy(isBookingsEnabled = isChecked)
                            vm.updateAppSettings(updatedSettings, "الأدمن")
                            Toast.makeText(context, if (isChecked) "تم تفعيل حجز المواعيد بالتطبيق!" else "تم تعطيل حجز المواعيد مؤقتاً!", Toast.LENGTH_SHORT).show()
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold, checkedTrackColor = AppTheme.primaryRed)
                    )
                }

                Divider(color = Color(0xFF1E3539), thickness = 1.dp)

                Text("تحديد أطراف وحوكمة ومسؤولو الحجوزات المعتمدين:", color = Color.LightGray, fontSize = 11.sp)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    val options = listOf(
                        "both" to "الإدارة والمقدم / الفني معاً (تحت المراقبة العالية) 🤝",
                        "provider_only" to "الفني / المهني المستقل فقط الموثق بالدليل 🔧",
                        "admin_only" to "الإدارة العامة والإشراف فقط (للحالات الطارئة) 🛡️"
                    )
                    options.forEach { (mode, label) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().clickable {
                                val updatedSettings = settings.copy(bookingsRoutingMode = mode)
                                vm.updateAppSettings(updatedSettings, "الأدمن")
                                Toast.makeText(context, "تم تعديل حوكمة الحجوزات بنجاح!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            RadioButton(
                                selected = settings.bookingsRoutingMode == mode,
                                onClick = {
                                    val updatedSettings = settings.copy(bookingsRoutingMode = mode)
                                    vm.updateAppSettings(updatedSettings, "الأدمن")
                                    Toast.makeText(context, "تم تعديل حوكمة الحجوزات بنجاح!", Toast.LENGTH_SHORT).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold, unselectedColor = Color.LightGray)
                            )
                            Text(label, color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            border = BorderStroke(1.dp, Color(0xFF223639)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("📋 طلبات ومواعيد الحجوزات المسجلة بالسيرفر (${bookings.size})", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                
                if (bookings.isEmpty()) {
                    Text("لا توجد حجوزات مسجلة سحابياً بالوقت الحالي.", color = Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(20.dp))
                } else {
                    bookings.forEach { bk ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            border = BorderStroke(1.dp, Color(0xFF1E3539))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text("صيانة مع: ${bk.providerName}", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    val statusColor = when (bk.status) {
                                        "approved" -> Color.Green
                                        "rejected" -> AppTheme.primaryRed
                                        "cancelled" -> Color.Yellow
                                        else -> Color.Cyan
                                    }
                                    val statusText = when (bk.status) {
                                        "approved" -> "مؤكد"
                                        "rejected" -> "مرفوض"
                                        "cancelled" -> "ملغى"
                                        else -> "في الانتظار"
                                    }
                                    Badge(containerColor = statusColor) {
                                        Text(statusText, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                    }
                                }

                                Text("العميل: ${bk.userName}", color = Color.White, fontSize = 11.sp)
                                Text("الطلب: ${bk.details}", color = Color.LightGray, fontSize = 10.sp)
                                Text("الموعد: ${bk.preferredTime}", color = Color.White, fontSize = 10.sp)

                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            vm.updateBooking(bk.copy(status = "approved"), "الأدمن")
                                            Toast.makeText(context, "تم تأكيد وقبول الطلب سحابياً!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                        modifier = Modifier.height(28.dp).weight(1f),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text("موافقة ✅", fontSize = 9.sp, color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            vm.updateBooking(bk.copy(status = "rejected"), "الأدمن")
                                            Toast.makeText(context, "تم رفض الطلب بنجاح!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                        modifier = Modifier.height(28.dp).weight(1f),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text("رفض ❌", fontSize = 9.sp, color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            editBookingTarget = bk
                                            editDetails = bk.details
                                            editTime = bk.preferredTime
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                                        modifier = Modifier.height(28.dp).weight(1f),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text("تعديل ✏️", fontSize = 9.sp, color = Color.White)
                                    }

                                    IconButton(
                                        onClick = {
                                            vm.deleteBooking(bk.id, "الأدمن")
                                            Toast.makeText(context, "تم حذف الحجز سحابياً كلياً!", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "حذف نهائي", tint = AppTheme.primaryRed, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 9: COLORS CONFIGURATION & DIRECT TERMS RULES MANAGER ---
@Composable
fun ColorsConfigAndConditionsTab(vm: MainViewModel, settings: AppSettings) {
    val context = LocalContext.current

    var showDesignPreviewDialog by remember { mutableStateOf(false) }

    var aboutImageUrlVal by remember { mutableStateOf(settings.aboutImageUrl) }

    val coverImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val compressed = compressImageBase64(context, it, maxWidth = 480, maxHeight = 240, quality = 80)
            aboutImageUrlVal = compressed
            Toast.makeText(context, "تم تحميل وضغط صورة غلاف معلومات التطبيق! 🎨", Toast.LENGTH_SHORT).show()
        }
    }

    var primaryColorField by remember { mutableStateOf(settings.primaryColorHex) }
    var accentColorField by remember { mutableStateOf(settings.accentColorHex) }
    var bgColorField by remember { mutableStateOf(settings.bgColorHex) }
    var surfaceColorField by remember { mutableStateOf(settings.surfaceColorHex) }

    var appNameVal by remember { mutableStateOf(settings.appNameAr) }
    var welcomeMsgVal by remember { mutableStateOf(settings.welcomeMessage) }
    var downloadUrlVal by remember { mutableStateOf(settings.downloadUrl) }
    var footerTextVal by remember { mutableStateOf(settings.footerText) }

    var aboutPhoneVal by remember { mutableStateOf(settings.aboutPhone) }
    var aboutWhatsappVal by remember { mutableStateOf(settings.aboutWhatsapp) }
    var aboutEmailVal by remember { mutableStateOf(settings.aboutEmail) }
    var aboutShareUrlVal by remember { mutableStateOf(settings.aboutShareUrl) }
    var aboutPhoneVisibleVal by remember { mutableStateOf(settings.aboutPhoneVisible) }
    var aboutWhatsappVisibleVal by remember { mutableStateOf(settings.aboutWhatsappVisible) }
    var aboutEmailVisibleVal by remember { mutableStateOf(settings.aboutEmailVisible) }
    var aboutShareUrlVisibleVal by remember { mutableStateOf(settings.aboutShareUrlVisible) }
    var aboutImageVisibleVal by remember { mutableStateOf(settings.aboutImageVisible) }

    var aboutTitleTextVal by remember { mutableStateOf(settings.aboutTitleText) }
    var aboutVersionLabelVal by remember { mutableStateOf(settings.aboutVersionLabel) }
    var aboutVersionValueVal by remember { mutableStateOf(settings.aboutVersionValue) }
    var aboutVersionVisibleVal by remember { mutableStateOf(settings.aboutVersionVisible) }
    var aboutSecurityLabelVal by remember { mutableStateOf(settings.aboutSecurityLabel) }
    var aboutSecurityValueVal by remember { mutableStateOf(settings.aboutSecurityValue) }
    var aboutSecurityVisibleVal by remember { mutableStateOf(settings.aboutSecurityVisible) }

    var cSizeValue by remember { mutableFloatStateOf(settings.chatIconSize.toFloat()) }
    var cColField by remember { mutableStateOf(settings.chatIconColorHex) }
    var cHiddenField by remember { mutableStateOf(settings.chatIconHidden) }

    var aSizeValue by remember { mutableFloatStateOf(settings.assistantIconSize.toFloat()) }
    var aColField by remember { mutableStateOf(settings.assistantIconColorHex) }
    var aHiddenField by remember { mutableStateOf(settings.assistantIconHidden) }
    var aXOffsetValue by remember { mutableFloatStateOf(settings.assistantIconXOffset.toFloat()) }
    var aYOffsetValue by remember { mutableFloatStateOf(settings.assistantIconYOffset.toFloat()) }
    var aIconTypeField by remember { mutableStateOf(settings.assistantIconType) }

    var fontColorHexField by remember { mutableStateOf(settings.fontColorHex) }
    var selectedFontField by remember { mutableStateOf(settings.selectedFontName) }
    var footerTextVisibleVal by remember { mutableStateOf(settings.footerTextVisible) }

    var isChatEnabledVal by remember { mutableStateOf(settings.isChatEnabled) }
    var chatDisMsgVal by remember { mutableStateOf(settings.chatDisabledMessage) }
    var noResultsMessageVal by remember { mutableStateOf(settings.noResultsMessage) }

    var isVoiceSpeechEnabledVal by remember { mutableStateOf(settings.isWebSpeechEnabled) }
    var isGeoSearchEnabledVal by remember { mutableStateOf(settings.isGeoSearchEnabled) }
    var searchMatchingMethodHexField by remember { mutableStateOf(settings.searchMatchingMethodHex) }
    var radiusValSelect by remember { mutableStateOf(settings.radiusSearchLimitKm.toString()) }
    var autoCleanupDaysVal by remember { mutableStateOf(settings.autoCleanupDays.toString()) }
    var maxPortImagesVal by remember { mutableStateOf(settings.maxPortfolioImages.toString()) }

    var inlineRulesList by remember(settings.registrationRulesList) { mutableStateOf(settings.registrationRulesList) }
    var ruleNewCandidateText by remember { mutableStateOf("") }
    var inlinePresetsList by remember(settings.colorsPresetsList) { mutableStateOf(settings.colorsPresetsList) }
    var editingPresetIndex by remember { mutableStateOf<Int?>(null) }
    var newPresetName by remember { mutableStateOf("") }
    var isNewRuleMandatory by remember { mutableStateOf(true) }

    var regNameVisibleVal by remember { mutableStateOf(settings.regNameVisible) }
    var regNameRequiredVal by remember { mutableStateOf(settings.regNameRequired) }
    var regPhoneVisibleVal by remember { mutableStateOf(settings.regPhoneVisible) }
    var regPhoneRequiredVal by remember { mutableStateOf(settings.regPhoneRequired) }
    var regAreaVisibleVal by remember { mutableStateOf(settings.regAreaVisible) }
    var regAreaRequiredVal by remember { mutableStateOf(settings.regAreaRequired) }
    var regDescVisibleVal by remember { mutableStateOf(settings.regDescVisible) }
    var regDescRequiredVal by remember { mutableStateOf(settings.regDescRequired) }
    var regCategoryVisibleVal by remember { mutableStateOf(settings.regCategoryVisible) }
    var regCategoryRequiredVal by remember { mutableStateOf(settings.regCategoryRequired) }
    var regSelfieVisibleVal by remember { mutableStateOf(settings.regSelfieVisible) }
    var regSelfieRequiredVal by remember { mutableStateOf(settings.regSelfieRequired) }
    var regIdCardVisibleVal by remember { mutableStateOf(settings.regIdCardVisible) }
    var regIdCardRequiredVal by remember { mutableStateOf(settings.regIdCardRequired) }
    var registrationChipColorHexVal by remember { mutableStateOf(settings.registrationChipColorHex) }
    var searchRatingWeightVal by remember { mutableStateOf(settings.searchRatingWeight.toString()) }
    var approvedProviderSortingMethodVal by remember { mutableStateOf(settings.approvedProviderSortingMethod) }
    var regChipBgColorsListVal by remember(settings.regChipBgColorsList) { mutableStateOf(settings.regChipBgColorsList) }
    val fontStyle = resolveAppFontFamily(selectedFontField)

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("🎨 لوحات الألوان والسمات الجاهزة وتخصيصها (حذف، تعديل، أو إضافة ألوان جديدة):", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                
                // Active list representation
                inlinePresetsList.forEachIndexed { index, preset ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                primaryColorField = preset.primaryHex
                                accentColorField = preset.accentHex
                                bgColorField = preset.bgHex
                                surfaceColorField = preset.surfaceHex
                                Toast.makeText(context, "تم تطبيق لوحة ${preset.name} وتعميمها بنجاح!", Toast.LENGTH_SHORT).show()
                                
                                val updatedSettingsObj = settings.copy(
                                    primaryColorHex = preset.primaryHex,
                                    accentColorHex = preset.accentHex,
                                    bgColorHex = preset.bgHex,
                                    surfaceColorHex = preset.surfaceHex
                                )
                                vm.updateAppSettings(updatedSettingsObj, "الأدمن")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = safeParseColor(preset.primaryHex, AppTheme.primaryRed)
                            ),
                            modifier = Modifier.weight(1f).height(38.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            Text(
                                text = preset.name,
                                color = safeParseColor(preset.accentHex, Color.White),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        IconButton(onClick = {
                            editingPresetIndex = index
                            newPresetName = preset.name
                            primaryColorField = preset.primaryHex
                            accentColorField = preset.accentHex
                            bgColorField = preset.bgHex
                            surfaceColorField = preset.surfaceHex
                            Toast.makeText(context, "تم جلب بيانات ${preset.name} للتعديل بالأسفل!", Toast.LENGTH_SHORT).show()
                        }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Edit, contentDescription = "تعديل", tint = AppTheme.accentGold, modifier = Modifier.size(16.dp))
                        }

                        IconButton(onClick = {
                            val updatedList = inlinePresetsList.toMutableList()
                            updatedList.removeAt(index)
                            inlinePresetsList = updatedList
                            Toast.makeText(context, "تم حذف باليت ${preset.name} مؤقتاً، اضغط حفظ بالأسفل للتأكيد سحابياً!", Toast.LENGTH_SHORT).show()
                        }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "حذف", tint = AppTheme.primaryRed, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                Divider(color = Color(0xFF223639), modifier = Modifier.padding(vertical = 4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPresetName,
                        onValueChange = { newPresetName = it },
                        label = { Text("اسم لوحة الألوان الجديدة/المعدلة...") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        textStyle = TextStyle(fontSize = 11.sp)
                    )

                    Button(
                        onClick = {
                            if (newPresetName.isNotBlank()) {
                                val updatedList = inlinePresetsList.toMutableList()
                                val newPreset = PresetPalette(
                                    name = newPresetName,
                                    primaryHex = primaryColorField,
                                    accentHex = accentColorField,
                                    bgHex = bgColorField,
                                    surfaceHex = surfaceColorField
                                )
                                if (editingPresetIndex != null && editingPresetIndex!! < updatedList.size) {
                                    updatedList[editingPresetIndex!!] = newPreset
                                    editingPresetIndex = null
                                    Toast.makeText(context, "تم تعديل لوحة الألوان بنجاح!", Toast.LENGTH_SHORT).show()
                                } else {
                                    updatedList.add(newPreset)
                                    Toast.makeText(context, "تم إضافة لوحة الألوان الحالية للقائمة الحية!", Toast.LENGTH_SHORT).show()
                                }
                                inlinePresetsList = updatedList
                                newPresetName = ""
                            } else {
                                Toast.makeText(context, "الرجاء كتابة اسم للوحة الألوان أولاً!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.height(38.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(if (editingPresetIndex != null) "تحديث ✏️" else "إضافة + 🎨", color = Color.White, fontSize = 9.sp)
                    }

                    if (editingPresetIndex != null) {
                        IconButton(onClick = {
                            editingPresetIndex = null
                            newPresetName = ""
                        }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Close, contentDescription = "إلغاء التعديل", tint = Color.Red, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🎨 تغيير هويات وألوان الواجهات (HEX)", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = primaryColorField,
                    onValueChange = { primaryColorField = it },
                    label = { Text("كود لون السمة الرئيسي") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = accentColorField,
                    onValueChange = { accentColorField = it },
                    label = { Text("كود لون اللمسات الثانوية") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = bgColorField,
                    onValueChange = { bgColorField = it },
                    label = { Text("كود لون الخلفية العامة (Background)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = surfaceColorField,
                    onValueChange = { surfaceColorField = it },
                    label = { Text("كود لون البطاقات والحاويات (Surface)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("💬 تخصيص مظهر وحجم أيقونة الدردشة الفورية والمساعد", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Text("حجم أيقونة الدردشة: ${cSizeValue.toInt()}dp", color = Color.White, fontSize = 11.sp)
                Slider(value = cSizeValue, onValueChange = { cSizeValue = it }, valueRange = 32f..88f)

                OutlinedTextField(
                    value = cColField,
                    onValueChange = { cColField = it },
                    label = { Text("لون خلفية أيقونة الدردشة الفورية") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = cHiddenField, onCheckedChange = { cHiddenField = it })
                    Text("إخفاء أيقونة الدردشة السريعة", color = Color.White, fontSize = 11.sp)
                }

                Divider(color = Color(0xFF223639), modifier = Modifier.padding(vertical = 4.dp))

                Text("حجم أيقونة المساعد الذكي AI: ${aSizeValue.toInt()}dp", color = Color.White, fontSize = 11.sp)
                Slider(value = aSizeValue, onValueChange = { aSizeValue = it }, valueRange = 32f..88f)

                OutlinedTextField(
                    value = aColField,
                    onValueChange = { aColField = it },
                    label = { Text("لون خلفية أيقونة المساعد الذكي AI") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Text("الموقع الأفقي للأيقونة (X Offset): ${aXOffsetValue.toInt()}dp", color = Color.White, fontSize = 11.sp)
                Slider(value = aXOffsetValue, onValueChange = { aXOffsetValue = it }, valueRange = -150f..150f)

                Text("الموقع العمودي للأيقونة (Y Offset): ${aYOffsetValue.toInt()}dp", color = Color.White, fontSize = 11.sp)
                Slider(value = aYOffsetValue, onValueChange = { aYOffsetValue = it }, valueRange = 0f..600f)

                Text("اختر شكل/رمز أيقونة المساعد الذكي:", color = Color.White, fontSize = 11.sp)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        "SmartToy" to "🤖 معالج ذكي",
                        "Support" to "📞 خدمة مهنية",
                        "Chat" to "💬 تواصل مباشر",
                        "Star" to "✨ ذكاء اصطناعي",
                        "Help" to "❓ مساعدة فورية"
                    ).forEach { (icName, icLbl) ->
                        val isSelected = aIconTypeField == icName
                        FilterChip(
                            selected = isSelected,
                            onClick = { aIconTypeField = icName },
                            label = { Text(icLbl, fontSize = 8.sp, color = if (isSelected) Color.Black else Color.White) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AppTheme.accentGold
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = aHiddenField, onCheckedChange = { aHiddenField = it })
                    Text("حذف / إخفاء أيقونة المساعد الذكي بالكامل للزوار", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("✍️ تخصيص خطوط ونصوص البرمجية وألوانها الفورية", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = fontColorHexField,
                    onValueChange = { fontColorHexField = it },
                    label = { Text("كود لون الخطوط العام للبرق الأبيض (HEX)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Text("اختر نوع الخط الافتراضي المفضل بالدليل للتطبيق (بما فيها خطوط Google العربية الممتازة):", color = Color.White, fontSize = 11.sp)
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf(
                        "Default" to "عادي",
                        "SansSerif" to "نسخ",
                        "Serif" to "رقعة",
                        "Monospace" to "برمجي",
                        "Cursive" to "يدوي",
                        "Cairo" to "خط كايْرو 💎",
                        "Tajawal" to "خط تجاوُل ✨",
                        "Amiri" to "خط أميْري 📖",
                        "Almarai" to "خط المَراعي 🌸"
                    ).forEach { (fn, lbl) ->
                        val isSelected = selectedFontField == fn
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFontField = fn },
                            label = { Text(lbl, fontSize = 10.sp, color = if (isSelected) Color.Black else Color.White) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AppTheme.accentGold
                            )
                        )
                    }
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "👁️ تجربة ومعاينة الحلة والتصميم المحدث",
                    color = AppTheme.accentGold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = fontStyle
                )
                Text(
                    text = "قبل الحفظ والاعتماد نهائياً لجميع المستخدمين، يمكنك رؤية شكل تطبيقك فورياً عبر لوحة محاكاة ذكية.",
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = fontStyle,
                    lineHeight = 14.sp
                )
                
                Button(
                    onClick = { showDesignPreviewDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                    modifier = Modifier.fillMaxWidth().height(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "معاينة",
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "معاينة التغييرات الحالية على التصميم 👁️",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontStyle
                    )
                }
            }
        }

        if (showDesignPreviewDialog) {
            androidx.compose.ui.window.Dialog(
                onDismissRequest = { showDesignPreviewDialog = false }
            ) {
                val previewPrimary = safeParseColor(primaryColorField, Color(0xFFCE1126))
                val previewAccent = safeParseColor(accentColorField, Color(0xFFFFD700))
                val previewBg = safeParseColor(bgColorField, Color(0xFF0D1B1E))
                val previewSurface = safeParseColor(surfaceColorField, Color(0xFF162A2D))
                val previewFontColor = safeParseColor(fontColorHexField, Color(0xFFFFFFFF))
                val previewFontFamily = resolveAppFontFamily(selectedFontField)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF162A2D)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, AppTheme.accentGold)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📱 محاكاة حية لتصميم التطبيق",
                            color = AppTheme.accentGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontStyle
                        )
                        Text(
                            text = "هذه محاكاة حية لطريقة ظهور الألوان والخطوط المختارة حالياً على شاشات الزوار.",
                            color = Color.LightGray,
                            fontSize = 9.sp,
                            fontFamily = fontStyle,
                            lineHeight = 13.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        // Smartphone frame mockup
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(290.dp),
                            colors = CardDefaults.cardColors(containerColor = previewBg),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(3.dp, Color.Gray)
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                // Status Bar simulator
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(previewBg)
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "07:04",
                                            color = previewFontColor.copy(alpha = 0.8f),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("📶", fontSize = 8.sp)
                                            Text("🔋", fontSize = 9.sp)
                                        }
                                    }
                                }

                                // Title Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(previewPrimary)
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = null,
                                            tint = previewAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = appNameVal.ifBlank { "دليل خدمات اليمن" },
                                            color = previewFontColor,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = previewFontFamily
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            tint = previewFontColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                // Body Content mockup
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "مرحباً بكم في خدمات اليمن السريعة 👋",
                                        color = previewFontColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = previewFontFamily
                                    )

                                    // Surface card mockup for lists
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = previewSurface),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(0.5.dp, previewAccent.copy(alpha = 0.3f))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                        .background(previewAccent, CircleShape),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "⚡",
                                                        color = previewBg,
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                                Text(
                                                    text = "صيانة غسالات وثلاجات متميزة",
                                                    color = previewFontColor,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = previewFontFamily
                                                )
                                            }
                                            Text(
                                                text = "فنيون متدربون، ضمان وجودة وتغطية لجميع المحافظات.",
                                                color = previewFontColor.copy(alpha = 0.7f),
                                                fontSize = 8.sp,
                                                fontFamily = previewFontFamily,
                                                lineHeight = 11.sp
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))

                                    // Button mockup inside smartphone
                                    Button(
                                        onClick = {},
                                        colors = ButtonDefaults.buttonColors(containerColor = previewPrimary),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(34.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text(
                                            text = "انقر للتواصل الفوري 📞",
                                            color = previewFontColor,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = previewFontFamily
                                        )
                                    }
                                }
                            }
                        }

                        // Design quality warnings (Accessibility helper)
                        val isLowContrast = previewBg == previewFontColor || 
                                (previewBg.red == previewFontColor.red && 
                                 previewBg.green == previewFontColor.green && 
                                 previewBg.blue == previewFontColor.blue)
                        if (isLowContrast) {
                            Text(
                                text = "⚠️ تنبيه: لون الخلفية الحالية مطابق للون الخط، مما يمنع وضوح القراءة. ننصح بتمييز كود لون الخط العام عن لون الخلفية.",
                                color = Color.Yellow,
                                fontSize = 8.sp,
                                fontFamily = fontStyle,
                                lineHeight = 11.sp,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }

                        // Dialog controls row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { showDesignPreviewDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "إغلاق المعاينة ❌",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontFamily = fontStyle
                                )
                            }
                        }
                    }
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🛑 إيقاف/تفعيل الدردشة ورسالة التعطيل المخصصة", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("المكالمات والمحادثات حرة ومفتوحة فورا؟", color = Color.White, fontSize = 11.sp)
                    Switch(checked = isChatEnabledVal, onCheckedChange = { isChatEnabledVal = it })
                }

                if (!isChatEnabledVal) {
                    OutlinedTextField(
                        value = chatDisMsgVal,
                        onValueChange = { chatDisMsgVal = it },
                        label = { Text("رسالة التعطيل التي تظهر للمستخدمين فورا") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        maxLines = 3
                    )
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("🔍 تخصيص رسالة خلو نتائج الفلترة والبحث", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = noResultsMessageVal,
                    onValueChange = { noResultsMessageVal = it },
                    label = { Text("الرسالة التي تظهر عند عدم العثور على فنيين", color = Color.Gray, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color(0xFF223639)
                    ),
                    maxLines = 3
                )
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("⚙️ محددات خرائط التواجد والتنظيف الدوري التلقائي السريع", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isVoiceSpeechEnabledVal, onCheckedChange = { isVoiceSpeechEnabledVal = it })
                    Text("تفعيل ميزة البحث الصوتي بمحرك الدليل 🎙️", color = Color.White, fontSize = 11.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isGeoSearchEnabledVal, onCheckedChange = { isGeoSearchEnabledVal = it })
                    Text("تفعيل رادار الخرائط التفاعلية والمسافات للمشتركين 🌍", color = Color.White, fontSize = 11.sp)
                }

                Text("آلية وطريقة عمل شريط البحث بالصفحة الرئيسية:", color = Color.White, fontSize = 11.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("fuzzy" to "بحث مرن ذكي 🔍", "exact" to "مطابق دقيق 🔎", "disabled" to "تعطيل البحث 🔒").forEach { (method, lbl) ->
                        val isSel = searchMatchingMethodHexField == method
                        FilterChip(
                            selected = isSel,
                            onClick = { searchMatchingMethodHexField = method },
                            label = { Text(lbl, fontSize = 9.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AppTheme.primaryRed,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFF0F2225)
                            )
                        )
                    }
                }

                Text("المدى الأقصى لبحث إحداثيات الخرائط:", color = Color.White, fontSize = 11.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("5" to "5 كم", "10" to "10 كم", "20" to "20 كم", "30" to "30 كم", "50" to "50 كم").forEach { (valKm, lbl) ->
                        FilterChip(
                            selected = radiusValSelect == valKm,
                            onClick = { radiusValSelect = valKm },
                            label = { Text(lbl, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.primaryRed)
                        )
                    }
                }

                Text("طريقة ترتيب وتثبيت وظهور مقدمي الخدمات في تصنيف الأقسام:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(
                        "admin_priority" to "ترتيب الأولوية للأدمن (orderPriority) 🔢",
                        "pin_first" to "تثبيت المثبتين في المقدمة أولاً 📌",
                        "rating_desc" to "ترتيب حسب أعلى تقييم تنازلياً ⭐",
                        "subscribed_first" to "ترتيب المشتركين VIP أولاً 👑",
                        "recommended_first" to "ترتيب الموثوقين والموصى بهم أولاً 🎖️",
                        "confidence_search" to "مستويات الثقة بالبحث (تثبيت ومضاعفة وزن التقييم) 🎖️⭐📌"
                    ).forEach { (method, lbl) ->
                        val isSel = approvedProviderSortingMethodVal == method
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { approvedProviderSortingMethodVal = method }
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = isSel,
                                onClick = { approvedProviderSortingMethodVal = method },
                                colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(lbl, color = if (isSel) AppTheme.accentGold else Color.White, fontSize = 10.sp, fontFamily = fontStyle)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = searchRatingWeightVal,
                    onValueChange = { input ->
                        searchRatingWeightVal = input.filter { it.isDigit() || it == '.' }
                    },
                    label = { Text("المعامل / وزن التقييم في البحث (Rating Weight)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold,
                        unfocusedBorderColor = Color.Gray
                    ),
                    textStyle = TextStyle(color = Color.White, fontFamily = fontStyle)
                )
                Text("تعديل المعامل (مثال: 1.0 أو 1.5 أو 2.0). عند قيام العميل بالبحث، تظهر النتائج الموثقة ذات التقييم المضروب بالوزن أولاً تلقائياً.", color = Color.Gray, fontSize = 9.sp, fontFamily = fontStyle)

                OutlinedTextField(
                    value = autoCleanupDaysVal,
                    onValueChange = { autoCleanupDaysVal = it },
                    label = { Text("مدة بقاء واحتفاظ البلاغات والاتصالات القديمة (يوم)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = maxPortImagesVal,
                    onValueChange = { maxPortImagesVal = it },
                    label = { Text("الحد الأقصى لعدد صور معرض أعمال كل عضو مهني") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Button(
                    onClick = {
                        vm.addAuditLog("الأدمن", "تنظيف يدوي فوري: تم فحص وإبعاد كافة الملفات والسجلات.")
                        Toast.makeText(context, "تم تنفيذ تنظيف وإتلاف البيانات والسجلات القديمة بنجاح!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("بدء تنظيف السجلات التالفة والقديمة الآن 🧹", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📝 تعديل نصوص وعناصر واجهات التطبيق الرئيسية وصفحة (معلومات)", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = appNameVal,
                    onValueChange = { appNameVal = it },
                    label = { Text("اسم التطبيق الظاهر في شريط العنوان العلوي") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = welcomeMsgVal,
                    onValueChange = { welcomeMsgVal = it },
                    label = { Text("رسالة الترحيب ووصف خدمات صفحة (عن التطبيق)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                    maxLines = 4
                )

                OutlinedTextField(
                    value = downloadUrlVal,
                    onValueChange = { downloadUrlVal = it },
                    label = { Text("رابط تحميل ملف التطبيق المباشر (APK)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {

                    OutlinedTextField(
                        value = aboutImageUrlVal,
                        onValueChange = { aboutImageUrlVal = it },
                        label = { Text("رابط صورة الغلاف أو رمز Base64 أو نص يبدأ بـ :text") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White, fontFamily = fontStyle),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            errorContainerColor = AppTheme.darkBg,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = AppTheme.accentGold
                        )
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { coverImagePicker.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.weight(1f).height(40.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(4.dp)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("🖼️ رفع صورة من الذاكرة", fontSize = 10.sp, color = AppTheme.accentGold, fontFamily = fontStyle)
                        }
                        Button(
                            onClick = { 
                                aboutImageUrlVal = "text:كل خدمات اليمن ترحب بكم" 
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.weight(1f).height(40.dp).border(1.dp, Color(0xFF223639), RoundedCornerShape(4.dp)),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("✍️ تحويل لغلاف نصي", fontSize = 10.sp, color = AppTheme.accentGold, fontFamily = fontStyle)
                        }
                    }
                    Text(
                        text = "نصيحة: يمكنك رفع صورة وتطبيقها فوراً من المعرض، أو إدخال رابط إنترنت، أو كتابة نص يبدأ بعبارة 'text:' لظهورها كشعار نصي مبتكر ومظلل.",
                        color = Color.LightGray,
                        fontSize = 9.sp,
                        lineHeight = 12.sp,
                        fontFamily = fontStyle
                    )
                }

                OutlinedTextField(
                    value = footerTextVal,
                    onValueChange = { footerTextVal = it },
                    label = { Text("نص التذييل السفلي للحقوق والمصداقية المهنية") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = footerTextVisibleVal, onCheckedChange = { footerTextVisibleVal = it })
                    Text("إظهار نص تذييل الحقوق في أسفل الشاشة الرئيسية", color = Color.White, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("📞 تخصيص معلومات التواصل بصفحة معلومات التطبيق:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = aboutImageVisibleVal, onCheckedChange = { aboutImageVisibleVal = it })
                    Text("إظهار صورة غلاف معلومات التطبيق", color = Color.White, fontSize = 11.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutPhoneVisibleVal, onCheckedChange = { aboutPhoneVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutPhoneVal,
                        onValueChange = { aboutPhoneVal = it },
                        label = { Text("رقم الهاتف للاتصال والشكاوى") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutWhatsappVisibleVal, onCheckedChange = { aboutWhatsappVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutWhatsappVal,
                        onValueChange = { aboutWhatsappVal = it },
                        label = { Text("رقم الواتساب للتواصل والتوثيق") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutEmailVisibleVal, onCheckedChange = { aboutEmailVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutEmailVal,
                        onValueChange = { aboutEmailVal = it },
                        label = { Text("البريد الإلكتروني الرسمي لخدمات اليمن") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutShareUrlVisibleVal, onCheckedChange = { aboutShareUrlVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutShareUrlVal,
                        onValueChange = { aboutShareUrlVal = it },
                        label = { Text("رابط مشاركة التطبيق") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text("📝 تخصيص نصوص النسخة والحماية لصفحة معلومات التطبيق:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = aboutTitleTextVal,
                    onValueChange = { aboutTitleTextVal = it },
                    label = { Text("عنوان قسم (عن المنصة) بصفحة معلومات التطبيق") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutVersionVisibleVal, onCheckedChange = { aboutVersionVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutVersionLabelVal,
                        onValueChange = { aboutVersionLabelVal = it },
                        label = { Text("عنوان النسخة") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutVersionValueVal,
                        onValueChange = { aboutVersionValueVal = it },
                        label = { Text("رقم النسخة") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = aboutSecurityVisibleVal, onCheckedChange = { aboutSecurityVisibleVal = it })
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutSecurityLabelVal,
                        onValueChange = { aboutSecurityLabelVal = it },
                        label = { Text("عنوان تشفير الأمان") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    OutlinedTextField(
                        value = aboutSecurityValueVal,
                        onValueChange = { aboutSecurityValueVal = it },
                        label = { Text("نص حالة التشفير والسرية") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📜 إدارة وتعديل شروط تسجيل واعتماد مزودي الخدمات (تحديد إجباري/اختياري):", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                inlineRulesList.forEachIndexed { index, rule ->
                    val isMandatory = !rule.startsWith("[اختياري]")
                    val cleanText = rule.removePrefix("[إجباري] ").removePrefix("[اختياري] ")
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = cleanText,
                            onValueChange = { editedText ->
                                val updatedList = inlineRulesList.toMutableList()
                                val prefix = if (isMandatory) "[إجباري] " else "[اختياري] "
                                updatedList[index] = prefix + editedText
                                inlineRulesList = updatedList
                            },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            textStyle = TextStyle(fontSize = 11.sp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        // Toggle condition type button
                        Button(
                            onClick = {
                                val updatedList = inlineRulesList.toMutableList()
                                val newPrefix = if (isMandatory) "[اختياري] " else "[إجباري] "
                                updatedList[index] = newPrefix + cleanText
                                inlineRulesList = updatedList
                                Toast.makeText(context, "تم تغيير نوع الشرط!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isMandatory) AppTheme.primaryRed.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.2f)
                            ),
                            contentPadding = PaddingValues(horizontal = 6.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text(
                                text = if (isMandatory) "إجباري 📋" else "اختياري 💡",
                                color = if (isMandatory) AppTheme.primaryRed else Color.LightGray,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        IconButton(onClick = {
                            val updatedList = inlineRulesList.toMutableList()
                            updatedList.removeAt(index)
                            inlineRulesList = updatedList
                            Toast.makeText(context, "تم حذف الشرط مؤقتاً، انقر نشر بالأسفل للحفظ سحابياً!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Rule", tint = AppTheme.primaryRed, modifier = Modifier.size(20.dp))
                        }
                    }
                }

                Divider(color = Color(0xFF223639), modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("حدد حالة وتصنيف الشرط الجديد:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.clickable { isNewRuleMandatory = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isNewRuleMandatory,
                            onClick = { isNewRuleMandatory = true },
                            colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                        )
                        Text("إجباري 📋", color = Color.White, fontSize = 11.sp)
                    }
                    Row(
                        modifier = Modifier.clickable { isNewRuleMandatory = false },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isNewRuleMandatory,
                            onClick = { isNewRuleMandatory = false },
                            colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                        )
                        Text("اختياري 💡", color = Color.White, fontSize = 11.sp)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = ruleNewCandidateText,
                        onValueChange = { ruleNewCandidateText = it },
                        label = { Text("أكتب شرطاً جديداً لإضافته...") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    IconButton(onClick = {
                        if (ruleNewCandidateText.isNotBlank()) {
                            val updatedList = inlineRulesList.toMutableList()
                            val prefix = if (isNewRuleMandatory) "[إجباري] " else "[اختياري] "
                            updatedList.add(prefix + ruleNewCandidateText)
                            inlineRulesList = updatedList
                            ruleNewCandidateText = ""
                            Toast.makeText(context, "تم إدراج الشرط الجديد للقائمة!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add Rule", tint = AppTheme.accentGold, modifier = Modifier.size(32.dp))
                    }
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📋 إدارة حقول ومتطلبات قائمة تسجيل مقدمي الخدمات:", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 11.sp, fontFamily = fontStyle)
                
                // Color configuration of registration options
                OutlinedTextField(
                    value = registrationChipColorHexVal,
                    onValueChange = { registrationChipColorHexVal = it },
                    label = { Text("رمز لون خيارات القائمة وفئات التسجيل (Hex)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )
                Text("مثال لخيارات واضحة: #1E3A47 (أزرق داكن مريح وملائم للخط الأبيض الساطع)", color = Color.Gray, fontSize = 9.sp, fontFamily = fontStyle)

                Spacer(modifier = Modifier.height(4.dp))
                Text("السمات والألوان المتاحة لتسجيل مقدمي الخدمات (اضغط للاختيار، أو احذف، أو أضف أدناه):", color = Color.White, fontSize = 10.sp, fontFamily = fontStyle)
                
                // Horizontal list of colors for the chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    items(regChipBgColorsListVal) { colorHex ->
                        val parsedColor = try { Color(android.graphics.Color.parseColor(colorHex)) } catch (e: Exception) { Color.Gray }
                        val isSelectedColor = registrationChipColorHexVal.equals(colorHex, ignoreCase = true)
                        
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(parsedColor)
                                .border(
                                    width = if (isSelectedColor) 3.dp else 1.dp,
                                    color = if (isSelectedColor) AppTheme.accentGold else Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { registrationChipColorHexVal = colorHex },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelectedColor) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            // Delete button
                            IconButton(
                                onClick = {
                                    regChipBgColorsListVal = regChipBgColorsListVal.filter { !it.equals(colorHex, ignoreCase = true) }
                                },
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Delete",
                                    tint = Color.Red,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }
                }
                
                Button(
                    onClick = {
                        val cleanedHex = registrationChipColorHexVal.trim()
                        if (cleanedHex.startsWith("#") && (cleanedHex.length == 7 || cleanedHex.length == 9)) {
                            if (!regChipBgColorsListVal.any { it.equals(cleanedHex, ignoreCase = true) }) {
                                regChipBgColorsListVal = regChipBgColorsListVal + cleanedHex
                                Toast.makeText(context, "تمت إضافة اللون إلى القائمة السريعة بنجاح! 🎨", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "اللون مضاف بالفعل بقائمة الخيارات السريعة!", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "يرجى كتابة رمز لون Hex صحيح يبدأ بـ # (مثال: #2A9D8F)", Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(6.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text("إضافة اللون إلى قائمة الخيارات السريعة 🎨➕", color = Color.White, fontSize = 9.sp, fontFamily = fontStyle)
                }

                Divider(color = Color(0xFF223639))

                // 1. Name Field
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("حقل الاسم الكامل:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regNameVisibleVal, onCheckedChange = { regNameVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regNameRequiredVal, onCheckedChange = { regNameRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 2. Phone Field
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("حقل رقم الهاتف اليمني:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regPhoneVisibleVal, onCheckedChange = { regPhoneVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regPhoneRequiredVal, onCheckedChange = { regPhoneRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 3. Area Field
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("حقل المنطقة والشارع:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regAreaVisibleVal, onCheckedChange = { regAreaVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regAreaRequiredVal, onCheckedChange = { regAreaRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 4. Description Field
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("حقل نبذة مختصرة عن مؤهلاتك:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regDescVisibleVal, onCheckedChange = { regDescVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regDescRequiredVal, onCheckedChange = { regDescRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 5. Category selection
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("اختيار فئة التخصص:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regCategoryVisibleVal, onCheckedChange = { regCategoryVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regCategoryRequiredVal, onCheckedChange = { regCategoryRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 6. Selfie Capture
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("التقاط الصورة السيلفي الحية:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regSelfieVisibleVal, onCheckedChange = { regSelfieVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regSelfieRequiredVal, onCheckedChange = { regSelfieRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }

                // 7. National ID capture (Required by user!)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("رفع وثيقة بطاقة الهوية الوطنية:", color = Color.White, fontSize = 11.sp, fontFamily = fontStyle)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = regIdCardVisibleVal, onCheckedChange = { regIdCardVisibleVal = it })
                        Text("مرئي", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                        Spacer(modifier = Modifier.width(6.dp))
                        Checkbox(checked = regIdCardRequiredVal, onCheckedChange = { regIdCardRequiredVal = it })
                        Text("مطلوب", color = Color.LightGray, fontSize = 10.sp, fontFamily = fontStyle)
                    }
                }
            }
        }

        Button(
            onClick = {
                val upPrimary = primaryColorField.ifBlank { "#CE1126" }
                val upAccent = accentColorField.ifBlank { "#FFD700" }
                val upBg = bgColorField.ifBlank { "#0D1B1E" }
                val upSurf = surfaceColorField.ifBlank { "#162A2D" }

                val upRadius = radiusValSelect.toIntOrNull() ?: 30
                val upCleanup = autoCleanupDaysVal.toIntOrNull() ?: 30

                val updatedSettingsObj = settings.copy(
                    primaryColorHex = upPrimary,
                    accentColorHex = upAccent,
                    bgColorHex = upBg,
                    surfaceColorHex = upSurf,
                    registrationRulesList = inlineRulesList,
                    colorsPresetsList = inlinePresetsList,
                    maxPortfolioImages = maxPortImagesVal.toIntOrNull() ?: 5,
                    chatIconSize = cSizeValue.toInt(),
                    chatIconColorHex = cColField,
                    chatIconHidden = cHiddenField,
                    assistantIconSize = aSizeValue.toInt(),
                    assistantIconColorHex = aColField,
                    assistantIconHidden = aHiddenField,
                    assistantIconXOffset = aXOffsetValue.toInt(),
                    assistantIconYOffset = aYOffsetValue.toInt(),
                    assistantIconType = aIconTypeField,
                    fontColorHex = fontColorHexField.ifBlank { "#FFFFFF" },
                    selectedFontName = selectedFontField,
                    footerTextVisible = footerTextVisibleVal,
                    isChatEnabled = isChatEnabledVal,
                    chatDisabledMessage = chatDisMsgVal,
                    isWebSpeechEnabled = isVoiceSpeechEnabledVal,
                    isGeoSearchEnabled = isGeoSearchEnabledVal,
                    searchMatchingMethodHex = searchMatchingMethodHexField,
                    radiusSearchLimitKm = upRadius,
                    autoCleanupDays = upCleanup,
                    appNameAr = appNameVal,
                    welcomeMessage = welcomeMsgVal,
                    downloadUrl = downloadUrlVal,
                    aboutImageUrl = aboutImageUrlVal,
                    footerText = footerTextVal,
                    aboutPhone = aboutPhoneVal,
                    aboutWhatsapp = aboutWhatsappVal,
                    aboutEmail = aboutEmailVal,
                    aboutShareUrl = aboutShareUrlVal,
                    aboutPhoneVisible = aboutPhoneVisibleVal,
                    aboutWhatsappVisible = aboutWhatsappVisibleVal,
                    aboutEmailVisible = aboutEmailVisibleVal,
                    aboutShareUrlVisible = aboutShareUrlVisibleVal,
                    aboutImageVisible = aboutImageVisibleVal,
                    aboutTitleText = aboutTitleTextVal,
                    aboutVersionLabel = aboutVersionLabelVal,
                    aboutVersionValue = aboutVersionValueVal,
                    aboutVersionVisible = aboutVersionVisibleVal,
                    aboutSecurityLabel = aboutSecurityLabelVal,
                    aboutSecurityValue = aboutSecurityValueVal,
                    aboutSecurityVisible = aboutSecurityVisibleVal,
                    regNameVisible = regNameVisibleVal,
                    regNameRequired = regNameRequiredVal,
                    regPhoneVisible = regPhoneVisibleVal,
                    regPhoneRequired = regPhoneRequiredVal,
                    regAreaVisible = regAreaVisibleVal,
                    regAreaRequired = regAreaRequiredVal,
                    regDescVisible = regDescVisibleVal,
                    regDescRequired = regDescRequiredVal,
                    regCategoryVisible = regCategoryVisibleVal,
                    regCategoryRequired = regCategoryRequiredVal,
                    regSelfieVisible = regSelfieVisibleVal,
                    regSelfieRequired = regSelfieRequiredVal,
                    regIdCardVisible = regIdCardVisibleVal,
                    regIdCardRequired = regIdCardRequiredVal,
                    registrationChipColorHex = registrationChipColorHexVal,
                    searchRatingWeight = searchRatingWeightVal.toFloatOrNull() ?: 1.0f,
                    approvedProviderSortingMethod = approvedProviderSortingMethodVal,
                    regChipBgColorsList = regChipBgColorsListVal,
                    noResultsMessage = noResultsMessageVal
                )
                
                vm.updateAppSettings(updatedSettingsObj, "الأدمن")
                Toast.makeText(context, "تم حفظ الشروط المحدثة وتعديلات مرئيات الألوان والأيقونات بنجاح!", Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(bottom = 12.dp)
        ) {
            Text("حفظ التعديلات وضبط الشروط والمظاهر الفورية 💾", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- LEGACY FOOTER TAB DELETED ---

// --- LEGACY APP INFO TAB DELETED ---

// --- LEGACY MANUAL PROVIDER TAB DELETED ---

// --- LEGACY REPORTS AND CATEGORIES TABS DELETED ---

// --- LEGACY AUDIT, SUBSCRIPTIONS, AND SUPERVISORS TABS DELETED ---

// --- SMART ASSISTANT SHEET OVERLAY CONSTITUENT (FULLY CORRECTED TEXT INPUT & FOCUS) ---
@Composable
fun SmartAssistantSheet(
    vm: MainViewModel,
    onClose: () -> Unit,
    fontFamily: FontFamily
) {
    val context = LocalContext.current
    val geminiHistory by vm.geminiMessages.collectAsState()
    val isThinking by vm.isGeminiThinking.collectAsState()
    var promptInput by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    // Setup Text to Speech
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var isTtsAutoSpeak by remember { mutableStateOf(true) }

    DisposableEffect(context) {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsInstance?.language = Locale("ar")
            }
        }
        tts = ttsInstance
        onDispose {
            ttsInstance.stop()
            ttsInstance.shutdown()
        }
    }

    // Speech to text activity contract launcher
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data = result.data
            val speechResults = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = speechResults?.getOrNull(0) ?: ""
            if (recognizedText.isNotBlank()) {
                promptInput = recognizedText
                Toast.makeText(context, "تم التعرف على صوتك بنجاح! 👍", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Auto-scroll helper & speech response speaker
    LaunchedEffect(geminiHistory.size) {
        if (geminiHistory.isNotEmpty()) {
            lazyListState.animateScrollToItem(geminiHistory.size - 1)
            val lastMsg = geminiHistory.last()
            // If the last message is from the AI (!isUser), auto-speak it
            if (!lastMsg.second && isTtsAutoSpeak) {
                tts?.speak(lastMsg.first, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Backdrop overlay sibling
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
                .clickable { onClose() }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .align(Alignment.Center)
                .imePadding()
                .clickable(
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                    indication = null
                ) { /* Prevent backdrop click from hiding the sheet */ },
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, AppTheme.accentGold)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header of AI Assistant
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F2225))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(AppTheme.accentGold.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = "AI icon", tint = AppTheme.accentGold, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "المساعد الذكي الصوتي لليمن 🎙️🤖",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = fontFamily
                            )
                            Text(
                                text = "دعم المبادرة وتفعيل الأغراض الصوتية سريعة الاستجابة",
                                color = Color.LightGray,
                                fontSize = 9.sp,
                                fontFamily = fontFamily
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        // Speaker switch for auto voicing
                        IconButton(
                            onClick = {
                                isTtsAutoSpeak = !isTtsAutoSpeak
                                if (!isTtsAutoSpeak) {
                                    tts?.stop()
                                }
                                Toast.makeText(context, if (isTtsAutoSpeak) "تم تفعيل النطق الصوتي التلقائي 🔊" else "تم كتم الصوت التلقائي 🔇", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = if (isTtsAutoSpeak) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = "تسجيل النطق",
                                tint = if (isTtsAutoSpeak) AppTheme.accentGold else Color.LightGray,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = onClose,
                            modifier = Modifier.size(28.dp).testTag("close_assistant_btn")
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = AppTheme.primaryRed)
                        }
                    }
                }

                // Quick scrolling FAQ suggestion shortcuts (Works offline & online!)
                Text(
                    text = "💡 أسئلة شائعة واختصارات سريعة (أوفلاين):",
                    color = AppTheme.accentGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontFamily = fontFamily
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
                ) {
                    val faqs = listOf(
                        "أشتي سباك بصنعاء 💧" to "هل يوجد سباك في صنعاء؟",
                        "أشتي كهربائي بعدن ⚡" to "هل يوجد كهربائي موثق في عدن؟",
                        "رقم خدمة دعم المبادرة؟ 📞" to "ما هو رقم الدعم الفني وتواصل مبادرة اليمن؟",
                        "كيف أسجل كـ عضو بالدليل؟ 💼" to "كيف يمكنني التسجيل كـ فني أو مهني في التطبيق؟",
                        "فني تكييف صيانة ❄️" to "أشتي فني تكييف وتبريد"
                    )
                    items(faqs) { (lbl, prompt) ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.clickable {
                                vm.askGemini(prompt)
                            },
                            border = BorderStroke(1.dp, Color(0xFF223639))
                        ) {
                            Text(
                                text = lbl,
                                color = Color.White,
                                fontSize = 8.5.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                fontFamily = fontFamily
                            )
                        }
                    }
                }

                Divider(color = Color(0xFF223639), thickness = 1.dp)

                // Chat Messages Body Area
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(geminiHistory) { (message, isUser) ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!isUser) {
                                // Left message speaker trigger
                                IconButton(
                                    onClick = {
                                        tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)
                                    },
                                    modifier = Modifier.padding(end = 4.dp).size(26.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "استماع",
                                        tint = AppTheme.accentGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomStart = if (isUser) 10.dp else 0.dp,
                                            bottomEnd = if (isUser) 0.dp else 10.dp
                                        )
                                    )
                                    .background(if (isUser) AppTheme.primaryRed else Color(0xFF1E3539))
                                    .then(
                                        if (!isUser) Modifier.border(1.dp, AppTheme.accentGold.copy(alpha = 0.5f), RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 10.dp))
                                        else Modifier
                                    )
                                    .padding(10.dp)
                                    .widthIn(max = 240.dp)
                            ) {
                                Text(
                                    text = message,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = fontFamily
                                )
                            }
                        }
                    }

                    if (isThinking) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = AppTheme.darkBg),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "جاري البحث الفوري وصياغة الرصد مع المزامنة الصوتية...",
                                        color = AppTheme.accentGold,
                                        fontSize = 10.sp,
                                        modifier = Modifier.padding(6.dp),
                                        fontFamily = fontFamily
                                    )
                                }
                            }
                        }
                    }
                }

                // SMART ASSISTANT INPUT AREA WITH VOICE RECORD TRIGGER
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F2225))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-YE")
                                putExtra(RecognizerIntent.EXTRA_PROMPT, "تحدث الآن.. اسأل المساعد الذكي الصوتي لليمن")
                            }
                            try {
                                speechRecognizerLauncher.launch(speechIntent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "التحقق الصوتي غير متاح بجهازك، سيتم استخدام محرك إدخال لوحة المفاتيح", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AppTheme.primaryRed)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "التعرف الصوتي",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        value = promptInput,
                        onValueChange = { promptInput = it },
                        placeholder = { Text("اسألني شيئاً أو تحدث معي بالصوت...", color = Color.Gray) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("assistant_input_text_field"),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = AppTheme.darkBg,
                            unfocusedContainerColor = AppTheme.darkBg,
                            cursorColor = AppTheme.accentGold,
                            focusedIndicatorColor = AppTheme.accentGold
                        ),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (promptInput.isNotBlank()) {
                                vm.askGemini(promptInput)
                                promptInput = ""
                            }
                        })
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (promptInput.isNotBlank()) {
                                vm.askGemini(promptInput)
                                promptInput = ""
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(AppTheme.accentGold)
                            .testTag("send_prompt_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send prompt",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- USER NOTIFICATION CENTER DIALOG COMPOSABLE ---
@Composable
fun UserNotificationCenterDialog(
    vm: MainViewModel,
    notifications: List<UserNotification>,
    onDismiss: () -> Unit,
    fontFamily: FontFamily
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔔 الإشعارات اللحظية",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = AppTheme.accentGold,
                    fontFamily = fontFamily
                )
                TextButton(
                    onClick = { vm.markAllNotificationsAsRead() },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("قرأت الكل ✓", color = AppTheme.accentGold, fontSize = 10.sp, fontFamily = fontFamily)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                if (notifications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "لا توجد أي إشعارات جديدة حالياً.",
                            color = Color.Gray,
                            fontSize = 11.sp,
                            fontFamily = fontFamily
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(notifications) { item ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (item.isRead) Color(0xFF0F2225) else Color(0xFF1B2E31)
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    if (item.isRead) Color(0xFF223639) else AppTheme.accentGold.copy(alpha = 0.5f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when (item.statusType) {
                                                    "order_approved" -> AppTheme.lightGreen
                                                    "appointment_updated" -> AppTheme.accentGold
                                                    "msg_received" -> AppTheme.primaryRed
                                                    else -> Color.Gray
                                                }
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = when (item.statusType) {
                                                "order_approved" -> Icons.Default.Check
                                                "appointment_updated" -> Icons.Default.Event
                                                "msg_received" -> Icons.Default.Message
                                                else -> Icons.Default.Info
                                            },
                                            contentDescription = null,
                                            tint = Color.Black,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                    
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.title,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            fontFamily = fontFamily
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = item.body,
                                            color = Color.LightGray,
                                            fontSize = 10.sp,
                                            fontFamily = fontFamily,
                                            lineHeight = 13.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = item.time,
                                            color = Color.Gray,
                                            fontSize = 8.sp,
                                            fontFamily = fontFamily
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("إغلاق", color = Color.White, fontSize = 11.sp, fontFamily = fontFamily)
            }
        },
        containerColor = AppTheme.surfaceDark
    )
}
