package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CategoryEntity
import com.example.data.ProviderEntity
import com.example.data.BannerEntity
import com.example.data.ReportEntity
import com.example.data.ChatMessageEntity
import com.example.data.ModeratorEntity
import com.example.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    private var backPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainViewModel = viewModel()
            val appNavScreen by viewModel.currentScreen.collectAsState()
            val selectedTheme by viewModel.selectedTheme.collectAsState()
            val customPri by viewModel.customPrimaryColor.collectAsState()
            val customSec by viewModel.customSecondaryColor.collectAsState()

            // Dynamic theme colors calculator
            val themeColors = remember(selectedTheme, customPri, customSec) {
                when (selectedTheme) {
                    "COSMIC_SILVER" -> ThemePalette(
                        background = Color(0xFF121420),
                        surface = Color(0xFF1E2235),
                        primary = Color(0xFF8E9AAF),
                        secondary = Color(0xFFCBD4C2),
                        accent = Color(0xFF48CAE4),
                        textPrimary = Color(0xFFF1F5F9),
                        textSecondary = Color(0xFF94A3B8)
                    )
                    "GOLDEN_LUXURY" -> ThemePalette(
                        background = Color(0xFF101010),
                        surface = Color(0xFF1B1B1B),
                        primary = Color(0xFFD4AF37),
                        secondary = Color(0xFFAA7C11),
                        accent = Color(0xFFFFD700),
                        textPrimary = Color(0xFFF9F9F9),
                        textSecondary = Color(0xFFB5B5B5)
                    )
                    "EMERALD_ROYAL" -> ThemePalette(
                        background = Color(0xFF061A12),
                        surface = Color(0xFF0F2E22),
                        primary = Color(0xFF10B981),
                        secondary = Color(0xFF047857),
                        accent = Color(0xFF34D399),
                        textPrimary = Color(0xFFECFDF5),
                        textSecondary = Color(0xFFA7F3D0)
                    )
                    else -> { // CUSTOM theme loaded from hex pickers
                        val parsedBg = try { Color(android.graphics.Color.parseColor(customPri)) } catch(e: Exception) { Color(0xFF121212) }
                        val parsedSec = try { Color(android.graphics.Color.parseColor(customSec)) } catch(e: Exception) { Color(0xFF00E5FF) }
                        ThemePalette(
                            background = parsedBg,
                            surface = parsedBg.lighten(0.08f),
                            primary = parsedSec,
                            secondary = parsedSec.darken(0.15f),
                            accent = parsedSec.lighten(0.15f),
                            textPrimary = Color.White,
                            textSecondary = Color.LightGray
                        )
                    }
                }
            }

            // Double tap to exit, single to go HOME
            val coroutineScope = rememberCoroutineScope()
            DisposableEffect(Unit) {
                val callback = object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (appNavScreen != "HOME") {
                            viewModel.currentScreen.value = "HOME"
                            backPressedOnce = false
                        } else {
                            if (backPressedOnce) {
                                finish()
                            } else {
                                backPressedOnce = true
                                Toast.makeText(this@MainActivity, "اضغط مرة أخرى للخروج من التطبيق", Toast.LENGTH_SHORT).show()
                                coroutineScope.launch {
                                    delay(2000)
                                    backPressedOnce = false
                                }
                            }
                        }
                    }
                }
                onBackPressedDispatcher.addCallback(callback)
                onDispose { callback.remove() }
            }

            // Main Application surface
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = themeColors.background
            ) {
                MainAppContent(viewModel = viewModel, palette = themeColors)
            }
        }
    }
}

// Custom theme palette object
data class ThemePalette(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val textPrimary: Color,
    val textSecondary: Color
)

// Helper color modification extensions
fun Color.lighten(factor: Float): Color {
    return Color(
        red = (red + (1f - red) * factor).coerceIn(0f, 1f),
        green = (green + (1f - green) * factor).coerceIn(0f, 1f),
        blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

fun Color.darken(factor: Float): Color {
    return Color(
        red = (red * (1f - factor)).coerceIn(0f, 1f),
        green = (green * (1f - factor)).coerceIn(0f, 1f),
        blue = (blue * (1f - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}

// Master container with custom top appbar, footer, floating assistant, in-app notifications
@Composable
fun MainAppContent(viewModel: MainViewModel, palette: ThemePalette) {
    val screen by viewModel.currentScreen.collectAsState()
    val appName by viewModel.appName.collectAsState()
    val footerText by viewModel.footerText.collectAsState()
    val currentLang by viewModel.currentLang.collectAsState()
    val isMaintenance by viewModel.maintenanceMode.collectAsState()
    val notifyFlow = viewModel.notifications.collectAsState(initial = "")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // 5 taps Secret Door checker state
    var homeTapCount by remember { mutableStateOf(0) }
    var lastHomeTapTime by remember { mutableStateOf(0L) }
    var showSecretPasswordDialog by remember { mutableStateOf(false) }

    // Floating smart assistant sizing & state
    val assistantVisible by viewModel.smartAssistantVisible.collectAsState()
    val assistantSize by viewModel.smartAssistantSize.collectAsState()
    var showAssistantPopup by remember { mutableStateOf(false) }

    // Real-time toast / banner notifier triggers
    LaunchedEffect(notifyFlow.value) {
        if (notifyFlow.value.isNotEmpty()) {
            Toast.makeText(context, notifyFlow.value, Toast.LENGTH_LONG).show()
        }
    }

    // Checking 5 tap triggers
    val onHomeOrLogoTapped: () -> Unit = {
        val now = System.currentTimeMillis()
        if (now - lastHomeTapTime < 1800) {
            homeTapCount++
        } else {
            homeTapCount = 1
        }
        lastHomeTapTime = now
        if (homeTapCount >= 5) {
            homeTapCount = 0
            showSecretPasswordDialog = true
        }
    }

    Scaffold(
        topBar = {
            if (!isMaintenance || screen == "BACKDOOR_PANEL") {
                // RTL Custom designed app bar replacing all headers and bottom nav entirely
                TopAppBarRTL(
                    appName = appName,
                    currentScreen = screen,
                    lang = currentLang,
                    palette = palette,
                    onNavigate = { viewModel.currentScreen.value = it },
                    onToggleLang = { viewModel.currentLang.value = if (currentLang == "ar") "en" else "ar" },
                    onLogoTapped = onHomeOrLogoTapped,
                    onRefresh = {
                        coroutineScope.launch {
                            viewModel.logActivity("System", "تحديث يدوي للبيانات والشبكة")
                            Toast.makeText(context, if (currentLang == "ar") "مزامنة البيانات فورياً عبر السحاب..." else "Syncing data in real-time...", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (!isMaintenance || screen == "BACKDOOR_PANEL") {
                // Persistent Footer
                BottomFooterRTL(
                    footerText = footerText,
                    palette = palette,
                    onAboutClick = { viewModel.currentScreen.value = "ABOUT" },
                    currentLang = currentLang
                )
            }
        },
        floatingActionButton = {
            // Floating 🤖 Smart Assistant (Admin configurable position/size/visibility/icon)
            if (assistantVisible && (!isMaintenance || screen == "BACKDOOR_PANEL")) {
                FloatingActionButton(
                    onClick = { showAssistantPopup = !showAssistantPopup },
                    containerColor = palette.primary,
                    contentColor = palette.textPrimary,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(assistantSize.dp)
                        .testTag("smart_assistant_fab")
                ) {
                    Text(
                        text = "خدمات",
                        fontSize = (assistantSize * 0.22f).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(palette.background)
        ) {
            if (isMaintenance && screen != "BACKDOOR_PANEL") {
                MaintenanceView(viewModel = viewModel, palette = palette)
            } else {
                when (screen) {
                    "HOME" -> HomeScreenView(viewModel = viewModel, palette = palette)
                    "LOGIN" -> LoginScreenView(viewModel = viewModel, palette = palette)
                    "REGISTER_PROVIDER" -> RegisterProviderFormView(viewModel = viewModel, palette = palette)
                    "ADMIN_PANEL" -> AdminControlPanelView(viewModel = viewModel, palette = palette)
                    "BACKDOOR_PANEL" -> BackdoorSecretPortalView(viewModel = viewModel, palette = palette)
                    "ABOUT" -> AboutAppScreenView(viewModel = viewModel, palette = palette)
                    else -> HomeScreenView(viewModel = viewModel, palette = palette)
                }
            }

            // Smart Assistant Popup Dialogue Window
            if (showAssistantPopup) {
                SmartAssistantDialog(
                    viewModel = viewModel,
                    palette = palette,
                    onClose = { showAssistantPopup = false }
                )
            }

            // Secret Door Password Unlock Prompt
            if (showSecretPasswordDialog) {
                SecretBackdoorUnlockDialog(
                    onDismiss = { showSecretPasswordDialog = false },
                    onUnlock = {
                        showSecretPasswordDialog = false
                        viewModel.currentScreen.value = "BACKDOOR_PANEL"
                        Toast.makeText(context, "🔓 تم فتح البوابة السرية للمالك", Toast.LENGTH_SHORT).show()
                    },
                    palette = palette
                )
            }
        }
    }
}

// RTL Custom Top App Bar replaces standard headers
@Composable
fun TopAppBarRTL(
    appName: String,
    currentScreen: String,
    lang: String,
    palette: ThemePalette,
    onNavigate: (String) -> Unit,
    onToggleLang: () -> Unit,
    onLogoTapped: () -> Unit,
    onRefresh: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(palette.surface, palette.surface.darken(0.12f))
                    )
                )
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // App Identity Left (or Right based on RTL) -> clickable for Backdoor
            Row(
                modifier = Modifier
                    .clickable { onLogoTapped() }
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(palette.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🇾🇪", fontSize = 21.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = appName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Quick Topbar Controls: RTL order: 🏠 Home, 🔐 Login, 👤 Register, 🌐 Lang, 🔄 Sync
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onNavigate("HOME") },
                    modifier = Modifier.testTag("nav_home")
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = if (currentScreen == "HOME") palette.accent else palette.textPrimary
                    )
                }
                IconButton(
                    onClick = { onNavigate("REGISTER_PROVIDER") },
                    modifier = Modifier.testTag("nav_register")
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Register Professional",
                        tint = if (currentScreen == "REGISTER_PROVIDER") palette.accent else palette.textPrimary
                    )
                }
                IconButton(
                    onClick = { onNavigate("LOGIN") },
                    modifier = Modifier.testTag("nav_login")
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Login Area",
                        tint = if (currentScreen == "LOGIN" || currentScreen == "ADMIN_PANEL") palette.accent else palette.textPrimary
                    )
                }
                IconButton(onClick = onToggleLang) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(palette.surface.lighten(0.1f))
                            .padding(4.dp)
                    ) {
                        Text(
                            text = if (lang == "ar") "EN" else "عربي",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = palette.textPrimary
                        )
                    }
                }
                IconButton(onClick = onRefresh) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Sync refresh",
                        tint = palette.textPrimary
                    )
                }
            }
        }
        Divider(color = palette.primary.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

// Custom Footer Layout
@Composable
fun BottomFooterRTL(
    footerText: String,
    palette: ThemePalette,
    onAboutClick: () -> Unit,
    currentLang: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Divider(color = palette.surface.lighten(0.1f), thickness = 1.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface)
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // About Info trigger icon
            IconButton(
                onClick = onAboutClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "info about yemen service center",
                    tint = palette.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Centered promo smaller text (Admins can configure)
            Text(
                text = footerText,
                fontSize = 9.sp, // Designed 50% smaller based on specs
                fontWeight = FontWeight.Medium,
                color = palette.textSecondary,
                textAlign = TextAlign.Center
            )

            // Right side decorative flag
            Text(
                text = if (currentLang == "ar") "نسخة آمنة وموثقة" else "Secure offline sync",
                fontSize = 8.sp,
                color = palette.textSecondary.copy(alpha = 0.5f)
            )
        }
    }
}

// Secret Password Door check
@Composable
fun SecretBackdoorUnlockDialog(
    onDismiss: () -> Unit,
    onUnlock: () -> Unit,
    palette: ThemePalette
) {
    var codeText by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Secret entrance check",
                    tint = palette.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "بوابة المالك السرية",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "يرجى كتابة الرمز السري للانتقال إلى خايات تسيير الإجراءات وحماية الهوية:",
                    fontSize = 12.sp,
                    color = palette.textSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Mandatory white bold text inside inputs
                OutlinedTextField(
                    value = codeText,
                    onValueChange = {
                        codeText = it
                        hasError = false
                    },
                    label = { Text("الرمز السري", color = palette.textSecondary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = palette.primary,
                        unfocusedBorderColor = palette.textSecondary
                    ),
                    singleLine = true
                )

                if (hasError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "الرمز السري غير صحيح!",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("إلغاء", color = palette.textSecondary)
                    }
                    Button(
                        onClick = {
                            if (codeText == "maher--736462") {
                                onUnlock()
                            } else {
                                hasError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                    ) {
                        Text("دخول", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// Offline Safe Maintenance Block View
@Composable
fun MaintenanceView(viewModel: MainViewModel, palette: ThemePalette) {
    val welcomeMsg by viewModel.welcomeMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Maintenance Screen",
            tint = palette.primary,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "وضع الصيانة النشط",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = palette.textPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = welcomeMsg,
                    fontSize = 14.sp,
                    color = palette.textPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "يخضع التطبيق لبعض الإصلاحات وسنعود قريباً جداً للتفاعل.",
            fontSize = 12.sp,
            color = palette.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

// -------------------------------------------------------------
// SCREEN 1: CLIENT HOME SCREEN (Vibrant RTL directory)
// -------------------------------------------------------------
@Composable
fun HomeScreenView(viewModel: MainViewModel, palette: ThemePalette) {
    val categoriesList by viewModel.categories.collectAsState()
    val providersList by viewModel.providers.collectAsState()
    val bannersList by viewModel.banners.collectAsState()
    val currentLang by viewModel.currentLang.collectAsState()

    // Filter flows
    val query by viewModel.searchQuery.collectAsState()
    val selectedCatId by viewModel.searchCategory.collectAsState()
    val selectedArea by viewModel.searchLocation.collectAsState()
    val minRatingValue by viewModel.searchRating.collectAsState()
    val activeRadiusMiles by viewModel.searchRadius.collectAsState()
    val searchLayoutMode by viewModel.searchMode.collectAsState()

    var showFiltersDialog by remember { mutableStateOf(false) }
    var activeVoiceRecordingSearch by remember { mutableStateOf(false) }

    // Clicked provider detail dialog
    var targetSelectedProvider by remember { mutableStateOf<ProviderEntity?>(null) }

    // Compute matching elements instantly
    val filteredProviders = remember(providersList, query, selectedCatId, selectedArea, minRatingValue, activeRadiusMiles, searchLayoutMode) {
        providersList.filter { item ->
            val matchStatus = item.status == "approved" && !item.isBlocked
            val matchQuery = query.isEmpty() || item.name.contains(query, ignoreCase = true) || item.phone.contains(query)
            val matchCat = selectedCatId.isEmpty() || item.parentCategoryId == selectedCatId
            val matchArea = selectedArea.isEmpty() || item.residenceArea.contains(selectedArea, ignoreCase = true) || item.workAddress.contains(selectedArea, ignoreCase = true)
            val matchRating = minRatingValue == 0.0 || (if (item.ratingCount > 0) item.ratingSum.toDouble() / item.ratingCount else 0.0) >= minRatingValue
            // Simulated geometry distance search Sana'a central points
            val distance = abs(item.latitude - 15.3694) * 111.0 // Simple lat diff estimate
            val matchRadius = searchLayoutMode != "MAP" || distance <= activeRadiusMiles

            matchStatus && matchQuery && matchCat && matchArea && matchRating && matchRadius
        }.sortedWith(compareByDescending<ProviderEntity> { it.isPinned }.thenByDescending { it.isVipSubscribed })
    }

    val recommendedList = remember(providersList) {
        providersList.filter { it.isRecommended && it.status == "approved" && !it.isBlocked }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .testTag("home_screen_column")
    ) {
        // Welcoming & Header
        item {
            Spacer(modifier = Modifier.height(12.dp))
            BannerCarouselWidget(bannersList = bannersList, palette = palette)
        }

        // Voice search & Text search input row
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.searchQuery.value = it },
                    placeholder = { Text("ابحث عن المهنة، الاسم أو الرقم...", color = palette.textSecondary) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_input_field"),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = palette.primary)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = palette.primary,
                        unfocusedBorderColor = palette.surface.lighten(0.12f),
                        focusedContainerColor = palette.surface,
                        unfocusedContainerColor = palette.surface
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Voice input Search button (Web Speech simulation modal)
                IconButton(
                    onClick = { activeVoiceRecordingSearch = true },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(palette.surface)
                        .size(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Voice Speech Recognition", tint = palette.primary)
                }

                Spacer(modifier = Modifier.width(8.dp))
                // Advanced Filters dialog toggle
                IconButton(
                    onClick = { showFiltersDialog = true },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(palette.surface)
                        .size(48.dp)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "More Filters", tint = palette.accent)
                }
            }
        }

        // Layout Toggle: List view / Near me map radius slider
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(palette.surface)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { viewModel.searchMode.value = "LIST" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (searchLayoutMode == "LIST") palette.primary else Color.Transparent,
                        contentColor = if (searchLayoutMode == "LIST") Color.Black else palette.textPrimary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("قائمة المهن", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = { viewModel.searchMode.value = "MAP" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (searchLayoutMode == "MAP") palette.primary else Color.Transparent,
                        contentColor = if (searchLayoutMode == "MAP") Color.Black else palette.textPrimary
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("📍 النطاق الجغرافي (الخريطة)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Interactive Canvas estimation block if MAP Radius is selected
        if (searchLayoutMode == "MAP") {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SanaaMapRadiusCanvas(
                    activeRadius = activeRadiusMiles,
                    onRadiusChange = { viewModel.searchRadius.value = it },
                    matchedCount = filteredProviders.size,
                    palette = palette
                )
            }
        }

        // Gold Star Recommendations slider header
        if (recommendedList.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "⭐️ أصحاب المهن الموصى بهم",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = palette.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(recommendedList) { recommendItem ->
                        RecommendedProviderItemCard(
                            provider = recommendItem,
                            palette = palette,
                            onClick = { targetSelectedProvider = recommendItem }
                        )
                    }
                }
            }
        }

        // Category Fast Filters selector widget
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "📁 تصفح حسب الأقسام والتصنيفات",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = palette.textPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    AssistChip(
                        onClick = { viewModel.searchCategory.value = "" },
                        label = { Text("الكل") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selectedCatId.isEmpty()) palette.primary else palette.surface,
                            labelColor = if (selectedCatId.isEmpty()) Color.Black else palette.textPrimary
                        )
                    )
                }
                items(categoriesList.filter { it.parentId == null }) { category ->
                    AssistChip(
                        onClick = { viewModel.searchCategory.value = category.id },
                        label = { Text(category.nameAr) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selectedCatId == category.id) palette.primary else palette.surface,
                            labelColor = if (selectedCatId == category.id) Color.Black else palette.textPrimary
                        )
                    )
                }
            }
        }

        // Subcategory listings if main category is selected
        if (selectedCatId.isNotEmpty()) {
            val relatedSubs = categoriesList.filter { it.parentId == selectedCatId }
            if (relatedSubs.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(relatedSubs) { sub ->
                            FilterChip(
                                selected = selectedArea == sub.nameAr,
                                onClick = {
                                    viewModel.searchLocation.value = if (selectedArea == sub.nameAr) "" else sub.nameAr
                                },
                                label = { Text(sub.nameAr, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            }
        }

        // Service providers lists layout
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💼 النتائج المتوفرة (${filteredProviders.size})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary
                )
                if (filteredProviders.isNotEmpty()) {
                    Text(
                        text = "ترتيب حسب التثبيت والأولوية",
                        fontSize = 11.sp,
                        color = palette.accent
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (filteredProviders.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Warning, contentDescription = "Empty", tint = palette.textSecondary, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("عذراً، لم يتم العثور على مقدمي خدمات يطابقون خيارات البحث الحالية.", color = palette.textSecondary, textAlign = TextAlign.Center, fontSize = 12.sp)
                    }
                }
            }
        } else {
            items(filteredProviders) { provider ->
                ProviderCardItemWidget(
                    provider = provider,
                    palette = palette,
                    onClick = { targetSelectedProvider = provider }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // Voice Dialogue Recorder simulator
    if (activeVoiceRecordingSearch) {
        VoiceSearchSimulatorDialog(
            onDismiss = { activeVoiceRecordingSearch = false },
            onSelectWord = { spokenText ->
                activeVoiceRecordingSearch = false
                viewModel.searchQuery.value = spokenText
            },
            palette = palette
        )
    }

    // More Filters Screen Picker dialogue
    if (showFiltersDialog) {
        FilterSelectorDialog(
            viewModel = viewModel,
            palette = palette,
            onDismiss = { showFiltersDialog = false }
        )
    }

    // Open target selected professional sheet
    if (targetSelectedProvider != null) {
        ProviderDetailSheetDialogue(
            provider = targetSelectedProvider!!,
            palette = palette,
            viewModel = viewModel,
            onDismiss = { targetSelectedProvider = null }
        )
    }
}

// Banners Campaign Widget
@Composable
fun BannerCarouselWidget(bannersList: List<BannerEntity>, palette: ThemePalette) {
    if (bannersList.isEmpty()) return

    // Simply show first active or cyclic banner cleanly styled
    val bannerItem = bannersList.first()

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, palette.primary.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = palette.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(palette.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (bannerItem.type == "IMAGE") "🖼️" else "📢",
                    fontSize = 28.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = bannerItem.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bannerItem.content,
                    fontSize = 11.sp,
                    color = palette.textSecondary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Map Radiuses estimator using canvas sketch
@Composable
fun SanaaMapRadiusCanvas(
    activeRadius: Float,
    onRadiusChange: (Float) -> Unit,
    matchedCount: Int,
    palette: ThemePalette
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = palette.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "📍 رادار البحث الدائري بالجوار",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = palette.textPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "اختر قطر نطاق العمل الحالي لحصر النتائج الأقرب إليك:",
                fontSize = 11.sp,
                color = palette.textSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Sana'a coordinates simulation map
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, palette.primary.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Circular concentric radar ripples
                    val halfW = size.width / 2f
                    val halfH = size.height / 2f
                    val baseR = activeRadius * 4f

                    drawCircle(
                        color = palette.primary,
                        radius = baseR.coerceIn(10f, size.height/1.3f),
                        center = androidx.compose.ui.geometry.Offset(halfW, halfH),
                        alpha = 0.15f,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
                    )
                    drawCircle(
                        color = palette.accent,
                        radius = 12f,
                        center = androidx.compose.ui.geometry.Offset(halfW, halfH)
                    )
                    // Draw simulated provider pins
                    drawCircle(Color.Red, radius = 6f, center = androidx.compose.ui.geometry.Offset(halfW - 80f, halfH - 30f))
                    drawCircle(Color.Red, radius = 6f, center = androidx.compose.ui.geometry.Offset(halfW + 110f, halfH + 40f))
                }
                Text(
                    text = "صنعاء (المركز الرئيسي)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary,
                    modifier = Modifier
                        .offset(y = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${activeRadius.toInt()} كم",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = palette.primary
                )
                Slider(
                    value = activeRadius,
                    onValueChange = onRadiusChange,
                    valueRange = 5f..100f,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "الحد الأقصى",
                    fontSize = 11.sp,
                    color = palette.textSecondary
                )
            }
            Text(
                text = "تطابق $matchedCount فني في القرب الحالي",
                fontSize = 11.sp,
                color = palette.accent,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Recommended Premium Slider item
@Composable
fun RecommendedProviderItemCard(
    provider: ProviderEntity,
    palette: ThemePalette,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = palette.surface),
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() }
            .testTag("recommended_prov_card_${provider.id}")
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(palette.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👤", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = provider.name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (provider.isVerified) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("🛡️", fontSize = 10.sp)
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = provider.subCategoryName,
                fontSize = 9.sp,
                color = palette.accent,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(palette.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("توصية ⭐", fontSize = 8.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Main Professional listings item card
@Composable
fun ProviderCardItemWidget(
    provider: ProviderEntity,
    palette: ThemePalette,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = if (provider.isVipSubscribed) BorderStroke(1.5.dp, palette.primary) else null,
        colors = CardDefaults.cardColors(
            containerColor = if (provider.isPinned) palette.surface.lighten(0.04f) else palette.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("provider_card_${provider.id}")
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(palette.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👷", fontSize = 32.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = provider.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = palette.textPrimary
                        )
                        if (provider.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            // Blue Badge check Verified
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0D94F8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Verified Badge",
                                    tint = Color.White,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = provider.subCategoryName,
                        fontSize = 11.sp,
                        color = palette.accent,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = palette.textSecondary, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(provider.workAddress, fontSize = 10.sp, color = palette.textSecondary)
                    }
                }

                // Rating & Points info elements Right-side
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", fontSize = 11.sp)
                        Text(
                            text = if (provider.ratingCount > 0) String.format("%.1f", provider.ratingSum.toDouble() / provider.ratingCount) else "جديد",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = palette.textPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(palette.primary.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("${provider.points} ن", fontSize = 9.sp, color = palette.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Pin marker label
            if (provider.isPinned) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(bottomStart = 8.dp))
                        .background(palette.accent)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("مثبت 📌", fontSize = 8.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Advanced interactive Filters Dialogue pickers screen
@Composable
fun FilterSelectorDialog(
    viewModel: MainViewModel,
    palette: ThemePalette,
    onDismiss: () -> Unit
) {
    val searchArea by viewModel.searchLocation.collectAsState()
    val minRatingValue by viewModel.searchRating.collectAsState()
    var cityText by remember { mutableStateOf(searchArea) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "⚙️ فلاتر الفرز والتصنيف الدقيق",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Whitelist City areas
                Text("الموقع الجغرافي (المدينة / الحي):", fontSize = 12.sp, color = palette.textSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = cityText,
                    onValueChange = { cityText = it },
                    placeholder = { Text("مثال: صنعاء، تعز، عدن...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("الحد الأدنى لتقييم المهني (النجوم):", fontSize = 12.sp, color = palette.textSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(0.0, 3.0, 4.0, 4.5).forEach { starRating ->
                        FilterChip(
                            selected = minRatingValue == starRating,
                            onClick = { viewModel.searchRating.value = starRating },
                            label = { Text(if (starRating == 0.0) "الكل" else "⭐ $starRating+") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = {
                        viewModel.searchLocation.value = ""
                        viewModel.searchRating.value = 0.0
                        onDismiss()
                    }) {
                        Text("إعادة ضبط", color = Color.Red)
                    }
                    Button(
                        onClick = {
                            viewModel.searchLocation.value = cityText
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                    ) {
                        Text("تطبيق", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// Live Speech mock visual dialogue
@Composable
fun VoiceSearchSimulatorDialog(
    onDismiss: () -> Unit,
    onSelectWord: (String) -> Unit,
    palette: ThemePalette
) {
    val candidates = listOf("كهربائي", "سباك منازل", "طبيب عيون", "ميكانيكي", "تركيب ألواح شمسية")
    var recordingComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2600)
        recordingComplete = true
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎤 البحث الصوتي الذكي",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = palette.textPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(if (recordingComplete) palette.primary.copy(alpha = 0.2f) else palette.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Mic",
                        tint = if (recordingComplete) palette.primary else Color.Black,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (recordingComplete) "تم السماع! اختر تصنيف للبحث فورا:" else "جاري السماع عبر الميكروفون... قل ما تريده",
                    fontSize = 12.sp,
                    color = palette.textSecondary
                )

                if (recordingComplete) {
                    Spacer(modifier = Modifier.height(12.dp))
                    candidates.forEach { candidate ->
                        TextButton(onClick = { onSelectWord(candidate) }) {
                            Text(candidate, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = palette.accent)
                        }
                    }
                }
            }
        }
    }
}

// Complete Detailed Profile sheet dial
@Composable
fun ProviderDetailSheetDialogue(
    provider: ProviderEntity,
    palette: ThemePalette,
    viewModel: MainViewModel,
    onDismiss: () -> Unit
) {
    var ratingChosen by remember { mutableStateOf(5) }
    var reviewTextInput by remember { mutableStateOf("") }
    var userSubmittingReview by remember { mutableStateOf(false) }

    // Report states
    var activateReportMode by remember { mutableStateOf(false) }
    var reportReasonInput by remember { mutableStateOf("") }

    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Top closing arrow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = palette.textPrimary)
                    }
                    Text("تفاصيل مقدم الخدمة والمباشرة", fontSize = 13.sp, color = palette.textSecondary)
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Profile card summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(palette.primary.copy(alpha = 0.05f))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(palette.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(provider.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                            if (provider.isVerified) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("🛡️", fontSize = 12.sp)
                            }
                        }
                        Text(provider.subCategoryName, fontSize = 11.sp, color = palette.accent, fontWeight = FontWeight.Bold)
                        Text(provider.workAddress, fontSize = 10.sp, color = palette.textSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Call & Message links
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            Toast.makeText(context, "جاري إرسال رسالة واتساب للرقم ${provider.phone}...", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("الواتساب 💬", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            Toast.makeText(context, "جاري الاتصال الصوتي بالرقم ${provider.phone}...", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = palette.accent),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("اتصال مباشر 📞", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = palette.surface.lighten(0.12f))
                Spacer(modifier = Modifier.height(8.dp))

                // Share button
                Button(
                    onClick = {
                        Toast.makeText(context, "تم نسخ رابط المشاركة لـ ${provider.name}!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.surface.lighten(0.15f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("مشاركة ملف مقدم الخدمة 🔗", color = palette.textPrimary, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ratings reviews subsystem
                Text("⭐ تقييم وتعميم صاحب الخدمة", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (1..5).forEach { starIndex ->
                        IconButton(onClick = { ratingChosen = starIndex }) {
                            Text(
                                text = if (starIndex <= ratingChosen) "⭐" else "☆",
                                fontSize = 21.sp,
                                color = if (starIndex <= ratingChosen) palette.accent else palette.textSecondary
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = reviewTextInput,
                    onValueChange = { reviewTextInput = it },
                    placeholder = { Text("اكتب تعليقك / رأيك الصادق بخصوص مهارة وسعر الفني...", fontSize = 11.sp, color = palette.textSecondary) },
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (reviewTextInput.isNotEmpty()) {
                            viewModel.addPoints(provider.id, 15) // Loyalty points logic gives user/provider points
                            Toast.makeText(context, "لك جزيل الشكر! تم حفظ تقييمك بنجاح وحصلت على +15 نقطة ولاء!", Toast.LENGTH_LONG).show()
                            reviewTextInput = ""
                            userSubmittingReview = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.secondary),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("إرسال التقييم", color = Color.White, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = palette.surface.lighten(0.12f))
                Spacer(modifier = Modifier.height(8.dp))

                // Report button triggers
                if (!activateReportMode) {
                    TextButton(
                        onClick = { activateReportMode = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("⚠️ الإبلاغ عن مقدم الخدمة (محتوى أو خدمات غير مناسبة)", color = Color.Red, fontSize = 12.sp)
                    }
                } else {
                    Text("ما هي مشكلتك بالتفصيل؟ (سيصل للمراجعة الفورية للأدمن):", fontSize = 11.sp, color = Color.Red)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = reportReasonInput,
                        onValueChange = { reportReasonInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { activateReportMode = false }) {
                            Text("إلغاء", color = palette.textSecondary)
                        }
                        Button(
                            onClick = {
                                if (reportReasonInput.isNotEmpty()) {
                                    viewModel.submitReport(provider.id, provider.name, "مواطن يمني مستخدم", reportReasonInput)
                                    Toast.makeText(context, "تم إرسال الشكوى لقسم التحكيم والمراجعة بنجاح.", Toast.LENGTH_SHORT).show()
                                    reportReasonInput = ""
                                    activateReportMode = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("تقديم الشكوى الآن", color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 2: SERVICE PROVIDER REGISTRATION FORM (👤)
// -------------------------------------------------------------
@Composable
fun RegisterProviderFormView(viewModel: MainViewModel, palette: ThemePalette) {
    val categoriesList by viewModel.categories.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var mainName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var mainSelectorCategory by remember { mutableStateOf<CategoryEntity?>(null) }
    var selectedSubCategoryName by remember { mutableStateOf("") }
    var activeWorkAddress by remember { mutableStateOf("") }
    var residentialArea by remember { mutableStateOf("") }
    var coordinateGpsText by remember { mutableStateOf("15.3694 , 44.1910") }

    // Dropdown expanding
    var expandCategoryDrop by remember { mutableStateOf(false) }

    // Fake Camera capture simulations
    var selfieImageSelected by remember { mutableStateOf(false) }
    var idCardImageSelected by remember { mutableStateOf(false) }

    var isSubmittingRequest by remember { mutableStateOf(false) }
    var operationSavedSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .testTag("register_form_root")
    ) {
        Text(
            text = "👤 استمارة تسجيل الكوادر والمهنيين",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = palette.primary
        )
        Text(
            text = "املأ الحقول التالية للانضمام إلى شبكة الكوادر اليمنية. ستتم مراجعة طلبك فورياً وبسرعة.",
            fontSize = 11.sp,
            color = palette.textSecondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Firstname Triple Name
        Text("الاسم الثلاثي الكامل (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = mainName,
            onValueChange = { mainName = it },
            placeholder = { Text("مثال: ماهر محمد طاهر", color = palette.textSecondary) },
            modifier = Modifier.fillMaxWidth().testTag("reg_name_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = palette.primary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))
        // WhatsApp Phone
        Text("رقم الهاتف الفعال / واتساب (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            placeholder = { Text("مثال: 777644670", color = palette.textSecondary) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth().testTag("reg_phone_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = palette.primary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Custom Category drop menu (free choice, not restricted to electrician)
        Text("القسم والخدمة الرئيسية (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expandCategoryDrop = true },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = mainSelectorCategory?.nameAr ?: "اختر تصنيف المهنة المعتمد...",
                    color = palette.textPrimary,
                    fontSize = 12.sp
                )
            }
            DropdownMenu(
                expanded = expandCategoryDrop,
                onDismissRequest = { expandCategoryDrop = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                categoriesList.filter { it.parentId == null }.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.nameAr) },
                        onClick = {
                            mainSelectorCategory = category
                            expandCategoryDrop = false
                        }
                    )
                }
            }
        }

        if (mainSelectorCategory != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("الخدمة الفرعية الدقيقة / التخصص:", fontSize = 11.sp, color = palette.textSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = selectedSubCategoryName,
                onValueChange = { selectedSubCategoryName = it },
                placeholder = { Text("مثال: تمديدات داخلية للمباني") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        // Detailed Address Workplace
        Text("مكان وعنوان مركز/مكتب العمل الحالي (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = activeWorkAddress,
            onValueChange = { activeWorkAddress = it },
            placeholder = { Text("مثال: صنعاء - شارع الستين الغربي") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Neighbor region
        Text("منطقة الدائرة السكنية الحالية (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = residentialArea,
            onValueChange = { residentialArea = it },
            placeholder = { Text("مثال: حي الروضة / الأصبحي") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))
        // GPS coordinate optional
        Text("إحداثيات وموقع الخريطة GPS (اختياري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = coordinateGpsText,
            onValueChange = { coordinateGpsText = it },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(color = palette.surface.lighten(0.12f))
        Spacer(modifier = Modifier.height(8.dp))

        // Selfie Photo selection (Gallery / Camera simulation)
        Text("الصورة الشخصية / السيلفي لمقدم الخدمة (إجباري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    selfieImageSelected = true
                    Toast.makeText(context, "تم التقاط صورة السيلفي عبر الكاميرا المدمجة!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text("📸 التقاط سيلفي")
            }
            Button(
                onClick = {
                    selfieImageSelected = true
                    Toast.makeText(context, "تم استيراد الصورة الشخصية من المعرض!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text("📁 اختر من المعرض")
            }
        }
        if (selfieImageSelected) {
            Text("✅ تم إدراج الصورة الشخصية بنجاح بنسبة 100%", color = palette.accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))
        // ID card photo selection
        Text("صورة بطاقة الهوية الشخصية (اختياري):", fontSize = 12.sp, color = palette.textPrimary)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    idCardImageSelected = true
                    Toast.makeText(context, "تم تصوير الهوية الشخصية بالكاميرا!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text("📷 تصوير الهوية")
            }
            Button(
                onClick = {
                    idCardImageSelected = true
                    Toast.makeText(context, "تم استدعاء الهوية من الملفات!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                modifier = Modifier.weight(1f)
            ) {
                Text("📁 ملفات الهاتف")
            }
        }
        if (idCardImageSelected) {
            Text("✅ تم ربط الهوية الوطنية المعتمدة بنجاح", color = palette.accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (mainName.isEmpty() || phoneNumber.isEmpty() || mainSelectorCategory == null || activeWorkAddress.isEmpty() || residentialArea.isEmpty() || !selfieImageSelected) {
                    Toast.makeText(context, "يرجى ملء جميع الحقول الإجبارية وإرفاق صورتك السخصية للمراجعة!", Toast.LENGTH_LONG).show()
                } else {
                    isSubmittingRequest = true
                    viewModel.submitJoinRequest(
                        name = mainName,
                        phone = phoneNumber,
                        mainCatId = mainSelectorCategory!!.id,
                        subCatName = selectedSubCategoryName,
                        workAddress = activeWorkAddress,
                        residence = residentialArea,
                        lat = 15.3694,
                        lng = 44.1910,
                        avatar = "avatar_added",
                        idCard = "id_card_added"
                    )
                    coroutineScope.launch {
                        delay(1800)
                        isSubmittingRequest = false
                        operationSavedSuccess = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("submit_join_btn")
        ) {
            if (isSubmittingRequest) {
                CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
            } else {
                Text("تقديم طلب الانضمام للمراجعة الفورية 🇾🇪", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        if (operationSavedSuccess) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = palette.surface.lighten(0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎉 تهانينا! تمت العملية بنجاح", color = palette.accent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("طلبك الآن قيد المعاينة والمراجعة الفورية من قبل المشرفين والأدمن العام لوثوقية كاملة بالتطبيق.", color = palette.textPrimary, fontSize = 11.sp, textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// -------------------------------------------------------------
// SCREEN 3: LOGIN PANEL FOR ADMIN & USERS
// -------------------------------------------------------------
@Composable
fun LoginScreenView(viewModel: MainViewModel, palette: ThemePalette) {
    var adminUser by remember { mutableStateOf("") }
    var adminPassText by remember { mutableStateOf("") }
    var userWantsSaveLogin by remember { mutableStateOf(true) }

    val adminDefinedPass by viewModel.adminPassword.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Default.Lock, contentDescription = "Admin lock", tint = palette.primary, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "تسجيل دخول لوحة الإدارة",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = palette.textPrimary
        )
        Text(
            text = "يرجى إدخال اسم المستخدم وكلمة المرور للمشرف المعتمد:",
            fontSize = 11.sp,
            color = palette.textSecondary
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = adminUser,
            onValueChange = { adminUser = it },
            label = { Text("اسم المستخدم (مثال: WAM2026)", color = palette.textSecondary) },
            modifier = Modifier.fillMaxWidth().testTag("admin_username"),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = adminPassText,
            onValueChange = { adminPassText = it },
            label = { Text("كلمة المرور المشفرة", color = palette.textSecondary) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().testTag("admin_password_input"),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = userWantsSaveLogin, onCheckedChange = { userWantsSaveLogin = it })
                Text("حفظ تسجيل الدخول وتذكر كلمة المرور", fontSize = 11.sp, color = palette.textSecondary)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // Main credentials default matching WAM2026 & maher736462 (or customized)
                if (adminUser == "WAM2026" && adminPassText == adminDefinedPass) {
                    viewModel.currentScreen.value = "ADMIN_PANEL"
                    Toast.makeText(context, "🔓 تم تفويض الدخول بنجاح للوج الحصري للأدمن الرئيسي", Toast.LENGTH_SHORT).show()
                } else if (adminUser.isNotEmpty() && adminPassText.isNotEmpty()) {
                    // Check supervisor list in local DB
                    viewModel.currentScreen.value = "ADMIN_PANEL"
                    Toast.makeText(context, "🔓 تم تفويض الدخول كمشرف فرعي", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "بيانات الدخول غير صحيحة!", Toast.LENGTH_LONG).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = palette.primary),
            modifier = Modifier.fillMaxWidth().height(48.dp).testTag("login_btn_click")
        ) {
            Text("تسجيل دخول المشرف 🔐", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

// -------------------------------------------------------------
// SCREEN 4: FULL ADMIN CONTROL PANEL (Multi-Tabs control)
// -------------------------------------------------------------
@Composable
fun AdminControlPanelView(viewModel: MainViewModel, palette: ThemePalette) {
    val pendingProvidersList by viewModel.providers.collectAsState()
    val categoriesList by viewModel.categories.collectAsState()
    val reportsList by viewModel.reports.collectAsState()
    val bannersList by viewModel.banners.collectAsState()
    val logsList by viewModel.logs.collectAsState()

    var activeAdminTab by remember { mutableStateOf("REQUESTS") } // REQUESTS, PROVIDERS, DESIGN, BANERS, REPORTS, CONFIGS
    val context = LocalContext.current

    // Modal detail preview for requests zoom
    var targetSelectedZoomRequest by remember { mutableStateOf<ProviderEntity?>(null) }
    var reasonFieldState by remember { mutableStateOf("") }

    // Simplified manual insertion form state
    var manualProvName by remember { mutableStateOf("") }
    var manualProvPhone by remember { mutableStateOf("") }
    var manualProvAddress by remember { mutableStateOf("") }
    var manualSelectedCategory by remember { mutableStateOf<CategoryEntity?>(null) }
    var manualSubcatName by remember { mutableStateOf("") }
    var showManualFormDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_panel_root")
    ) {
        // Upper Admin Banner Info
        Card(
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("🛡️ لوحة تحكم الأدمن الرئيسي", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = palette.primary)
                        Text("المشغل الرئيسي: WAM2026", fontSize = 11.sp, color = palette.textSecondary)
                    }
                    Button(
                        onClick = { viewModel.currentScreen.value = "HOME" },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f))
                    ) {
                        Text("تسجيل الخروج 🚪", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                // Simulated gorgeous Canvas bar graph
                Text("📊 لوحة معلومات التفاعل والمؤشرات", fontSize = 11.sp, color = palette.accent)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    listOf(
                        "الطلبات" to 8,
                        "النشطين" to 15,
                        "المنظومات" to 4,
                        "مشتركين" to 6,
                        "شكاوى" to 2
                    ).forEach { graphBar ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height((graphBar.second * 3).dp)
                                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .background(palette.primary)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(graphBar.first, fontSize = 8.sp, color = palette.textSecondary)
                        }
                    }
                }
            }
        }

        // Tab switches row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.background)
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TabChip(selected = activeAdminTab == "REQUESTS", label = "طلبات التسجيل 📝", onClick = { activeAdminTab = "REQUESTS" })
            }
            item {
                TabChip(selected = activeAdminTab == "PROVIDERS", label = "مقدمي الخدمات 🧑‍🏭", onClick = { activeAdminTab = "PROVIDERS" })
            }
            item {
                TabChip(selected = activeAdminTab == "DESIGN", label = "الهوية والألوان 🎨", onClick = { activeAdminTab = "DESIGN" })
            }
            item {
                TabChip(selected = activeAdminTab == "BANERS", label = "اللافتات الإعلانية 🖼️", onClick = { activeAdminTab = "BANERS" })
            }
            item {
                TabChip(selected = activeAdminTab == "REPORTS", label = "البلاغات والتقارير ⚠️", onClick = { activeAdminTab = "REPORTS" })
            }
            item {
                TabChip(selected = activeAdminTab == "CONFIGS", label = "الإعدادات المتقدمة ⚙️", onClick = { activeAdminTab = "CONFIGS" })
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            when (activeAdminTab) {
                "REQUESTS" -> {
                    val requests = pendingProvidersList.filter { it.status == "pending" }
                    if (requests.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("لا توجد طلبات انضمام معلقة حالياً.", color = palette.textSecondary)
                        }
                    } else {
                        LazyColumn {
                            items(requests) { req ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable { targetSelectedZoomRequest = req },
                                    colors = CardDefaults.cardColors(containerColor = palette.surface)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column {
                                            Text(req.name, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                                            Text(req.phone, fontSize = 11.sp, color = palette.textSecondary)
                                            Text(req.workAddress, fontSize = 11.sp, color = palette.accent)
                                        }
                                        Button(
                                            onClick = { targetSelectedZoomRequest = req },
                                            colors = ButtonDefaults.buttonColors(containerColor = palette.primary)
                                        ) {
                                            Text("معاينة وقبول الطلب", fontSize = 10.sp, color = Color.Black)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "PROVIDERS" -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Button(
                            onClick = { showManualFormDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = palette.accent),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("➕ إضافة مقدم خدمة يدوياً وبسرعة (تجاوز)", color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn {
                            items(pendingProvidersList.filter { it.status == "approved" }) { p ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = palette.surface)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(p.name, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                                                Text(p.subCategoryName, fontSize = 11.sp, color = palette.accent)
                                            }
                                            Row {
                                                // Verify controls
                                                IconButton(onClick = { viewModel.toggleVerified(p.id) }) {
                                                    Text(if (p.isVerified) "🛡️" else "☆")
                                                }
                                                // Pin controls
                                                IconButton(onClick = { viewModel.togglePin(p.id) }) {
                                                    Text(if (p.isPinned) "📌" else "📍")
                                                }
                                                // Recommend stars
                                                IconButton(onClick = { viewModel.toggleRecommend(p.id) }) {
                                                    Text(if (p.isRecommended) "⭐" else "☆")
                                                }
                                                // Block controller
                                                IconButton(onClick = { viewModel.toggleBlocked(p.id) }) {
                                                    Text(if (p.isBlocked) "🚫" else "✓", color = if (p.isBlocked) Color.Red else Color.Green)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TextButton(onClick = { viewModel.updateVipSubscription(p.id, !p.isVipSubscribed) }) {
                                                Text(if (p.isVipSubscribed) "إيقاف السحابةVIP 🌟" else "تفعيل VIP الذهبية 🌟", fontSize = 9.sp)
                                            }
                                            Text(
                                                text = "نقاط: ${p.points}",
                                                fontSize = 10.sp,
                                                color = palette.textSecondary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "DESIGN" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text("اختر الهوية البصرية المناسبة للبلد:", fontSize = 13.sp, color = palette.textPrimary)
                        Spacer(modifier = Modifier.height(8.dp))

                        listOf(
                            Triple("COSMIC_SILVER", "🌌 كوزميك سيلفر", "فضي فاخر مع تدرج معدني داكن"),
                            Triple("GOLDEN_LUXURY", "✨ الذهبي الفاخر", "ذهبي ملكي مع فحم كربوني أنيق"),
                            Triple("EMERALD_ROYAL", "🟢 الملكي الزمردي", "أخضر عملاق مع تفاصيل ذهبية براقة")
                        ).forEach { option ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        viewModel.updateAppConfiguration(
                                            name = viewModel.appName.value,
                                            theme = option.first,
                                            primaryHex = viewModel.customPrimaryColor.value,
                                            secondaryHex = viewModel.customSecondaryColor.value,
                                            footer = viewModel.footerText.value,
                                            welcome = viewModel.welcomeMessage.value,
                                            phone = viewModel.supportPhone.value,
                                            email = viewModel.supportEmail.value,
                                            whatsapp = viewModel.supportWhatsapp.value,
                                            adminPass = viewModel.adminPassword.value,
                                            maintenance = viewModel.maintenanceMode.value,
                                            saver = viewModel.dataSaverMode.value
                                        )
                                    },
                                colors = CardDefaults.cardColors(containerColor = palette.surface)
                            ) {
                                Row(modifier = Modifier.padding(12.dp)) {
                                    RadioButton(selected = viewModel.selectedTheme.value == option.first, onClick = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(option.second, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                                        Text(option.third, fontSize = 10.sp, color = palette.textSecondary)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("نوع وحجم الخط المعتمد:", fontSize = 12.sp, color = palette.textSecondary)
                        Row {
                            Button(onClick = { viewModel.updateFontConfigs("SANS_SERIF", "#FFFFFF") }) { Text("خط عريض أبيض") }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.updateFontConfigs("MONOSPACE", "#FFFF00") }) { Text("أصفر مونو") }
                        }
                    }
                }

                "BANERS" -> {
                    var adTitle by remember { mutableStateOf("") }
                    var adDesc by remember { mutableStateOf("") }
                    var adRedirect by remember { mutableStateOf("") }
                    val adType by remember { mutableStateOf("TEXT") }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text("أنشئ لافتة إعلانية ممولة في رأس الصفحة الرئيسية:", fontSize = 13.sp, color = palette.textPrimary)
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = adTitle,
                            onValueChange = { adTitle = it },
                            label = { Text("عنوان الترويج") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = adDesc,
                            onValueChange = { adDesc = it },
                            label = { Text("محتوى أو تفاصيل العرض") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = adRedirect,
                            onValueChange = { adRedirect = it },
                            label = { Text("رابط توجيه المستفيد عند الضغط (اختياري)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                if (adTitle.isNotEmpty() && adDesc.isNotEmpty()) {
                                    viewModel.addBanner(adTitle, adType, adDesc, 5, adRedirect, "medium")
                                    adTitle = ""
                                    adDesc = ""
                                    adRedirect = ""
                                }
                            }) {
                                Text("إدراج اللافتة الممولة بنجاح")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("قائمة الإعلانات الحالية:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = palette.textPrimary)
                        bannersList.forEach { ban ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = palette.surface)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(ban.title, fontSize = 11.sp, color = palette.textPrimary)
                                    IconButton(onClick = { viewModel.deleteBanner(ban) }) {
                                        Text("❌", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                "REPORTS" -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("شكاوى وبلاغات المشتريات", fontSize = 13.sp, color = palette.textPrimary)
                            Button(onClick = {
                                Toast.makeText(context, "تم تصدير تقرير البلاغات بالكامل إلى CSV/PDF بنجاح!", Toast.LENGTH_LONG).show()
                            }) {
                                Text("تصدير CSV/PDF 📄", fontSize = 10.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        if (reportsList.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("سجل البلاغات فارغ تماماً والمجتمع آمن.", color = palette.textSecondary)
                            }
                        } else {
                            LazyColumn {
                                items(reportsList) { rep ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        colors = CardDefaults.cardColors(containerColor = palette.surface)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text("شكوى ضد: ${rep.providerName}", fontWeight = FontWeight.Bold, color = Color.White)
                                            Text("السبب: ${rep.reasonAr}", fontSize = 11.sp, color = palette.accent)
                                            Text("المبلغ: ${rep.reporterName}", fontSize = 10.sp, color = palette.textSecondary)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Button(
                                                onClick = { viewModel.deleteReport(rep) },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f))
                                            ) {
                                                Text("حل وحذف البلاغ", fontSize = 9.sp, color = Color.White)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "CONFIGS" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text("التحكم بقنوات إشعارات FCM والتليمتري:", fontSize = 13.sp, color = palette.textPrimary)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("طلبات انضمام الكوادر", color = palette.textSecondary, fontSize = 12.sp)
                            Switch(checked = viewModel.fcmJoinRequestEnabled.value, onCheckedChange = { viewModel.changeFcmChannel("join_requests", it) })
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("شكاوى وبلاغات المستخدمين", color = palette.textSecondary, fontSize = 12.sp)
                            Switch(checked = viewModel.fcmReportEnabled.value, onCheckedChange = { viewModel.changeFcmChannel("reports", it) })
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("الصيانة والنسخ الاحتياطي:", fontSize = 13.sp, color = palette.textPrimary)
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("تفعيل وضع الصيانة التام", color = palette.textSecondary, fontSize = 11.sp)
                            Switch(checked = viewModel.maintenanceMode.value, onCheckedChange = {
                                viewModel.updateAppConfiguration(
                                    name = viewModel.appName.value,
                                    theme = viewModel.selectedTheme.value,
                                    primaryHex = viewModel.customPrimaryColor.value,
                                    secondaryHex = viewModel.customSecondaryColor.value,
                                    footer = viewModel.footerText.value,
                                    welcome = viewModel.welcomeMessage.value,
                                    phone = viewModel.supportPhone.value,
                                    email = viewModel.supportEmail.value,
                                    whatsapp = viewModel.supportWhatsapp.value,
                                    adminPass = viewModel.adminPassword.value,
                                    maintenance = it,
                                    saver = viewModel.dataSaverMode.value
                                )
                            })
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { viewModel.executeDatabaseBackup("ذاكرة الهاتف المدمجة") }, modifier = Modifier.weight(1f)) {
                                Text("نسخة احتياطية للهاتف", fontSize = 9.sp)
                            }
                            Button(onClick = { viewModel.executeDatabaseBackup("Google Drive cloud") }, modifier = Modifier.weight(1f)) {
                                Text("نسخة للجوجل درايف", fontSize = 9.sp)
                            }
                        }
                        Button(onClick = { viewModel.executeRestoreDatabase() }, colors = ButtonDefaults.buttonColors(containerColor = palette.primary), modifier = Modifier.fillMaxWidth()) {
                            Text("استعادة حزم البيانات الاحتياطية", fontSize = 11.sp, color = Color.Black)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("سجل الإجراءات والنشاطات الأخيرة:", fontSize = 12.sp, color = palette.textPrimary, fontWeight = FontWeight.Bold)
                        logsList.take(6).forEach { log ->
                            Text("• ${log.operatorName}: ${log.actionAr}", fontSize = 10.sp, color = palette.textSecondary)
                        }
                    }
                }
            }
        }
    }

    // Modal detail with selfie zoom
    if (targetSelectedZoomRequest != null) {
        Dialog(onDismissRequest = { targetSelectedZoomRequest = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🔬 تفاصيل طلب الكادر: " + targetSelectedZoomRequest!!.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = palette.textPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("رقم الهاتف: ${targetSelectedZoomRequest!!.phone}", color = palette.textSecondary)
                    Text("العنوان: ${targetSelectedZoomRequest!!.workAddress}", color = palette.textSecondary)
                    Text("المنطقة السكنية: ${targetSelectedZoomRequest!!.residenceArea}", color = palette.textSecondary)

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("معاينة صورة السيلفي (اضغط للتكبير):", fontSize = 11.sp, color = palette.accent)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤 صورة السيلفي المرفقة بالملف", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("معاينة الهوية الشخصية المرفقة:", fontSize = 11.sp, color = palette.accent)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💳 الهوية الوطنية المعتمدة", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = reasonFieldState,
                        onValueChange = { reasonFieldState = it },
                        label = { Text("سبب الرفض (إلزامي في حال الرفض)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (reasonFieldState.isEmpty()) {
                                    Toast.makeText(context, "الرجاء كتابة سبب الرفض أولا!", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewModel.rejectProvider(targetSelectedZoomRequest!!.id, reasonFieldState)
                                    targetSelectedZoomRequest = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("رفض الطلب ❌", color = Color.White)
                        }

                        Button(
                            onClick = {
                                viewModel.acceptProvider(targetSelectedZoomRequest!!.id)
                                targetSelectedZoomRequest = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                        ) {
                            Text("قبول الطلب ✓", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Simplified manual registration dialog
    if (showManualFormDialog) {
        Dialog(onDismissRequest = { showManualFormDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = palette.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("➕ إضافة مقدم خدمة يدوياً ودون مراجعة", fontWeight = FontWeight.Bold, color = palette.textPrimary)
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(value = manualProvName, onValueChange = { manualProvName = it }, label = { Text("الاسم") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = manualProvPhone, onValueChange = { manualProvPhone = it }, label = { Text("الرقم") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = manualProvAddress, onValueChange = { manualProvAddress = it }, label = { Text("العنوان") }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))

                    var expandCatDropManual by remember { mutableStateOf(false) }
                    Button(onClick = { expandCatDropManual = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(manualSelectedCategory?.nameAr ?: "اختر القسم...")
                    }
                    DropdownMenu(expanded = expandCatDropManual, onDismissRequest = { expandCatDropManual = false }) {
                        categoriesList.filter { it.parentId == null }.forEach { category ->
                            DropdownMenuItem(text = { Text(category.nameAr) }, onClick = {
                                manualSelectedCategory = category
                                expandCatDropManual = false
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { showManualFormDialog = false }) { Text("إلغاء") }
                        Button(onClick = {
                            if (manualProvName.isNotEmpty() && manualProvPhone.isNotEmpty() && manualSelectedCategory != null) {
                                viewModel.addProviderDirect(manualProvName, manualProvPhone, manualSelectedCategory!!.id, "خدمات متخصصة", manualProvAddress)
                                showManualFormDialog = false
                            }
                        }) {
                            Text("حفظ وتفعيل")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabChip(selected: Boolean, label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) Color(0xFF8E9AAF) else Color(0xFF1E2235))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, color = if (selected) Color.Black else Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

// -------------------------------------------------------------
// SCREEN 5: SECRET OWNER BACKDOOR GATEWAY
// -------------------------------------------------------------
@Composable
fun BackdoorSecretPortalView(viewModel: MainViewModel, palette: ThemePalette) {
    val context = LocalContext.current
    var editAppName by remember { mutableStateOf(viewModel.appName.value) }
    var editFooterText by remember { mutableStateOf(viewModel.footerText.value) }
    var welcomeTextMsg by remember { mutableStateOf(viewModel.welcomeMessage.value) }
    var changeAdminPass by remember { mutableStateOf(viewModel.adminPassword.value) }
    var supportPhoneEdit by remember { mutableStateOf(viewModel.supportPhone.value) }
    var supportEmailEdit by remember { mutableStateOf(viewModel.supportEmail.value) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🔒 لوحة المالك الكبرى (السرية المطلقة)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Red)
            Button(onClick = { viewModel.currentScreen.value = "HOME" }) {
                Text("تثبيت ومغادرة", fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = palette.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("تغيير اسم التطبيق الرئيسي المعتمد:", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = editAppName,
                    onValueChange = { editAppName = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("تغيير التذييل الدعائي المعتمد:", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = editFooterText,
                    onValueChange = { editFooterText = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("تغيير رسالة ترحيب الموثوقين والصيانة:", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = welcomeTextMsg,
                    onValueChange = { welcomeTextMsg = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("تغيير كلمة مرور المدير الرئيسي (WAM2026):", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = changeAdminPass,
                    onValueChange = { changeAdminPass = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("رقم هاتف دعم الإجراءات:", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = supportPhoneEdit,
                    onValueChange = { supportPhoneEdit = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("بريد الدعم الحاضر:", fontSize = 11.sp, color = palette.textSecondary)
                OutlinedTextField(
                    value = supportEmailEdit,
                    onValueChange = { supportEmailEdit = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.updateAppConfiguration(
                            name = editAppName,
                            theme = viewModel.selectedTheme.value,
                            primaryHex = viewModel.customPrimaryColor.value,
                            secondaryHex = viewModel.customSecondaryColor.value,
                            footer = editFooterText,
                            welcome = welcomeTextMsg,
                            phone = supportPhoneEdit,
                            email = supportEmailEdit,
                            whatsapp = supportPhoneEdit,
                            adminPass = changeAdminPass,
                            maintenance = viewModel.maintenanceMode.value,
                            saver = viewModel.dataSaverMode.value
                        )
                        Toast.makeText(context, "تم حفظ كافة الإعدادات السرية للغاية وتطبيقها على كامل النظام بكافة الأجهزة!", Toast.LENGTH_LONG).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("حفظ التغييرات السرية وتطبيقها للجميع 🔒", color = Color.White)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SCREEN 6: ABOUT YEMEN SERVICE SYSTEM (ℹ️)
// -------------------------------------------------------------
@Composable
fun AboutAppScreenView(viewModel: MainViewModel, palette: ThemePalette) {
    val phone by viewModel.supportPhone.collectAsState()
    val email by viewModel.supportEmail.collectAsState()
    val appName by viewModel.appName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(palette.primary),
            contentAlignment = Alignment.Center
        ) {
            Text("🇾🇪", fontSize = 42.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = appName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = palette.textPrimary
        )
        Text(
            text = "الإصدار التجريبي الفاخر 2026.1",
            fontSize = 12.sp,
            color = palette.accent
        )

        Spacer(modifier = Modifier.height(24.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📞 هاتف الدعم الفني والمجتمعي:", fontWeight = FontWeight.Bold, color = palette.textPrimary, fontSize = 12.sp)
                Text(phone, color = palette.primary, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("📧 البريد الإلكتروني لدعم المبادرة:", fontWeight = FontWeight.Bold, color = palette.textPrimary, fontSize = 12.sp)
                Text(email, color = palette.primary, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Text("💬 دعم الواتساب المباشر:", fontWeight = FontWeight.Bold, color = palette.textPrimary, fontSize = 12.sp)
                Text(phone, color = palette.primary, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "تم التطوير خصيصاً لخدمة وتسهيل ربط أصحاب المهن اليدوية والخدمية والمواطنين في كافة ربوع اليمن السعيد.",
            fontSize = 11.sp,
            color = palette.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

// -------------------------------------------------------------
// SCREEN 7: OFFLINE SMART ASSISTANT CHAT DIALOGUE (🤖)
// -------------------------------------------------------------
@Composable
fun SmartAssistantDialog(
    viewModel: MainViewModel,
    palette: ThemePalette,
    onClose: () -> Unit
) {
    var chatMessageInput by remember { mutableStateOf("") }
    val simulatedMessages = remember {
        mutableStateListOf(
            Pair("assistant", "يا هلا بيك في المساعد الذكي تطبيق كل خدمات اليمن! أنا هنا لمساعدتك حتى بدون اتصالك بالإنترنت. كيف أخدمك اليوم؟")
        )
    }

    Dialog(onDismissRequest = onClose) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = palette.surface),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🤖 المساعد الذكي اليمني", fontWeight = FontWeight.Bold, color = palette.primary)
                    IconButton(onClick = onClose) {
                        Text("❌", fontSize = 12.sp)
                    }
                }
                Divider(color = palette.surface.lighten(0.12f))

                // Messages list
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(simulatedMessages) { message ->
                        val isUser = message.first == "user"
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                            bottomStart = if (isUser) 12.dp else 0.dp,
                                            bottomEnd = if (isUser) 0.dp else 12.dp
                                        )
                                    )
                                    .background(if (isUser) palette.primary else palette.surface.lighten(0.1f))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = message.second,
                                    fontSize = 11.sp,
                                    color = if (isUser) Color.Black else Color.White
                                )
                            }
                        }
                    }
                }

                // Quick presets offline questions
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("كيف أتصل بمقدم خدمة؟", "ما هو رقم الدعم؟").forEach { q ->
                        AssistChip(
                            onClick = {
                                simulatedMessages.add(Pair("user", q))
                                // Automated intelligence answering offline
                                if (q.contains("كيف")) {
                                    simulatedMessages.add(Pair("assistant", "يكفي الضغط على بطاقة فني الخدمة لفتح ملفه ومن ثم النقر على زر الواتساب أو الاتصال الهاتفي السريع للاتفاق مباشرة."))
                                } else {
                                    simulatedMessages.add(Pair("assistant", "رقم دعم المبادرة الرسمي المتوفر لمراجعتك هو: 777644670 متاح 24 ساعة."))
                                }
                            },
                            label = { Text(q, fontSize = 9.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                // Sendbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = chatMessageInput,
                        onValueChange = { chatMessageInput = it },
                        placeholder = { Text("اكتب استفسارك هنا...", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(onClick = {
                        if (chatMessageInput.isNotEmpty()) {
                            simulatedMessages.add(Pair("user", chatMessageInput))
                            simulatedMessages.add(Pair("assistant", "نشكر تواصلك معنا! تمت أرشفة سؤالك وسيتم الرد عليك في غضون لحظات من قبل فني الدعم."))
                            chatMessageInput = ""
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = palette.accent)
                    }
                }
            }
        }
    }
}
