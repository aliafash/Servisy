package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.*
import com.example.ui.MainViewModel
import java.util.UUID

class MainActivity : ComponentActivity() {
    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val settingsState by viewModel.settings.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()

            // Dynamic theme palette selection based on settingsState Theme ID or custom color codes
            val colors = remember(settingsState.activeThemeId, settingsState.customPrimaryHex, settingsState.customSecondaryHex) {
                resolveThemePalette(settingsState)
            }

            // Implement custom back press double tap mechanics
            val context = LocalContext.current
            BackHandler {
                val handled = viewModel.goBack()
                if (!handled) {
                    val now = System.currentTimeMillis()
                    if (now - lastBackPressTime < 2000) {
                        finish() // Exit application
                    } else {
                        lastBackPressTime = now
                        Toast.makeText(context, "اضغط مرة أخرى للخروج من التطبيق", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            MaterialTheme(
                colorScheme = colors.scheme
            ) {
                // Force RTL context globally for Arabic Yemen local market
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colors.background
                    ) {
                        AppNavigator(viewModel = viewModel, themeColors = colors)
                    }
                }
            }
        }
    }
}

// ------ Dynamic Visual Palette Definitions ------
data class VisualThemePalette(
    val activeId: String,
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color,
    val gradientBrush: Brush,
    val scheme: ColorScheme
)

fun resolveThemePalette(settings: AdminSettingsEntity): VisualThemePalette {
    return when (settings.activeThemeId) {
        "COSMIC_SILVER" -> {
            val primary = Color(0xFF4B5563) // Faded Slate
            val secondary = Color(0xFF1F2937) // Midnight Charcoal
            val background = Color(0xFF111827) // Dark Obsidian
            val surface = Color(0xFF1F2937) // Sleek Slate box
            val textPrimary = Color(0xFFF9FAFB)
            val textSecondary = Color(0xFF9CA3AF)
            val accent = Color(0xFF60A5FA) // Cosmic Blue accent
            VisualThemePalette(
                activeId = "COSMIC_SILVER",
                primary = primary,
                secondary = secondary,
                background = background,
                surface = surface,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                accent = accent,
                gradientBrush = Brush.verticalGradient(listOf(Color(0xFF1F2937), Color(0xFF111827))),
                scheme = darkColorScheme(primary = primary, secondary = secondary, background = background, surface = surface)
            )
        }
        "ACCENT_ORANGE" -> { // Lux Golden desert vibes
            val primary = Color(0xFFD97706) // Yemen Warm Amber
            val secondary = Color(0xFF451A03) // Dark Amber / Charcoal
            val background = Color(0xFF0F0F10) 
            val surface = Color(0xFF1C1917)
            val textPrimary = Color(0xFFFFFAFA)
            val textSecondary = Color(0xFFD6D3D1)
            val accent = Color(0xFFFBBF24) // Gold Coin Sparkle
            VisualThemePalette(
                activeId = "ACCENT_ORANGE",
                primary = primary,
                secondary = secondary,
                background = background,
                surface = surface,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                accent = accent,
                gradientBrush = Brush.verticalGradient(listOf(Color(0xFF292524), Color(0xFF0F0F10))),
                scheme = darkColorScheme(primary = primary, secondary = secondary, background = background, surface = surface)
            )
        }
        "CUSTOM_THEME" -> {
            val primary = try { Color(android.graphics.Color.parseColor(settings.customPrimaryHex)) } catch (e: Exception) { Color(0xFF059669) }
            val secondary = try { Color(android.graphics.Color.parseColor(settings.customSecondaryHex)) } catch (e: Exception) { Color(0xFF064E3B) }
            val background = Color(0xFF0A0F0D)
            val surface = Color(0xFF121D18)
            val textPrimary = Color.White
            val textSecondary = Color(0xFF94A3B8)
            val accent = Color(0xFFF59E0B)
            VisualThemePalette(
                activeId = "CUSTOM_THEME",
                primary = primary,
                secondary = secondary,
                background = background,
                surface = surface,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                accent = accent,
                gradientBrush = Brush.verticalGradient(listOf(surface, background)),
                scheme = darkColorScheme(primary = primary, secondary = secondary, background = background, surface = surface)
            )
        }
        else -> { // Default: EMERALD_YEMEN (Yemen Noble Royal green)
            val primary = Color(0xFF059669) // Emerald
            val secondary = Color(0xFF115E59) // Teal Forest
            val background = Color(0xFF022C22) // Royal Pine
            val surface = Color(0xFF064E3B) // Pine Card box
            val textPrimary = Color(0xFFF0FDF4)
            val textSecondary = Color(0xFFA7F3D0)
            val accent = Color(0xFFF59E0B) // Bright Gold Amber
            VisualThemePalette(
                activeId = "EMERALD_YEMEN",
                primary = primary,
                secondary = secondary,
                background = background,
                surface = surface,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                accent = accent,
                gradientBrush = Brush.verticalGradient(listOf(surface, background)),
                scheme = darkColorScheme(primary = primary, secondary = secondary, background = background, surface = surface)
            )
        }
    }
}

// ------ App Main Navigator ------
@Composable
fun AppNavigator(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val toastMessage by viewModel.toastFlow.collectAsState()
    val settingsState by viewModel.settings.collectAsState()
    val adminRole by viewModel.adminRole.collectAsState()
    val context = LocalContext.current

    // Modal dialog trigger states
    var showInfoDialog by remember { mutableStateOf(false) }
    var showAssistantDialog by remember { mutableStateOf(false) }
    var showChatDialog by remember { mutableStateOf(false) }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearNotification()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppHeaderBar(viewModel = viewModel, themeColors = themeColors)
        },
        bottomBar = {
            AppFooterBar(
                viewModel = viewModel,
                themeColors = themeColors,
                onInfoClick = { showInfoDialog = true }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(themeColors.background)
        ) {
            // Maintenance Mode Check (Only allows bypass for Owner/Master Admin)
            if (settingsState.isMaintenanceActive && adminRole == "GUEST") {
                MaintenanceSplashView(settingsState = settingsState, themeColors = themeColors, viewModel = viewModel)
            } else {
                when (currentScreen) {
                    "OWNER_PANEL" -> OwnerBackdoorPanelLayout(viewModel = viewModel, themeColors = themeColors)
                    "ADMIN_PANEL" -> AdminPanelLayout(viewModel = viewModel, themeColors = themeColors)
                    "REGISTER_FORM" -> ProviderRegisterFormLayout(viewModel = viewModel, themeColors = themeColors)
                    "ABOUT_APP" -> AboutAppScreenContent(viewModel = viewModel, themeColors = themeColors)
                    else -> ServicesBrowserLayout(viewModel = viewModel, themeColors = themeColors)
                }

                // Interactive Floating Bubbles controls configured by Admin settings data
                FloatingIconsOverlay(
                    settings = settingsState,
                    themeColors = themeColors,
                    onAssistantClick = { showAssistantDialog = true },
                    onChatClick = { showChatDialog = true }
                )
            }
        }
    }

    // Modal Overlays
    if (showInfoDialog) {
        AboutAppDialogView(settings = settingsState, themeColors = themeColors, onDismiss = { showInfoDialog = false })
    }

    if (showAssistantDialog) {
        SmartAssistantDialogView(viewModel = viewModel, settings = settingsState, themeColors = themeColors, onDismiss = { showAssistantDialog = false })
    }

    if (showChatDialog) {
        ChatPanelDialogView(viewModel = viewModel, themeColors = themeColors, onDismiss = { showChatDialog = false })
    }
}

// ------ Custom Top App Bar ------
@Composable
fun AppHeaderBar(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val settingsState by viewModel.settings.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(themeColors.primary)
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag("app_header_bar"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // App Title Clicking 5 times sequentially activates secret Backdoor Master Panel
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { viewModel.registerBackdoorInteraction() }
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.HomeRepairService,
                contentDescription = "Logo",
                tint = themeColors.accent,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = settingsState.appName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = settingsState.welcomeMessage.take(24) + "...",
                    fontSize = 10.sp,
                    color = themeColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // New RTL Header Buttons: 🏠 -> 🔐 -> 👤 -> 🌐 -> 🔄
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 🏠 Home Browser Layout
            IconButton(
                onClick = { viewModel.navigateTo("USER_BROWSE") },
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        if (currentScreen == "USER_BROWSE") Color.White.copy(alpha = 0.25f) else Color.Transparent,
                        CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "الرئيسية", tint = Color.White, modifier = Modifier.size(18.dp))
            }

            // 🔐 Login Screener
            IconButton(
                onClick = { viewModel.navigateTo("ADMIN_PANEL") },
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        if (currentScreen == "ADMIN_PANEL") Color.White.copy(alpha = 0.25f) else Color.Transparent,
                        CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "قفل تسجيل الدخول", tint = Color.White, modifier = Modifier.size(18.dp))
            }

            // 👤 Join register Form
            IconButton(
                onClick = { viewModel.navigateTo("REGISTER_FORM") },
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        if (currentScreen == "REGISTER_FORM") Color.White.copy(alpha = 0.25f) else Color.Transparent,
                        CircleShape
                    )
            ) {
                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "تسجيل فني", tint = Color.White, modifier = Modifier.size(18.dp))
            }

            // 🌐 Switch languages
            IconButton(
                onClick = { viewModel.switchLanguage() },
                modifier = Modifier
                    .size(34.dp)
                    .background(Color.Transparent, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Language, contentDescription = "تغيير اللغة", tint = themeColors.accent, modifier = Modifier.size(18.dp))
            }

            // 🔄 Refresh current stream
            IconButton(
                onClick = { viewModel.triggerNotification("🔄 تم مزامنة البيانات وتحديث الصفحة بنجاح!") },
                modifier = Modifier
                    .size(34.dp)
                    .background(Color.Transparent, CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "مزامنة", tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ------ Custom Small Dynamic Footer ------
@Composable
fun AppFooterBar(viewModel: MainViewModel, themeColors: VisualThemePalette, onInfoClick: () -> Unit) {
    val settingsState by viewModel.settings.collectAsState()

    if (!settingsState.hidePromoFooter) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(themeColors.secondary)
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .testTag("app_footer_bar"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Content: About Information
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onInfoClick() }
                    .padding(4.dp)
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "عن التطبيق", tint = themeColors.textSecondary, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("عن التطبيق", fontSize = 10.sp, color = themeColors.textSecondary)
            }

            // Central content: Slogan small metadata
            Text(
                text = settingsState.footerMessage,
                fontSize = 8.sp, // Custom size 50% smaller based on instructions
                fontWeight = FontWeight.Bold,
                color = themeColors.accent.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            // Right content indicator
            Text(
                text = settingsState.appVersion,
                fontSize = 8.sp,
                color = themeColors.textSecondary.copy(alpha = 0.60f)
            )
        }
    }
}

// ------ Floating Icons Overlay Container ------
@Composable
fun BoxScope.FloatingIconsOverlay(
    settings: AdminSettingsEntity,
    themeColors: VisualThemePalette,
    onAssistantClick: () -> Unit,
    onChatClick: () -> Unit
) {
    // Left side: Smart Assistant bubble Services
    if (!settings.assistantHidden) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 48.dp)
                .size(settings.assistantSize.dp)
                .background(themeColors.accent, CircleShape)
                .clickable { onAssistantClick() }
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.SmartToy, contentDescription = "المساعد", tint = Color.Black, modifier = Modifier.size(20.dp))
                Text("خدمات", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }

    // Chat Floating messenger (Above Assistant)
    if (!settings.chatHidden) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 48.dp)
                .size(settings.chatSize.dp)
                .background(themeColors.primary, CircleShape)
                .clickable { onChatClick() }
                .border(1.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Default.Chat, contentDescription = "دردشة", tint = Color.White, modifier = Modifier.size(20.dp))
                Text("دردشة", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// ------ Maintenance Banner view ------
@Composable
fun MaintenanceSplashView(settingsState: AdminSettingsEntity, themeColors: VisualThemePalette, viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.Construction, contentDescription = "تحت الصيانة", tint = themeColors.accent, modifier = Modifier.size(72.dp))
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "التطبيق في وضع الصيانة والتحديث",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.textPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "رسالة الإدارة: ${settingsState.welcomeMessage}",
            fontSize = 13.sp,
            color = themeColors.textSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { viewModel.navigateTo("ADMIN_PANEL") },
            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary)
        ) {
            Text("تسجيل دخول المالك", color = Color.White)
        }
    }
}

// ------ Main Category and Service Directory Browser Layout ------
@Composable
fun ServicesBrowserLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val categories by viewModel.categories.collectAsState()
    val filteredProviders by viewModel.filteredProviders.collectAsState()
    val selectedCategory by viewModel.selectedCategoryId.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isVipOnly by viewModel.filterVipOnly.collectAsState()
    val isAvailableOnly by viewModel.filterAvailableOnly.collectAsState()
    val activeLanguage by viewModel.currentLanguage.collectAsState()
    val citiesList by viewModel.cities.collectAsState()
    val activeCityId by viewModel.filterCityId.collectAsState()
    val radiusKm by viewModel.maxKmRadius.collectAsState()
    val neighborFilter by viewModel.filterNeighborhoodName.collectAsState()
    val phoneOrNameFilter by viewModel.phoneOrNameFilter.collectAsState()
    val bannersList by viewModel.banners.collectAsState()
    val userPoints by viewModel.currentUserPoints.collectAsState()
    val settingsState by viewModel.settings.collectAsState()

    var showFiltersPanel by remember { mutableStateOf(false) }
    var activeReportProviderId by remember { mutableStateOf<String?>(null) }
    var activeReportProviderName by remember { mutableStateOf("") }
    var reporterNameInput by remember { mutableStateOf("") }
    var reportContentInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // A. Dynamic Advertisements Banner slider (At the top of list)
        if (bannersList.isNotEmpty()) {
            BannerSliderView(banners = bannersList, themeColors = themeColors, onBannerClick = { categoryTarget ->
                if (categoryTarget.isNotEmpty()) viewModel.selectCategory(categoryTarget)
            })
            Spacer(modifier = Modifier.height(8.dp))
        }

        // B. Search Field with Expand Filters Panel button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(themeColors.surface, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = themeColors.textSecondary)
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("ابحث عن سباك، كهربائي، صنعاء...", fontSize = 13.sp, color = themeColors.textSecondary) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("search_text_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )
            
            // Dynamic Web Speech simulated voice search button toggle
            if (settingsState.isSpeechSearchEnabled) {
                IconButton(onClick = {
                    val phrases = listOf("سباك صنعاء ممتاز", "كهربائي الحصبة متاح", "تصليح مكيف", "نجارة")
                    viewModel.updateSearchQuery(phrases.random())
                    viewModel.triggerNotification("🎙️ تم سماع الصوت وتحديث الكلمات المفتاحية!")
                }) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "بحث صوتي", tint = themeColors.accent)
                }
            }

            // Expand filters toggle
            IconButton(onClick = { showFiltersPanel = !showFiltersPanel }) {
                Icon(
                    imageVector = if (showFiltersPanel) Icons.Default.FilterListOff else Icons.Default.FilterList,
                    contentDescription = "توسيع الفلاتر",
                    tint = themeColors.accent
                )
            }
        }

        // Expanded Advanced Filters Panel Drawer Card
        AnimatedVisibility(visible = showFiltersPanel) {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, themeColors.accent.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🔍 معايير البحث المتقدم:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.accent)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Row: Filter by phone/name input
                    OutlinedTextField(
                        value = phoneOrNameFilter,
                        onValueChange = { viewModel.setPhoneOrNameFilter(it) },
                        placeholder = { Text("البحث بالاسم الثلاثي أو الهاتف...", fontSize = 11.sp, color = themeColors.textSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // City Select Filter dropdown
                        Column(modifier = Modifier.weight(1f)) {
                            Text("المدينة:", fontSize = 10.sp, color = themeColors.textSecondary)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.3f))
                                    .clickable {
                                        // Simple round robin click iteration through cities
                                        val idx = citiesList.indexOfFirst { it.id == activeCityId }
                                        val nextIdx = if (idx == -1) 0 else if (idx == citiesList.size -1) -1 else idx + 1
                                        viewModel.setCityFilter(if (nextIdx == -1) null else citiesList[nextIdx].id)
                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = citiesList.firstOrNull { it.id == activeCityId }?.nameAr ?: "كل المدن",
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }

                        // Neighborhood textual input
                        Column(modifier = Modifier.weight(1f)) {
                            Text("المنطقة / الحي:", fontSize = 10.sp, color = themeColors.textSecondary)
                            OutlinedTextField(
                                value = neighborFilter,
                                onValueChange = { viewModel.setNeighborhoodFilter(it) },
                                placeholder = { Text("حدة، المنصورة...", fontSize = 11.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Radius search slider (Interactive Radius search 5 -> 10 -> 20 km)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("البحث بنطاق جغرافي (دائرة):", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("${radiusKm} كم (الحد الأقصى المسموح ${settingsState.maxSearchRadiusKm}كم)", fontSize = 11.sp, color = themeColors.accent, fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = radiusKm.toFloat(),
                            onValueChange = { viewModel.setRadiusKm(it.toInt().coerceAtMost(settingsState.maxSearchRadiusKm)) },
                            valueRange = 5f..50f,
                            steps = 4,
                            colors = SliderDefaults.colors(
                                thumbColor = themeColors.accent,
                                activeTrackColor = themeColors.accent
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // C. Filter buttons VIP and Online
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterButton(
                label = "نخبة VIP والنشطين الموصى بهم",
                isActive = isVipOnly,
                activeColor = themeColors.accent,
                inactiveColor = themeColors.surface,
                textColor = if (isVipOnly) Color.Black else themeColors.textPrimary,
                icon = Icons.Default.Star,
                onTap = { viewModel.toggleVipFilter() }
            )

            FilterButton(
                label = "المتاحين حالياً بالجوار",
                isActive = isAvailableOnly,
                activeColor = themeColors.primary,
                inactiveColor = themeColors.surface,
                textColor = if (isAvailableOnly) Color.White else themeColors.textPrimary,
                icon = Icons.Default.CheckCircle,
                onTap = { viewModel.toggleAvailableFilter() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // D. Horizontal Categories navigation chip flow
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            item {
                CategoryChip(
                    name = "الكل",
                    isSelected = selectedCategory == null,
                    themeColors = themeColors,
                    icon = Icons.Default.HomeWork,
                    onTap = { viewModel.selectCategory(null) }
                )
            }
            items(categories) { category ->
                val icon = mapIconNameToVector(category.iconName)
                CategoryChip(
                    name = category.nameAr,
                    isSelected = selectedCategory == category.id,
                    themeColors = themeColors,
                    icon = icon,
                    onTap = { viewModel.selectCategory(category.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Loyalty overview scorecard
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.2f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = themeColors.accent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("رصيد نقاط الولاء الخاص بك: $userPoints نقطة", fontSize = 11.sp, color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(themeColors.accent)
                        .clickable { viewModel.redeemLoyaltyPoints() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("استبدال خصم 🎁", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // E. Dynamic Providers list with VIP pins & Blue Tick badges
        if (filteredProviders.isEmpty()) {
            EmptyStateLayout(themeColors = themeColors)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredProviders, key = { it.id }) { provider ->
                    ProviderDisplayCard(
                        provider = provider,
                        themeColors = themeColors,
                        onCall = { phone ->
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            viewModel.triggerNotification("📞 جاري فتح شاشة مكالمة المهني...")
                        },
                        onRate = { id, rating ->
                            viewModel.submitRating(id, rating)
                        },
                        onToggleAvailability = { prov ->
                            viewModel.toggleProviderStatus(prov)
                        },
                        onShare = {
                            viewModel.rewardSharePoints()
                            viewModel.triggerNotification("🔗 تم نسخ رابط مشاركة الفني وحصلت على 20 نقطة!")
                        },
                        onReportClick = { id, name ->
                            activeReportProviderId = id
                            activeReportProviderName = name
                        }
                    )
                }
            }
        }
    }

    // Modal dialog to report abuse/abusive content
    if (activeReportProviderId != null) {
        Dialog(onDismissRequest = { activeReportProviderId = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("🛡️ الإبلاغ عن محتوى أو سلوك غير ملائم", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Red)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("المهني المستهدف: $activeReportProviderName", fontSize = 12.sp, color = themeColors.textPrimary)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text("اسمك (اختياري):", fontSize = 11.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = reporterNameInput,
                        onValueChange = { reporterNameInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("سبب البلاغ بالتفصيل:", fontSize = 11.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = reportContentInput,
                        onValueChange = { reportContentInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.sendReport(
                                    activeReportProviderId!!,
                                    activeReportProviderName,
                                    reporterNameInput,
                                    reportContentInput
                                )
                                reporterNameInput = ""
                                reportContentInput = ""
                                activeReportProviderId = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("إرسال البلاغ", color = Color.White)
                        }
                        OutlinedButton(
                            onClick = { activeReportProviderId = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("تراجع")
                        }
                    }
                }
            }
        }
    }
}

// ------ Composable Banner Horizontal Carousel / Slider view ------
@Composable
fun BannerSliderView(banners: List<BannerEntity>, themeColors: VisualThemePalette, onBannerClick: (String) -> Unit) {
    var activeIdx by remember { mutableStateOf(0) }

    // Auto timing rotation simulation
    LaunchedEffect(banners.size) {
        while (true) {
            kotlinx.coroutines.delay(10000)
            if (banners.isNotEmpty()) {
                activeIdx = (activeIdx + 1) % banners.size
            }
        }
    }

    if (banners.isNotEmpty() && activeIdx < banners.size) {
        val activeBanner = banners[activeIdx]
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clickable { onBannerClick(activeBanner.redirectUrl) },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = themeColors.secondary)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Diagonal gradient overlay block
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    themeColors.primary.copy(alpha = 0.85f),
                                    themeColors.secondary.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(themeColors.accent)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "إعلان ممول", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = activeBanner.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "اضغط للتوجيه المباشر للخدمة 🔗",
                        fontSize = 10.sp,
                        color = themeColors.textSecondary
                    )
                }
            }
        }
    }
}

// ------ Provider Display List Card ------
@Composable
fun ProviderDisplayCard(
    provider: ProviderEntity,
    themeColors: VisualThemePalette,
    onCall: (String) -> Unit,
    onRate: (String, Int) -> Unit,
    onToggleAvailability: (ProviderEntity) -> Unit,
    onShare: () -> Unit,
    onReportClick: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("provider_card_${provider.id}"),
        colors = CardDefaults.cardColors(containerColor = themeColors.surface),
        shape = RoundedCornerShape(14.dp),
        border = if (provider.isPinned) BorderStroke(1.5.dp, themeColors.accent) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // First row: Title details + Dial
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = provider.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.textPrimary
                        )
                        // Monthly membership subscription indicators
                        if (provider.subscriptionStatus == "APPROVED" || provider.isVip) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(themeColors.accent)
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text("نخبة VIP ⭐", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }

                        // Blue Verified Badge Tick
                        if (provider.isVerified) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.Verified, contentDescription = "موثق بالشارة الزرقاء", tint = Color(0xFF38BDF8), modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = themeColors.textSecondary, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = provider.area, fontSize = 11.sp, color = themeColors.textSecondary)
                    }
                }

                // Interactive Contact row icons
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Share icon
                    IconButton(
                        onClick = { onShare() },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "مشاركة الفني", tint = themeColors.accent, modifier = Modifier.size(16.dp))
                    }

                    // Dial icon
                    IconButton(
                        onClick = { onCall(provider.phone) },
                        modifier = Modifier
                            .background(themeColors.primary, CircleShape)
                            .size(34.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Phone, contentDescription = "اتصال", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Divider(color = themeColors.secondary.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 10.dp))

            // Lower Info Block
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    val average = if (provider.ratingCount > 0) provider.ratingSum.toFloat() / provider.ratingCount else 5.0f
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(String.format("%.1f", average), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.accent)
                        Spacer(modifier = Modifier.width(4.dp))
                        Row {
                            for (i in 1..5) {
                                Icon(
                                    imageVector = if (i <= average.toInt()) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = null,
                                    tint = themeColors.accent,
                                    modifier = Modifier
                                        .size(13.dp)
                                        .clickable { onRate(provider.id, i) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("(${provider.ratingCount} تقييم)", fontSize = 9.sp, color = themeColors.textSecondary)
                    }
                    Text("سعر المعاينة الأولي: ${provider.basePrice.toInt()} ر.ي", fontSize = 10.sp, color = themeColors.textSecondary)
                }

                // Status Indicator click toggler & Complaint button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Abuse ticket button
                    IconButton(
                        onClick = { onReportClick(provider.id, provider.name) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Report, contentDescription = "بلاغ عن الفني", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                    }

                    // Online toggle clickable
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (provider.isAvailable) Color(0xFF064E3B) else Color(0xFF7F1D1D))
                            .clickable { onToggleAvailability(provider) }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(7.dp).background(if (provider.isAvailable) Color.Green else Color.Red, CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (provider.isAvailable) "متاح للعمل" else "مشغول",
                            fontSize = 9.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// ------ Provider Join Registrations Application Web Form (👤) ------
@Composable
fun ProviderRegisterFormLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val categories by viewModel.categories.collectAsState()

    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var selectedCatId by remember { mutableStateOf("") }
    var addressInput by remember { mutableStateOf("") }
    var neighborhoodInput by remember { mutableStateOf("") }
    var gpsCoordsMock by remember { mutableStateOf("15.3694, 44.1910") }
    
    // Media mock selections
    var localAvatarPath by remember { mutableStateOf("") }
    var identityCardPath by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "👤 تقديم طلب انضمام كفني / مهني معتمد",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.accent
                    )
                    Text(
                        text = "املأ الاستمارة التالية بدقة للانضمام المباشر لشبكة المهنيين في اليمن:",
                        fontSize = 11.sp,
                        color = themeColors.textSecondary
                    )
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Name Full Name Check
                    Text("1. الاسم الثلاثي الكامل (إجباري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        placeholder = { Text("مثال: ماهر محمد طاهر") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            unfocusedBorderColor = themeColors.secondary
                        ),
                        singleLine = true
                    )

                    // Phone Whatsapp Number
                    Text("2. رقم الهاتف الفعال / واتساب (إجباري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it },
                        placeholder = { Text("مثال: 777644670") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )

                    // Category dropdown select - FIXED! (Loads categories dynamically, not locked!)
                    Text("3. القسم والخدمة الرئيسية (إجباري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    Text("برجاء اختيار المهنة التي تجيدها من القائمة التالية:", fontSize = 10.sp, color = themeColors.textSecondary)
                    
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(categories) { cat ->
                            val isChosen = selectedCatId == cat.id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isChosen) themeColors.accent else Color.Black.copy(alpha = 0.3f))
                                    .clickable { selectedCatId = cat.id }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cat.nameAr,
                                    fontSize = 11.sp,
                                    color = if (isChosen) Color.Black else Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Office Address
                    Text("4. مكان وعنوان مركز / مكتب العمل (إجباري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = addressInput,
                        onValueChange = { addressInput = it },
                        placeholder = { Text("مثال: صنعاء - شارع حدة أمام البريد") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Resident neighborhood area
                    Text("5. منطقة الدائرة السكنية الحالية (إجباري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = neighborhoodInput,
                        onValueChange = { neighborhoodInput = it },
                        placeholder = { Text("مثال: حي الأصبحي") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Coords GPS simulation ADDRESS autocomplete
                    Text("6. إحداثيات وموقع الخريطة GPS (اختياري - إكمال تلقائي):", fontSize = 12.sp, color = themeColors.textPrimary)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = gpsCoordsMock,
                            onValueChange = { gpsCoordsMock = it },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = { 
                                gpsCoordsMock = "15.362940, 44.195610" // mock autocomplete location
                                viewModel.triggerNotification("🗺️ تم تقدير إحداثيات حي حدة أوتوماتيكياً!")
                            },
                            modifier = Modifier.background(themeColors.secondary, CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.MyLocation, contentDescription = "تحديد موقعي", tint = themeColors.accent)
                        }
                    }
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Personal Photo Upload selectors (Gallery OR direct camera capturing!)
                    Text("7. تحميل صورة شخصية حديثة (صورة سيلفي - إجباري):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { 
                                localAvatarPath = "gallery://photos/avatar_" + UUID.randomUUID().toString().take(4) + ".webp"
                                viewModel.triggerNotification("🖼️ تم اختيار صورة من معرض الاستوديو!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.InsertPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("الاستوديو", fontSize = 11.sp, color = Color.White)
                        }

                        Button(
                            onClick = { 
                                localAvatarPath = "camera://capture/avatar_direct.webp"
                                viewModel.triggerNotification("📸 تم تشغيل الكاميرا والتقاط صورة سيلفي متميزة!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("الكاميرا 📷", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    if (localAvatarPath.isNotEmpty()) {
                        Text("تم الإرفاق بنجاح: ${localAvatarPath.take(30)}...", fontSize = 11.sp, color = Color.Green, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // ID Personal check
                    Text("8. صورة بطاقة الهوية الشخصية (اختياري):", fontSize = 12.sp, color = themeColors.textPrimary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { 
                                identityCardPath = "gallery://docs/idcard_" + UUID.randomUUID().toString().take(4) + ".webp"
                                viewModel.triggerNotification("📂 تم اختيار الهوية بنجاح!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("أقحم من الهاتف", fontSize = 11.sp, color = Color.White)
                        }

                        Button(
                            onClick = { 
                                identityCardPath = "camera://capture/idcard.webp"
                                viewModel.triggerNotification("📷 صورت بطاقة هويتك مباشرة!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("تصوير الكاميرا", fontSize = 11.sp, color = Color.White)
                        }
                    }
                    if (identityCardPath.isNotEmpty()) {
                        Text("تم الإرفاق: $identityCardPath", fontSize = 11.sp, color = themeColors.accent)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Submission trigger
                    Button(
                        onClick = {
                            if (nameInput.isBlank() || phoneInput.isBlank() || selectedCatId.isBlank()) {
                                viewModel.triggerNotification("⚠️ يرجى إدخال الحقول الإجبارية أولاً!")
                            } else {
                                viewModel.submitJoinForm(
                                    name = nameInput,
                                    phone = phoneInput,
                                    catId = selectedCatId,
                                    area = "$addressInput ($neighborhoodInput)",
                                    neighborhood = neighborhoodInput,
                                    photoPath = localAvatarPath,
                                    idCardPath = identityCardPath,
                                    gpsCoords = gpsCoordsMock
                                )
                                // Clear Fields
                                nameInput = ""
                                phoneInput = ""
                                selectedCatId = ""
                                addressInput = ""
                                neighborhoodInput = ""
                                localAvatarPath = ""
                                identityCardPath = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.accent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("تقديم طلب الانضمام للمراجعة الفورية 🚀", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
            }
        }
    }
}

// ------ Administrator Tabbed Control PanelLayout ------
@Composable
fun AdminPanelLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val adminRole by viewModel.adminRole.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val providers by viewModel.providers.collectAsState()
    val pendingList by viewModel.pendingProviders.collectAsState()
    val bannersList by viewModel.banners.collectAsState()
    val reportsList by viewModel.reports.collectAsState()
    val settingsState by viewModel.settings.collectAsState()
    val logsList by viewModel.activityLogs.collectAsState()
    val whitelistDevices by viewModel.whitelistedDevices.collectAsState()
    val citiesList by viewModel.cities.collectAsState()

    var activeTab by remember { mutableStateOf(0) } // 0: Requests, 1: Add manual, 2: Banners, 3: Categories & Cities, 4: Reports & Audits

    // Login screen drawer if current user is not validated
    if (adminRole == "GUEST") {
        AdminLoginScreen(viewModel = viewModel, themeColors = themeColors)
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Auditing Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = themeColors.accent)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("الصلاحية: $adminRole", color = themeColors.accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Button(
                    onClick = { viewModel.logout() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    modifier = Modifier.height(26.dp)
                ) {
                    Text("خروج 🚪", fontSize = 9.sp, color = Color.White)
                }
            }

            // Tab selects Row
            ScrollableTabRow(
                selectedTabIndex = activeTab,
                containerColor = themeColors.surface,
                contentColor = themeColors.accent
            ) {
                Tab(selected = activeTab == 0, onClick = { activeTab = 0 }) {
                    Text("طلبات التسجيل (${pendingList.filter { it.status == "PENDING" }.size})", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
                }
                Tab(selected = activeTab == 1, onClick = { activeTab = 1 }) {
                    Text("إضافة فني يدوياً", fontSize = 11.sp, modifier = Modifier.padding(12.dp))
                }
                Tab(selected = activeTab == 2, onClick = { activeTab = 2 }) {
                    Text("إعلانات وبنرات", fontSize = 11.sp, modifier = Modifier.padding(12.dp))
                }
                Tab(selected = activeTab == 3, onClick = { activeTab = 3 }) {
                    Text("إدارة الأقسام والمدن", fontSize = 11.sp, modifier = Modifier.padding(12.dp))
                }
                Tab(selected = activeTab == 4, onClick = { activeTab = 4 }) {
                    Text("البلاغات والتقارير", fontSize = 11.sp, modifier = Modifier.padding(12.dp))
                }
                Tab(selected = activeTab == 5, onClick = { activeTab = 5 }) {
                    Text("المزودين النشطين", fontSize = 11.sp, modifier = Modifier.padding(12.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(10.dp)
            ) {
                when (activeTab) {
                    0 -> AdminPendingRequestsTab(pendingList = pendingList, themeColors = themeColors, onApprove = { viewModel.approveRequest(it) }, onReject = { p, reason -> viewModel.rejectRequest(p, reason) })
                    1 -> AdminManualAddTab(categories = categories, themeColors = themeColors, onAdd = { name, phone, cat, area, price, premium ->
                        viewModel.addNewProvider(name, phone, cat, area, premium, price)
                    })
                    2 -> AdminBannersTab(banners = bannersList, themeColors = themeColors, onAdd = { title, url, redirect, type, size, dur ->
                        viewModel.addNewBanner(title, url, redirect, type, size, dur)
                    }, onDelete = { viewModel.deleteBanner(it) })
                    3 -> AdminCategoriesCitiesTab(categories = categories, cities = citiesList, themeColors = themeColors, viewModel = viewModel)
                    4 -> AdminReportsAuditsTab(reports = reportsList, logs = logsList, themeColors = themeColors, viewModel = viewModel)
                    else -> AdminActiveProvidersTab(providers = providers, themeColors = themeColors, viewModel = viewModel)
                }
            }
        }
    }
}

// ------ Admin LogScreener view ------
@Composable
fun AdminLoginScreen(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    var userInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = themeColors.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "🔐 بوابة تسجيل الدخول الإدارية الموحدة",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColors.accent,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("يرجى إدخال اسم المستخدم وكلمة المرور للدخول للوحة الإشراف أو البوابة السرية للمالك.", fontSize = 11.sp, color = themeColors.textSecondary)

                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    label = { Text("اسم المستخدم (أو اترك فارغ للمالك)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                OutlinedTextField(
                    value = passInput,
                    onValueChange = { passInput = it },
                    label = { Text("كلمة المرور السرية") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("حفظ تسجيل الدخول في هذا الجهاز", fontSize = 11.sp, color = themeColors.textPrimary)
                }

                Button(
                    onClick = { viewModel.attemptLogin(userInput, passInput, rememberMe) },
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("تسجيل الدخول الآمن", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ------ Tab 0: Admin Reviews Pending request from FNs ------
@Composable
fun AdminPendingRequestsTab(
    pendingList: List<PendingProviderEntity>,
    themeColors: VisualThemePalette,
    onApprove: (PendingProviderEntity) -> Unit,
    onReject: (PendingProviderEntity, String) -> Unit
) {
    val context = LocalContext.current
    val activePendings = pendingList.filter { it.status == "PENDING" }

    var expandedPendingForReview by remember { mutableStateOf<PendingProviderEntity?>(null) }
    var rejectReasonText by remember { mutableStateOf("") }
    var isMagnifiedViewActive by remember { mutableStateOf(false) }
    var activeMagnifiedUri by remember { mutableStateOf("") }

    if (activePendings.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("لا توجد طلبات انضمام معلقة للمراجعة حالياً 👍", color = themeColors.textSecondary)
        }
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(activePendings) { req ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = themeColors.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("الاسم: ${req.name}", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                        Text("الهاتف: ${req.phone}", fontSize = 12.sp, color = themeColors.textSecondary)
                        Text("القسم المقترح: ${req.categoryId}", fontSize = 11.sp, color = themeColors.accent)
                        Text("العنوان: ${req.area}", fontSize = 11.sp, color = themeColors.textSecondary)

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { expandedPendingForReview = req },
                                colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("معاينة الوثائق والمراجعة 🔍", fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal dialogue to review documents with zooming/magnifying support!
    if (expandedPendingForReview != null) {
        val req = expandedPendingForReview!!
        Dialog(onDismissRequest = { expandedPendingForReview = null }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                LazyColumn(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Text("📝 تفاصيل طلب الفني: ${req.name}", fontWeight = FontWeight.Bold, color = themeColors.accent)
                        Divider(color = themeColors.secondary, modifier = Modifier.padding(vertical = 4.dp))
                    }

                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("الاسم: ${req.name}", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("الهاتف: ${req.phone}", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("القسم المطلق: ${req.categoryId}", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("العنوان: ${req.area}", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("التحي السكني: ${req.localNeighborhood}", fontSize = 11.sp, color = themeColors.textPrimary)
                            Text("الموقع GPS الخرائطي: ${req.coords}", fontSize = 11.sp, color = themeColors.accent)
                        }
                    }

                    // Photo attachments previews with magnified togglers!
                    item {
                        Text("1. المعاينة المزدوجة للصورة الشخصية (سيلفي):", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black)
                                .clickable {
                                    activeMagnifiedUri = req.photoUri.ifEmpty { "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde" }
                                    isMagnifiedViewActive = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("اضغط لمعاينة وتكبير الصورة الشخصية 🔍", color = Color.White, fontSize = 11.sp)
                        }
                    }

                    item {
                        Text("2. المعاينة المزدوجة لبطاقة الهوية الشخصية:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black)
                                .clickable {
                                    activeMagnifiedUri = req.idCardUri.ifEmpty { "https://images.unsplash.com/photo-1543269865-cbf427effbad" }
                                    isMagnifiedViewActive = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("اضغط لمعاينة وتكبير بطاقة الهوية الوطنية 🔍", color = Color.White, fontSize = 11.sp)
                        }
                    }

                    // Accept or Reject triggers
                    item {
                        OutlinedTextField(
                            value = rejectReasonText,
                            onValueChange = { rejectReasonText = it },
                            placeholder = { Text("سبب الرفض (إجباري فقط عند الرفض)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    onApprove(req)
                                    expandedPendingForReview = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("قبول المهني ✅", color = Color.White)
                            }

                            Button(
                                onClick = {
                                    if (rejectReasonText.isBlank()) {
                                        Toast.makeText(context, "الرجاء كتابة سبب الرفض لتوضيح الأمر للفني!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        onReject(req, rejectReasonText)
                                        rejectReasonText = ""
                                        expandedPendingForReview = null
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("رفض الطلب ❌", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    // Overlaid zoomable Picture viewer dialog
    if (isMagnifiedViewActive) {
        Dialog(onDismissRequest = { isMagnifiedViewActive = false }) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.DarkGray, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = themeColors.accent, modifier = Modifier.size(64.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("صورة موثقة مشفرة أمنياً", color = Color.White, fontWeight = FontWeight.Bold)
                            Text(activeMagnifiedUri, color = Color.LightGray, fontSize = 10.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { isMagnifiedViewActive = false },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.accent)
                    ) {
                        Text("إغلاق المعاينة المكبرة", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ------ Tab 1: Admin manual creation of providers ------
@Composable
fun AdminManualAddTab(
    categories: List<CategoryEntity>,
    themeColors: VisualThemePalette,
    onAdd: (String, String, String, String, Double, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var catId by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var priceStr by remember { mutableStateOf("") }
    var isVip by remember { mutableStateOf(false) }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("إضافة فني يدوياً ومباشرة دون شرط استمارة الموافقة", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("الاسم") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("رقم الهاتف") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = area, onValueChange = { area = it }, label = { Text("الموقع والمدينة") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = priceStr, onValueChange = { priceStr = it }, label = { Text("سعر المعاينة بالريال") }, modifier = Modifier.fillMaxWidth())

                    Text("حدد قسم الفني:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(categories) { cat ->
                            val chosen = catId == cat.id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (chosen) themeColors.accent else Color.Black.copy(alpha = 0.3f))
                                    .clickable { catId = cat.id }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(cat.nameAr, color = if (chosen) Color.Black else Color.White, fontSize = 11.sp)
                            }
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isVip, onCheckedChange = { isVip = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("منح شارة نخبة VIP مباشرة", fontSize = 11.sp)
                    }

                    Button(
                        onClick = {
                            if (name.isBlank() || phone.isBlank() || catId.isBlank()) {
                                // trigger error
                            } else {
                                onAdd(name, phone, catId, area, priceStr.toDoubleOrNull() ?: 2000.0, isVip)
                                name = ""
                                phone = ""
                                area = ""
                                priceStr = ""
                                isVip = false
                                catId = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إضافة مباشر للدليل", color = Color.White)
                    }
                }
            }
        }
    }
}

// ------ Tab 2: Admin Advertisement Banner configurations ------
@Composable
fun AdminBannersTab(
    banners: List<BannerEntity>,
    themeColors: VisualThemePalette,
    onAdd: (String, String, String, String, String, Int) -> Unit,
    onDelete: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var descUrl by remember { mutableStateOf("") }
    var targetRedirectCategory by remember { mutableStateOf("") }
    var displayType by remember { mutableStateOf("IMAGE") }
    var dispSize by remember { mutableStateOf("MEDIUM") }
    var dispDurSec by remember { mutableStateOf(10) }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("🖼️ إنشاء وإطلاق بنر ترويجي ممول بالرئيسية", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("عنوان البنر الدعائي الترويجي") }, modifier = Modifier.fillMaxWidth())

                    Text("نوع المحتوى الدعائي المرغوب:", fontSize = 11.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        listOf("IMAGE", "VIDEO", "TEXT").forEach { item ->
                            val active = displayType == item
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (active) themeColors.accent else Color.DarkGray)
                                    .clickable { displayType = item }
                                    .padding(8.dp)
                            ) {
                                Text(if (item == "IMAGE") "صورة 🖼️" else if (item == "VIDEO") "فيديو 📹" else "نص ترويجي 📝", color = if (active) Color.Black else Color.White, fontSize = 11.sp)
                            }
                        }
                    }

                    OutlinedTextField(value = descUrl, onValueChange = { descUrl = it }, label = { Text("رابط صورة/فيديو الخلفية الدعائية (اختياري)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = targetRedirectCategory, onValueChange = { targetRedirectCategory = it }, label = { Text("القسم المراد التوجيه إليه بدقة عند النقر (مثال: ac أو plumb)") }, modifier = Modifier.fillMaxWidth())

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("حجم الإعلان:", fontSize = 10.sp)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.2f))
                                    .clickable { dispSize = if (dispSize == "SMALL") "MEDIUM" else if (dispSize == "MEDIUM") "LARGE" else "SMALL" }
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(dispSize, fontSize = 11.sp, color = Color.White)
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text("مدة العرض (ثانية):", fontSize = 10.sp)
                            OutlinedTextField(
                                value = dispDurSec.toString(),
                                onValueChange = { dispDurSec = it.toIntOrNull() ?: 10 },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (title.isNotEmpty()) {
                                onAdd(title, descUrl, targetRedirectCategory, displayType, dispSize, dispDurSec)
                                title = ""
                                descUrl = ""
                                targetRedirectCategory = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إطلاق البنر ونشره فورا", color = Color.White)
                    }
                }
            }
        }

        item {
            Text("قائمة الإعلانات المنشورة حالياً:", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
        }

        items(banners) { b ->
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(b.title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = themeColors.textPrimary)
                        Text("النوع: ${b.displayType} | الوقت: ${b.durationSeconds}ث | القسم: ${b.redirectUrl}", fontSize = 10.sp, color = themeColors.textSecondary)
                    }
                    IconButton(onClick = { onDelete(b.id) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "مسح البنر", tint = Color.Red)
                    }
                }
            }
        }
    }
}

// ------ Tab 3: Categories & Cities feeds ------
@Composable
fun AdminCategoriesCitiesTab(
    categories: List<CategoryEntity>,
    cities: List<CityEntity>,
    themeColors: VisualThemePalette,
    viewModel: MainViewModel
) {
    var catAr by remember { mutableStateOf("") }
    var catEn by remember { mutableStateOf("") }
    var catDesc by remember { mutableStateOf("") }
    var catIcon by remember { mutableStateOf("electrical") }

    var cityAr by remember { mutableStateOf("") }
    var cityEn by remember { mutableStateOf("") }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("📁 إضافة قسم رئيسي جديد للنظام", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    OutlinedTextField(value = catAr, onValueChange = { catAr = it }, label = { Text("الاسم بالعربية") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = catEn, onValueChange = { catEn = it }, label = { Text("الاسم بالإنجليزية") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = catDesc, onValueChange = { catDesc = it }, label = { Text("الوصف التعريفي للجمهور") }, modifier = Modifier.fillMaxWidth())

                    Text("حدد الرمز التخطيطي:", fontSize = 11.sp)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(listOf("plumbing", "electrical", "ac_unit", "carpentry", "paint", "cleaning")) { opt ->
                            val chosen = catIcon == opt
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (chosen) themeColors.accent else Color.Black.copy(alpha = 0.2f))
                                    .clickable { catIcon = opt }
                                    .padding(8.dp)
                            ) {
                                Text(opt, color = if (chosen) Color.Black else Color.White, fontSize = 11.sp)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (catAr.isNotEmpty() && catEn.isNotEmpty()) {
                                viewModel.addNewCategory(catAr, catEn, catIcon, catDesc)
                                catAr = ""
                                catEn = ""
                                catDesc = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إدراج القسم مباشرة للتصفح الحرافي", color = Color.White)
                    }
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("🏙️ إضافة أو إدراج مدينة / محافظة تغطية", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    OutlinedTextField(value = cityAr, onValueChange = { cityAr = it }, label = { Text("المدينة بالعربية") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = cityEn, onValueChange = { cityEn = it }, label = { Text("المدينة بالإنجليزية") }, modifier = Modifier.fillMaxWidth())

                    Button(
                        onClick = {
                            if (cityAr.isNotEmpty() && cityEn.isNotEmpty()) {
                                viewModel.addNewCity(cityAr, cityEn)
                                cityAr = ""
                                cityEn = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ المدينة الجديدة بالقائمة", color = Color.White)
                    }
                }
            }
        }

        item {
            Text("المدن المسجلة للتصفية الجغرافية:", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
        }

        items(cities) { city ->
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${city.nameAr} (${city.nameEn})", color = themeColors.textPrimary)
                    IconButton(onClick = { viewModel.removeCity(city.id) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                    }
                }
            }
        }
    }
}

// ------ Tab 4: User Complaints reports, PDF exports and Logs audit trails ------
@Composable
fun AdminReportsAuditsTab(
    reports: List<ReportEntity>,
    logs: List<ActivityLogEntity>,
    themeColors: VisualThemePalette,
    viewModel: MainViewModel
) {
    var logsSearchQuery by remember { mutableStateOf("") }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("🛡️ شاشة إدارة التقارير والبلاغات والشكاوي", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("تصدير قوائم وملخصات البلاغات في نطاقات زمنية للجهات الرقابية والمواطنين:", fontSize = 11.sp, color = themeColors.textSecondary)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.exportComplaintsToCSV() },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("تصدير CSV مميز 📁", fontSize = 11.sp, color = Color.White)
                        }

                        Button(
                            onClick = { viewModel.exportComplaintsToPDF() },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("تصدير أسبوعي PDF 📃", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        item {
            Text("بلاغات وشكاوى المستخدمين المعلقة (${reports.size}):", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
        }

        if (reports.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("لم يقدم أي مستخدم أي بلاغ سلبي ضد مقدمي الخدمات! السجل نظيف تماماً 🌟", color = themeColors.textSecondary, fontSize = 12.sp)
                }
            }
        }

        items(reports) { rep ->
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("الشاكي: ${rep.reporterName}", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                        IconButton(onClick = { viewModel.clearReport(rep.id) }) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "معالجة الشكوى", tint = Color.Green)
                        }
                    }
                    Text("المهني المشتكى عليه: ${rep.providerName}", fontSize = 12.sp, color = themeColors.accent)
                    Text("محتوى الشكوى: ${rep.content}", fontSize = 11.sp, color = themeColors.textSecondary)
                    Text("التاريخ: ${rep.timestamp}", fontSize = 9.sp, color = themeColors.textSecondary)
                }
            }
        }

        // Audit Trail Logs listing for Admin verification checks!
        item {
            Text("📋 سجلات تدقيق نشاط المشرفين والمدراء (Audit Logs):", fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
        }

        item {
            OutlinedTextField(
                value = logsSearchQuery,
                onValueChange = { logsSearchQuery = it },
                placeholder = { Text("ابحث في السجلات والقرارات الإدارية...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        items(logs.filter { it.actionDesc.contains(logsSearchQuery, ignoreCase = true) || it.adminName.contains(logsSearchQuery, ignoreCase = true) }) { log ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.25f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = log.actionDesc, fontSize = 11.sp, color = themeColors.textPrimary)
                        Text(text = "القائم بالإجراء: ${log.adminName} | ${log.timestamp}", fontSize = 9.sp, color = themeColors.textSecondary)
                    }
                }
            }
        }
    }
}

// ------ Tab 5: List of active service providers supporting Pin, Recommendation and Verified Badges ---
@Composable
fun AdminActiveProvidersTab(
    providers: List<ProviderEntity>,
    themeColors: VisualThemePalette,
    viewModel: MainViewModel
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("⭐️ لوحة التحكم بالاشتراكات والتثبيت", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    Text("هنا يمكنك تثبيت مهنيين في مقدمة التصفح، منحهم شارة التوثيق الزرقاء، أو تفعيل ترقيات VIP المدفوعة للمحافظة عليها بمواقع تصفح مميزة.", fontSize = 11.sp, color = themeColors.textSecondary)
                }
            }
        }

        items(providers) { prov ->
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                border = if (prov.isPinned) BorderStroke(1.dp, themeColors.accent) else null
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(prov.name, fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                            Text("القسم: ${prov.categoryId} | المدينة: ${prov.area}", fontSize = 11.sp, color = themeColors.textSecondary)
                        }

                        IconButton(onClick = { viewModel.removeProvider(prov.id) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "حذف المهني", tint = Color.Red)
                        }
                    }

                    // Toggles Row: Pin / Recommend / Verify / Premium Subscription
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // Pin Toggle button (forces top position)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (prov.isPinned) themeColors.accent else Color.DarkGray)
                                .clickable { viewModel.pinProvider(prov.id, !prov.isPinned) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (prov.isPinned) "مثبت 📌" else "تثبيت بالصدارة", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (prov.isPinned) Color.Black else Color.White)
                        }

                        // Recommend toggle button (golden star recommended list)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (prov.isRecommended) themeColors.accent else Color.DarkGray)
                                .clickable { viewModel.recommendProvider(prov.id, !prov.isRecommended) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (prov.isRecommended) "موصى به ⭐" else "توصية هامة", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (prov.isRecommended) Color.Black else Color.White)
                        }

                        // blue verification icon toggle
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (prov.isVerified) Color(0xFF0284C7) else Color.DarkGray)
                                .clickable { viewModel.verifyProviderBadge(prov.id, !prov.isVerified) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (prov.isVerified) "موثق 🔷" else "توثيق شارة زرقاء", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        // Premium Monthly membership configuration
                        Box(
                            modifier = Modifier
                                .weight(1.2f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (prov.subscriptionStatus == "APPROVED") Color(0xFF15803D) else Color.DarkGray)
                                .clickable { 
                                    val nextStatus = if (prov.subscriptionStatus == "APPROVED") "NONE" else "APPROVED"
                                    viewModel.toggleProviderSubscription(prov.id, nextStatus)
                                }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (prov.subscriptionStatus == "APPROVED") "عضوية ذهبية نشطة" else "تفعيل عضوية ممتازة", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// ------ OWNER SECRET PORTAL BACKDOOR SETTINGS LAYOUT (🏠 LOGO 5 CLICKS) ------
@Composable
fun OwnerBackdoorPanelLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val settingsState by viewModel.settings.collectAsState()
    val adminRole by viewModel.adminRole.collectAsState()

    var appNameInput by remember { mutableStateOf(settingsState.appName) }
    var welcomeInput by remember { mutableStateOf(settingsState.welcomeMessage) }
    var footerInput by remember { mutableStateOf(settingsState.footerMessage) }
    var supportPhoneInput by remember { mutableStateOf(settingsState.supportPhone) }
    var supportEmailInput by remember { mutableStateOf(settingsState.supportEmail) }
    var supportWhatsappInput by remember { mutableStateOf(settingsState.supportWhatsapp) }
    
    // Toggles
    var isMaintenance by remember { mutableStateOf(settingsState.isMaintenanceActive) }
    var hideFooter by remember { mutableStateOf(settingsState.hidePromoFooter) }
    var robotHidden by remember { mutableStateOf(settingsState.assistantHidden) }
    var robotSize by remember { mutableStateOf(settingsState.assistantSize) }
    var chatHidden by remember { mutableStateOf(settingsState.chatHidden) }
    var chatSize by remember { mutableStateOf(settingsState.chatSize) }

    var radiusKm by remember { mutableStateOf(settingsState.maxSearchRadiusKm) }
    var isSpeech by remember { mutableStateOf(settingsState.isSpeechSearchEnabled) }

    // Admin password change inputs
    val splitted = settingsState.adminPassword.split(":")
    var WAMUserText by remember { mutableStateOf(splitted.getOrNull(0) ?: "WAM2026") }
    var WAMPassText by remember { mutableStateOf(splitted.getOrNull(1) ?: "maher736462") }

    // Custom colors definitions
    var customPrimaryColorHexText by remember { mutableStateOf(settingsState.customPrimaryHex) }
    var customSecondaryColorHexText by remember { mutableStateOf(settingsState.customSecondaryHex) }

    var isDataSaverOn by remember { mutableStateOf(settingsState.isDataSaverActive) }
    var imageQualitySlider by remember { mutableStateOf(settingsState.imageQualityPercent) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("👑 البوابة الخلفية السرية للمالك (Owner Console)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = themeColors.accent)
                    Text("أنت في واجهة الإعدادات الجوهرية الآمنة للمالك ماهر. لا تظهر هذه الواجهة في أي رسالة للمستخدمين.", fontSize = 11.sp, color = themeColors.textSecondary)
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("1. معايير الهوية ولون المظهر (Theme UI Color):", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    // Standard theme switcher option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ThemeSelectorBox(
                            title = "الأخضر اليماني 🌌",
                            isActive = settingsState.activeThemeId == "EMERALD_YEMEN",
                            primaryColor = Color(0xFF059669),
                            onSelect = { viewModel.updateTheme("EMERALD_YEMEN") }
                        )

                        ThemeSelectorBox(
                            title = "الفضي الكوني ✨",
                            isActive = settingsState.activeThemeId == "COSMIC_SILVER",
                            primaryColor = Color(0xFF6B7280),
                            onSelect = { viewModel.updateTheme("COSMIC_SILVER") }
                        )

                        ThemeSelectorBox(
                            title = "شمس الذهبي 🟢",
                            isActive = settingsState.activeThemeId == "ACCENT_ORANGE",
                            primaryColor = Color(0xFFD97706),
                            onSelect = { viewModel.updateTheme("ACCENT_ORANGE") }
                        )

                        ThemeSelectorBox(
                            title = "ثيم مخصص 🛠️",
                            isActive = settingsState.activeThemeId == "CUSTOM_THEME",
                            primaryColor = Color(0xFF475569),
                            onSelect = { viewModel.updateTheme("CUSTOM_THEME") }
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    if (settingsState.activeThemeId == "CUSTOM_THEME") {
                        Text("أدخل أكواد ألوان الهوية الخاصة بك (نظام Hex):", fontSize = 11.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = customPrimaryColorHexText,
                                onValueChange = { customPrimaryColorHexText = it },
                                label = { Text("اللون رئيسي") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = customSecondaryColorHexText,
                                onValueChange = { customSecondaryColorHexText = it },
                                label = { Text("اللون فرعي") },
                                modifier = Modifier.weight(1f),
                                singleLine = true
                            )
                        }
                    }

                    OutlinedTextField(value = appNameInput, onValueChange = { appNameInput = it }, label = { Text("تغيير اسم التطبيق:") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = welcomeInput, onValueChange = { welcomeInput = it }, label = { Text("رسالة ترحيبية وضع الصيانة:") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = footerInput, onValueChange = { footerInput = it }, label = { Text("تغيير التذييل الترويجي (MAW 777644670):") }, modifier = Modifier.fillMaxWidth())
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("2. قنوات الدعم والتواصل المباشرة (عن التطبيق):", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    
                    OutlinedTextField(value = supportPhoneInput, onValueChange = { supportPhoneInput = it }, label = { Text("رقم هاتف الدعم:") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = supportEmailInput, onValueChange = { supportEmailInput = it }, label = { Text("البريد الإلكتروني المعتمد:") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = supportWhatsappInput, onValueChange = { supportWhatsappInput = it }, label = { Text("رقم واتساب الدعم المباشر:") }, modifier = Modifier.fillMaxWidth())
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("3. حساب المدير الرئيسي (Admin WAM2026):", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    Text("اضبط اسم مستخدم ورمز مرور مدير الواجهة الأمامية الرئيسي من هنا لتغييرها فوراً ومزامنتها بكل الهواتف:", fontSize = 11.sp, color = themeColors.textSecondary)
                    
                    OutlinedTextField(value = WAMUserText, onValueChange = { WAMUserText = it }, label = { Text("اسم مستخدم المدير:") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = WAMPassText, onValueChange = { WAMPassText = it }, label = { Text("رمز المرور للمدير:") }, modifier = Modifier.fillMaxWidth())

                    Button(
                        onClick = { viewModel.changeAdminCredentials(WAMUserText, WAMPassText) },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.secondary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("مزامنة وتعديل حساب WAM2026", color = Color.White)
                    }
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("4. قنوات المساعد الذكي وأيقونة الدردشة العائمة:", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = robotHidden, onCheckedChange = { robotHidden = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("إخفاء تام لأيقونة المساعد الذكي \"خدمات\" 🤖", fontSize = 11.sp)
                    }

                    OutlinedTextField(
                        value = robotSize.toString(),
                        onValueChange = { robotSize = it.toIntOrNull() ?: 54 },
                        label = { Text("حجم أيقونة المساعد الذكي (بالبكسل/dp)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = chatHidden, onCheckedChange = { chatHidden = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("إخفاء تام لزر المحادثة والدردشة العائمة 💬", fontSize = 11.sp)
                    }

                    OutlinedTextField(
                        value = chatSize.toString(),
                        onValueChange = { chatSize = it.toIntOrNull() ?: 54 },
                        label = { Text("حجم أيقونة الدردشة العائمة:") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("5. أوضاع الشبكة وتوفير البيانات (Data Saver Active):", fontWeight = FontWeight.Bold, color = themeColors.accent)
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isDataSaverOn, onCheckedChange = { isDataSaverOn = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("تفعيل وضع توفير البيانات تلقائياً (تخفيض حجم الذاكرة)", fontSize = 11.sp)
                    }

                    Text("جودة صور ملفات الفنيين الموصى به (${imageQualitySlider}%):", fontSize = 11.sp)
                    Slider(
                        value = imageQualitySlider.toFloat(),
                        onValueChange = { imageQualitySlider = it.toInt() },
                        valueRange = 50f..100f,
                        colors = SliderDefaults.colors(thumbColor = themeColors.accent)
                    )
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = themeColors.surface)) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("6. وضع الصيانة وقواعد النسخ الاحتياطي المستقر:", fontWeight = FontWeight.Bold, color = themeColors.accent)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isMaintenance, onCheckedChange = { isMaintenance = it })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("تفعيل وضع الصيانة العام (حجب التصفح لعرض واجهة splash)", fontSize = 11.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.triggerManualBackup() },
                            colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("أخذ نسخة احتياطية 💿", fontSize = 11.sp, color = Color.White)
                        }

                        Button(
                            onClick = { viewModel.triggerRestoreBackup() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("استعادة البيانات", fontSize = 11.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.updateBackdoorSettings(
                            appName = appNameInput,
                            welcomeMsg = welcomeInput,
                            footerMsg = footerInput,
                            themeId = settingsState.activeThemeId,
                            supportPhone = supportPhoneInput,
                            supportEmail = supportEmailInput,
                            supportWhatsapp = supportWhatsappInput,
                            isMaintenance = isMaintenance,
                            hiddenFooter = hideFooter,
                            botHidden = robotHidden,
                            botSize = robotSize,
                            chatHidden = chatHidden,
                            chatSize = chatSize,
                            radiusKm = radiusKm,
                            isSpeech = isSpeech,
                            isDataSaver = isDataSaverOn,
                            imgQuality = imageQualitySlider
                        )
                        viewModel.navigateTo("USER_BROWSE")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.accent),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("حفظ والبدء بالتحديث المباشر 💾", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.navigateTo("USER_BROWSE") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("تراجع")
                }
            }
        }
    }
}

// ------ Dialog for 'About App' information context (ℹ️) ------
@Composable
fun AboutAppDialogView(settings: AdminSettingsEntity, themeColors: VisualThemePalette, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = themeColors.surface),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, themeColors.accent.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("ℹ️ عن تطبيق خدمات اليمن المطور", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = themeColors.accent)
                Divider(color = themeColors.secondary)

                Text("اسم التطبيق المعتمد: ${settings.appName}", fontSize = 12.sp, color = themeColors.textPrimary)
                Text("الإصدار الجاري: ${settings.appVersion}", fontSize = 11.sp, color = themeColors.textSecondary)
                Text("التذييل المبرمج: ${settings.footerMessage}", fontSize = 10.sp, color = themeColors.textSecondary)

                Spacer(modifier = Modifier.height(10.dp))
                Text("الدعم الفني المباشر للمستخدمين والمحترفين:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = themeColors.accent)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = themeColors.accent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("هاتف الدعم: ${settings.supportPhone}", fontSize = 11.sp, color = themeColors.textPrimary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = themeColors.accent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("البريد الإلكتروني: ${settings.supportEmail}", fontSize = 11.sp, color = themeColors.textPrimary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Forum, contentDescription = null, tint = themeColors.accent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("رقم واتساب المشرفين: ${settings.supportWhatsapp}", fontSize = 11.sp, color = themeColors.textPrimary)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("إغلاق نافذة الدعم", color = Color.White)
                }
            }
        }
    }
}

// ------ Unused screen content fallback representing informational AboutApp page ------
@Composable
fun AboutAppScreenContent(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val settingsState by viewModel.settings.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AboutAppDialogView(settings = settingsState, themeColors = themeColors, onDismiss = { viewModel.navigateTo("USER_BROWSE") })
    }
}

// ------ Modal Dialog representing smart Offline/Online assistant Services 🤖 ------
@Composable
fun SmartAssistantDialogView(
    viewModel: MainViewModel,
    settings: AdminSettingsEntity,
    themeColors: VisualThemePalette,
    onDismiss: () -> Unit
) {
    var queryText by remember { mutableStateOf("") }
    var chatHistoryText by remember { mutableStateOf("🤖 أهلاً بك مع المساعد الذكي غير المحدود! كيف يمكنني إفادتك اليوم؟ \n(الأسئلة المقترحة: ما هي الأقسام؟ أو كيف أتصل بفني؟)") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = themeColors.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.SmartToy, contentDescription = null, tint = themeColors.accent)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("المساعد الذكي (يعمل بدون انترنت ✈️)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Divider(color = themeColors.secondary)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(text = chatHistoryText, fontSize = 11.sp, color = Color.White)
                }

                OutlinedTextField(
                    value = queryText,
                    onValueChange = { queryText = it },
                    placeholder = { Text("اكتب سؤالك بخصوص أقسام خدمات اليمن...") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (queryText.isNotEmpty()) {
                                val reply = viewModel.getBotOfflineAnswer(queryText)
                                chatHistoryText += "\n\n👤 أنت: $queryText\n🤖 المساعد: $reply"
                                queryText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.accent),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("طرح السؤال ❓", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text("إغلاق")
                    }
                }
            }
        }
    }
}

// ------ Custom Dialog representing Real-time interactive Chat tool (💬) ------
@Composable
fun ChatPanelDialogView(viewModel: MainViewModel, themeColors: VisualThemePalette, onDismiss: () -> Unit) {
    val messagesList by viewModel.chatMessages.collectAsState()
    var userMessageInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = themeColors.surface),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("💬 محادثة فورية مباشرة (أوفلاين/أونلاين)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = themeColors.accent)
                Divider(color = themeColors.secondary)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(messagesList) { msg ->
                        val isSelf = msg.senderId == viewModel.currentUserId.value
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = if (isSelf) Alignment.End else Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 8.dp, topEnd = 8.dp,
                                            bottomStart = if (isSelf) 8.dp else 0.dp,
                                            bottomEnd = if (isSelf) 0.dp else 8.dp
                                        )
                                    )
                                    .background(if (isSelf) themeColors.primary else Color.DarkGray)
                                    .padding(8.dp)
                            ) {
                                Text(msg.messageText, fontSize = 11.sp, color = Color.White)
                            }
                            Text(msg.senderName, fontSize = 8.sp, color = themeColors.textSecondary)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = userMessageInput,
                        onValueChange = { userMessageInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("اكتب رسالتك للإدارة أو المهني...") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            if (userMessageInput.isNotEmpty()) {
                                viewModel.sendSimpleChatMessage(userMessageInput, isWithAdmin = true)
                                userMessageInput = ""
                            }
                        },
                        modifier = Modifier.background(themeColors.accent, CircleShape)
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "تنفيذ الإرسال", tint = Color.Black)
                    }
                }

                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("إغلاق جلسة الدردشة")
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    themeColors: VisualThemePalette,
    icon: ImageVector,
    onTap: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) themeColors.accent else themeColors.surface
        ),
        border = BorderStroke(1.dp, if (isSelected) Color.White else themeColors.secondary),
        modifier = Modifier
            .clickable { onTap() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.Black else themeColors.accent,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = name,
                fontSize = 11.sp,
                color = if (isSelected) Color.Black else themeColors.textPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FilterButton(
    label: String,
    isActive: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    textColor: Color,
    icon: ImageVector,
    onTap: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) activeColor else inactiveColor
        ),
        modifier = Modifier
            .clickable { onTap() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EmptyStateLayout(message: String = "لم يتم العثور على مقدمي خدمات يطابقون خيارات البحث الخاصة بك. حاول تعديل المدينة أو تصفية البحث.", themeColors: VisualThemePalette) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = themeColors.textSecondary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = message,
            fontSize = 12.sp,
            color = themeColors.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RowScope.ThemeSelectorBox(
    title: String,
    isActive: Boolean,
    primaryColor: Color,
    onSelect: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, if (isActive) Color.White else Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) primaryColor else Color.DarkGray
        ),
        modifier = Modifier
            .weight(1f)
            .height(72.dp)
            .clickable { onSelect() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(primaryColor)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun mapIconNameToVector(name: String): ImageVector {
    return when (name.lowercase()) {
        "plumbing", "plumb" -> Icons.Default.Build
        "electrical", "electric" -> Icons.Default.ElectricBolt
        "ac_unit", "ac" -> Icons.Default.AcUnit
        "carpentry" -> Icons.Default.Hardware
        "paint" -> Icons.Default.Brush
        "cleaning" -> Icons.Default.CleanHands
        "doctor", "medical" -> Icons.Default.MedicalServices
        "delivery", "transport" -> Icons.Default.LocalShipping
        else -> Icons.Default.Construction
    }
}

