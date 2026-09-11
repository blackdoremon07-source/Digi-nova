package com.example.ui.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CompanyContact
import com.example.model.DigitalService
import com.example.ui.theme.NovaBorder
import com.example.ui.theme.NovaCyan
import com.example.ui.theme.NovaNavyCard
import com.example.ui.theme.NovaNavyCardElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.util.DownloadHelper

@Composable
fun DownloadOptionsDialog(
    contact: CompanyContact,
    services: List<DigitalService>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showApkGuideInfo by remember { mutableStateOf(false) }

    val appShareUrl = "https://ais-pre-oiyz3lbehkqtz5yfvoffd7-974661219181.asia-southeast1.run.app"

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(22.dp))
                .background(NovaNavyCardElevated)
                .border(1.dp, NovaCyan, RoundedCornerShape(22.dp))
                .padding(20.dp)
                .testTag("download_options_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x2000F0FF))
                                .border(1.dp, NovaCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Download Options",
                                tint = NovaCyan,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Downloads & Exports",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite
                            )
                            Text(
                                text = "DIGI NOVA Offline & APK Resources",
                                style = MaterialTheme.typography.bodySmall,
                                color = NovaCyan
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_download_dialog_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Option 1: Download Android App APK (Instruction & Sideload Guide)
                DownloadActionCard(
                    title = "Download Android APK",
                    subtitle = "Get the Android installation package (.apk) to run on your phone",
                    badge = "APK / Mobile",
                    icon = Icons.Default.Android,
                    accentColor = Color(0xFF10B981),
                    testTag = "download_apk_option_btn",
                    onClick = {
                        showApkGuideInfo = true
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Option 2: Download Company Brochure & Catalog
                DownloadActionCard(
                    title = "Download Company Brochure",
                    subtitle = "Official catalog with services, pricing & contact info (Text/Doc)",
                    badge = "Free Brochure",
                    icon = Icons.Default.Description,
                    accentColor = NovaCyan,
                    testTag = "download_brochure_option_btn",
                    onClick = {
                        DownloadHelper.downloadCompanyBrochure(context, contact, services)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Option 3: Share App Download Link
                DownloadActionCard(
                    title = "Share App Download Link",
                    subtitle = "Send direct web link to clients or friends via WhatsApp / Email",
                    badge = "Shareable",
                    icon = Icons.Default.Share,
                    accentColor = Color(0xFF38BDF8),
                    testTag = "share_app_link_option_btn",
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Experience DIGI NOVA — Digital • Simple • Smart by ${contact.ownerName}.\nExplore services, online tools & direct consultations:\n$appShareUrl"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share DIGI NOVA App"))
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Option 4: Copy App Preview Link
                DownloadActionCard(
                    title = "Copy App Web URL",
                    subtitle = "Copy the direct cloud streaming URL to your device clipboard",
                    badge = "Clipboard",
                    icon = Icons.Default.ContentCopy,
                    accentColor = Color(0xFFA78BFA),
                    testTag = "copy_app_link_option_btn",
                    onClick = {
                        DownloadHelper.copyToClipboard(context, "DIGI NOVA Web App Link", appShareUrl)
                    }
                )

                if (showApkGuideInfo) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = NovaBorder)
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF10B981), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF06281E)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = null,
                                    tint = Color(0xFF34D399),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "How to Download APK & Source Code",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6EE7B7)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "1. In Google AI Studio Build window, click the Settings (⚙️) menu at the top right.\n" +
                                        "2. Click 'Generate APK / AAB' to download the ready-to-install Android package.\n" +
                                        "3. You can also select 'Export as ZIP' to download the complete Android Studio Kotlin project source code.\n" +
                                        "4. Transfer the .apk to your Android device, tap it in your Files app, and tap 'Install'.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE2E8F0),
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF10B981))
                                    .clickable {
                                        DownloadHelper.downloadAndShareFile(
                                            context = context,
                                            fileName = "DIGI_NOVA_APK_Download_Guide.txt",
                                            mimeType = "text/plain",
                                            fileContent = """
                                                ====================================================
                                                    DIGI NOVA APK DOWNLOAD & INSTALLATION GUIDE
                                                ====================================================
                                                App Name: DIGI NOVA
                                                Author: Anup Digi Nova
                                                Target OS: Android 8.0+ (API 26+)
                                                
                                                HOW TO DOWNLOAD PRODUCTION APK:
                                                1. In your AI Studio Build workspace, look at the upper toolbar.
                                                2. Click on the Settings icon (⚙️) in the top-right corner.
                                                3. Select 'Generate APK / AAB' or 'Export as ZIP'.
                                                4. Download the generated APK file to your computer or phone.
                                                
                                                HOW TO INSTALL ON ANDROID:
                                                1. Open the downloaded APK on your Android device.
                                                2. When prompted 'For your security, your phone is not allowed to install unknown apps', tap Settings.
                                                3. Toggle 'Allow from this source'.
                                                4. Tap 'Install'.
                                                5. Open DIGI NOVA and enjoy digital simplicity!
                                                
                                                Web App Streaming Preview:
                                                $appShareUrl
                                                
                                                Support: ${contact.supportEmail} | WhatsApp: ${contact.supportWhatsAppNumber}
                                                ====================================================
                                            """.trimIndent(),
                                            chooserTitle = "Download APK Installation Guide"
                                        )
                                    }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = null,
                                        tint = Color(0xFF041812),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Download Offline Install Guide (.txt)",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF041812)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadActionCard(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
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
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.15f))
                    .border(1.dp, accentColor.copy(alpha = 0.4f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall,
                            color = accentColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = accentColor.copy(alpha = 0.7f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
