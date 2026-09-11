package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

/**
 * Official DIGI NOVA Logo Component
 * Renders the authentic Nova Starburst with electric cyan & sapphire blue gradient,
 * glowing cyber aura, geometric digital nodes, and bold DIGI NOVA lettermark.
 */
@Composable
fun DigiNovaLogo(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    showText: Boolean = true,
    showTagline: Boolean = false,
    animated: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "nova_logo_pulse")
    val pulseScale by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(1800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
        )
    } else {
        rememberUpdatedState(1f)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Nova Starburst Emblem Canvas
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0xFF0D2342), Color(0xFF081224))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(size * 0.85f * pulseScale)
            ) {
                val w = this.size.width
                val h = this.size.height
                val cx = w / 2f
                val cy = h / 2f
                val radius = w * 0.46f

                // Luminous outer cyan halo
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0x5500F0FF), Color(0x100070F3), Color.Transparent),
                        center = Offset(cx, cy),
                        radius = radius * 1.15f
                    ),
                    radius = radius * 1.15f,
                    center = Offset(cx, cy)
                )

                // Diamond orbit accent
                val diamondPath = Path().apply {
                    moveTo(cx, cy - radius * 0.88f)
                    lineTo(cx + radius * 0.88f, cy)
                    lineTo(cx, cy + radius * 0.88f)
                    lineTo(cx - radius * 0.88f, cy)
                    close()
                }
                drawPath(
                    path = diamondPath,
                    color = Color(0x3300F0FF),
                    style = Stroke(width = 1.5.dp.toPx())
                )

                // Digital circuit corner nodes
                val nodeOffset = radius * 0.88f
                drawCircle(Color(0xFF00F0FF), radius = 2.dp.toPx(), center = Offset(cx, cy - nodeOffset))
                drawCircle(Color(0xFF00F0FF), radius = 2.dp.toPx(), center = Offset(cx + nodeOffset, cy))
                drawCircle(Color(0xFF00F0FF), radius = 2.dp.toPx(), center = Offset(cx, cy + nodeOffset))
                drawCircle(Color(0xFF00F0FF), radius = 2.dp.toPx(), center = Offset(cx - nodeOffset, cy))

                // Primary Nova 4-pointed Starburst
                val starPath = Path().apply {
                    val outer = radius * 0.95f
                    val inner = radius * 0.22f
                    moveTo(cx, cy - outer)
                    cubicTo(cx, cy - inner, cx + inner, cy, cx + outer, cy)
                    cubicTo(cx + inner, cy, cx, cy + inner, cx, cy + outer)
                    cubicTo(cx, cy + inner, cx - inner, cy, cx - outer, cy)
                    cubicTo(cx - inner, cy, cx, cy - inner, cx, cy - outer)
                    close()
                }

                drawPath(
                    path = starPath,
                    brush = Brush.linearGradient(
                        colors = listOf(NovaCyan, NovaElectricBlue, NovaRoyalBlue),
                        start = Offset(cx - radius, cy - radius),
                        end = Offset(cx + radius, cy + radius)
                    ),
                    style = Fill
                )

                // Inner brilliant core
                val innerCorePath = Path().apply {
                    val coreOuter = radius * 0.45f
                    val coreInner = radius * 0.12f
                    moveTo(cx, cy - coreOuter)
                    cubicTo(cx, cy - coreInner, cx + coreInner, cy, cx + coreOuter, cy)
                    cubicTo(cx + coreInner, cy, cx, cy + coreInner, cx, cy + coreOuter)
                    cubicTo(cx, cy + coreInner, cx - coreInner, cy, cx - coreOuter, cy)
                    cubicTo(cx - coreInner, cy, cx, cy - coreInner, cx, cy - coreOuter)
                    close()
                }

                drawPath(
                    path = innerCorePath,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color(0xFF99F6FF)),
                        start = Offset(cx - radius * 0.5f, cy - radius * 0.5f),
                        end = Offset(cx + radius * 0.5f, cy + radius * 0.5f)
                    ),
                    style = Fill
                )

                // Sparkle pinpoint
                drawCircle(Color.White, radius = 1.8.dp.toPx(), center = Offset(cx, cy))
            }
        }

        if (showText) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "DIGI ",
                        style = if (size > 60.dp) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextWhite,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "NOVA",
                        style = if (size > 60.dp) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = NovaCyan,
                        letterSpacing = 1.sp
                    )
                }

                if (showTagline) {
                    Text(
                        text = "DIGITAL • SIMPLE • SMART",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
