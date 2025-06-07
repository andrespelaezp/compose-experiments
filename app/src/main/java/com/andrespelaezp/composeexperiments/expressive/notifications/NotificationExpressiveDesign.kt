package com.andrespelaezp.composeexperiments.expressive.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.andrespelaezp.composeexperiments.R
import kotlinx.coroutines.delay

/*
* This file contains the code for creating and managing notifications in an Android application.
* Currently notifications don't support Compose UI, so we use RemoteViews to create custom layouts.
* TODO: Consider using Jetpack Compose for notifications when it becomes available.
*
 */

val ExpressivePink = Color(0xFFE91E63)
const val PROGRESS_CHANNEL_ID = "progress_channel"
const val DETERMINATE_NOTIFICATION_ID = 1001
const val INDETERMINATE_NOTIFICATION_ID = 1002

fun createProgressNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Progress Notifications"
        val descriptionText = "Channel for notifications showing progress"
        val importance = NotificationManager.IMPORTANCE_LOW // Use LOW to avoid sound for progress
        val channel = NotificationChannel(PROGRESS_CHANNEL_ID, name, importance).apply {
            description = descriptionText
            enableLights(true)
            lightColor = ExpressivePink.hashCode() // Use a theme color
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@SuppressLint("MissingPermission")
suspend fun showDeterminateProgressNotification(context: Context) {
    val customNotificationLayout = RemoteViews("com.andrespelaezp.composeexperiments", R.layout.notification_layout)

    // Set content for the RemoteViews
    customNotificationLayout.setTextViewText(R.id.notification_title, "Material")
    customNotificationLayout.setTextViewText(R.id.notification_message, "Expressive Notification")
    customNotificationLayout.setProgressBar(R.id.notification_progress_bar, 100, 75, false) // max, progress, indeterminate

    val builder = NotificationCompat.Builder(context, PROGRESS_CHANNEL_ID)
        .setSmallIcon(android.R.drawable.star_on) // Replace with your own small icon
        .setCustomContentView(customNotificationLayout)
        .setOngoing(true) // Makes it non-swipeable while in progress
        .setOnlyAlertOnce(true) // Don't make sound/vibrate for updates
        .setCustomBigContentView(customNotificationLayout) // Using the same layout for simplicity
        // Apply DecoratedCustomViewStyle to ensure system decorations (app icon, timestamp, expand/collapse) are still present
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())

    NotificationManagerCompat.from(context).apply {
        val maxProgress = 100
        for (currentProgress in 0..maxProgress step 10) {
            customNotificationLayout.setProgressBar(R.id.notification_progress_bar, 100, currentProgress, false) // max, progress, indeterminate
            customNotificationLayout.setTextViewText(R.id.notification_progress_text, "$currentProgress% Complete")
            notify(DETERMINATE_NOTIFICATION_ID, builder.build())
            delay(500) // Simulate progress
        }
        builder.setContentText("Download complete!")
            .setProgress(0, 0, false) // Remove progress bar
            .setOngoing(false) // Make it swipeable
        notify(DETERMINATE_NOTIFICATION_ID, builder.build())
        // Optionally, auto-cancel after a delay
        delay(3000)
        cancel(DETERMINATE_NOTIFICATION_ID)
    }
}


@SuppressLint("MissingPermission")
fun showIndeterminateProgressNotification(context: Context, showNotification: Boolean) {
    NotificationManagerCompat.from(context).apply {
        if (showNotification) {
            // Create a RemoteViews object from your custom XML layout
            val customNotificationLayout = RemoteViews("com.andrespelaezp.composeexperiments", R.layout.notification_layout)

            // Set content for the RemoteViews
            customNotificationLayout.setTextViewText(R.id.notification_title, "Material")
            customNotificationLayout.setTextViewText(R.id.notification_message, "Expressive Notification")
            customNotificationLayout.setProgressBar(R.id.notification_progress_bar, 100, 75, false) // max, progress, indeterminate
            customNotificationLayout.setTextViewText(R.id.notification_progress_text, "75% Complete")

            val builder = NotificationCompat.Builder(context, PROGRESS_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_ff) // Replace with your own small icon
                .setCustomContentView(customNotificationLayout)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setCustomBigContentView(customNotificationLayout) // Using the same layout for simplicity
                // Apply DecoratedCustomViewStyle to ensure system decorations (app icon, timestamp, expand/collapse) are still present
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())

            notify(INDETERMINATE_NOTIFICATION_ID, builder.build())
        } else {
            cancel(INDETERMINATE_NOTIFICATION_ID) // To dismiss if called with false
        }
    }
}