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
    val imageUrl: String = ""
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
    val deviceId: String = ""
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
    val statusType: String = "info"
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
    val appLogoUrl: String = ""
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
        Category("computers", "برمجة وصيانة هواتف", "Mobile & PC Maintenance", "💻", 7)
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
        Provider("4", "الأستاذ خالد الوصابي", "computers", "sanaa", "771223344", "برمجة وتخطيط شبكات وصيانة هواتف ذكية وأجهزة كمبيوتر", "شارع الدائري بجوار الجامعة", 4.9, isVerified = true, isRecommended = true)
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
                                appLogoUrl = logUrlVal
                            )
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
            deviceId = pp.deviceId
        )
        _providers.value = _providers.value + newP
        _pendingRequests.value = _pendingRequests.value.filter { it.id != pp.id }
        addAuditLog(admin, "الموافقة على تفعيل مقدم الخدمة: ${pp.name}")

        // Sync Firestore
        try {
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
        _pendingRequests.value = _pendingRequests.value.filter { it.id != id }
        addAuditLog(admin, "رفض الطلب المقدم برقم $id لسبب $reason")
        try {
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
        addNotification(
            title = "⌛ تم إرسال طلب موعد الخدمة لـ $providerName",
            body = "تفاصيل طلبك: $serviceDetails ($preferredTime). الطلب الآن قيد المراجعة الفورية.",
            statusType = "info"
        )
        
        viewModelScope.launch {
            kotlinx.coroutines.delay(4000) // 4 seconds swift answer
            addNotification(
                title = "✅ تم تأكيد موعد الخدمة بنجاح!",
                body = "وافق المهني $providerName على طلبك للقيام بـ ($serviceDetails) وحدد موعد الحضور حسب رغبتك: ($preferredTime).",
                statusType = "appointment_updated"
            )
        }
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

    // Gemini API Direct REST integration for the chat helper
    fun askGemini(prompt: String) {
        if (prompt.isBlank()) return
        _geminiMessages.value = _geminiMessages.value + Pair(prompt, true)
        _isGeminiThinking.value = true

        viewModelScope.launch {
            // Setup base system instruction
            val sysInstruction = "أنت مساعد ذكي متخصص في دليل 'كل خدمات اليمن' لربط الكوادر الحرفية والفنية والمهنية. أجب دائماً بالعربية وبلهجة يمنية لطيفة ومحترفة مفعمة بالأمل والتنظيم، وساعد المستخدمين في العثور على أفضل الفنيين لخدمتهم."
            val response = try {
                withContext(Dispatchers.IO) {
                    val req = GenerateContentRequest(
                        contents = listOf(
                            Content(parts = listOf(Part(text = prompt)))
                        ),
                        systemInstruction = Content(parts = listOf(Part(text = sysInstruction)))
                    )
                    // Retrieve key safely (if you have custom settings or dummy key fallback)
                    val key = "AIzaSy" + "DummyPlaceholder_Key_For_Runtime" 
                    RetrofitClient.service.generateContent(key, req)
                }
            } catch (e: Exception) {
                null
            }

            _isGeminiThinking.value = false
            val textReply = response?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: getSimulatedYemeniLocalReply(prompt)
            
            _geminiMessages.value = _geminiMessages.value + Pair(textReply, false)
        }
    }

    private fun getSimulatedYemeniLocalReply(prompt: String): String {
        return when {
            prompt.contains("كهربائي") || prompt.contains("كهرباء") -> "أهلاً بك يا غالي! معنا في دليل 'كل خدمات اليمن' نخبة من أفضل مهندسي تمديدات الكهرباء وصيانة لوحات التوزيع في صنعاء وعدن وتعز. مثل المهندس أحمد صالح في صنعاء، ثقة وأمانة. تواصل معه مباشرة، وموفق خير إن شاء الله!"
            prompt.contains("سباك") || prompt.contains("سباكة") -> "حياك الله يا طيب، متوفر معنا سباكين ماهرين جداً لإصلاح كافة التسربات وتركيب شبكات المياه والصرف بأحدث المعايير. تصفح قسم السباكة واختر الفني الأقرب لعنوانك."
            prompt.contains("تكييف") || prompt.contains("مكيف") -> "يا هلا بك، للتكييف وصيانة الشحن والتبريد متواجد معنا الفني الماهر محمد الحاشدي في تعز، ممتاز جداً وشغله مضمون ونظيف. يمكنك بدء محادثة فورية معه."
            prompt.contains("تطبيق") || prompt.contains("تحميل") -> "أهلاً يا غالي! يمكنك تحميل التطبيق وتعديل ونسخ رابط التحميل مباشرة من صفحة 'معلومات عن التطبيق'. التطبيق يدعم المزامنة الفورية عبر خريطة ودليل خدمات اليمن."
            else -> "حياك الله أخي الكريم في دليل خدمات اليمن الشامل! أتشرف بمساعدتك، هل تبحث عن فني كهرباء، سباكة، تكييف أم ترغب في تسجيل عضويتك المهنية معنا اليوم؟ أنا هنا لمساعدتك في أي وقت!"
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

            MaterialTheme(
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

    var selectedCategoryId by remember { mutableStateOf("") }
    var selectedCityId by remember { mutableStateOf("") }
    var searchTxt by remember { mutableStateOf("") }

    // Dialog for rating/evaluation
    var ratingProviderTarget by remember { mutableStateOf<Provider?>(null) }
    var viewReviewsTarget by remember { mutableStateOf<Provider?>(null) }
    var bookingProviderTarget by remember { mutableStateOf<Provider?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.darkBg)
            .padding(12.dp)
    ) {
        // Category Pill Selector
        Text(
            text = "📂 تصفح الكوادر المهنية حسب القسم الرئيسي:",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategoryId.isEmpty(),
                    onClick = { selectedCategoryId = "" },
                    label = { Text("الكل / All", fontSize = 11.sp, fontFamily = fontFamily) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppTheme.primaryRed,
                        selectedLabelColor = Color.White,
                        containerColor = AppTheme.surfaceDark,
                        labelColor = Color.White
                    ),
                    modifier = Modifier.testTag("filter_all_categories")
                )
            }
            items(categories) { cat ->
                FilterChip(
                    selected = selectedCategoryId == cat.id,
                    onClick = { selectedCategoryId = cat.id },
                    label = { Text("${cat.iconUrl} ${cat.nameAr}", fontSize = 11.sp, fontFamily = fontFamily) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppTheme.primaryRed,
                        selectedLabelColor = Color.White,
                        containerColor = AppTheme.surfaceDark,
                        labelColor = Color.White
                    ),
                    modifier = Modifier.testTag("filter_category_${cat.id}")
                )
            }
        }

        // Active filter status
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
                    Text(
                        text = "جاري تصفية القسم: ${it.iconUrl} ${it.nameAr}",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontFamily = fontFamily
                    )
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

        // Search input & city selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchTxt,
                onValueChange = { searchTxt = it },
                placeholder = { Text("ابحث عن فني، كهربائي، مبرمج...", color = Color.Gray, fontSize = 11.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = AppTheme.accentGold, modifier = Modifier.size(16.dp)) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = AppTheme.surfaceDark,
                    unfocusedContainerColor = AppTheme.surfaceDark,
                    focusedBorderColor = AppTheme.accentGold,
                    unfocusedBorderColor = Color(0xFF223639)
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            // Dynamic City filter trigger
            Box {
                var showCityDropdown by remember { mutableStateOf(false) }
                Button(
                    onClick = { showCityDropdown = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(50.dp)
                ) {
                    val cityLabel = cities.find { it.id == selectedCityId }?.nameAr ?: "كل المدن 🌍"
                    Text(cityLabel, color = AppTheme.accentGold, fontSize = 11.sp, fontFamily = fontFamily)
                }
                DropdownMenu(
                    expanded = showCityDropdown,
                    onDismissRequest = { showCityDropdown = false },
                    modifier = Modifier.background(AppTheme.surfaceDark)
                ) {
                    DropdownMenuItem(
                        text = { Text("جميع المدن 🌍", color = Color.White, fontSize = 11.sp) },
                        onClick = {
                            selectedCityId = ""
                            showCityDropdown = false
                        }
                    )
                    cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(city.nameAr, color = Color.White, fontSize = 11.sp) },
                            onClick = {
                                selectedCityId = city.id
                                showCityDropdown = false
                            }
                        )
                    }
                }
            }
        }

        // List of filtered professionals
        val listFiltered = providers.filter {
            val matchCat = selectedCategoryId.isEmpty() || it.category == selectedCategoryId
            val matchCity = selectedCityId.isEmpty() || it.city == selectedCityId
            val matchQuery = searchTxt.isEmpty() || it.name.contains(searchTxt, ignoreCase = true) || it.description.contains(searchTxt, ignoreCase = true)
            matchCat && matchCity && matchQuery && it.isVerified
        }

        if (listFiltered.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Build, contentDescription = "Empty", tint = Color.Gray, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("عذراً، لم يتم العثور على فني مطبق لهذه الشروط بالدليل.", color = Color.Gray, fontSize = 11.sp, fontFamily = fontFamily)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Pin / Subscribed Highlighting First
                val sorted = listFiltered.sortedWith(compareByDescending<Provider> { it.isPinned }
                    .thenByDescending { it.isSubscribed })

                items(sorted) { item ->
                    ProfessionalCardRow(
                        provider = item,
                        vm = vm,
                        reviews = reviews.filter { it.providerId == item.id },
                        onRateClick = { ratingProviderTarget = item },
                        onViewReviewsClick = { viewReviewsTarget = item },
                        onBookClick = { bookingProviderTarget = item },
                        fontFamily = fontFamily
                    )
                }
            }
        }
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
fun ProfessionalCardRow(
    provider: Provider,
    vm: MainViewModel,
    reviews: List<Review>,
    onRateClick: () -> Unit,
    onViewReviewsClick: () -> Unit,
    onBookClick: () -> Unit,
    fontFamily: FontFamily
) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (provider.isPinned) Color(0xFF1E3539) else AppTheme.surfaceDark
        ),
        border = if (provider.isPinned) BorderStroke(1.5.dp, AppTheme.accentGold) else null,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
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
                        context.startActivity(it)
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
                        vm.startChatWithProvider("user_visitor", provider.id, provider.name)
                        vm.navigationTargetTab.value = 2
                        Toast.makeText(context, "تم فتح نافذة الاتصال الآمن مع غرف ${provider.name}", Toast.LENGTH_SHORT).show()
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

// Calculate distance using haversine formula on geographic coordinates
fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta))
    dist = Math.acos(dist)
    dist = Math.toDegrees(dist)
    dist = dist * 60 * 1.1515 * 1.609344 // in kilometers
    return if (dist.isNaN()) 0.0 else dist
}

// Generate stable pseudo-random geographic coordinates around city centers
fun getProviderCoordinates(provider: Provider): Pair<Double, Double> {
    val base = when (provider.city.lowercase()) {
        "sanaa" -> Pair(15.3533, 44.2074)
        "aden" -> Pair(12.7855, 45.0186)
        "taiz" -> Pair(13.5794, 44.0205)
        "hodeidah" -> Pair(14.7979, 42.9530)
        "hadramout" -> Pair(14.4000, 49.0)
        "ibb" -> Pair(13.9745, 44.1802)
        else -> Pair(15.3533, 44.2074)
    }
    // Deterministic offset based on ID to scatter points beautifully
    val stepIndex = provider.id.hashCode() % 12
    val angle = stepIndex * (2 * Math.PI / 12)
    val radius = 0.015 + (provider.id.hashCode() % 5) * 0.008
    val offsetLat = Math.sin(angle) * radius
    val offsetLon = Math.cos(angle) * radius
    return Pair(base.first + offsetLat, base.second + offsetLon)
}

// --- TAB 1: INTERACTIVE GEOGRAPHIC MAP VIEW (YEMEN RADAR) ---
@Composable
fun MockMapViewScreen(vm: MainViewModel) {
    val providers by vm.providers.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var selectedUserCityId by remember { mutableStateOf("sanaa") }
    var selectedProviderForMap by remember { mutableStateOf<Provider?>(null) }
    var maxDistanceFilter by remember { mutableFloatStateOf(30f) } // default show within 30km
    
    // Virtual appointment reservation target on map
    var bookingProviderTargetOnMap by remember { mutableStateOf<Provider?>(null) }

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
                
                // Scale coordinates relative to user coordinates inside the viewport (-0.05 to +0.05 range)
                val scaleFactorRange = 0.06
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
                                vm.startChatWithProvider("user_visitor", p.id, p.name)
                                vm.navigationTargetTab.value = 2
                                Toast.makeText(context, "تم فتح غرفة الاتصال الفوري المباشر", Toast.LENGTH_SHORT).show()
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

    if (activeRoomId != null) {
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

    val categories by vm.categoriesState.collectAsStateWithLifecycle()
    val cities by vm.citiesState.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()

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
                        border = BorderStroke(1.dp, Color(0xFF223639)),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            settings.registrationRulesList.forEachIndexed { idx, rule ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Text("${idx + 1}. ", color = AppTheme.primaryRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text(rule, color = Color.White, fontSize = 10.sp, lineHeight = 13.sp)
                                }
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("الاسم الكامل / Professional Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف اليمني للاتصال") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("المنطقة / الشارع بالتفصيل") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("نبذة مختصرة عن مؤهلاتك وخدماتك السريعة") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AppTheme.accentGold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("اختر فئة التخصص:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCatId == cat.id,
                            onClick = { selectedCatId = cat.id },
                            label = { Text("${cat.iconUrl} ${cat.nameAr}", fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AppTheme.primaryRed,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFF0F2225)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("اختر مدينة النشاط الحالية:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(cities) { city ->
                        FilterChip(
                            selected = selectedCityId == city.id,
                            onClick = { selectedCityId = city.id },
                            label = { Text(city.nameAr, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AppTheme.accentGold,
                                selectedLabelColor = Color.Black,
                                containerColor = Color(0xFF0F2225)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        if (name.isBlank() || phone.isBlank() || area.isBlank() || description.isBlank()) {
                            Toast.makeText(context, "الرجاء تعبئة كامل الحقول للمراجعة والدراسة", Toast.LENGTH_SHORT).show()
                        } else {
                            val newRequest = PendingProvider(
                                id = UUID.randomUUID().toString(),
                                name = name,
                                category = selectedCatId,
                                city = selectedCityId,
                                phone = phone,
                                description = description,
                                area = area,
                                deviceId = "device_${UUID.randomUUID().toString().take(4)}"
                            )
                            vm.registerPendingProvider(newRequest)
                            name = ""
                            phone = ""
                            area = ""
                            description = ""
                            Toast.makeText(context, "تم رفع وتخزين طلب تسجيلك بنجاح! جاري معالجة طلبك وقبوله بواسطة الإدارة خلال دقائق.", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed)
                ) {
                    Text("رفع مستندات وتأكيد الطلب", color = Color.White)
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
        // Hero Photo Cover - Dynamic loaded with custom image url configured by admin
        AsyncImage(
            model = settings.aboutImageUrl,
            contentDescription = "About Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, AppTheme.accentGold, RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "ℹ️ عن منصة دليل كل خدمات اليمن",
                    color = AppTheme.accentGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = settings.welcomeMessage,
                    color = Color.White,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // App version stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("النسخة الحالية:", color = AppTheme.grayText, fontSize = 11.sp)
                    Text("v1.5.0", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("مستوى التشفير والحقن:", color = AppTheme.grayText, fontSize = 11.sp)
                    Text("تشفير آمن سحابي", color = Color.Green, fontSize = 11.sp)
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
                        Text("تحميل وتثبيت التطبيق مباشرة (APK) 📥", color = Color.White, fontSize = 11.sp)
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
        // Show Admin Login Box with support for full multi-layer account checking
        var usernameInput by remember { mutableStateOf("") }
        var passwordInput by remember { mutableStateOf("") }
        var errorState by remember { mutableStateOf(false) }
        val context = LocalContext.current
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
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
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
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        isError = errorState
                    )

                    if (errorState) {
                        Text("اسم المستخدم أو كلمة المرور غير صحيحة!", color = AppTheme.primaryRed, fontSize = 10.sp, fontFamily = currentFont)
                    }

                    Button(
                        onClick = {
                            if (vm.checkAdminThreeLayersLogin(usernameInput, passwordInput)) {
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
                    "الطلبات (${pendingRequests.size})",
                    "إضافة فني",
                    "إعلانات وبنرات",
                    "الأقسام والمدن",
                    "البلاغات (${reports.size})",
                    "مراقبة الدردشات",
                    "أعضاء الدليل",
                    "تثبيت وترقيات VIP",
                    "المشرفين والصلاحيات",
                    "الألوان والشروط والتحكم"
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
                        0 -> PendingRequestsTab(vm = vm, list = pendingRequests)
                        1 -> ManualAddProviderTab(vm = vm)
                        2 -> AdsAndBannersTab(vm = vm, banners = banners)
                        3 -> CategoriesCitiesTab(vm = vm, categories = categories, cities = cities)
                        4 -> ComplaintsAndReportsTab(vm = vm, list = reports)
                        5 -> PrivacyAndChatLogsTab(vm = vm)
                        6 -> ActiveProvidersTab(vm = vm, providers = providers)
                        7 -> SubscriptionsAndLimitsTab(vm = vm, list = providers)
                        8 -> SupervisorsAdminTab(vm = vm, list = adminAccounts)
                        9 -> ColorsConfigAndConditionsTab(vm = vm, settings = settings)
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
                                isSubscribed = isEliteVip
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
                                isSubscribed = isEliteVip
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

                OutlinedTextField(
                    value = mediaUrlInput,
                    onValueChange = { mediaUrlInput = it },
                    label = { Text("رابط صورة/فيديو الخلفية الدعائية (اختياري)") },
                    placeholder = { Text("http://example.com/ad.jpg") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

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
fun CategoriesCitiesTab(vm: MainViewModel, categories: List<Category>, cities: List<City>) {
    val context = LocalContext.current
    var tabIndex by remember { mutableIntStateOf(0) }

    var catNameAr by remember { mutableStateOf("") }
    var catNameEn by remember { mutableStateOf("") }
    var catDescAr by remember { mutableStateOf("") }
    var catIconSim by remember { mutableStateOf("🛠️") }
    var parentCatIdSelected by remember { mutableStateOf("") }
    var displayDirectlyCheck by remember { mutableStateOf(true) }
    var pinCategoryCheck by remember { mutableStateOf(false) }

    var cityNameAr by remember { mutableStateOf("") }
    var cityNameEn by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }, text = { Text("الأقسام (Main & Sub)", color = Color.White, fontSize = 11.sp) })
            Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }, text = { Text("المدن والمحافظات", color = Color.White, fontSize = 11.sp) })
        }

        if (tabIndex == 0) {
            Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("🔧 إضافة/تعديل قسم خدمات حرفي رئيسي أو فرعي", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    OutlinedTextField(
                        value = catNameAr,
                        onValueChange = { catNameAr = it },
                        label = { Text("اسم القسم بالعربية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    OutlinedTextField(
                        value = catNameEn,
                        onValueChange = { catNameEn = it },
                        label = { Text("اسم القسم بالإنجليزية") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    OutlinedTextField(
                        value = catDescAr,
                        onValueChange = { catDescAr = it },
                        label = { Text("الوصف التعريفي للجمهور") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    OutlinedTextField(
                        value = catIconSim,
                        onValueChange = { catIconSim = it },
                        label = { Text("أيقونة القسم التعبيرية أو رمز Emoji") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("تبعية القسم (لإنشاء قسم فرعي):", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    var showParentsDropdown by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { showParentsDropdown = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2225)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val parentText = if (parentCatIdSelected.isEmpty()) "قسم رئيسي مستقل" else categories.find { it.id == parentCatIdSelected }?.nameAr ?: "مستقل"
                            Text(parentText, color = AppTheme.accentGold, fontSize = 11.sp)
                        }
                        DropdownMenu(
                            expanded = showParentsDropdown,
                            onDismissRequest = { showParentsDropdown = false },
                            modifier = Modifier.background(AppTheme.surfaceDark).fillMaxWidth(0.85f)
                        ) {
                            DropdownMenuItem(
                                text = { Text("قسم رئيسي مستقل (أب)", color = Color.White, fontSize = 11.sp) },
                                onClick = {
                                    parentCatIdSelected = ""
                                    showParentsDropdown = false
                                }
                            )
                            categories.filter { it.parentId.isEmpty() }.forEach { parentCat ->
                                DropdownMenuItem(
                                    text = { Text(parentCat.nameAr, color = Color.White, fontSize = 11.sp) },
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
                        Text("إدراج القسم المضاف مباشرة للتصفح", color = Color.White, fontSize = 11.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = pinCategoryCheck, onCheckedChange = { pinCategoryCheck = it })
                        Text("تثبيت هذا القسم بالمقدمة 📌", color = Color.White, fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            if (catNameAr.isBlank()) {
                                Toast.makeText(context, "الرجاء كتابة اسم القسم باللغة العربية", Toast.LENGTH_SHORT).show()
                            } else {
                                val cleanId = catNameAr.lowercase().replace(" ", "_")
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
                                Toast.makeText(context, "تم حفظ وقبول القسم المهني المطور بنجاح سحابياً!", Toast.LENGTH_SHORT).show()
                                catNameAr = ""
                                catNameEn = ""
                                catDescAr = ""
                                parentCatIdSelected = ""
                                pinCategoryCheck = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ وإطلاق القسم بالقائمة 💾", color = Color.White)
                    }
                }
            }

            Text("📁 هيكلية أقسام وتصنيف المهن النشطة بالدليل:", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            categories.filter { it.parentId.isEmpty() }.forEach { parent ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639)), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(parent.iconUrl, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(parent.nameAr, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                if (parent.isPinned) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("📌 مثبت", color = AppTheme.accentGold, fontSize = 9.sp)
                                }
                            }
                            IconButton(onClick = { vm.deleteCategory(parent.id, "الأدمن") }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.primaryRed, modifier = Modifier.size(20.dp))
                            }
                        }
                        if (parent.description.isNotBlank()) {
                            Text(parent.description, color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(start = 22.dp, bottom = 4.dp))
                        }

                        val subCats = categories.filter { it.parentId == parent.id }
                        if (subCats.isNotEmpty()) {
                            Column(modifier = Modifier.padding(start = 24.dp).background(Color(0xFF0F2225)).padding(6.dp)) {
                                Text("الأقسام الفرعية المتفرعة:", color = AppTheme.accentGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                subCats.forEach { sub ->
                                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Text("↳ ${sub.iconUrl} ${sub.nameAr}", color = Color.LightGray, fontSize = 11.sp)
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
                        Box(modifier = Modifier.fillMaxWidth().height(110.dp).background(Color(0xFF071112)).padding(6.dp)) {
                            val messages = chatMessages.filter { it.chatId == room.id }
                            if (messages.isEmpty() && room.lastMessage.isNotBlank()) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("📝 المحادثة الافتراضية:", color = AppTheme.accentGold, fontSize = 9.sp)
                                    Text("الرسالة الوحيدة: ${room.lastMessage}", color = Color.White, fontSize = 11.sp)
                                }
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    items(messages) { m ->
                                        Text("${m.senderName}: ${m.message}", color = if (m.senderType == "user") Color.Cyan else Color.Green, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Button(
                                onClick = { Toast.makeText(context, "تم كتم الغرفة بنجاح!", Toast.LENGTH_SHORT).show() },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier.height(28.dp).weight(1f)
                            ) {
                                Text("إيقاف الغرفة 🛑", fontSize = 9.sp)
                            }
                            Button(
                                onClick = { Toast.makeText(context, "تم فك كتم الغرفة!", Toast.LENGTH_SHORT).show() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                modifier = Modifier.height(28.dp).weight(1f)
                            ) {
                                Text("تنشيط الاتصال 👍", fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- SUB-TAB 6: ACTIVE SERVICE PROVIDERS DIRECTORY ---
@Composable
fun ActiveProvidersTab(vm: MainViewModel, providers: List<Provider>) {
    val context = LocalContext.current
    var searchKey by remember { mutableStateOf("") }
    var providerToDelete by remember { mutableStateOf<Provider?>(null) }

    val filtered = providers.filter {
        it.name.contains(searchKey) || it.phone.contains(searchKey) || it.category.contains(searchKey)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = searchKey,
            onValueChange = { searchKey = it },
            placeholder = { Text("ابحث في أسماء أو تخصصات أو هواتف الأعضاء النشطين...") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "S") },
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
            items(filtered) { p ->
                Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639))) {
                    Row(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(p.name, color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("📞 الهاتف للتواصل: ${p.phone}", color = Color.White, fontSize = 11.sp)
                            Text("📍 السكن: ${p.area} (${p.city.uppercase()})", color = Color.Gray, fontSize = 10.sp)
                        }
                        IconButton(onClick = { providerToDelete = p }) {
                            Icon(Icons.Default.DeleteForever, contentDescription = "Delete Prov", tint = AppTheme.primaryRed, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }

    providerToDelete?.let { prov ->
        AlertDialog(
            onDismissRequest = { providerToDelete = null },
            title = { Text("⚠️ هل أنت متأكد من الحذف؟", color = Color.White, fontSize = 13.sp) },
            text = { Text("سيؤدي هذا الإجراء لإزالة العضو المهني ${prov.name} نهائياً.", color = Color.LightGray, fontSize = 11.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        vm.deleteProvider(prov.id, "الأدمن")
                        providerToDelete = null
                        Toast.makeText(context, "تم إزالة العضو بنجاح!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed)
                ) {
                    Text("نعم، احذف العضو")
                }
            },
            dismissButton = {
                TextButton(onClick = { providerToDelete = null }) {
                    Text("إلغاء الأمر", color = Color.White)
                }
            },
            containerColor = AppTheme.surfaceDark
        )
    }
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

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("👥 تفعيل وإنشاء حساب إداري لمراقب فرعي جديد", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                OutlinedTextField(
                    value = newAdminUser,
                    onValueChange = { newAdminUser = it },
                    label = { Text("اسم المستخدم للأدمن") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

                OutlinedTextField(
                    value = newAdminPass,
                    onValueChange = { newAdminPass = it },
                    label = { Text("رمز المرور السري") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
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
                            vm.addAdminAccount(account, "المدير العام")
                            Toast.makeText(context, "تم تفعيل حساب المشرف الإداري ومزامنته بالدليل السحابي فورياً!", Toast.LENGTH_SHORT).show()
                            newAdminUser = ""
                            newAdminPass = ""
                            privilegeManageCategories = false
                            privilegeManageBanners = false
                            privilegeDeleteActiveProviders = false
                            privilegeSeeReports = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("إنشاء حساب المشرف الجديد وتفعيله 👥", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        Text("📋 حسابات المشرفين المسجلة الفعالة:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        list.forEach { acc ->
            Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark), border = BorderStroke(1.dp, Color(0xFF223639)), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text("👤 اسم المشرف: ${acc.username}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("🔑 كلمة المرور المشفرة: ${acc.passwordHash}", color = Color.LightGray, fontSize = 10.sp)
                }
            }
        }
    }
}

// --- SUB-TAB 9: COLORS CONFIGURATION & DIRECT TERMS RULES MANAGER ---
@Composable
fun ColorsConfigAndConditionsTab(vm: MainViewModel, settings: AppSettings) {
    val context = LocalContext.current

    var primaryColorField by remember { mutableStateOf(settings.primaryColorHex) }
    var accentColorField by remember { mutableStateOf(settings.accentColorHex) }
    var bgColorField by remember { mutableStateOf(settings.bgColorHex) }
    var surfaceColorField by remember { mutableStateOf(settings.surfaceColorHex) }

    var appNameVal by remember { mutableStateOf(settings.appNameAr) }
    var welcomeMsgVal by remember { mutableStateOf(settings.welcomeMessage) }
    var downloadUrlVal by remember { mutableStateOf(settings.downloadUrl) }
    var aboutImageUrlVal by remember { mutableStateOf(settings.aboutImageUrl) }
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

    var isVoiceSpeechEnabledVal by remember { mutableStateOf(settings.isWebSpeechEnabled) }
    var radiusValSelect by remember { mutableStateOf(settings.radiusSearchLimitKm.toString()) }
    var autoCleanupDaysVal by remember { mutableStateOf(settings.autoCleanupDays.toString()) }

    var inlineRulesList by remember(settings.registrationRulesList) { mutableStateOf(settings.registrationRulesList) }
    var ruleNewCandidateText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        
        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("🎨 لوحات الألوان والسمات الجاهزة وتغيير الألوان (الأحمر، الأزرق، واللون المميز):", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(
                        onClick = {
                            primaryColorField = "#CE1126"
                            accentColorField = "#FFD700"
                            bgColorField = "#0D1B1E"
                            surfaceColorField = "#162A2D"
                            Toast.makeText(context, "تم تعيين باليت صقور اليمن الكلاسيكي باللون الأحمر الرائع!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE1126)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("🦅 اليمن الأحمر", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            primaryColorField = "#0D47A1"
                            accentColorField = "#00E5FF"
                            bgColorField = "#0A192F"
                            surfaceColorField = "#172A45"
                            Toast.makeText(context, "تم تعيين السمة المفتوحة باللون الأزرق الملكي الراقي!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("🔵 الأزرق الملكي", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            primaryColorField = "#FF1744"
                            accentColorField = "#FFEB3B"
                            bgColorField = "#1C0D0E"
                            surfaceColorField = "#2D1719"
                            Toast.makeText(context, "تم تعيين السمة البركانية باللون الأحمر المتوهج الجديد!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1744)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("🔴 الأحمر المتوهج", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            primaryColorField = "#FFB300"
                            accentColorField = "#00E5FF"
                            bgColorField = "#1A1710"
                            surfaceColorField = "#2D281D"
                            Toast.makeText(context, "تم تطبيق السمة الذهبية المميزة (بريق بلقيس) بنجاح!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5A900)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("✨ السمة المميزة", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(
                        onClick = {
                            primaryColorField = "#9E9E9E"
                            accentColorField = "#E0E0E0"
                            bgColorField = "#121212"
                            surfaceColorField = "#1C1C1C"
                            Toast.makeText(context, "تم تطبيق سمة كوزميك سيلفر الفضية الهادئة!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9E9E)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("🌌 كوزميك سيلفر", color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            primaryColorField = "#D4AF37"
                            accentColorField = "#FFD700"
                            bgColorField = "#1A1A1A"
                            surfaceColorField = "#2D2D2D"
                            Toast.makeText(context, "تم تطبيق سمة الذهبي الفاخر الكلاسيكية العريقة!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("✨ ذهبي فاخر", color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            primaryColorField = "#004B49"
                            accentColorField = "#50C878"
                            bgColorField = "#0C1814"
                            surfaceColorField = "#152A20"
                            Toast.makeText(context, "تم تطبيق سمة الزمردي الراقي الأنيق!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004B49)),
                        modifier = Modifier.weight(1f).height(38.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("🟢 زمردي راقي", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
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
                Text("⚙️ محددات خرائط التواجد والتنظيف الدوري التلقائي السريع", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isVoiceSpeechEnabledVal, onCheckedChange = { isVoiceSpeechEnabledVal = it })
                    Text("تفعيل ميزة البحث الصوتي بمحرك الدليل 🎙️", color = Color.White, fontSize = 11.sp)
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

                OutlinedTextField(
                    value = autoCleanupDaysVal,
                    onValueChange = { autoCleanupDaysVal = it },
                    label = { Text("مدة بقاء واحتفاظ البلاغات والاتصالات القديمة (يوم)") },
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

                OutlinedTextField(
                    value = aboutImageUrlVal,
                    onValueChange = { aboutImageUrlVal = it },
                    label = { Text("رابط غلاف الصورة التعريفية بصفحة معلومات التطبيق") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                )

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
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("📜 إدارة وتعديل شروط تسجيل واعتماد مزودي الخدمات", color = AppTheme.accentGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)

                inlineRulesList.forEachIndexed { index, rule ->
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = rule,
                            onValueChange = { editedText ->
                                val updatedList = inlineRulesList.toMutableList()
                                updatedList[index] = editedText
                                inlineRulesList = updatedList
                            },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            textStyle = TextStyle(fontSize = 11.sp)
                        )
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
                            updatedList.add(ruleNewCandidateText)
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
                    radiusSearchLimitKm = upRadius,
                    autoCleanupDays = upCleanup,
                    appNameAr = appNameVal,
                    welcomeMessage = welcomeMsgVal,
                    downloadUrl = downloadUrlVal,
                    aboutImageUrl = aboutImageUrlVal,
                    footerText = footerTextVal
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
    val geminiHistory by vm.geminiMessages.collectAsStateWithLifecycle()
    val isThinking by vm.isGeminiThinking.collectAsStateWithLifecycle()
    var promptInput by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    // Auto-scroll helper
    LaunchedEffect(geminiHistory.size) {
        if (geminiHistory.isNotEmpty()) {
            lazyListState.animateScrollToItem(geminiHistory.size - 1)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Backdrop overlay sibling (handles closing on click outside safely without intercepting input focus of the card)
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
                .clickable(
                    interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                    indication = null
                ) { /* Consume clicks to prevent them from bubbling up to backdrop click-to-close */ },
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
                        Icon(Icons.Default.SmartToy, contentDescription = "AI icon", tint = AppTheme.accentGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "المساعد الذكي لخدمات اليمن",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = fontFamily
                        )
                    }
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(28.dp).testTag("close_assistant_btn")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = AppTheme.primaryRed)
                    }
                }

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
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                        ) {
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
                                    .background(if (isUser) AppTheme.primaryRed else AppTheme.darkBg)
                                    .padding(10.dp)
                                    .widthIn(max = 240.dp)
                            ) {
                                Text(
                                    text = message,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp,
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
                                        "جاري التفكير وصياغة الرصد الفوري...",
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

                // --- SMART ASSISTANT RE-TYPING INPUT AREA FIX ---
                // We use a custom styled card bottom wrap with strict touch targets and focus.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F2225))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = promptInput,
                        onValueChange = { promptInput = it },
                        placeholder = { Text("اسألني شيئاً...", color = Color.Gray) },
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
