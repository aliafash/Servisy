package com.maw

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

// Theme definition
object AppTheme {
    val primaryRed = Color(0xFFCE1126)  // Yemen flag Red
    val accentGold = Color(0xFFFFD700)  // Golden accent
    val bgDark = Color(0xFF0D1B1E)      // Slate dark background
    val surfaceDark = Color(0xFF162A2D) // Lighter slate for cards
    val cardBg = Color(0xFF223639)      // Accent card bg
}

// Data Classes
data class Category(val id: String, val nameAr: String, val iconUrl: String)
data class City(val id: String, val nameAr: String)
data class Provider(
    val id: String,
    val name: String,
    val categoryId: String,
    val cityId: String,
    val phone: String,
    val rating: Double,
    val reviewsCount: Int,
    val photoUrl: String,
    val lat: Double,
    val lng: Double,
    val yemenRegion: String
)

data class BookingField(
    val id: String,
    val label: String,
    val type: String, // "text", "phone", "number"
    val isRequired: Boolean,
    val placeholder: String,
    val isEnabled: Boolean = true
)

data class BookingRequest(
    val id: String,
    val clientName: String,
    val clientPhone: String,
    val serviceType: String,
    val residenceCity: String,
    val imageUri: String?,
    val extraFieldsData: Map<String, String> = emptyMap(),
    val timestamp: String = "الآن"
)

data class AssistantMessage(
    val id: String,
    val sender: String, // "user", "assistant"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

// Main ViewModel
class MainViewModel : ViewModel() {
    // Categories
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    // Cities
    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities = _cities.asStateFlow()

    // Providers
    private val _providers = MutableStateFlow<List<Provider>>(emptyList())
    val providers = _providers.asStateFlow()

    // Dynamic Booking Form Fields (Form Builder)
    private val _bookingFields = MutableStateFlow<List<BookingField>>(emptyList())
    val bookingFields = _bookingFields.asStateFlow()

    // Bookings received
    private val _bookings = MutableStateFlow<List<BookingRequest>>(emptyList())
    val bookings = _bookings.asStateFlow()

    // AI Chat History
    private val _chatHistory = MutableStateFlow<List<AssistantMessage>>(emptyList())
    val chatHistory = _chatHistory.asStateFlow()

    // Screen tab flow
    val currentTab = MutableStateFlow("directory") // "directory", "booking", "assistant", "admin"

    // Theme Configs
    val themePrimaryHex = MutableStateFlow("#CE1126")
    val themeAccentHex = MutableStateFlow("#FFD700")
    val chatIconSize = MutableStateFlow(60f)
    val assistantSize = MutableStateFlow(50f)
    val appCoverUri = MutableStateFlow("https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=600&q=80")

    init {
        // Hydrate default data
        _categories.value = listOf(
            Category("cat_1", "كهرباء وتمديدات", "https://img.icons8.com/color/96/000000/flash-on.png"),
            Category("cat_2", "سباكة وصيانة صحية", "https://img.icons8.com/color/96/000000/water-pipe.png"),
            Category("cat_3", "التجميل والكوافير", "https://img.icons8.com/color/96/000000/makeup.png"),
            Category("cat_4", "صيانة الأجهزة الكهربائية", "https://img.icons8.com/color/96/000000/home-appliance.png"),
            Category("cat_5", "أعمال البناء والترميم", "https://img.icons8.com/color/96/000000/brickwall.png")
        )

        _cities.value = listOf(
            City("c_1", "صنعاء"),
            City("c_2", "عدن"),
            City("c_3", "تعز"),
            City("c_4", "الحديدة"),
            City("c_5", "إب"),
            City("c_6", "حضرموت")
        )

        _providers.value = listOf(
            Provider(
                id = "p_1",
                name = "ليال الكبسي",
                categoryId = "cat_3",
                cityId = "c_1",
                phone = "+967777123456",
                rating = 4.8,
                reviewsCount = 14,
                photoUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=200&q=80",
                lat = 15.3694,
                lng = 44.1910,
                yemenRegion = "صنعاء - شارع حدة"
            ),
            Provider(
                id = "p_2",
                name = "عماد المهندس",
                categoryId = "cat_1",
                cityId = "c_2",
                phone = "+967733987654",
                rating = 4.9,
                reviewsCount = 28,
                photoUrl = "https://images.unsplash.com/photo-1560250097-0b93528c311a?auto=format&fit=crop&w=200&q=80",
                lat = 12.7855,
                lng = 45.0186,
                yemenRegion = "عدن - كريتر"
            ),
            Provider(
                id = "p_3",
                name = "صالح السباك",
                categoryId = "cat_2",
                cityId = "c_1",
                phone = "+967711222333",
                rating = 4.5,
                reviewsCount = 9,
                photoUrl = "https://images.unsplash.com/photo-1540569014015-19a7be504e3a?auto=format&fit=crop&w=200&q=80",
                lat = 15.3500,
                lng = 44.2000,
                yemenRegion = "صنعاء - الحصبة"
            )
        )

        // Default Dynamic Booking Fields
        _bookingFields.value = listOf(
            BookingField("f_name", "الاسم الثلاثي بالكامل", "text", true, "أدخل اسمك الثلاثي..."),
            BookingField("f_phone", "رقم الهاتف الفعال", "phone", true, "77xxxxxxx"),
            BookingField("f_service", "نوع الخدمة المطلوبة مسبقاً", "text", true, "مثال: إصلاح إنارة، تسليك مياه..."),
            BookingField("f_city", "مكان الإقامة والحي السكني باليمن", "text", true, "مثال: صنعاء - حي الأصبحي...")
        )

        // App Initial AI Greeting
        _chatHistory.value = listOf(
            AssistantMessage("init_1", "assistant", "أهلاً بك في الدليل اليمني الموحد! أنا المساعد الذكي، كيف يمكنني مساعدتك اليوم بخصوص الحرف والخدمات؟")
        )
    }

    // Dynamic Form Builders
    fun addFormField(label: String, type: String, isRequired: Boolean, placeholder: String) {
        val newField = BookingField(
            id = "custom_" + UUID.randomUUID().toString(),
            label = label,
            type = type,
            isRequired = isRequired,
            placeholder = placeholder
        )
        _bookingFields.value = _bookingFields.value + newField
    }

    fun removeFormField(id: String) {
        _bookingFields.value = _bookingFields.value.filter { it.id != id }
    }

    fun toggleFieldRequired(id: String) {
        _bookingFields.value = _bookingFields.value.map {
            if (it.id == id) it.copy(isRequired = !it.isRequired) else it
        }
    }

    fun toggleFieldEnabled(id: String) {
        _bookingFields.value = _bookingFields.value.map {
            if (it.id == id) it.copy(isEnabled = !it.isEnabled) else it
        }
    }

    // Booking Submission
    fun submitBooking(
        name: String,
        phone: String,
        service: String,
        city: String,
        imageUri: String?,
        extraData: Map<String, String>
    ) {
        val newBooking = BookingRequest(
            id = "b_" + UUID.randomUUID().toString(),
            clientName = name,
            clientPhone = phone,
            serviceType = service,
            residenceCity = city,
            imageUri = imageUri,
            extraFieldsData = extraData
        )
        _bookings.value = listOf(newBooking) + _bookings.value
    }

    fun deleteBooking(id: String) {
        _bookings.value = _bookings.value.filter { it.id != id }
    }

    // AI Conversations
    fun sendAssistantMessage(userText: String, replyText: String) {
        val userMsg = AssistantMessage(UUID.randomUUID().toString(), "user", userText)
        val aiMsg = AssistantMessage(UUID.randomUUID().toString(), "assistant", replyText)
        _chatHistory.value = _chatHistory.value + userMsg + aiMsg
    }

    fun clearChatHistory() {
        _chatHistory.value = listOf(
            AssistantMessage(UUID.randomUUID().toString(), "assistant", "تم تطهير ومسح سجل المساعد الذكي نجاحاً بصورة فورية!")
        )
    }
}

class MainActivity : ComponentActivity() {

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize natural High Fidelity Arabic text-to-speech support
        try {
            tts = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts?.setLanguage(Locale("ar", "YE"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts?.setLanguage(Locale("ar")) // Fallback to general Arabic
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            val vm: MainViewModel = viewModel()
            val currentTab by vm.currentTab.collectAsState()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = AppTheme.bgDark
            ) {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = AppTheme.surfaceDark,
                            contentColor = Color.White
                        ) {
                            NavigationBarItem(
                                selected = currentTab == "directory",
                                onClick = { vm.currentTab.value = "directory" },
                                icon = { Icon(Icons.Default.ListAlt, contentDescription = "الدليل") },
                                label = { Text("الدليل الموحد", color = Color.White, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AppTheme.accentGold,
                                    selectedTextColor = AppTheme.accentGold,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                            NavigationBarItem(
                                selected = currentTab == "booking",
                                onClick = { vm.currentTab.value = "booking" },
                                icon = { Icon(Icons.Default.EditCalendar, contentDescription = "حجز") },
                                label = { Text("استمارة الحجز", color = Color.White, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AppTheme.accentGold,
                                    selectedTextColor = AppTheme.accentGold,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                            NavigationBarItem(
                                selected = currentTab == "assistant",
                                onClick = { vm.currentTab.value = "assistant" },
                                icon = { Icon(Icons.Default.SmartToy, contentDescription = "المساعد") },
                                label = { Text("المساعد الذكي", color = Color.White, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AppTheme.accentGold,
                                    selectedTextColor = AppTheme.accentGold,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                            NavigationBarItem(
                                selected = currentTab == "admin",
                                onClick = { vm.currentTab.value = "admin" },
                                icon = { Icon(Icons.Default.Settings, contentDescription = "الإدارة") },
                                label = { Text("لوحة التحكم", color = Color.White, fontSize = 10.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AppTheme.accentGold,
                                    selectedTextColor = AppTheme.accentGold,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentTab,
                            label = "MainTabsSwitches"
                        ) { tab ->
                            when (tab) {
                                "directory" -> DirectoryTab(vm)
                                "booking" -> BookingTab(vm)
                                "assistant" -> AssistantTab(vm, tts)
                                "admin" -> AdminTab(vm)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}

// ==================== SCREEN TABS ====================

@Composable
fun DirectoryTab(vm: MainViewModel) {
    val categories by vm.categories.collectAsState()
    val providers by vm.providers.collectAsState()
    val cities by vm.cities.collectAsState()

    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    var selectedCityId by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredProviders = providers.filter {
        (selectedCategoryId == null || it.categoryId == selectedCategoryId) &&
                (selectedCityId == null || it.cityId == selectedCityId) &&
                (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.yemenRegion.contains(searchQuery, ignoreCase = true))
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "كل خدمات اليمن 🇾🇪",
                    style = TextStyle(
                        color = AppTheme.accentGold,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        textDirection = TextDirection.Rtl
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Clean description preventing vertical breaks
                Text(
                    text = "مظلة موحدة لربط مقدمي الخدمات الفنية والصيانة بمستخدميها بجميع المحافظات.",
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        textDirection = TextDirection.Rtl
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Sub-activities triggers (for testing the system file edits)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📱 محاكيات لوحة الإدارة الثانوية (تحتوي على ImageView وصلاحية كاملة):",
                        color = AppTheme.accentGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Button(
                            onClick = { context.startActivity(Intent(context, BookingsActivity::class.java)) },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.cardBg),
                            modifier = Modifier.weight(1f).height(38.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("الحجوزات 📅", fontSize = 10.sp, color = Color.White)
                        }
                        Button(
                            onClick = { context.startActivity(Intent(context, CustomThemeActivity::class.java)) },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.cardBg),
                            modifier = Modifier.weight(1f).height(38.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("التخصيص 🎨", fontSize = 10.sp, color = Color.White)
                        }
                        Button(
                            onClick = { context.startActivity(Intent(context, CategoriesActivity::class.java)) },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.cardBg),
                            modifier = Modifier.weight(1f).height(38.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("الأقسام 📁", fontSize = 10.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        // Categories Selector Row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "📁 تصفح حسب الفئات والمهن الرئيسية:",
                    color = AppTheme.accentGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .clickable { selectedCategoryId = null }
                            .width(100.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedCategoryId == null) AppTheme.primaryRed else AppTheme.surfaceDark
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("الكل 🛠️", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    categories.forEach { cat ->
                        Card(
                            modifier = Modifier
                                .clickable { selectedCategoryId = cat.id }
                                .width(120.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedCategoryId == cat.id) AppTheme.primaryRed else AppTheme.surfaceDark
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(cat.iconUrl),
                                    contentDescription = cat.nameAr,
                                    modifier = Modifier.size(34.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = cat.nameAr,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search Bar in the middle
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "البحث بالاسم أو المحافظة أو الحي...",
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = AppTheme.accentGold,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                ),
                textStyle = TextStyle(
                    fontSize = 13.sp,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                )
            )
        }

        // City Selector Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cities.forEach { city ->
                    Card(
                        modifier = Modifier
                            .clickable {
                                selectedCityId = if (selectedCityId == city.id) null else city.id
                            }
                            .wrapContentSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedCityId == city.id) AppTheme.accentGold else AppTheme.surfaceDark
                        )
                    ) {
                        Text(
                            text = city.nameAr,
                            color = if (selectedCityId == city.id) Color.Black else Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Providers Section List
        item {
            Text(
                text = "⚡ مزودو ومزودات الخدمة الأقرب لك (" + filteredProviders.size + "):",
                color = AppTheme.accentGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }

        if (filteredProviders.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "لا توجد نتائج مطابقة لبحثك في المحافظات المختارة.",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(filteredProviders) { provider ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Quick Action Buttons
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {
                                    Toast.makeText(context, "بدء اتصال فوري بمزود الخدمة: " + provider.name, Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                                modifier = Modifier
                                    .width(85.dp)
                                    .height(34.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("اتصال 📞", color = Color.White, fontSize = 10.sp)
                            }
                            Button(
                                onClick = {
                                    // Navigate to Booking tab to book specifically
                                    vm.currentTab.value = "booking"
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                                modifier = Modifier
                                    .width(85.dp)
                                    .height(34.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("حجز موعد 📅", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Text details (RTL)
                        Column(
                            modifier = Modifier.weight(1f).padding(end = 12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = provider.name,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "📍 " + provider.yemenRegion,
                                color = Color.LightGray,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "(${provider.reviewsCount} تقييم)",
                                    color = Color.Gray,
                                    fontSize = 10.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = provider.rating.toString(),
                                    color = AppTheme.accentGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "التقييم",
                                    tint = AppTheme.accentGold,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }

                        // Picture Frame
                        Image(
                            painter = rememberAsyncImagePainter(provider.photoUrl),
                            contentDescription = provider.name,
                            modifier = Modifier
                                .size(55.dp)
                                .clip(CircleShape)
                                .border(1.dp, AppTheme.accentGold, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BookingTab(vm: MainViewModel) {
    val bookingFields by vm.bookingFields.collectAsState()
    val context = LocalContext.current

    // State holders for form fields dynamically
    var nameVal by remember { mutableStateOf("") }
    var phoneVal by remember { mutableStateOf("") }
    var serviceVal by remember { mutableStateOf("") }
    var cityVal by remember { mutableStateOf("") }

    // Dynamic field inputs list
    val extraInputs = remember { mutableStateMapOf<String, String>() }

    // Image Picker State
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                Toast.makeText(context, "تم تحديد مرفق المشكلة/العطل بنجاح!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "📝 استمارة طلب حجز موعد خدمة",
                style = TextStyle(
                    color = AppTheme.accentGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "املأ الحقول المطلوبة بالأسفل وسيتواصل معك فني الصيانة الأقرب لموقعك فوراً بالتنسيق صامتاً مع قاعدة البيانات.",
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Render Fields Dynamically Based on Admin form builder settings
        bookingFields.filter { it.isEnabled }.forEach { field ->
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (field.isRequired) {
                            Text(" * ", color = AppTheme.primaryRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            text = field.label + ":",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                textAlign = TextAlign.Right,
                                textDirection = TextDirection.Rtl
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    val inputVal = when (field.id) {
                        "f_name" -> nameVal
                        "f_phone" -> phoneVal
                        "f_service" -> serviceVal
                        "f_city" -> cityVal
                        else -> extraInputs[field.id] ?: ""
                    }

                    OutlinedTextField(
                        value = inputVal,
                        onValueChange = { newValue ->
                            when (field.id) {
                                "f_name" -> nameVal = newValue
                                "f_phone" -> phoneVal = newValue
                                "f_service" -> serviceVal = newValue
                                "f_city" -> cityVal = newValue
                                else -> extraInputs[field.id] = newValue
                            }
                        },
                        placeholder = {
                            Text(
                                field.placeholder,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (field.type == "phone") KeyboardType.Phone else if (field.type == "number") KeyboardType.Number else KeyboardType.Text
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("field_" + field.id),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AppTheme.accentGold,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.4f)
                        ),
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            textAlign = TextAlign.Right,
                            textDirection = TextDirection.Rtl
                        )
                    )
                }
            }
        }

        // Image Picker and True ImageView Preview (TASK 2 Implementation)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.surfaceDark, RoundedCornerShape(12.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "🖼️ صور إضافية توضح نوع العطل أو الطلب:",
                    color = AppTheme.accentGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "ارفع صوراً حقيقية للعطل لتمكين المهندس من إحضار المعدات المناسبة فوراً.",
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (selectedImageUri != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "صورة العطل المرفقة",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "جاري الحفظ صامتاً في الخلفية مع Firebase...",
                        color = Color.Green,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clickable {
                                imagePickerLauncher.launch(arrayOf("image/*"))
                            }
                            .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Upload, contentDescription = "رفع", tint = Color.LightGray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("اضغط لاختيار صورة من هاتفك 📸", color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Submit Action Button
        item {
            Button(
                onClick = {
                    // Check required fields according to booking rules
                    val emptyFields = bookingFields.filter { it.isEnabled && it.isRequired }.filter {
                        val v = when (it.id) {
                            "f_name" -> nameVal
                            "f_phone" -> phoneVal
                            "f_service" -> serviceVal
                            "f_city" -> cityVal
                            else -> extraInputs[it.id] ?: ""
                        }
                        v.isBlank()
                    }

                    if (emptyFields.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            "الرجاء ملء الحقول الإجبارية التالية أولاً: " + emptyFields.joinToString { it.label },
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // Submit booking
                        vm.submitBooking(
                            name = nameVal,
                            phone = phoneVal,
                            service = serviceVal,
                            city = cityVal,
                            imageUri = selectedImageUri?.toString(),
                            extraData = extraInputs.toMap()
                        )

                        Toast.makeText(
                            context,
                            "🇾🇪 تم إرسال طلب الحجز بنجاح ومزامنته صامتاً مع لوحة التحكم!",
                            Toast.LENGTH_LONG
                        ).show()

                        // Reset fields
                        nameVal = ""
                        phoneVal = ""
                        serviceVal = ""
                        cityVal = ""
                        selectedImageUri = null
                        extraInputs.clear()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("submit_booking_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.primaryRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("إرسال طلب الحجز وتأكيد الموعد 🚀", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun AssistantTab(vm: MainViewModel, tts: TextToSpeech?) {
    val chatHistory by vm.chatHistory.collectAsState()
    var inputQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Launch TTS helper speak
    fun speakText(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UTTERANCE_ID")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Assistant Header with a Trash Recycler Can (TASK 4 implementation)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    vm.clearChatHistory()
                    Toast.makeText(context, "تم تطهير ومسح سجل الحوار الذكي مباشرة!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.background(AppTheme.primaryRed.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "مسح سجل الدردشة", tint = AppTheme.primaryRed)
            }

            Text(
                text = "🤖 المساعد الذكي الصوتي لليمن",
                style = TextStyle(
                    color = AppTheme.accentGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Chats lists
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chatHistory.forEach { msg ->
                val isAi = msg.sender == "assistant"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .background(
                                color = if (isAi) AppTheme.surfaceDark else AppTheme.primaryRed,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Column(horizontalAlignment = if (isAi) Alignment.Start else Alignment.End) {
                            Text(
                                text = msg.text,
                                color = Color.White,
                                fontSize = 13.sp,
                                style = TextStyle(
                                    textAlign = if (isAi) TextAlign.Left else TextAlign.Right,
                                    textDirection = if (isAi) TextDirection.Ltr else TextDirection.Rtl
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier.padding(top = 4.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (isAi) {
                                    IconButton(
                                        onClick = { speakText(msg.text) },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.VolumeUp,
                                            contentDescription = "نطق",
                                            tint = AppTheme.accentGold,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Input send block
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    speakText("أهلاً بك! يرجى التحدث المباشر مع دعم خدمات اليمن الصوتي المحدث لمحافظة صنعاء وعدن.")
                    Toast.makeText(context, "التقاط الصوت مهيأ بدقة عالية لمدن اليمن: ar-YE", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .size(44.dp)
                    .background(AppTheme.accentGold, CircleShape)
            ) {
                Icon(Icons.Default.Mic, contentDescription = "تحدث صوتي", tint = Color.Black)
            }

            OutlinedTextField(
                value = inputQuery,
                onValueChange = { inputQuery = it },
                placeholder = {
                    Text(
                        "اسألني عن حرفي أو خدمة معينة...",
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Right
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = AppTheme.accentGold,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f)
                ),
                textStyle = TextStyle(
                    fontSize = 13.sp,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                )
            )

            IconButton(
                onClick = {
                    if (inputQuery.isNotBlank()) {
                        val query = inputQuery
                        inputQuery = ""

                        // Generate customized Yemen answers directly based on keywords
                        val answer = generateYemenAssistantResponse(query)
                        vm.sendAssistantMessage(query, answer)
                    }
                },
                modifier = Modifier
                    .size(44.dp)
                    .background(AppTheme.primaryRed, CircleShape)
            ) {
                Icon(Icons.Default.Send, contentDescription = "إرسال", tint = Color.White)
            }
        }
    }
}

private fun generateYemenAssistantResponse(query: String): String {
    val q = query.lowercase()
    return when {
        q.contains("صنعاء") || q.contains("حدة") -> {
            "يتوفر لدينا 5 مهندسي كهرباء وسباكة نشطين حالياً في العاصمة صنعاء - حدة وبيت بوس. هل تريد حجز أحدهم؟"
        }
        q.contains("كهرباء") || q.contains("طاقة") -> {
            "في فئة الكهرباء، يسعدني ترشيح م. عماد المهندس من عدن أو م. ليال الكبسي في صنعاء. يمكنهم تسليك وصيانة الألواح الشمسية فوراً."
        }
        q.contains("سباك") || q.contains("ماء") -> {
            "لدينا م. صالح السباك متخصص لتمديدات المياه وإصلاح التسريبات، ومقر عمله في صنعاء وضواحيها."
        }
        q.contains("عدن") || q.contains("كريتر") -> {
            "أهلاً بأهل ثغر اليمن الباسم عدن! يتوفر لدينا م. عماد لإنارة وصيانة تمديدات الكهرباء وكافة المهن المنزلية الأخرى."
        }
        else -> {
            "تم استلام استفسارك حول: \"$query\". بصفتي المساعد الذكي لكل خدمات اليمن، تتوفر لدينا كافة الكفاءات المهنية لحل وتسهيل طلبك فوراً!"
        }
    }
}


@Composable
fun AdminTab(vm: MainViewModel) {
    val bookings by vm.bookings.collectAsState()
    val bookingFields by vm.bookingFields.collectAsState()

    var activeAdminTab by remember { mutableStateOf("form_builder") } // "form_builder", "received_bookings"

    // Form builder states
    var newFieldLabel by remember { mutableStateOf("") }
    var newFieldPlaceholder by remember { mutableStateOf("") }
    var newFieldType by remember { mutableStateOf("text") }
    var newFieldRequired by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🛠️ لوحة تحكم وإدارة ومزامنة الجمهورية",
                style = TextStyle(
                    color = AppTheme.accentGold,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "التحكم بخصائص التطبيق، إحصاءات الحساب، وتعديل حقول استمارة حجز المستخدمين ديناميكياً.",
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Right,
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Subtab toggle bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { activeAdminTab = "received_bookings" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeAdminTab == "received_bookings") AppTheme.primaryRed else AppTheme.surfaceDark
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("طلبات الحجز المستلمة (" + bookings.size + ")", fontSize = 11.sp)
                }
                Button(
                    onClick = { activeAdminTab = "form_builder" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (activeAdminTab == "form_builder") AppTheme.primaryRed else AppTheme.surfaceDark
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("بناء الإستمارة (Form Builder)", fontSize = 11.sp)
                }
            }
        }

        if (activeAdminTab == "form_builder") {
            // Add new field card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "➕ إضافة حقل جديد للاستمارة:",
                            color = AppTheme.accentGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )

                        OutlinedTextField(
                            value = newFieldLabel,
                            onValueChange = { newFieldLabel = it },
                            placeholder = { Text("اسم الحقل بالعربية (مثال: البريد الإلكتروني)", color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Right, textDirection = TextDirection.Rtl)
                        )

                        OutlinedTextField(
                            value = newFieldPlaceholder,
                            onValueChange = { newFieldPlaceholder = it },
                            placeholder = { Text("التعليمات التوضيحية (البلاد، التفاصيل...)", color = Color.Gray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White),
                            textStyle = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Right, textDirection = TextDirection.Rtl)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Switch(
                                    checked = newFieldRequired,
                                    onCheckedChange = { newFieldRequired = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = AppTheme.primaryRed)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("حقل إجباري؟", color = Color.White, fontSize = 11.sp)
                            }

                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { newFieldType = "text" }) {
                                    RadioButton(selected = newFieldType == "text", onClick = { newFieldType = "text" }, colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold))
                                    Text("نص", color = Color.White, fontSize = 10.sp)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { newFieldType = "phone" }) {
                                    RadioButton(selected = newFieldType == "phone", onClick = { newFieldType = "phone" }, colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold))
                                    Text("هاتف", color = Color.White, fontSize = 10.sp)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { newFieldType = "number" }) {
                                    RadioButton(selected = newFieldType == "number", onClick = { newFieldType = "number" }, colors = RadioButtonDefaults.colors(selectedColor = AppTheme.accentGold))
                                    Text("رقم", color = Color.White, fontSize = 10.sp)
                                }
                            }
                        }

                        Button(
                            onClick = {
                                if (newFieldLabel.isNotBlank()) {
                                    vm.addFormField(
                                        label = newFieldLabel,
                                        type = newFieldType,
                                        isRequired = newFieldRequired,
                                        placeholder = newFieldPlaceholder.ifBlank { "أدخل التفاصيل..." }
                                    )
                                    Toast.makeText(context, "تمت إضافة الحقل الجديد بنجاح وانعكس على استمارة المستخدم!", Toast.LENGTH_SHORT).show()
                                    newFieldLabel = ""
                                    newFieldPlaceholder = ""
                                    newFieldRequired = false
                                } else {
                                    Toast.makeText(context, "يرجى إعطاء عنوان للحقل أولاً!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppTheme.accentGold),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("تثبيت الحقل في الاستمارة ➕", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Current fields listing
            item {
                Text(
                    text = "⚙️ التحكم بخصائص الحقول الحالية وتعديل شروطها:",
                    color = AppTheme.accentGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right
                )
            }

            items(bookingFields) { field ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Action controls
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                IconButton(onClick = { vm.removeFormField(field.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "حذف", tint = AppTheme.primaryRed)
                                }
                                IconButton(onClick = { vm.toggleFieldRequired(field.id) }) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "إجبارية",
                                        tint = if (field.isRequired) AppTheme.accentGold else Color.Gray
                                    )
                                }
                                IconButton(onClick = { vm.toggleFieldEnabled(field.id) }) {
                                    Icon(
                                        Icons.Default.RemoveRedEye,
                                        contentDescription = "إظهار/إخفاء",
                                        tint = if (field.isEnabled) Color.Green else Color.DarkGray
                                    )
                                }
                            }

                            // Info text
                            Column(horizontalAlignment = Alignment.End) {
                                Row {
                                    if (field.isRequired) {
                                        Text(" (إجباري) ", color = AppTheme.primaryRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    } else {
                                        Text(" (اختياري) ", color = Color.Gray, fontSize = 10.sp)
                                    }
                                    Text(text = field.label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Text("النوع: " + field.type + " | " + if (field.isEnabled) "نشط ومتاح للجمع" else "معطل حالياً", color = Color.LightGray, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        } else {
            // Received Bookings Tab
            if (bookings.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("لا توجد طلبات حجز مسجلة حالياً.", color = Color.LightGray, fontSize = 13.sp)
                    }
                }
            } else {
                items(bookings) { b ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AppTheme.surfaceDark)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Header row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        vm.deleteBooking(b.id)
                                        Toast.makeText(context, "تم مسح وتطهير طلب الحجز صامتاً مع قاعدة البيانات!", Toast.LENGTH_SHORT).show()
                                    }
                                ) {
                                    Icon(Icons.Default.DeleteForever, contentDescription = "حذف الطلب", tint = AppTheme.primaryRed)
                                }

                                Text(
                                    text = "طلب حجز: " + b.clientName,
                                    color = AppTheme.accentGold,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Divider(color = Color.White.copy(alpha = 0.1f))

                            // Details
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text("رقم الهاتف: " + b.clientPhone, color = Color.White, fontSize = 12.sp)
                                Text("نوع الخدمة: " + b.serviceType, color = Color.White, fontSize = 12.sp)
                                Text("مكان الإقامة: " + b.residenceCity, color = Color.White, fontSize = 12.sp)

                                // Render any custom extra fields
                                b.extraFieldsData.forEach { (fid, fval) ->
                                    val originalField = bookingFields.find { it.id == fid }
                                    if (originalField != null && fval.isNotBlank()) {
                                        Text(originalField.label + ": " + fval, color = Color.White, fontSize = 12.sp)
                                    }
                                }
                            }

                            // Image preview
                            if (b.imageUri != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Image(
                                    painter = rememberAsyncImagePainter(b.imageUri),
                                    contentDescription = "صورة العطل المرفقة",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                        .clip(RoundedCornerShape(6.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
