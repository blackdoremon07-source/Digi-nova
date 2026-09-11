package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavScreen
import com.example.ui.DigiNovaViewModel
import com.example.ui.admin.AdminDashboardScreen
import com.example.ui.admin.AdminLoginScreen
import com.example.ui.components.CustomerEnquiryDialog
import com.example.ui.components.DigiNovaBottomBar
import com.example.ui.screens.*
import com.example.ui.theme.DigiNovaTheme
import com.example.ui.theme.NovaDarkNavy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigiNovaTheme {
                DigiNovaApp()
            }
        }
    }
}

@Composable
fun DigiNovaApp(
    viewModel: DigiNovaViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Android Hardware/System Back Button Handling
    BackHandler(enabled = !uiState.isSplashScreenVisible) {
        if (!viewModel.handleBackPress()) {
            (context as? ComponentActivity)?.finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NovaDarkNavy),
        contentAlignment = Alignment.TopCenter
    ) {
        // Enforce responsive width constraint for tablets/foldables while full-bleed on phones
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 680.dp)
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = uiState.isSplashScreenVisible,
                transitionSpec = {
                    fadeIn(animationSpec = tween(400)) togetherWith
                            fadeOut(animationSpec = tween(400))
                },
                label = "splash_transition"
            ) { isSplash ->
                if (isSplash) {
                    SplashScreen(
                        onSplashFinished = { viewModel.dismissSplashScreen() }
                    )
                } else {
                    MainAppScaffold(viewModel = viewModel)
                }
            }

            // Customer Enquiry Dialog (Global Overlay)
            if (uiState.isEnquiryDialogOpen) {
                CustomerEnquiryDialog(
                    initialService = uiState.targetEnquiryService,
                    onDismiss = { viewModel.closeCustomerEnquiryDialog() },
                    onSubmit = { name, phone, email, serviceId, serviceTitle, message ->
                        viewModel.submitCustomerEnquiry(
                            name = name,
                            phone = phone,
                            email = email,
                            serviceId = serviceId,
                            serviceTitle = serviceTitle,
                            message = message,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Thank you! Your enquiry has been received. Our team will contact you promptly.",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            onError = { err ->
                                Toast.makeText(context, "Failed: $err", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainAppScaffold(
    viewModel: DigiNovaViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Reactive Data Sources from Local Database
    val digitalServices by viewModel.digitalServices.collectAsState()
    val onlineServices by viewModel.onlineServices.collectAsState()
    val usefulTips by viewModel.usefulTips.collectAsState()
    val latestUpdates by viewModel.latestUpdates.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val companyContact by viewModel.companyContact.collectAsState()

    // Admin Data Sources
    val adminServices by viewModel.allServicesForAdmin.collectAsState()
    val adminOnlineTools by viewModel.allOnlineToolsForAdmin.collectAsState()
    val adminTips by viewModel.allTipsForAdmin.collectAsState()
    val adminUpdates by viewModel.allUpdatesForAdmin.collectAsState()
    val adminNotifications by viewModel.allNotificationsForAdmin.collectAsState()
    val adminEnquiries by viewModel.allEnquiriesForAdmin.collectAsState()
    val pendingEnquiriesCount by viewModel.pendingEnquiriesCount.collectAsState()
    val auditLogs by viewModel.auditLogs.collectAsState()
    val rawSettings by viewModel.rawSettings.collectAsState()

    val isDetailActive = uiState.selectedDigitalService != null ||
            uiState.selectedLatestUpdate != null ||
            uiState.selectedTip != null

    val isDedicatedScreen = uiState.currentScreen == NavScreen.ADMIN_LOGIN ||
            uiState.currentScreen == NavScreen.ADMIN_DASHBOARD ||
            uiState.currentScreen == NavScreen.PRIVACY_POLICY ||
            uiState.currentScreen == NavScreen.TERMS_CONDITIONS ||
            uiState.currentScreen == NavScreen.CONTACT_US ||
            uiState.currentScreen == NavScreen.NOTIFICATIONS

    val shouldShowBottomBar = !isDetailActive && !isDedicatedScreen

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = NovaDarkNavy,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            // Show bottom navigation only when on standard customer tabs
            AnimatedVisibility(
                visible = shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                DigiNovaBottomBar(
                    currentScreen = uiState.currentScreen,
                    onNavigate = { screen ->
                        viewModel.clearSearchQuery()
                        viewModel.navigateTo(screen)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Screen router
            when {
                uiState.selectedDigitalService != null -> {
                    ServiceDetailScreen(
                        service = uiState.selectedDigitalService!!,
                        onBack = { viewModel.closeServiceDetail() },
                        onWhatsAppInquiry = { msg -> viewModel.openWhatsApp(context, msg) },
                        onEmailInquiry = { subject -> viewModel.sendEmail(context, subject) },
                        onShare = { title, content -> viewModel.shareContent(context, title, content) },
                        onOpenEnquiry = {
                            viewModel.openCustomerEnquiryDialog(uiState.selectedDigitalService)
                        }
                    )
                }

                uiState.selectedLatestUpdate != null -> {
                    UpdateDetailScreen(
                        update = uiState.selectedLatestUpdate!!,
                        onBack = { viewModel.closeUpdateDetail() },
                        onShare = { title, content -> viewModel.shareContent(context, title, content) }
                    )
                }

                uiState.selectedTip != null -> {
                    val tip = uiState.selectedTip!!
                    TipDetailScreen(
                        tip = tip,
                        isBookmarked = uiState.bookmarkedTipIds.contains(tip.id),
                        onToggleBookmark = { viewModel.toggleBookmarkTip(tip.id) },
                        onBack = { viewModel.closeTipDetail() },
                        onShare = { title, content -> viewModel.shareContent(context, title, content) }
                    )
                }

                else -> {
                    when (uiState.currentScreen) {
                        NavScreen.HOME -> {
                            HomeScreen(
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onNavigate = { viewModel.navigateTo(it) },
                                onServiceClick = { viewModel.openServiceDetail(it) },
                                onUpdateClick = { viewModel.openUpdateDetail(it) },
                                onTipClick = { viewModel.openTipDetail(it) },
                                onOpenUrl = { viewModel.openWebUrl(context, it) },
                                onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                                allServices = digitalServices,
                                allOnline = onlineServices,
                                allTips = usefulTips,
                                allUpdates = latestUpdates,
                                contact = companyContact,
                                onNotificationsClick = { viewModel.navigateTo(NavScreen.NOTIFICATIONS) },
                                onOpenEnquiry = { viewModel.openCustomerEnquiryDialog(it) }
                            )
                        }

                        NavScreen.DIGITAL_SERVICES -> {
                            DigitalServicesScreen(
                                services = digitalServices,
                                selectedCategory = uiState.selectedServiceCategory,
                                onCategoryChange = { viewModel.setServiceCategory(it) },
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onServiceClick = { viewModel.openServiceDetail(it) }
                            )
                        }

                        NavScreen.ONLINE_SERVICES -> {
                            OnlineServicesScreen(
                                services = onlineServices,
                                selectedCategory = uiState.selectedOnlineCategory,
                                onCategoryChange = { viewModel.setOnlineCategory(it) },
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onOpenUrl = { viewModel.openWebUrl(context, it) }
                            )
                        }

                        NavScreen.USEFUL_TIPS -> {
                            UsefulTipsScreen(
                                tips = usefulTips,
                                selectedCategory = uiState.selectedTipCategory,
                                onCategoryChange = { viewModel.setTipCategory(it) },
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onTipClick = { viewModel.openTipDetail(it) },
                                bookmarkedTipIds = uiState.bookmarkedTipIds,
                                onToggleBookmark = { viewModel.toggleBookmarkTip(it) }
                            )
                        }

                        NavScreen.LATEST_UPDATES -> {
                            LatestUpdatesScreen(
                                updates = latestUpdates,
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onUpdateClick = { viewModel.openUpdateDetail(it) }
                            )
                        }

                        NavScreen.PROFILE -> {
                            ProfileAboutScreen(
                                contact = companyContact,
                                onBack = { viewModel.navigateTo(NavScreen.HOME) },
                                onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                                onCallClick = { viewModel.makePhoneCall(context, it) },
                                onEmailClick = { viewModel.sendEmail(context, it) },
                                onOpenUrl = { viewModel.openWebUrl(context, it) },
                                onOpenContactUs = { viewModel.navigateTo(NavScreen.CONTACT_US) },
                                onOpenPrivacyPolicy = { viewModel.navigateTo(NavScreen.PRIVACY_POLICY) },
                                onOpenTermsConditions = { viewModel.navigateTo(NavScreen.TERMS_CONDITIONS) },
                                onOpenAdminPortal = {
                                    if (uiState.isAdminLoggedIn) {
                                        viewModel.navigateTo(NavScreen.ADMIN_DASHBOARD)
                                    } else {
                                        viewModel.navigateTo(NavScreen.ADMIN_LOGIN)
                                    }
                                },
                                services = digitalServices
                            )
                        }

                        NavScreen.NOTIFICATIONS -> {
                            NotificationsScreen(
                                notifications = notifications,
                                onBack = { viewModel.navigateTo(NavScreen.HOME) }
                            )
                        }

                        NavScreen.CONTACT_US -> {
                            ContactUsScreen(
                                contact = companyContact,
                                onBack = { viewModel.navigateTo(NavScreen.PROFILE) },
                                onCallClick = { viewModel.makePhoneCall(context, it) },
                                onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                                onEmailClick = { viewModel.sendEmail(context, it) },
                                onOpenEnquiryDialog = { viewModel.openCustomerEnquiryDialog(null) }
                            )
                        }

                        NavScreen.PRIVACY_POLICY -> {
                            LegalScreen(
                                title = "Privacy Policy",
                                content = companyContact.privacyPolicy,
                                onBack = { viewModel.navigateTo(NavScreen.PROFILE) }
                            )
                        }

                        NavScreen.TERMS_CONDITIONS -> {
                            LegalScreen(
                                title = "Terms & Conditions",
                                content = companyContact.termsConditions,
                                onBack = { viewModel.navigateTo(NavScreen.PROFILE) }
                            )
                        }

                        NavScreen.ADMIN_LOGIN -> {
                            AdminLoginScreen(
                                onLoginSubmit = { user, pass ->
                                    viewModel.loginAdmin(user, pass)
                                },
                                errorMessage = uiState.adminLoginError,
                                isLoading = uiState.isAdminLoading,
                                onBack = { viewModel.navigateTo(NavScreen.PROFILE) }
                            )
                        }

                        NavScreen.ADMIN_DASHBOARD -> {
                            if (!uiState.isAdminLoggedIn) {
                                // Enforce least-privilege: customer cannot access admin dashboard without authenticating
                                viewModel.navigateTo(NavScreen.ADMIN_LOGIN)
                            } else {
                                AdminDashboardScreen(
                                    adminUsername = uiState.adminUsername,
                                    adminEmail = uiState.adminEmail,
                                    services = adminServices,
                                    onlineTools = adminOnlineTools,
                                    tips = adminTips,
                                    updates = adminUpdates,
                                    notifications = adminNotifications,
                                    enquiries = adminEnquiries,
                                    pendingEnquiriesCount = pendingEnquiriesCount,
                                    settings = rawSettings,
                                    auditLogs = auditLogs,
                                    onSaveService = { viewModel.saveService(it) },
                                    onDeleteService = { viewModel.deleteService(it) },
                                    onToggleService = { viewModel.toggleServiceStatus(it) },
                                    onSaveOnlineTool = { viewModel.saveOnlineTool(it) },
                                    onDeleteOnlineTool = { viewModel.deleteOnlineTool(it) },
                                    onSaveTip = { viewModel.saveTip(it) },
                                    onDeleteTip = { viewModel.deleteTip(it) },
                                    onSaveUpdate = { viewModel.saveUpdate(it) },
                                    onDeleteUpdate = { viewModel.deleteUpdate(it) },
                                    onSaveNotification = { viewModel.saveNotification(it) },
                                    onDeleteNotification = { viewModel.deleteNotification(it) },
                                    onUpdateEnquiryStatus = { id, status -> viewModel.updateEnquiryStatus(id, status) },
                                    onDeleteEnquiry = { id -> viewModel.deleteEnquiry(id) },
                                    onCallCustomer = { phone -> viewModel.makePhoneCall(context, phone) },
                                    onWhatsAppCustomer = { phone, name, srv ->
                                        val msg = "Hello $name, this is Anup Digi Nova regarding your enquiry for '$srv'."
                                        viewModel.openWhatsApp(context, msg)
                                    },
                                    onSaveSetting = { k, v -> viewModel.saveSetting(k, v) },
                                    onSaveBatchSettings = { map -> viewModel.saveBatchSettings(map) },
                                    onChangePassword = { cur, newP, cb ->
                                        viewModel.changeAdminPassword(cur, newP, cb)
                                    },
                                    onLogout = {
                                        viewModel.logoutAdmin()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
