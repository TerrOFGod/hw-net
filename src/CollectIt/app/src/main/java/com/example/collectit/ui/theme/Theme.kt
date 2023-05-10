package com.example.collectit.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Send80,
    onPrimary = Send20,
    primaryContainer = Send30,
    onPrimaryContainer = Send90,
    inversePrimary = Send40,
    secondary = DarkSend80,
    onSecondary = DarkSend20,
    secondaryContainer = DarkSend30,
    onSecondaryContainer = DarkSend90,
    error = Red80,
    onError = Red20,
    errorContainer = Red90,
    onErrorContainer = Red30,
    background = Grey10,
    onBackground = Grey90,
    surface = SendGrey30,
    onSurface = SendGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = SendGrey30,
    onSurfaceVariant = SendGrey80,
    outline = SendGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Send40,
    onPrimary = Send80,
    primaryContainer = Send90,
    onPrimaryContainer = Send10,
    inversePrimary = Send80,
    secondary = DarkSend40,
    onSecondary = Color.White,
    secondaryContainer = DarkSend90,
    onSecondaryContainer = DarkSend10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = SendGrey90,
    onSurface = SendGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = SendGrey90,
    onSurfaceVariant = SendGrey30,
    outline = SendGrey50
)

@Composable
fun CollectItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}