package com.example.vitalflow.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = AeroBlue,
    onPrimary = AeroText,
    primaryContainer = AeroLightBlue,
    onPrimaryContainer = AeroText,
    secondary = AeroAccent,
    onSecondary = AeroText,
    secondaryContainer = AeroGlass,
    onSecondaryContainer = AeroText,
    tertiary = AeroInfo,
    onTertiary = AeroText,
    background = AeroBackground,
    onBackground = AeroText,
    surface = AeroSurface,
    onSurface = AeroText,
    surfaceVariant = AeroGlass,
    onSurfaceVariant = AeroTextSecondary,
    error = AeroError,
    onError = AeroText
)

private val DarkColorScheme = darkColorScheme(
    primary = AeroBlueDark,
    onPrimary = AeroTextDark,
    primaryContainer = AeroLightBlueDark,
    onPrimaryContainer = AeroTextDark,
    secondary = AeroAccentDark,
    onSecondary = AeroTextDark,
    secondaryContainer = AeroGlassDark,
    onSecondaryContainer = AeroTextDark,
    tertiary = AeroInfo,
    onTertiary = AeroTextDark,
    background = AeroBackgroundDark,
    onBackground = AeroTextDark,
    surface = AeroSurfaceDark,
    onSurface = AeroTextDark,
    surfaceVariant = AeroGlassDark,
    onSurfaceVariant = AeroTextSecondaryDark,
    error = AeroError,
    onError = AeroTextDark
)

@Composable
fun VitalFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

// Extension function to easily get themed colors in composables
object AeroTheme {
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes
}
