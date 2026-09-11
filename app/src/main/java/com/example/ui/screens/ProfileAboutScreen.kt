package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DigiNovaRepository
import com.example.ui.components.DigiNovaLogo
import com.example.ui.theme.*

@Composable
fun ProfileAboutScreen(
    onBack: () -> Unit,
    onWhatsAppClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
    onOpenUrl: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val contact = DigiNovaRepository.contactInfo
    var showFeedbackDialog by remember { mutableStateOf(false) }

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextWhite
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Profile & About",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }
        }

        // Branding Card with DIGI NOVA Logo
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0C2448),
                                Color(0xFF091428),
                                Color(0xFF060B18)
                            )
                        )
                    )
                    .border(1.dp, Color(0x4000F0FF), RoundedCornerShape(20.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DigiNovaLogo(
                        size = 80.dp,
                        showText = true,
                        showTagline = true,
                        animated = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = contact.companyBio,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFCBD5E1),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NovaNavyCardElevated)
                            .border(1.dp, NovaBorder, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Version: ${contact.version}",
                            style = MaterialTheme.typography.labelSmall,
                            color = NovaCyan,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Primary WhatsApp Contact Button
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Button(
                    onClick = {
                        onWhatsAppClick("Hello DIGI NOVA! I would like to chat with your support and services team.")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 54.dp)
                        .testTag("profile_whatsapp_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NovaWhatsApp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Chat on WhatsApp Support",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }

        // Direct Contact Methods Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Direct Contact",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                ContactCardItem(
                    title = "Official Email",
                    subtitle = contact.supportEmail,
                    icon = Icons.Default.Email,
                    onClick = { onEmailClick("Inquiry for DIGI NOVA") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactCardItem(
                    title = "Official Website",
                    subtitle = contact.websiteUrl,
                    icon = Icons.Default.Language,
                    onClick = { onOpenUrl(contact.websiteUrl) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactCardItem(
                    title = "Global Headquarters",
                    subtitle = contact.address,
                    icon = Icons.Default.LocationOn,
                    onClick = {}
                )
            }
        }

        // Social Channels
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Official Channels & Social",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SocialLinkPill(
                        name = "Website",
                        icon = Icons.Default.Public,
                        onClick = { onOpenUrl(contact.websiteUrl) },
                        modifier = Modifier.weight(1f)
                    )
                    SocialLinkPill(
                        name = "X / Twitter",
                        icon = Icons.Default.Share,
                        onClick = { onOpenUrl(contact.twitterUrl) },
                        modifier = Modifier.weight(1f)
                    )
                    SocialLinkPill(
                        name = "LinkedIn",
                        icon = Icons.Default.Business,
                        onClick = { onOpenUrl(contact.linkedinUrl) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Quick Feedback & Privacy
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                OutlinedButton(
                    onClick = { showFeedbackDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NovaBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.RateReview,
                        contentDescription = null,
                        tint = NovaCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Submit App Feedback",
                        color = TextWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }

    if (showFeedbackDialog) {
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showFeedbackDialog = false
                        onEmailClick("DIGI NOVA App Feedback")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NovaCyan)
                ) {
                    Text("Send Feedback", color = Color(0xFF040711), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showFeedbackDialog = false }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            title = {
                Text(
                    text = "DIGI NOVA Feedback",
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            },
            text = {
                Text(
                    text = "We value your input. Share your thoughts or suggestions directly with our development team.",
                    color = TextMuted
                )
            },
            containerColor = NovaNavyCardElevated,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
fun ContactCardItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x2000F0FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = NovaCyan,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextDim,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SocialLinkPill(
    name: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(NovaNavyCard)
            .border(1.dp, NovaBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = name,
                tint = NovaCyan,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelSmall,
                color = TextWhite,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp
            )
        }
    }
}
