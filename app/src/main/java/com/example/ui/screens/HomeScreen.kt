package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DigiNovaRepository
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNavigate: (NavScreen) -> Unit,
    onServiceClick: (DigitalService) -> Unit,
    onUpdateClick: (LatestUpdate) -> Unit,
    onTipClick: (UsefulTip) -> Unit,
    onOpenUrl: (String) -> Unit,
    onWhatsAppClick: (String) -> Unit,
    allServices: List<DigitalService> = emptyList(),
    allOnline: List<OnlineService> = emptyList(),
    allTips: List<UsefulTip> = emptyList(),
    allUpdates: List<LatestUpdate> = emptyList(),
    contact: CompanyContact = CompanyContact(),
    onNotificationsClick: () -> Unit = {},
    onOpenEnquiry: (DigitalService?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val isSearching = searchQuery.isNotBlank()
    val filteredServices = if (isSearching) {
        allServices.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true) ||
                    it.category.contains(searchQuery, ignoreCase = true)
        }
    } else allServices

    val filteredOnline = if (isSearching) {
        allOnline.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true)
        }
    } else allOnline

    val filteredTips = if (isSearching) {
        allTips.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.summary.contains(searchQuery, ignoreCase = true)
        }
    } else allTips

    val filteredUpdates = if (isSearching) {
        allUpdates.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.summary.contains(searchQuery, ignoreCase = true)
        }
    } else allUpdates

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NovaDarkNavy),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Top Header Bar
        item {
            HomeTopHeader(
                onProfileClick = { onNavigate(NavScreen.PROFILE) },
                onWhatsAppClick = { onWhatsAppClick("Hello DIGI NOVA, I am exploring your app!") },
                onNotificationsClick = onNotificationsClick
            )
        }

        // Search Bar
        item {
            SearchBarView(
                query = searchQuery,
                onQueryChange = onSearchChange,
                onClear = onClearSearch,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        if (isSearching) {
            // Search Results Mode
            item {
                Text(
                    text = "Search Results for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    color = NovaCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (filteredServices.isEmpty() && filteredOnline.isEmpty() && filteredTips.isEmpty() && filteredUpdates.isEmpty()) {
                item {
                    EmptyStateView(
                        message = "No services, tools, or tips found matching '$searchQuery'",
                        onClearClick = onClearSearch
                    )
                }
            } else {
                if (filteredServices.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Digital Services (${filteredServices.size})",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(filteredServices) { service ->
                        ServiceListCard(
                            service = service,
                            onClick = { onServiceClick(service) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                if (filteredOnline.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Online Services (${filteredOnline.size})",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(filteredOnline) { online ->
                        OnlineServiceCard(
                            service = online,
                            onOpen = { onOpenUrl(online.url) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                if (filteredTips.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Useful Tips (${filteredTips.size})",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(filteredTips) { tip ->
                        TipCard(
                            tip = tip,
                            onClick = { onTipClick(tip) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }

                if (filteredUpdates.isNotEmpty()) {
                    item {
                        SectionHeader(
                            title = "Updates (${filteredUpdates.size})",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(filteredUpdates) { update ->
                        UpdateCard(
                            update = update,
                            onClick = { onUpdateClick(update) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        } else {
            // Standard Rich Home Content
            // 1. Hero Promo Banner
            item {
                HomeHeroBanner(
                    onExploreServices = { onNavigate(NavScreen.DIGITAL_SERVICES) },
                    onContact = { onWhatsAppClick("Hello DIGI NOVA! I would like to consult with your digital services team.") },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // 2. Quick Navigation Shortcut Hub
            item {
                QuickHubRow(
                    onNavigate = onNavigate,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            // 3. Featured Digital Services Carousel
            item {
                SectionHeader(
                    title = "Digital Services",
                    actionText = "See All",
                    onActionClick = { onNavigate(NavScreen.DIGITAL_SERVICES) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    items(allServices.take(4)) { service ->
                        FeaturedServiceCard(
                            service = service,
                            onClick = { onServiceClick(service) }
                        )
                    }
                }
            }

            // 4. Online Services Section
            item {
                SectionHeader(
                    title = "Popular Online Services",
                    actionText = "View All",
                    onActionClick = { onNavigate(NavScreen.ONLINE_SERVICES) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(allOnline.take(3)) { online ->
                OnlineServiceCard(
                    service = online,
                    onOpen = { onOpenUrl(online.url) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // 5. Useful Tips Spotlight
            item {
                SectionHeader(
                    title = "Useful Tech Tips",
                    actionText = "More Tips",
                    onActionClick = { onNavigate(NavScreen.USEFUL_TIPS) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(allTips.take(2)) { tip ->
                TipCard(
                    tip = tip,
                    onClick = { onTipClick(tip) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // 6. Latest Updates Ticker
            item {
                SectionHeader(
                    title = "Latest Updates",
                    actionText = "Read All",
                    onActionClick = { onNavigate(NavScreen.LATEST_UPDATES) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(allUpdates.take(2)) { update ->
                UpdateCard(
                    update = update,
                    onClick = { onUpdateClick(update) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun HomeTopHeader(
    onProfileClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onNotificationsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DigiNovaLogo(
            size = 40.dp,
            showText = true,
            showTagline = true,
            animated = false
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Notifications Action
            IconButton(
                onClick = onNotificationsClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(NovaNavyCardElevated)
                    .border(1.dp, NovaBorder, CircleShape)
                    .testTag("home_notifications_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = NovaCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            // WhatsApp Quick Action
            IconButton(
                onClick = onWhatsAppClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0x2025D366))
                    .border(1.dp, Color(0x6025D366), CircleShape)
                    .testTag("home_whatsapp_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp Support",
                    tint = NovaWhatsApp,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Profile / About Button
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(NovaNavyCardElevated)
                    .border(1.dp, NovaBorder, CircleShape)
                    .testTag("home_profile_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "About DIGI NOVA",
                    tint = NovaCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun SearchBarView(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                "Search services, online tools, tips...",
                color = TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = NovaCyan,
                modifier = Modifier.size(22.dp)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = NovaNavyCardElevated,
            unfocusedContainerColor = NovaNavyCard,
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite,
            cursorColor = NovaCyan,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, if (query.isNotBlank()) NovaCyan else NovaBorder, RoundedCornerShape(14.dp))
            .testTag("home_search_field")
    )
}

@Composable
fun HomeHeroBanner(
    onExploreServices: () -> Unit,
    onContact: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF003D7A),
                        Color(0xFF0C2448),
                        Color(0xFF071124)
                    )
                )
            )
            .border(1.dp, Color(0x4000F0FF), RoundedCornerShape(18.dp))
            .padding(20.dp)
    ) {
        Column {
            // Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0x3000F0FF))
                    .border(0.5.dp, NovaCyan, RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "NEXT-GEN DIGITAL ECOSYSTEM",
                    style = MaterialTheme.typography.labelSmall,
                    color = NovaCyan,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Modern Digital Solutions for Every Ambition",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Explore high-performance web development, brand identity, online utilities, and expert tech insights.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCBD5E1),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onExploreServices,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NovaCyan),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Explore Services",
                        color = Color(0xFF040711),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                OutlinedButton(
                    onClick = onContact,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x6000F0FF)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = NovaCyan),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "WhatsApp Us",
                        color = TextWhite,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun QuickHubRow(
    onNavigate: (NavScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        QuickHubItem(
            title = "Digital",
            subtitle = "Services",
            icon = Icons.Default.Code,
            accentColor = NovaCyan,
            onClick = { onNavigate(NavScreen.DIGITAL_SERVICES) }
        )
        QuickHubItem(
            title = "Online",
            subtitle = "Tools",
            icon = Icons.Default.Language,
            accentColor = NovaElectricBlue,
            onClick = { onNavigate(NavScreen.ONLINE_SERVICES) }
        )
        QuickHubItem(
            title = "Useful",
            subtitle = "Tips",
            icon = Icons.Default.Lightbulb,
            accentColor = Color(0xFFFFB703),
            onClick = { onNavigate(NavScreen.USEFUL_TIPS) }
        )
        QuickHubItem(
            title = "Latest",
            subtitle = "Updates",
            icon = Icons.Default.Campaign,
            accentColor = NovaAccentTeal,
            onClick = { onNavigate(NavScreen.LATEST_UPDATES) }
        )
    }
}

@Composable
fun QuickHubItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(NovaNavyCardElevated)
                .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "$title $subtitle",
                tint = accentColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = TextWhite
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            color = TextMuted
        )
    }
}

@Composable
fun FeaturedServiceCard(
    service: DigitalService,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(260.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0x2000F0FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getServiceIcon(service.iconType),
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }

                if (service.badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x300070F3))
                            .border(0.5.dp, NovaElectricBlue, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = service.badge,
                            style = MaterialTheme.typography.labelSmall,
                            color = NovaCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = service.tagline,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = service.pricing,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = NovaAccentTeal
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = NovaCyan
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceListCard(
    service: DigitalService,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x2000F0FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getServiceIcon(service.iconType),
                    contentDescription = null,
                    tint = NovaCyan,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = service.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = service.category + " • " + service.deliveryTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = NovaAccentTeal,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = service.tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = NovaCyan,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun OnlineServiceCard(
    service: OnlineService,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x200070F3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getServiceIcon(service.iconType),
                    contentDescription = null,
                    tint = NovaElectricBlue,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = service.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    if (service.badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0x2000F0FF))
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = service.badge,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 9.sp,
                                color = NovaCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = service.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = onOpen,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NovaElectricBlue),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.defaultMinSize(minHeight = 36.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Open",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TipCard(
    tip: UsefulTip,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x20FFB703)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFFFFB703),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tip.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "${tip.readTimeMinutes} min read",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextDim,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = tip.summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun UpdateCard(
    update: LatestUpdate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0x3000F0FF))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = update.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = NovaCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }

                    if (update.badge != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0x40EF4444))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = update.badge,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFFCA5A5),
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Text(
                    text = update.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDim,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = update.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = update.summary,
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = update.readTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDim
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Read Story",
                        style = MaterialTheme.typography.labelSmall,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}
