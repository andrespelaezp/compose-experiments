package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing

// Define some expressive colors
val ExpressivePink = Color(0xFFE91E63)
val ExpressivePurple = Color(0xFF9C27B0)
val ExpressiveTeal = Color(0xFF009688)
val ExpressiveLime = Color(0xFFCDDC39)
val DarkSurface = Color(0xFF1E1E1E)
val LightSurface = Color(0xFFFFFBFE)

private val ExpressiveDarkColorScheme = darkColorScheme(
    primary = ExpressivePink,
    secondary = ExpressivePurple,
    tertiary = ExpressiveTeal,
    background = DarkSurface,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF3A3A3A),
    outline = ExpressiveLime
)

private val ExpressiveLightColorScheme = lightColorScheme(
    primary = ExpressivePink,
    secondary = ExpressivePurple,
    tertiary = ExpressiveTeal,
    background = LightSurface,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE7E0EC),
    outline = ExpressivePurple
)

// Expressive Typography
val ExpressiveTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Serif, // Or a custom bold font
        fontWeight = FontWeight.ExtraBold,
        fontSize = 58.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp,
        color = ExpressivePurple // Example of specific color for a style
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    // Define other text styles as needed
)

// Expressive Shapes
val ExpressiveShapes = Shapes(
    small = CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp), // Asymmetric cut
    medium = RoundedCornerShape(20.dp), // More pronounced rounding
    large = RoundedCornerShape(topStart = 32.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 32.dp) // Unique rounding
)

// Expressive Animation Specs
val bouncySpring = spring<Float>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessLow
)

val gentleSpring = spring<Float>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessVeryLow
)

val quickTween = tween<Float>(durationMillis = 300, easing = FastOutSlowInEasing)
val expressiveTween = tween<Float>(durationMillis = 600, easing = LinearOutSlowInEasing)

@Composable
fun MyCoolExpressiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is preferred by M3, but we're showcasing a specific expressive theme here.
    // You could integrate dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        ExpressiveDarkColorScheme
    } else {
        ExpressiveLightColorScheme
    }

    // You might also look into MaterialExpressiveTheme if you're using a very new
    // version of the material3 library and want to opt into experimental features.
    // For now, customizing MaterialTheme is the standard way.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = ExpressiveTypography,
        shapes = ExpressiveShapes,
        content = content
    )
}