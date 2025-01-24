package com.example.booktracker.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SeriesProgressIndicator(ownedProgress: Float, readingProgress: Float, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(ownedProgress)
                .height(height)
                .background(
                    MaterialTheme.colorScheme.tertiary
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(readingProgress)
                .height(height)
                .background(
                    MaterialTheme.colorScheme.primary
                )
        )
    }
}