package com.andrespelaezp.composeexperiments.experiments.toggle_bubble

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt



@Composable
fun LiveCompassWidget() {
    val context = LocalContext.current
    val azimuth = rememberCompassOrientation(context)

    Box(
        Modifier
            .size(250.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2.5f
            val directions = listOf("N", "E", "S", "W")
            for (i in directions.indices) {
                val angle = Math.toRadians((i * 90).toDouble())
                val x = center.x + radius * kotlin.math.sin(angle).toFloat()
                val y = center.y - radius * kotlin.math.cos(angle).toFloat()
                drawContext.canvas.nativeCanvas.drawText(
                    directions[i],
                    x,
                    y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 32f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            rotate(-azimuth) {
                drawLine(
                    color = Color.Red,
                    start = center,
                    end = Offset(center.x, center.y - radius),
                    strokeWidth = 8f
                )
            }
        }
    }
    Text(text = "Azimuth: ${azimuth.roundToInt()}°", style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun rememberCompassOrientation(context: Context): Float {
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    var azimuth by remember { mutableStateOf(0f) }
    val gravity = remember { FloatArray(3) }
    val geomagnetic = remember { FloatArray(3) }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, gravity, 0, 3)
                    Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, geomagnetic, 0, 3)
                }

                val R = FloatArray(9)
                val I = FloatArray(9)
                if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(R, orientation)
                    val azimuthRad = orientation[0]
                    val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
                    azimuth = (azimuthDeg + 360) % 360
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, magnetometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return azimuth
}

@Preview(showBackground = true)
@Composable
fun PreviewCompass() {
    Column(Modifier.padding(16.dp)) {
        Text("Live Compass")
        LiveCompassWidget()
    }
}
