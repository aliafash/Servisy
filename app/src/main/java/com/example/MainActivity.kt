package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CategoryEntity
import com.example.data.ProviderEntity
import com.example.data.AdminSettingsEntity
import com.example.ui.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val settingsState by viewModel.settings.collectAsState()

            // Resolve dynamic Colors based on settingsState Theme ID
            val colors = remember(settingsState.activeThemeId) {
                resolveThemePalette(settingsState.activeThemeId)
            }

            MaterialTheme(
                colorScheme = colors.scheme
            ) {
                // Force RTL Layout Direction context for Arabic Localization in Yemen
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

// ------ Theme Palette Mapping Structure ------
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

fun resolveThemePalette(themeId: String): VisualThemePalette {
    return when (themeId) {
        "COSMIC_SILVER" -> {
            val primary = Color(0xFF6B7280) // Sleek Slate
            val secondary = Color(0xFF374151) // Charcoal
            val background = Color(0xFF111827) // Obsidian Light Dark
            val surface = Color(0xFF1F2937) // Deep Gray Box
            val textPrimary = Color(0xFFF9FAFB)
            val textSecondary = Color(0xFF9CA3AF)
            val accent = Color(0xFF60A5FA) // Cosmic Blue
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
        "ACCENT_ORANGE" -> {
            val primary = Color(0xFFD97706) // Yemen Desert Gold / Amber
            val secondary = Color(0xFF4B5563)
            val background = Color(0xFF0F0F10) 
            val surface = Color(0xFF1A1A1C)
            val textPrimary = Color(0xFFFFFAFA)
            val textSecondary = Color(0xFF909296)
            val accent = Color(0xFFFBBF24)
            VisualThemePalette(
                activeId = "ACCENT_ORANGE",
                primary = primary,
                secondary = secondary,
                background = background,
                surface = surface,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                accent = accent,
                gradientBrush = Brush.verticalGradient(listOf(Color(0xFF2C2216), Color(0xFF0F0F10))),
                scheme = darkColorScheme(primary = primary, secondary = secondary, background = background, surface = surface)
            )
        }
        else -> { // Default: EMERALD_YEMEN (Yemen Classic Green Style)
            val primary = Color(0xFF059669) // Emerald Green
            val secondary = Color(0xFF047857) // Dark Emerald
            val background = Color(0xFF022C22) // Deep Pine
            val surface = Color(0xFF064E3B) // Pine Card
            val textPrimary = Color(0xFFF0FDF4)
            val textSecondary = Color(0xFFA7F3D0)
            val accent = Color(0xFFF59E0B) // Bright Gold Accent
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
    val context = LocalContext.current

    // Observe App-wide custom notifications and show a lightweight Toast safely
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
            AppFooterBar(viewModel = viewModel, themeColors = themeColors)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(themeColors.background)
        ) {
            when (currentScreen) {
                "ADMIN_PANEL" -> AdminPanelLayout(viewModel = viewModel, themeColors = themeColors)
                else -> ServicesBrowserLayout(viewModel = viewModel, themeColors = themeColors)
            }
        }
    }
}

// ------ Custom Top App Bar ------
@Composable
fun AppHeaderBar(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val settingsState by viewModel.settings.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(themeColors.primary)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag("app_header_bar"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = settingsState.appName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "دقة • جهوزية • أمان",
                fontSize = 11.sp,
                color = themeColors.textSecondary
            )
        }

        IconButton(
            onClick = {
                if (currentScreen == "ADMIN_PANEL") {
                    viewModel.navigateTo("USER_BROWSE")
                } else {
                    viewModel.navigateTo("ADMIN_PANEL")
                }
            },
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.15f), CircleShape)
                .testTag("toggle_admin_button")
        ) {
            Icon(
                imageVector = if (currentScreen == "ADMIN_PANEL") Icons.Default.Home else Icons.Default.Settings,
                contentDescription = "الانتقال للوحة التحكم",
                tint = Color.White
            )
        }
    }
}

// ------ Custom Animated Sticky Footer ------
@Composable
fun AppFooterBar(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val settingsState by viewModel.settings.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(themeColors.secondary)
            .padding(12.dp)
            .testTag("app_footer_bar"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = settingsState.footerMessage,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = themeColors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ------ Screen Layout 1: Users Directory Browser (With category filtering and search) ------
@Composable
fun ServicesBrowserLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val context = LocalContext.current
    val categories by viewModel.categories.collectAsState()
    val filteredProviders by viewModel.filteredProviders.collectAsState()
    val selectedCategory by viewModel.selectedCategoryId.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isVipOnly by viewModel.filterVipOnly.collectAsState()
    val isAvailableOnly by viewModel.filterAvailableOnly.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        // 1. Interactive Search with Filters Panel - No CSS overlap conflicts
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(themeColors.surface, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
                tint = themeColors.textSecondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = { Text("بحث عن مهني أو منطقة في اليمن...", fontSize = 13.sp, color = themeColors.textSecondary) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("search_text_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = themeColors.textPrimary,
                    unfocusedTextColor = themeColors.textPrimary
                ),
                singleLine = true
            )
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "مسح البحث",
                        tint = themeColors.accent
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Toggle Quick Filter Buttons layout (VIP and Available Status Filters)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // VIP Filter Button
            FilterButton(
                label = "نخبة المهنيين (VIP)",
                isActive = isVipOnly,
                activeColor = themeColors.accent,
                inactiveColor = themeColors.surface,
                textColor = if (isVipOnly) Color.Black else themeColors.textPrimary,
                icon = Icons.Default.Star,
                onTap = { viewModel.toggleVipFilter() }
            )

            // Available Now Filter Button
            FilterButton(
                label = "المتاحين للعمل الآن",
                isActive = isAvailableOnly,
                activeColor = themeColors.primary,
                inactiveColor = themeColors.surface,
                textColor = if (isAvailableOnly) Color.White else themeColors.textPrimary,
                icon = Icons.Default.CheckCircle,
                onTap = { viewModel.toggleAvailableFilter() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 2. Beautiful Categories Selector Row
        Text(
            text = "تصفح خدمات الصيانة والمهن اليدوية:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.textSecondary,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
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

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Dynamic Service Providers Directory list
        Text(
            text = if (selectedCategory == null) "كل المهنيين المعتمدين في اليمن:" 
                   else "المهنيين المتاحين في التصنيف المحدد:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.textPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

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
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.triggerNotification("📞 لا يمكن فتح الاتصال في بيئة المحاكي الحالية.")
                            }
                        },
                        onRate = { id, rating ->
                            viewModel.submitRating(id, rating)
                        },
                        onToggleAvailability = { prov ->
                            viewModel.toggleProviderStatus(prov)
                        }
                    )
                }
            }
        }
    }
}

// ------ Category Chip Composable Layout ------
@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean,
    themeColors: VisualThemePalette,
    icon: ImageVector,
    onTap: () -> Unit
) {
    val bg = if (isSelected) themeColors.primary else themeColors.surface
    val border = if (isSelected) BorderStroke(1.5.dp, themeColors.accent) else BorderStroke(1.dp, themeColors.secondary.copy(alpha = 0.5f))
    val textColor = if (isSelected) Color.White else themeColors.textPrimary

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(bg)
            .border(border, RoundedCornerShape(24.dp))
            .clickable { onTap() }
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) themeColors.accent else themeColors.textSecondary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

// ------ Filter Dynamic Toggle Button ------
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
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isActive) activeColor else inactiveColor)
            .clickable { onTap() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isActive) Color.White else textColor.copy(alpha = 0.7f),
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

// ------ Service Provider Display Card Layout ------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderDisplayCard(
    provider: ProviderEntity,
    themeColors: VisualThemePalette,
    onCall: (String) -> Unit,
    onRate: (String, Int) -> Unit,
    onToggleAvailability: (ProviderEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("provider_card_${provider.id}"),
        colors = CardDefaults.cardColors(containerColor = themeColors.surface),
        shape = RoundedCornerShape(14.dp),
        border = if (provider.isVip) BorderStroke(1.5.dp, themeColors.accent) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Header Details
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = provider.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.textPrimary
                        )
                        if (provider.isVip) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Badge(
                                containerColor = themeColors.accent,
                                contentColor = Color.Black
                            ) {
                                Text("نخبة VIP", fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "الموقع",
                            tint = themeColors.textSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = provider.area,
                            fontSize = 11.sp,
                            color = themeColors.textSecondary
                        )
                    }
                }

                // Call To Action Dial button
                IconButton(
                    onClick = { onCall(provider.phone) },
                    modifier = Modifier
                        .background(themeColors.primary, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "اتصل بالمهني",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Divider(
                color = themeColors.secondary.copy(alpha = 0.3f),
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // Dynamic Rating + Pricing and Availability switch to avoid CSS overlap conflicts
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Interactive Rating
                Column {
                    val averageRating = if (provider.ratingCount > 0) provider.ratingSum.toFloat() / provider.ratingCount else 5.0f
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = String.format("%.1f", averageRating),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColors.accent
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Row {
                            for (i in 1..5) {
                                Icon(
                                    imageVector = if (i <= averageRating.toInt()) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = null,
                                    tint = themeColors.accent,
                                    modifier = Modifier
                                        .size(13.dp)
                                        .clickable { onRate(provider.id, i) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${provider.ratingCount} تقييم)",
                            fontSize = 9.sp,
                            color = themeColors.textSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "السعر البدئي: ${provider.basePrice.toInt()} ريال يمني",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = themeColors.textSecondary
                    )
                }

                // Active Switch
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (provider.isAvailable) Color(0xFF064E3B) else Color(0xFF7F1D1D))
                        .clickable { onToggleAvailability(provider) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .background(if (provider.isAvailable) Color.Green else Color.Red, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (provider.isAvailable) "متاح للعمل" else "مشغول / غير متاح",
                        fontSize = 9.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ------ Screen Layout 2: Administrator & Configuration Board Panel ------
@Composable
fun AdminPanelLayout(viewModel: MainViewModel, themeColors: VisualThemePalette) {
    val categories by viewModel.categories.collectAsState()
    val providers by viewModel.providers.collectAsState()
    val settingsState by viewModel.settings.collectAsState()

    var appNameInput by remember { mutableStateOf(settingsState.appName) }
    var footerInput by remember { mutableStateOf(settingsState.footerMessage) }
    var forceVipOnly by remember { mutableStateOf(settingsState.showVipOnly) }

    // Forms context for Category creation
    var newCatNameAr by remember { mutableStateOf("") }
    var newCatNameEn by remember { mutableStateOf("") }
    var newCatDesc by remember { mutableStateOf("") }
    var selectedIconName by remember { mutableStateOf("electrical") }

    // Forms context for Professional provider creation
    var newProvName by remember { mutableStateOf("") }
    var newProvPhone by remember { mutableStateOf("") }
    var newProvArea by remember { mutableStateOf("") }
    var newProvCatId by remember { mutableStateOf("") }
    var newProvIsVip by remember { mutableStateOf(false) }
    var newProvBasePrice by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .testTag("admin_panel_scrollable"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // CARD A: Core Layout Synchronization & Config Header
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "⚙️ إعدادات التحكم ومزامنة التطبيق المباشرة",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.accent
                    )
                    Spacer(modifier = Modifier.height(11.dp))

                    Text("اسم التطبيق:", fontSize = 11.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = appNameInput,
                        onValueChange = { appNameInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("رسالة الشريط السفلي (Footer Text):", fontSize = 11.sp, color = themeColors.textPrimary)
                    OutlinedTextField(
                        value = footerInput,
                        onValueChange = { footerInput = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = forceVipOnly,
                            onCheckedChange = { forceVipOnly = it }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "فرض عرض نخبة المهنيين فقط (VIP) تلقائياً للمستخدمين",
                            fontSize = 11.sp,
                            color = themeColors.textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.updateAdminSettings(
                                appNameInput,
                                footerInput,
                                forceVipOnly
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_settings_button")
                    ) {
                        Icon(imageVector = Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("حفظ الإعدادات وإجراء المزامنة الفورية", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

        // CARD B: Active Layout CSS & Theme switcher - prevents crash dynamic
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🎨 مراجعة ومزامنة السمة والثيم (Theme Configuration)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.accent
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "اختر اللون المعبر عن هوية التطبيق لتعديله مباشرة عبر أجهزة المستخدمين:",
                        fontSize = 11.sp,
                        color = themeColors.textSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeSelectorBox(
                            title = "الأخضر اليماني",
                            isActive = settingsState.activeThemeId == "EMERALD_YEMEN",
                            primaryColor = Color(0xFF059669),
                            onSelect = { viewModel.updateTheme("EMERALD_YEMEN") }
                        )
                        ThemeSelectorBox(
                            title = "الفضي الكوني",
                            isActive = settingsState.activeThemeId == "COSMIC_SILVER",
                            primaryColor = Color(0xFF6B7280),
                            onSelect = { viewModel.updateTheme("COSMIC_SILVER") }
                        )
                        ThemeSelectorBox(
                            title = "شمس الصحراء",
                            isActive = settingsState.activeThemeId == "ACCENT_ORANGE",
                            primaryColor = Color(0xFFD97706),
                            onSelect = { viewModel.updateTheme("ACCENT_ORANGE") }
                        )
                    }
                }
            }
        }

        // CARD C: Category Custom Structure Admin Form
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🛠️ إضافة تصنيف خدمة جديد",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newCatNameAr,
                        onValueChange = { newCatNameAr = it },
                        label = { Text("الاسم بالعربية (مثال: سباكة أو صيانة إلكترونيات)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newCatNameEn,
                        onValueChange = { newCatNameEn = it },
                        label = { Text("الاسم بالإنجليزية (مثال: Electronics Repair)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newCatDesc,
                        onValueChange = { newCatDesc = it },
                        label = { Text("وصف الخدمة للمستخدمين") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("أيقونة التصنيف:", fontSize = 11.sp, color = themeColors.textPrimary)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val iconOptions = listOf("plumbing", "electrical", "ac_unit", "carpentry", "paint", "cleaning")
                        items(iconOptions) { item ->
                            val mapIcon = mapIconNameToVector(item)
                            val isSelected = selectedIconName == item
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) themeColors.accent else themeColors.secondary.copy(alpha = 0.4f))
                                    .clickable { selectedIconName = item }
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    imageVector = mapIcon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.Black else Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.addNewCategory(newCatNameAr, newCatNameEn, selectedIconName, newCatDesc)
                            newCatNameAr = ""
                            newCatNameEn = ""
                            newCatDesc = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إنشاء وإضافة تصنيف الخدمة", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

        // CARD D: Professional Provider Custom Structure Form
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "👤 تسجيل مهني / فني خدمي جديد في الدليل",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newProvName,
                        onValueChange = { newProvName = it },
                        label = { Text("اسم المهني ثلاثي") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newProvPhone,
                        onValueChange = { newProvPhone = it },
                        label = { Text("رقم الهاتف (الواتساب والاتصال)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newProvArea,
                        onValueChange = { newProvArea = it },
                        label = { Text("الموقع والمنطقة (مثال: صنعاء - شارع الستين)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newProvBasePrice,
                        onValueChange = { newProvBasePrice = it },
                        label = { Text("سعر معاينة الخدمة الأولي بالريال") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("التصنيف الرئيسي المهني المنضم إليه:", fontSize = 11.sp, color = themeColors.textPrimary)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(categories) { cat ->
                            val isSelected = newProvCatId == cat.id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) themeColors.accent else themeColors.secondary.copy(alpha = 0.4f))
                                    .clickable { newProvCatId = cat.id }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = cat.nameAr,
                                    fontSize = 11.sp,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = newProvIsVip,
                            onCheckedChange = { newProvIsVip = it }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("ترقية اشتراك هذا المهني لنخبة المهنيين (VIP)", fontSize = 11.sp, color = themeColors.textPrimary)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            val price = newProvBasePrice.toDoubleOrNull() ?: 1000.0
                            viewModel.addNewProvider(
                                newProvName,
                                newProvPhone,
                                newProvCatId,
                                newProvArea,
                                newProvIsVip,
                                price
                            )
                            newProvName = ""
                            newProvPhone = ""
                            newProvArea = ""
                            newProvBasePrice = ""
                            newProvIsVip = false
                            newProvCatId = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = themeColors.primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("إدراج المهني في الدواوين المعتمدة", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

        // CARD E: Delete and Structure Admin cleanup Lists
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = themeColors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🗑️ تنظيف الهياكل وإلغاء تسجيل الفنيين والخدمات",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("تفريغ وحذف تصنيفات المهن:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                    categories.forEach { cat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "• ${cat.nameAr} (${cat.nameEn})", fontSize = 11.sp, color = themeColors.textPrimary)
                            IconButton(
                                onClick = { viewModel.removeCategory(cat.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "حذف التصنيف", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("إلغاء وإقالة فني من الدليل:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = themeColors.textPrimary)
                    providers.forEach { prov ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "• ${prov.name} / ${prov.area}", fontSize = 11.sp, color = themeColors.textPrimary)
                            IconButton(
                                onClick = { viewModel.removeProvider(id = prov.id) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "حذف المهني", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// ------ Inner Composable Theme Selector Box ------
@Composable
fun ThemeSelectorBox(
    title: String,
    isActive: Boolean,
    primaryColor: Color,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isActive) primaryColor else primaryColor.copy(alpha = 0.25f))
            .border(
                border = if (isActive) BorderStroke(2.dp, Color.White) else BorderStroke(1.dp, Color.Transparent),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onSelect() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isActive) Color.White else primaryColor,
            textAlign = TextAlign.Center
        )
    }
}

// ------ Composable Helper: Empty State View ------
@Composable
fun EmptyStateLayout(themeColors: VisualThemePalette) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = "لا يوجد نتائج",
            tint = themeColors.textSecondary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "عذراً، لم نجد نتائج مطابقة لبحثك!",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = themeColors.textPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "حاول تعديل الفلاتر أو كتابة عبارات عامة كـ سباكة، كهرباء، صنعاء..",
            fontSize = 11.sp,
            color = themeColors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// ------ Vector Icons Mapper for Clean UI representation ------
fun mapIconNameToVector(iconName: String): ImageVector {
    return when (iconName) {
        "plumbing" -> Icons.Default.Build
        "electrical" -> Icons.Default.Bolt
        "ac_unit" -> Icons.Default.AcUnit
        "carpentry" -> Icons.Default.Handyman
        "paint" -> Icons.Default.Brush
        "cleaning" -> Icons.Default.CleanHands
        else -> Icons.Default.Handyman
    }
}
