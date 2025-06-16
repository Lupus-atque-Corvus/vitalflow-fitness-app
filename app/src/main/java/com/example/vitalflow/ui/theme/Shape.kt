package com.example.vitalflow.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    // Larger corners for cards and surfaces to achieve the Aero look
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Custom shapes for specific UI elements
val AeroCardShape = RoundedCornerShape(
    topStart = 24.dp,
    topEnd = 24.dp,
    bottomStart = 24.dp,
    bottomEnd = 24.dp
)

val AeroButtonShape = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 16.dp,
    bottomEnd = 16.dp
)

val AeroBottomSheetShape = RoundedCornerShape(
    topStart = 32.dp,
    topEnd = 32.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)
