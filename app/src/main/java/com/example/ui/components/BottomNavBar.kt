package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NavScreen
import com.example.ui.theme.*

@Composable
fun DigiNovaBottomBar(
    currentScreen: NavScreen,
    onNavigate: (NavScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(NovaNavySurface)
            .border(
                width = 1.dp,
                color = NovaBorder,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                title = "Home",
                selected = currentScreen == NavScreen.HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onNavigate(NavScreen.HOME) },
                testTag = "nav_home"
            )

            BottomNavItem(
                title = "Services",
                selected = currentScreen == NavScreen.DIGITAL_SERVICES,
                selectedIcon = Icons.Filled.Devices,
                unselectedIcon = Icons.Outlined.Devices,
                onClick = { onNavigate(NavScreen.DIGITAL_SERVICES) },
                testTag = "nav_services"
            )

            BottomNavItem(
                title = "Online",
                selected = currentScreen == NavScreen.ONLINE_SERVICES,
                selectedIcon = Icons.Filled.Language,
                unselectedIcon = Icons.Outlined.Language,
                onClick = { onNavigate(NavScreen.ONLINE_SERVICES) },
                testTag = "nav_online"
            )

            BottomNavItem(
                title = "Tips",
                selected = currentScreen == NavScreen.USEFUL_TIPS,
                selectedIcon = Icons.Filled.Lightbulb,
                unselectedIcon = Icons.Outlined.Lightbulb,
                onClick = { onNavigate(NavScreen.USEFUL_TIPS) },
                testTag = "nav_tips"
            )

            BottomNavItem(
                title = "Updates",
                selected = currentScreen == NavScreen.LATEST_UPDATES,
                selectedIcon = Icons.Filled.Campaign,
                unselectedIcon = Icons.Outlined.Campaign,
                onClick = { onNavigate(NavScreen.LATEST_UPDATES) },
                testTag = "nav_updates"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    title: String,
    selected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    onClick: () -> Unit,
    testTag: String
) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) NovaCyan else TextMuted,
        label = "icon_color"
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) NovaCyan else TextDim,
        label = "text_color"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (selected) Color(0x2000F0FF) else Color.Transparent)
                .padding(horizontal = 14.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (selected) selectedIcon else unselectedIcon,
                contentDescription = title,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = textColor
        )
    }
}
