package com.taaboor.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PrimaryBlue = Color(0xFF3B7BF6)
val PrimaryBlueDark = Color(0xFF2557C7)
val SuccessTeal = Color(0xFF17A589)
val WarningOrange = Color(0xFFF5A623)
val ErrorRed = Color(0xFFE74C3C)
val BgWhite = Color(0xFFFFFFFF)
val SurfaceSoft = Color(0xFFF5F8FF)
val TextPrimary = Color(0xFF1B2430)
val TextSecondary = Color(0xFF6B7684)

private val TaaboorColors = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = SurfaceSoft,
    secondary = SuccessTeal,
    background = BgWhite,
    surface = BgWhite,
    error = ErrorRed,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

val TaaboorTypography = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp),
    headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
    titleLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    bodyLarge = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
    bodyMedium = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
)

@Composable
fun TaaboorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TaaboorColors,
        typography = TaaboorTypography,
        content = content
    )
}
