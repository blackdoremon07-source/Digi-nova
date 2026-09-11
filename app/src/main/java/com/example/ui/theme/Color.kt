package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// DIGI NOVA Brand Palette - Dark Navy & Obsidian with Electric Cyan & Blue accents
val NovaDarkNavy = Color(0xFF060912)
val NovaNavySurface = Color(0xFF0D1424)
val NovaNavyCard = Color(0xFF121B2F)
val NovaNavyCardElevated = Color(0xFF18243E)
val NovaBorder = Color(0xFF1E2F4F)
val NovaBorderBright = Color(0xFF28416E)

// Neon & Brand Accents
val NovaCyan = Color(0xFF00F0FF)
val NovaCyanGlow = Color(0x3300F0FF)
val NovaElectricBlue = Color(0xFF0070F3)
val NovaRoyalBlue = Color(0xFF2563EB)
val NovaDeepBlue = Color(0xFF1D4ED8)
val NovaAccentTeal = Color(0xFF2DD4BF)
val NovaWhatsApp = Color(0xFF25D366)

// Text Colors
val TextWhite = Color(0xFFF8FAFC)
val TextMuted = Color(0xFF94A3B8)
val TextDim = Color(0xFF64748B)

// Utility Gradients
val NovaGradient = Brush.linearGradient(
    colors = listOf(NovaCyan, NovaElectricBlue, NovaRoyalBlue)
)

val NovaCardGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF142038), Color(0xFF0D1527))
)

val NovaHeroGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF003875), Color(0xFF0D1C38), Color(0xFF060913))
)

