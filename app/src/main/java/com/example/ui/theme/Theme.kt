package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DigiNovaColorScheme = darkColorScheme(
    primary = NovaCyan,
    onPrimary = Color(0xFF040711),
    primaryContainer = Color(0xFF003F6B),
    onPrimaryContainer = NovaCyan,
    secondary = NovaElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF132A55),
    onSecondaryContainer = Color(0xFFBAE6FD),
    tertiary = NovaAccentTeal,
    onTertiary = Color(0xFF042F2E),
    background = NovaDarkNavy,
    onBackground = TextWhite,
    surface = NovaNavySurface,
    onSurface = TextWhite,
    surfaceVariant = NovaNavyCard,
    onSurfaceVariant = TextMuted,
    outline = NovaBorder,
    outlineVariant = NovaBorderBright
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    // DIGI NOVA is intentionally designed with dark navy/black background and cyan+blue accents
    MaterialTheme(
        colorScheme = DigiNovaColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun DigiNovaTheme(
    content: @Composable () -> Unit
) {
    MyApplicationTheme(content = content)
}

