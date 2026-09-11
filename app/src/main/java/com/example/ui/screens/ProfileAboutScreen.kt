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
import com.example.model.CompanyContact
import com.example.ui.components.DigiNovaLogo
import com.example.ui.theme.*

@Composable
fun ProfileAboutScreen(
    contact: CompanyContact,
    onBack: () -> Unit,
    onWhatsAppClick: (String) -> Unit,
    onCallClick: (String) -> Unit,
    onEmailClick: (String) -> Unit,
    onOpenUrl: (String) -> Unit,
    onOpenContactUs: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    onOpenTermsConditions: () -> Unit,
    onOpenAdminPortal: () -> Unit,
    modifier: Modifier = Modifier
) {
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

                    Spacer(modifier = Modifier.height(14.dp))

                    // Leadership & Ownership Info Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(NovaNavyCardElevated)
                            .border(1.dp, NovaBorder, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Founder & Developer: ${contact.ownerName}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = NovaCyan
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = contact.copyright,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "App Version: ${contact.version}",
                                style = MaterialTheme.typography.labelSmall,
                                color = NovaAccentTeal,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Quick Professional Communication Buttons (WhatsApp, Call, Email)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        onWhatsAppClick("Hello ${contact.ownerName}! I am reaching out regarding DIGI NOVA digital services.")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .testTag("profile_whatsapp_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NovaWhatsApp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chat on WhatsApp Support",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onCallClick(contact.supportPhoneNumber) },
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(minHeight = 46.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NovaElectricBlue)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = NovaCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Call Direct", color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = { onEmailClick("Inquiry for ${contact.ownerName} - DIGI NOVA") },
                        modifier = Modifier
                            .weight(1f)
                            .defaultMinSize(minHeight = 46.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NovaBorder)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = NovaCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Email Team", color = TextWhite, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Direct Contact Details Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Corporate Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ContactCardItem(
                    title = "Support Hotline",
                    subtitle = contact.supportPhoneNumber,
                    icon = Icons.Default.Phone,
                    onClick = { onCallClick(contact.supportPhoneNumber) }
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                    title = "Headquarters",
                    subtitle = contact.address,
                    icon = Icons.Default.LocationOn,
                    onClick = {}
                )
            }
        }

        // Legal, Support & Admin Navigation Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "App & Governance",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ContactCardItem(
                    title = "Contact & Consultation",
                    subtitle = "Direct lines, office address, and consultation forms",
                    icon = Icons.Default.ContactSupport,
                    onClick = onOpenContactUs
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactCardItem(
                    title = "Privacy Policy",
                    subtitle = "Data handling, confidentiality, and protection standards",
                    icon = Icons.Default.Security,
                    onClick = onOpenPrivacyPolicy
                )

                Spacer(modifier = Modifier.height(8.dp))

                ContactCardItem(
                    title = "Terms & Conditions",
                    subtitle = "Service agreements, intellectual property, and warranties",
                    icon = Icons.Default.Gavel,
                    onClick = onOpenTermsConditions
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Dedicated Owner & Administrator Portal Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF0B1E38), Color(0xFF0F325E), Color(0xFF0B1E38))
                            )
                        )
                        .border(1.dp, NovaCyan, RoundedCornerShape(14.dp))
                        .clickable(onClick = onOpenAdminPortal)
                        .padding(16.dp)
                        .testTag("open_admin_portal_btn")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x2000F0FF))
                                    .border(1.dp, NovaCyan, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = NovaCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Owner / Admin Portal",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                                Text(
                                    text = "Manage services, enquiries, settings & security",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = NovaCyan
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = NovaCyan
                        )
                    }
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
