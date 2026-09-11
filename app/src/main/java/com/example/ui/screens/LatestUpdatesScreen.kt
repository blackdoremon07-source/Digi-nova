package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LatestUpdate
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun LatestUpdatesScreen(
    updates: List<LatestUpdate>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onUpdateClick: (LatestUpdate) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredUpdates = if (searchQuery.isBlank()) {
        updates
    } else {
        updates.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.summary.contains(searchQuery, ignoreCase = true) ||
                    it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NovaDarkNavy),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Screen Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Latest Updates",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stay informed with official DIGI NOVA announcements, security bulletins, and technology news.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted
                )
            }
        }

        // Search Bar
        item {
            SearchBarView(
                query = searchQuery,
                onQueryChange = onSearchChange,
                onClear = onClearSearch,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        if (filteredUpdates.isEmpty()) {
            item {
                EmptyStateView(
                    message = "No announcements or updates match '$searchQuery'.",
                    onClearClick = onClearSearch
                )
            }
        } else {
            items(filteredUpdates) { update ->
                LatestUpdateListItem(
                    update = update,
                    onClick = { onUpdateClick(update) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun LatestUpdateListItem(
    update: LatestUpdate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
            .testTag("update_card_${update.id}")
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
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0x3000F0FF))
                            .border(0.5.dp, Color(0x6000F0FF), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
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
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x30EF4444))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = update.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = update.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By ${update.author} • ${update.readTime}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDim
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Read Full Update",
                        style = MaterialTheme.typography.labelSmall,
                        color = NovaCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

/**
 * Update Detail Screen
 */
@Composable
fun UpdateDetailScreen(
    update: LatestUpdate,
    onBack: () -> Unit,
    onShare: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(NovaDarkNavy),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // App Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }

                Text(
                    text = "Update Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                IconButton(onClick = {
                    onShare("DIGI NOVA News: ${update.title}", "${update.title}\n\n${update.fullArticle}")
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = NovaCyan
                    )
                }
            }
        }

        // Header Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF0F2648),
                                Color(0xFF0A1830),
                                Color(0xFF060D1A)
                            )
                        )
                    )
                    .border(1.dp, Color(0x3000F0FF), RoundedCornerShape(18.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0x3000F0FF))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = update.category.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = NovaCyan,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = update.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = update.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        lineHeight = 30.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Published by ${update.author} • ${update.readTime}",
                        style = MaterialTheme.typography.bodySmall,
                        color = NovaAccentTeal
                    )
                }
            }
        }

        // Article Content
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(NovaNavyCard)
                        .border(1.dp, NovaBorder, RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Text(
                        text = update.fullArticle,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextWhite,
                        lineHeight = 26.sp
                    )
                }
            }
        }
    }
}
