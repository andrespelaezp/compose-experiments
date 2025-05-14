package com.andrespelaezp.composeexperiments.experiments.swipes

//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SwipeableEmailCard(
    modifier: Modifier = Modifier,
    onArchive: () -> Unit,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberDismissState(
        confirmStateChange = {
            when (it) {
                DismissValue.DismissedToEnd -> {
                    onArchive()
                    true
                }
                DismissValue.DismissedToStart -> {
                    onDelete()
                    true
                }
                else -> false
            }
        }
    )

    SwipeToDismiss(
        state = swipeState,
        directions = setOf(
            DismissDirection.StartToEnd, DismissDirection.EndToStart),
        background = {
            val color = when (swipeState.dismissDirection) {
                DismissDirection.StartToEnd -> Color(0xFF4CAF50) // Green (Archive)
                DismissDirection.EndToStart -> Color(0xFFF44336) // Red (Delete)
                null -> Color.Transparent
            }

            val alignment = when (swipeState.dismissDirection) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
                null -> Alignment.Center
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    imageVector = when (swipeState.dismissDirection) {
                        DismissDirection.StartToEnd -> Icons.Default.ArrowBack
                        DismissDirection.EndToStart -> Icons.Default.Delete
                        else -> Icons.Default.MoreVert
                    },
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        dismissContent = {
            Card(
                elevation = 4.dp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {
                Box(Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    )
}

@Preview
@Composable
fun EmailListPreview() {
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