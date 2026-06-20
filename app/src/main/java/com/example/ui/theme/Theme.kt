package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = PolishPrimaryDark,
    primaryContainer = PolishPrimaryContainerDark,
    secondary = PolishSecondaryDark,
    tertiary = PolishTertiaryDark,
    background = PolishBackgroundDark,
    surface = PolishSurfaceDark,
    surfaceVariant = PolishSurfaceVariantDark,
    outline = PolishOutlineDark,
    onPrimary = Color(0xFF003916),
    onPrimaryContainer = Color(0xFFB5F4BF),
    onSecondary = Color(0xFF283424),
    onTertiary = Color(0xFF003916),
    onBackground = Color(0xFFE2E3DC),
    onSurface = Color(0xFFE2E3DC),
    onSurfaceVariant = Color(0xFFC4C8BA)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PolishPrimary,
    primaryContainer = PolishPrimaryContainer,
    secondary = PolishSecondary,
    tertiary = PolishTertiary,
    background = PolishBackground,
    surface = PolishSurface,
    surfaceVariant = PolishSurfaceVariant,
    outline = PolishOutline,
    onPrimary = Color.White,
    onPrimaryContainer = Color(0xFF00210B),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1A1C18),
    onSurface = Color(0xFF1A1C18),
    onSurfaceVariant = Color(0xFF43493E)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Keep off by default to display our carefully designed Islamic themed visual assets
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
