package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoldExtendedFab(
    text: String,
    onClick: () -> Unit,
    buttonShapeSize: CornerBasedShape = MaterialTheme.shapes.medium
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.PlayArrow, contentDescription = text, tint = MaterialTheme.colorScheme.onPrimary) },
        text = { Text(text, style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)) }, // Using titleLarge for impact
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = buttonShapeSize, // Expressive medium shape
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, name = "Bold FAB Light")
@Composable
fun PreviewBoldExtendedFabLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomEnd) {
                BoldExtendedFab(text = "Start Journey", onClick = {})
            }
        }
    }
}

@Preview(showBackground = true, name = "Bold FAB Dark")
@Composable
fun PreviewBoldExtendedFabDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomEnd) {
                BoldExtendedFab(text = "Ignite Now", onClick = {})
            }
        }
    }
}

@Preview(showBackground = true, name = "Bold FAB Dark")
@Composable
fun PreviewBoldExtendedFabSmallDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomEnd) {
                BoldExtendedFab(text = "Ignite Now", onClick = {}, buttonShapeSize = MaterialTheme.shapes.small)
            }
        }
    }
}

@Preview(showBackground = true, name = "Bold FAB Dark")
@Composable
fun PreviewBoldExtendedFabLargeDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomEnd) {
                BoldExtendedFab(text = "Ignite Now", onClick = {}, buttonShapeSize = MaterialTheme.shapes.large)
            }
        }
    }
}