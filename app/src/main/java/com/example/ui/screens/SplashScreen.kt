package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DigiNovaLogo
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "splash_alpha"
    )

    val progressAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1600, easing = LinearOutSlowInEasing),
        label = "splash_progress"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(1900)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF070C18),
                        Color(0xFF0A1326),
                        Color(0xFF050811)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
                .alpha(alphaAnim)
        ) {
            // Authentic DIGI NOVA Branding with pulse
            DigiNovaLogo(
                size = 96.dp,
                showText = true,
                showTagline = true,
                animated = true
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Sleek glowing progress indicator
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF13233F))
            ) {
                LinearProgressIndicator(
                    progress = { progressAnim },
                    modifier = Modifier.fillMaxSize(),
                    color = NovaCyan,
                    trackColor = Color.Transparent
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading digital ecosystem...",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted,
                fontSize = 12.sp,
                letterSpacing = 0.8.sp
            )
        }

        // Bottom copyright / info
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alphaAnim)
        ) {
            Text(
                text = "POWERED BY DIGI NOVA • 2026",
                style = MaterialTheme.typography.labelSmall,
                color = TextDim,
                letterSpacing = 1.5.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
