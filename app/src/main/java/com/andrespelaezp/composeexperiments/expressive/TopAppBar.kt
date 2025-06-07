package com.andrespelaezp.composeexperiments.expressive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrespelaezp.composeexperiments.expressive.notifications.createProgressNotificationChannel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpressiveAppBarScreen(
    onNavigationIconClicked: () -> Unit
) {
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var userPoints by remember { mutableStateOf(125) }
    val context = LocalContext.current

    // Create notification channel on first composition
    LaunchedEffect(Unit) {
        createProgressNotificationChannel(context)
    }

    CenterAlignedTopAppBar( // Or TopAppBar for start/end alignment
        title = {
            Text(
                "EXPRESSIVE BAR",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Monospace // Expressive font
                ),
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavigationIconClicked.invoke() }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Open Menu",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        actions = {
            // 1. Clickable Item: Simple action button
            IconButton(onClick = { /* Handle search action */ }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(26.dp) // Slightly larger
                )
            }

            // 2. Toggleable Item: Notification bell
            IconToggleButton(
                checked = isNotificationsEnabled,
                onCheckedChange = { isNotificationsEnabled = it },
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .border( // Expressive border when active
                        width = if (isNotificationsEnabled) 1.5.dp else 0.dp,
                        color = if (isNotificationsEnabled) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                        shape = CircleShape
                    )
            ) {
                val icon = if (isNotificationsEnabled) Icons.Filled.Notifications else Icons.Filled.Build
                val tint = if (isNotificationsEnabled) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                Icon(
                    imageVector = icon,
                    contentDescription = "Toggle Notifications",
                    tint = tint,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Spacer for visual separation
            Spacer(modifier = Modifier.width(8.dp))

            // 3. Custom Item: User points display
            CustomUserPointsItem(
                points = userPoints,
                onClick = { userPoints += 10 /* Example action */ }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f), // Slightly transparent for depth
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        // Potentially add scrollBehavior for collapsable app bars
    )
}

@Composable
fun CustomUserPointsItem(points: Int, onClick: () -> Unit) {
    // This item combines an icon and text in a chip-like, clickable container.
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min) // Ensure consistent height for children
            .clip(MaterialTheme.shapes.small) // Using expressive small shape
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .padding(end = 4.dp), // Extra padding for icon spacing
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Points",
            tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = points.toString(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp // Custom size for this item
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

// Previews
@Preview(showBackground = true, name = "Expressive AppBar Light")
@Composable
fun PreviewExpressiveAppBarScreenLight() {
    MyCoolExpressiveTheme(darkTheme = false) {
        ExpressiveAppBarScreen(
            onNavigationIconClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Expressive AppBar Dark")
@Composable
fun PreviewExpressiveAppBarScreenDark() {
    MyCoolExpressiveTheme(darkTheme = true) {
        ExpressiveAppBarScreen(
            onNavigationIconClicked = {}
        )
    }
}
