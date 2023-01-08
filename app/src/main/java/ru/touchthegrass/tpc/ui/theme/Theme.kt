package ru.touchthegrass.tpc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val tpcLightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = White1,
    primaryContainer = Purple,
    onPrimaryContainer = White2,
    secondary = Green0,
    onSecondary = White3,
    secondaryContainer = Green1,
    onSecondaryContainer = White4,
    error = Red,
    onError = White0,
    background = White6,
    onBackground = Gray0,
    surface = White5,
    onSurface = Gray0,
)

// TODO customize dark theme
private val tpcDarkColorScheme = tpcLightColorScheme

@Composable
fun TpcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val tpcColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> tpcDarkColorScheme
        else -> tpcLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = tpcColorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = tpcColorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
