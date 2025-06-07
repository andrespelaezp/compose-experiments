package com.andrespelaezp.composeexperiments.expressive.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            if (!isGranted) {
                println("Notification permission denied.")
            }
        }
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Content Area",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
//                userPoints += 50
            },
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Add 50 Points (to test custom item)")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (hasNotificationPermission) {
                            scope.launch { showDeterminateProgressNotification(context) }
                        } else {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            println("Determinate: Notification permission not granted. Requesting...")
                        }
                    } else { // Pre-Tiramisu, permission is implicitly granted
                        scope.launch { showDeterminateProgressNotification(context) }
                    }
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.Build, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Determinate")
            }
            Button(
                onClick = {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (hasNotificationPermission) {
                            showIndeterminateProgressNotification(context, true)
                        } else {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            println("Determinate: Notification permission not granted. Requesting...")
                        }
                    }

                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Filled.ThumbUp, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Indeterminate")
            }
        }
    }
}