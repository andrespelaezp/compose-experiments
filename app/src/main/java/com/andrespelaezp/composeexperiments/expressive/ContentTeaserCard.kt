package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
// Re-importing necessary components from the previous theme setup
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource // For placeholder image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpressiveContentTeaserCard(
    category: String,
    title: String,
    subtitle: String,
    imageUrl: Int?, // Placeholder for a real image URI or resource ID
    onActionClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Card(
        onClick = onActionClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = MaterialTheme.shapes.large, // Using our expressive large shape
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f) // Slightly transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp, pressedElevation = 12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    // Expressive shape for the image - can be different from the card's main shape
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            ) {
                if (imageUrl != null) {
                    Image(
                        painter = painterResource(id = imageUrl), // Replace with your actual image loading
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Placeholder if no image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    ) {
                        Text(
                            "Image Placeholder",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                }

                // Category Tag with expressive shape and color
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                        .background(
                            MaterialTheme.colorScheme.tertiary,
                            shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = category.uppercase(),
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        Icons.Filled.Done,
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge, // Bold and impactful
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onActionClick,
                    shape = MaterialTheme.shapes.small, // Expressive small shape
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text("Discover More", style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Content Teaser Card Light")
@Composable
fun PreviewExpressiveContentTeaserCardLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                ExpressiveContentTeaserCard(
                    category = "Adventure",
                    title = "Uncharted Realms",
                    subtitle = "Journey beyond the known horizon and uncover secrets of ancient civilizations.",
                    // Replace with a valid drawable resource in your project or null
                    imageUrl = null, // e.g. R.drawable.my_adventure_image
                    onActionClick = {},
                    onBookmarkClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Content Teaser Card Dark")
@Composable
fun PreviewExpressiveContentTeaserCardDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(16.dp)) {
                ExpressiveContentTeaserCard(
                    category = "Sci-Fi",
                    title = "Cybernetic Dawn",
                    subtitle = "In a world of neon and steel, a new consciousness arises. Can humanity adapt?",
                    imageUrl = null, // e.g. R.drawable.my_scifi_image
                    onActionClick = {},
                    onBookmarkClick = {}
                )
            }
        }
    }
}