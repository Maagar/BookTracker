package com.example.booktracker.presentation.screen.Profile.component

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.booktracker.R

@Composable
fun BooksPieChart(percentage: Float) {
    ElevatedCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val readColor = MaterialTheme.colorScheme.primary
            val unreadColor = MaterialTheme.colorScheme.tertiary

            Box(contentAlignment = Alignment.Center) {
                Canvas(
                    modifier = Modifier
                        .size(240.dp, 120.dp)
                        .padding(16.dp)
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val arcRadius = (canvasWidth / 2) - 20f
                    val arcStart = Offset(canvasWidth / 2, canvasHeight)

                    drawArc(
                        color = readColor,
                        startAngle = 180f,
                        sweepAngle = percentage / 100f * 180f,
                        useCenter = false,
                        topLeft = arcStart - Offset(arcRadius, arcRadius),
                        size = Size(arcRadius * 2, arcRadius * 2),
                        style = Stroke(width = 32f, cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = unreadColor,
                        startAngle = 180f + percentage / 100f * 180f,
                        sweepAngle = (100f - percentage) / 100f * 180f,
                        useCenter = false,
                        topLeft = arcStart - Offset(arcRadius, arcRadius),
                        size = Size(arcRadius * 2, arcRadius * 2),
                        style = Stroke(width = 32f, cap = StrokeCap.Round)
                    )
                }
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${if (percentage >= 0) percentage.toInt() else 0}%",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = stringResource(R.string.reading_progress_owned_books),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
            val context = LocalContext.current
            Text(
                text = getMessage(percentage / 100, context),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

fun getMessage(percentageRead: Float, context: Context): String {
    return when (percentageRead) {
        0f -> context.getString(R.string.zero_percent_message)
        in 0f..0.25f -> context.getString(R.string.low_percent_message)
        in 0.26f..0.75f -> context.getString(R.string.mid_percent_message)
        in 0.76f..1f -> context.getString(R.string.high_percent_message)
        1f -> context.getString(R.string.hundred_percent_message)
        else -> context.getString(R.string.no_owned_books_message)
    }
}