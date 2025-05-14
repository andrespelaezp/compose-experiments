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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrespelaezp.composeexperiments.experiments.swipes.SwipeableEmailCard
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.LiveCompassWidget
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.MarkdownBubble
import com.andrespelaezp.composeexperiments.experiments.toggle_bubble.SpringyToggle
import com.andrespelaezp.composeexperiments.ui.theme.ComposeExperimentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExperimentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var isToggled by remember { mutableStateOf(false) }

                    Column(Modifier.padding(16.dp)) {
                        Greeting(
                            name = "Android Compose Experiments",
                            modifier = Modifier.padding(innerPadding)
                        )

                        Text("Springy Toggle")
                        SpringyToggle(isToggled) { isToggled = it }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Markdown Bubble")
                        MarkdownBubble("**Hello!** This is a _Markdown_ bubble.`inline code`.")

                        Text("Compass Widget")
                        LiveCompassWidget()


                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Swipeable Email Card")
                        LazyColumn {
                            items(5) { index ->
                                SwipeableEmailCard(
                                    onArchive = { Log.d("SwipeAction", "Archived item $index") },
                                    onDelete = { Log.d("SwipeAction", "Deleted item $index") }
                                ) {
                                    Text("Email item #$index", style = MaterialTheme.typography.bodyLarge)
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