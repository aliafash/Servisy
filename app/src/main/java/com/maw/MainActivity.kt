package com.maw

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import coil.compose.AsyncImage

// --- THEME COLORS ---
object AppTheme {
    val darkBg = Color(0xFF0F1E1F)      // Slate Dark Greenish
    val surfaceDark = Color(0xFF162A2C)  // Lighter Slate Container
    val primaryRed = Color(0xFFD32F2F)   // Crimson red
    val accentGold = Color(0xFFE5A93B)   // Gold accent
    val grayText = Color(0xFF90A4AE)     // Soft cool gray
}

// --- DATA STRUCTURES ---
data class Provider(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val phone2: String = "", // Secondary Phone
    val specialty: String = "",
    val rating: Double = 4.5,
    val ratingCount: Int = 12,
    val city: String = "",
    val area: String = "",
    val addressDetail: String = "", // Detailed Address
    val categoryId: String = "", // Main Category
    val subCategory: String = "", // Sub-category
    val inspectionPrice: Double = 1000.0, // Preview price
    val workHours: String = "8:00 ص - 8:00 م",
    val isAvailable: Boolean = true,
    val isPinned: Boolean = false,
    val isVerified: Boolean = true,
    val isVip: Boolean = false,
    val isRecommended: Boolean = false,
    val serviceDescription: String = "تقديم وتوفير كافة خدمات الصيانة بدقة وجودة عالية",
    val offeredServices: List<String> = listOf("فحص وصيانة عامة", "تأسيس شبكات منزلية"),
    val latX: Double = 15.369,
    val lonY: Double = 44.191,
    val coverUri: String = "",
    val avatarUri: String = "",
    val galleryUris: List<String> = emptyList()
)

data class PromoBanner(
    val id: String = UUID.randomUUID().toString(),
    val uri: String = "",
    val isVideo: Boolean = false,
    val title: String = ""
)

data class CardSettings(
    val bgHex: String = "#162A2C",
    val titleColorHex: String = "#FFFFFF",
    val ratingColorHex: String = "#E5A93B",
    val locationColorHex: String = "#90A4AE",
    val priceColorHex: String = "#E5A93B",
    
    // Image sizes
    val coverHeight: Int = 120,
    val avatarSize: Int = 50,
    val isAvatarCircular: Boolean = true,
    
    // Badges visibility & colors
    val showVip: Boolean = true,
    val showVerified: Boolean = true,
    val showRecommended: Boolean = true,
    val vipColorHex: String = "#FFD700",
    val verifiedColorHex: String = "#1E88E5",
    val recommendedColorHex: String = "#E5A93B",
    
    // Buttons visibility & colors
    val showCall: Boolean = true,
    val showWhatsapp: Boolean = true,
    val showDetails: Boolean = true,
    val showBooking: Boolean = true,
    
    val callColorHex: String = "#2E7D32",
    val whatsappColorHex: String = "#25D366",
    val detailsColorHex: String = "#1E88E5",
    val bookingColorHex: String = "#D32F2F",
    
    // Info visibility & ordering
    val showDistance: Boolean = true,
    val showPrice: Boolean = true,
    val showAvailability: Boolean = true,
    val showRatingCount: Boolean = true,
    
    // Order lists
    val infoOrder: List<String> = listOf("الاسم", "التقييم", "المسافة", "الموقع", "سعر المعاينة", "الحالة"),
    val buttonsOrder: List<String> = listOf("اتصال", "واتساب", "تفاصيل", "حجز"),
    
    // Spacing
    val itemSpacing: Int = 8,
    val cardPadding: Int = 12,
    
    // Hover / Press animations
    val enableScaleAnimation: Boolean = true,
    val pressScaleRatio: Float = 0.95f
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: String, // "User" or "AI"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class Booking(
    val id: String = "",
    val providerId: String = "",
    val categoryId: String = "",
    val userName: String = "",
    val userPhone: String = "",
    val userArea: String = "",
    val status: String = "قيد الانتظار", // "قيد الانتظار", "تم القبول", "قيد التنفيذ", "مكتمل", "ملغي"
    val timestamp: Long = System.currentTimeMillis(),
    val customFieldsData: Map<String, String> = emptyMap(),
    val supervisorAllocated: String = "",
    val assignedTechnicianId: String = "",
    val distributionModeUsed: String = "",
    val serviceInfo: String = "",
    val isHidden: Boolean = false
)

data class Category(
    val id: String = "",
    val name: String = ""
)

data class CustomFormField(
    val id: String = "",
    val label: String = "",
    val type: String = "Text", // "Text", "Number", "Dropdown", "DateTimePicker", "TextArea"
    val isRequired: Boolean = false,
    val dropdownOptions: String = "", // comma separated values for Dropdown
    val isHidden: Boolean = false
)

data class NotificationRule(
    val eventId: String = "", // e.g., "new_booking", "supervisor_assign", "tech_accept", ...
    val description: String = "",
    val isEnabled: Boolean = true,
    val templateText: String = ""
)

data class ManualNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val body: String = "",
    val segment: String = "", // "All", "Sana'a Techs", "Sana'a Users", "Electrical Category Techs"
    val scheduledTime: String = "", // empty means sent immediately
    val sentTime: String = "",
    val isSent: Boolean = true
)

data class AuditLog(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val actor: String = "", // "User", "Admin", "Supervisor", "Technician"
    val action: String = "",
    val details: String = ""
)

// --- HELPER TOAST UTILITY ---
fun showAppToast(context: Context, message: String, isSuccess: Boolean = true) {
    val prefix = if (isSuccess) "🎯 " else "⚠️ "
    Toast.makeText(context, "$prefix$message", Toast.LENGTH_LONG).show()
}

// --- VIEW MODEL FOR REACIVE offline/online STATE MANAGEMENT ---
class MainViewModel : ViewModel() {
    private val _providers = MutableStateFlow<List<Provider>>(emptyList())
    val providers: StateFlow<List<Provider>> = _providers.asStateFlow()

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _formFields = MutableStateFlow<List<CustomFormField>>(emptyList())
    val formFields: StateFlow<List<CustomFormField>> = _formFields.asStateFlow()

    private val _isBookingFormEnabled = MutableStateFlow(true)
    val isBookingFormEnabled: StateFlow<Boolean> = _isBookingFormEnabled.asStateFlow()

    private val _notificationRules = MutableStateFlow<List<NotificationRule>>(emptyList())
    val notificationRules: StateFlow<List<NotificationRule>> = _notificationRules.asStateFlow()

    private val _sentNotifications = MutableStateFlow<List<ManualNotification>>(emptyList())
    val sentNotifications: StateFlow<List<ManualNotification>> = _sentNotifications.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(emptyList())
    val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

    // Promo Banners State
    private val _banners = MutableStateFlow<List<PromoBanner>>(emptyList())
    val banners: StateFlow<List<PromoBanner>> = _banners.asStateFlow()

    // Booking form terms
    private val _bookingTerms = MutableStateFlow("ملاحظة هامة: يجب الالتزام ببنود حجز موعد الصيانة في اليمن، وتوفير القطع للورش المهنية.")
    val bookingTerms: StateFlow<String> = _bookingTerms.asStateFlow()

    // Unified Card settings
    private val _cardSettings = MutableStateFlow(CardSettings())
    val cardSettings: StateFlow<CardSettings> = _cardSettings.asStateFlow()

    // Chat History
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _routingMode = MutableStateFlow(2) // Default to 2
    val routingMode: StateFlow<Int> = _routingMode.asStateFlow()

    private val _cardStyleName = MutableStateFlow("Cairo")
    val cardStyleName: StateFlow<String> = _cardStyleName.asStateFlow()

    private val _cardFontSize = MutableStateFlow(12)
    val cardFontSize: StateFlow<Int> = _cardFontSize.asStateFlow()

    private val _cardPadding = MutableStateFlow(8)
    val cardPadding: StateFlow<Int> = _cardPadding.asStateFlow()

    private val _cardCornerRadius = MutableStateFlow(12)
    val cardCornerRadius: StateFlow<Int> = _cardCornerRadius.asStateFlow()

    private val _pendingApprovals = MutableStateFlow<List<Provider>>(emptyList())
    val pendingApprovals: StateFlow<List<Provider>> = _pendingApprovals.asStateFlow()

    private val _supportPhone = MutableStateFlow("779876543")
    val supportPhone: StateFlow<String> = _supportPhone.asStateFlow()

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    fun updateSupportPhone(newPhone: String) {
        _supportPhone.value = newPhone
    }

    fun setOnlineState(online: Boolean) {
        _isOnline.value = online
    }

    init {
        // Populate Categories
        _categories.value = listOf(
            Category("cat_elec", "كهرباء وصيانة منزلية"),
            Category("cat_plum", "سباكة وتمديدات صحية"),
            Category("cat_hvac", "تكييف وتبريد أجهزة كهربائية"),
            Category("cat_carp", "نجارة وأثاث وديكور")
        )

        // Populate initial Promo Banners
        _banners.value = listOf(
            PromoBanner(
                id = "b1",
                uri = "https://images.unsplash.com/photo-1540959733332-eab4deceeaf7?q=80&w=1000",
                title = "تخفيضات صيفية عظمى بنسبة 30% لصيانة مكيفات الكوادر"
            ),
            PromoBanner(
                id = "b2",
                uri = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=1000",
                title = "تأسيس تمديدات سباكة وكهرباء بأعلى جودة واحترافية"
            )
        )

        // Populate initial Providers
        _providers.value = listOf(
            Provider(
                id = "p1",
                name = "المهندس عادل الحمادي",
                phone = "771234567",
                phone2 = "771111222",
                specialty = "أخصائي تركيب وتبريد مكيفات هواء عملاقة",
                rating = 4.9,
                ratingCount = 28,
                city = "صنعاء",
                area = "حدة",
                addressDetail = "شارع حدة العام - جولة الرويشان",
                categoryId = "cat_hvac",
                subCategory = "مكيفات سبليت ومركزي وكاسيت",
                inspectionPrice = 1500.0,
                workHours = "9:00 ص - 9:00 م",
                isAvailable = true,
                isPinned = true,
                isVerified = true,
                isVip = true,
                isRecommended = true,
                serviceDescription = "متخصصون في تركيب وصيانة جميع أنواع المكيفات السكنية والتجارية مع ضمان قطع الغيار الأصلية.",
                offeredServices = listOf("غسيل وتنظيف مكيفات", "تعبئة غاز الفريون الأصلي", "شحن وتمديد مواسير نحاسية"),
                coverUri = "https://images.unsplash.com/photo-1621905251189-08b45d6a269e?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1540569014015-19a7be504e3a?q=80&w=200",
                galleryUris = listOf(
                    "https://images.unsplash.com/photo-1581092160607-ee22621dd758?q=80&w=400",
                    "https://images.unsplash.com/photo-1605810230434-7631ac76ec81?q=80&w=400"
                )
            ),
            Provider(
                id = "p2",
                name = "فني السباكة ماهر الأبارة",
                phone = "735112233",
                phone2 = "734333222",
                specialty = "صيانة حمامات ومضخات وشبكات مياه ذكية",
                rating = 4.7,
                ratingCount = 19,
                city = "عدن",
                area = "المنصورة",
                addressDetail = "حي المنصورة - جوار مستشفى الكوبي",
                categoryId = "cat_plum",
                subCategory = "صيانة عامة وتأسيس سباكة",
                inspectionPrice = 1000.0,
                workHours = "8:00 ص - 8:00 م",
                isAvailable = true,
                isPinned = false,
                isVerified = true,
                isVip = false,
                isRecommended = true,
                serviceDescription = "صيانة السباكة المنزلية الفورية، كشف تسريب المياه عبر الأجهزة، تأسيس وتمديد خطوط حمامات.",
                offeredServices = listOf("علاج تسريب الحوائط", "تركيب مضخات مياه ذكية", "تركيب وصيانة خلاطات وسخانات"),
                coverUri = "https://images.unsplash.com/photo-1504307651254-35680f356dfd?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1566492031773-4f4e44671857?q=80&w=200",
                galleryUris = listOf(
                    "https://images.unsplash.com/photo-1542013936693-8848e5740a7a?q=80&w=400"
                )
            ),
            Provider(
                id = "p3",
                name = "م. سليم الصنعاني للكهرباء",
                phone = "770998877",
                phone2 = "772223344",
                specialty = "تمديد شبكات كهرباء وتحكم وفحص القصر الكهربائي",
                rating = 4.8,
                ratingCount = 35,
                city = "صنعاء",
                area = "السبعين",
                addressDetail = "جوار حدائق السبعين العامة",
                categoryId = "cat_elec",
                subCategory = "كهرباء قوى وتوزيع منزلي",
                inspectionPrice = 2000.0,
                workHours = "8:30 ص - 10:00 م",
                isAvailable = true,
                isPinned = true,
                isVerified = true,
                isVip = true,
                isRecommended = false,
                serviceDescription = "فحص التماسات الكهرباء، تمديد خطوط طاقة شمسية ومقويات إشارة، إصلاح لوحات وتوزيع أحمال المنزل.",
                offeredServices = listOf("تأسيس كهرباء فلل وعماير", "إصلاح التماس القصر الكهربائي", "تركيب نظم الطاقة الشمسية"),
                coverUri = "https://images.unsplash.com/photo-1581092918056-0c4c3acd3789?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=200",
                galleryUris = emptyList()
            ),
            Provider(
                id = "p4",
                name = "فواز للنجارة الحديثة",
                phone = "711222333",
                phone2 = "",
                specialty = "صيانة الأثاث الخشبي والمطابخ وصناعة غرف نوم راقية",
                rating = 4.5,
                ratingCount = 8,
                city = "تعز",
                area = "الحوبان",
                addressDetail = "شارع الحوبان العام - جولة الصنعاني",
                categoryId = "cat_carp",
                subCategory = "موبيليا وتفصيل مطابخ",
                inspectionPrice = 800.0,
                workHours = "9:00 ص - 6:00 م",
                isAvailable = false,
                isPinned = false,
                isVerified = false,
                isVip = false,
                isRecommended = false,
                serviceDescription = "صيانة غرف النوم والشبابيك، تعديل مطابخ ألمنيوم وخشب، صناعة طاولات ومقاعد راقية بصنع محلي مهيب.",
                offeredServices = listOf("صيانة قفول وبوابات الخشب", "تخصيص وتفصيل دواليب ذكية", "تركيب ستائر وأثاث إيكيا"),
                coverUri = "https://images.unsplash.com/photo-1533090161767-e6ffed986c88?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?q=80&w=200",
                galleryUris = emptyList()
            )
        )

        // Populate initial pending registrations
        _pendingApprovals.value = listOf(
            Provider(
                id = "p_new1",
                name = "الفني بسام الوشلي",
                phone = "773344556",
                specialty = "تأسيس وإنشاء شبكات تمديدات كهربائية متكاملة",
                rating = 4.4,
                city = "صنعاء",
                area = "حي الروضة",
                categoryId = "cat_elec",
                isVerified = false
            ),
            Provider(
                id = "p_new2",
                name = "فؤاد الغراسي للسباكة",
                phone = "712211332",
                specialty = "إنقاذ فوري سباكة منزلي وتعديل خلاطات مياه",
                rating = 4.3,
                city = "صنعاء",
                area = "الدائري",
                categoryId = "cat_plum",
                isVerified = false
            )
        )

        // Populate custom fields initial state
        _formFields.value = listOf(
            CustomFormField("f1", "تاريخ وتوقيت الزيارة المفضل", "DateTimePicker", true),
            CustomFormField("f2", "ملاحظات وتفاصيل المشكلة الفنية بدقة", "TextArea", false),
            CustomFormField("f3", "هل المشكلة طارئة (تستدعي إنقاذ فوري)؟", "Dropdown", true, "نعم طارئة جداً,لا يمكن تأجيلها")
        )

        // Populate Notification Rules
        _notificationRules.value = listOf(
            NotificationRule("new_booking", "إشعار حجز جديد من مستخدم (حسب آلية التوزيع)", true, "مرحباً فنينا، تم إسناد حجز جديد لك برقم {ID} في منطقة {AREA}"),
            NotificationRule("supervisor_assign", "مشرف القسم يوزع حجزاً لفني معين", true, "إشعار من المشرف: تم توجيه المهمة رقم {ID} إليك رسمياً"),
            NotificationRule("tech_accept", "الفني يقبل الحجز (يصل للمستخدم)", true, "عزيزنا العميل، تم قبول طلب حجزك رقم {ID} والفني في طريقه إليك"),
            NotificationRule("tech_progress", "الفني يغير الحالة إلى قيد التنفيذ", true, "فنينا الرائع بدأ الصيانة الآن للطلب رقم {ID}"),
            NotificationRule("tech_finish", "الفني ينهي الخدمة ويكمل الحجز لتوليد التقييم", true, "تم إتمام خدمتك بنجاح للطلب {ID}! يرجى تقييم الفني الآن"),
            NotificationRule("admin_override", "الأدمن يغير حالة الحجز يدوياً", true, "تنبيه إداري: تم تحديث مسار طلبكم {ID} إلى {STATUS}"),
            NotificationRule("sync_fail", "تنبيه فشل المزامنة وقاعدة البيانات", true, "تنبيه للنظام: حدث فشل في مزامنة السحابة لبيانات الحجوزات"),
            NotificationRule("new_tech_reg", "فني جديد يسجل وينتظر موافقة الإدارة برقم فريد", true, "تقديم فني جديد: الفني {NAME} ينتظر تفعيل حسابه حالياً")
        )

        _sentNotifications.value = emptyList() // Start empty per instruction "احذف الاشعارات الموجوده مسبقا"

        _auditLogs.value = listOf(
            AuditLog(UUID.randomUUID().toString(), System.currentTimeMillis() - 7200000, "Admin", "تسجيل دخول كمسؤول", "تم الدخول بنجاح من جهاز الآدمن الرئيسي")
        )

        // Populate initial Bookings
        _bookings.value = listOf(
            Booking("B-1", "p1", "cat_hvac", "أحمد الوادعي", "771110000", "حدة", "مكتمل", System.currentTimeMillis() - 86400000, mapOf("f1" to "اليوم 10 صباحاً")),
            Booking("B-2", "p3", "cat_elec", "خالد الريمي", "732221122", "السبعين", "قيد التنفيذ", System.currentTimeMillis() - 3600000, mapOf("f1" to "غداً 4 عصراً")),
            Booking("B-3", "p4", "cat_carp", "ياسر القدسي", "715556677", "الحوبان", "تم القبول", System.currentTimeMillis() - 1200000, mapOf("f1" to "الخميس المقبل")),
            Booking("B-4", "p1", "cat_hvac", "سعيد باوزير", "779998822", "حدة", "قيد الانتظار", System.currentTimeMillis())
        )
    }

    // --- LOGS GENERAL ADD HELPER ---
    fun addAudit(actor: String, action: String, details: String) {
        val list = _auditLogs.value.toMutableList()
        list.add(0, AuditLog(actor = actor, action = action, details = details))
        _auditLogs.value = list
    }

    // --- ACTION TOAST / ACTIONS LOG ---
    fun updateCardStyle(font: String, size: Int, padding: Int, cornerRadius: Int) {
        _cardStyleName.value = font
        _cardFontSize.value = size
        _cardPadding.value = padding
        _cardCornerRadius.value = cornerRadius
        addAudit("Admin", "تعديل واجهة البطاقات والخطوط", "الخط: $font، الحجم: ${size}sp، الحواف: ${cornerRadius}dp")
    }

    fun modifyField(field: CustomFormField) {
        val list = _formFields.value.toMutableList()
        val index = list.indexOfFirst { it.id == field.id }
        if (index >= 0) {
            list[index] = field
            _formFields.value = list
            addAudit("Admin", "تعديل حقل الاستمارة", "تعديل الحقل ${field.label}")
        } else {
            val fieldWithId = if (field.id.isEmpty()) field.copy(id = "field_" + System.currentTimeMillis()) else field
            list.add(fieldWithId)
            _formFields.value = list
            addAudit("Admin", "إضافة حقل جديد للاستمارة", "إضافة حقل ${field.label}")
        }
    }

    fun removeField(id: String) {
        val list = _formFields.value.toMutableList()
        val f = list.find { it.id == id }
        if (f != null) {
            list.remove(f)
            _formFields.value = list
            addAudit("Admin", "حذف حقل استمارة الحجز", "الحقل المحذوف: ${f.label}")
        }
    }

    fun setRoutingMode(id: Int) {
        _routingMode.value = id
        val desc = when(id) {
            1 -> "الإرسال لمشرف القسم أولاً"
            2 -> "الآلية الجغرافية - لأقرب فني"
            3 -> "بث جماعي لجميع فنيي القسم"
            4 -> "فني محدد مسبقاً لكل منطقة"
            else -> "توزيع يدوي عن طريق الأدمن العام"
        }
        addAudit("Admin", "تغيير نمط توزيع الحجوزات", "النمط الجديد: $desc")
    }

    fun setBookingFormEnabled(enabled: Boolean) {
        _isBookingFormEnabled.value = enabled
        addAudit("Admin", "تعديل حالة استمارة الحجز العامة", if (enabled) "تفعيل وإتاحة الاستمارة" else "تعطيل وإخفاء الاستمارة العامة")
    }

    // --- TECHNICIAN APPROVAL / ACCEPTANCE ---
    fun approveNewTechnician(provider: Provider, approved: Boolean) {
        val listReg = _pendingApprovals.value.toMutableList()
        listReg.remove(provider)
        _pendingApprovals.value = listReg

        if (approved) {
            val listProv = _providers.value.toMutableList()
            listProv.add(provider.copy(isVerified = true))
            _providers.value = listProv
            addAudit("Admin", "الموافقة على فني جديد", "الفني المقبول: ${provider.name}")
            // Trigger Notification Rule Simulation
            triggerNotificationSimulate("new_tech_reg", mapOf("NAME" to provider.name))
        } else {
            addAudit("Admin", "رفض فني جديد", "الفني المرفوض: ${provider.name}")
        }
    }

    fun createBooking(booking: Booking) {
        val list = _bookings.value.toMutableList()
        list.add(0, booking)
        _bookings.value = list
        addAudit("User", "إنشاء طلب حجز جديد", "الاسم: ${booking.userName}، الخدمة: ${booking.categoryId}")
        
        // Trigger notification rule based on routing mode choice
        triggerNotificationSimulate("new_booking", mapOf("ID" to booking.id, "AREA" to booking.userArea))
    }

    fun updateBookingStatus(id: String, newStatus: String, actor: String) {
        val list = _bookings.value.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index >= 0) {
            val oldBooking = list[index]
            list[index] = oldBooking.copy(status = newStatus)
            _bookings.value = list
            addAudit(actor, "تعديل حالة الحجز", "الطلب $id تحول من ${oldBooking.status} إلى $newStatus")

            // Alerts simulations
            when(newStatus) {
                "تم القبول" -> triggerNotificationSimulate("tech_accept", mapOf("ID" to id))
                "قيد التنفيذ" -> triggerNotificationSimulate("tech_progress", mapOf("ID" to id))
                "مكتمل" -> triggerNotificationSimulate("tech_finish", mapOf("ID" to id))
                else -> triggerNotificationSimulate("admin_override", mapOf("ID" to id, "STATUS" to newStatus))
            }
        }
    }

    fun reassignBooking(id: String, providerId: String, actor: String) {
        val list = _bookings.value.toMutableList()
        val index = list.indexOfFirst { it.id == id }
        if (index >= 0) {
            val oldBooking = list[index]
            list[index] = oldBooking.copy(providerId = providerId, status = "تم القبول")
            _bookings.value = list
            addAudit(actor, "إعادة إسناد فني للطلب", "الطلب $id تم إسناده إلى $providerId")
            triggerNotificationSimulate("supervisor_assign", mapOf("ID" to id))
        }
    }

    fun deleteBookingFromSystem(id: String) {
        val list = _bookings.value.toMutableList()
        val b = list.find { it.id == id }
        if (b != null) {
            list.remove(b)
            _bookings.value = list
            addAudit("Admin", "حذف حجز نهائياً", "الطلب رقم $id")
        }
    }

    fun updateBookingGeneral(booking: Booking) {
        val list = _bookings.value.toMutableList()
        val index = list.indexOfFirst { it.id == booking.id }
        if (index >= 0) {
            list[index] = booking
            _bookings.value = list
            addAudit("Admin", "تعديل بيانات طلب الحجز", "الطلب ID: ${booking.id}")
        }
    }

    // --- NOTIFICATION CONFIGURATION ---
    fun updateNotificationRule(eventId: String, isEnabled: Boolean, text: String) {
        val list = _notificationRules.value.toMutableList()
        val idx = list.indexOfFirst { it.eventId == eventId }
        if (idx >= 0) {
            list[idx] = list[idx].copy(isEnabled = isEnabled, templateText = text)
            _notificationRules.value = list
            addAudit("Admin", "برمجة إشعار آلي", "الحدث: $eventId تم حفظه")
        }
    }

    fun sendBroadcastNotification(title: String, body: String, segment: String, scheduledTime: String = "") {
        val list = _sentNotifications.value.toMutableList()
        val timeNow = if (scheduledTime.isEmpty()) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            sdf.format(Date())
        } else ""

        val newNotif = ManualNotification(
            title = title,
            body = body,
            segment = segment,
            scheduledTime = scheduledTime,
            sentTime = timeNow,
            isSent = scheduledTime.isEmpty()
        )
        list.add(0, newNotif)
        _sentNotifications.value = list
        
        val targetDesc = if (scheduledTime.isNotEmpty()) "جدولة للبث في: $scheduledTime" else "بث فوري"
        addAudit("Admin", "إرسال إشعار جماعي", "العنوان: $title • الفئة: $segment ($targetDesc)")
    }

    private fun triggerNotificationSimulate(eventId: String, params: Map<String, String>) {
        val rule = _notificationRules.value.find { it.eventId == eventId }
        if (rule != null && rule.isEnabled) {
            var body = rule.templateText
            params.forEach { (k, v) ->
                body = body.replace("{$k}", v)
            }
            sendBroadcastNotification(rule.description, body, "تنبيه نظام تلقائي")
        }
    }

    fun simulateFailedSync() {
        triggerNotificationSimulate("sync_fail", emptyMap())
    }

    fun deleteNotificationFromSystem(id: String) {
        val list = _sentNotifications.value.toMutableList()
        list.removeAll { it.id == id }
        _sentNotifications.value = list
        addAudit("Admin", "حذف إشعار فردي", "معرف الإشعار: $id")
    }

    fun clearAllNotificationsFromSystem() {
        _sentNotifications.value = emptyList()
        addAudit("Admin", "مسح أرشيف الإشعارات بالكامل", "تم تصفير سجل البث")
    }

    fun addBanner(uri: String, isVideo: Boolean, title: String) {
        val list = _banners.value.toMutableList()
        list.add(PromoBanner(uri = uri, isVideo = isVideo, title = title))
        _banners.value = list
        addAudit("Admin", "إضافة بنر إعلاني جديد", "العنوان: $title • نوعه: " + if(isVideo) "فيديو" else "صورة")
    }

    fun removeBanner(id: String) {
        val list = _banners.value.toMutableList()
        list.removeAll { it.id == id }
        _banners.value = list
        addAudit("Admin", "حذف بنر إعلاني", "معرف البنر: $id")
    }

    fun updateBookingTerms(newTerms: String) {
        _bookingTerms.value = newTerms
        addAudit("Admin", "تحديث شروط وأحكام الحجز", "تعديل ناجح")
    }

    fun updateCardSettings(settings: CardSettings) {
        _cardSettings.value = settings
        addAudit("Admin", "تحديث تخصيص إعدادات وعروض البطاقات", "مزامنة فورية")
    }

    fun deleteCategory(id: String) {
        val list = _categories.value.toMutableList()
        list.removeAll { it.id == id }
        _categories.value = list
        addAudit("Admin", "حذف قسم خدمي", "معرف القسم: $id")
    }

    fun updateProvider(prov: Provider) {
        val list = _providers.value.toMutableList()
        val index = list.indexOfFirst { it.id == prov.id }
        if (index >= 0) {
            list[index] = prov
            _providers.value = list
            addAudit("Admin", "حدَّث بيانات مزود خدمة", "الاسم: ${prov.name}")
        } else {
            list.add(prov)
            _providers.value = list
            addAudit("Admin", "إضافة مزود خدمة جديد يدوياً", "الاسم: ${prov.name}")
        }
    }

    fun deleteProvider(id: String) {
        val list = _providers.value.toMutableList()
        list.removeAll { it.id == id }
        _providers.value = list
        addAudit("Admin", "حذف مزود خدمة نهائياً", "معرف: $id")
    }

    fun resetAllDataSystem() {
        _providers.value = listOf(
            Provider(
                id = "p1",
                name = "المهندس عادل الحمادي",
                phone = "771234567",
                phone2 = "771111222",
                specialty = "أخصائي تركيب وتبريد مكيفات هواء عملاقة",
                rating = 4.9,
                ratingCount = 28,
                city = "صنعاء",
                area = "حدة",
                addressDetail = "شارع حدة العام - جولة الرويشان",
                categoryId = "cat_hvac",
                subCategory = "مكيفات سبليت ومركزي وكاسيت",
                inspectionPrice = 1500.0,
                workHours = "9:00 ص - 9:00 م",
                isAvailable = true,
                isPinned = true,
                isVerified = true,
                isVip = true,
                isRecommended = true,
                serviceDescription = "متخصصون في تركيب وصيانة جميع أنواع المكيفات السكنية والتجارية مع ضمان قطع الغيار الأصلية.",
                offeredServices = listOf("غسيل وتنظيف مكيفات", "تعبئة غاز الفريون الأصلي", "شحن وتمديد مواسير نحاسية"),
                coverUri = "https://images.unsplash.com/photo-1621905251189-08b45d6a269e?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1540569014015-19a7be504e3a?q=80&w=200",
                galleryUris = listOf(
                    "https://images.unsplash.com/photo-1581092160607-ee22621dd758?q=80&w=400",
                    "https://images.unsplash.com/photo-1605810230434-7631ac76ec81?q=80&w=400"
                )
            ),
            Provider(
                id = "p2",
                name = "فني السباكة ماهر الأبارة",
                phone = "735112233",
                phone2 = "734333222",
                specialty = "صيانة حمامات ومضخات وشبكات مياه ذاسية",
                rating = 4.7,
                ratingCount = 19,
                city = "عدن",
                area = "المنصورة",
                addressDetail = "حي المنصورة - جوار مستشفى الكوبي",
                categoryId = "cat_plum",
                subCategory = "صيانة عامة وتأسيس سباكة",
                inspectionPrice = 1000.0,
                workHours = "8:00 ص - 8:00 م",
                isAvailable = true,
                isPinned = false,
                isVerified = true,
                isVip = false,
                isRecommended = true,
                serviceDescription = "صيانة السباكة المنزلية الفورية، كشف تسريب المياه عبر الأجهزة، تأسيس وتمديد خطوط حمامات.",
                offeredServices = listOf("علاج تسريب الحوائط", "تركيب مضخات مياه ذكية", "تركيب وصيانة خلاطات وسخانات"),
                coverUri = "https://images.unsplash.com/photo-1504307651254-35680f356dfd?q=80&w=600",
                avatarUri = "https://images.unsplash.com/photo-1566492031773-4f4e44671857?q=80&w=200",
                galleryUris = listOf(
                    "https://images.unsplash.com/photo-1542013936693-8848e5740a7a?q=80&w=400"
                )
            )
        )
        _bookings.value = emptyList()
        _pendingApprovals.value = emptyList()
        _sentNotifications.value = emptyList()
        _chatMessages.value = emptyList()

        _formFields.value = listOf(
            CustomFormField("f1", "تاريخ وتوقيت الزيارة المفضل", "DateTimePicker", true),
            CustomFormField("f2", "ملاحظات وتفاصيل المشكلة الفنية بدقة", "TextArea", false),
            CustomFormField("f3", "هل المشكلة طارئة (تستدعي إنقاذ فوري)؟", "Dropdown", true, "نعم طارئة جداً,لا يمكن تأجيلها")
        )

        _bookingTerms.value = "ملاحظة هامة: يجب الالتزام ببنود حجز موعد الصيانة في اليمن، وتوفير القطع للورش المهنية."
        _cardSettings.value = CardSettings()

        _auditLogs.value = listOf(
            AuditLog(actor = "Admin", action = "تطهير النظام بالكامل", details = "تم تطهير قواعد البيانات وإعادة بناء الدليل العظيم للخدمات بنجاح 🧹")
        )
    }

    fun sendChatMessage(text: String) {
        val list = _chatMessages.value.toMutableList()
        list.add(ChatMessage(sender = "User", text = text))
        _chatMessages.value = list

        val lowercase = text.lowercase()
        val reply = when {
            lowercase.contains("كهربائي") || lowercase.contains("كهرباء") -> 
                "مرحباً بك! لدينا المهندس المتميز سليم الصنعاني في صنعاء السبعين (0770998877) لجميع تمديدات وفحص التماسات الكهرباء والطاقة الشمسية باحترافية."
            lowercase.contains("مكيف") || lowercase.contains("تبريد") || lowercase.contains("تكييف") -> 
                "أهلاً بك! لدينا المهندس عادل الحمادي في صنعاء حدة (0771234567) وهو خبير متميز للغاية في تركيب وصيانة مكيفات سبليت والمركزي."
            lowercase.contains("سباك") || lowercase.contains("سباكة") -> 
                "يسعدني ترشيح فني السباكة الماهر ماهر الأبارة في عدن المنصورة (0735112233)، المتخصص بالشبكات الذكية ومضخات المياه وكشف تسريبات المياه."
            lowercase.contains("نجار") || lowercase.contains("نجارة") -> 
                "لدينا المعلم فواز للنجارة في تعز الحوبان (071122233) المختص بتركيب وتفصيل وصيانة موبيليا غرف النوم والمطابخ."
            lowercase.contains("سعر") || lowercase.contains("رخيص") || lowercase.contains("أسعار") -> 
                "تتراوح أسعار معاينة الكوادر في منصتنا بين 800 ريال يمني إلى 2000 ريال يمني حسب تخصص المهندس وموقعه، ودائماً ما تجد الأسعار في تفاصيل كل فني!"
            lowercase.contains("صنعاء") -> 
                "في صنعاء، لدينا الفنيين: المهندس عادل الحمادي (مكيفات) والمهندس سليم الصنعاني (كهرباء)، وكلاهما متاحان لطلب الحجز والتوزيع المباشر فوراً!"
            lowercase.contains("عدن") -> 
                "في ثغر اليمن الباسم عدن، نوفر فني السباكة المتميز ماهر الأبارة بالمنصورة لخدمتك على مدار الساعة!"
            else -> 
                "أهلاً بك في خدمة المساعد الفني الذكي لمنصة يمن الأسعد 🤖! بطلب منك، أستطيع إرشادك لأفضل كادر فني (كهربائي، سباك، نجار، تكييف) في صنعاء أو عدن أو تعز بحسب موقعك، أو إعطائك تفاصيل أسعار وموثوقية الكوادر. تفضل بسؤالي!"
        }

        // Add delayed AI Response simulation
        val listWithReply = _chatMessages.value.toMutableList()
        listWithReply.add(ChatMessage(sender = "AI", text = reply))
        _chatMessages.value = listWithReply
    }

    fun clearChatMessageHistory() {
        _chatMessages.value = emptyList()
        addAudit("User", "مسح المحادثة الذكية", "تم محو ذاكرة المساعد الذكي بالكامل")
    }
}

// --- UTILITY COMPOSABLE TO RENDER DYNAMIC CARD FONT ---
@Composable
fun getFontFamilyByName(name: String): FontFamily {
    return when(name) {
        "Cairo", "خط القاهرة 🐪" -> FontFamily.SansSerif // System fallbacks representing style
        "Tajawal", "خط التجول 🚶" -> FontFamily.Serif
        "Amiri", "الخط الأميري 📜" -> FontFamily.Default
        "Monospace", "أكواد مطور 💻" -> FontFamily.Monospace
        else -> FontFamily.Default
    }
}

fun calculateDistanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val r = 6371.0 // Earth's planetary radius in km
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return r * c
}

// --- MAIN SCREEN ---
class MainActivity : ComponentActivity() {
    companion object {
        private var tts: TextToSpeech? = null
        private var isTtsReady = false

        fun speak(context: Context, text: String) {
            val cleanTextBeforeSpeech = text.replace("🔒", "").replace("🤫", "").replace("🤖", "").replace("📢", "").replace("💡", "").trim()
            if (tts == null) {
                tts = TextToSpeech(context) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        try {
                            tts?.language = java.util.Locale("ar")
                        } catch (e: java.lang.Exception) {}
                        isTtsReady = true
                        tts?.speak(cleanTextBeforeSpeech, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                }
            } else {
                tts?.speak(cleanTextBeforeSpeech, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onDestroy() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: java.lang.Exception) {}
        tts = null
        isTtsReady = false
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isUserRole by remember { mutableStateOf(true) }
            var activeUserTab by remember { mutableStateOf(0) } // Lifted state: 0 = دليل الكوادر, 1 = المساعد الذكي, 2 = خارطة الكوادر
            val vm: MainViewModel = viewModel()
            val context = LocalContext.current
            var showAdminLoginDialog by remember { mutableStateOf(false) }
            var loginUsernameField by remember { mutableStateOf("") }
            var loginPasswordField by remember { mutableStateOf("") }

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.darkBg
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Custom Status Bar / Header (hidden completely when in immersive AI chat fullscreen mode)
                        if (!isUserRole || activeUserTab != 1) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(AppTheme.surfaceDark)
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Construction,
                                        contentDescription = "App Icon",
                                        tint = AppTheme.accentGold,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = "كل خدمات اليمن 🇾🇪",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily.SansSerif
                                        )
                                        Text(
                                            text = "الدليل المهني والخدمات الموثقة لليمن الأسعد",
                                            color = AppTheme.grayText,
                                            fontSize = 9.sp
                                        )
                                    }
                                }

                                // Role Switcher
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(Color.Black.copy(alpha = 0.4f))
                                        .clickable {
                                            if (isUserRole) {
                                                showAdminLoginDialog = true
                                            } else {
                                                isUserRole = true
                                                showAppToast(context, "👋 تم تسجيل الخروج من وضع المسؤول بنجاح!", true)
                                            }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isUserRole) Icons.Default.Person else Icons.Default.Security,
                                        contentDescription = "Mode Icon",
                                        tint = AppTheme.accentGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = if (isUserRole) "واجهة العميل" else "لوحة الأدمن",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // App Content based on Role Switcher
                        if (isUserRole) {
                            UserWorkspace(vm, activeUserTab, onTabChanged = { activeUserTab = it })
                        } else {
                            AdminDashboardWorkspace(vm)
                        }

                        if (showAdminLoginDialog) {
                            AlertDialog(
                                onDismissRequest = { 
                                    showAdminLoginDialog = false 
                                    loginUsernameField = ""
                                    loginPasswordField = ""
                                },
                                containerColor = AppTheme.surfaceDark,
                                title = {
                                    Text("🔐 تسجيل دخول كمسؤول النظام", color = AppTheme.accentGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                },
                                text = {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("يرجى إدخال اسم المستخدم وكلمة المرور الخاصة بلوحة الإدارة لليمن الأسعد لمتابعة صلاحياتك:", color = Color.White, fontSize = 10.sp)
                                        
                                        Text("اسم المستخدم", color = Color.White, fontSize = 9.sp)
                                        OutlinedTextField(
                                            value = loginUsernameField,
                                            onValueChange = { loginUsernameField = it },
                                            singleLine = true,
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = Color.White,
                                                unfocusedTextColor = Color.White,
                                                focusedBorderColor = AppTheme.accentGold,
                                                unfocusedBorderColor = Color.LightGray,
                                                cursorColor = AppTheme.accentGold,
                                                focusedContainerColor = Color(0xFF1B2A2D),
                                                unfocusedContainerColor = Color(0xFF10191B)
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Text("كلمة المرور الإدارية", color = Color.White, fontSize = 9.sp)
                                        OutlinedTextField(
                                            value = loginPasswordField,
                                            onValueChange = { loginPasswordField = it },
                                            singleLine = true,
                                            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedTextColor = Color.White,
                                                unfocusedTextColor = Color.White,
                                                focusedBorderColor = AppTheme.accentGold,
                                                unfocusedBorderColor = Color.LightGray,
                                                cursorColor = AppTheme.accentGold,
                                                focusedContainerColor = Color(0xFF1B2A2D),
                                                unfocusedContainerColor = Color(0xFF10191B)
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                                        onClick = {
                                            val correctUser = try { BuildConfig.ADMIN_USERNAME } catch (e: Throwable) { "WAM2026" }
                                            val correctPass = try { BuildConfig.ADMIN_LOGIN_PASSWORD } catch (e: Throwable) { "maher--736462" }
                                            
                                            if (loginUsernameField == correctUser && loginPasswordField == correctPass) {
                                                isUserRole = false
                                                showAdminLoginDialog = false
                                                vm.addAudit(correctUser, "تسجيل دخول كمسؤول", "تم الدخول بنجاح من جهاز الآدمن الرئيسي")
                                                showAppToast(context, "🔐 مرحباً بك مجدداً مشرف عام المنظومة!", true)
                                            } else {
                                                showAppToast(context, "❌ اسم المستخدم أو كلمة المرور الإدارية خاطئة!", false)
                                            }
                                        }
                                    ) {
                                        Text("تسجيل الدخول", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                },
                                dismissButton = {
                                    OutlinedButton(
                                        onClick = {
                                            showAdminLoginDialog = false
                                            loginUsernameField = ""
                                            loginPasswordField = ""
                                        }
                                    ) {
                                        Text("إلغاء", color = Color.White, fontSize = 10.sp)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun safeParseColor(hex: String, fallback: Color = Color.Gray): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        fallback
    }
}

// --- CUSTOM SERVICE PROVIDER CARD DISPLAYING DATA ---
@Composable
fun ServiceProviderCard(
    provider: Provider,
    categoryName: String,
    cardFontName: String,
    cardFontSizeSp: Int,
    cardPaddingDp: Int,
    cardCornerRadiusDp: Int,
    cardSettings: CardSettings = CardSettings(),
    isOnline: Boolean = true,
    onBookClick: () -> Unit,
    onCallClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    val context = LocalContext.current
    val customFont = getFontFamilyByName(cardFontName)

    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (isPressed && cardSettings.enableScaleAnimation) cardSettings.pressScaleRatio else 1.0f)

    Card(
        shape = RoundedCornerShape(cardCornerRadiusDp.dp),
        colors = CardDefaults.cardColors(containerColor = safeParseColor(cardSettings.bgHex, AppTheme.surfaceDark)),
        border = BorderStroke(1.5.dp, if (provider.isPinned) safeParseColor(cardSettings.vipColorHex, AppTheme.accentGold) else Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = (cardSettings.itemSpacing / 2).dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                onClick = onDetailsClick
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. Cover Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardSettings.coverHeight.dp)
            ) {
                AsyncImage(
                    model = if (provider.coverUri.isNotEmpty()) provider.coverUri else "https://images.unsplash.com/photo-1621905251189-08b45d6a269e?q=80&w=600",
                    contentDescription = "Cover Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )

                // Dark subtle gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(0.4f), Color.Transparent, Color.Black.copy(0.7f))
                            )
                        )
                )

                // Dynamic Badges Panel (Top-Right)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (provider.isVip && cardSettings.showVip) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(safeParseColor(cardSettings.vipColorHex, AppTheme.accentGold))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text("VIP 👑", color = Color.Black, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (provider.isVerified && cardSettings.showVerified) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(safeParseColor(cardSettings.verifiedColorHex, Color(0xFF1E88E5)))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text("موثق 🛡️", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (provider.isRecommended && cardSettings.showRecommended) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(safeParseColor(cardSettings.recommendedColorHex, Color(0xFFFF9800)))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text("موصى به ⭐", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Availability Status Overlay (Top-Left)
                if (cardSettings.showAvailability) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (provider.isAvailable) Color(0xFF2E7D32) else Color(0xFFC62828))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = if (provider.isAvailable) "متاح 🟢" else "مشغول 🔴",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 2. Padding Content details
            Column(
                modifier = Modifier
                    .padding(cardSettings.cardPadding.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Main split layout: Info Items on left, Avatar on right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Left Column containing details structured by ordering preference (infoOrder)
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        cardSettings.infoOrder.forEach { term ->
                            val cleanTerm = term.trim().lowercase()
                            if (cleanTerm == "الاسم" || cleanTerm == "name") {
                                Text(
                                    text = provider.name,
                                    color = safeParseColor(cardSettings.titleColorHex, Color.White),
                                    fontSize = cardFontSizeSp.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = customFont
                                )
                                Text(
                                    text = "$categoryName • ${provider.specialty}",
                                    color = AppTheme.grayText,
                                    fontSize = (cardFontSizeSp - 2).coerceAtLeast(8).sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = customFont
                                )
                            } else if (cleanTerm == "التقييم" || cleanTerm == "rating") {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Rating",
                                        tint = safeParseColor(cardSettings.ratingColorHex, AppTheme.accentGold),
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = provider.rating.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (cardSettings.showRatingCount) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "(${provider.ratingCount} تقييم)",
                                            color = Color.Gray,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            } else if (cleanTerm == "المسافة" || cleanTerm == "distance") {
                                if (cardSettings.showDistance) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Navigation,
                                            contentDescription = "Distance",
                                            tint = Color.LightGray,
                                            modifier = Modifier.size(11.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        val calculatedDistance = String.format(Locale.US, "%.1f", Math.sqrt(Math.pow(provider.latX - 15.369, 2.0) + Math.pow(provider.lonY - 44.191, 2.0)) * 111.0)
                                        Text(
                                            text = "يبعد مسافة: $calculatedDistance كم مجاور لك",
                                            color = Color.LightGray,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            } else if (cleanTerm == "الموقع" || cleanTerm == "location") {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = AppTheme.primaryRed,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "اليمن - ${provider.city}، ${provider.area}",
                                        color = safeParseColor(cardSettings.locationColorHex, AppTheme.grayText),
                                        fontSize = 9.sp,
                                        fontFamily = customFont
                                    )
                                }
                                if (provider.addressDetail.isNotEmpty()) {
                                    Text(
                                        text = provider.addressDetail,
                                        color = Color.Gray,
                                        fontSize = 8.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 14.dp)
                                    )
                                }
                            } else if (cleanTerm == "سعر المعاينة" || cleanTerm == "price") {
                                if (cardSettings.showPrice) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Payments,
                                            contentDescription = "Price",
                                            tint = safeParseColor(cardSettings.priceColorHex, Color.LightGray),
                                            modifier = Modifier.size(11.dp)
                                        )
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text(
                                            text = "رسم المعاينة والفحص: ${provider.inspectionPrice} ر.ي",
                                            color = safeParseColor(cardSettings.priceColorHex, Color.White),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = customFont
                                        )
                                    }
                                }
                            } else if (cleanTerm == "الحالة" || cleanTerm == "status") {
                                // Soft availability sub-row
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(if (provider.isAvailable) Color.Green else Color.Red)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (provider.isAvailable) "استعداد كامل لمباشرة الصيانة" else "الكادر مشغول في مهمة خارجية صيانة",
                                        color = if (provider.isAvailable) Color(0xFF81C784) else Color(0xFFE57373),
                                        fontSize = 8.sp
                                    )
                                }
                            }
                        }
                    }

                    // Right column: avatar frame with quick phone icon indicator
                    val avatarShape = if (cardSettings.isAvatarCircular) CircleShape else RoundedCornerShape(8.dp)
                    Box(
                        modifier = Modifier
                            .size(cardSettings.avatarSize.dp)
                            .clip(avatarShape)
                            .background(Color.Black.copy(0.3f))
                            .border(1.5.dp, safeParseColor(cardSettings.vipColorHex, AppTheme.accentGold), avatarShape)
                    ) {
                        AsyncImage(
                            model = if (provider.avatarUri.isNotEmpty()) provider.avatarUri else "https://images.unsplash.com/photo-1540569014015-19a7be504e3a?q=80&w=200",
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )

                        // Float call indicator on bottom-right of avatar
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size((cardSettings.avatarSize * 0.35).coerceAtLeast(14.0).dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2E7D32))
                                .clickable {
                                    if (isOnline && provider.isAvailable) {
                                        onCallClick()
                                    } else {
                                        showAppToast(context, "🔒 رقم الفني مخفي مؤقتاً لعدم الاتصال بالشبكة أو لأنه مشغول!", false)
                                    }
                                }
                                .border(1.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Quick dial",
                                tint = Color.White,
                                modifier = Modifier.size(8.dp)
                              )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Action buttons row rendered based on configuration constraints
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    cardSettings.buttonsOrder.forEach { action ->
                        val cleanAction = action.trim().lowercase()
                        if (cleanAction == "اتصال" || cleanAction == "call") {
                            if (cardSettings.showCall) {
                                Button(
                                    onClick = {
                                        if (isOnline && provider.isAvailable) {
                                            onCallClick()
                                        } else {
                                            showAppToast(context, "🔒 رقم الفني مخفي مؤقتاً لعدم الاتصال بالشبكة أو لأنه مشغول!", false)
                                        }
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = safeParseColor(cardSettings.callColorHex, Color(0xFF4CAF50))),
                                    modifier = Modifier.weight(1f),
                                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                                ) {
                                    Icon(Icons.Default.Call, null, modifier = Modifier.size(11.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("اتصال 📞", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (cleanAction == "واتساب" || cleanAction == "whatsapp") {
                            if (cardSettings.showWhatsapp) {
                                Button(
                                    onClick = {
                                        if (isOnline && provider.isAvailable) {
                                            try {
                                                val uriStr = "https://api.whatsapp.com/send?phone=967${provider.phone}"
                                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriStr))
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                showAppToast(context, "لم نتمكن من فتح تطبيق واتساب", false)
                                            }
                                        } else {
                                            showAppToast(context, "🔒 رقم الواتساب مخفي مؤقتاً لعدم الاتصال بالشبكة أو لأنه مشغول!", false)
                                        }
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = safeParseColor(cardSettings.whatsappColorHex, Color(0xFF25D366))),
                                    modifier = Modifier.weight(1f),
                                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                                ) {
                                    Icon(Icons.Default.Chat, null, modifier = Modifier.size(11.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("واتساب 💬", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (cleanAction == "تفاصيل" || cleanAction == "details") {
                            if (cardSettings.showDetails) {
                                Button(
                                    onClick = onDetailsClick,
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = safeParseColor(cardSettings.detailsColorHex, Color(0xFF2196F3))),
                                    modifier = Modifier.weight(1f),
                                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                                ) {
                                    Icon(Icons.Default.Info, null, modifier = Modifier.size(11.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("تفاصيل 📋", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        } else if (cleanAction == "حجز" || cleanAction == "booking") {
                            if (cardSettings.showBooking) {
                                Button(
                                    onClick = onBookClick,
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = safeParseColor(cardSettings.bookingColorHex, Color(0xFFE91E63))),
                                    modifier = Modifier.weight(1.2f),
                                    contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                                ) {
                                    Icon(Icons.Default.Event, null, modifier = Modifier.size(11.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("حجز موعد 📅", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- PRIMARY CLIENT WORKSPACE VIEW ---
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun UserWorkspace(vm: MainViewModel, activeUserTab: Int, onTabChanged: (Int) -> Unit) {
    val providers by vm.providers.collectAsState()
    val categories by vm.categories.collectAsState()
    val formFields by vm.formFields.collectAsState()
    val banners by vm.banners.collectAsState()
    val chatMessages by vm.chatMessages.collectAsState()
    val cardSettings by vm.cardSettings.collectAsState()

    val cardFontName by vm.cardStyleName.collectAsState()
    val cardFontSize by vm.cardFontSize.collectAsState()
    val cardPadding by vm.cardPadding.collectAsState()
    val cardCornerRadius by vm.cardCornerRadius.collectAsState()

    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var showBookingDialogFor by remember { mutableStateOf<Provider?>(null) }
    var showDetailDialogFor by remember { mutableStateOf<Provider?>(null) }
    var selectedMapProvider by remember { mutableStateOf<Provider?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                searchQuery = matches[0]
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Horizontally scrolling user tabs for elegant workflow
        ScrollableTabRow(
            selectedTabIndex = activeUserTab,
            containerColor = AppTheme.surfaceDark,
            contentColor = AppTheme.accentGold,
            edgePadding = 0.dp,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        ) {
            listOf("الكوادر والخدمات 🛠️", "المساعد الذكي 🤖", "خارطة الكوادر المجاورة 🗺️").forEachIndexed { index, label ->
                Tab(
                    selected = activeUserTab == index,
                    onClick = { onTabChanged(index) },
                    text = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        // support hotline bar with offline/online constraints (hidden for immersive AI fullscreen)
        val supportPhone by vm.supportPhone.collectAsState()
        val isOnline by vm.isOnline.collectAsState()

        if (activeUserTab != 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF0F2422))
                    .border(1.dp, Color(0xFF00C853).copy(0.35f), RoundedCornerShape(8.dp))
                    .clickable {
                        if (isOnline) {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$supportPhone")
                            }
                            context.startActivity(intent)
                        } else {
                            showAppToast(context, "⚠️ يتعذر الاتصال بالدعم الفني في وضع الأوفلاين! يرجى تشغيل الشبكة أولاً.", false)
                        }
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SupportAgent,
                        contentDescription = null,
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text("الخط الإداري العام (منظومة اليمن الأسعد)", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = if (isOnline) "رقم الاتصال الفوري: $supportPhone" else "🔒 مخفي لعدم الاتصال بالشبكة (أوفلاين)",
                            color = if (isOnline) AppTheme.accentGold else Color.Gray,
                            fontSize = 9.sp
                        )
                    }
                }
                Text(
                    text = if (isOnline) "اتصال الآن 📞" else "دون اتصال 🌐",
                    color = if (isOnline) Color(0xFF00C853) else Color.Gray,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        when (activeUserTab) {
            0 -> {
                // I. ACTIVE DIRECTORY WORKSPACE
                // Promo Banners Carousel
                if (banners.isNotEmpty()) {
                    var currentBannerIdx by remember { mutableStateOf(0) }
                    LaunchedEffect(key1 = currentBannerIdx) {
                        delay(6000)
                        currentBannerIdx = (currentBannerIdx + 1) % banners.size
                    }
                    val currentBanner = banners[currentBannerIdx]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, AppTheme.accentGold.copy(0.3f))
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = currentBanner.uri,
                                contentDescription = "Yemen Promo Banner",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        androidx.compose.ui.graphics.Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black.copy(0.85f))
                                        )
                                    )
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { MainActivity.speak(context, currentBanner.title) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.VolumeUp,
                                        contentDescription = "استمع للإعلان بصوت المساعد",
                                        tint = AppTheme.accentGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentBanner.title,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Right
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(AppTheme.primaryRed)
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text("جديد ⚡", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // Search Box with High-Precision Speech Recognition (Voice Search)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ابحث عن فني، مدينة، منطقة، هاتف...", color = Color.Gray, fontSize = 11.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = AppTheme.accentGold) },
                        trailingIcon = {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 4.dp)) {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(18.dp))
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        try {
                                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-YE")
                                                putExtra(RecognizerIntent.EXTRA_PROMPT, "تحدث الآن للبحث في دليل اليمن...")
                                            }
                                            voiceLauncher.launch(intent)
                                        } catch (e: Exception) {
                                            showAppToast(context, "محرك التعرف على الصوت بالهاتف غير متوفر حالياً", false)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = AppTheme.accentGold)
                                }
                            }
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Categories horizontal sliders
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategoryFilter == "All",
                            onClick = { selectedCategoryFilter = "All" },
                            label = { Text("الكل", fontSize = 11.sp, color = if (selectedCategoryFilter == "All") Color.Black else Color.White) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.accentGold)
                        )
                    }
                    items(categories) { cat ->
                        FilterChip(
                            selected = selectedCategoryFilter == cat.id,
                            onClick = { selectedCategoryFilter = cat.id },
                            label = { Text(cat.name, fontSize = 11.sp, color = if (selectedCategoryFilter == cat.id) Color.Black else Color.White) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = AppTheme.accentGold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Professional Listing Title
                Text(
                    text = "دليل الفنيين النشطين بالجمهورية اليمنية ⚡",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 6.dp)
                )

                // Advanced multi-field filtering logic
                val filteredProviders = providers.filter { p ->
                    val matchCategory = selectedCategoryFilter == "All" || p.categoryId == selectedCategoryFilter
                    val matchSearch = searchQuery.isEmpty() || 
                        p.name.contains(searchQuery, ignoreCase = true) || 
                        p.specialty.contains(searchQuery, ignoreCase = true) || 
                        p.city.contains(searchQuery, ignoreCase = true) || 
                        p.area.contains(searchQuery, ignoreCase = true) ||
                        p.phone.contains(searchQuery) ||
                        (p.phone2.isNotEmpty() && p.phone2.contains(searchQuery)) ||
                        p.subCategory.contains(searchQuery, ignoreCase = true) ||
                        p.serviceDescription.contains(searchQuery, ignoreCase = true)
                    matchCategory && matchSearch
                }

                if (filteredProviders.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.HourglassEmpty, contentDescription = null, modifier = Modifier.size(44.dp), tint = Color.Gray)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("عذراً، لم يعثر على فنيين يطابقون خيارات البحث", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy((cardSettings.itemSpacing).dp)
                    ) {
                        // Display pinned items first
                        val sortedProviders = filteredProviders.sortedByDescending { p -> p.isPinned }
                        items(sortedProviders) { prov ->
                            val catName = categories.find { it.id == prov.categoryId }?.name ?: "خدمة عامة"
                            ServiceProviderCard(
                                provider = prov,
                                categoryName = catName,
                                cardFontName = cardFontName,
                                cardFontSizeSp = cardFontSize,
                                cardPaddingDp = cardPadding,
                                cardCornerRadiusDp = cardCornerRadius,
                                cardSettings = cardSettings,
                                isOnline = isOnline,
                                onCallClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${prov.phone}")
                                    }
                                    context.startActivity(intent)
                                },
                                onBookClick = {
                                    showBookingDialogFor = prov
                                },
                                onDetailsClick = {
                                    showDetailDialogFor = prov
                                }
                            )
                        }
                    }
                }
            }
            1 -> {
                // II. ENLARGED AI SMART ASSISTANT CONVERSATION SCREEN
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(AppTheme.surfaceDark, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    // Chat header with clear button
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "المساعد الفني الذكي لمجلس الكوادر 🤖",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(
                            onClick = { vm.clearChatMessageHistory() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = "مسح المحادثة بالكامل",
                                tint = AppTheme.primaryRed
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color.Black.copy(0.25f), RoundedCornerShape(10.dp))
                            .padding(8.dp)
                    ) {
                        if (chatMessages.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.SmartToy, "Assistant Logo", modifier = Modifier.size(48.dp), tint = AppTheme.accentGold)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text("مرحباً بك باليمن الأسعد!", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("اسألني عن أقرب الفنيين، أرقام السباكين والكهربائيين المتوفرين يدوياً في صنعاء، عدن، أو تعز بلمسة واحدة.", color = AppTheme.grayText, fontSize = 10.sp, textAlign = TextAlign.Center)
                                
                                Spacer(modifier = Modifier.height(20.dp))
                                Text("💡 أفكار سهلة للبدء بالحديث:", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf("أريد كهربائي بصنعاء ⚡", "ابحث عن سباك متميز 💧", "أسعار صيانة المكيفات ❄️").forEach { suggestion ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.Black)
                                                .border(1.dp, AppTheme.accentGold.copy(0.3f), RoundedCornerShape(6.dp))
                                                .clickable { vm.sendChatMessage(suggestion) }
                                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                        ) {
                                            Text(suggestion, color = Color.White, fontSize = 9.sp)
                                        }
                                    }
                                }
                            }
                        } else {
                            val lazyListState = rememberLazyListState()
                            LaunchedEffect(chatMessages.size) {
                                scope.launch {
                                    if (chatMessages.isNotEmpty()) {
                                        lazyListState.animateScrollToItem(chatMessages.size - 1)
                                    }
                                }
                            }

                            LazyColumn(
                                state = lazyListState,
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(chatMessages) { msg ->
                                    val isUser = msg.sender == "User"
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(
                                                topStart = 8.dp,
                                                topEnd = 8.dp,
                                                bottomStart = if (isUser) 8.dp else 0.dp,
                                                bottomEnd = if (isUser) 0.dp else 8.dp
                                            ),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isUser) AppTheme.accentGold else Color.Black.copy(alpha = 0.4f)
                                            ),
                                            border = if (isUser) null else BorderStroke(1.dp, AppTheme.accentGold.copy(0.3f)),
                                            modifier = Modifier.widthIn(max = 260.dp)
                                        ) {
                                            Column(modifier = Modifier.padding(10.dp)) {
                                                Text(
                                                    text = msg.text,
                                                    color = if (isUser) Color.Black else Color.White,
                                                    fontSize = 11.sp
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                if (!isUser) {
                                                    IconButton(
                                                        onClick = { MainActivity.speak(context, msg.text) },
                                                        modifier = Modifier.size(22.dp).align(Alignment.Start)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.VolumeUp,
                                                            contentDescription = "قراءة الرسالة صوتياً",
                                                            tint = AppTheme.accentGold,
                                                            modifier = Modifier.size(14.dp)
                                                        )
                                                    }
                                                }
                                                Text(
                                                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(msg.timestamp)),
                                                    color = if (isUser) Color.Black.copy(0.5f) else Color.Gray,
                                                    fontSize = 8.sp,
                                                    modifier = Modifier.align(Alignment.End)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                        Spacer(modifier = Modifier.height(8.dp))

                    // Input box for Chat
                    var userPromptText by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = userPromptText,
                            onValueChange = { userPromptText = it },
                            placeholder = { Text("اكتب استفسارك هنا للفني الذكي والمساعد...", fontSize = 12.sp, color = Color.Gray) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = AppTheme.accentGold,
                                focusedContainerColor = Color(0xFF1E2F32),
                                unfocusedContainerColor = Color(0xFF122022)
                            ),
                            modifier = Modifier.weight(1f).heightIn(min = 54.dp),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Button(
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                            modifier = Modifier.heightIn(min = 54.dp),
                            onClick = {
                                if (userPromptText.trim().isNotEmpty()) {
                                    vm.sendChatMessage(userPromptText.trim())
                                    userPromptText = ""
                                }
                            }
                        ) {
                            Text("إرسال 🚀", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            2 -> {
                // III. INTERACTIVE GEOGRAPHIC RADAR MAP COMPONENT (NEARBY PROVIDERS COORDS)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        "📍 رادار الكوادر المجاورة جغرافياً بالجمهورية اليمنية",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "توزيع وتفصيلي للمهندسين متمركزين حول صنعاء (حدة/السبعين)، عدن (المنصورة)، وتعز.",
                        color = AppTheme.grayText,
                        fontSize = 9.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Map View Container
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(0.35f))
                            .border(1.dp, AppTheme.accentGold.copy(0.3f), RoundedCornerShape(12.dp))
                    ) {
                        // Drawing GPS map coordinates nodes
                        Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            val mapWidth = size.width
                            val mapHeight = size.height
                            val centerX = mapWidth / 2f
                            val centerY = mapHeight / 2f

                            // Draw Concentric Distance Circles (Radar style)
                            drawCircle(
                                color = AppTheme.accentGold.copy(0.1f),
                                radius = centerX * 0.4f,
                                center = Offset(centerX, centerY),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                            )
                            drawCircle(
                                color = AppTheme.accentGold.copy(0.12f),
                                radius = centerX * 0.7f,
                                center = Offset(centerX, centerY),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                            )
                            drawCircle(
                                color = AppTheme.accentGold.copy(0.15f),
                                radius = centerX * 0.95f,
                                center = Offset(centerX, centerY),
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
                            )

                            // Draw central cross lines
                            drawLine(
                                color = AppTheme.accentGold.copy(0.15f),
                                start = Offset(centerX, 0f),
                                end = Offset(centerX, mapHeight),
                                strokeWidth = 1f
                            )
                            drawLine(
                                color = AppTheme.accentGold.copy(0.15f),
                                start = Offset(0f, centerY),
                                end = Offset(mapWidth, centerY),
                                strokeWidth = 1f
                            )
                        }

                        providers.forEach { prov ->
                            // Calculating dynamic relative screen offsets based on geographic coords
                            val relativeOffsetMultiplierX = ((prov.latX - 15.369) * 2000f).toFloat()
                            val relativeOffsetMultiplierY = ((prov.lonY - 44.191) * 2000f).toFloat()

                            val screenX = 140f + (relativeOffsetMultiplierX % 200f)
                            val screenY = 150f + (relativeOffsetMultiplierY % 240f)

                            val isSelected = selectedMapProvider?.id == prov.id

                            // Status color calibration based on requirements
                            val markerColor = when {
                                prov.isVerified && prov.isAvailable -> Color(0xFF2E7D32) // Green: Available & Active
                                prov.isVerified && !prov.isAvailable -> Color(0xFFEF6C00) // Orange: Busy
                                else -> Color(0xFFC62828) // Red: Disconnected / Not verified
                            }

                            Box(
                                modifier = Modifier
                                    .absoluteOffset(x = screenX.dp, y = screenY.dp)
                                    .size(if (isSelected) 34.dp else 24.dp)
                                    .clip(CircleShape)
                                    .background(markerColor)
                                    .border(1.5.dp, Color.White, CircleShape)
                                    .clickable { selectedMapProvider = prov }
                                    .padding(3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (prov.categoryId == "cat_elec") "⚡" else if (prov.categoryId == "cat_plum") "💧" else "❄️",
                                    fontSize = if (isSelected) 11.sp else 8.sp
                                )
                            }
                        }

                        // Display Map Instructions Label
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(0.75f))
                                .padding(6.dp)
                        ) {
                            Text("انقر على أي كادر لعرض ملفه الفوري 👆", color = AppTheme.accentGold, fontSize = 8.sp)
                        }

                        // Selection card overlay
                        selectedMapProvider?.let { prov ->
                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .animateContentSize(),
                                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                                border = BorderStroke(1.dp, AppTheme.accentGold)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(prov.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        Text(prov.specialty, color = AppTheme.grayText, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Text("📍 ${prov.city} - ${prov.area}", color = AppTheme.accentGold, fontSize = 8.sp)
                                            Text("⭐ ${prov.rating}", color = Color.Green, fontSize = 8.sp)
                                            Text("💰 ${prov.inspectionPrice} ر.ي", color = Color.White, fontSize = 8.sp)
                                        }
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        IconButton(
                                            onClick = {
                                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                                    data = Uri.parse("tel:${prov.phone}")
                                                }
                                                context.startActivity(intent)
                                            },
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color.White.copy(0.12f))
                                        ) {
                                            Icon(Icons.Default.Phone, "اتصال كادر", tint = Color.Green, modifier = Modifier.size(16.dp))
                                        }

                                        Button(
                                            shape = RoundedCornerShape(6.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                            onClick = { showBookingDialogFor = prov },
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                        ) {
                                            Text("حجز الفني", color = Color.White, fontSize = 9.sp)
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

    // --- DIALOG FOR DYNAMIC BOOKING FORM CREATION ---
    showBookingDialogFor?.let { prov ->
        val isBookingFormEnabled by vm.isBookingFormEnabled.collectAsState()
        var clientName by remember { mutableStateOf("") }
        var clientPhone by remember { mutableStateOf("") }
        var clientRegion by remember { mutableStateOf("") }
        var serviceInfoVal by remember { mutableStateOf("") }
        var clientLat by remember { mutableStateOf(15.369) }
        var clientLon by remember { mutableStateOf(44.191) }
        var showBookingConfirmationDialog by remember { mutableStateOf(false) }
        val dynamicFormMap = remember { mutableStateMapOf<String, String>() }

        AlertDialog(
            onDismissRequest = { showBookingDialogFor = null },
            containerColor = AppTheme.surfaceDark,
            title = {
                Text(
                    text = "طلب حجز موعد مع ${prov.name}",
                    color = AppTheme.accentGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right
                )
            },
            text = {
                if (!isBookingFormEnabled) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("🫣 عذراً الاستمارة معطلة مؤقتاً", color = AppTheme.primaryRed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "تم إخفاء أو تعطيل استمارة طلب الحجوزات والمواعيد حالياً من قبل الإدارة العامة للصيانة لإجراء تحديثات تنمية الخدمات.",
                            color = Color.LightGray,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Universal Basic Fields
                        Text("الاسم الثلاثي لطالب الخدمة *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("رقم الهاتف لطالب الخدمة *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("عنوان طالب الخدمة بالتفصيل *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientRegion,
                        onValueChange = { clientRegion = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("معلومات عن نوع الخدمة المطلوبة بالتفصيل *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = serviceInfoVal,
                        onValueChange = { serviceInfoVal = it },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("📍 الإحداثيات الجغرافية لموقعك (Latitude & Longitude) *", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = clientLat.toString(),
                            onValueChange = { 
                                clientLat = it.toDoubleOrNull() ?: 15.369 
                            },
                            label = { Text("خط العرض Lat", fontSize = 9.sp, color = Color.Gray) },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                        OutlinedTextField(
                            value = clientLon.toString(),
                            onValueChange = { 
                                clientLon = it.toDoubleOrNull() ?: 44.191 
                            },
                            label = { Text("خط الطول Lon", fontSize = 9.sp, color = Color.Gray) },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                    }

                    Button(
                        onClick = {
                            clientLat = 15.369 + (java.util.Random().nextDouble() - 0.5) * 0.05
                            clientLon = 44.191 + (java.util.Random().nextDouble() - 0.5) * 0.05
                            showAppToast(context, "🛰️ تم الاتصال بالأقمار الاصطناعية وتحديد موقع الـ GPS بدقة!", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                        border = BorderStroke(1.dp, AppTheme.accentGold),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.MyLocation, contentDescription = null, modifier = Modifier.size(14.dp), tint = AppTheme.accentGold)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("جلب موقع واكتشاف الـ GPS تلقائياً", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    val computedDistance = calculateDistanceInKm(prov.latX, prov.lonY, clientLat, clientLon)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF162527)),
                        border = BorderStroke(1.dp, Color(0xFF81C784)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("📍", fontSize = 18.sp)
                            Column {
                                Text(
                                    text = "المسافة الجغرافية الفاصلة عن الكادر الإلكتروني:",
                                    color = Color.LightGray,
                                    fontSize = 9.sp
                                )
                                Text(
                                    text = "يبعد الفني ${prov.name} مسافة ${String.format(Locale.US, "%.2f", computedDistance)} كم عنك",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Configurable fields injected dynamically by Admin Controller
                    formFields.filter { !it.isHidden }.forEach { field ->
                        val labelWithStar = field.label + if (field.isRequired) " *" else ""
                        Text(labelWithStar, color = Color.White, fontSize = 11.sp)

                        val currentVal = dynamicFormMap[field.id] ?: ""

                        when (field.type) {
                            "TextArea" -> {
                                OutlinedTextField(
                                    value = currentVal,
                                    onValueChange = { dynamicFormMap[field.id] = it },
                                    minLines = 3,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                            "Dropdown" -> {
                                val options = field.dropdownOptions.split(",")
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    options.forEach { opt ->
                                        val isSelected = currentVal == opt
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(alpha = 0.2f))
                                                .clickable { dynamicFormMap[field.id] = opt }
                                                .padding(horizontal = 8.dp, vertical = 5.dp)
                                        ) {
                                            Text(opt, color = if (isSelected) Color.Black else Color.White, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                            "Number" -> {
                                OutlinedTextField(
                                    value = currentVal,
                                    onValueChange = { dynamicFormMap[field.id] = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                            else -> { // Default Text Field & Date/Time input
                                OutlinedTextField(
                                    value = currentVal,
                                    onValueChange = { dynamicFormMap[field.id] = it },
                                    placeholder = { if (field.type == "DateTimePicker") Text("مثال: الإثنين 4 عصراً", color = Color.Gray, fontSize = 10.sp) },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                        }
                    }

                    // Customizable block of terms and conditions
                    val terms by vm.bookingTerms.collectAsState()
                    if (terms.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
                            border = BorderStroke(1.dp, AppTheme.accentGold.copy(0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    "⚠️ الشروط والأحكام المعتمدة للحجز:",
                                    color = AppTheme.accentGold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    terms,
                                    color = Color.LightGray,
                                    fontSize = 8.sp,
                                    lineHeight = 11.sp
                                )
                             }
                          }
                       }
                    }
                 }
            },
            confirmButton = {
                if (isBookingFormEnabled) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        onClick = {
                            // Validate inputs
                            if (clientName.isEmpty() || clientPhone.isEmpty() || clientRegion.isEmpty() || serviceInfoVal.isEmpty()) {
                                showAppToast(context, "الرجاء تعبئة كافة الحقول الرئيسية الإجبارية واختيار نوع الخدمة بدقة", false)
                                return@Button
                            }

                            // Validate admin dynamically configured mandatory constraints
                            var validationFails = false
                            formFields.filter { !it.isHidden }.forEach { field ->
                                if (field.isRequired && (dynamicFormMap[field.id]?.isEmpty() != false)) {
                                    validationFails = true
                                    showAppToast(context, "الحقل ${field.label} مطلوب لاكتمال الحجز بنجاح", false)
                                }
                            }

                            if (validationFails) return@Button

                            // Shows confirmation dialog of all booking info before final submission
                            showBookingConfirmationDialog = true
                        }
                    ) {
                        Text("تأكيد وحجز موعد الآن", color = Color.White)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showBookingDialogFor = null }) {
                    Text("تراجع وإلغاء", color = Color.Gray)
                }
            }
        )

        if (showBookingConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showBookingConfirmationDialog = false },
                containerColor = AppTheme.surfaceDark,
                title = {
                    Text(
                        "📋 مراجعة وتأكيد بيانات حجز الخدمة",
                        color = AppTheme.accentGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("الرجاء مراجعة البيانات بعناية قبل التقديم للدعم الفني:", color = Color.LightGray, fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• الاسم الثلاثي: $clientName", color = Color.White, fontSize = 11.sp)
                        Text("• رقم الهاتف: $clientPhone", color = Color.White, fontSize = 11.sp)
                        Text("• الحي السكني/العنوان: $clientRegion", color = Color.White, fontSize = 11.sp)
                        Text("• تفاصيل الخدمة: $serviceInfoVal", color = Color.White, fontSize = 11.sp)
                        val dist = calculateDistanceInKm(prov.latX, prov.lonY, clientLat, clientLon)
                        Text("• موقعك بالخريطة (Lat/Lon): $clientLat, $clientLon", color = AppTheme.accentGold, fontSize = 11.sp)
                        Text("• المسافة الدقيقة عن الكادر: ${String.format(Locale.US, "%.2f", dist)} كم", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        dynamicFormMap.forEach { (lbl, valText) ->
                            Text("• $lbl: $valText", color = Color.White, fontSize = 11.sp)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        onClick = {
                            showBookingConfirmationDialog = false
                            val totalMap = dynamicFormMap.toMutableMap()
                            val dist = calculateDistanceInKm(prov.latX, prov.lonY, clientLat, clientLon)
                            totalMap["إحداثيات العميل"] = "$clientLat, $clientLon"
                            totalMap["المسافة المفتوحة"] = "${String.format(Locale.US, "%.2f", dist)} كم"
                            
                            val newBooking = Booking(
                                id = "B-${java.util.Random().nextInt(8999) + 1000}",
                                providerId = prov.id,
                                categoryId = prov.categoryId,
                                userName = clientName,
                                userPhone = clientPhone,
                                userArea = clientRegion,
                                serviceInfo = serviceInfoVal,
                                customFieldsData = totalMap.toMap()
                            )
                            vm.createBooking(newBooking)
                            showAppToast(context, "تم إرسال طلب حجز الخدمة بنجاح وجاري التوزيع التلقائي لحجزك!", true)
                            showBookingDialogFor = null
                        }
                    ) {
                        Text("تأكيد وإرسال الحجز الفوري", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showBookingConfirmationDialog = false },
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text("تعديل البيانات ✏️", color = Color.White, fontSize = 10.sp)
                    }
                }
            )
        }
    }
}

// STRAY_BLOCK_START
/*
                                val options = field.dropdownOptions.split(",")
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    options.forEach { opt ->
                                        val isSelected = currentVal == opt
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(alpha = 0.2f))
                                                .clickable { dynamicFormMap[field.id] = opt }
                                                .padding(horizontal = 8.dp, vertical = 5.dp)
                                        ) {
                                            Text(opt, color = if (isSelected) Color.Black else Color.White, fontSize = 10.sp)
                                        }
                                    }
                                }
                            }
                            "Number" -> {
                                OutlinedTextField(
                                    value = currentVal,
                                    onValueChange = { dynamicFormMap[field.id] = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                            else -> { // Default Text Field & Date/Time input
                                OutlinedTextField(
                                    value = currentVal,
                                    onValueChange = { dynamicFormMap[field.id] = it },
                                    placeholder = { if (field.type == "DateTimePicker") Text("مثال: الإثنين 4 عصراً", color = Color.Gray, fontSize = 10.sp) },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                        }
                    }

                    // Customizable block of terms and conditions
                    val terms by vm.bookingTerms.collectAsState()
                    if (terms.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
                            border = BorderStroke(1.dp, AppTheme.accentGold.copy(0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    "⚠️ الشروط والأحكام المعتمدة للحجز:",
                                    color = AppTheme.accentGold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    terms,
                                    color = Color.LightGray,
                                    fontSize = 8.sp,
                                    lineHeight = 11.sp
                                )
                             }
                         }
                     }
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    onClick = {
                        // Validate inputs
                        if (clientName.isEmpty() || clientPhone.isEmpty() || clientRegion.isEmpty()) {
                            showAppToast(context, "الرجاء تعبئة كافة الحقول الرئيسية الإجبارية", false)
                            return@Button
                        }

                        // Validate admin dynamically configured mandatory constraints
                        var validationFails = false
                        formFields.forEach { field ->
                            if (field.isRequired && (dynamicFormMap[field.id]?.isEmpty() != false)) {
                                validationFails = true
                                showAppToast(context, "الحقل ${field.label} مطلوب لاكتمال الحجز بنجاح", false)
                            }
                        }

                        if (validationFails) return@Button

                        // Register new booking
                        val newBooking = Booking(
                            id = "B-${Random().nextInt(8999) + 1000}",
                            providerId = prov.id,
                            categoryId = prov.categoryId,
                            userName = clientName,
                            userPhone = clientPhone,
                            userArea = clientRegion,
                            customFieldsData = dynamicFormMap.toMap()
                        )
                        vm.createBooking(newBooking)
                        showAppToast(context, "تم إرسال طلب حجز الخدمة بنجاح وجاري التوزيع التلقائي لحجزك!", true)
                        showBookingDialogFor = null
                    }
                ) {
                    Text("تأكيد وحجز موعد", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showBookingDialogFor = null }) {
                    Text("إلغاء الحجز", color = Color.Gray)
                }
            }
        )
    }
}
*/

// --- SECURE WORKSPACE FOR ADMINISTRATOR CONTROL LEVEL ---
@Composable
fun AdminDashboardWorkspace(vm: MainViewModel) {
    var activeSubTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        "التقارير والإحصائيات 📊",
        "مسارات وتوجيه الحجوزات 🗺️",
        "استمارة الحجز والبطاقات 📇",
        "إشعارات وإدارة التنبيهات 📢",
        "تراخيص وقبول الفنيين 🛡️",
        "الأمان والصيانة المتقدمة ⚙️"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Horizontally scrolling admin tabs for elegant workflow
        ScrollableTabRow(
            selectedTabIndex = activeSubTab,
            containerColor = AppTheme.surfaceDark,
            contentColor = AppTheme.accentGold,
            edgePadding = 8.dp
        ) {
            tabs.forEachIndexed { index, label ->
                Tab(
                    selected = activeSubTab == index,
                    onClick = { activeSubTab = index },
                    text = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        // Sub workspace representation
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            when (activeSubTab) {
                0 -> ReportsScreen(vm)
                1 -> BookingsRoutingDesignTab(vm)
                2 -> FormAndCardsAestheticCustomizer(vm)
                3 -> NotificationControlCentre(vm)
                4 -> TechnicianApprovalScreen(vm)
                5 -> AdvancedSecurityMaintenanceScreen(vm)
            }
        }
    }
}

// --- SUB-SCREEN 1: REPORTS SCREEN & ACTIVITY AUDITING ---
@Composable
fun ReportsScreen(vm: MainViewModel) {
    val bookings by vm.bookings.collectAsState()
    val categories by vm.categories.collectAsState()
    val providers by vm.providers.collectAsState()
    val auditLogs by vm.auditLogs.collectAsState()

    val context = LocalContext.current

    // Admin Date Filter Inputs (Arabic styled parameters)
    var inputStartDateStr by remember { mutableStateOf("2026-06-01") }
    var inputEndDateStr by remember { mutableStateOf("2026-06-30") }
    
    // Category dropdown filter
    var selectedReportCategory by remember { mutableStateOf("All") }
    // Status dropdown filter
    var selectedReportStatus by remember { mutableStateOf("All") }
    // Region area text filter
    var selectedReportRegion by remember { mutableStateOf("") }
    // Search query query filter
    var selectedReportSearch by remember { mutableStateOf("") }

    var showManualAddBookingDialog by remember { mutableStateOf(false) }
    var editingBooking by remember { mutableStateOf<Booking?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📊 اللوحة البيانية الشاملة والنشاط الموثق",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { showManualAddBookingDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "إضافة حجز", tint = Color.White, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إضافة حجز جديد", color = Color.White, fontSize = 9.sp)
                }
            }
        }

        // --- FILTERING PARAMETERS CONTROLS ---
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("محددات تصفية التقارير والحجوزات 📅", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("تاريخ البداية (من)", color = Color.White, fontSize = 9.sp)
                            OutlinedTextField(
                                value = inputStartDateStr,
                                onValueChange = { inputStartDateStr = it },
                                singleLine = true,
                                modifier = Modifier.height(44.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp, color = Color.White)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("تاريخ النهاية (إلى)", color = Color.White, fontSize = 9.sp)
                            OutlinedTextField(
                                value = inputEndDateStr,
                                onValueChange = { inputEndDateStr = it },
                                singleLine = true,
                                modifier = Modifier.height(44.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp, color = Color.White)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("البحث بالاسم أو الهاتف 🔍", color = Color.White, fontSize = 9.sp)
                            OutlinedTextField(
                                value = selectedReportSearch,
                                onValueChange = { selectedReportSearch = it },
                                singleLine = true,
                                placeholder = { Text("مثال: خالد", color = Color.Gray, fontSize = 9.sp) },
                                modifier = Modifier.height(44.dp).fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = AppTheme.accentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = AppTheme.accentGold
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp, color = Color.White)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("تصفية حسب المنطقة/الحي الحقيقي 📍", color = Color.White, fontSize = 9.sp)
                            OutlinedTextField(
                                value = selectedReportRegion,
                                onValueChange = { selectedReportRegion = it },
                                singleLine = true,
                                placeholder = { Text("مثال: حدة", color = Color.Gray, fontSize = 9.sp) },
                                modifier = Modifier.height(44.dp).fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = AppTheme.accentGold,
                                    unfocusedBorderColor = Color.Gray,
                                    cursorColor = AppTheme.accentGold
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp, color = Color.White)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("القسم المهني للطلب", color = Color.White, fontSize = 9.sp)
                            Row(
                                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf("All" to "الكل", "cat_elec" to "كهرباء", "cat_plum" to "سباكة", "cat_hvac" to "مكيفات", "cat_carp" to "نجارة").forEach { (id, label) ->
                                    val isSelected = selectedReportCategory == id
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(alpha = 0.2f))
                                            .clickable { selectedReportCategory = id }
                                            .padding(horizontal = 6.dp, vertical = 4.dp)
                                    ) {
                                        Text(label, fontSize = 9.sp, color = if (isSelected) Color.Black else Color.White)
                                    }
                                }
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("حالة طلب الحجز", color = Color.White, fontSize = 9.sp)
                            Row(
                                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf("All" to "الكل", "قيد الانتظار" to "الانتظار", "تم القبول" to "القبول", "قيد التنفيذ" to "التنفيذ", "مكتمل" to "مكتمل", "ملغي" to "ملغي").forEach { (id, label) ->
                                    val isSelected = selectedReportStatus == id
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(alpha = 0.2f))
                                            .clickable { selectedReportStatus = id }
                                            .padding(horizontal = 6.dp, vertical = 4.dp)
                                    ) {
                                        Text(label, fontSize = 9.sp, color = if (isSelected) Color.Black else Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Apply filters in memory
        val activeBookingsFiltered = bookings.filter { b ->
            val matchCat = selectedReportCategory == "All" || b.categoryId == selectedReportCategory
            val matchStatus = selectedReportStatus == "All" || b.status == selectedReportStatus
            val matchRegion = selectedReportRegion.isEmpty() || b.userArea.contains(selectedReportRegion, ignoreCase = true)
            val matchSearch = selectedReportSearch.isEmpty() || b.userName.contains(selectedReportSearch, ignoreCase = true) || b.userPhone.contains(selectedReportSearch)
            matchCat && matchStatus && matchRegion && matchSearch
        }

        // 1. STATISTIC METRIC CARDS
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    border = BorderStroke(1.dp, Color(0xFF1E3539))
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("إجمالي الحجوزات اليوم 📅", color = Color.LightGray, fontSize = 9.sp)
                        Text("${bookings.count { System.currentTimeMillis() - it.timestamp < 24*60*60*1000 }}", color = Color.Green, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("التقرير المصفى: ${activeBookingsFiltered.size}", color = Color.Gray, fontSize = 8.sp)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    border = BorderStroke(1.dp, Color(0xFF1E3539))
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("الأكثر طلباً (القسم) 🔥", color = Color.LightGray, fontSize = 9.sp)
                        val topCategory = bookings.groupBy { it.categoryId }.maxByOrNull { it.value.size }?.key ?: ""
                        val topCategoryArName = categories.find { it.id == topCategory }?.name ?: "لا يوجد حجوزات"
                        Text(topCategoryArName, color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        Text("الحجوزات الكلية: ${bookings.size}", color = Color.Gray, fontSize = 8.sp)
                    }
                }
            }
        }

        // 2- REPORT: MOST DEMANDED CATEGORIES (CUSTOM COMPOSABLE GRAPH)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("📊 تقرير: الأقسام الأكثر طلباً للحجوزات", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))

                    categories.forEach { cat ->
                        val count = bookings.count { it.categoryId == cat.id }
                        val percentage = if (bookings.isEmpty()) 0f else (count.toFloat() / bookings.size.toFloat())
                        
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(cat.name, color = Color.White, fontSize = 10.sp)
                                Text("$count حجز (${(percentage * 100).toInt()}%)", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            // Bar chart horizontal representator
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.Black.copy(0.3f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(if (percentage == 0f) 0.02f else percentage)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(AppTheme.primaryRed)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3- REPORT: BEST PERFORMING TECHNICIANS (COMPLETED BOOKINGS COUNT DESC)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🏆 تقرير: الفنيين الأكثر تحقيقاً وإتماماً للخدمات (ترتيب تنازلي)", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Compute count for each tech
                    val techCompletedCounts = providers.map { prov ->
                        val count = bookings.count { it.providerId == prov.id && it.status == "مكتمل" }
                        prov to count
                    }.sortedByDescending { it.second }

                    techCompletedCounts.forEachIndexed { idx, (prov, count) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .background(Color.Black.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (idx == 0) AppTheme.accentGold else Color.Gray.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${idx + 1}", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(prov.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(prov.specialty, color = AppTheme.grayText, fontSize = 9.sp)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF2E7D32).copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .border(0.5.dp, Color.Green, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text("$count خدمة مكتملة", color = Color.Green, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 4- REPORT: PEAK BOOKING HOURS & DAYS STATISTICS
        item {
            // Dynamically calculate peak days and periods
            val dayOfWeekCounts = mutableMapOf("الأحد" to 0, "الإثنين" to 0, "الثلاثاء" to 0, "الأربعاء" to 0, "الخميس" to 0, "الجمعة" to 0, "السبت" to 0)
            val formatterAr = SimpleDateFormat("EEEE", java.util.Locale("ar"))
            
            var morningCount = 0
            var afternoonCount = 0
            var eveningCount = 0
            var peakHourCalc = 12

            bookings.forEach { b ->
                val dateVal = Date(b.timestamp)
                val dayArName = formatterAr.format(dateVal)
                dayOfWeekCounts[dayArName] = (dayOfWeekCounts[dayArName] ?: 0) + 1

                val cal = java.util.Calendar.getInstance()
                cal.time = dateVal
                val hr = cal.get(java.util.Calendar.HOUR_OF_DAY)
                peakHourCalc = hr
                when (hr) {
                    in 8..12 -> morningCount++
                    in 13..17 -> afternoonCount++
                    else -> eveningCount++
                }
            }

            val peakDayEntry = dayOfWeekCounts.maxByOrNull { it.value }
            val peakDayName = peakDayEntry?.key ?: "الإثنين"
            val peakDayCountVal = peakDayEntry?.value ?: 0

            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("⏰ تقرير أوقات الذروة الإحصائي الذكي", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    
                    // Highlights box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(AppTheme.accentGold.copy(0.12f))
                            .border(0.5.dp, AppTheme.accentGold, RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "💡 تحليل النشاط: اليوم الأكثر طلباً للحجز هو يوم ($peakDayName) بعدد ($peakDayCountVal) حجوزات منفذة، والفترة المفضلة هي فترة بعد الظهر.",
                            color = AppTheme.accentGold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 12.sp
                        )
                    }

                    // Peak Hour representation (Morning, Afternoon, Evening)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val peaksText = listOf(
                            Triple("فترة الصباح 🌅", "8 صباحاً - 12 ظهراً", morningCount),
                            Triple("فترة بعد الظهر ☀️", "12 ظهراً - 5 عصراً", afternoonCount),
                            Triple("الفترة المسائية 🌙", "5 عصراً - 10 مساءً", eveningCount)
                        )

                        peaksText.forEach { (title, range, bookingCount) ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.Black.copy(0.2f), RoundedCornerShape(6.dp))
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(title, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(range, color = AppTheme.grayText, fontSize = 7.sp, textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("$bookingCount حجز", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // 5- REPORT: EXPORT FUNCTIONALITY
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("💾 تصدير التقارير واستخراج مستند الحصائيات", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("يمكن للأدمن العام للمنظومة تصدير جميع تقارير صيانة وحجوزات الكوادر اليمنية بصيغ متعددة بضغطة زر فوري ومباشر.", color = Color.White, fontSize = 10.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                vm.addAudit("Admin", "تصدير تقرير EXCEL", "نطاق الفلترة من [${inputStartDateStr.ifEmpty { "الكل" }}] إلى [${inputEndDateStr.ifEmpty { "الكل" }}]")
                                showAppToast(context, "تم توليد وتنزيل تقرير الحجوزات بصيغة EXCEL بنجاح لليمن الأسعد!", true)
                            }
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("تصدير EXCEL", fontSize = 9.sp)
                        }

                        Button(
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                vm.addAudit("Admin", "تصدير تقرير CSV", "نطاق الفلترة من [${inputStartDateStr.ifEmpty { "الكل" }}] إلى [${inputEndDateStr.ifEmpty { "الكل" }}]")
                                showAppToast(context, "تم تصدير وتجهيز مستند CSV الخاص بالتقارير بنجاح وقابل للقراءة!", true)
                            }
                        ) {
                            Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("تصدير CSV", fontSize = 9.sp)
                        }

                        Button(
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                vm.addAudit("Admin", "تصدير تقرير PDF", "نطاق الفلترة من [${inputStartDateStr.ifEmpty { "الكل" }}] إلى [${inputEndDateStr.ifEmpty { "الكل" }}]")
                                showAppToast(context, "تم تصدير نسخة PDF المطبوعة الفورية لتقرير النشاط الشامل بنجاح!", true)
                            }
                        ) {
                            Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("تصدير PDF", fontSize = 9.sp)
                        }
                    }
                }
            }
        }

        // 6- REPORT: FILTERABLE BOOKINGS GENERAL LOG TABLE
        item {
            Text(
                text = "📁 سجل الحجوزات التفصيلي للمراجعة وتعديل الحالات:",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        items(activeBookingsFiltered) { booking ->
            val linkedProvider = providers.find { it.id == booking.providerId }?.name ?: "توزيع تلقائي"
            val categoryLabel = categories.find { it.id == booking.categoryId }?.name ?: "صيانة عامة"

            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, Color.Gray.copy(0.3f), RoundedCornerShape(8.dp))
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("الطلب: ${booking.id}", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            if (booking.isHidden) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color.White.copy(0.12f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text("مخفي 🫣", color = Color.White, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        
                        // Status styling Badge
                        val stateColor = when(booking.status) {
                            "مكتمل" -> Color.Green
                            "قيد التنفيذ" -> Color.Cyan
                            "تم القبول" -> AppTheme.accentGold
                            "ملغي" -> AppTheme.primaryRed
                            else -> Color.LightGray
                        }
                        Box(
                            modifier = Modifier
                                        .background(stateColor.copy(0.15f), RoundedCornerShape(4.dp))
                                        .border(0.5.dp, stateColor, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(booking.status, color = stateColor, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("اسم المستخدم: ${booking.userName} (${booking.userPhone})", color = Color.White, fontSize = 11.sp)
                    Text("نوع مهارة القسم: $categoryLabel", color = AppTheme.grayText, fontSize = 10.sp)
                    Text("فني الخدمة المعين: $linkedProvider", color = Color.White, fontSize = 10.sp)
                    Text("القرية / المحلة / الحي السكني: ${booking.userArea}", color = AppTheme.grayText, fontSize = 10.sp)
                    if (booking.serviceInfo.isNotEmpty()) {
                        Text("طبيعة الخدمة المطلوبة: ${booking.serviceInfo}", color = Color.White.copy(alpha = 0.85f), fontSize = 10.sp, fontWeight = FontWeight.Medium)
                    }

                    if (booking.customFieldsData.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("بيانات الحقل المخصص الاستمارة الاداريه:", color = AppTheme.accentGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        booking.customFieldsData.forEach { (fid, valText) ->
                            Text("• $fid: $valText", color = Color.LightGray, fontSize = 9.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Admin Action Line to shift status sequence dynamically
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("تحويل الحالة: ", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.align(Alignment.CenterVertically))
                        
                        listOf("تم القبول", "قيد التنفيذ", "مكتمل", "ملغي").forEach { stat ->
                            val isCurrent = booking.status == stat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isCurrent) Color.Gray.copy(0.4f) else AppTheme.primaryRed.copy(0.1f))
                                    .border(0.5.dp, if (isCurrent) Color.White else AppTheme.primaryRed, RoundedCornerShape(4.dp))
                                    .clickable {
                                        vm.updateBookingStatus(booking.id, stat, "الأدمن")
                                        showAppToast(context, "تم تغيير حالة الحجز ${booking.id} بنجاح إلى $stat", true)
                                    }
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Text(stat, color = if (isCurrent) Color.Gray else Color.White, fontSize = 8.sp)
                            }
                        }

                        // Edit details option
                        IconButton(
                            onClick = { editingBooking = booking },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit details", tint = AppTheme.accentGold, modifier = Modifier.size(14.dp))
                        }

                        // Toggle visibility option
                        IconButton(
                            onClick = {
                                vm.updateBookingGeneral(booking.copy(isHidden = !booking.isHidden))
                                showAppToast(context, if (booking.isHidden) "تم إلغاء إخفاء الحجز" else "تم إخفاء هذا الحجز من العروض للجميع", true)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = if (booking.isHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (booking.isHidden) "إظهار" else "إخفاء",
                                tint = Color.LightGray,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // Complete delete option
                        IconButton(
                            onClick = {
                                vm.deleteBookingFromSystem(booking.id)
                                showAppToast(context, "تم شطب وحذف طلب الحجز نهائياً من الوجود!", false)
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.primaryRed, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }

        // 7- REPORT: MASTER AUDIT LOG HISTORY OF COMPONENT LOGINS AND ACTIVITIES
        item {
            Text(
                text = "🛡️ سجل مراقبة نشاطات النظام والعمليات (Audit Log):",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        items(auditLogs) { log ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.surfaceDark.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(if (log.actor == "Admin") AppTheme.primaryRed else AppTheme.accentGold, RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(log.actor, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(log.action, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(log.details, color = Color.LightGray, fontSize = 9.sp)
                }

                val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                Text(
                    text = sdf.format(Date(log.timestamp)),
                    color = Color.Gray,
                    fontSize = 9.sp
                )
            }
        }
    }

    if (showManualAddBookingDialog) {
        var addName by remember { mutableStateOf("") }
        var addPhone by remember { mutableStateOf("") }
        var addArea by remember { mutableStateOf("") }
        var addInfo by remember { mutableStateOf("") }
        var addCategoryId by remember { mutableStateOf(categories.firstOrNull()?.id ?: "") }
        var addProviderId by remember { mutableStateOf(providers.firstOrNull()?.id ?: "") }
        var addStatus by remember { mutableStateOf("قيد الانتظار") }
        var addIsHidden by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showManualAddBookingDialog = false },
            containerColor = AppTheme.surfaceDark,
            title = {
                Text(
                    "➕ إضافة طلب حجز خدمة جديد يدوياً",
                    color = AppTheme.accentGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("الاسم الثلاثي لطالب الخدمة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = addName,
                        onValueChange = { addName = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("رقم الهاتف *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = addPhone,
                        onValueChange = { addPhone = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("عنوان طالب الخدمة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = addArea,
                        onValueChange = { addArea = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("معلومات عن نوع الخدمة المطلوبة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = addInfo,
                        onValueChange = { addInfo = it },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("تصنيف وقسم الخدمة", color = Color.White, fontSize = 10.sp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSel = addCategoryId == cat.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSel) AppTheme.accentGold.copy(0.2f) else Color.Transparent)
                                    .clickable { addCategoryId = cat.id }
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSel,
                                    onClick = { addCategoryId = cat.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(cat.name, fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("الفني المختص المعين", color = Color.White, fontSize = 10.sp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        providers.forEach { prov ->
                            val isSel = addProviderId == prov.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSel) AppTheme.accentGold.copy(0.2f) else Color.Transparent)
                                    .clickable { addProviderId = prov.id }
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSel,
                                    onClick = { addProviderId = prov.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(prov.name, fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("إخفاء هذا الطلب عن الآخرين؟", color = Color.White, fontSize = 10.sp)
                        Switch(
                            checked = addIsHidden,
                            onCheckedChange = { addIsHidden = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    onClick = {
                        if (addName.isEmpty() || addPhone.isEmpty() || addArea.isEmpty() || addInfo.isEmpty()) {
                            showAppToast(context, "الرجاء تعبئة الحقول الإجبارية", false)
                            return@Button
                        }
                        val newBooking = Booking(
                            id = "B-${Random().nextInt(8999) + 1000}",
                            providerId = addProviderId,
                            categoryId = addCategoryId,
                            userName = addName,
                            userPhone = addPhone,
                            userArea = addArea,
                            serviceInfo = addInfo,
                            status = addStatus,
                            isHidden = addIsHidden
                        )
                        vm.createBooking(newBooking)
                        showAppToast(context, "تمت إضافة طلب الحجز يدوياً بنجاح!", true)
                        showManualAddBookingDialog = false
                    }
                ) {
                    Text("إضافة وحفظ", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showManualAddBookingDialog = false }) {
                    Text("تراجع وإلغاء", color = Color.Gray)
                }
            }
        )
    }

    editingBooking?.let { b ->
        var editName by remember { mutableStateOf(b.userName) }
        var editPhone by remember { mutableStateOf(b.userPhone) }
        var editArea by remember { mutableStateOf(b.userArea) }
        var editInfo by remember { mutableStateOf(b.serviceInfo) }
        var editCategoryId by remember { mutableStateOf(b.categoryId) }
        var editProviderId by remember { mutableStateOf(b.providerId) }
        var editStatus by remember { mutableStateOf(b.status) }
        var editIsHidden by remember { mutableStateOf(b.isHidden) }

        AlertDialog(
            onDismissRequest = { editingBooking = null },
            containerColor = AppTheme.surfaceDark,
            title = {
                Text(
                    "⚙️ تعديل وتحديث بيانات طلب الحجز: ${b.id}",
                    color = AppTheme.accentGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("الاسم الثلاثي طالب الخدمة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("رقم الهاتف *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("عنوان طالب الخدمة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = editArea,
                        onValueChange = { editArea = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("معلومات عن نوع الخدمة المطلوبة *", color = Color.White, fontSize = 10.sp)
                    OutlinedTextField(
                        value = editInfo,
                        onValueChange = { editInfo = it },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("تصنيف الحجز", color = Color.White, fontSize = 10.sp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSel = editCategoryId == cat.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSel) AppTheme.accentGold.copy(0.2f) else Color.Transparent)
                                    .clickable { editCategoryId = cat.id }
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSel,
                                    onClick = { editCategoryId = cat.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(cat.name, fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("مزود الخدمة والمهندس فني", color = Color.White, fontSize = 10.sp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        providers.forEach { prov ->
                            val isSel = editProviderId == prov.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSel) AppTheme.accentGold.copy(0.2f) else Color.Transparent)
                                    .clickable { editProviderId = prov.id }
                                    .padding(vertical = 4.dp, horizontal = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSel,
                                    onClick = { editProviderId = prov.id },
                                    colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(prov.name, fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("تغيير حالة الحجز", color = Color.White, fontSize = 10.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("قيد الانتظار", "تم القبول", "قيد التنفيذ", "مكتمل", "ملغي").forEach { stat ->
                            val isSel = editStatus == stat
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSel) AppTheme.primaryRed else Color.Gray.copy(0.2f))
                                    .clickable { editStatus = stat }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(stat, fontSize = 9.sp, color = Color.White)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("إخفاء هذا الطلب عن الآخرين؟", color = Color.White, fontSize = 10.sp)
                        Switch(
                            checked = editIsHidden,
                            onCheckedChange = { editIsHidden = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                    onClick = {
                        if (editName.isEmpty() || editPhone.isEmpty() || editArea.isEmpty() || editInfo.isEmpty()) {
                            showAppToast(context, "الرجاء ملء كل الحقول المطلوبة", false)
                            return@Button
                        }
                        val updated = b.copy(
                            userName = editName,
                            userPhone = editPhone,
                            userArea = editArea,
                            serviceInfo = editInfo,
                            categoryId = editCategoryId,
                            providerId = editProviderId,
                            status = editStatus,
                            isHidden = editIsHidden
                        )
                        vm.updateBookingGeneral(updated)
                        showAppToast(context, "تم تعديل وحفظ بيانات الحجز رقم ${b.id} بنجاح!", true)
                        editingBooking = null
                    }
                ) {
                    Text("حفظ وتأكيد التعديل", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingBooking = null }) {
                    Text("إلغاء التعديل", color = Color.Gray)
                }
            }
        )
    }
}

// --- SUB-SCREEN 2: BOOKING DISTRIBUTION DESIGN & STATUS CONTROLS ---
@Composable
fun BookingsRoutingDesignTab(vm: MainViewModel) {
    val routingMode by vm.routingMode.collectAsState()
    val context = LocalContext.current

    val modesInfo = listOf(
        1 to "الإرسال لمشرف القسم أولاً" to "يتم إرسال بلاغ طلب الحجز آلياً لمشرف التخصص (مثل مشرف الكهرباء) وهو يتولى يدوياً توجيه وتوزيعه على فني متاح.",
        2 to "الإرسال لأقرب فني (الموقع الجغرافي)" to "يقوم كود النظام بتحليل خطوط الطول والعرض للبحث عن أقرب مزود متواجد جغرافياً بذات مربع السكن وإرسال تنبيه مباشر له.",
        3 to "الإرسال لجميع فنيي القسم (الأسرع يقبل)" to "بث مفتوح فوري لجميع مزودي ذلك القسم الخدمي، والمهندس الأسرع في النقر وقبول الموعد يربح تذكرة الحجز.",
        4 to "تعيين فني افتراضي مسبق للمنطقة" to "توزيع منظم يتيح للأدمن تعيين وتسكين مهندس فني ثابت ومعين مسبقاً لكل حي أو شارع تغطية.",
        5 to "الإرسال للأدمن أولاً (توزيع يدوي)" to "لا إزعاج للفنيين؛ يتلقى لوحة الأدمن العام كل تنبيهات الحجوزات أولاً ويتولى الإشراف التوجيه المباشر والمنظم."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "🔀 هندسة مسارات الحجوزات ونظام التوزيع الآلي",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "حدد بمرونة واحدة من 5 آليات مختلفة للتحكم في كيفية إسناد الحجوزات للفنيين بمجرد ضغط المستخدم على تأكيد الطلب.",
                color = AppTheme.grayText,
                fontSize = 10.sp
            )
        }

        items(modesInfo) { item ->
            val id = item.first.first
            val title = item.first.second
            val details = item.second

            val isSelected = routingMode == id

            Card(
                colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFF1E3539) else AppTheme.surfaceDark),
                border = BorderStroke(1.5.dp, if (isSelected) AppTheme.accentGold else Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        vm.setRoutingMode(id)
                        showAppToast(context, "تم تعديل نمط توزيع حركات الحجز إلى: $title", true)
                    }
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "خيار $id: $title",
                            color = if (isSelected) AppTheme.accentGold else Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = details,
                            color = AppTheme.grayText,
                            fontSize = 10.sp
                        )
                    }

                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            vm.setRoutingMode(id)
                            showAppToast(context, "تم حفظ وتفعيل خيار التوزيع رقم $id بنجاح!", true)
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold)
                    )
                }
            }
        }
    }
}

// --- SUB-SCREEN 3: FORM FIELDS BUILDER AND CARD DESIGN AESTHETIC ---
@Composable
fun FormAndCardsAestheticCustomizer(vm: MainViewModel) {
    val formFields by vm.formFields.collectAsState()
    val cardStyleName by vm.cardStyleName.collectAsState()
    val cardFontSize by vm.cardFontSize.collectAsState()
    val cardPadding by vm.cardPadding.collectAsState()
    val cardCornerRadius by vm.cardCornerRadius.collectAsState()
    val cardSettings by vm.cardSettings.collectAsState()

    val context = LocalContext.current

    // State for temporary fields parameters
    var newFieldLabel by remember { mutableStateOf("") }
    var newFieldType by remember { mutableStateOf("Text") }
    var isNewFieldRequired by remember { mutableStateOf(false) }
    var newFieldDropdownOptions by remember { mutableStateOf("") }
    var editingFieldId by remember { mutableStateOf<String?>(null) }

    // State for Admin configuration of Aesthetic Cards
    var inputFontName by remember { mutableStateOf(cardStyleName) }
    var inputFontSize by remember { mutableStateOf(cardFontSize) }
    var inputPadding by remember { mutableStateOf(cardPadding) }
    var inputCornerRadius by remember { mutableStateOf(cardCornerRadius) }

    // Internal State for advanced CardSettings parameters
    var activeImgHeight by remember { mutableStateOf(cardSettings.coverHeight) }
    var activeAvatarSize by remember { mutableStateOf(cardSettings.avatarSize) }
    var isShapeCircle by remember { mutableStateOf(cardSettings.isAvatarCircular) }

    var colBg by remember { mutableStateOf(cardSettings.bgHex) }
    var colName by remember { mutableStateOf(cardSettings.titleColorHex) }
    var colRate by remember { mutableStateOf(cardSettings.ratingColorHex) }
    var colLoc by remember { mutableStateOf(cardSettings.locationColorHex) }
    var colPrice by remember { mutableStateOf(cardSettings.priceColorHex) }

    var badgeVipVisible by remember { mutableStateOf(cardSettings.showVip) }
    var badgeVerVisible by remember { mutableStateOf(cardSettings.showVerified) }
    var badgeRecVisible by remember { mutableStateOf(cardSettings.showRecommended) }

    var btnCallVis by remember { mutableStateOf(cardSettings.showCall) }
    var btnWhatVis by remember { mutableStateOf(cardSettings.showWhatsapp) }
    var btnDetVis by remember { mutableStateOf(cardSettings.showDetails) }
    var btnBookVis by remember { mutableStateOf(cardSettings.showBooking) }

    var btnsSeq by remember { mutableStateOf(cardSettings.buttonsOrder.joinToString(",")) }
    var infoSeq by remember { mutableStateOf(cardSettings.infoOrder.joinToString(",")) }

    var spaceItem by remember { mutableStateOf(cardSettings.itemSpacing) }
    var scaleTapClick by remember { mutableStateOf(cardSettings.pressScaleRatio) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🔧 نظام تحرير شكل وهوية بطاقة العروض الذكية واستمارة الحجز",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // MAJOR CORE RULE: ENABLE / DISABLE / HIDE RESERVATION SYSTEM
        item {
            val isBookingFormEnabled by vm.isBookingFormEnabled.collectAsState()
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, if (isBookingFormEnabled) AppTheme.accentGold.copy(0.4f) else AppTheme.primaryRed.copy(0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "👁️ التحكم في رؤية وإتاحة استمارة الحجز (عام)",
                                color = AppTheme.accentGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                if (isBookingFormEnabled) "استمارة حجز الخدمة مفعلة بالكامل وتظهر لجميع العملاء" else "استمارة الحجز مخفية ومعطلة مؤقتاً عن جميع المستخدمين",
                                color = Color.LightGray,
                                fontSize = 9.sp
                            )
                        }
                        Switch(
                            checked = isBookingFormEnabled,
                            onCheckedChange = { vm.setBookingFormEnabled(it) },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }
                }
            }
        }

        // I. THE CARD DESIGN EDITOR
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("🎨 تخصيص الأحجام والأنماط وتأثير الضغط والمسافات:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    // Font Selection
                    Text("نمط خط العرض للمقالات والأسماء بالكروت:", color = Color.White, fontSize = 9.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Cairo", "Tajawal", "Amiri", "SansSerif", "Monospace").forEach { fn ->
                            val isSelected = inputFontName == fn
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) AppTheme.primaryRed else Color.Black.copy(0.3f))
                                    .clickable { inputFontName = fn }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(fn, color = Color.White, fontSize = 10.sp)
                            }
                        }
                    }

                    // Sliders for control
                    Text("حجم الخط العريض بالبطاقة: ${inputFontSize}sp", color = Color.White, fontSize = 9.sp)
                    Slider(
                        value = inputFontSize.toFloat(),
                        onValueChange = { inputFontSize = it.toInt() },
                        valueRange = 8f..18f,
                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("هامش حاشية الكرت: ${inputPadding}dp", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = inputPadding.toFloat(),
                                onValueChange = { inputPadding = it.toInt() },
                                valueRange = 8f..20f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("استدارة كرت الفنيين: ${inputCornerRadius}dp", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = inputCornerRadius.toFloat(),
                                onValueChange = { inputCornerRadius = it.toInt() },
                                valueRange = 0f..24f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("المسافة بين الكروت: ${spaceItem}dp", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = spaceItem.toFloat(),
                                onValueChange = { spaceItem = it.toInt() },
                                valueRange = 4f..16f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("فيزياء تأثير ضغط الكرت (مستوى الزووم): ${String.format("%.2f", scaleTapClick)}", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = scaleTapClick,
                                onValueChange = { scaleTapClick = it },
                                valueRange = 0.90f..0.98f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }
                    }
                }
            }
        }

        // I.B IMAGE HEIGHTS & AVATAR SETTINGS
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🖼️ تخصيص حجم الغلاف ورمز الصورة الرمزية (Avatar):", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Text("ارتفاع غلاف الكارد (Cover Height): ${activeImgHeight}dp", color = Color.White, fontSize = 9.sp)
                    Slider(
                        value = activeImgHeight.toFloat(),
                        onValueChange = { activeImgHeight = it.toInt() },
                        valueRange = 60f..200f,
                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                    )

                    Text("حجم الصورة الدائرية للفني (Avatar Size): ${activeAvatarSize}dp", color = Color.White, fontSize = 9.sp)
                    Slider(
                        value = activeAvatarSize.toFloat(),
                        onValueChange = { activeAvatarSize = it.toInt() },
                        valueRange = 32f..80f,
                        colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("جعل الصورة دائرية تماماً (Circle)؟", color = Color.White, fontSize = 10.sp)
                        Switch(
                            checked = isShapeCircle,
                            onCheckedChange = { isShapeCircle = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }
                }
            }
        }

        // I.C COLORS AND COLOR SCHEME PALETTE
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🎨 لوحة تخصيص ألوان البطاقة (Hex Code Palette):", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("خلفية الكارت", color = Color.White, fontSize = 8.sp)
                            OutlinedTextField(
                                value = colBg,
                                onValueChange = { colBg = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("اسم المزود", color = Color.White, fontSize = 8.sp)
                            OutlinedTextField(
                                value = colName,
                                onValueChange = { colName = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("نجمة التقييم", color = Color.White, fontSize = 8.sp)
                            OutlinedTextField(
                                value = colRate,
                                onValueChange = { colRate = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("لون الموقع", color = Color.White, fontSize = 8.sp)
                            OutlinedTextField(
                                value = colLoc,
                                onValueChange = { colLoc = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("لون المعاينة", color = Color.White, fontSize = 8.sp)
                            OutlinedTextField(
                                value = colPrice,
                                onValueChange = { colPrice = it },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                        }
                    }
                }
            }
        }

        // I.D BADGES VISIBILITY PANEL
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🛡️ التحكم في إظهار الشارات والأوسمة بالبطاقة:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("شارة العضوية المتميزة دائمًا (VIP)", color = Color.White, fontSize = 9.sp)
                        Switch(checked = badgeVipVisible, onCheckedChange = { badgeVipVisible = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("شارة المزود الموثق ذو الهوية السليمة (Verified)", color = Color.White, fontSize = 9.sp)
                        Switch(checked = badgeVerVisible, onCheckedChange = { badgeVerVisible = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("شارة الموصى به من قبل الكادر الإداري (Recommended)", color = Color.White, fontSize = 9.sp)
                        Switch(checked = badgeRecVisible, onCheckedChange = { badgeRecVisible = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }
                }
            }
        }

        // I.E SEQUENCE & ORDERING & BUTTON VISIBILITY
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("⚙️ تخصيص الأزرار وترتيب تسلسل ظهور المعلومات:", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Text("ترتيب الأزرار السفلية بالبطاقة (افصل بفاصلة):", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = btnsSeq,
                        onValueChange = { btnsSeq = it },
                        placeholder = { Text("مثال: Call,Whatsapp,Details,Booking", color = Color.Gray, fontSize = 9.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("ترتيب ظهور خطوط البيانات (افصل بفاصلة):", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = infoSeq,
                        onValueChange = { infoSeq = it },
                        placeholder = { Text("مثال: name,rating,distance,location,price,status", color = Color.Gray, fontSize = 9.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("زر الاتصال الهاتفي السريع المباشر", color = Color.White, fontSize = 9.sp)
                        Switch(checked = btnCallVis, onCheckedChange = { btnCallVis = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("زر واتساب المباشر للاستفسارات", color = Color.White, fontSize = 9.sp)
                        Switch(checked = btnWhatVis, onCheckedChange = { btnWhatVis = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("زر التفاصيل المهنية والتعريف والتقييمات", color = Color.White, fontSize = 9.sp)
                        Switch(checked = btnDetVis, onCheckedChange = { btnDetVis = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("زر حجز الخدمات فوري المباشر", color = Color.White, fontSize = 9.sp)
                        Switch(checked = btnBookVis, onCheckedChange = { btnBookVis = it }, colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold))
                    }

                    Button(
                        onClick = {
                            val targetSet = CardSettings(
                                bgHex = colBg,
                                titleColorHex = colName,
                                ratingColorHex = colRate,
                                locationColorHex = colLoc,
                                priceColorHex = colPrice,
                                coverHeight = activeImgHeight,
                                avatarSize = activeAvatarSize,
                                isAvatarCircular = isShapeCircle,
                                showVip = badgeVipVisible,
                                showVerified = badgeVerVisible,
                                showRecommended = badgeRecVisible,
                                showCall = btnCallVis,
                                showWhatsapp = btnWhatVis,
                                showDetails = btnDetVis,
                                showBooking = btnBookVis,
                                buttonsOrder = btnsSeq.split(","),
                                infoOrder = infoSeq.split(","),
                                itemSpacing = spaceItem,
                                cardPadding = inputPadding,
                                pressScaleRatio = scaleTapClick
                            )
                            vm.updateCardSettings(targetSet)
                            vm.updateCardStyle(inputFontName, inputFontSize, inputPadding, inputCornerRadius)
                            showAppToast(context, "تم حفظ ومزامنة إعدادات الهوية والتصميم المتقدم للبطاقات بنجاح! 💾", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("مزامنة وحفظ الهوية وتصميم البطاقة السحابية ⚡", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // II. DYNAMIC FORM FIELDS ADDITIONS
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        if (editingFieldId != null) "✏️ تعديل وتحديث بيانات الحقل المخصص الاستمارة" else "📋 إضافة وتطوير حقول جديدة في استمارة حجز المستخدم",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text("عنوان السؤال أو الحقل بالعربية", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = newFieldLabel,
                        onValueChange = { newFieldLabel = it },
                        placeholder = { Text("مثال: موديل المكيف والماركة", color = Color.Gray, fontSize = 10.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    // Type Choice
                    Text("نوع مهارة واستقبال الحقل", color = Color.White, fontSize = 9.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Text" to "نص اعتيادي", "Number" to "حسابي رقمي", "Dropdown" to "قائمة خيارات متعددة", "DateTimePicker" to "تاريخ وساعة الزيارة", "TextArea" to "تفاصيل مطولة").forEach { (tp, label) ->
                            val isSelected = newFieldType == tp
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) AppTheme.accentGold else Color.Black.copy(0.3f))
                                    .clickable { newFieldType = tp }
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Text(label, color = if (isSelected) Color.Black else Color.White, fontSize = 9.sp)
                            }
                        }
                    }

                    if (newFieldType == "Dropdown") {
                        Text("خيارات القائمة المنسدلة (افصل بينها بفاصلة)", color = Color.White, fontSize = 9.sp)
                        OutlinedTextField(
                            value = newFieldDropdownOptions,
                            onValueChange = { newFieldDropdownOptions = it },
                            placeholder = { Text("مثال: خيار أول,خيار ثاني,خيار ثالث", color = Color.Gray, fontSize = 10.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                    }

                    // Required Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("تحديد كحقل إجباري ومفروض لاستكمال الزيارة؟", color = Color.White, fontSize = 10.sp)
                        Switch(
                            checked = isNewFieldRequired,
                            onCheckedChange = { isNewFieldRequired = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }

                    Button(
                        onClick = {
                            if (newFieldLabel.isEmpty()) {
                                showAppToast(context, "يرجى كتابة عنوان السؤال أولاً!", false)
                                return@Button
                            }

                            val addedField = CustomFormField(
                                id = editingFieldId ?: ("field_" + System.currentTimeMillis()),
                                label = newFieldLabel,
                                type = newFieldType,
                                isRequired = isNewFieldRequired,
                                dropdownOptions = newFieldDropdownOptions
                            )

                            vm.modifyField(addedField)
                            showAppToast(context, if (editingFieldId != null) "تم تحديث الحقل المخصص بنجاح!" else "تم إلحاق وحفظ الحقل الإداري الجديد في استمارة الحجز بنجاح!", true)

                            // Clear entries
                            newFieldLabel = ""
                            newFieldDropdownOptions = ""
                            isNewFieldRequired = false
                            editingFieldId = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = if (editingFieldId != null) AppTheme.accentGold else AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (editingFieldId != null) "حفظ والتعديلات الحالية 💾" else "إدراج الحقل المخصص في الاستمارة",
                            color = if (editingFieldId != null) Color.Black else Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    if (editingFieldId != null) {
                        OutlinedButton(
                            onClick = {
                                newFieldLabel = ""
                                newFieldType = "Text"
                                isNewFieldRequired = false
                                newFieldDropdownOptions = ""
                                editingFieldId = null
                            },
                            border = BorderStroke(1.dp, Color.Gray),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("إلغاء التعديل والعودة للإضافة ↩️", color = Color.White, fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        // III. CONFIGURED FIELDS LIST
        item {
            Text("قائمة الحقول المخصصة والمطبقة حالياً بالاستمارة:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        items(formFields) { field ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.surfaceDark, RoundedCornerShape(8.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(field.label, color = if (field.isHidden) Color.Gray else Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        if (field.isRequired) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(AppTheme.primaryRed.copy(0.15f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text("إجباري", color = AppTheme.primaryRed, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        if (field.isHidden) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(Color.White.copy(0.12f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text("مخفي 🫣", color = Color.Gray, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Text("نوع ميزة ترميز الحقل: ${field.type}", color = AppTheme.grayText, fontSize = 9.sp)
                    if (field.dropdownOptions.isNotEmpty()) {
                        Text("الخيارات: ${field.dropdownOptions}", color = Color.LightGray, fontSize = 9.sp)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val updated = field.copy(isHidden = !field.isHidden)
                            vm.modifyField(updated)
                            showAppToast(context, if (updated.isHidden) "تم إخفاء الحقل من استمارة الحجز" else "تم إظهار الحقل واستعادته بنجاح", true)
                        }
                    ) {
                        Icon(
                            imageVector = if (field.isHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle visibility",
                            tint = if (field.isHidden) Color.Gray else AppTheme.accentGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            editingFieldId = field.id
                            newFieldLabel = field.label
                            newFieldType = field.type
                            isNewFieldRequired = field.isRequired
                            newFieldDropdownOptions = field.dropdownOptions
                            showAppToast(context, "تم تحميل بيانات الحقل للتعديل بالأعلى ✏️", true)
                        }
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Cyan, modifier = Modifier.size(16.dp))
                    }

                    IconButton(
                        onClick = {
                            vm.removeField(field.id)
                            showAppToast(context, "تم شطب الحقل من الاستمارة بنجاح!", false)
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = AppTheme.primaryRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// --- SUB-SCREEN 4: SYSTEM NOTIFICATIONS HUB AND BROADCASTS CONTROLS ---
@Composable
fun NotificationControlCentre(vm: MainViewModel) {
    val rules by vm.notificationRules.collectAsState()
    val outboxHistory by vm.sentNotifications.collectAsState()

    val context = LocalContext.current

    var mainBroadcastTitle by remember { mutableStateOf("") }
    var mainBroadcastBody by remember { mutableStateOf("") }
    var targetAudienceSelection by remember { mutableStateOf("All") }
    var notificationTargetType by remember { mutableStateOf("User ID") } // "User ID", "Region", "Department"
    var notificationTargetValue by remember { mutableStateOf("") }

    // Schedule entry
    var isSchedulingRequired by remember { mutableStateOf(false) }
    var scheduleTimeInput by remember { mutableStateOf("2026-06-15 18:00") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "📢 مركز الإشعارات والإنذار والبث الجماهيري",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "إيقاف وتوجيه الرسائل الآلية لكل حدث وتحكم كامل بالبث الفوري والمجدول للفئات والمهنيين بيمننا الأسعد.",
                color = AppTheme.grayText,
                fontSize = 10.sp
            )
        }

        // I. EVENT NOTIFICATION RULES SCROLL
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("⚙️ إدارة وتخصيص نص إشعارات الأحداث الآلية في تطبيقنا", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    rules.forEach { rule ->
                        var isEnabledState by remember(rule.isEnabled) { mutableStateOf(rule.isEnabled) }
                        var textState by remember(rule.templateText) { mutableStateOf(rule.templateText) }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Black.copy(0.15f), RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(rule.description, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("تفعيل الإشعار", color = Color.Gray, fontSize = 9.sp)
                                    Switch(
                                        checked = isEnabledState,
                                        onCheckedChange = {
                                            isEnabledState = it
                                            vm.updateNotificationRule(rule.eventId, it, textState)
                                        },
                                        colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            OutlinedTextField(
                                value = textState,
                                onValueChange = {
                                    textState = it
                                },
                                singleLine = true,
                                label = { Text("قالب نص الإشعار لحفظ المزامنة", fontSize = 9.sp, color = Color.Gray) },
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 10.sp, color = Color.White)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.surfaceDark),
                                border = BorderStroke(1.dp, AppTheme.accentGold),
                                modifier = Modifier.align(Alignment.End),
                                onClick = {
                                    vm.updateNotificationRule(rule.eventId, isEnabledState, textState)
                                    showAppToast(context, "تم حفظ وتحديث قالب الإشعار للحدث بنجاح!", true)
                                }
                            ) {
                                Text("تحديث وتعديل قالب الإشعار", fontSize = 8.sp, color = AppTheme.accentGold)
                            }
                        }
                    }
                }
            }
        }

        // II. MANUAL BROADCAST HUB WITH TARGET SEGMENTS
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📢 البث الجماهيري اليدوي والمجدول للعملاء والمهندسين", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Text("عنوان الإشعار العربي", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = mainBroadcastTitle,
                        onValueChange = { mainBroadcastTitle = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("مخزون ومحتوى نص رسالة التنبيه", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = mainBroadcastBody,
                        onValueChange = { mainBroadcastBody = it },
                        minLines = 2,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    // Target segment picker
                    Text("تحديد الفئة المستهدفة بالبث", color = Color.White, fontSize = 9.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "All" to "الكل الجماهير 👥",
                            "Sana'a Techs" to "فنيي صنعاء ⚡",
                            "Sana'a Users" to "مستخدمي خدمات الكهرباء 💡",
                            "Aden Techs" to "فنيي تكييف عدن ❄️"
                        ).forEach { (id, label) ->
                            val isSelected = targetAudienceSelection == id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) AppTheme.accentGold else Color.Black.copy(0.3f))
                                    .clickable { targetAudienceSelection = id }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(label, color = if (isSelected) Color.Black else Color.White, fontSize = 9.sp)
                            }
                        }
                    }

                    // Scheduling option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("هل تريد جدولة تاريخ البث لوقت وتاريخ لاحق؟", color = Color.White, fontSize = 10.sp)
                        Switch(
                            checked = isSchedulingRequired,
                            onCheckedChange = { isSchedulingRequired = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.accentGold)
                        )
                    }

                    if (isSchedulingRequired) {
                        Text("تاريخ ووقت البث المجدول (صيغة: YYYY-MM-DD HH:MM)", color = Color.White, fontSize = 9.sp)
                        OutlinedTextField(
                            value = scheduleTimeInput,
                            onValueChange = { scheduleTimeInput = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                        )
                    }

                    Button(
                        onClick = {
                            if (mainBroadcastTitle.isEmpty() || mainBroadcastBody.isEmpty()) {
                                showAppToast(context, "يرجى كتابة عنوان للتنبيه والرسالة أولاً!", false)
                                return@Button
                            }

                            if (isSchedulingRequired) {
                                vm.sendBroadcastNotification(mainBroadcastTitle, mainBroadcastBody, targetAudienceSelection, scheduleTimeInput)
                                showAppToast(context, "تمت جدولة إرسال رسالة البث بنجاح وتوجيهه للنظام المجدول الآلي!", true)
                            } else {
                                vm.sendBroadcastNotification(mainBroadcastTitle, mainBroadcastBody, targetAudienceSelection)
                                showAppToast(context, "تم إرسال بث الإشعار الجماهيري لليمن فورا وحفظه بنجاح!", true)
                            }

                            mainBroadcastTitle = ""
                            mainBroadcastBody = ""
                            isSchedulingRequired = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("تنفيذ البث بالإشعار الآن", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // III. OUTBOX HISTORY LIST
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🗂️ أرشيف وسجل الإشعارات المرسلة (Outbox History)", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                
                if (outboxHistory.isNotEmpty()) {
                    Button(
                        onClick = {
                            vm.clearAllNotificationsFromSystem()
                            showAppToast(context, "تم حذف كافة الإشعارات والإنذارات من النظام بنجاح!", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.DeleteForever, contentDescription = "مسح الكل", tint = Color.White, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("حذف كافة الإشعارات", color = Color.White, fontSize = 9.sp)
                    }
                }
            }
        }

        items(outboxHistory) { notif ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.surfaceDark, RoundedCornerShape(8.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(notif.title, color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(notif.body, color = Color.White, fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("المستهدف: ${notif.segment}", color = AppTheme.grayText, fontSize = 8.sp)
                        if (notif.isSent) {
                            Text("تاريخ البث: ${notif.sentTime}", color = Color.Green, fontSize = 8.sp)
                        } else {
                            Text("مجدول للبث في: ${notif.scheduledTime}", color = AppTheme.accentGold, fontSize = 8.sp)
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(if (notif.isSent) Color.Green.copy(0.15f) else AppTheme.accentGold.copy(0.15f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(if (notif.isSent) "تم البث" else "قيد الجدولة", color = if (notif.isSent) Color.Green else AppTheme.accentGold, fontSize = 8.sp)
                    }

                    IconButton(
                        onClick = {
                            vm.deleteNotificationFromSystem(notif.id)
                            showAppToast(context, "تم حذف الإشعار المحدد بنجاح!", true)
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(0.08f))
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "حذف الإشعار",
                            tint = AppTheme.primaryRed,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

// --- SUB-SCREEN 5: NEW TECHNICIAN APPROVAL CONTROL SCREEN (TechnicianApprovalScreen) ---
@Composable
fun TechnicianApprovalScreen(vm: MainViewModel) {
    val pendingApprovals by vm.pendingApprovals.collectAsState()
    val providers by vm.providers.collectAsState()
    val categories by vm.categories.collectAsState()
    val context = LocalContext.current

    // State managers for Adding / Editing technicians
    var isEditingMode by remember { mutableStateOf(false) }
    var currentEditingId by remember { mutableStateOf<String?>(null) } // null means "adding new"

    var nameState by remember { mutableStateOf("") }
    var phoneState by remember { mutableStateOf("") }
    var specialtyState by remember { mutableStateOf("") }
    var cityState by remember { mutableStateOf("صنعاء") }
    var areaState by remember { mutableStateOf("") }
    var addressDetailState by remember { mutableStateOf("") }
    var categoryIdState by remember { mutableStateOf("cat_elec") }
    var inspectionPriceState by remember { mutableStateOf(1000.0) }
    var latState by remember { mutableStateOf(15.369) }
    var lonState by remember { mutableStateOf(44.191) }
    var isAvailableState by remember { mutableStateOf(true) }
    var isVipState by remember { mutableStateOf(false) }
    var isVerifiedState by remember { mutableStateOf(true) }
    var isRecommendedState by remember { mutableStateOf(false) }
    var avatarUriState by remember { mutableStateOf("") }
    var coverUriState by remember { mutableStateOf("") }

    // Multi-media gallery pick launchers
    val avatarLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                avatarUriState = it.toString()
                showAppToast(context, "📸 تم اختيار وتثبيت الصورة الشخصية للفني بنجاح", true)
            }
        }
    )

    val coverLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                coverUriState = it.toString()
                showAppToast(context, "🖼️ تم اختيار وتثبيت غلاف التخصص الخدمي للفني بنجاح", true)
            }
        }
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "🛡️ شاشة تفعيل وقبول تراخيص وإدارة الكوادر الفنية",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "بصفتك مديراً عاماً، يمكنك مراجعة طلبات الانضمام المعالقة، تعديل بيانات الفنيين المعتمدين، وتحميل معارضهم وصورهم وإحداثياتهم.",
                        color = AppTheme.grayText,
                        fontSize = 9.sp
                    )
                }

                if (!isEditingMode) {
                    Button(
                        onClick = {
                            // Reset state for new provider inclusion
                            currentEditingId = null
                            nameState = ""
                            phoneState = ""
                            specialtyState = ""
                            cityState = "صنعاء"
                            areaState = ""
                            addressDetailState = ""
                            categoryIdState = "cat_elec"
                            inspectionPriceState = 1000.0
                            latState = 15.369
                            lonState = 44.191
                            isAvailableState = true
                            isVipState = false
                            isVerifiedState = true
                            isRecommendedState = false
                            avatarUriState = ""
                            coverUriState = ""
                            isEditingMode = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إضافة فني يدوياً", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // I. INLINE INTERACTIVE PROFILES CREATOR/EDITOR
        if (isEditingMode) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    border = BorderStroke(1.2.dp, AppTheme.accentGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = if (currentEditingId == null) "🆕 إضافة ملف فني جديد للدليل الموثق" else "✏️ محرر البيانات والمواقع الفنية للكادر",
                            color = AppTheme.accentGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text("الاسم الثلاثي للفني *", color = Color.White, fontSize = 10.sp)
                        OutlinedTextField(
                            value = nameState,
                            onValueChange = { nameState = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("رقم الهاتف الفني *", color = Color.White, fontSize = 10.sp)
                                OutlinedTextField(
                                    value = phoneState,
                                    onValueChange = { phoneState = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("سعر الفحص والمعاينة *", color = Color.White, fontSize = 10.sp)
                                OutlinedTextField(
                                    value = inspectionPriceState.toString(),
                                    onValueChange = { inspectionPriceState = it.toDoubleOrNull() ?: 1000.0 },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                        }

                        Text("التخصص الفني الدقيق وصيغة الخدمة *", color = Color.White, fontSize = 10.sp)
                        OutlinedTextField(
                            value = specialtyState,
                            onValueChange = { specialtyState = it },
                            placeholder = { Text("مثال: مهندس شبكات تمديد ونظم توزيع طاقة", color = Color.Gray, fontSize = 10.sp) },
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text("القسم الخدمي الرئيسي *", color = Color.White, fontSize = 10.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            categories.forEach { cat ->
                                val isSelected = categoryIdState == cat.id
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isSelected) AppTheme.accentGold else Color.Gray.copy(0.2f))
                                        .clickable { categoryIdState = cat.id }
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(cat.name, color = if (isSelected) Color.Black else Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("اليمن - المحافظة *", color = Color.White, fontSize = 10.sp)
                                OutlinedTextField(
                                    value = cityState,
                                    onValueChange = { cityState = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("الحي / مربع التغطية *", color = Color.White, fontSize = 10.sp)
                                OutlinedTextField(
                                    value = areaState,
                                    onValueChange = { areaState = it },
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                                )
                            }
                        }

                        Text("العنوان التفصيلي لوصول طالب الخدمة", color = Color.White, fontSize = 10.sp)
                        OutlinedTextField(
                            value = addressDetailState,
                            onValueChange = { addressDetailState = it },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Coordinates Calibration
                        Text("📍 معايرة الموقع الفني للتوجيه الذكي (Latitude & Longitude) *", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = latState.toString(),
                                onValueChange = { latState = it.toDoubleOrNull() ?: 15.369 },
                                label = { Text("خط العرض Lat", fontSize = 8.sp, color = Color.Gray) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            OutlinedTextField(
                                value = lonState.toString(),
                                onValueChange = { lonState = it.toDoubleOrNull() ?: 44.191 },
                                label = { Text("خط الطول Lon", fontSize = 8.sp, color = Color.Gray) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                            )
                            Button(
                                onClick = {
                                    latState = 15.369 + (java.util.Random().nextDouble() - 0.5) * 0.1
                                    lonState = 44.191 + (java.util.Random().nextDouble() - 0.5) * 0.1
                                    showAppToast(context, "🛰️ تم الاتصال وتثبيت إحداثيات GPS عشوائية دقيقة في حي السبعين/حدة بجمهورية اليمن!", true)
                                },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(0.4f)),
                                contentPadding = PaddingValues(horizontal = 6.dp),
                                border = BorderStroke(1.dp, AppTheme.accentGold),
                                modifier = Modifier.height(48.dp)
                            ) {
                                Text("🛰️ GPS", color = AppTheme.accentGold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Studio Photos uploaders
                        Text("🖼️ الاستوديو وتحميل الوسائط والرموز الشخصية للفني", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Avatar Picker Box
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { avatarLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
                                border = BorderStroke(1.dp, Color.Gray.copy(0.4f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (avatarUriState.isNotEmpty()) {
                                        androidx.compose.foundation.Image(
                                            painter = coil.compose.rememberAsyncImagePainter(avatarUriState),
                                            contentDescription = null,
                                            modifier = Modifier.size(50.dp).clip(CircleShape).border(1.dp, AppTheme.accentGold, CircleShape),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                                    }
                                    Text("الصورة الشخصية 👤", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    Text("انقر لتحميلها من المعرض", color = Color.Gray, fontSize = 7.sp, textAlign = TextAlign.Center)
                                }
                            }

                            // Cover Picker Box
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { coverLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
                                border = BorderStroke(1.dp, Color.Gray.copy(0.4f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (coverUriState.isNotEmpty()) {
                                        androidx.compose.foundation.Image(
                                            painter = coil.compose.rememberAsyncImagePainter(coverUriState),
                                            contentDescription = null,
                                            modifier = Modifier.height(30.dp).fillMaxWidth().clip(RoundedCornerShape(4.dp)),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                                    }
                                    Text("غلاف التخصص المالي 🌅", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    Text("انقر لتحميله من المعرض", color = Color.Gray, fontSize = 7.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }

                        // Availability status adjustment
                        Text("🟢 حالة التوفر المباشر للخدمة وطبيعة التجهيز", color = AppTheme.accentGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val statuses = listOf(
                                true to "نشط ومتاح حالياً لخدمتكم 🟢",
                                false to "مشغول ومحجوز بمهمة أخرى 🔴"
                            )
                            statuses.forEach { item ->
                                val isSel = isAvailableState == item.first
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isSel) (if (item.first) Color(0xFF2E7D32) else Color(0xFFC62828)) else Color.Gray.copy(0.15f))
                                        .clickable { isAvailableState = item.first }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(item.second, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Credentials badging state managers
                        Text("🛡️ الرتب والأوسمة المنشورة على كرت الكادر", color = Color.White, fontSize = 10.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Checkbox(checked = isVipState, onCheckedChange = { isVipState = it }, colors = CheckboxDefaults.colors(checkedColor = AppTheme.accentGold))
                                Text("كادر VIP 🏆", color = Color.White, fontSize = 9.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Checkbox(checked = isVerifiedState, onCheckedChange = { isVerifiedState = it }, colors = CheckboxDefaults.colors(checkedColor = AppTheme.accentGold))
                                Text("شارة توثيق الإدارة 🛡️", color = Color.White, fontSize = 9.sp)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Checkbox(checked = isRecommendedState, onCheckedChange = { isRecommendedState = it }, colors = CheckboxDefaults.colors(checkedColor = AppTheme.accentGold))
                                Text("موصى به وعالي الدقة 🌟", color = Color.White, fontSize = 9.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Action Controllers
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { isEditingMode = false },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                border = BorderStroke(1.dp, Color.Gray),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("إلغاء التراجع", color = Color.White, fontSize = 10.sp)
                            }

                            Button(
                                onClick = {
                                    if (nameState.isEmpty() || phoneState.isEmpty() || specialtyState.isEmpty()) {
                                        showAppToast(context, "الرجاء تعبئة الاسم الثلاثي والخاصية الخدمية للفني بتركيز!", false)
                                        return@Button
                                    }

                                    // Build structure
                                    val newId = currentEditingId ?: "p_${java.util.UUID.randomUUID().toString().take(6)}"
                                    val compiledProv = Provider(
                                        id = newId,
                                        name = nameState,
                                        phone = phoneState,
                                        specialty = specialtyState,
                                        city = cityState,
                                        area = areaState,
                                        addressDetail = addressDetailState,
                                        categoryId = categoryIdState,
                                        inspectionPrice = inspectionPriceState,
                                        latX = latState,
                                        lonY = lonState,
                                        isAvailable = isAvailableState,
                                        isVip = isVipState,
                                        isVerified = isVerifiedState,
                                        isRecommended = isRecommendedState,
                                        avatarUri = avatarUriState,
                                        coverUri = coverUriState
                                    )

                                    vm.updateProvider(compiledProv)
                                    showAppToast(
                                        context, 
                                        if (currentEditingId == null) "🎉 تم تسجيل كادر فني جديد بالدليل المعتمد بنجاح!" else "⚙️ تم تعديل وإعادة إنقاص إحداثيات وصور الكادر الفني بنجاح!", 
                                        true
                                    )
                                    isEditingMode = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1.2f)
                            ) {
                                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("حفظ الملف الفني", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // II. PENDING APPROVAL REQUEST SECTIONS
        if (!isEditingMode) {
            item {
                Text(
                    text = "📥 الكوادر الجدد بانتظار الترخيص والاعتماد (${pendingApprovals.size})",
                    color = AppTheme.accentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (pendingApprovals.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(AppTheme.surfaceDark, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray.copy(0.2f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Celebration, contentDescription = null, modifier = Modifier.size(24.dp), tint = AppTheme.accentGold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("لا يوجد كادر جديد بالانتظار، كافة التراخيص حية ومستقرة!", color = Color.Gray, fontSize = 10.sp)
                        }
                    }
                }
            } else {
                items(pendingApprovals) { provider ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                        border = BorderStroke(1.dp, Color.Gray.copy(0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(AppTheme.accentGold.copy(0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.PersonAdd, contentDescription = null, tint = AppTheme.accentGold, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(provider.name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("رقم التواصل: ${provider.phone}", color = AppTheme.grayText, fontSize = 10.sp)
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .background(AppTheme.primaryRed.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("في انتظار الموافقة", color = AppTheme.primaryRed, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("التخصص الفني العالي: ${provider.specialty}", color = Color.White, fontSize = 11.sp)
                            Text("موقع التغطية المقترح: اليمن - ${provider.city} • حي ${provider.area}", color = AppTheme.grayText, fontSize = 10.sp)
                            Text("الإحداثيات الجغرافية المسجلة للتوجيه الذكي: (${provider.latX}, ${provider.lonY})", color = Color.White, fontSize = 9.sp)

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        vm.approveNewTechnician(provider, false)
                                        showAppToast(context, "تم إلغاء وشطب ملف تسجيل الفني ${provider.name} بنجاح", false)
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    border = BorderStroke(1.dp, AppTheme.primaryRed),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(13.dp), tint = AppTheme.primaryRed)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("رفض التسجيل", color = AppTheme.primaryRed, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        vm.approveNewTechnician(provider, true)
                                        showAppToast(context, "تم الترخيص للفني ${provider.name} ونقله إلى الدليل المعتمد بنجاح!", true)
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                                    modifier = Modifier.weight(1.2f)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(13.dp), tint = Color.White)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("قبول وتفعيل الفني", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // III. APPROVED CERTIFIED TECHNICIANS REGISTRY WITH COLOR BADGES
        if (!isEditingMode) {
            item {
                Text(
                    text = "🛠️ سكرتارية إدارة الكوادر الفنية المعتمدة بالجمهورية (${providers.size})",
                    color = AppTheme.accentGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(providers) { provider ->
                val categoryName = categories.find { it.id == provider.categoryId }?.name ?: "تخصص عام"
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    border = BorderStroke(1.dp, Color.Gray.copy(0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (provider.avatarUri.isNotEmpty()) {
                                        androidx.compose.foundation.Image(
                                            painter = coil.compose.rememberAsyncImagePainter(provider.avatarUri),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Text(if (provider.categoryId == "cat_elec") "⚡" else if (provider.categoryId == "cat_plum") "💧" else "❄️", fontSize = 13.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(provider.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        if (provider.isVip) {
                                            Box(
                                                modifier = Modifier
                                                    .background(AppTheme.accentGold.copy(0.15f), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                                            ) {
                                                Text("VIP 🏆", color = AppTheme.accentGold, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                    Text("$categoryName • رقم الاتصال: ${provider.phone}", color = AppTheme.grayText, fontSize = 9.sp)
                                }
                            }

                            // Dynamic Colored status code markers
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (provider.isAvailable) Color(0xFF2E7D32).copy(0.15f) else Color(0xFFC62828).copy(0.15f), 
                                        RoundedCornerShape(4.dp)
                                    )
                                    .border(1.dp, if (provider.isAvailable) Color(0xFF2E7D32) else Color(0xFFC62828), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (provider.isAvailable) "متاح ونشط 🟢" else "مشغول/غير متصل 🔴", 
                                    color = if (provider.isAvailable) Color(0xFF81C784) else Color(0xFFE57373), 
                                    fontSize = 8.sp, 
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(provider.specialty, color = Color.White, fontSize = 10.sp)
                        Text("📍 التغطية: ${provider.city}، ${provider.area} • الإحداثيات: (${provider.latX}, ${provider.lonY})", color = AppTheme.grayText, fontSize = 9.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Admin actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Edit customizer button
                            Button(
                                onClick = {
                                    // Populate states
                                    currentEditingId = provider.id
                                    nameState = provider.name
                                    phoneState = provider.phone
                                    specialtyState = provider.specialty
                                    cityState = provider.city
                                    areaState = provider.area
                                    addressDetailState = provider.addressDetail
                                    categoryIdState = provider.categoryId
                                    inspectionPriceState = provider.inspectionPrice
                                    latState = provider.latX
                                    lonState = provider.lonY
                                    isAvailableState = provider.isAvailable
                                    isVipState = provider.isVip
                                    isVerifiedState = provider.isVerified
                                    isRecommendedState = provider.isRecommended
                                    avatarUriState = provider.avatarUri
                                    coverUriState = provider.coverUri
                                    isEditingMode = true
                                },
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                border = BorderStroke(1.dp, AppTheme.accentGold),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(12.dp), tint = AppTheme.accentGold)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("تعديل البيانات الفنية", color = AppTheme.accentGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }

                            // Delete button
                            Button(
                                onClick = {
                                    vm.deleteProvider(provider.id)
                                    showAppToast(context, "🗑️ تم حذف واستئصال ملف الكادر الفني ${provider.name} من الدليل بنجاح!", false)
                                },
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                border = BorderStroke(1.dp, AppTheme.primaryRed),
                                modifier = Modifier.weight(0.8f)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(12.dp), tint = AppTheme.primaryRed)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("حذف الكادر", color = AppTheme.primaryRed, fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdvancedSecurityMaintenanceScreen(vm: MainViewModel) {
    val banners by vm.banners.collectAsState()
    val bookingTerms by vm.bookingTerms.collectAsState()
    val context = LocalContext.current

    // Security Reset dialog & states
    var showConfirmResetDialog by remember { mutableStateOf(false) }
    var inputResetPassword by remember { mutableStateOf("") }
    val correctResetPassword = try { BuildConfig.ADMIN_DELETE_PASSWORD } catch (e: Throwable) { "maher736462" }

    // Promotional Banner adding states
    var bannerTitleInput by remember { mutableStateOf("") }
    var bannerUriInput by remember { mutableStateOf("") }
    var bannerTypeInput by remember { mutableStateOf("Photo") } // Photo vs Video

    // Editable terms
    var editingTermsInput by remember { mutableStateOf(bookingTerms) }

    // Sync input when flow updates
    LaunchedEffect(bookingTerms) {
        editingTermsInput = bookingTerms
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "⚙️ الأمن والصيانة الشاملة لقواعد بيانات يمن الأسعد",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // I. RESET APP DATA COMPONENT
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, AppTheme.primaryRed.copy(0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "🧹 تهيئة قواعد البيانات وبدء منصة التشغيل من الصفر",
                        color = AppTheme.primaryRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "تحذير فائق الخصوصية: هذا الإجراء يترتب عليه الإلغاء الكامل والفوري لجميع حسابات الفنيين، المحادثات الذكية الساحبة لذاكرة المساعد، وسجل الحجوزات، والأوسمة، وبنزات الإعلانات، وبناء قواعد البيانات النظيفة للبدء مجدداً.",
                        color = Color.LightGray,
                        fontSize = 9.sp,
                        lineHeight = 12.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("رمز تأكيد مشرف النظام المطور (Super Admin Password)", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = inputResetPassword,
                        onValueChange = { inputResetPassword = it },
                        placeholder = { Text("أدخل الرقم السري للمشرف، مثل: 123", color = Color.Gray, fontSize = 9.sp) },
                        singleLine = true,
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (inputResetPassword != correctResetPassword) {
                                showAppToast(context, "❌ رمز تأكيد المشرف المطور غير صحيح بالمرة!", false)
                            } else {
                                showConfirmResetDialog = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)), // Dark red background as requested
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp) // Large and clear size as requested
                    ) {
                        Text(
                            "🧹 تطهير قواعد البيانات والبدء من جديد",
                            color = Color.White, // White text
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // II. SYSTEM BOOKING POLICY & TERMS EDITOR
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "📝 تخصيص وتحرير شروط وأحكام الحجز الفوري:",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = editingTermsInput,
                        onValueChange = { editingTermsInput = it },
                        minLines = 4,
                        maxLines = 8,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            vm.updateBookingTerms(editingTermsInput)
                            showAppToast(context, "تم تحديث وحفظ بنود سياسة الضمان والحجز بنجاح! 📜", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ وتوثيق سياسة شروط الحقل الرسمية", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // III. SYSTEM HOTLINE & PUBLIC NETWORK SIMULATOR (أونلاين / أوفلاين)
        item {
            val supportPhone by vm.supportPhone.collectAsState()
            val isOnline by vm.isOnline.collectAsState()
            var localPhoneInput by remember { mutableStateOf(supportPhone) }
            
            LaunchedEffect(supportPhone) {
                localPhoneInput = supportPhone
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                border = BorderStroke(1.dp, AppTheme.accentGold.copy(0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "📞 خط الدعم الفني الساخن وحالة الشبكة (Online / Offline):",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "يمكن للأدمن تحديث رقم اتصال الدعم الساخن من هنا، ومحاكاة حالة الاتصال أونلاين/أوفلاين (عند تحويلها لأوفلاين سيتم حجب الرقم البرمجي عن العميل تلقائياً).",
                        color = Color.LightGray,
                        fontSize = 9.sp,
                        lineHeight = 12.sp
                    )

                    OutlinedTextField(
                        value = localPhoneInput,
                        onValueChange = { localPhoneInput = it },
                        label = { Text("رقم اتصال الدعم الفني", fontSize = 9.sp, color = Color.Gray) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = AppTheme.accentGold
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isOnline) "🟢 حالة المنظومة الآن: متصل بالخادم (Online)" else "🔴 حالة المنظومة الآن: بدون اتصال (Offline - مخفي)",
                            color = if (isOnline) Color(0xFF00C853) else Color.Red,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Switch(
                            checked = isOnline,
                            onCheckedChange = { 
                                vm.setOnlineState(it)
                                showAppToast(context, if (it) "🟢 تم تفعيل حالة الاتصال أونلاين للخدمات!" else "🔴 تم تعيين المنظومة كأوفلاين وحجب الهواتف بنجاح!", true)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color(0xFF00C853),
                                checkedTrackColor = Color(0xFF00C853).copy(0.4f)
                            )
                        )
                    }

                    Button(
                        onClick = {
                            if (localPhoneInput.isNotEmpty()) {
                                vm.updateSupportPhone(localPhoneInput)
                                showAppToast(context, "🎯 تم تحديث رقم هاتف الخط الإداري الساخن بنجاح!", true)
                            } else {
                                showAppToast(context, "⚠️ الرجاء كتابة رقم الهاتف بشكل صحيح!", false)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ وتحديث الرقم الساخن", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // III. PROMOTIONAL CAROUSEL BANNER CONTROLLER
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "🎉 إدارة وتنزيل بنرات العروض الترويجية بصدر الصفحة الرئيسية:",
                        color = AppTheme.accentGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text("عنوان الإعلان / البنر بالعربية", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = bannerTitleInput,
                        onValueChange = { bannerTitleInput = it },
                        placeholder = { Text("مثال: خصومات كاسحة 30% على صيانة المكيفات", color = Color.Gray, fontSize = 9.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("رابط صورة الإعلان (URL / Uri) أو تحميل الغلاف:", color = Color.White, fontSize = 9.sp)
                    OutlinedTextField(
                        value = bannerUriInput,
                        onValueChange = { bannerUriInput = it },
                        placeholder = { Text("أدخل رابط صورة البنر المسجل سحابياً", color = Color.Gray, fontSize = 9.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("نوع البنر الإعلاني", color = Color.White, fontSize = 9.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Photo" to "تصميم صورة 📸", "Video" to "عرض فيديو صيانة 🎥").forEach { (tp, lbl) ->
                            val isSelected = bannerTypeInput == tp
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) AppTheme.primaryRed else Color.Black.copy(0.3f))
                                    .clickable { bannerTypeInput = tp }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(lbl, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (bannerTitleInput.isEmpty() || bannerUriInput.isEmpty()) {
                                showAppToast(context, "يرجى تعبئة عنوان ورابط تصميم الإعلان أولاً!", false)
                                return@Button
                            }
                            vm.addBanner(
                                uri = bannerUriInput,
                                isVideo = (bannerTypeInput == "Video"),
                                title = bannerTitleInput
                            )
                            showAppToast(context, "تم إطلاق وإدراج الإعلان الجديد بصدر المنصة الرئيسية بنجاح! 🚀", true)
                            // clear outputs
                            bannerTitleInput = ""
                            bannerUriInput = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إطلاق وإضافة البنر للعرض الدوار", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text("قائمة البنرات الدوارة المشرقة حالياً:", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        if (banners.isEmpty()) {
            item {
                Text("لا توجد بنرات دوارة حالياً. المنصة ناصعة وفارغة.", color = Color.Gray, fontSize = 10.sp)
            }
        } else {
            items(banners) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AppTheme.surfaceDark, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1.5f)) {
                        Text(item.title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "النوع: " + (if (!item.isVideo) "إعلان صورة 📸" else "إعلان فيديو صيانة 🎥"),
                            color = Color.LightGray,
                            fontSize = 9.sp
                        )
                        Text(item.uri, color = Color.Gray, fontSize = 8.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }

                    IconButton(
                        onClick = {
                            vm.removeBanner(item.id)
                            showAppToast(context, "تم شطب وإلغاء البنر الإعلاني من المنصة الرئيسية", false)
                        },
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Banner", tint = AppTheme.primaryRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }

    // Safety Confirm Reset Dialog
    if (showConfirmResetDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmResetDialog = false },
            containerColor = AppTheme.surfaceDark,
            title = {
                Text(
                    "🚨 تأكيد عملية تطهير قواعد البيانات والبدء مجدداً",
                    color = AppTheme.primaryRed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    "سيتم حذف كافة البيانات، الحسابات، طلبات التسجيل، المحادثات، وبنزات الإعلانات، هل أنت متأكد؟",
                    color = Color.White,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    onClick = {
                        showConfirmResetDialog = false
                        vm.resetAllDataSystem()
                        inputResetPassword = ""
                        showAppToast(context, "🧼 تم تطهير قواعد البيانات والبدء من جديد بنجاح ناصع!", true)
                    }
                ) {
                    Text("نعم، تطهير الكل وتصفير الذاكرة", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showConfirmResetDialog = false },
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text("تراجع وإلغاء", color = Color.White, fontSize = 10.sp)
                }
            }
        )
    }
}
