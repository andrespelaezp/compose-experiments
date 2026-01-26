package com.andrespelaezp.composeexperiments.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.andrespelaezp.composeexperiments.R // Assuming you have a default R file

object LiveUpdateNotificationManager {

    private const val CHANNEL_ID = "live_update_channel"
    private const val CHANNEL_NAME = "Live Updates"
    private const val NOTIFICATION_ID = 101

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW // Use low to avoid sound on update
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = "Channel for live update notifications"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showProgressNotification(
        context: Context,
        progress: Int,
        maxProgress: Int = 100,
        title: String = "Live Task In Progress",
        contentText: String = "Updating...",
        showPermissionCheck: () -> Unit
    ) {
        // Create channel (idempotent)
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_download) // Standard M3-style download icon
            .setContentTitle(title)
            .setContentText("$contentText ($progress%)")
            .setPriority(NotificationCompat.PRIORITY_LOW) // Low priority for ongoing tasks
            .setOngoing(true) // Makes the notification non-dismissible by swipe
            .setOnlyAlertOnce(true) // Don't make sound/vibrate for updates
            .setProgress(maxProgress, progress, false) // false = determinate progress

        // On first show, if progress is max, make it not ongoing and cancel it after a delay or allow dismissal
        if (progress >= maxProgress) {
            builder.setContentText("Task Completed!")
                .setProgress(0, 0, false) // Clear progress
                .setOngoing(false) // Allow dismissal
        }

        with(NotificationManagerCompat.from(context)) {
            // Consider adding a check for POST_NOTIFICATIONS permission on Android 13+
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                showPermissionCheck.invoke()
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun cancelNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            cancel(NOTIFICATION_ID)
        }
    }
}
