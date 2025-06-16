package com.example.vitalflow.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.vitalflow.ui.theme.AeroBlue
import com.example.vitalflow.ui.theme.AeroGlass
import kotlin.math.sin

@Composable
fun AeroWaterEffect(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    
    // Animate water wave effect
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    // Animate glass shimmer effect
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .background(AeroGlass)
            .blur(radius = 1.dp)
            .drawBehind {
                // Draw water wave effect
                val wavePath = Path()
                val height = size.height
                val width = size.width
                
                wavePath.moveTo(0f, height * 0.5f)
                
                for (x in 0..width.toInt() step 16) {
                    val y = height * 0.5f + 
                        sin((x / 16f + waveOffset) * 2f * Math.PI) * 8f
                    wavePath.lineTo(x.toFloat(), y.toFloat())
                }
                
                wavePath.lineTo(width, height)
                wavePath.lineTo(0f, height)
                wavePath.close()
                
                drawPath(
                    path = wavePath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AeroBlue.copy(alpha = 0.2f),
                            AeroBlue.copy(alpha = 0.1f)
                        )
                    ),
                    alpha = 0.5f
                )
                
                // Draw glass shimmer effect
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0f),
                            Color.White.copy(alpha = 0.2f),
                            Color.White.copy(alpha = 0f)
                        ),
                        start = Offset(width * (shimmerOffset - 0.5f), 0f),
                        end = Offset(width * (shimmerOffset + 0.5f), height)
                    )
                )
            }
    ) {
        content()
    }
}

@Composable
fun AeroCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AeroWaterEffect(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun AeroSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
            .background(AeroGlass)
            .blur(radius = 0.5.dp)
    ) {
        content()
    }
}
