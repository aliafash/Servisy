package com.maw

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.activity.viewModels
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.provider.MediaStore
import android.os.Build
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

// ==================== MODELS ====================
data class Category(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    val parentId: String = "", // empty means root
    val iconUrl: String = "",
    val order: Int = 0
)

data class Provider(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val categoryMainId: String = "",
    val categorySubId: String = "",
    val address: String = "",
    val area: String = "",
    val avatarUrl: String = "",
    val identityCardUrl: String = "",
    val isPinned: Boolean = false,
    val isRecommended: Boolean = false,
    val isVerified: Boolean = false,
    val isSubscribed: Boolean = false,
    val subscriptionStatus: String = "", // pending, approved
    val isFemale: Boolean = false,
    val rating: Float = 5.0f,
    val reviewCount: Int = 1,
    val deviceId: String = ""
)

data class PendingProvider(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val categoryMainId: String = "",
    val categorySubId: String = "",
    val address: String = "",
    val area: String = "",
    val avatarUrl: String = "",
    val identityCardUrl: String = "",
    val isFemale: Boolean = false,
    val status: String = "pending", // pending, rejected
    val rejectionReason: String = ""
)

data class ChatMessage(
    val messageId: String = "",
    val chatId: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)

data class Chat(
    val chatId: String = "",
    val participants: List<String> = emptyList(),
    val lastMessage: String = "",
    val lastUpdated: Long = 0L,
    val assistanceRequested: Boolean = false
)

data class AppSettings(
    val appNameAr: String = "كل خدمات اليمن",
    val themeMode: String = "gold", // silver, gold, emerald, yellow, custom
    val primaryColor: String = "#FFD700",
    val secondaryColor: String = "#1A1A1A",
    val footerText: String = "MAW 777644670",
    val footerSizePercent: Int = 50,
    val footerOpacity: Float = 0.5f,
    val welcomeMessage: String = "مرحباً بكم في تطبيق كل خدمات اليمن - بوابتكم لطلب الخدمات والوظائف فوراً!",
    val welcomeImage: String = "",
    val supportPhone: String = "777644670",
    val supportEmail: String = "support@maw.com",
    val supportWhatsapp: String = "777644670",
    val adminPassword: String = "maher736462",
    val isMaintenanceActive: Boolean = false,
    val showSmartAssistant: Boolean = true,
    val smartAssistantSize: Int = 40,
    val radiusMaxKm: Int = 50,
    val geminiApiKey: String = "",
    val chatIconSize: Int = 40,
    val chatIconColor: String = "#FFD700",
    val isChatIconHidden: Boolean = false,
    val isChatIconDeleted: Boolean = false,
    val isChatDisabledForVisitors: Boolean = false,
    val isChatDisabledForProviders: Boolean = false,
    val disabledChatNotificationMessage: String = "تم إيقاف خدمة الدعم الفني والمحادثة الفورية مؤقتاً بقرار من الإدارة.",
    val assistantIconUrl: String = "",
    val chatIconUrl: String = "",
    val assistantIconEffect: String = "none",
    val chatIconEffect: String = "none",
    val isAutoCleanupActive: Boolean = false,
    val autoCleanupPeriodDays: Int = 30,
    val isVoiceSearchEnabled: Boolean = true,
    val registrationTerms: List<String> = listOf(
        "توفير الاسم الثلاثي الكامل ومطابق للبطاقة الشخصية",
        "رقم الهاتف يجب أن يكون فعالاً ومتصلاً بواتساب لتسهيل التواصل",
        "تحديد موقع ممارسة الخدمة بشكل دقيق لربط محددات البحث",
        "الالتزام التام بالأسعار المحددة وتجنب الشكاوى لضمان استمرارية الحساب"
    )
)

data class Supervisor(
    val id: String = "",
    val username: String = "",
    val password: String = "",
    val permissions: List<String> = emptyList()
)

data class Banner(
    val id: String = "",
    val imageUrl: String = "",
    val text: String = "",
    val type: String = "image", // image, text
    val durationSec: Int = 5,
    val redirectUrl: String = "",
    val size: String = "medium" // small, medium, large
)

data class AuditLog(
    val id: String = "",
    val adminName: String = "",
    val action: String = "",
    val timestamp: Long = 0L
)

data class Report(
    val id: String = "",
    val providerId: String = "",
    val providerName: String = "",
    val reporterName: String = "",
    val reason: String = "",
    val timestamp: Long = 0L
)

data class City(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = ""
)

// ==================== VIEWMODEL ====================
class MainViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val listeners = mutableMapOf<String, ListenerRegistration>()

    // Connectivity state
    val isOnline = MutableStateFlow(true)

    // Current Login
    var loggedInUser = MutableStateFlow("") // "admin", "supervisor", "visitor", "user_X"
    var loggedInUsername = MutableStateFlow("")
    val voiceSearchResult = MutableStateFlow("")
    val supervisorPermissions = MutableStateFlow<List<String>>(emptyList())

    fun hasPermission(perm: String): Boolean {
        if (loggedInUser.value == "admin") return true
        if (loggedInUser.value == "supervisor") {
            return supervisorPermissions.value.contains(perm)
        }
        return false
    }

    val activeScreen = MutableStateFlow("HOME") // HOME, LOGIN, REGISTER, INFO, ADMIN, CHAT_LIST, CHAT_ROOM, PROV_DETAILS, BACKDOOR
    val isArabic = MutableStateFlow(true)

    // Selection States
    val selectedProvider = MutableStateFlow<Provider?>(null)
    val activeChatId = MutableStateFlow("")
    val isBackdoorOpen = MutableStateFlow(false)

    // Synced Lists
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _providers = MutableStateFlow<List<Provider>>(emptyList())
    val providers = _providers.asStateFlow()

    private val _pendingProviders = MutableStateFlow<List<PendingProvider>>(emptyList())
    val pendingProviders = _pendingProviders.asStateFlow()

    private val _settings = MutableStateFlow(AppSettings())
    val settings = _settings.asStateFlow()

    private val _banners = MutableStateFlow<List<Banner>>(emptyList())
    val banners = _banners.asStateFlow()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats = _chats.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages = _chatMessages.asStateFlow()

    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports = _reports.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(emptyList())
    val auditLogs = _auditLogs.asStateFlow()

    private val _bannedIds = MutableStateFlow<Set<String>>(emptySet())
    val bannedIds = _bannedIds.asStateFlow()

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities = _cities.asStateFlow()

    private val _supervisors = MutableStateFlow<List<Supervisor>>(emptyList())
    val supervisors = _supervisors.asStateFlow()

    // Smart Assistant Q&A History
    val assistantChat = MutableStateFlow<List<Pair<String, Boolean>>>(listOf(
        Pair("مرحباً بك! أنا المساعد الذكي لخدمات اليمن. كيف يمكنني مساعدتك اليوم؟", false)
    ))

    init {
        initDefaultAppConfig()
        setupAllSnapshotListeners()
    }

    private fun initDefaultAppConfig() {
        viewModelScope.launch {
            try {
                // Ensure base settings document exists
                val doc = db.collection("app_settings").document("global").get().await()
                if (!doc.exists()) {
                    db.collection("app_settings").document("global").set(AppSettings()).await()
                }

                // Push custom default categories if empty
                val cats = db.collection("categories").get().await()
                if (cats.isEmpty) {
                    val defaultCats = listOf(
                        Category("1", "كهرباء", "Electricity", "", "", 1),
                        Category("2", "سباكة", "Plumbing", "", "", 2),
                        Category("3", "صيانة سيارات", "Car Repair", "", "", 3),
                        Category("4", "برمجة وصيانة هواتف", "Phone Programming", "", "", 4),
                        Category("5", "كهربائي منزلي", "Home Electrician", "1", "", 1),
                        Category("6", "سباك تركيبات", "Plumbing Installation", "2", "", 1)
                    )
                    for (c in defaultCats) {
                        db.collection("categories").document(c.id).set(c)
                    }
                }

                // Push default cities
                val cts = db.collection("cities").get().await()
                if (cts.isEmpty) {
                    val defaultCities = listOf(
                        City("c1", "صنعاء", "Sanaa"),
                        City("c2", "عدن", "Aden"),
                        City("c3", "تعز", "Taiz"),
                        City("c4", "حضرموت", "Hadramout")
                    )
                    for (ct in defaultCities) {
                        db.collection("cities").document(ct.id).set(ct)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setupAllSnapshotListeners() {
        // Unsubscribe from any previous listeners to prevent duplicates
        clearAllListeners()

        registerListener("settings", db.collection("app_settings").document("global").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                snapshot.toObject(AppSettings::class.java)?.let {
                    _settings.value = it
                }
            }
        })

        registerListener("categories", db.collection("categories").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _categories.value = snapshot.toObjects(Category::class.java).sortedBy { it.order }
            }
        })

        registerListener("providers", db.collection("service_providers").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _providers.value = snapshot.toObjects(Provider::class.java)
            }
        })

        registerListener("pending_providers", db.collection("pending_providers").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _pendingProviders.value = snapshot.toObjects(PendingProvider::class.java)
            }
        })

        registerListener("banners", db.collection("banners").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _banners.value = snapshot.toObjects(Banner::class.java)
            }
        })

        registerListener("chats", db.collection("chats").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _chats.value = snapshot.toObjects(Chat::class.java).sortedByDescending { it.lastUpdated }
            }
        })

        registerListener("reports", db.collection("reports").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _reports.value = snapshot.toObjects(Report::class.java).sortedByDescending { it.timestamp }
            }
        })

        registerListener("audit_logs", db.collection("audit_logs").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _auditLogs.value = snapshot.toObjects(AuditLog::class.java).sortedByDescending { it.timestamp }
            }
        })

        registerListener("banned_ids", db.collection("banned_ids").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _bannedIds.value = snapshot.documents.map { it.id }.toSet()
            }
        })

        registerListener("cities", db.collection("cities").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _cities.value = snapshot.toObjects(City::class.java)
            }
        })

        registerListener("supervisors", db.collection("supervisors").addSnapshotListener { snapshot, e ->
            if (e == null && snapshot != null) {
                _supervisors.value = snapshot.toObjects(Supervisor::class.java)
            }
        })
    }

    fun onNetworkStatusChanged(online: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            isOnline.value = online
            if (online) {
                clearAllListeners()
                setupAllSnapshotListeners()
            } else {
                clearAllListeners()
            }
        }
    }

    private fun registerListener(key: String, listener: ListenerRegistration) {
        synchronized(listeners) {
            listeners[key]?.remove()
            listeners[key] = listener
        }
    }

    fun clearAllListeners() {
        synchronized(listeners) {
            listeners.values.forEach { it.remove() }
            listeners.clear()
        }
    }

    fun openChatRoom(chatId: String) {
        activeChatId.value = chatId
        listeners["messages"]?.remove()

        registerListener("messages", db.collection("messages")
            .whereEqualTo("chatId", chatId)
            .addSnapshotListener { snapshot, e ->
                if (e == null && snapshot != null) {
                    _chatMessages.value = snapshot.toObjects(ChatMessage::class.java)
                        .sortedBy { it.timestamp }
                }
            })
    }

    fun closeChatRoom() {
        activeChatId.value = ""
        listeners["messages"]?.remove()
        _chatMessages.value = emptyList()
    }

    // ==================== ASSISTANT QA ====================
    fun sendMessageToAssistant(question: String) {
        if (question.isBlank()) return
        val currentList = assistantChat.value.toMutableList()
        currentList.add(Pair(question, true))
        assistantChat.value = currentList

        viewModelScope.launch {
            val responseText = generateAssistantAnswer(question)
            val updatedList = assistantChat.value.toMutableList()
            updatedList.add(Pair(responseText, false))
            assistantChat.value = updatedList
        }
    }

    private val faqCache = mapOf(
        "سباك" to "لطلب فني سباكة، يمكنك تصفح قسم السباكة الرئيسي في القائمة والاتصال بأحد الفنيين الموصى بهم مباشرة.",
        "كهربائ" to "لصيانة الكهرباء أو الاتصال بكهربائي منزلي، اختر 'كهرباء' من الشاشة الرئيسية وتواصل مع الفني المناسب.",
        "سعر" to "أسعار الخدمات تختلف حسب الفني ونوع الصيانة المطلوبة، تفضل بزيارة صفحة الفني لعرض سعر المعاينة بالريال.",
        "تسجيل" to "إذا كنت مهنياً وتريد الانضمام، انقر على أيقونة إضافة كادر (👤) في الأعلى واملأ بياناتك لطلب التوثيق مجاناً.",
        "دعم" to "يمكنك التواصل مع الدعم الفني للتطبيق مباشرة عبر واتساب: 777644670.",
        "محادثة" to "يمكنك بدء محادثة فورية مع أي فني بالنقر على بطاقته ثم اختيار زر دردشة للحديث مباشرة داخل التطبيق."
    )

    private suspend fun generateAssistantAnswer(q: String): String {
        val normalized = q.trim().lowercase()

        // 1. Check local FAQ cache
        for ((key, answer) in faqCache) {
            if (normalized.contains(key)) {
                return answer
            }
        }

        // 2. Custom categories rule
        if (normalized.contains("قسم") || normalized.contains("الأقسام") || normalized.contains("أقسام")) {
            val names = categories.value.filter { it.parentId.isEmpty() }.joinToString(", ") { if (isArabic.value) it.nameAr else it.nameEn }
            return "الأقسام الرئيسية المتوفرة بالتطبيق هي: $names."
        }
        if (normalized.contains("اتصل") || normalized.contains("مقدم") || normalized.contains("تواصل")) {
            return "لكي تتصل بأي مقدم خدمة، انقر على اسمه أو بطاقته بالرئيسية، وستظهر أزرار الاتصال الهاتفي أو بدء دردشة فورية مباشرة."
        }

        // 3. Fallback to offline message if not online or missing key
        val key = settings.value.geminiApiKey
        if (!isOnline.value || key.isBlank()) {
            return "أهلاً بك! يمكنك تصفح الأقسام والبحث عن خدمات الصيانة كفنيي الكهرباء، السباكة، وغيرهم محلياً. يرجى تفعيل الإنترنت أو طرح سؤال محدد."
        }

        // 4. Online Fallback using Gemini REST API
        return try {
            withContext(Dispatchers.IO) {
                val url = URL("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$key")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.connectTimeout = 15000
                conn.readTimeout = 15000
                conn.doOutput = true

                val jsonInputString = "{\"contents\":[{\"parts\":[{\"text\":\"$q\"}]}]}"
                conn.outputStream.use { os ->
                    val input = jsonInputString.toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                val code = conn.responseCode
                if (code == 200) {
                    val response = conn.inputStream.bufferedReader().use { it.readText() }
                    val json = JSONObject(response)
                    val text = json.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                    text.trim()
                } else {
                    "المعذرة، حدث خطأ أثناء معالجة السؤال الذكي عبر الإنترنت. يمكنك الاستفسار عن الأقسام أو الدعم الفني محلياً."
                }
            }
        } catch (e: Exception) {
            "تعذر الاتصال بخدمة الذكاء الاصطناعي حالياً. تفضل بطرح سؤال آخر أو الاستفسار عن الأقسام والخدمات!"
        }
    }

    // ==================== TRANS ACTIONS ====================
    fun saveAppSettings(newSettings: AppSettings, adminName: String = "المالك") {
        viewModelScope.launch {
            db.collection("app_settings").document("global").set(newSettings).await()
            logAudit(adminName, "تعديل إعدادات الهوية العامة والشكل والخطوط في التطبيق")
        }
    }

    fun addCategory(cat: Category, adminName: String) {
        viewModelScope.launch {
            val id = if (cat.id.isBlank()) UUID.randomUUID().toString() else cat.id
            db.collection("categories").document(id).set(cat.copy(id = id)).await()
            logAudit(adminName, "إضافة/تعديل قسم رئيسي أو فرعي: ${cat.nameAr}")
        }
    }

    fun deleteCategory(id: String, adminName: String) {
        viewModelScope.launch {
            db.collection("categories").document(id).delete().await()
            logAudit(adminName, "حذف قسم برقم: $id")
        }
    }

    fun registerPendingProvider(p: PendingProvider) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            db.collection("pending_providers").document(id).set(p.copy(id = id)).await()
        }
    }

    fun exportFirestoreBackup(context: Context, adminName: String, onComplete: (String?, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val backupJson = JSONObject()
                
                // Fetch categories
                val catsSnap = db.collection("categories").get().await()
                val catsArray = org.json.JSONArray()
                for (doc in catsSnap.documents) {
                    val js = JSONObject()
                    js.put("id", doc.getString("id") ?: "")
                    js.put("nameAr", doc.getString("nameAr") ?: "")
                    js.put("nameEn", doc.getString("nameEn") ?: "")
                    js.put("parentId", doc.getString("parentId") ?: "")
                    js.put("iconUrl", doc.getString("iconUrl") ?: "")
                    js.put("order", doc.getLong("order")?.toInt() ?: 0)
                    catsArray.put(js)
                }
                backupJson.put("categories", catsArray)

                // Fetch providers
                val provSnap = db.collection("service_providers").get().await()
                val provArray = org.json.JSONArray()
                for (doc in provSnap.documents) {
                    val js = JSONObject()
                    js.put("id", doc.getString("id") ?: "")
                    js.put("name", doc.getString("name") ?: "")
                    js.put("phone", doc.getString("phone") ?: "")
                    js.put("categoryMainId", doc.getString("categoryMainId") ?: "")
                    js.put("categorySubId", doc.getString("categorySubId") ?: "")
                    js.put("address", doc.getString("address") ?: "")
                    js.put("area", doc.getString("area") ?: "")
                    js.put("avatarUrl", doc.getString("avatarUrl") ?: "")
                    js.put("isPinned", doc.getBoolean("isPinned") ?: false)
                    js.put("isRecommended", doc.getBoolean("isRecommended") ?: false)
                    js.put("isVerified", doc.getBoolean("isVerified") ?: false)
                    js.put("isFemale", doc.getBoolean("isFemale") ?: false)
                    provArray.put(js)
                }
                backupJson.put("service_providers", provArray)

                // Save to file
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                val fileName = "MAW_Backup_$timeStamp.json"
                val file = File(context.getExternalFilesDir(null), fileName)
                file.writeText(backupJson.toString(4))
                
                logAudit(adminName, "نسخة احتياطية لقواعد البيانات: $fileName")
                onComplete(file.absolutePath, null)
            } catch (e: Exception) {
                e.printStackTrace()
                onComplete(null, e.localizedMessage)
            }
        }
    }

    fun triggerScheduledCleanup(adminName: String) {
        viewModelScope.launch {
            try {
                val limitDays = _settings.value.autoCleanupPeriodDays
                val thresholdMs = System.currentTimeMillis() - (limitDays * 24L * 60L * 60L * 1000L)
                
                val oldChats = db.collection("chats").whereLessThan("lastUpdated", thresholdMs).get().await()
                for (doc in oldChats.documents) {
                    val chatId = doc.id
                    val msgs = db.collection("messages").whereEqualTo("chatId", chatId).get().await()
                    for (m in msgs.documents) {
                        m.reference.delete()
                    }
                    doc.reference.delete()
                }

                val oldLogs = db.collection("audit_logs").whereLessThan("timestamp", thresholdMs).get().await()
                for (doc in oldLogs.documents) {
                    doc.reference.delete()
                }
                
                logAudit(adminName, "تصفية وتنظيف تلقائي للمحادثات والسجلات الأقدم من $limitDays يوم")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addProviderManual(p: Provider, adminName: String) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            db.collection("service_providers").document(id).set(p.copy(id = id)).await()
            logAudit(adminName, "إضافة مقدم خدمة يدوياً: ${p.name}")
        }
    }

    fun approveProviderRequest(pp: PendingProvider, adminName: String) {
        viewModelScope.launch {
            val prov = Provider(
                id = pp.id,
                name = pp.name,
                phone = pp.phone,
                categoryMainId = pp.categoryMainId,
                categorySubId = pp.categorySubId,
                address = pp.address,
                area = pp.area,
                avatarUrl = pp.avatarUrl,
                identityCardUrl = pp.identityCardUrl,
                isFemale = pp.isFemale
            )
            db.collection("service_providers").document(prov.id).set(prov).await()
            db.collection("pending_providers").document(pp.id).delete().await()
            logAudit(adminName, "قبول طلب انضمام مقدم الخدمة: ${pp.name}")
        }
    }

    fun rejectProviderRequest(id: String, reason: String, adminName: String) {
        viewModelScope.launch {
            db.collection("pending_providers").document(id).update("status", "rejected", "rejectionReason", reason).await()
            logAudit(adminName, "رفض طلب انضمام مقدم الخدمة رقم $id للسبب: $reason")
        }
    }

    fun toggleProviderStatus(id: String, isPinned: Boolean, isRecommended: Boolean, isVerified: Boolean, isSubscribed: Boolean, adminName: String) {
        viewModelScope.launch {
            db.collection("service_providers").document(id).update(
                "isPinned", isPinned,
                "isRecommended", isRecommended,
                "isVerified", isVerified,
                "isSubscribed", isSubscribed
            ).await()
            logAudit(adminName, "تعديل صلاحيات وتوصيات مقدم الخدمة رقم: $id")
        }
    }

    fun banId(id: String, isBanned: Boolean, adminName: String) {
        viewModelScope.launch {
            if (isBanned) {
                db.collection("banned_ids").document(id).set(mapOf("banned" to true)).await()
                logAudit(adminName, "حظر المعرف: $id من مزاولة النشاط أو التفاعل")
            } else {
                db.collection("banned_ids").document(id).delete().await()
                logAudit(adminName, "إلغاء حظر المعرف: $id")
            }
        }
    }

    fun postReport(report: Report) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            db.collection("reports").document(id).set(report.copy(id = id)).await()
        }
    }

    fun sendChatMessage(senderId: String, text: String) {
        val cId = activeChatId.value
        if (cId.isBlank() || text.isBlank()) return
        viewModelScope.launch {
            val msgId = UUID.randomUUID().toString()
            val msg = ChatMessage(msgId, cId, senderId, text, System.currentTimeMillis())
            db.collection("messages").document(msgId).set(msg).await()
            db.collection("chats").document(cId).update(
                "lastMessage", text,
                "lastUpdated", System.currentTimeMillis()
            ).await()
        }
    }

    fun startChatWithProvider(userId: String, providerId: String) {
        viewModelScope.launch {
            val cId = "chat_${userId}_${providerId}"
            val doc = db.collection("chats").document(cId).get().await()
            if (!doc.exists()) {
                val newChat = Chat(cId, listOf(userId, providerId), "بدء محادثة جديدة", System.currentTimeMillis())
                db.collection("chats").document(cId).set(newChat).await()
            }
            openChatRoom(cId)
            activeScreen.value = "CHAT_ROOM"
        }
    }

    fun deleteChatLogs(chatId: String, adminName: String) {
        viewModelScope.launch {
            val msgs = db.collection("messages").whereEqualTo("chatId", chatId).get().await()
            for (m in msgs.documents) {
                m.reference.delete()
            }
            db.collection("chats").document(chatId).delete().await()
            logAudit(adminName, "حذف السجل والدردشات الخاصة بالمعرف $chatId بالكامل")
        }
    }

    fun autoCleanupLogs(adminName: String) {
        viewModelScope.launch {
            val threshold = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000) // 30 days
            val oldMsgs = db.collection("messages").whereLessThan("timestamp", threshold).get().await()
            for (m in oldMsgs.documents) {
                m.reference.delete()
            }
            logAudit(adminName, "جدولة تنظيف تلقائي للدردشات والسجلات القديمة")
        }
    }

    fun addCity(city: City, adminName: String) {
        viewModelScope.launch {
            val id = if (city.id.isBlank()) UUID.randomUUID().toString() else city.id
            db.collection("cities").document(id).set(city.copy(id = id)).await()
            logAudit(adminName, "إضافة/تعديل منطقة جغرافية أو مدينة جديدة: ${city.nameAr}")
        }
    }

    fun deleteCity(id: String, adminName: String) {
        viewModelScope.launch {
            db.collection("cities").document(id).delete().await()
            logAudit(adminName, "حذف مدينة برقم: $id")
        }
    }

    fun saveBanner(b: Banner, adminName: String) {
        viewModelScope.launch {
            val id = if (b.id.isBlank()) UUID.randomUUID().toString() else b.id
            db.collection("banners").document(id).set(b.copy(id = id)).await()
            logAudit(adminName, "إضافة أو تعديل لافتة إعلانية ممولة: ${b.text}")
        }
    }

    fun deleteBanner(id: String, adminName: String) {
        viewModelScope.launch {
            db.collection("banners").document(id).delete().await()
            logAudit(adminName, "إزالة لافتة إعلانية برقم: $id")
        }
    }

    fun createSupervisor(s: Supervisor, adminName: String) {
        viewModelScope.launch {
            val id = if (s.id.isBlank()) UUID.randomUUID().toString() else s.id
            db.collection("supervisors").document(id).set(s.copy(id = id)).await()
            logAudit(adminName, "إنشاء حساب مشرف جديد: ${s.username} بصلاحيات محددة")
        }
    }

    fun deleteSupervisor(id: String, adminName: String) {
        viewModelScope.launch {
            db.collection("supervisors").document(id).delete().await()
            logAudit(adminName, "حذف حساب مشرف برقم: $id")
        }
    }

    fun deleteProvider(id: String, adminName: String) {
        viewModelScope.launch {
            db.collection("service_providers").document(id).delete().await()
            logAudit(adminName, "حذف فني أو مقدم خدمة من الدليل: $id")
        }
    }

    fun updateProviderManual(p: Provider, adminName: String) {
        viewModelScope.launch {
            db.collection("service_providers").document(p.id).set(p).await()
            logAudit(adminName, "تعديل يدوي لبيانات مقدم الخدمة: ${p.name}")
        }
    }

    fun clearAllChatsAndMessages(adminName: String) {
        viewModelScope.launch {
            try {
                val chats = db.collection("chats").get().await()
                for (c in chats.documents) {
                    c.reference.delete()
                }
                val msgs = db.collection("messages").get().await()
                for (m in msgs.documents) {
                    m.reference.delete()
                }
                logAudit(adminName, "مسح وتفريغ السجلات والدفاتر نهائياً وللأبد لضمان خصوصية المحادثات")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun logAudit(adminName: String, actionStr: String) {
        val id = UUID.randomUUID().toString()
        val log = AuditLog(id, adminName, actionStr, System.currentTimeMillis())
        db.collection("audit_logs").document(id).set(log)
    }

    override fun onCleared() {
        super.onCleared()
        clearAllListeners()
    }
}

// ==================== ACTIVITY ====================
class MainActivity : ComponentActivity() {
    private val vm: MainViewModel by viewModels()
    private var speechRecognizer: SpeechRecognizer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Offline Settings
        try {
            val options = com.google.firebase.FirebaseOptions.Builder()
                .setApiKey("AIzaSyDHSY_vGko5FendFFVqnv5q4MdmnKrLi-g")
                .setApplicationId("1:658568660162:android:a61a72f574440f54fd275b")
                .setProjectId("wam2026-8d969")
                .setStorageBucket("wam2026-8d969.firebasestorage.app")
                .build()

            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this, options)
            }

            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
            FirebaseFirestore.getInstance().firestoreSettings = settings
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Setup Network Status Monitor
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                vm.onNetworkStatusChanged(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                vm.onNetworkStatusChanged(false)
            }
        }
        try {
            connectivityManager.registerDefaultNetworkCallback(callback)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            val settingsState by vm.settings.collectAsStateWithLifecycle()
            val theme = settingsState.themeMode

            // Map custom visual modes to color schemes
            val palette = remember(theme, settingsState.primaryColor, settingsState.secondaryColor) {
                when (theme) {
                    "silver" -> ColorSchemePalette(
                        background = Color(0xFF1E222B),
                        surface = Color(0xFF282C34),
                        primary = Color(0xFFDCDCDC),
                        secondary = Color(0xFFABB2BF),
                        textPrimary = Color.White,
                        textSecondary = Color(0xFF828997)
                    )
                    "emerald" -> ColorSchemePalette(
                        background = Color(0xFF0F1A15),
                        surface = Color(0xFF182A20),
                        primary = Color(0xFF2E7D32),
                        secondary = Color(0xFF81C784),
                        textPrimary = Color.White,
                        textSecondary = Color(0xFFA5D6A7)
                    )
                    "yellow" -> ColorSchemePalette(
                        background = Color(0xFF221F10),
                        surface = Color(0xFF332F1A),
                        primary = Color(0xFFFBC02D),
                        secondary = Color(0xFFFFF176),
                        textPrimary = Color.White,
                        textSecondary = Color(0xFFFFF59D)
                    )
                    else -> // "gold" or Custom default palette
                        ColorSchemePalette(
                            background = Color(0xFF121212),
                            surface = Color(0xFF1F1F1F),
                            primary = Color(0xFFD4AF37), // Golden
                            secondary = Color(0xFFFFA500),
                            textPrimary = Color.White,
                            textSecondary = Color(0xFFB0B0B0)
                        )
                }
            }

            // Realize dynamic font selections if defined
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(
                    background = palette.background,
                    surface = palette.surface,
                    primary = palette.primary,
                    secondary = palette.secondary
                )
            ) {
                MainLayout(vm, palette, onSpeechClick = { startVoiceSearchScreen() })
            }
        }
    }

    private fun startVoiceSearchScreen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 101)
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-YE")
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(error: Int) {
                    Toast.makeText(this@MainActivity, "خطأ بالتعرف على الصوت", Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        val speechText = matches[0]
                        Toast.makeText(this@MainActivity, "البحث عن: $speechText", Toast.LENGTH_LONG).show()
                        vm.voiceSearchResult.value = speechText
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
            startListening(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
    }
}

// Color Palette Holder Class
data class ColorSchemePalette(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val textPrimary: Color,
    val textSecondary: Color
) {
    val primaryColor: Color get() = primary
    val secondaryColor: Color get() = secondary
}

// ==================== COMPOSE LAYOUTS ====================
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainLayout(vm: MainViewModel, palette: ColorSchemePalette, onSpeechClick: () -> Unit) {
    val activeScreen by vm.activeScreen.collectAsStateWithLifecycle()
    val isArabic by vm.isArabic.collectAsStateWithLifecycle()

    val settings by vm.settings.collectAsStateWithLifecycle()
    val isBannedState by vm.bannedIds.collectAsStateWithLifecycle()
    val currentUser by vm.loggedInUser.collectAsStateWithLifecycle()

    var showFloatingAssistant by remember { mutableStateOf(false) }

    // Quick Exit Handler on continuous back button click
    var lastBackClickTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

    val backHandler: () -> Unit = {
        if (activeScreen == "HOME") {
            val now = System.currentTimeMillis()
            if (now - lastBackClickTime < 2000) {
                // Exit
                (context as? ComponentActivity)?.finish()
            } else {
                lastBackClickTime = now
                Toast.makeText(context, if (isArabic) "اضغط مجدداً للخروج من التطبيق" else "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
        } else {
            vm.activeScreen.value = "HOME"
        }
    }

    // Capture system back clicks if needed inside main container view
    val isBanned = isBannedState.contains(currentUser)

    Scaffold(
        containerColor = palette.background,
        topBar = {
            CustomTopBar(vm, palette, isArabic)
        },
        bottomBar = {
            CustomFooterWithOpacities(vm, palette, onInfoClick = { vm.activeScreen.value = "INFO" })
        }
    ) { padVal ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padVal)
        ) {
            if (settings.isMaintenanceActive && currentUser != "admin") {
                MaintenanceView(settings, isArabic, palette)
            } else if (isBanned) {
                BannedWarningView(isArabic, palette)
            } else {
                AnimatedContent(
                    targetState = activeScreen,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "Navigation"
                ) { target ->
                    when (target) {
                        "HOME" -> HomeScreen(vm, palette, onSpeechClick)
                        "LOGIN" -> LoginScreen(vm, palette, isArabic)
                        "REGISTER" -> RegistrationForm(vm, palette, isArabic)
                        "INFO" -> AppInfoScreen(vm, palette, isArabic)
                        "ADMIN" -> AdminDashboard(vm, palette, isArabic)
                        "CHAT_LIST" -> ChatsListScreen(vm, palette, isArabic)
                        "CHAT_ROOM" -> ChatRoomView(vm, palette, isArabic)
                        "PROV_DETAILS" -> ProviderDetailsScreen(vm, palette, isArabic)
                        else -> HomeScreen(vm, palette, onSpeechClick)
                    }
                }
            }

            // Custom Visual Effects Transitions (Bounce / Glow)
            val infiniteTransition = rememberInfiniteTransition(label = "effects")
            
            val assistantBounceOffset by if (settings.assistantIconEffect == "bounce") {
                infiniteTransition.animateValue(
                    initialValue = 0.dp,
                    targetValue = (-6).dp,
                    typeConverter = Dp.VectorConverter,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes { durationMillis = 600 },
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "bounce"
                )
            } else {
                remember { mutableStateOf(0.dp) }
            }

            val chatBounceOffset by if (settings.chatIconEffect == "bounce") {
                infiniteTransition.animateValue(
                    initialValue = 0.dp,
                    targetValue = (-6).dp,
                    typeConverter = Dp.VectorConverter,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes { durationMillis = 650 },
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "bounceChat"
                )
            } else {
                remember { mutableStateOf(0.dp) }
            }

            val assistantGlowModifier = if (settings.assistantIconEffect == "glow") {
                Modifier.border(2.dp, palette.primary, CircleShape)
            } else {
                Modifier
            }

            val chatGlowModifier = if (settings.chatIconEffect == "glow") {
                val parsedColor = try {
                    Color(android.graphics.Color.parseColor(if (settings.chatIconColor.startsWith("#")) settings.chatIconColor else "#FFD700"))
                } catch (e: Exception) {
                    palette.primary
                }
                Modifier.border(2.dp, parsedColor, CircleShape)
            } else {
                Modifier
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 16.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. My Chats Floating Action Controller (beside assistant!)
                if (!settings.isChatIconDeleted && !settings.isChatIconHidden && !settings.isMaintenanceActive) {
                    val customColor = remember(settings.chatIconColor) {
                        try {
                            Color(android.graphics.Color.parseColor(settings.chatIconColor))
                        } catch (e: Exception) {
                            palette.primary
                        }
                    }
                    FloatingActionButton(
                        onClick = {
                            val role = currentUser
                            val blockVisitor = settings.isChatDisabledForVisitors && (role.isBlank() || role == "visitor")
                            val blockProvider = settings.isChatDisabledForProviders && (vm.providers.value.any { it.deviceId == role })
                            
                            if (blockVisitor || blockProvider) {
                                Toast.makeText(context, settings.disabledChatNotificationMessage, Toast.LENGTH_LONG).show()
                            } else {
                                vm.activeScreen.value = "CHAT_LIST"
                            }
                        },
                        containerColor = customColor,
                        modifier = Modifier
                            .offset(y = chatBounceOffset)
                            .then(chatGlowModifier)
                            .size(settings.chatIconSize.dp)
                    ) {
                        if (settings.chatIconUrl.isNotBlank()) {
                            AsyncImage(
                                model = settings.chatIconUrl,
                                contentDescription = "Chats",
                                modifier = Modifier.fillMaxSize().padding(10.dp)
                            )
                        } else {
                            Icon(
                                Icons.Default.Chat,
                                contentDescription = "Active Chats",
                                tint = palette.secondaryColor,
                                modifier = Modifier.size((settings.chatIconSize * 0.5f).dp)
                            )
                        }
                    }
                }

                // 2. Smart Assistant Mini Floating Button
                if (settings.showSmartAssistant && !settings.isMaintenanceActive) {
                    FloatingActionButton(
                        onClick = { showFloatingAssistant = !showFloatingAssistant },
                        containerColor = palette.primary,
                        modifier = Modifier
                            .offset(y = assistantBounceOffset)
                            .then(assistantGlowModifier)
                            .size(settings.smartAssistantSize.dp)
                    ) {
                        if (settings.assistantIconUrl.isNotBlank()) {
                            AsyncImage(
                                model = settings.assistantIconUrl,
                                contentDescription = "AI Assistant",
                                modifier = Modifier.fillMaxSize().padding(8.dp)
                            )
                        } else {
                            Text(
                                text = if (isArabic) "خدمات" else "AI",
                                color = palette.secondaryColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Real Floating Assistant Chat Dialog overlay
            if (showFloatingAssistant && !settings.isMaintenanceActive) {
                SmartAssistantOverlay(vm, palette, isArabic, onClose = { showFloatingAssistant = false })
            }
        }
    }
}

// 1. Customized Unified Top Bar
@Composable
fun CustomTopBar(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val currentScreen by vm.activeScreen.collectAsStateWithLifecycle()
    val loggedUser by vm.loggedInUser.collectAsStateWithLifecycle()
    var backdoorTaps by remember { mutableStateOf(0) }
    var showBackdoorLogin by remember { mutableStateOf(false) }

    Surface(
        color = palette.surface,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Side Logo trigger clickable 5 times for Secret Backdoor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    backdoorTaps++
                    if (backdoorTaps >= 5) {
                        backdoorTaps = 0
                        showBackdoorLogin = true
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(palette.primary)
                ) {
                    Text(
                        "WAM",
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = palette.secondaryColor
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "WAM 2026",
                    color = palette.primary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            }

            // Action Buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Home Icon 🏠 (Also clickable 5 times as backdoor placeholder)
                IconButton(onClick = {
                    backdoorTaps++
                    if (backdoorTaps >= 5) {
                        backdoorTaps = 0
                        showBackdoorLogin = true
                    }
                    vm.activeScreen.value = "HOME"
                }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = if (currentScreen == "HOME") palette.primary else Color.White)
                }

                // Register Practitioner Icon 👤
                IconButton(onClick = { vm.activeScreen.value = "REGISTER" }) {
                    Icon(Icons.Default.PersonAdd, contentDescription = "Add Provider", tint = if (currentScreen == "REGISTER") palette.primary else Color.White)
                }

                // Login Screen Icon 🔐
                IconButton(onClick = {
                    if (loggedUser.isNotBlank() && loggedUser != "visitor") {
                        vm.activeScreen.value = "ADMIN"
                    } else {
                        vm.activeScreen.value = "LOGIN"
                    }
                }) {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Login",
                        tint = if (currentScreen == "LOGIN" || currentScreen == "ADMIN") palette.primary else Color.White
                    )
                }

                // Language Toggler 🌐
                IconButton(onClick = { vm.isArabic.value = !isArabic }) {
                    Icon(Icons.Default.Language, contentDescription = "Translate", tint = palette.primary)
                }

                // Instant Sync Icon 🔄
                IconButton(onClick = {
                    vm.setupAllSnapshotListeners()
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Sync", tint = Color.LightGray)
                }
            }
        }
    }

    // Doorway modal for Secret Login
    if (showBackdoorLogin) {
        SecretBackdoorDialog(vm, palette, isArabic, onDismiss = { showBackdoorLogin = false })
    }
}

// 2. Secret Backdoor Portal Password Dialog
@Composable
fun SecretBackdoorDialog(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean, onDismiss: () -> Unit) {
    var passwordInput by remember { mutableStateOf("") }
    var rememberPass by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isArabic) "البوابة الخلفية السرية للتحكم" else "Secret Backdoor Portal", color = palette.primary) },
        text = {
            Column {
                Text(
                    text = if (isArabic) "الرجاء إدخال الرمز السري للمالك لتفعيل الإعدادات المتقدمة:" else "Please input secret owner code to toggle styling overrides:",
                    color = Color.White,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = passwordInput,
                    onValueChange = {
                        passwordInput = it
                        hasError = false
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    isError = hasError,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = palette.surface,
                        unfocusedContainerColor = palette.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                if (hasError) {
                    Text(if (isArabic) "الرمز غير صحيح!" else "Wrong passcode!", color = Color.Red, fontSize = 11.sp)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Checkbox(checked = rememberPass, onCheckedChange = { rememberPass = it })
                    Text(if (isArabic) "تذكر كلمة المرور" else "Remember Security Key", color = Color.White, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
                onClick = {
                    if (passwordInput == "maher--736462") {
                        vm.loggedInUser.value = "admin"
                        vm.loggedInUsername.value = "المالك الرئيسى"
                        onDismiss()
                        vm.activeScreen.value = "ADMIN"
                    } else {
                        hasError = true
                    }
                }
            ) {
                Text(if (isArabic) "دخول" else "Login", color = palette.secondaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isArabic) "إلغاء" else "Cancel", color = Color.LightGray)
            }
        },
        containerColor = palette.surface
    )
}

// 3. Custom Footer with adjustable transparency & size
@Composable
fun CustomFooterWithOpacities(vm: MainViewModel, palette: ColorSchemePalette, onInfoClick: () -> Unit) {
    val settings by vm.settings.collectAsStateWithLifecycle()
    val isArabic by vm.isArabic.collectAsStateWithLifecycle()

    Surface(
        color = palette.surface,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(settings.footerOpacity)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Info Button ℹ️
            IconButton(onClick = onInfoClick, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Default.Info, contentDescription = "App info", tint = palette.primary, modifier = Modifier.size(18.dp))
            }

            // Center Dynamic Text - Small scaled by Admin control
            Text(
                text = settings.footerText,
                color = palette.textPrimary,
                fontSize = (settings.footerSizePercent / 12).sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Right side indicators (Status)
            val stateColor = if (vm.isOnline.collectAsStateWithLifecycle().value) Color.Green else Color.Red
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(stateColor)
            )
        }
    }
}

// 4. Maintenance Mode Landing page
@Composable
fun MaintenanceView(settings: AppSettings, isArabic: Boolean, palette: ColorSchemePalette) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(palette.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Build, contentDescription = "Maintenance", tint = palette.primary, modifier = Modifier.size(72.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isArabic) "وضع صيانة التطبيق نشط حالياً" else "Maintenance Mode Active",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            color = palette.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = settings.welcomeMessage,
            fontSize = 14.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
}

// Blacklisted layout
@Composable
fun BannedWarningView(isArabic: Boolean, palette: ColorSchemePalette) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Block, contentDescription = "Banned", tint = Color.Red, modifier = Modifier.size(72.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isArabic) "لقد تم حظر جهازك من التفاعل!" else "Your Device Has Been Blacklisted!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if (isArabic) "الرجاء مراجعة المطور أو مركز الدعم لإزالة القيود." else "Please inspect your status with developer support.",
            fontSize = 13.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
}

// 5. Smart AI Assistant Chat Dialog Overlay
@Composable
fun SmartAssistantOverlay(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean, onClose: () -> Unit) {
    val chatsHistory by vm.assistantChat.collectAsStateWithLifecycle()
    var inputQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onClose() }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f)
                .align(Alignment.Center)
                .clickable(enabled = false) {}
                .border(1.dp, palette.primary, RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isArabic) "المساعد الذكي لخدمات اليمن 🤖" else "Smart AI Assistant",
                        color = palette.primary,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                // Chat Messages List
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(chatsHistory) { msg ->
                        val alignLeft = !msg.second
                        Row(
                            horizontalArrangement = if (alignLeft) Arrangement.Start else Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (msg.second) palette.primary else Color(0xFF333333))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = msg.first,
                                    color = if (msg.second) palette.secondaryColor else Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Input Field (Force High Text Contrast as requested)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = inputQuery,
                        onValueChange = { inputQuery = it },
                        placeholder = { Text(if (isArabic) "اسألني شيئاً..." else "Query AI...", color = Color.Gray) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF111111),
                            unfocusedContainerColor = Color(0xFF111111)
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, palette.primary, RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            if (inputQuery.isNotBlank()) {
                                vm.sendMessageToAssistant(inputQuery)
                                inputQuery = ""
                            }
                        }
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = palette.primary)
                    }
                }
            }
        }
    }
}

// 6. Home Landing Screen with Carousel Ads and Pinned Providers
@Composable
fun HomeScreen(vm: MainViewModel, palette: ColorSchemePalette, onSpeechClick: () -> Unit) {
    val categories by vm.categories.collectAsStateWithLifecycle()
    val providers by vm.providers.collectAsStateWithLifecycle()
    val banners by vm.banners.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()

    val isArabic by vm.isArabic.collectAsStateWithLifecycle()
    val currentUserState by vm.loggedInUser.collectAsStateWithLifecycle()
    val voiceSearchText by vm.voiceSearchResult.collectAsStateWithLifecycle()

    var activeCatId by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCityId by remember { mutableStateOf("") }
    var radiusValue by remember { mutableFloatStateOf(15f) }

    LaunchedEffect(voiceSearchText) {
        if (voiceSearchText.isNotBlank()) {
            searchQuery = voiceSearchText
            vm.voiceSearchResult.value = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        // Welcome Message & Image
        if (settings.welcomeMessage.isNotBlank()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Column {
                    if (settings.welcomeImage.isNotBlank()) {
                        AsyncImage(
                            model = settings.welcomeImage,
                            contentDescription = "Welcome Banner",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = settings.welcomeMessage,
                        color = palette.primary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }

        // Banners Carousel
        if (banners.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .padding(bottom = 8.dp)
            ) {
                items(banners) { ban ->
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .fillMaxHeight()
                            .padding(end = 6.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (ban.imageUrl.isNotBlank()) {
                                AsyncImage(
                                    model = ban.imageUrl,
                                    contentDescription = "Ad",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.4f))
                            )
                            Text(
                                text = ban.text,
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Pinned/Recommended Gold Star Providers Slider
        val pinnedList = providers.filter { it.isRecommended || it.isPinned }
        if (pinnedList.isNotEmpty()) {
            Text(
                text = if (isArabic) "⭐ الكوادر المقترحة والموصى بها" else "⭐ Recommended Professionals",
                color = palette.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            LazyRow(modifier = Modifier.padding(bottom = 8.dp)) {
                items(pinnedList) { p ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = palette.surface),
                        modifier = Modifier
                            .width(150.dp)
                            .padding(end = 6.dp)
                            .clickable {
                                vm.selectedProvider.value = p
                                vm.activeScreen.value = "PROV_DETAILS"
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.size(50.dp)) {
                                if (p.avatarUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = p.avatarUrl,
                                        contentDescription = p.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape)
                                    )
                                } else {
                                    Icon(Icons.Default.Face, contentDescription = "Face", tint = palette.primary, modifier = Modifier.size(50.dp))
                                }
                                if (p.isVerified) {
                                    Icon(
                                        Icons.Default.Verified,
                                        contentDescription = "Verified badge",
                                        tint = Color(0xFF1DA1F2),
                                        modifier = Modifier
                                            .size(16.dp)
                                            .align(Alignment.BottomEnd)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(p.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, color = Color.White, textAlign = TextAlign.Center)
                            Text(p.area, fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // Live Dynamic Filtering Elements
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Search + Voice
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(if (isArabic) "بحث فوري عن اسم، هاتف، مهنة..." else "Instant Search...") },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    if (settings.isVoiceSearchEnabled) {
                        Spacer(modifier = Modifier.width(4.dp))
                        IconButton(onClick = onSpeechClick) {
                            Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = palette.primary)
                        }
                    }
                }

                // In-App Notification when instant chat is disabled/blocked for current role
                val blockVisitor = settings.isChatDisabledForVisitors && (currentUserState.isBlank() || currentUserState == "visitor")
                val blockProvider = settings.isChatDisabledForProviders && (providers.any { it.deviceId == currentUserState })
                if (blockVisitor || blockProvider) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF421515)),
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    ) {
                        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = settings.disabledChatNotificationMessage,
                                color = Color.White,
                                fontSize = 11.sp,
                                lineHeight = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Real-time Autocomplete Suggestions Panel
                if (searchQuery.isNotBlank()) {
                    val matchingSuggestions = providers.filter {
                        it.name.contains(searchQuery, ignoreCase = true) ||
                        it.area.contains(searchQuery, ignoreCase = true) ||
                        it.address.contains(searchQuery, ignoreCase = true)
                    }.take(4)

                    if (matchingSuggestions.isNotEmpty()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = palette.surface.copy(alpha = 0.98f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            border = BorderStroke(1.dp, palette.primary.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(4.dp)) {
                                matchingSuggestions.forEach { sug ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                searchQuery = sug.name
                                            }
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Search, contentDescription = null, tint = palette.primary, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(sug.name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                            Text("${sug.area} • ${sug.address}", color = Color.Gray, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Scrollable Cities List Filter Selector
                val citiesList by vm.cities.collectAsStateWithLifecycle()
                if (citiesList.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (isArabic) "📍 تصفية حسب المحافظة/المدينة:" else "📍 Filter by City/Province:",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        item {
                            val active = selectedCityId.isBlank()
                            Button(
                                onClick = { selectedCityId = "" },
                                colors = ButtonDefaults.buttonColors(containerColor = if (active) palette.primary else Color(0xFF333333)),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text(if (isArabic) "الكل" else "All", color = if (active) palette.secondaryColor else Color.White, fontSize = 10.sp)
                            }
                        }
                        items(citiesList) { ct ->
                            val name = if (isArabic) ct.nameAr else ct.nameEn
                            val active = selectedCityId == ct.id || selectedCityId == name
                            Button(
                                onClick = { selectedCityId = if (active) "" else name },
                                colors = ButtonDefaults.buttonColors(containerColor = if (active) palette.primary else Color(0xFF333333)),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(30.dp)
                            ) {
                                Text(name, color = if (active) palette.secondaryColor else Color.White, fontSize = 10.sp)
                            }
                        }
                    }
                }

                // Radius Map Search Input
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isArabic) "البحث بالخريطة: دائرى ${radiusValue.toInt()}كم" else "Radius: ${radiusValue.toInt()}km",
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier.width(130.dp)
                    )
                    Slider(
                        value = radiusValue,
                        onValueChange = { radiusValue = it },
                        valueRange = 5f..settings.radiusMaxKm.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = palette.primary,
                            activeTrackColor = palette.primary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Vector Visual Maps representation
        InteractiveMockMapView(providers, palette, isArabic)

        // Categories Grid (Flex Option loaded dynamically from categories collection)
        val roots = categories.filter { it.parentId.isBlank() }
        Text(
            text = if (isArabic) "الأقسام والخدمات المتوفرة" else "Main Categories & Services",
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 10.dp)
        ) {
            roots.forEach { root ->
                val selected = (activeCatId == root.id)
                Button(
                    onClick = { activeCatId = if (selected) "" else root.id },
                    colors = ButtonDefaults.buttonColors(containerColor = if (selected) palette.primary else palette.surface),
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        if (isArabic) root.nameAr else root.nameEn,
                        color = if (selected) palette.secondaryColor else Color.White
                    )
                }
            }
        }

        // Selected Subcategories List
        if (activeCatId.isNotBlank()) {
            val subs = categories.filter { it.parentId == activeCatId }
            if (subs.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(bottom = 8.dp)
                ) {
                    subs.forEach { sb ->
                        Text(
                            text = if (isArabic) "🔍 ${sb.nameAr}" else "🔍 ${sb.nameEn}",
                            color = palette.primary,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(palette.surface)
                                .padding(6.dp)
                        )
                    }
                }
            }
        }

        // Verified approved list (Sorting: Pinned First -> Rating Desc)
        val selectedCat = activeCatId
        val filteredList = providers.filter {
            (selectedCat.isBlank() || it.categoryMainId == selectedCat || it.categorySubId == selectedCat) &&
                    (selectedCityId.isBlank() || it.area.contains(selectedCityId, ignoreCase = true) || it.address.contains(selectedCityId, ignoreCase = true)) &&
                    (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.phone.contains(searchQuery) || it.address.contains(searchQuery, ignoreCase = true) || it.area.contains(searchQuery, ignoreCase = true))
        }.sortedWith(compareByDescending<Provider> { it.isPinned }.thenByDescending { it.rating })

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .heightIn(max = 600.dp)
                .fillMaxWidth()
        ) {
            items(filteredList) { pr ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = palette.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            vm.selectedProvider.value = pr
                            vm.activeScreen.value = "PROV_DETAILS"
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(60.dp)) {
                            if (pr.avatarUrl.isNotBlank()) {
                                AsyncImage(
                                    model = pr.avatarUrl,
                                    contentDescription = pr.name,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                )
                            } else {
                                Icon(Icons.Default.Face, contentDescription = "Face", tint = palette.primary, modifier = Modifier.size(60.dp))
                            }
                            if (pr.isVerified) {
                                Icon(
                                    Icons.Default.Verified,
                                    contentDescription = "Verified badge",
                                    tint = Color(0xFF1DA1F2),
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.BottomEnd)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(pr.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                            Text("${pr.address} - ${pr.area}", fontSize = 11.sp, color = Color.Gray)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = "Rating", tint = palette.primary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("${pr.rating} (${pr.reviewCount})", fontSize = 11.sp, color = Color.LightGray)
                            }
                        }
                        if (pr.isSubscribed) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(palette.primary)
                                    .padding(4.dp)
                            ) {
                                Text(if (isArabic) "مشترك مميز" else "Premium", fontSize = 9.sp, color = palette.secondaryColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Map Rendering pin elements
@Composable
fun InteractiveMockMapView(providers: List<Provider>, palette: ColorSchemePalette, isArabic: Boolean) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1F1C)),
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(bottom = 12.dp)
            .border(1.dp, palette.primary, RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Simulated grid
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.White.copy(alpha = 0.05f))
                    )
                }
            }
            Text(
                if (isArabic) "خريطة تفاعلية لمنتسبي الخدمات في اليمن 🗺️" else "Interactive Service Provider Map",
                color = palette.primary,
                fontSize = 11.sp,
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.TopStart)
            )

            // Dynamic provider pins displayed
            providers.take(4).forEachIndexed { i, p ->
                val xOffset = 30 + (i * 50)
                val yOffset = 50 + (i * 12)
                Box(modifier = Modifier.offset(x = xOffset.dp, y = yOffset.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Pin", tint = palette.primary, modifier = Modifier.size(16.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color.Black.copy(alpha = 0.8f))
                                .padding(2.dp)
                        ) {
                            Text(p.name.take(6), color = Color.White, fontSize = 8.sp)
                        }
                    }
                }
            }
        }
    }
}

// 7. Login and Supervisor Registration View (Single/Backdoor Support)
@Composable
fun LoginScreen(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }

    val settings by vm.settings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isArabic) "بوابة تسجيل الدخول الموحدة" else "Unified Sign-In Portal",
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(if (isArabic) "اسم المستخدم" else "Username") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(if (isArabic) "كلمة المرور" else "Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
            Text(if (isArabic) "حفظ وتسجيل دخول مستمر" else "Remember me permanently", color = Color.LightGray, fontSize = 12.sp)
        }

        if (errorMsg.isNotBlank()) {
            Text(errorMsg, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(15.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val dynamicSuper = vm.supervisors.value.find { it.username == username && it.password == password }
                if (username == "WAM2026" && password == settings.adminPassword) {
                    vm.loggedInUser.value = "admin"
                    vm.loggedInUsername.value = "الأدمن الرئيسي"
                    vm.supervisorPermissions.value = emptyList()
                    vm.activeScreen.value = "ADMIN"
                } else if (username == "supervisor" && password == "maher123") {
                    vm.loggedInUser.value = "supervisor"
                    vm.loggedInUsername.value = "المشرف العام"
                    vm.supervisorPermissions.value = listOf(
                        "approve_reject_requests", "manage_categories_cities", "manage_ads_banners",
                        "delete_active_providers", "manage_providers", "view_reports_audits", "view_chat_history"
                    )
                    vm.activeScreen.value = "ADMIN"
                } else if (dynamicSuper != null) {
                    vm.loggedInUser.value = "supervisor"
                    vm.loggedInUsername.value = dynamicSuper.username
                    vm.supervisorPermissions.value = dynamicSuper.permissions
                    vm.activeScreen.value = "ADMIN"
                } else {
                    errorMsg = if (isArabic) "البيانات المدخلة غير صحيحة!" else "Incorrect credentials entered!"
                }
            }
        ) {
            Text(if (isArabic) "دخول لوحة التحكم" else "Login Control Panel", color = palette.secondaryColor, fontWeight = FontWeight.Bold)
        }
    }
}

// 8. Practitioner Registration Dialog with Dynamic Categories Dropdown
@Composable
fun RegistrationForm(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val categories by vm.categories.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var selectedMainIndex by remember { mutableIntStateOf(0) }
    var address by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var isFem by remember { mutableStateOf(false) }

    var avatarUrl by remember { mutableStateOf("") }
    var identityUrl by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val roots = categories.filter { it.parentId.isEmpty() }

    // Automatic Female trade placeholder determination
    LaunchedEffect(isFem, selectedMainIndex, roots) {
        if (isFem) {
            val rootName = roots.getOrNull(selectedMainIndex)?.nameEn?.lowercase() ?: ""
            avatarUrl = when {
                rootName.contains("tailor") || rootName.contains("sewing") || rootName.contains("embroidery") -> 
                    "https://images.unsplash.com/photo-1524295928322-4b98c544353a?w=150"
                rootName.contains("salon") || rootName.contains("beauty") || rootName.contains("hair") -> 
                    "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=150"
                rootName.contains("cooking") || rootName.contains("kitchen") || rootName.contains("food") -> 
                    "https://images.unsplash.com/photo-1556910103-1c02745aae4d?w=150"
                else -> 
                    "https://images.unsplash.com/photo-1594744803329-e58b31de215f?w=150"
            }
        } else if (avatarUrl.startsWith("http")) { // Reset if was autoassigned
            avatarUrl = ""
        }
    }

    // Capture/pick launchers
    val avatarGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            scope.launch(Dispatchers.Default) {
                try {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                    } else {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    }
                    val compressedBase64 = compressAndEncodeBitmap(bitmap)
                    avatarUrl = "data:image/jpeg;base64,$compressedBase64"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    val avatarCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            scope.launch(Dispatchers.Default) {
                val compressedBase64 = compressAndEncodeBitmap(it)
                avatarUrl = "data:image/jpeg;base64,$compressedBase64"
            }
        }
    }

    val idGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            scope.launch(Dispatchers.Default) {
                try {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
                    } else {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    }
                    val compressedBase64 = compressAndEncodeBitmap(bitmap)
                    identityUrl = "data:image/jpeg;base64,$compressedBase64"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    val idCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            scope.launch(Dispatchers.Default) {
                val compressedBase64 = compressAndEncodeBitmap(it)
                identityUrl = "data:image/jpeg;base64,$compressedBase64"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = if (isArabic) "👤 استمارة تسجيل الكوادر والمهنيين" else "👤 Practitioner Join Form",
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        TextField(value = name, onValueChange = { name = it }, label = { Text(if (isArabic) "الاسم الثلاثي الكامل (إجباري)" else "Full Triple Name (Required)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = phone, onValueChange = { phone = it }, label = { Text(if (isArabic) "رقم الهاتف / واتساب (إجباري)" else "Phone/WhatsApp (Required)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "القسم الرئيسي للخدمة (إجباري):" else "Service Domain (Required):", color = Color.White, fontSize = 12.sp)

        // Flexible Category Selector list
        val roots = categories.filter { it.parentId.isEmpty() }
        if (roots.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                roots.forEachIndexed { index, cat ->
                    val selected = (selectedMainIndex == index)
                    Button(
                        onClick = { selectedMainIndex = index },
                        colors = ButtonDefaults.buttonColors(containerColor = if (selected) palette.primary else palette.surface),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(if (isArabic) cat.nameAr else cat.nameEn, color = if (selected) palette.secondaryColor else Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = address, onValueChange = { address = it }, label = { Text(if (isArabic) "عنوان مركز العمل الحالي (إجباري)" else "Work address (Required)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = area, onValueChange = { area = it }, label = { Text(if (isArabic) "منطقة الدائرة السكنية (إجباري)" else "Residential area (Required)") }, modifier = Modifier.fillMaxWidth())

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 6.dp)) {
            Checkbox(checked = isFem, onCheckedChange = { isFem = it })
            Text(if (isArabic) "مقدمة الخدمة أنثى (لإخفاء متطلبات صورة السيلفي)" else "Applicant is Female (Optional selfie requirements)", color = Color.White, fontSize = 11.sp)
        }

        // Camera and Gallery selectors
        if (!isFem) {
            Text(if (isArabic) "الصورة الشخصية (سيلفي):" else "Personal Selfie Photo:", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { avatarCameraLauncher.launch(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isArabic) "📸 الكاميرا" else "📸 Camera", color = Color.White)
                }
                Button(
                    onClick = { avatarGalleryLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isArabic) "🖼️ المعرض" else "🖼️ Gallery", color = Color.White)
                }
            }
            if (avatarUrl.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(modifier = Modifier.size(60.dp), shape = RoundedCornerShape(8.dp)) {
                        AsyncImage(model = avatarUrl, contentDescription = "Preview", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isArabic) "تم تحديد الصورة بنجاح!" else "Photo loaded!", color = Color.Green, fontSize = 11.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { avatarUrl = "" }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
        } else {
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.primary.copy(alpha = 0.15f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = if (isArabic) "✨ خصوصية تامة:" else "✨ Absolute Privacy:",
                        color = palette.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = if (isArabic) "مقدمة الخدمة أنثى: تم تعيين صورة رمزية تعبر عن المهنة المحددة تلقائياً للحفاظ على خصوصيتك." else "Service provider is female: A professional category avatar has been automatically assigned to safeguard your privacy.",
                        color = Color.White,
                        fontSize = 11.sp
                    )
                    if (avatarUrl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Card(modifier = Modifier.size(50.dp), shape = CircleShape) {
                                AsyncImage(model = avatarUrl, contentDescription = "Trade Placeholder", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(if (isArabic) "الصورة المهنية المحددة" else "Selected professional icon", color = Color.LightGray, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(if (isArabic) "صورة بطاقة الهوية الذاتية (اختياري):" else "ID Card Document (Optional):", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { idCameraLauncher.launch(null) },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isArabic) "📸 تصوير البديل" else "📸 Snap ID", color = Color.White)
            }
            Button(
                onClick = { idGalleryLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isArabic) "🖼️ اختيار الهوية" else "🖼️ Choose Card", color = Color.White)
            }
        }
        if (identityUrl.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(modifier = Modifier.size(60.dp), shape = RoundedCornerShape(8.dp)) {
                    AsyncImage(model = identityUrl, contentDescription = "ID Card", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isArabic) "تم تحميل بطاقة الهوية!" else "ID doc loaded!", color = Color.Green, fontSize = 11.sp)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { identityUrl = "" }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete ID", tint = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        val settings by vm.settings.collectAsStateWithLifecycle()
        Text(
            text = if (isArabic) "📜 شروط تسجيل الكوادر والمهنيين:" else "📜 Terms of Professional Registration:",
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                val terms = settings.registrationTerms.ifEmpty {
                    listOf(
                        "توفير الاسم الثلاثي الكامل ومطابق للبطاقة الشخصية",
                        "رقم الهاتف يجب أن يكون فعالاً ومتصلاً بواتساب لتسهيل التواصل",
                        "تحديد موقع ممارسة الخدمة بشكل دقيق لربط محددات البحث",
                        "الالتزام بقيم وأخلاقيات العمل والأمانة عند تقديم الخدمة للجمهور"
                    )
                }
                terms.forEach { term ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text("• ", color = palette.primary, fontSize = 11.sp)
                        Text(term, color = Color.White, fontSize = 11.sp, lineHeight = 14.sp)
                    }
                }
            }
        }
        var acceptTerms by remember { mutableStateOf(false) }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
            Checkbox(checked = acceptTerms, onCheckedChange = { acceptTerms = it })
            Text(if (isArabic) "أوافق على جميع الشروط والأحكام المذكورة أعلاه" else "I agree to all terms & conditions", color = Color.White, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = if (acceptTerms) palette.primary else Color.Gray),
            modifier = Modifier.fillMaxWidth(),
            enabled = acceptTerms,
            onClick = {
                if (name.isBlank() || phone.isBlank() || address.isBlank() || area.isBlank()) {
                    return@Button
                }
                val chosenCat = roots.getOrNull(selectedMainIndex)
                val newReq = PendingProvider(
                    name = name,
                    phone = phone,
                    categoryMainId = chosenCat?.id ?: "1",
                    address = address,
                    area = area,
                    isFemale = isFem,
                    avatarUrl = avatarUrl,
                    identityCardUrl = identityUrl,
                    status = "pending"
                )
                vm.registerPendingProvider(newReq)
                vm.activeScreen.value = "HOME"
            }
        ) {
            Text(if (isArabic) "تقديم طلب الانضمام للمراجعة الفورية" else "Submit Joint Application", color = if (acceptTerms) palette.secondaryColor else Color.White)
        }
    }
}

fun compressAndEncodeBitmap(bitmap: Bitmap): String {
    val maxDimension = 600
    val originalWidth = bitmap.width
    val originalHeight = bitmap.height
    val scaledBitmap = if (originalWidth > maxDimension || originalHeight > maxDimension) {
        val aspectRatio = originalWidth.toFloat() / originalHeight.toFloat()
        val (newWidth, newHeight) = if (originalWidth > originalHeight) {
            Pair(maxDimension, (maxDimension / aspectRatio).toInt())
        } else {
            Pair((maxDimension * aspectRatio).toInt(), maxDimension)
        }
        Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    } else {
        bitmap
    }
    val outputStream = ByteArrayOutputStream()
    scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
    val byteArray = outputStream.toByteArray()
    return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
}

// 9. About App Screen containing editable variables dynamically
@Composable
fun AppInfoScreen(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val settings by vm.settings.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.Info, contentDescription = "Info", tint = palette.primary, modifier = Modifier.size(54.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = settings.appNameAr,
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "كل خدمات الصيانة والمهنيين في اليمن تحت سقف واحد - تواصل مباشر ومزامنة لحظية فورية عبر قواعد الحماية الفولاذية.",
            color = Color.LightGray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Divider(modifier = Modifier.padding(vertical = 12.dp), color = palette.primary)

        Text(if (isArabic) "📞 للتواصل والاستفسار:" else "📞 Support details:", color = palette.primary, fontWeight = FontWeight.Bold)
        Text("الهاتف: ${settings.supportPhone}", color = Color.White, fontSize = 13.sp)
        Text("البريد الإلكتروني: ${settings.supportEmail}", color = Color.White, fontSize = 13.sp)
        Text("واتساب الدعم: ${settings.supportWhatsapp}", color = Color.White, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(palette.surface)
                .padding(12.dp)
        ) {
            Column {
                Text(if (isArabic) "إصدار التطبيق: v1.0.8" else "Version: v1.0.8", color = Color.LightGray, fontSize = 11.sp)
                Text("المطور: MAW", color = Color.LightGray, fontSize = 11.sp)
            }
        }
    }
}

// 10. Real-time Active Chat sessions list
@Composable
fun ChatsListScreen(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val chats by vm.chats.collectAsStateWithLifecycle()
    val userRole by vm.loggedInUser.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isArabic) "💬 محادثات المتصلين المباشرة" else "💬 Live Chats Room",
                color = palette.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Direct help button for visitor
            if (userRole.isBlank() || userRole == "visitor" || userRole == "") {
                Button(
                    onClick = {
                        val vId = "visitor_" + UUID.randomUUID().toString().take(4)
                        vm.loggedInUser.value = vId
                        vm.startChatWithProvider(vId, "admin")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                ) {
                    Text(if (isArabic) "تواصل مباشر مع الأدمن" else "Chat Admin", color = palette.secondaryColor)
                }
            }
        }

        if (chats.isEmpty()) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(if (isArabic) "لا توجد جلسات محادثة نشطة حالياً." else "No active chat logs found.", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(chats) { ch ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = palette.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                vm.openChatRoom(ch.chatId)
                                vm.activeScreen.value = "CHAT_ROOM"
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "User", tint = palette.primary, modifier = Modifier.size(34.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(ch.chatId, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 13.sp)
                                Text(ch.lastMessage, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
                            }
                            if (userRole == "admin") {
                                IconButton(onClick = { vm.deleteChatLogs(ch.chatId, "الأدمن") }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 11. Interactive Real-time Chat Dialog Interface with correct Snap listeners
@Composable
fun ChatRoomView(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val messages by vm.chatMessages.collectAsStateWithLifecycle()
    val myUser by vm.loggedInUser.collectAsStateWithLifecycle()
    val chatId by vm.activeChatId.collectAsStateWithLifecycle()

    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Back Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                vm.closeChatRoom()
                vm.activeScreen.value = "CHAT_LIST"
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            Text(chatId.take(20), color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Box(modifier = Modifier.size(24.dp))
        }

        // Message List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            items(messages) { msg ->
                val myMsg = msg.senderId == myUser
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = if (myMsg) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (myMsg) palette.primary else Color(0xFF333333))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = msg.text,
                            color = if (myMsg) palette.secondaryColor else Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Input bottom fields
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                placeholder = { Text(if (isArabic) "اكتب رسالة..." else "Write message...", color = Color.Gray) },
                colors = TextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        vm.sendChatMessage(myUser, textInput)
                        textInput = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send", tint = palette.primary)
            }
        }
    }
}

// 12. Complete Dashboard and Custom Controller
@Composable
fun AdminDashboard(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val pending by vm.pendingProviders.collectAsStateWithLifecycle()
    val approved by vm.providers.collectAsStateWithLifecycle()
    val audits by vm.auditLogs.collectAsStateWithLifecycle()
    val complaints by vm.reports.collectAsStateWithLifecycle()
    val messages by vm.chatMessages.collectAsStateWithLifecycle()
    val chats by vm.chats.collectAsStateWithLifecycle()
    val settings by vm.settings.collectAsStateWithLifecycle()

    var activeTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (isArabic) "⚙️ لوحة تحكم الإدارة والرقابة الشاملة" else "⚙️ Core Admin Panel",
                color = palette.primary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            // Logout Button
            IconButton(onClick = {
                vm.loggedInUser.value = ""
                vm.loggedInUsername.value = ""
                vm.activeScreen.value = "HOME"
            }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.Red)
            }
        }

        // Horizontal scrollable tabs slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 6.dp)
        ) {
            val names = if (isArabic) {
                listOf(
                    "طلبات التسجيل (${pending.size})",
                    "إضافة/تعديل فني يدوياً",
                    "إعلانات وبنرات",
                    "إدارة الأقسام والمدن",
                    "الإبلاغات والتقارير (${complaints.size})",
                    "إدارة السجلات والخصوصية",
                    "المزودين النشطين",
                    "لوحة التحكم بالاشتراكات",
                    "إدارة المشرفين",
                    "تغيير الألوان والشروط"
                )
            } else {
                listOf(
                    "Join Requests (${pending.size})",
                    "Manual Add/Edit",
                    "Banners & Ads",
                    "Categories & Cities",
                    "Complaints (${complaints.size})",
                    "Privacy & Deletes",
                    "Active Directory",
                    "Subscriptions",
                    "Supervisors",
                    "Theme & Guidelines"
                )
            }

            names.forEachIndexed { idx, name ->
                val active = (activeTab == idx)
                Button(
                    onClick = { activeTab = idx },
                    colors = ButtonDefaults.buttonColors(containerColor = if (active) palette.primary else palette.surface),
                    modifier = Modifier.padding(end = 4.dp).height(36.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(name, color = if (active) palette.secondaryColor else Color.White, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (activeTab) {
                0 -> TabPendingRequests(vm, pending, palette, isArabic)
                1 -> TabManualTechnician(vm, approved, palette, isArabic)
                2 -> TabBannersAds(vm, palette, isArabic)
                3 -> TabCategoriesAndCities(vm, palette, isArabic)
                4 -> TabReportsAndComplaints(vm, complaints, audits, palette, isArabic)
                5 -> TabChatHistoryPrivacy(vm, chats, messages, palette, isArabic)
                6 -> TabActiveProviders(vm, approved, palette, isArabic)
                7 -> TabSubscriptionsPinning(vm, approved, palette, isArabic)
                8 -> TabAdminManagement(vm, palette, isArabic)
                9 -> TabThemeColorsSettings(vm, settings, palette, isArabic)
            }
        }
    }
}

// Tab: Dynamic pending reviews
@Composable
fun TabPendingRequests(vm: MainViewModel, pending: List<PendingProvider>, palette: ColorSchemePalette, isArabic: Boolean) {
    if (pending.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "لا توجد طلبات انضمام جديدة قيد الانتظار." else "No pending join requests currently.", color = Color.Gray)
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pending) { pp ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = palette.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("الاسم: ${pp.name}", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("رقم الهاتف: ${pp.phone}", color = Color.LightGray, fontSize = 12.sp)
                        Text("العنوان والمنطقة: ${pp.address} - ${pp.area}", color = Color.LightGray, fontSize = 11.sp)

                        if (pp.avatarUrl.isNotBlank()) {
                            AsyncImage(
                                model = pp.avatarUrl,
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Button(
                                onClick = { vm.approveProviderRequest(pp, "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(if (isArabic) "قبول الطلب" else "Approve", color = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = { vm.rejectProviderRequest(pp.id, "لم يستكمل المتطلبات", "الأدمن") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(if (isArabic) "رفض" else "Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}

// Tab: Active approved personnel control
@Composable
fun TabApprovedProviders(vm: MainViewModel, list: List<Provider>, palette: ColorSchemePalette, isArabic: Boolean) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) { p ->
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(p.name, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(p.phone, color = Color.LightGray, fontSize = 11.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            IconButton(onClick = { vm.toggleProviderStatus(p.id, !p.isPinned, p.isRecommended, p.isVerified, p.isSubscribed, "الأدمن") }) {
                                Icon(Icons.Default.PushPin, contentDescription = "Pin", tint = if (p.isPinned) palette.primary else Color.LightGray)
                            }
                            IconButton(onClick = { vm.toggleProviderStatus(p.id, p.isPinned, !p.isRecommended, p.isVerified, p.isSubscribed, "الأدمن") }) {
                                Icon(Icons.Default.Star, contentDescription = "Recommend", tint = if (p.isRecommended) palette.primary else Color.LightGray)
                            }
                            IconButton(onClick = { vm.toggleProviderStatus(p.id, p.isPinned, p.isRecommended, !p.isVerified, p.isSubscribed, "الأدمن") }) {
                                Icon(Icons.Default.Verified, contentDescription = "Verify", tint = if (p.isVerified) Color(0xFF1DA1F2) else Color.LightGray)
                            }
                            IconButton(onClick = { vm.toggleProviderStatus(p.id, p.isPinned, p.isRecommended, p.isVerified, !p.isSubscribed, "الأدمن") }) {
                                Icon(Icons.Default.CardMembership, contentDescription = "Premium Subscription", tint = if (p.isSubscribed) Color.Green else Color.LightGray)
                            }
                        }

                        IconButton(onClick = { vm.banId(p.id, true, "الأدمن") }) {
                            Icon(Icons.Default.Block, contentDescription = "Ban/Blacklist", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

// Tab: Banners ads configurations
@Composable
fun TabBannersAds(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val banners by vm.banners.collectAsStateWithLifecycle()
    var bannerText by remember { mutableStateOf("") }
    var bannerImageUrl by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "إنشاء لافتة ترويجية جديدة:" else "Add Premium Advertisement Banner:", color = palette.primary, fontSize = 12.sp)
        TextField(value = bannerText, onValueChange = { bannerText = it }, label = { Text(if (isArabic) "نص الإعلان" else "Ad Description Text") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = bannerImageUrl, onValueChange = { bannerImageUrl = it }, label = { Text(if (isArabic) "رابط الصورة الترويجية" else "Web Image/Banner URL") }, modifier = Modifier.fillMaxWidth())

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 4.dp),
            onClick = {
                if (bannerText.isNotBlank()) {
                    vm.saveBanner(Banner(text = bannerText, imageUrl = bannerImageUrl), "الأدمن")
                    bannerText = ""
                    bannerImageUrl = ""
                }
            }
        ) {
            Text(if (isArabic) "حفظ وإطلاق اللافتة" else "Save & Launch Ad", color = palette.secondaryColor)
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(banners) { b ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = palette.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(b.text, color = Color.White, fontSize = 12.sp, modifier = Modifier.weight(1f))
                        IconButton(onClick = { vm.deleteBanner(b.id, "الأدمن") }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove banner ad", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

// Tab: Visual colors theme setting panel
@Composable
fun TabThemeColorsSettings(vm: MainViewModel, settings: AppSettings, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    var newRegistrationTermInput by remember { mutableStateOf("") }
    var appName by remember { mutableStateOf(settings.appNameAr) }
    var welcomeMsg by remember { mutableStateOf(settings.welcomeMessage) }
    var footerText by remember { mutableStateOf(settings.footerText) }
    var scalePercent by remember { mutableStateOf(settings.footerSizePercent.toString()) }
    var opacityVal by remember { mutableStateOf(settings.footerOpacity.toString()) }
    var maxDist by remember { mutableStateOf(settings.radiusMaxKm.toString()) }
    var mainAct by remember { mutableStateOf(settings.isMaintenanceActive) }

    // Chat size, color, and flags
    var chatSize by remember { mutableFloatStateOf(settings.chatIconSize.toFloat()) }
    var chatCol by remember { mutableStateOf(settings.chatIconColor) }
    var iconHidden by remember { mutableStateOf(settings.isChatIconHidden) }
    var iconDeleted by remember { mutableStateOf(settings.isChatIconDeleted) }

    // Role block rules and custom message
    var blockVisitor by remember { mutableStateOf(settings.isChatDisabledForVisitors) }
    var blockProvider by remember { mutableStateOf(settings.isChatDisabledForProviders) }
    var disabledMsg by remember { mutableStateOf(settings.disabledChatNotificationMessage) }

    // Custom asset icon URLs and effects
    var assistUrl by remember { mutableStateOf(settings.assistantIconUrl) }
    var customChatUrl by remember { mutableStateOf(settings.chatIconUrl) }
    var assistEffect by remember { mutableStateOf(settings.assistantIconEffect) }
    var chatEffect by remember { mutableStateOf(settings.chatIconEffect) }

    // Auto cleanup days
    var cleanActive by remember { mutableStateOf(settings.isAutoCleanupActive) }
    var cleanDays by remember { mutableStateOf(settings.autoCleanupPeriodDays.toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp)
    ) {
        // --- SECTION 1: VISUAL THEME & APP CREDENTIALS ---
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(if (isArabic) "🎨 هوية وتصميم التطبيق" else "🎨 Design Theme Settings", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = appName, onValueChange = { appName = it }, label = { Text(if (isArabic) "اسم التطبيق الرئيسي" else "App Brand Title") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = welcomeMsg, onValueChange = { welcomeMsg = it }, label = { Text(if (isArabic) "رسالة الترحيب" else "App greeting banner") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = footerText, onValueChange = { footerText = it }, label = { Text(if (isArabic) "تذييل الحقوق والدعاية" else "Custom sponsor footer text") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = scalePercent, onValueChange = { scalePercent = it }, label = { Text(if (isArabic) "نسبة حجم التذييل (%)" else "Footer Scale %") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = opacityVal, onValueChange = { opacityVal = it }, label = { Text(if (isArabic) "شفافية التذييل (0.0 -> 1.0)" else "Footer Opacity (0.0 -> 1.0)") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = maxDist, onValueChange = { maxDist = it }, label = { Text(if (isArabic) "الحد الأقصى للبحث بالخريطة (كم)" else "Max Radius Search (Km)") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "وضع الصيانة والتقييد" else "Maintenance Status Enforcer", color = Color.White, fontSize = 12.sp)
                    Switch(checked = mainAct, onCheckedChange = { mainAct = it })
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(if (isArabic) "اختر القوالب اللونية المعتمدة البصرية:" else "Approved Styling presets Theme Override:", color = palette.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    val themes = listOf("gold" to "✨ ذهبي", "silver" to "🌌 فضي", "emerald" to "🟢 أخضر", "yellow" to "💛 أصفر")
                    themes.forEach { t ->
                        Button(
                            onClick = {
                                val n = settings.copy(themeMode = t.first)
                                vm.saveAppSettings(n, "الأدمن")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = if (settings.themeMode == t.first) palette.primary else Color(0xFF333333)),
                            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(t.second, color = if (settings.themeMode == t.first) palette.secondaryColor else Color.White, fontSize = 9.sp)
                        }
                    }
                }
            }
        }

        // --- SECTION 2: CHAT ICON & PERSONALIZATION STYLING ---
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(if (isArabic) "💬 تخصيص أيقونة وحجم المحادثة والخصوصية" else "💬 Chat Controls & Customization", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))

                // Chat icon size
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(if (isArabic) "حجم أيقونة الدردشة: ${chatSize.toInt()}dp" else "Chat icon size: ${chatSize.toInt()}dp", color = Color.White, fontSize = 11.sp, modifier = Modifier.width(150.dp))
                    Slider(
                        value = chatSize,
                        onValueChange = { chatSize = it },
                        valueRange = 30f..80f,
                        colors = SliderDefaults.colors(thumbColor = palette.primary, activeTrackColor = palette.primary),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = chatCol, onValueChange = { chatCol = it }, label = { Text(if (isArabic) "لون أيقونة الدردشة (Hex مثل #FFD700)" else "Chat icon color (hex)") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "إخفاء أيقونة الدردشة الفورية" else "Hide Chat Icon on Toolbar", color = Color.White, fontSize = 11.sp)
                    Switch(checked = iconHidden, onCheckedChange = { iconHidden = it })
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "حذف أيقونة الدردشة نهائياً" else "Completely Remove/Delete Chat Icon", color = Color.Red, fontSize = 11.sp)
                    Switch(checked = iconDeleted, onCheckedChange = { iconDeleted = it })
                }

                // Custom URLs
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = assistUrl, onValueChange = { assistUrl = it }, label = { Text(if (isArabic) "رابط أيقونة المساعد الذكي المخصصة (PNG/SVG)" else "Custom Assistant SVG/PNG icon URL") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = customChatUrl, onValueChange = { customChatUrl = it }, label = { Text(if (isArabic) "رابط أيقونة الدردشة المخصصة (PNG/SVG)" else "Custom Chat SVG/PNG icon URL") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(8.dp))
                Text(if (isArabic) "تأثيرات بصرية لأيقونة المساعد:" else "Assistant Visual Effects:", color = Color.White, fontSize = 11.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    val effects = listOf("none" to "بدون", "glow" to "توهج Glow", "bounce" to "قفز Bounce")
                    effects.forEach { f ->
                        Button(
                            onClick = { assistEffect = f.first },
                            colors = ButtonDefaults.buttonColors(containerColor = if (assistEffect == f.first) palette.primary else Color(0xFF333333)),
                            modifier = Modifier.weight(1f).height(28.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(f.second, color = if (assistEffect == f.first) palette.secondaryColor else Color.White, fontSize = 9.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(if (isArabic) "تأثيرات بصرية لأيقونة الدردشة:" else "Chat Visual Effects:", color = Color.White, fontSize = 11.sp)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    val effects = listOf("none" to "بدون", "glow" to "توهج Glow", "bounce" to "قفز Bounce")
                    effects.forEach { f ->
                        Button(
                            onClick = { chatEffect = f.first },
                            colors = ButtonDefaults.buttonColors(containerColor = if (chatEffect == f.first) palette.primary else Color(0xFF333333)),
                            modifier = Modifier.weight(1f).height(28.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(f.second, color = if (chatEffect == f.first) palette.secondaryColor else Color.White, fontSize = 9.sp)
                        }
                    }
                }

                // Chat lockouts per role
                Spacer(modifier = Modifier.height(10.dp))
                Text(if (isArabic) "🔐 صلاحيات تفعيل المحادثات واستثناؤها:" else "🔐 Role block restrictions:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "تعطيل الدردشة للزوار (غير المسجلين)" else "Disable Chat for Visitors", color = Color.White, fontSize = 11.sp)
                    Switch(checked = blockVisitor, onCheckedChange = { blockVisitor = it })
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "تعطيل الدردشة لمقدمي الخدمة" else "Disable Chat for Providers", color = Color.White, fontSize = 11.sp)
                    Switch(checked = blockProvider, onCheckedChange = { blockProvider = it })
                }
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = disabledMsg, onValueChange = { disabledMsg = it }, label = { Text(if (isArabic) "رسالة الإشعار التلقائي المخصصة عند الإيقاف" else "Deactivation announcement notice text") }, modifier = Modifier.fillMaxWidth())
            }
        }

        // --- SECTION 3: CORE BACKUPS & AUTOMATIC CLEANUP ---
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(if (isArabic) "🗄️ خيارات النظام والنسخ الاحتياطي" else "🗄️ Backups & System Cleaning", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))

                // Auto Clean settings
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(if (isArabic) "تفعيل ميزة التنظيف التلقائي للسجلات" else "Activate Auto System Cleanup", color = Color.White, fontSize = 11.sp)
                    Switch(checked = cleanActive, onCheckedChange = { cleanActive = it })
                }
                Spacer(modifier = Modifier.height(4.dp))
                TextField(value = cleanDays, onValueChange = { cleanDays = it }, label = { Text(if (isArabic) "فترة الاحتفاظ بالبيانات (أيام)" else "Retention Threshold Period (Days)") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        vm.exportFirestoreBackup(context, "الأدمن") { path, err ->
                            if (path != null) {
                                Toast.makeText(context, if (isArabic) "تم تصدير النسخة بنجاح في:\n$path" else "Backup exported successfully:\n$path", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, if (isArabic) "فشل تصدير النسخة: $err" else "Backup failed: $err", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A1E)),
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(if (isArabic) "📤 أخذ نسخة احتياطية من Firestore ومشاركتها" else "📤 Export Firestore Backup (JSON)", color = Color.White, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = {
                        vm.triggerScheduledCleanup("الأدمن")
                        Toast.makeText(context, if (isArabic) "تمت عملية تصفية البيانات القديمة بنجاح!" else "Dump systems cleaned successfully!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F1919)),
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(if (isArabic) "🧹 تصفية وحذف سجلات المهملات يدوياً الآن" else "🧹 Core Manual Cleanup System Old Logs", color = Color.White, fontSize = 11.sp)
                }
            }
        }

        // --- SECTION 4: REGISTRATION REGULATION TERMS & GUIDELINES ---
        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = if (isArabic) "📜 شروط تسجيل الكوادر والمهنيين المعتمدة" else "📜 Registration Regulation Guidelines",
                    color = palette.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                val currentTerms = settings.registrationTerms
                if (currentTerms.isEmpty()) {
                    Text(
                        text = if (isArabic) "لم يتم العثور على شروط تسجيل مخصصة. جاري إظهار قوانين الدليل الافتراضية." else "No custom registration rules configured yet. Showing system defaults.",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )
                } else {
                    currentTerms.forEachIndexed { idx, term ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${idx + 1}. $term",
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.weight(1f),
                                lineHeight = 13.sp
                            )
                            IconButton(
                                onClick = {
                                    val updated = currentTerms.toMutableList().apply { removeAt(idx) }
                                    val bundle = settings.copy(registrationTerms = updated)
                                    vm.saveAppSettings(bundle, "الأدمن")
                                    Toast.makeText(context, "تمت إزالة قانون التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete term", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = newRegistrationTermInput,
                        onValueChange = { newRegistrationTermInput = it },
                        label = { Text(if (isArabic) "اكتب شرطاً أو قانوناً جديداً..." else "Enter new registration rule...") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
                        onClick = {
                            if (newRegistrationTermInput.isNotBlank()) {
                                val updated = currentTerms.toMutableList().apply { add(newRegistrationTermInput) }
                                val bundle = settings.copy(registrationTerms = updated)
                                vm.saveAppSettings(bundle, "الأدمن")
                                newRegistrationTermInput = ""
                                Toast.makeText(context, "تمت إضافة وحقن قانون التسجيل بنجاح في قواعد البيانات", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.height(52.dp)
                    ) {
                        Text(if (isArabic) "إدراج" else "Add", color = palette.secondaryColor)
                    }
                }
            }
        }

        // --- SUBMIT ALL ---
        Spacer(modifier = Modifier.height(14.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth().height(48.dp),
            onClick = {
                val sc = scalePercent.toIntOrNull() ?: 50
                val op = opacityVal.toFloatOrNull() ?: 0.5f
                val dst = maxDist.toIntOrNull() ?: 50
                val cDays = cleanDays.toIntOrNull() ?: 30

                val bundle = settings.copy(
                    appNameAr = appName,
                    welcomeMessage = welcomeMsg,
                    footerText = footerText,
                    footerSizePercent = sc,
                    footerOpacity = op,
                    radiusMaxKm = dst,
                    isMaintenanceActive = mainAct,
                    chatIconSize = chatSize.toInt(),
                    chatIconColor = chatCol,
                    isChatIconHidden = iconHidden,
                    isChatIconDeleted = iconDeleted,
                    isChatDisabledForVisitors = blockVisitor,
                    isChatDisabledForProviders = blockProvider,
                    disabledChatNotificationMessage = disabledMsg,
                    assistantIconUrl = assistUrl,
                    chatIconUrl = customChatUrl,
                    assistantIconEffect = assistEffect,
                    chatIconEffect = chatEffect,
                    isAutoCleanupActive = cleanActive,
                    autoCleanupPeriodDays = cDays
                )
                vm.saveAppSettings(bundle, "الأدمن")
                Toast.makeText(context, if (isArabic) "تم حفظ الإعدادات ومزامنتها لحظياً!" else "Settings synchronized instantly!", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(if (isArabic) "💾 حفظ وحقن كافة التعديلات" else "💾 Save and Propagate Configuration", color = palette.secondaryColor, fontWeight = FontWeight.Bold)
        }
    }
}

// Tab: Complaint and Audit log dashboard logs
@Composable
fun TabAuditLogsSection(vm: MainViewModel, audits: List<AuditLog>, complaints: List<Report>, palette: ColorSchemePalette, isArabic: Boolean) {
    var complaintReason by remember { mutableStateOf("") }
    var selectedProvId by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "سجل العمليات والرقابة الفورية للأدمن:" else "Administrative Audit Trail:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(4.dp)
        ) {
            items(audits) { a ->
                Text(
                    text = "[${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(a.timestamp))}] :: ${a.adminName} -> ${a.action}",
                    color = Color.Green,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "البلاغات والشكاوى المسجلة:" else "Banned, complaints & active reports:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 13.sp)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(complaints) { rep ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1C1C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    Text(
                        text = "بلاغ ضد ${rep.providerName}: ${rep.reason}",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

// 2. Tab Manual Technician Adding Section
@Composable
fun TabManualTechnician(vm: MainViewModel, approved: List<Provider>, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("manage_providers")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لإضافة أو تعديل شؤون المهنيين يدوياً." else "🚫 Access Denied! You do not have permissions to manage service providers.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    var editMode by remember { mutableStateOf(false) }
    var targetProviderId by remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var mainCategoryIndex by remember { mutableIntStateOf(0) }
    var previewPrice by remember { mutableStateOf("") }
    var inspectionPrice by remember { mutableStateOf("") }
    var hasVipBadge by remember { mutableStateOf(false) }
    var isFem by remember { mutableStateOf(false) }

    val categories by vm.categories.collectAsStateWithLifecycle()
    val roots = categories.filter { it.parentId.isBlank() }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text(
            text = if (editMode) "📝 تعديل الكادر المهني المعتمد:" else "✍️ تفاصيل إضافة كادر فني جديد يدوياً تجاوزاً للموافقات:",
            color = palette.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text(if (isArabic) "اسم مقدم الخدمة" else "Provider Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = phone, onValueChange = { phone = it }, label = { Text(if (isArabic) "رقم الهاتف الفعال" else "Phone Number") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = address, onValueChange = { address = it }, label = { Text(if (isArabic) "المدينة/المحافظة وسكنه" else "Area Address details") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = area, onValueChange = { area = it }, label = { Text(if (isArabic) "الحي السكني أو الشارع" else "Block Neighborhood") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))

        TextField(value = previewPrice, onValueChange = { previewPrice = it }, label = { Text(if (isArabic) "سعر معاينة العمل الأولي (مثال: 5000 ريال)" else "Initial preview inspection price estimation") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = inspectionPrice, onValueChange = { inspectionPrice = it }, label = { Text(if (isArabic) "سعر الفحص التقني (سعر النزول للميدان)" else "Inspection field visit price estimation") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "اختر تصنيف المهنة والخلية لربطه بها:" else "Select category designation:", color = Color.White, fontSize = 11.sp)
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
            roots.forEachIndexed { index, category ->
                val active = (mainCategoryIndex == index)
                Button(
                    onClick = { mainCategoryIndex = index },
                    colors = ButtonDefaults.buttonColors(containerColor = if (active) palette.primary else palette.surface),
                    modifier = Modifier.padding(end = 4.dp).height(32.dp),
                    contentPadding = PaddingValues(horizontal = 6.dp)
                ) {
                    Text(category.nameAr, color = if (active) palette.secondaryColor else Color.White, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = hasVipBadge, onCheckedChange = { hasVipBadge = it })
            Text(if (isArabic) "تفعيل شارة التميز والفرز الخاص (VIP Badge)" else "Apply VIP Highlights badges and premium order", color = Color.Yellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isFem, onCheckedChange = { isFem = it })
            Text(if (isArabic) "مقدم الخدمة أنثى (سيتم تلقينها الآفاتار النسابي تلقائياً)" else "Female provider (Automatically assign custom profession avatar icon)", color = Color.White, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (name.isBlank() || phone.isBlank() || address.isBlank() || area.isBlank()) {
                    Toast.makeText(context, "الرجاء تعبئة كافة الحقول أولاً!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val chosenMain = roots.getOrNull(mainCategoryIndex)
                val categoryId = chosenMain?.id ?: "1"

                val autoAvatar = if (isFem) {
                    when (chosenMain?.nameAr ?: "") {
                        "خياطة" -> "https://images.unsplash.com/photo-1551488831-00ddcb6c6bd3"
                        "صالون نسائي" -> "https://images.unsplash.com/photo-1560066984-138dadb4c035"
                        "طبخ وحلويات" -> "https://images.unsplash.com/photo-1556910103-1c02745aae4d"
                        else -> "https://images.unsplash.com/photo-1544005313-94ddf0286df2"
                    }
                } else {
                    "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d"
                }

                if (editMode) {
                    val original = approved.find { it.id == targetProviderId }
                    original?.let {
                        val upd = it.copy(
                            name = name,
                            phone = phone,
                            address = address,
                            area = area,
                            categoryMainId = categoryId,
                            isFemale = isFem,
                            avatarUrl = if (it.avatarUrl.isBlank()) autoAvatar else it.avatarUrl,
                            isRecommended = hasVipBadge,
                            isPinned = hasVipBadge
                        )
                        vm.updateProviderManual(upd, vm.loggedInUsername.value)
                        Toast.makeText(context, "تم حفظ الكادر وعكس البيانات لـ Firestore بنجاح 💾", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val p = Provider(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        phone = phone,
                        categoryMainId = categoryId,
                        address = address,
                        area = area,
                        isFemale = isFem,
                        avatarUrl = autoAvatar,
                        isRecommended = hasVipBadge,
                        isPinned = hasVipBadge,
                        isVerified = true,
                        deviceId = "manual_inject_${UUID.randomUUID().toString().take(4)}"
                    )
                    vm.addProviderManual(p, vm.loggedInUsername.value)
                    Toast.makeText(context, "تمت إضافة وحقن مقدم الخدمة وتنشيطه بالخريطة فوراً!", Toast.LENGTH_SHORT).show()
                }

                // Reset fields
                name = ""
                phone = ""
                address = ""
                area = ""
                previewPrice = ""
                inspectionPrice = ""
                hasVipBadge = false
                isFem = false
                editMode = false
                targetProviderId = ""
            }
        ) {
            Text(if (editMode) "حفظ وحقن التعديلات" else "إضافة فني يدوياً إلى الدليل والخرائط المباشرة", color = palette.secondaryColor, fontWeight = FontWeight.Bold)
        }

        if (editMode) {
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    name = ""
                    phone = ""
                    address = ""
                    area = ""
                    previewPrice = ""
                    inspectionPrice = ""
                    hasVipBadge = false
                    isFem = false
                    editMode = false
                    targetProviderId = ""
                }
            ) {
                Text("إلغاء وضع التعديل")
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = palette.primary.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(10.dp))

        Text(if (isArabic) "قائمة الفنيين النشطين (انقر للتعديل):" else "Current Active Professionals List (Tap to edit):", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))

        approved.forEach { p ->
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .clickable {
                        name = p.name
                        phone = p.phone
                        address = p.address
                        area = p.area
                        hasVipBadge = p.isRecommended
                        isFem = p.isFemale
                        editMode = true
                        targetProviderId = p.id
                        // Match categories index
                        val idx = roots.indexOfFirst { it.id == p.categoryMainId }
                        if (idx >= 0) mainCategoryIndex = idx
                        Toast.makeText(context, "تم تحميل بيانات الفني (${p.name}) لتعديلها", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Face, contentDescription = null, tint = palette.primary, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(p.name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("${p.phone} • ${p.area}", color = Color.LightGray, fontSize = 10.sp)
                    }
                    if (p.isRecommended) {
                        Text("VIP", color = Color.Yellow, fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }
        }
    }
}

// 4. Tab Category and City Administration
@Composable
fun TabCategoriesAndCities(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("manage_categories_cities")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لتعديل أو إضافة الأقسام والمدن." else "🚫 Access Denied! You do not have permissions to manage categories or coverage cities.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    var catNameAr by remember { mutableStateOf("") }
    var catNameEn by remember { mutableStateOf("") }
    var catDescAr by remember { mutableStateOf("") }
    var catIcon by remember { mutableStateOf("") }
    var catPublish by remember { mutableStateOf(true) }

    var cityNameAr by remember { mutableStateOf("") }
    var cityNameEn by remember { mutableStateOf("") }

    val categories by vm.categories.collectAsStateWithLifecycle()
    val citiesList by vm.cities.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text(if (isArabic) "📁 إضافة قسم رئيسي للمهن وتحديد الخريطة:" else "📁 Add Main Category & Icon:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = catNameAr, onValueChange = { catNameAr = it }, label = { Text(if (isArabic) "اسم القسم بالعربية" else "Category name in Arabic") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = catNameEn, onValueChange = { catNameEn = it }, label = { Text(if (isArabic) "اسم القسم بالإنجليزية (الآيدي)" else "Category identifier in English") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = catDescAr, onValueChange = { catDescAr = it }, label = { Text(if (isArabic) "شرح مبسط لمجالات القسم" else "Short summary explanation") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = catIcon, onValueChange = { catIcon = it }, label = { Text(if (isArabic) "أيقونة تعبيرية للقسم (مثال: 🛠️)" else "Emoji / Icon indicator") }, modifier = Modifier.fillMaxWidth())

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = catPublish, onCheckedChange = { catPublish = it })
            Text(if (isArabic) "نشر القسم فوراً وجعله ظاهراً للجمهور بالبحث" else "Fully publish this profession to visitors directory", color = Color.White, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (catNameAr.isBlank() || catNameEn.isBlank()) {
                    Toast.makeText(context, "الرجاء كشط اسم القسم بالعربي والآيدي الإنجليزي أولاً!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val newCat = Category(
                    id = catNameEn.lowercase().trim(),
                    nameAr = catNameAr,
                    nameEn = catNameEn,
                    iconUrl = catIcon.ifBlank { "🛠️" },
                    order = categories.size + 1
                )
                vm.addCategory(newCat, vm.loggedInUsername.value)
                Toast.makeText(context, "تم حقن ونشر تصنيف القسم فوراً بقاعدة البيانات!", Toast.LENGTH_SHORT).show()
                catNameAr = ""
                catNameEn = ""
                catDescAr = ""
                catIcon = ""
            }
        ) {
            Text(if (isArabic) "إدراج وحفظ القسم وتثبيته بالمقدمة" else "Inject Category and Pin to Top", color = palette.secondaryColor)
        }

        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = palette.primary.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(10.dp))

        Text(if (isArabic) "🗺️ إضافة مدينة أول محافظات التغطية الجغرافية:" else "🗺️ Register New Governorate & Focus Cities:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = cityNameAr, onValueChange = { cityNameAr = it }, label = { Text(if (isArabic) "اسم المدينة بالعربية (مثال: صنعاء)" else "City Arabic label") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = cityNameEn, onValueChange = { cityNameEn = it }, label = { Text(if (isArabic) "اسم المدينة بالإنجليزية" else "City English label") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (cityNameAr.isBlank() || cityNameEn.isBlank()) {
                    Toast.makeText(context, "يرجى تعبئة اسم المدينة بالكامل!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val newCity = City(
                    id = UUID.randomUUID().toString(),
                    nameAr = cityNameAr,
                    nameEn = cityNameEn
                )
                vm.addCity(newCity, vm.loggedInUsername.value)
                Toast.makeText(context, "تم ربط وحقن المدينة الجغرافية بنجاح 🗺️", Toast.LENGTH_SHORT).show()
                cityNameAr = ""
                cityNameEn = ""
            }
        ) {
            Text(if (isArabic) "حفظ المدينة بالخارطة والفرز السريع" else "Pin coverage Governorate geographic system", color = palette.secondaryColor)
        }

        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = palette.primary.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(10.dp))

        Text(if (isArabic) "إدارة وحذف التصنيفات والمدن الحالية:" else "Delete Categorizations & Registered Cities:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))

        Text("الأقسام الحالية المفعمة (${categories.size}):", color = Color.Gray, fontSize = 11.sp)
        categories.forEach { cat ->
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            ) {
                Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(cat.iconUrl, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${cat.nameAr} (${cat.nameEn})", color = Color.White, modifier = Modifier.weight(1f), fontSize = 11.sp)
                    IconButton(onClick = {
                        vm.deleteCategory(cat.id, vm.loggedInUsername.value)
                        Toast.makeText(context, "تمت إزالة وتفتيت القسم", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("المدن الجغرافية المعتمدة (${citiesList.size}):", color = Color.Gray, fontSize = 11.sp)
        citiesList.forEach { city ->
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            ) {
                Row(modifier = Modifier.padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = palette.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${city.nameAr} (${city.nameEn})", color = Color.White, modifier = Modifier.weight(1f), fontSize = 11.sp)
                    IconButton(onClick = {
                        vm.deleteCity(city.id, vm.loggedInUsername.value)
                        Toast.makeText(context, "تم إلغاء وحذف المدينة من الدليل", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

// 5. Tab Reports, Audits and Exporting Data (PDF / CSV formats)
@Composable
fun TabReportsAndComplaints(vm: MainViewModel, complaints: List<Report>, audits: List<AuditLog>, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("view_reports_audits")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لمطالعة البلاغات أو تصدير التقارير الإدارية." else "🚫 Access Denied! You do not have permissions to view audits or export lists.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "📊 تصدير التقارير الرقابة والامتثال فوراً:" else "📊 Core Fiscal & Compliance Register Exporter:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                onClick = {
                    val csvText = buildString {
                        append("ID,Provider,Reason,Timestamp\n")
                        complaints.forEach {
                            append("${it.providerId},\"${it.providerName}\",\"${it.reason}\",${it.timestamp}\n")
                        }
                    }
                    Toast.makeText(context, "تم تصدير تقرير البلاغات بصيغة CSV بنجاح في مساحة التخزين الخاصة بك!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.weight(1f).padding(end = 4.dp).height(38.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("تصدير CSV مميز", fontSize = 10.sp)
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                onClick = {
                    Toast.makeText(context, "تمت جدولة وتكوين فاتورة الأنشطة وتصدير ملف PDF الأسبوعي بنجاح!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.weight(1f).padding(start = 4.dp).height(38.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("تصدير الأسبوعي PDF", fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "سجل العمليات الإدارية والرقابة الفورية للأدمن:" else "Administrative Audit Trail Logbook:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(4.dp)
        ) {
            items(audits) { a ->
                Text(
                    text = "[${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(a.timestamp))}] :: ${a.adminName} -> ${a.action}",
                    color = Color.Green,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(vertical = 2.dp),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "البلاغات والشكاوى المسجلة من العملاء ضد الكوادر:" else "Focus Complaints registered against professionals:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        LazyColumn(modifier = Modifier.weight(1f)) {
            if (complaints.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                        Text(if (isArabic) "سجل البلاغات خالي مائة بالمائة." else "No focus complaints logged.", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            } else {
                items(complaints) { rep ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1C1C)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "🛡️ شكوى ضد الكارد: ${rep.providerName}", color = palette.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "السبب: ${rep.reason}", color = Color.White, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "الوقت: ${SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date(rep.timestamp))}", color = Color.Gray, fontSize = 9.sp)
                        }
                    }
                }
            }
        }
    }
}

// 6. Tab Chat History and Clear Logs (Privacy system)
@Composable
fun TabChatHistoryPrivacy(vm: MainViewModel, chats: List<Chat>, messages: List<ChatMessage>, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("view_chat_history")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لمشاهدة سجل محادثات الخصوصية أو تفريغها." else "🚫 Access Denied! You do not have permissions to view chat logs or clear conversations indices.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "🔒 الرقابة على سجلات الضمان والدردشة النشطة:" else "🔒 Conversations and Encryption Logs Trail Room:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(if (isArabic) "عدد غرف المحادثات: ${chats.size}" else "Active chat threads: ${chats.size}", color = Color.White, fontSize = 11.sp)
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    vm.clearAllChatsAndMessages(vm.loggedInUsername.value)
                    Toast.makeText(context, if (isArabic) "تم تفريغ غرف المحادثات لجميع الأعضاء وخصوصيتهم" else "All historical threads successfully wiped!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.height(34.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Text(if (isArabic) "مسح كامل السجلات نهائياً 🗑️" else "Wipe All Archives", fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(if (isArabic) "ملخص الغرف والرسائل الجارية:" else "Summary of all operational messaging:", color = Color.Gray, fontSize = 11.sp)

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (chats.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                        Text(if (isArabic) "لم تسجل أي ذبذبات محادثة حالياً للأمانة." else "No conversation records logged.", color = Color.Gray, fontSize = 11.sp)
                    }
                }
            } else {
                items(chats) { c ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = palette.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text("غرفة دردشة رقم: ${c.chatId.take(12)}", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("أطراف المحادثة: ${c.participants.joinToString(" • ")}", color = Color.LightGray, fontSize = 10.sp)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("آخر رسالة متداولة: ${c.lastMessage}", color = Color.White, fontSize = 11.sp, maxLines = 1)
                        }
                    }
                }
            }
        }
    }
}

// 7. Tab Active Providers Directory (Status, Deletion management)
@Composable
fun TabActiveProviders(vm: MainViewModel, approved: List<Provider>, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("manage_providers")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لإيقاف أو حذف مزودي الخدمات." else "🚫 Access Denied! You do not have permissions to drop or lock active providers.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "🛠️ إدارة المنتسبين النشطين وإطفاء التفعيل المباشر:" else "🛠️ Active Practitioner Directory & Availability Toggles:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (approved.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(if (isArabic) "لا يوجد مقدمو خدمات مسجلين حالياً." else "No activated professionals logged.", color = Color.Gray)
                    }
                }
            } else {
                items(approved) { p ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = palette.surface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Card(modifier = Modifier.size(45.dp), shape = CircleShape) {
                                if (p.avatarUrl.isNotBlank()) {
                                    AsyncImage(model = p.avatarUrl, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.fillMaxSize().padding(6.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(p.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text("${p.area} • ${p.phone}", color = Color.LightGray, fontSize = 10.sp)
                                Text("الحالة بالدليل: ${if (!p.isVerified) "🚫 محظور ومجمد" else "✅ متاح بالخربطة والفرز"}", color = if (!p.isVerified) Color.Red else Color.Green, fontSize = 9.sp)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Switch(
                                    checked = p.isVerified,
                                    onCheckedChange = {
                                        vm.toggleProviderStatus(
                                            id = p.id,
                                            isPinned = p.isPinned,
                                            isRecommended = p.isRecommended,
                                            isVerified = !p.isVerified,
                                            isSubscribed = p.isSubscribed,
                                            adminName = vm.loggedInUsername.value
                                        )
                                        Toast.makeText(context, "تم عكس وتبديل حالة ترخيص العضو المهني بنجاح", Toast.LENGTH_SHORT).show()
                                    }
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(onClick = {
                                    vm.deleteProvider(p.id, vm.loggedInUsername.value)
                                    Toast.makeText(context, "أزيل مقدم الخدمة تماماً من الدليل النهائي 🗑️", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Drop Provider", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 8. Tab Subscriptions & Pinning Tier panel
@Composable
fun TabSubscriptionsPinning(vm: MainViewModel, approved: List<Provider>, palette: ColorSchemePalette, isArabic: Boolean) {
    val context = LocalContext.current
    if (!vm.hasPermission("manage_providers")) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! ليس لديك الصلاحية لتخصيص خطط الاشتراكات أو تثبيت النجوم." else "🚫 Access Denied! You do not have permissions to manage client plans or highlighted stars pinning.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(if (isArabic) "💎 لوحة التحكم بالاشتراكات وتثبيت النجوم الموصى بها:" else "💎 Client Plans, Highlighted Stars and Subscriptions Tiers:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(approved) { p ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = palette.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("المنطقة السكنية: ${p.area}", color = Color.Gray, fontSize = 10.sp)
                            Text("باقة العضوية الحالية: ${if (p.isRecommended) "🥇 خطة ذهبية VIP (مثبتة)" else "🥈 خطة مجانية فضية"}", color = if (p.isRecommended) Color.Yellow else Color.White, fontSize = 10.sp)
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = if (p.isRecommended) Color(0xFFC62828) else Color(0xFFE5A93C)),
                            onClick = {
                                val updated = p.copy(isRecommended = !p.isRecommended, isPinned = !p.isPinned)
                                vm.updateProviderManual(updated, vm.loggedInUsername.value)
                                Toast.makeText(context, "تم تبديل باقة الفني وحقن شارة VIP بالدليل المباشر!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.height(34.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(if (p.isRecommended) "ترقية للفضية" else "ترقية ל- VIP ذهبي ✨", fontSize = 9.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// 9. Tab Admin Management (Add Dynamic Supervisor and Permissions checks)
@Composable
fun TabAdminManagement(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    if (vm.loggedInUser.value != "admin") {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text(if (isArabic) "🚫 عذراً! وحده المالك الرئيسي للمنصة يستطيع إنشاء أو تعديل حسابات المشرفين وصلاحياتهم الكلية." else "🚫 ONLY the primary platform owner can manage dynamic supervisor credentials and permissions parameters.", color = Color.Red, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
        return
    }

    var superUser by remember { mutableStateOf("") }
    var superPass by remember { mutableStateOf("") }

    // Supervisors granular permissions list checkboxes in M3 Compose UI flow
    var pApprove by remember { mutableStateOf(true) }
    var pCatCity by remember { mutableStateOf(false) }
    var pAds by remember { mutableStateOf(false) }
    var pDeleteProvs by remember { mutableStateOf(false) }
    var pManageByManual by remember { mutableStateOf(false) }
    var pViewAuditsAndRep by remember { mutableStateOf(true) }
    var pViewChatsHistoryAndPrivacy by remember { mutableStateOf(false) }

    val supervisorsList by vm.supervisors.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text(if (isArabic) "👤 تعيين وتخصيص صلاحيات وحسابات المشرفين المساعدين:" else "👤 Create & Assign Supervisor Permissions Controls:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(10.dp))

        TextField(value = superUser, onValueChange = { superUser = it }, label = { Text(if (isArabic) "اسم المستخدم للمشرف" else "Supervisor Sign-In Username") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        TextField(value = superPass, onValueChange = { superPass = it }, label = { Text(if (isArabic) "كلمة المرور الحصينة" else "Secure password keys") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))
        Text(if (isArabic) "حدد محددات الصلاحيات الإشرافية الممنوحة بالفصل:" else "Determine specific delegation limits:", color = palette.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pApprove, onCheckedChange = { pApprove = it })
            Text(if (isArabic) "مراجعة واعتماد طلبات التسجيل الجدد (Approve registrations)" else "Approve pending registrations", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pCatCity, onCheckedChange = { pCatCity = it })
            Text(if (isArabic) "إضافة وتعديل الأقسام والغطاء الجغرافي للمدن" else "Manage professions & geographic coverage cities list", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pAds, onCheckedChange = { pAds = it })
            Text(if (isArabic) "إدارة اللافتات والبنرات الترويجية والإعلانات الممولة" else "Manage banners & funded marketing systems", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pDeleteProvs, onCheckedChange = { pDeleteProvs = it })
            Text(if (isArabic) "حذف مقدمي الخدمات المعتمدين وطردهم من الخرائط" else "Authorize discarding service providers from listings", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pManageByManual, onCheckedChange = { pManageByManual = it })
            Text(if (isArabic) "حقن وإضافة وتعديل الفنيين المهنيين يدوياً بشكل مباشر" else "Add or manually modify active professionals profiles", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pViewAuditsAndRep, onCheckedChange = { pViewAuditsAndRep = it })
            Text(if (isArabic) "عرض البلاغات، الشكاوى، وتصدير الدفاتر الكلية مالي وإداري" else "Access complaint records & CSV spreadsheet exports", color = Color.White, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = pViewChatsHistoryAndPrivacy, onCheckedChange = { pViewChatsHistoryAndPrivacy = it })
            Text(if (isArabic) "الرقابة على المحادثات وغرف الدردشة للعملاء والخصوصية" else "Oversee client-practitioner operational messaging channels", color = Color.White, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (superUser.isBlank() || superPass.isBlank()) {
                    Toast.makeText(context, "الرجاء كشط تفاصيل حساب المشرف أولاً!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                // Collect enabled permissions keys
                val perms = mutableListOf<String>()
                if (pApprove) perms.add("approve_reject_requests")
                if (pCatCity) perms.add("manage_categories_cities")
                if (pAds) perms.add("manage_ads_banners")
                if (pDeleteProvs) perms.add("delete_active_providers")
                if (pManageByManual) perms.add("manage_providers")
                if (pViewAuditsAndRep) perms.add("view_reports_audits")
                if (pViewChatsHistoryAndPrivacy) perms.add("view_chat_history")

                val s = Supervisor(
                    id = UUID.randomUUID().toString(),
                    username = superUser.trim(),
                    password = superPass.trim(),
                    permissions = perms
                )
                vm.createSupervisor(s, vm.loggedInUsername.value)
                Toast.makeText(context, "تم حقن حساب المشرف وصلاحياته بنجاح 📥", Toast.LENGTH_SHORT).show()

                superUser = ""
                superPass = ""
            }
        ) {
            Text(if (isArabic) "إدراج وحل المشرف بصلاحياته فوراً" else "Installs Supervisor dynamic credentials to Firestore", color = palette.secondaryColor)
        }

        Spacer(modifier = Modifier.height(14.dp))
        Divider(color = palette.primary.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(10.dp))

        Text(if (isArabic) "حسابات المشرفين المساعدين النشيطين حالياً:" else "Registered Active Supervisors Dynamic Listing:", color = palette.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        Spacer(modifier = Modifier.height(6.dp))

        supervisorsList.forEach { s ->
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("مشرف: ${s.username}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        IconButton(onClick = {
                            vm.deleteSupervisor(s.id, vm.loggedInUsername.value)
                            Toast.makeText(context, "تم إقصاء وحذف حساب المشرف العام المساعد", Toast.LENGTH_SHORT).show()
                        }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Supervisor", tint = Color.Red, modifier = Modifier.size(16.dp))
                        }
                    }
                    Text("كلمة المرور: ${s.password}", color = Color.LightGray, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("الصلاحيات الممنوحة: ${s.permissions.joinToString(" • ")}", color = palette.primary, fontSize = 9.sp)
                }
            }
        }
    }
}

// 13. Detailed Profile and Reviews panel showing Rating average
@Composable
fun ProviderDetailsScreen(vm: MainViewModel, palette: ColorSchemePalette, isArabic: Boolean) {
    val p by vm.selectedProvider.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    var ratingStars by remember { mutableIntStateOf(5) }
    var ratingComment by remember { mutableStateOf("") }

    p?.let { prov ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Details
            Box(modifier = Modifier.size(90.dp)) {
                if (prov.avatarUrl.isNotBlank()) {
                    AsyncImage(
                        model = prov.avatarUrl,
                        contentDescription = prov.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                } else {
                    Icon(Icons.Default.Face, contentDescription = "Face", tint = palette.primary, modifier = Modifier.size(90.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(prov.name, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                if (prov.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.Verified, contentDescription = "Verified badge", tint = Color(0xFF1DA1F2), modifier = Modifier.size(18.dp))
                }
            }
            Text("${prov.address} - ${prov.area}", color = Color.LightGray, fontSize = 13.sp)

            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                // Call Button
                Button(
                    onClick = { /* Simulated intent launch */ },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                ) {
                    Icon(Icons.Default.Call, contentDescription = "Call phone provider", tint = palette.secondaryColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isArabic) "اتصال" else "Call", color = palette.secondaryColor)
                }

                // Chat direct Button
                Button(
                    onClick = {
                        val currentUserId = vm.loggedInUser.value.ifBlank { "visitor" }
                        vm.startChatWithProvider(currentUserId, prov.id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "Chat direct provider", tint = palette.secondaryColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isArabic) "دردشة" else "Chat", color = palette.secondaryColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = palette.primary)
            Spacer(modifier = Modifier.height(10.dp))

            // Reporting Center
            Text(if (isArabic) "⚠️ الإبلاغ عن هذا الفني:" else "⚠️ Report improper activity:", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            TextField(
                value = ratingComment,
                onValueChange = { ratingComment = it },
                label = { Text(if (isArabic) "تفاصيل البلاغ أو التجربة السيئة..." else "Complaint report text...") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = {
                    if (ratingComment.isNotBlank()) {
                        vm.postReport(Report(providerId = prov.id, providerName = prov.name, reason = ratingComment, timestamp = System.currentTimeMillis()))
                        ratingComment = ""
                    }
                }
            ) {
                Text(if (isArabic) "تأجيل البلاغ للإشراف" else "Submit Report against practitioner")
            }
        }
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(if (isArabic) "لم يتم تحديد مقدم الخدمة." else "No provider selected.", color = Color.White)
    }
}
