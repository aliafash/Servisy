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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import java.text.SimpleDateFormat
import java.util.*

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
    val specialty: String = "",
    val rating: Double = 4.5,
    val city: String = "",
    val area: String = "",
    val categoryId: String = "",
    val isPinned: Boolean = false,
    val isVerified: Boolean = true,
    val latX: Double = 15.369, // Sana'a default
    val lonY: Double = 44.191
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
    val distributionModeUsed: String = ""
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
    val dropdownOptions: String = "" // comma separated values for Dropdown
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

    private val _notificationRules = MutableStateFlow<List<NotificationRule>>(emptyList())
    val notificationRules: StateFlow<List<NotificationRule>> = _notificationRules.asStateFlow()

    private val _sentNotifications = MutableStateFlow<List<ManualNotification>>(emptyList())
    val sentNotifications: StateFlow<List<ManualNotification>> = _sentNotifications.asStateFlow()

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(emptyList())
    val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

    // 5 Distribution Options
    // 1: الإرسال لمشرف القسم أولاً
    // 2: الإرسال مباشرة لأقرب فني (حسب الموقع)
    // 3: الإرسال لكل فنيي القسم (أول من يقبل يأخذ الحجز)
    // 4: الإرسال لفني محدد مسبقاً (الأدمن يعين فني لكل منطقة)
    // 5: الإرسال للأدمن أولاً (توزيع يدوي)
    private val _routingMode = MutableStateFlow(2) // Default to 2
    val routingMode: StateFlow<Int> = _routingMode.asStateFlow()

    // Cards custom styles variables managed by admin
    private val _cardStyleName = MutableStateFlow("Cairo") // "Cairo", "Tajawal", "Amiri", "SansSerif"
    val cardStyleName: StateFlow<String> = _cardStyleName.asStateFlow()

    private val _cardFontSize = MutableStateFlow(12)
    val cardFontSize: StateFlow<Int> = _cardFontSize.asStateFlow()

    private val _cardPadding = MutableStateFlow(8)
    val cardPadding: StateFlow<Int> = _cardPadding.asStateFlow()

    private val _cardCornerRadius = MutableStateFlow(12)
    val cardCornerRadius: StateFlow<Int> = _cardCornerRadius.asStateFlow()

    private val _pendingApprovals = MutableStateFlow<List<Provider>>(emptyList())
    val pendingApprovals: StateFlow<List<Provider>> = _pendingApprovals.asStateFlow()

    init {
        // Populate Categories
        _categories.value = listOf(
            Category("cat_elec", "كهرباء وصيانة منزلية"),
            Category("cat_plum", "سباكة وتمديدات صحية"),
            Category("cat_hvac", "تكييف وتبريد أجهزة كهربائية"),
            Category("cat_carp", "نجارة وأثاث وديكور")
        )

        // Populate initial Providers
        _providers.value = listOf(
            Provider("p1", "المهندس عادل الحمادي", "771234567", "أخصائي تركيب وتبريد مكيفات هواء عملاقة", 4.9, "صنعاء", "حدة", "cat_hvac", true),
            Provider("p2", "فني السباكة ماهر الأبارة", "735112233", "صيانة حمامات ومضخات وشبكات مياه ذكية", 4.7, "عدن", "المنصورة", "cat_plum", false),
            Provider("p3", "م. سليم الصنعاني للكهرباء", "770998877", "تمديد شبكات كهرباء وتحكم وفحص القصر الكهربائي", 4.8, "صنعاء", "السبعين", "cat_elec", true),
            Provider("p4", "فواز للنجارة الحديثة", "711222333", "صيانة الأثاث الخشبي والمطابخ وصناعة غرف نوم راقية", 4.5, "تعز", "الحوبان", "cat_carp", false)
        )

        // Populate initial pending registrations for validation task
        _pendingApprovals.value = listOf(
            Provider("p_new1", "الفني بسام الوشلي", "773344556", "تأسيس وإنشاء شبكات تمديدات كهربائية متكاملة", 4.4, "صنعاء", "حي الروضة", "cat_elec", false, isVerified = false),
            Provider("p_new2", "فؤاد الغراسي للسباكة", "712211332", "إنقاذ فوري سباكة منزلي وتعديل خلاطات مياه", 4.3, "صنعاء", "الدائري", "cat_plum", false, isVerified = false)
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

        _sentNotifications.value = listOf(
            ManualNotification(UUID.randomUUID().toString(), "تنفيذ عروض صيانة الصيف", "خصومات كبرى تصل 30% على خدمات المكيفات", "All", "", "2026-06-15 08:30"),
            ManualNotification(UUID.randomUUID().toString(), "تحديث فني هام لكهربائيي الأمانة", "الرجاء المداومة على تحديث حالات الحجوزات فوراً", "Sana'a Techs", "", "2026-06-15 09:12")
        )

        _auditLogs.value = listOf(
            AuditLog(UUID.randomUUID().toString(), System.currentTimeMillis() - 7200000, "Admin", "تسجيل دخول كمسؤول", "تم الدخول بنجاح من جهاز الآدمن الرئيسي"),
            AuditLog(UUID.randomUUID().toString(), System.currentTimeMillis() - 3600000, "Supervisor", "توزيع حجز", "المشرف وزع الطلب رقم 'B-998' للفني عادل الحمادي"),
            AuditLog(UUID.randomUUID().toString(), System.currentTimeMillis() - 1800000, "Admin", "تحديث أسلوب توزيع", "تم تبديل خيار توزيع الحجز التلقائي إلى 'أقرب فني'")
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
}

// --- MAIN SCREEN ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isUserRole by remember { mutableStateOf(true) }
            val vm: MainViewModel = viewModel()

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.darkBg
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Custom Status Bar / Header
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
                                    .clickable { isUserRole = !isUserRole }
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

                        // App Content based on Role Switcher
                        if (isUserRole) {
                            UserWorkspace(vm)
                        } else {
                            AdminDashboardWorkspace(vm)
                        }
                    }
                }
            }
        }
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

// --- CUSTOM SERVICE PROVIDER CARD DISPLAYING DATA ---
@Composable
fun ServiceProviderCard(
    provider: Provider,
    categoryName: String,
    cardFontName: String,
    cardFontSizeSp: Int,
    cardPaddingDp: Int,
    cardCornerRadiusDp: Int,
    onBookClick: () -> Unit,
    onCallClick: () -> Unit
) {
    val context = LocalContext.current
    val customFont = getFontFamilyByName(cardFontName)

    Card(
        shape = RoundedCornerShape(cardCornerRadiusDp.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
        border = BorderStroke(1.dp, if (provider.isPinned) AppTheme.accentGold else Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(cardPaddingDp.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Simulated Avatar with Quick Dial floating action
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.3f))
                            .border(1.dp, AppTheme.accentGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Engineering,
                            contentDescription = "Avatar",
                            tint = AppTheme.accentGold,
                            modifier = Modifier.size(28.dp)
                        )
                        // Floating dial on avatar
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(Color(0xFF2E7D32))
                                .clickable { onCallClick() }
                                .border(1.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Quick Call",
                                tint = Color.White,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = provider.name,
                                color = Color.White,
                                fontSize = cardFontSizeSp.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = customFont
                            )
                            if (provider.isPinned) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(AppTheme.accentGold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .border(0.5.dp, AppTheme.accentGold, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text("متميز ⭐", color = AppTheme.accentGold, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text(
                            text = "$categoryName • ${provider.specialty}",
                            color = AppTheme.grayText,
                            fontSize = (cardFontSizeSp - 2).coerceAtLeast(8).sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = customFont
                        )
                    }
                }

                // Rating Box
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Black.copy(0.2f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = AppTheme.accentGold,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = provider.rating.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Details/Location row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = AppTheme.primaryRed,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "اليمن - ${provider.city}، ${provider.area}",
                        color = AppTheme.grayText,
                        fontSize = 10.sp,
                        fontFamily = customFont
                    )
                }

                Text(
                    text = "رقم الهاتف: ${provider.phone}",
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = customFont
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Dial Button
                OutlinedButton(
                    onClick = onCallClick,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, AppTheme.accentGold),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.accentGold)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("اتصال مباشر", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // Booking request button
                Button(
                    onClick = onBookClick,
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                    modifier = Modifier.weight(1.2f)
                ) {
                    Icon(Icons.Default.Event, contentDescription = null, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("حجز فوري ومباشر", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- PRIMARY CLIENT WORKSPACE VIEW ---
@Composable
fun UserWorkspace(vm: MainViewModel) {
    val providers by vm.providers.collectAsState()
    val categories by vm.categories.collectAsState()
    val formFields by vm.formFields.collectAsState()

    val cardFontName by vm.cardStyleName.collectAsState()
    val cardFontSize by vm.cardFontSize.collectAsState()
    val cardPadding by vm.cardPadding.collectAsState()
    val cardCornerRadius by vm.cardCornerRadius.collectAsState()

    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var showBookingDialogFor by remember { mutableStateOf<Provider?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // Search & Category Bar Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("ابحث عن فني كهرباء، سباكة، مكيفات...", color = Color.Gray, fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = AppTheme.accentGold) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = AppTheme.accentGold,
                    unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Categories selector horizontally
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

        Spacer(modifier = Modifier.height(10.dp))

        // Title listing professional technicians
        Text(
            text = "الفنيين المتوفرين حالياً صيانة فورية ⚡",
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Filter algorithm
        val filteredProviders = providers.filter { p ->
            val matchCategory = selectedCategoryFilter == "All" || p.categoryId == selectedCategoryFilter
            val matchSearch = searchQuery.isEmpty() || p.name.contains(searchQuery) || p.specialty.contains(searchQuery) || p.city.contains(searchQuery) || p.area.contains(searchQuery)
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
                    Icon(Icons.Default.HourglassEmpty, contentDescription = null, size = 48.dp, tint = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("عذراً، لم يعثر على فنيين يطابقون خيارات البحث", color = Color.Gray, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Show pinned items first
                val sortedProviders = filteredProviders.sortedByDescending { it.isPinned }
                items(sortedProviders) { prov ->
                    val catName = categories.find { it.id == prov.categoryId }?.name ?: "خدمة عامة"
                    ServiceProviderCard(
                        provider = prov,
                        categoryName = catName,
                        cardFontName = cardFontName,
                        cardFontSizeSp = cardFontSize,
                        cardPaddingDp = cardPadding,
                        cardCornerRadiusDp = cardCornerRadius,
                        onCallClick = {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${prov.phone}")
                            }
                            context.startActivity(intent)
                        },
                        onBookClick = {
                            showBookingDialogFor = prov
                        }
                    )
                }
            }
        }
    }

    // --- DIALOG FOR DYNAMIC BOOKING FORM CREATION ---
    showBookingDialogFor?.let { prov ->
        var clientName by remember { mutableStateOf("") }
        var clientPhone by remember { mutableStateOf("") }
        var clientRegion by remember { mutableStateOf("") }
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
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Universal Basic Fields
                    Text("الاسم الكريم لقاصد الخدمة *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("رقم الموبايل للمتابعة الفورية *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    Text("مديرية / حي السكن الفعلي باليمن *", color = Color.White, fontSize = 11.sp)
                    OutlinedTextField(
                        value = clientRegion,
                        onValueChange = { clientRegion = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )

                    // Configurable fields injected dynamically by Admin Controller
                    formFields.forEach { field ->
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

// --- SECURE WORKSPACE FOR ADMINISTRATOR CONTROL LEVEL ---
@Composable
fun AdminDashboardWorkspace(vm: MainViewModel) {
    var activeSubTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        "التقارير والإحصائيات 📊",
        "مسارات وتوجيه الحجوزات 🗺️",
        "استمارة الحجز والبطاقات 📇",
        "إشعارات وإدارة التنبيهات 📢",
        "تراخيص وقبول الفنيين 🛡️"
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

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Screen Header title
            Text(
                text = "📊 اللوحة البيانية الشاملة والنشاط الموثق",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
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
            val matchRegion = selectedReportRegion.isEmpty() || b.userArea.contains(selectedReportRegion)
            matchCat && matchStatus && matchRegion
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
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("⏰ تقرير: أوقات الذروة والمواسم المفضلة", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Peak Hour representation (Morning, Afternoon, Evening)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val peaks = listOf(
                            Triple("فترة الصباح 🌅", "8 صباحاً - 12 ظهراً", 45),
                            Triple("فترة بعد الظهر ☀️", "12 ظهراً - 5 عصراً", 85),
                            Triple("الفترة المسائية 🌙", "5 عصراً - 10 مساءً", 62)
                        )

                        peaks.forEach { (title, range, bookingCount) ->
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
                                Text("$bookingCount حجز", color = AppTheme.accentGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
                        Text("الطلب: ${booking.id}", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        
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

    val context = LocalContext.current

    // State for temporary fields parameters
    var newFieldLabel by remember { mutableStateOf("") }
    var newFieldType by remember { mutableStateOf("Text") }
    var isNewFieldRequired by remember { mutableStateOf(false) }
    var newFieldDropdownOptions by remember { mutableStateOf("") }

    // State for Admin configuration of Aesthetic Cards
    var inputFontName by remember { mutableStateOf(cardStyleName) }
    var inputFontSize by remember { mutableStateOf(cardFontSize) }
    var inputPadding by remember { mutableStateOf(cardPadding) }
    var inputCornerRadius by remember { mutableStateOf(cardCornerRadius) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "🔧 أدوات تحرير استمارة حجز الكوادر وبطاقة العرض",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // I. THE CARD DESIGN EDITOR
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("🎨 تخصيص وتصميم شكل وحجم خطوط بطاقات مقدم الخدمة", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    // Font Selection
                    Text("نمط خط العرض العربي بالبطاقة", color = Color.White, fontSize = 9.sp)
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
                    Text("حجم الخط لعناوين البطاقة: ${inputFontSize}sp", color = Color.White, fontSize = 9.sp)
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
                            Text("هوامش البطاقة الداخلي: ${inputPadding}dp", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = inputPadding.toFloat(),
                                onValueChange = { inputPadding = it.toInt() },
                                valueRange = 4f..16f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("استدارة زوايا الكرت: ${inputCornerRadius}dp", color = Color.White, fontSize = 9.sp)
                            Slider(
                                value = inputCornerRadius.toFloat(),
                                onValueChange = { inputCornerRadius = it.toInt() },
                                valueRange = 0f..24f,
                                colors = SliderDefaults.colors(thumbColor = AppTheme.accentGold)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            vm.updateCardStyle(inputFontName, inputFontSize, inputPadding, inputCornerRadius)
                            showAppToast(context, "تم حفظ وتحديث الهوية البصرية للبطاقات المهنية بنجاح!", true)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ وتعديل حجم وشكل وتصميم البطاقة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                    Text("📋 إضافة وتطوير حقول جديدة في استمارة حجز المستخدم", color = AppTheme.accentGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)

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
                                id = "field_" + System.currentTimeMillis(),
                                label = newFieldLabel,
                                type = newFieldType,
                                isRequired = isNewFieldRequired,
                                dropdownOptions = newFieldDropdownOptions
                            )

                            vm.modifyField(addedField)
                            showAppToast(context, "تم إلحاق وحفظ الحقل الإداري الجديد في استمارة الحجز بنجاح!", true)

                            // Clear entries
                            newFieldLabel = ""
                            newFieldDropdownOptions = ""
                            isNewFieldRequired = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إدراج الحقل المخصص في الاستمارة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
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
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(field.label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
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
                    }
                    Text("نوع ميزة ترميز الحقل: ${field.type}", color = AppTheme.grayText, fontSize = 9.sp)
                    if (field.dropdownOptions.isNotEmpty()) {
                        Text("الخيارات: ${field.dropdownOptions}", color = Color.LightGray, fontSize = 9.sp)
                    }
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

// --- SUB-SCREEN 4: SYSTEM NOTIFICATIONS HUB AND BROADCASTS CONTROLS ---
@Composable
fun NotificationControlCentre(vm: MainViewModel) {
    val rules by vm.notificationRules.collectAsState()
    val outboxHistory by vm.sentNotifications.collectAsState()

    val context = LocalContext.current

    var mainBroadcastTitle by remember { mutableStateOf("") }
    var mainBroadcastBody by remember { mutableStateOf("") }
    var targetAudienceSelection by remember { mutableStateOf("All") }

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
            Text("🗂️ أرشيف وسجل الإشعارات المرسلة (Outbox History)", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
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

                Box(
                    modifier = Modifier
                        .background(if (notif.isSent) Color.Green.copy(0.15f) else AppTheme.accentGold.copy(0.15f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(if (notif.isSent) "تم البث" else "قيد الجدولة", color = if (notif.isSent) Color.Green else AppTheme.accentGold, fontSize = 8.sp)
                }
            }
        }
    }
}

// --- SUB-SCREEN 5: NEW TECHNICIAN APPROVAL CONTROL SCREEN (TechnicianApprovalScreen) ---
@Composable
fun TechnicianApprovalScreen(vm: MainViewModel) {
    val pendingApprovals by vm.pendingApprovals.collectAsState()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = "🛡️ شاشة تفعيل وقبول تراخيص الفنيين الجدد",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "بصفتك مديراً عاماً، يمكنك مراجعة طلبات الانضمام المهنية ومنحهم الموافقة أو الرفض للظهور بدليل كل خدمات اليمن.",
                color = AppTheme.grayText,
                fontSize = 10.sp
            )
        }

        if (pendingApprovals.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Celebration, contentDescription = null, size = 32.dp, tint = AppTheme.accentGold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("لا يوجد فنيين بانتظار الترخيص حالياً. كافة الملفات مدققة مسبقاً!", color = Color.Gray, fontSize = 11.sp, textAlign = TextAlign.Center)
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

                        // Specialty & Location Details
                        Text("التخصص الفني العالي: ${provider.specialty}", color = Color.White, fontSize = 11.sp)
                        Text("موقع التغطية المقترح: اليمن - ${provider.city} • حي ${provider.area}", color = AppTheme.grayText, fontSize = 10.sp)
                        Text("الإحداثيات الجغرافية المسجلة للتوجيه الذكي: (${provider.latX}, ${provider.lonY})", color = Color.White, fontSize = 9.sp)

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action button approvals and rejectings
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Reject Button
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

                            // Accept button approving status in Firebase/VM index
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
}
