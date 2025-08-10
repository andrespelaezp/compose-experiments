package com.andrespelaezp.composeexperiments.experiments.canary_build

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrespelaezp.composeexperiments.expressive.bouncySpring
import com.andrespelaezp.composeexperiments.expressive.gentleSpring
import com.andrespelaezp.composeexperiments.expressive.quickTween
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntSize
import com.andrespelaezp.composeexperiments.expressive.MyCoolExpressiveTheme

@Composable
fun ExpressiveComponentsExample(onFabClick: () -> Unit) {
    var isCardExpanded by remember { mutableStateOf(false) }
    val cardElevation by animateDpAsState(
        targetValue = if (isCardExpanded) 24.dp else 8.dp,
        label = "Card Elevation"
    )

    var fabClicked by remember { mutableStateOf(false) }
    val fabScale by animateFloatAsState(
        targetValue = if (fabClicked) 1.2f else 1.0f,
        animationSpec = bouncySpring,
        label = "FAB Scale",
        finishedListener = { fabClicked = false }
    )

    MyCoolExpressiveTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onFabClick()
                        fabClicked = true
                    },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.scale(fabScale)
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Material 3 Expressive Components",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Expressive Card with animated elevation and content size
                Card(
                    onClick = { isCardExpanded = !isCardExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(animationSpec = tween<IntSize>(durationMillis = 600, easing = LinearOutSlowInEasing)), // Animate size changes
                    elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Animated Expressive Card",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "This card uses expressive animations for elevation and content expansion. " +
                                    "Tap to see it in action!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (isCardExpanded) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Expanded content appears with a smooth transition, utilizing a tween animation. " + // Updated comment
                                        "The elevation also changes with a 'bouncySpring' when expanded and 'gentleSpring' when collapsed.",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                PressableExpressiveButton()
            }
        }
    }
}

@Composable
fun PressableExpressiveButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = gentleSpring, // Subtle spring for press
        label = "Button Scale"
    )
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) -5f else 0f, // Slight rotation
        animationSpec = quickTween, // Quick response
        label = "Button Rotation"
    )

    Button(
        onClick = { /* Handle Button Click */ },
        modifier = Modifier
            .graphicsLayer { // Apply transformations
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .fillMaxWidth(0.8f),
        interactionSource = interactionSource,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Icon(Icons.Filled.PlayArrow, contentDescription = "Play", modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Expressive Button")
    }
}

 @Preview(showBackground = true)
 @Composable
 fun PreviewExpressiveComponentsExample() {
     ExpressiveComponentsExample(onFabClick = {})
 }
