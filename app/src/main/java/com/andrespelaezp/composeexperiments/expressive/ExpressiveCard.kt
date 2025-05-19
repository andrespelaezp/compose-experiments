package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class) // For Card interaction
@Composable
fun ExpressiveFeatureCard(
    title: String,
    description: String,
    onClickAction: () -> Unit,
    buttonShapeSize: CornerBasedShape = MaterialTheme.shapes.small
) {
    Card(
        onClick = onClickAction,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large, // Using our expressive large shape
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp) // Generous padding
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge, // Bold and big
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button( // Action with expressive styling
                onClick = onClickAction,
                shape = buttonShapeSize, // Using our expressive small shape
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Explore",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Explore Feature", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Preview(showBackground = true, name = "Expressive Card Light")
@Composable
fun PreviewExpressiveFeatureCardLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        Surface {
            ExpressiveFeatureCard(
                title = "Dynamic Beats",
                description = "Experience music that adapts to your mood and environment in real-time.",
                onClickAction = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Expressive Card Dark")
@Composable
fun PreviewExpressiveFeatureCardDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface {
            ExpressiveFeatureCard(
                title = "Vivid Dreams",
                description = "Unlock a new realm of visual storytelling with AI-powered dreamscapes.",
                onClickAction = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Expressive Card Dark")
@Composable
fun PreviewExpressiveFeatureCardMediumDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface {
            ExpressiveFeatureCard(
                title = "Vivid Dreams",
                description = "Unlock a new realm of visual storytelling with AI-powered dreamscapes.",
                onClickAction = {},
                buttonShapeSize = MaterialTheme.shapes.medium
            )
        }
    }
}

@Preview(showBackground = true, name = "Expressive Card Dark")
@Composable
fun PreviewExpressiveFeatureCardLargeDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface {
            ExpressiveFeatureCard(
                title = "Vivid Dreams",
                description = "Unlock a new realm of visual storytelling with AI-powered dreamscapes.",
                onClickAction = {},
                buttonShapeSize = MaterialTheme.shapes.large
            )
        }
    }
}