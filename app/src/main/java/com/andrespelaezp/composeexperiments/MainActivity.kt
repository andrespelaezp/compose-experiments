package com.andrespelaezp.composeexperiments

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andrespelaezp.composeexperiments.experiments.swipes.SwipeableEmailCard
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.LiveCompassWidget
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.MarkdownBubble
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.SpringyToggle
import com.andrespelaezp.composeexperiments.expressive.BoldExtendedFab
import com.andrespelaezp.composeexperiments.expressive.DashboardMetricCard
import com.andrespelaezp.composeexperiments.expressive.ExpressiveAppBarScreen
import com.andrespelaezp.composeexperiments.expressive.ExpressiveContentTeaserCard
import com.andrespelaezp.composeexperiments.expressive.ExpressiveLime
import com.andrespelaezp.composeexperiments.expressive.notifications.NotificationScreen
import com.andrespelaezp.composeexperiments.expressive.notifications.createProgressNotificationChannel
import com.andrespelaezp.composeexperiments.ui.theme.ComposeExperimentsTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create notification channel (can also be done in Application class)
        createProgressNotificationChannel(this)

        setContent {
            ComposeExperimentsTheme {

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Spacer(Modifier.height(12.dp))
                            Text("Main Menu", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                            HorizontalDivider()

                            Text("Components", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                            NavigationDrawerItem(
                                label = { Text("Dashboard") },
                                selected = false,
                                onClick = {
                                    navController.navigate("dashboard")
                                    closeDrawer(scope, drawerState)
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("Cards") },
                                selected = false,
                                onClick = {
                                    navController.navigate("cards")
                                    closeDrawer(scope, drawerState)
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("Notifications") },
                                selected = false,
                                onClick = {
                                    navController.navigate("notifications")
                                    closeDrawer(scope, drawerState)
                                }
                            )
                        }
                    },
                ) {
                    Scaffold(
                        topBar = { ExpressiveAppBarScreen(onNavigationIconClicked = { scope.launch { drawerState.open() } }) },
                        floatingActionButton = {
                            val currentRoute = navController.currentBackStackEntry?.destination?.route
                            println("Current route: $currentRoute")
                            if (currentRoute == null || currentRoute == "welcome") {
                                BoldExtendedFab(
                                    text = "Start Journey",
                                    onClick = { Log.d("FAB", "Start Journey clicked") },
                                )
                            }
                        }
                    ) { innerPadding ->

                        NavHost(navController,
                            startDestination = "welcome",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("welcome") {
                                Column {
                                    Greeting(
                                        name = "Android Compose Experiments",
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                            }
                            composable("dashboard") {
                                Column {
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
                            composable("cards") { backStackEntry ->
                                ExpressiveContentTeaserCard(
                                    category = "Adventure",
                                    title = "Uncharted Realms",
                                    subtitle = "Journey beyond the known horizon and uncover secrets of ancient civilizations.",
                                    // Replace with a valid drawable resource in your project or null
                                    imageUrl = null, // e.g. R.drawable.my_adventure_image
                                    onActionClick = {},
                                    onBookmarkClick = {}
                                )
                                ExpressiveContentTeaserCard(
                                    category = "Sci-Fi",
                                    title = "Cybernetic Dawn",
                                    subtitle = "In a world of neon and steel, a new consciousness arises. Can humanity adapt?",
                                    imageUrl = null, // e.g. R.drawable.my_scifi_image
                                    onActionClick = {},
                                    onBookmarkClick = {}
                                )
                            }
                            composable("notifications") {
                                Column {
                                    NotificationScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeExperimentsTheme {
        Greeting("Android")
    }
}

private fun closeDrawer(scope: CoroutineScope, drawerState: DrawerState) {
    scope.launch { drawerState.close() }
}