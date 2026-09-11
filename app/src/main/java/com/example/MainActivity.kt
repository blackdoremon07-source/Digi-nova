package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.NavScreen
import com.example.ui.DigiNovaViewModel
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
        }
    }
}

@Composable
fun MainAppScaffold(
    viewModel: DigiNovaViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val isDetailActive = uiState.selectedDigitalService != null ||
            uiState.selectedLatestUpdate != null ||
            uiState.selectedTip != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = NovaDarkNavy,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            // Show bottom navigation when not viewing deep detail screens
            AnimatedVisibility(
                visible = !isDetailActive,
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
                        onShare = { title, content -> viewModel.shareContent(context, title, content) }
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
                                onWhatsAppClick = { viewModel.openWhatsApp(context, it) }
                            )
                        }

                        NavScreen.DIGITAL_SERVICES -> {
                            DigitalServicesScreen(
                                services = viewModel.digitalServices,
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
                                services = viewModel.onlineServices,
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
                                tips = viewModel.usefulTips,
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
                                updates = viewModel.latestUpdates,
                                searchQuery = uiState.searchQuery,
                                onSearchChange = { viewModel.updateSearchQuery(it) },
                                onClearSearch = { viewModel.clearSearchQuery() },
                                onUpdateClick = { viewModel.openUpdateDetail(it) }
                            )
                        }

                        NavScreen.PROFILE -> {
                            ProfileAboutScreen(
                                onBack = { viewModel.navigateTo(NavScreen.HOME) },
                                onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                                onEmailClick = { viewModel.sendEmail(context, it) },
                                onOpenUrl = { viewModel.openWebUrl(context, it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
