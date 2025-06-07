package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DashboardMetricCard(
    metricName: String,
    metricValue: String,
    metricUnit: String,
    trendIcon: ImageVector,
    trendColor: Color,
    sparklineData: List<Float> = emptyList(), // Simple list of values for a tiny graph
    onCardClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
     val animatedMetricValue by animateFloatAsState(targetValue = metricValue.toFloatOrNull() ?: 0f, label = "metricAnimation")

    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberMyDesignSystemRipple(bounded = true, color = trendColor, radius = 12.dp),
                onClick = onCardClick
            ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = metricName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Icon(
                    imageVector = trendIcon,
                    contentDescription = "Trend",
                    tint = trendColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = animatedMetricValue.toString(), // Potentially animatedMetricValue.format(2)
//                    text = metricValue, // Potentially animatedMetricValue.format(2)
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = metricUnit,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }


            if (sparklineData.isNotEmpty() && sparklineData.size > 1) {
                Spacer(modifier = Modifier.height(16.dp))
                // Simple Sparkline
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    val maxValue = sparklineData.maxOrNull() ?: 1f
                    val minValue = sparklineData.minOrNull() ?: 0f
                    val range = if (maxValue == minValue) 1f else maxValue - minValue

                    val path = androidx.compose.ui.graphics.Path()
                    sparklineData.forEachIndexed { index, value ->
                        val x = size.width * (index.toFloat() / (sparklineData.size - 1))
                        val y = size.height * (1 - (value - minValue) / range) // Invert Y-axis
                        if (index == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = trendColor.copy(alpha = 0.8f),
                        style = Stroke(
                            width = 4.dp.toPx(),
                            cap = StrokeCap.Round,
                            pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp)) // Ensure some padding at the bottom
        }
    }
}

@Preview(showBackground = true, name = "Dashboard Metric Light")
@Composable
fun PreviewDashboardMetricCardLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(8.dp)) {
                DashboardMetricCard(
                    metricName = "Active Users",
                    metricValue = "1245",
                    metricUnit = "users",
                    trendIcon = Icons.Filled.ThumbUp,
                    trendColor = MaterialTheme.colorScheme.secondary, // Using expressive color
                    sparklineData = listOf(10f, 12f, 9f, 15f, 13f, 18f, 17f, 20f),
                    onCardClick = {}
                )
                DashboardMetricCard(
                    metricName = "Engagement Rate",
                    metricValue = "67.3",
                    metricUnit = "%",
                    trendIcon = Icons.Filled.ThumbUp,
                    trendColor = ExpressiveLime, // From our custom colors
                    sparklineData = listOf(50f, 55f, 60f, 58f, 62f, 65f, 67f),
                    onCardClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dashboard Metric Dark")
@Composable
fun PreviewDashboardMetricCardDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(8.dp)) {
                DashboardMetricCard(
                    metricName = "Revenue",
                    metricValue = "25800",
                    metricUnit = "USD",
                    trendIcon = Icons.Filled.ThumbUp,
                    trendColor = MaterialTheme.colorScheme.tertiary,
                    sparklineData = listOf(1f, 2f, 1.5f, 3f, 2.5f, 4f, 3.8f),
                    onCardClick = {}
                )
            }
        }
    }
}

data class MyDesignSystemRippleConfiguration(
    val color: Color,
    val alpha: RippleAlpha,
    val bounded: Boolean = true,
    val radius: Dp = Dp.Unspecified // Or any other custom properties
)

object MyDesignSystemTheme {

    val rippleConfiguration: MyDesignSystemRippleConfiguration
        @Composable
        get() = MyDesignSystemRippleConfiguration(
            color = MaterialTheme.colorScheme.primary, // Or your custom color
            alpha = RippleAlpha(
                pressedAlpha = 0.12f,
                focusedAlpha = 0.12f,
                hoveredAlpha = 0.08f,
                draggedAlpha = 0.08f
            )
        )
}

@Composable
fun rememberMyDesignSystemRipple(
    bounded: Boolean = true,
    radius: Dp = Dp.Unspecified, // Or your design system's default
    color: Color = MyDesignSystemTheme.rippleConfiguration.color,
    rippleAlpha: RippleAlpha = MyDesignSystemTheme.rippleConfiguration.alpha
): Indication {

    return androidx.compose.material.ripple(
        bounded = bounded,
        radius = radius,
        color = color
    )

    // Note: The new material ripple() function takes a RippleConfiguration parameter
    // where you can specify alpha.
    // e.g., androidx.compose.material3.ripple(
    //    configuration = androidx.compose.material3.RippleConfiguration(
    //        color = color,
    //        rippleAlpha = rippleAlpha.alpha()
    //    )
    // )
}

// Helper class to manage the state and animation of a single ripple
private class RippleState(
    val origin: androidx.compose.ui.geometry.Offset?,
    val pressInteraction: PressInteraction.Press? = null, // To identify the press
    val onAnimationEnd: (RippleState) -> Unit
) {
    private val progressAnimatable = Animatable(0f)
    private var animationJob: Job? = null

    var isAnimating by mutableStateOf(false)
        private set

    suspend fun startAnimation() {
        isAnimating = true
        coroutineScope {
            animationJob = launch {
                progressAnimatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 350) // Adjust duration as needed
                )
                isAnimating = false
                onAnimationEnd(this@RippleState)
            }

        }
    }
}