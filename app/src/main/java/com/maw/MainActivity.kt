package com.maw

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.animation.ExperimentalAnimationApi::class)

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object AppTheme {
    fun getPrimary() = Color(0xFF132326)
    fun getAccent() = Color(0xFFFFD700)
    fun getDarkBg() = Color(0xFF132326)
    fun getSurface() = Color(0xFF1E3539)
    fun getCardBg() = Color(0xFF264146)
}

class MainActivity : ComponentActivity() {
    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = MainViewModel()
        setContent {
            val settingsState by vm.settings.collectAsState()
            
            // Dynamic theme based on settings configuration
            val primaryColorVal = remember(settingsState.primaryColorHex) {
                safeParseColor(settingsState.primaryColorHex, AppTheme.getPrimary())
            }
            val accentColorVal = remember(settingsState.accentColorHex) {
                safeParseColor(settingsState.accentColorHex, AppTheme.getAccent())
            }
            val bgColorVal = remember(settingsState.bgColorHex) {
                safeParseColor(settingsState.bgColorHex, AppTheme.getDarkBg())
            }
            val surfaceColorVal = remember(settingsState.surfaceColorHex) {
                safeParseColor(settingsState.surfaceColorHex, AppTheme.getSurface())
            }

            val currentColorScheme = darkColorScheme(
                primary = primaryColorVal,
                secondary = accentColorVal,
                background = bgColorVal,
                surface = surfaceColorVal,
                onPrimary = Color.White,
                onSecondary = Color.Black,
                onBackground = Color.White,
                onSurface = Color.White
            )

            MaterialTheme(
                colorScheme = currentColorScheme,
                typography = Typography()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = currentColorScheme.background
                ) {
                    AppNavigationLayout(vm = vm)
                }
            }
        }
    }
}

fun safeParseColor(hex: String, fallback: Color): Color {
    return try {
        if (hex.startsWith("#")) {
            val cleaned = hex.substring(1)
            val fullHex = if (cleaned.length == 6) "FF$cleaned" else cleaned
            Color(android.graphics.Color.parseColor("#$fullHex"))
        } else {
            fallback
        }
    } catch (e: Exception) {
        fallback
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val d = 2.0
    val a = (Math.sin(dLat / d) * Math.sin(dLat / d)) + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / d) * Math.sin(dLon / d))
    val c = d * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
    val dist = 6371.0 * c
    return if (dist.isNaN()) 0.0 else dist
}

fun getProviderCoordinates(provider: Provider): Pair<Double, Double> {
    val cityLower = provider.city.lowercase()
    val base = when (cityLower) {
        "sanaa" -> Pair(15.3533, 44.2074)
        "aden" -> Pair(12.7855, 45.0186)
        "taiz" -> Pair(13.5794, 44.0205)
        "hodeidah" -> Pair(14.7979, 42.953)
        "ibb" -> Pair(13.9745, 44.1802)
        "hadramout" -> Pair(14.4, 49.0)
        else -> Pair(15.3533, 44.2074)
    }
    
    val areaLower = provider.area.lowercase()
    val areaOffset = when {
        areaLower.contains("ستين") || areaLower.contains("60") -> Pair(-0.022, 0.005)
        areaLower.contains("الدائري") || areaLower.contains("الجامعة") || areaLower.contains("الجامعه") -> Pair(0.002, -0.008)
        areaLower.contains("الحصبة") || areaLower.contains("الحصبه") -> Pair(0.025, 0.003)
        areaLower.contains("التحرير") -> Pair(0.005, 0.001)
        areaLower.contains("الروضة") || areaLower.contains("الروضه") -> Pair(0.045, 0.015)
        areaLower.contains("الأصبحي") || areaLower.contains("الاصبحي") -> Pair(-0.035, 0.01)
        areaLower.contains("المنصورة") || areaLower.contains("المنصوره") -> Pair(0.005, 0.012)
        areaLower.contains("خور مكسر") || areaLower.contains("خورمكسر") -> Pair(-0.01, 0.035)
        areaLower.contains("الكريتر") || areaLower.contains("كريتر") -> Pair(-0.028, 0.052)
        areaLower.contains("الشيخ عثمان") -> Pair(0.025, 0.025)
        areaLower.contains("جمال") -> Pair(-0.002, -0.005)
        else -> {
            val h = if (areaLower.hashCode() == Int.MIN_VALUE) 0 else Math.abs(areaLower.hashCode())
            val angle = (h % 360) * 0.017453292519943295
            val dist = (h % 25) * 4.0E-4 + 0.006
            Pair(Math.sin(angle) * dist, Math.cos(angle) * dist)
        }
    }
    
    val individualAngle = (Math.abs(provider.id.hashCode()) % 12) * 0.5235987755982988
    val miniLat = Math.sin(individualAngle) * 0.0012
    val miniLon = Math.cos(individualAngle) * 0.0012
    
    return Pair(base.first + areaOffset.first + miniLat, base.second + areaOffset.second + miniLon)
}

@Composable
fun ShimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.08f),
            Color.White.copy(alpha = 0.22f),
            Color.White.copy(alpha = 0.08f)
        )
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmer_anim"
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = androidx.compose.ui.geometry.Offset.Zero,
            end = androidx.compose.ui.geometry.Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent)
        )
    }
}

@Composable
fun ProviderCardShimmer() {
    val brush = ShimmerBrush()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(AppTheme.getCardBg().copy(alpha = 0.5f))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(26.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(26.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush)
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationLayout(vm: MainViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Configuration states
    val categories by vm.categoriesState.collectAsState()
    val cities by vm.citiesState.collectAsState()
    val providers by vm.providers.collectAsState()
    val reviews by vm.reviewsState.collectAsState()
    val banners by vm.banners.collectAsState()
    val settingsState by vm.settings.collectAsState()
    val pendingProviders by vm.pendingRequests.collectAsState()
    val reports by vm.reports.collectAsState()
    val auditLogs by vm.auditLogs.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedCityId by remember { mutableStateOf("sanaa") } // Sana'a is default city selection

    // Local UI loading simulation
    var isLoading by remember { mutableStateOf(false) }

    // Active screen navigation
    // "explore", "chat_list", "joind_status", "admin_pane"
    var currentTab by remember { mutableStateOf("explore") }

    // Active modals
    var bookingProvider by remember { mutableStateOf<Provider?>(null) }
    var chattingProvider by remember { mutableStateOf<Provider?>(null) }
    var isNewProviderModalOpen by remember { mutableStateOf(false) }
    var isSmartAssistantOpen by remember { mutableStateOf(false) }

    // Admin login back door clicks
    var adminLogoPressCount by remember { mutableIntStateOf(0) }
    var isAdminLoginOpen by remember { mutableStateOf(false) }
    var adminUsername by remember { mutableStateOf("") }
    var adminPassword by remember { mutableStateOf("") }
    val isLoggedAsAdmin by vm.isAdminLoggedIn.collectAsState()
    val activeAdminName by vm.loggedInUsername.collectAsState()

    CompositionLocalProvider(androidx.compose.ui.platform.LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                SmallTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    adminLogoPressCount++
                                    if (adminLogoPressCount >= 5) {
                                        adminLogoPressCount = 0
                                        if (isLoggedAsAdmin) {
                                            currentTab = "admin_pane"
                                        } else {
                                            isAdminLoginOpen = true
                                        }
                                    }
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handyman,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "كل خدمات اليمن",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    actions = {
                        IconButton(onClick = { currentTab = "explore" }) {
                            Icon(Icons.Default.Home, "Explore", tint = if (currentTab == "explore") MaterialTheme.colorScheme.secondary else Color.White)
                        }
                        IconButton(onClick = { currentTab = "chat_list" }) {
                            Icon(Icons.Default.Chat, "Chat", tint = if (currentTab == "chat_list") MaterialTheme.colorScheme.secondary else Color.White)
                        }
                        IconButton(onClick = { isNewProviderModalOpen = true }) {
                            Icon(Icons.Default.PersonAdd, "Join Company", tint = Color.White)
                        }
                        if (isLoggedAsAdmin) {
                            IconButton(onClick = { currentTab = "admin_pane" }) {
                                Icon(Icons.Default.AdminPanelSettings, "Admin console", tint = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                if (!settingsState.assistantIconHidden) {
                    FloatingActionButton(
                        onClick = { isSmartAssistantOpen = true },
                        containerColor = safeParseColor(settingsState.assistantIconColorHex, AppTheme.getAccent()),
                        shape = CircleShape,
                        modifier = Modifier
                            .offset(
                                x = settingsState.assistantIconXOffset.dp,
                                y = -settingsState.assistantIconYOffset.dp
                            )
                            .size(settingsState.assistantIconSize.dp)
                    ) {
                        Icon(
                            imageVector = if (settingsState.assistantIconType == "robot") Icons.Default.SmartToy else Icons.Default.SupportAgent,
                            contentDescription = "مساعد ذكي",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (currentTab) {
                    "explore" -> {
                        ExploreScreen(
                            vm = vm,
                            providers = providers,
                            categories = categories,
                            cities = cities,
                            banners = banners,
                            selectedCityId = selectedCityId,
                            selectedCategory = selectedCategory,
                            searchQuery = searchQuery,
                            isLoading = isLoading,
                            onCitySelected = { selectedCityId = it },
                            onCategorySelected = { selectedCategory = if (selectedCategory == it) null else it },
                            onSearchQueryChanged = { searchQuery = it },
                            onBookClicked = { bookingProvider = it },
                            onChatClicked = { chattingProvider = it },
                            onSimulateLoading = {
                                coroutineScope.launch {
                                    isLoading = true
                                    delay(1000)
                                    isLoading = false
                                }
                            },
                            settingsState = settingsState
                        )
                    }
                    "chat_list" -> {
                        ChatScreenList(vm = vm)
                    }
                    "admin_pane" -> {
                        if (isLoggedAsAdmin) {
                            AdminPaneScreen(
                                vm = vm,
                                pendingProviders = pendingProviders,
                                reports = reports,
                                auditLogs = auditLogs,
                                categories = categories,
                                cities = cities,
                                providers = providers,
                                settings = settingsState,
                                activeAdmin = activeAdminName
                            )
                        } else {
                            currentTab = "explore"
                        }
                    }
                }

                // Chat View Modal Trigger
                chattingProvider?.let { provider ->
                    DirectChatView(
                        vm = vm,
                        provider = provider,
                        onDismiss = { chattingProvider = null }
                    )
                }

                // Booking Modal Screen
                bookingProvider?.let { provider ->
                    BookingDialog(
                        vm = vm,
                        provider = provider,
                        onDismiss = { bookingProvider = null }
                    )
                }

                // New Join Provider Registration Form
                if (isNewProviderModalOpen) {
                    JoinApplicationDialog(
                        vm = vm,
                        categories = categories,
                        cities = cities,
                        onDismiss = { isNewProviderModalOpen = false },
                        settingsState = settingsState
                    )
                }

                // AI Assistant bottom sheet dialog
                if (isSmartAssistantOpen) {
                    SmartAssistantSheet(
                        vm = vm,
                        onDismiss = { isSmartAssistantOpen = false }
                    )
                }

                // Back door admin login dialog popup
                if (isAdminLoginOpen) {
                    AlertDialog(
                        onDismissRequest = { isAdminLoginOpen = false },
                        title = { Text("تسجيل دخول المطور والمشرفين", fontWeight = FontWeight.Bold, color = Color.White) },
                        text = {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = adminUsername,
                                    onValueChange = { adminUsername = it },
                                    label = { Text("اسم المستخدم") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = adminPassword,
                                    onValueChange = { adminPassword = it },
                                    label = { Text("كلمة المرور") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = {
                                if (adminUsername.isBlank() || adminPassword.isBlank()) return@Button
                                if (adminUsername == "admin" && vm.checkAdminPassword(adminPassword)) {
                                    vm.addAdminAccount(
                                        AdminAccount(
                                            username = "admin",
                                            passwordHash = adminPassword,
                                            canManageCategories = true,
                                            canApproveRequests = true,
                                            canSeeReports = true,
                                            canDeleteActiveProviders = true,
                                            canManageBanners = true
                                        ),
                                        "System"
                                    )
                                }
                                val match = vm.checkAdminThreeLayersLogin(adminUsername, adminPassword)
                                if (match != null) {
                                    currentTab = "admin_pane"
                                    isAdminLoginOpen = false
                                    Toast.makeText(context, "أهلاً بك يا مشرف الدعم والمحتوى!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "البيانات المدخلة خاطئة!", Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Text("دخول")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { isAdminLoginOpen = false }) {
                                Text("إلغاء")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExploreScreen(
    vm: MainViewModel,
    providers: List<Provider>,
    categories: List<Category>,
    cities: List<City>,
    banners: List<Banner>,
    selectedCityId: String,
    selectedCategory: String?,
    searchQuery: String,
    isLoading: Boolean,
    onCitySelected: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onBookClicked: (Provider) -> Unit,
    onChatClicked: (Provider) -> Unit,
    onSimulateLoading: () -> Unit,
    settingsState: AppSettings
) {
    val coroutineScope = rememberCoroutineScope()

    // Coordinates of current city center selection for precision distance calculations
    val currentCityCenter = remember(selectedCityId) {
        when (selectedCityId) {
            "sanaa" -> Pair(15.3533, 44.2074)
            "aden" -> Pair(12.7855, 45.0186)
            "taiz" -> Pair(13.5794, 44.0205)
            "hodeidah" -> Pair(14.7979, 42.953)
            "ibb" -> Pair(13.9745, 44.1802)
            "hadramout" -> Pair(14.4, 49.0)
            else -> Pair(15.3533, 44.2074)
        }
    }

    // Dynamic list search and filter logic
    val filteredProviders = remember(providers, selectedCityId, selectedCategory, searchQuery) {
        providers.filter { p ->
            val cityMatch = p.city.equals(selectedCityId, ignoreCase = true)
            val catMatch = selectedCategory == null || p.category.equals(selectedCategory, ignoreCase = true)
            val queryMatch = searchQuery.isBlank() || 
                             p.name.contains(searchQuery, ignoreCase = true) ||
                             p.description.contains(searchQuery, ignoreCase = true) ||
                             p.skills.contains(searchQuery, ignoreCase = true) ||
                             p.area.contains(searchQuery, ignoreCase = true)

            // Block filter for offensive keywords configured under admin panel
            val isOffensiveBlocked = settingsState.blockedKeywords.any { b ->
                p.name.contains(b, ignoreCase = true) || p.description.contains(b, ignoreCase = true)
            }

            cityMatch && catMatch && queryMatch && !isOffensiveBlocked
        }.sortedWith(
            compareByDescending<Provider> { it.isPinned }
                .thenByDescending { it.rating }
                .thenBy { it.orderPriority }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Horizontal City Filters
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cities) { city ->
                val selected = city.id == selectedCityId
                FilterChip(
                    selected = selected,
                    onClick = {
                        onCitySelected(city.id)
                        onSimulateLoading()
                    },
                    label = { Text(city.nameAr, color = if (selected) Color.Black else Color.White) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }

        // Main Explore Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Optional search box bar
            item {
                Spacer(modifier = Modifier.height(10.dp))
                if (settingsState.searchBarVisible) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChanged,
                        placeholder = { Text("ابحث عن خدمات، مهندس، سباك، طبيب...", color = Color.LightGray) },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.secondary) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChanged("") }) {
                                    Icon(Icons.Default.Clear, null)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            // Category Slider
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "الأقسام والخدمات المتوفرة",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories.filter { it.isPublished && (it.parentId == null || it.parentId.isBlank()) }) { cat ->
                        val selected = cat.id == selectedCategory
                        Card(
                            onClick = {
                                onCategorySelected(cat.id)
                                onSimulateLoading()
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selected) MaterialTheme.colorScheme.secondary else AppTheme.getCardBg()
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(100.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(cat.iconUrl.ifBlank { "⚙️" }, fontSize = 24.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = cat.nameAr,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selected) Color.Black else Color.White,
                                    maxLines = 2,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // Results Headline
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "مقدموا الخدمات وبطاقات التواصل",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Providers listings
            if (isLoading) {
                items(5) {
                    ProviderCardShimmer()
                }
            } else if (filteredProviders.isEmpty()) {
                item {
                    val emptyStateMsg = if (settingsState.welcomeMessage.isNotBlank()) {
                        "عذراً! لا يوجد نتائج مطابقة للبحث حالياً في هذه المدينة."
                    } else {
                        "لا توجد نتائج مناسبة لبحثك بمدينة صنعاء"
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = emptyStateMsg,
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )
                        }
                    }
                }
            } else {
                items(filteredProviders) { provider ->
                    ProviderCardItem(
                        provider = provider,
                        userCoordinates = currentCityCenter,
                        onBookClicked = { onBookClicked(provider) },
                        onChatClicked = { onChatClicked(provider) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProviderCardItem(
    provider: Provider,
    userCoordinates: Pair<Double, Double>,
    onBookClicked: () -> Unit,
    onChatClicked: () -> Unit
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    // Dynamic coordinate-to-coordinate distance calculation in Yemen
    val distance = remember(provider, userCoordinates) {
        val providerLoc = getProviderCoordinates(provider)
        calculateDistance(userCoordinates.first, userCoordinates.second, providerLoc.first, providerLoc.second)
    }

    // Precise distance layout text
    val distanceText = remember(distance) {
        if (distance < 1.0) {
            "${(distance * 1000).toInt()} متر"
        } else {
            "${String.format("%.1f", distance)} كم"
        }
    }

    // Click interactive scale animation
    val scaleState = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scaleState.value)
            .padding(vertical = 4.dp)
            .clickable(
                onClick = {
                    coroutineScope.launch {
                        // Interactive tap scale effect
                        scaleState.animateTo(0.96f, animationSpec = tween(100))
                        scaleState.animateTo(1f, animationSpec = tween(100))
                        isExpanded = !isExpanded
                    }
                }
            )
            .testTag("provider_card_${provider.id}"),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.getCardBg()
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Provider Avatar/Image fallback
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    if (provider.imageUrl.isNotBlank()) {
                        AsyncImage(
                            model = provider.imageUrl,
                            contentDescription = provider.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = provider.name,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        if (provider.isPinned) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "متميز",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${provider.city} • ${provider.area}",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }

                // Precise distance small text next to rating
                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${provider.rating}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    // Mandatory small text for computed distance 12sp secondary text
                    Text(
                        text = "تبعد حوالي $distanceText",
                        fontSize = 12.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = provider.description,
                color = Color.LightGray,
                fontSize = 13.sp,
                maxLines = if (isExpanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )

            // Nested expandable fields inside card
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    if (provider.skills.isNotBlank()) {
                        Divider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💡 المهارات والخبرات: ${provider.skills}",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "📞 رقم الاتصال المباشر: ${provider.phone}",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "🕰️ ساعات العمل: يومياً من 8:00 ص إلى 8:00 م",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                try {
                                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${provider.phone}"))
                                    context.startActivity(callIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "الرجاء الاتصال يدوياً بـ: ${provider.phone}", Toast.LENGTH_LONG).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Call, null, tint = Color.Black)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("اتصال مباشر", color = Color.Black, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onChatClicked,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Chat, null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("دردشة فورية", color = Color.White, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onBookClicked,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Icon(Icons.Default.CalendarToday, null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حجز موعد", color = Color.White, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun bookingRoutingTextAndIcons() {
    // Auxiliary
}

@Composable
fun ChatScreenList(vm: MainViewModel) {
    val chats by vm.chats.collectAsState()
    var activeChatRoom by remember { mutableStateOf<Chat?>(null) }

    if (activeChatRoom != null) {
        DirectChatView(
            vm = vm,
            provider = Provider(id = activeChatRoom!!.providerId, name = activeChatRoom!!.providerName, city = "", phone = ""),
            onDismiss = { activeChatRoom = null }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(14.dp)) {
            Text(
                text = "قائمة المحادثات والدردشة النشطة",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (chats.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("لا توجد محادثات جارية حالياً.", color = Color.LightGray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(chats) { chat ->
                        Card(
                            onClick = { activeChatRoom = chat },
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = AppTheme.getCardBg())
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Person, null, tint = MaterialTheme.colorScheme.secondary)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = chat.providerName,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = chat.lastMessage,
                                        color = Color.LightGray,
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Icon(Icons.Default.ArrowForwardIos, null, tint = Color.LightGray, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DirectChatView(
    vm: MainViewModel,
    provider: Provider,
    onDismiss: () -> Unit
) {
    val coroutine = rememberCoroutineScope()
    var messageText by remember { mutableStateOf("") }
    val chats by vm.chats.collectAsState()
    val allMessages by vm.chatMessages.collectAsState()

    val chatRoomId = remember(provider) {
        "chat_client_${provider.id}"
    }

    // Load or start chat room
    LaunchedEffect(provider) {
        vm.startChatWithProvider("client", provider.id, provider.name)
    }

    val currentRoomMessages = remember(allMessages, chatRoomId) {
        allMessages.filter { it.chatId == chatRoomId }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxSize(),
        confirmButton = {},
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "الدردشة مع ${provider.name}", fontSize = 16.sp, color = Color.White)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = false
                ) {
                    items(currentRoomMessages) { msg ->
                        val isMe = msg.senderType == "client"
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                            bottomEnd = if (isMe) 0.dp else 12.dp,
                                            bottomStart = if (isMe) 12.dp else 0.dp
                                        )
                                    )
                                    .background(if (isMe) MaterialTheme.colorScheme.secondary else AppTheme.getCardBg())
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = msg.message,
                                    color = if (isMe) Color.Black else Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("اكتب رسالة...") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (messageText.isBlank()) return@IconButton
                            vm.sendChatMessage(chatRoomId, "ميلاد عميل", "client", messageText)
                            messageText = ""
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Send, null, tint = Color.Black)
                    }
                }
            }
        }
    )
}

@Composable
fun BookingDialog(
    vm: MainViewModel,
    provider: Provider,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var details by remember { mutableStateOf("") }
    var preferredTime by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("حجز موعد خدمة فوري ومباشر", fontWeight = FontWeight.Bold, color = Color.White) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("مزود الخدمة: ${provider.name}", color = Color.LightGray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("أكتب تفاصيل المشكلة أو الخدمة المطلوبة") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = preferredTime,
                    onValueChange = { preferredTime = it },
                    label = { Text("الموعد المفضل (مثلاً: غداً 4 عصراً)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (details.isBlank() || preferredTime.isBlank()) {
                    Toast.makeText(context, "الرجاء كمل البيانات المطلوبة يا غالي!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                vm.requestServiceAppointment(provider.id, provider.name, details, preferredTime)
                onDismiss()
                Toast.makeText(context, "تم إرسال طلب الحجز بنجاح! بيوصلك اتصال قريب.", Toast.LENGTH_LONG).show()
            }) {
                Text("إرسال طلب الحجز")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}

@Composable
fun JoinApplicationDialog(
    vm: MainViewModel,
    categories: List<Category>,
    cities: List<City>,
    onDismiss: () -> Unit,
    settingsState: AppSettings
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("sanaa") }
    var area by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("electricity") }
    var phone by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("تسجيل طلب انضمام كـ مزود خدمة", fontWeight = FontWeight.Bold, color = Color.White) },
        text = {
            Box(modifier = Modifier.fillMaxWidth().height(420.dp)) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("الاسم الكامل الحقيقي") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("رقم واتساب أو هاتف للتواصل الحقيقي") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("حدد المدينة", fontSize = 12.sp, color = Color.LightGray)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        cities.forEach { c ->
                            FilterChip(
                                selected = selectedCity == c.id,
                                onClick = { selectedCity = c.id },
                                label = { Text(c.nameAr) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = area,
                        onValueChange = { area = it },
                        label = { Text("الحارة أو الحي السكني") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("حدد تخصص الخدمة الأساسي", fontSize = 12.sp, color = Color.LightGray)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                    ) {
                        categories.filter { it.parentId == null }.forEach { cat ->
                            FilterChip(
                                selected = categoryId == cat.id,
                                onClick = { categoryId = cat.id },
                                label = { Text(cat.nameAr) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("شرح مختصر عن خبرتك للعملاء") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = skills,
                        onValueChange = { skills = it },
                        label = { Text("المهارات (افصل بينها بفاصلة)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isBlank() || phone.isBlank() || area.isBlank() || description.isBlank()) {
                    Toast.makeText(context, "الرجاء إدخال الحقول المطلوبة يا غالي!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val pp = PendingProvider(
                    area = area,
                    category = categoryId,
                    city = selectedCity,
                    description = description,
                    deviceId = "uuid_${System.currentTimeMillis() % 1000}",
                    id = UUID.randomUUID().toString(),
                    name = name,
                    phone = phone,
                    skills = skills
                )
                vm.registerPendingProvider(pp)
                onDismiss()
                Toast.makeText(context, "تم إرسال طلب انضمامك! سيقوم المشرف بمراجعته وتفعيله.", Toast.LENGTH_LONG).show()
            }) {
                Text("إرسال طلب الانضمام")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("إلغاء")
            }
        }
    )
}

@Composable
fun SmartAssistantSheet(
    vm: MainViewModel,
    onDismiss: () -> Unit
) {
    val geminiMessages by vm.geminiMessages.collectAsState()
    val isThinking by vm.isGeminiThinking.collectAsState()
    var prompt by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxSize(),
        confirmButton = {},
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("مساعد دليل خدمات اليمن الذكي", fontSize = 16.sp, color = Color.White)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(AppTheme.getCardBg())
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "حياك الله مع المساعد الذكي! اسألني عن أي خدمة تبحث عنها في اليمن وسأقوم بترشيح الأفضل لك فوراً وبكل سهولة.",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    items(geminiMessages) { msg ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.second) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp).copy(bottomEnd = if (msg.second) CornerSize(0.dp) else CornerSize(12.dp), bottomStart = if (msg.second) CornerSize(12.dp) else CornerSize(0.dp)))
                                    .background(if (msg.second) MaterialTheme.colorScheme.secondary else AppTheme.getCardBg())
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = msg.first,
                                    color = if (msg.second) Color.Black else Color.White,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    if (isThinking) {
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("جاري التفكير وتجهيز الرد اليمني الأصيل...", color = Color.LightGray, fontSize = 12.sp)
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        placeholder = { Text("مثلاً: أحتاج فني كهرباء في صنعاء") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (prompt.isBlank()) return@IconButton
                            vm.askGemini(prompt)
                            prompt = ""
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(Icons.Default.Send, null, tint = Color.Black)
                    }
                }
            }
        }
    )
}

@Composable
fun AdminPaneScreen(
    vm: MainViewModel,
    pendingProviders: List<PendingProvider>,
    reports: List<Report>,
    auditLogs: List<AuditLog>,
    categories: List<Category>,
    cities: List<City>,
    providers: List<Provider>,
    settings: AppSettings,
    activeAdmin: String
) {
    val context = LocalContext.current
    var subTab by remember { mutableStateOf("pending") }

    Column(modifier = Modifier.fillMaxSize().padding(14.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "لوحة التحكم والإدارة الفنية",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { vm.logoutAdmin() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("تسجيل خروج", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Admin Subtabs navigation
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(selected = subTab == "pending", onClick = { subTab = "pending" }, label = { Text("الطلبات المعلقة (${pendingProviders.size})") })
            FilterChip(selected = subTab == "providers", onClick = { subTab = "providers" }, label = { Text("المزودون النشطون") })
            FilterChip(selected = subTab == "settings", onClick = { subTab = "settings" }, label = { Text("تخصيص المظهر") })
            FilterChip(selected = subTab == "logs", onClick = { subTab = "logs" }, label = { Text("سجل العمليات (${auditLogs.size})") })
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (subTab) {
            "pending" -> {
                if (pendingProviders.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("لا توجد طلبات انضمام معلقة حالياً.", color = Color.LightGray)
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(pendingProviders) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = AppTheme.getCardBg())
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(item.name, fontWeight = FontWeight.Bold, color = Color.White)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("الهاتف: ${item.phone}", color = Color.LightGray, fontSize = 12.sp)
                                    Text("العنوان: ${item.city} - ${item.area}", color = Color.LightGray, fontSize = 12.sp)
                                    Text("التخصص: ${item.category}", color = Color.LightGray, fontSize = 12.sp)
                                    Text("الوصف: ${item.description}", color = Color.LightGray, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row {
                                        Button(
                                            onClick = { vm.approveProviderRequest(item, activeAdmin) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text("موافقة وقبول", color = Color.White, fontSize = 12.sp)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = { vm.rejectProviderRequest(item.id, "المعايير والوصف غير كافي", activeAdmin) },
                                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text("رفض الطلب", color = Color.White, fontSize = 12.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "providers" -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(providers) { p ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = AppTheme.getCardBg())
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(p.name, fontWeight = FontWeight.Bold, color = Color.White)
                                    Text("المدينة: ${p.city} - التخصص: ${p.category}", color = Color.LightGray, fontSize = 12.sp)
                                }
                                Button(
                                    onClick = { vm.deleteProvider(p.id, activeAdmin) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text("حذف", color = Color.White, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
            "settings" -> {
                var welcome by remember { mutableStateOf(settings.welcomeMessage) }
                var primaryHex by remember { mutableStateOf(settings.primaryColorHex) }
                var accentHex by remember { mutableStateOf(settings.accentColorHex) }
                var bgHex by remember { mutableStateOf(settings.bgColorHex) }
                var surfaceHex by remember { mutableStateOf(settings.surfaceColorHex) }

                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    OutlinedTextField(
                        value = welcome,
                        onValueChange = { welcome = it },
                        label = { Text("رسالة الترحيب الرئيسية") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = primaryHex,
                        onValueChange = { primaryHex = it },
                        label = { Text("كود الـ Hex للون الأساسي (مثال: #132326)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = accentHex,
                        onValueChange = { accentHex = it },
                        label = { Text("كود الـ Hex للون التحديد (مثال: #FFD700)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = {
                            val newS = settings.copy(
                                welcomeMessage = welcome,
                                primaryColorHex = primaryHex,
                                accentColorHex = accentHex,
                                bgColorHex = bgHex,
                                surfaceColorHex = surfaceHex
                            )
                            vm.updateAppSettings(newS, activeAdmin)
                            Toast.makeText(context, "تم تطبيق الألوان وحفظ الإعدادات بنجاح!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("حفظ وتحديث المظهر")
                    }
                }
            }
            "logs" -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(auditLogs) { log ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = AppTheme.getCardBg().copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(log.action, color = Color.White, fontSize = 13.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("بواسطة: ${log.adminName} • قبل قليل", color = Color.LightGray, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
