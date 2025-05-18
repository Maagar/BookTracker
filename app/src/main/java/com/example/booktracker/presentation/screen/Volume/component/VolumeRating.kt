package com.example.booktracker.presentation.screen.Volume.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.booktracker.R

@Composable
fun VolumeRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    starSize: Dp = 48.dp,
    spacing: Dp = 12.dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(modifier = modifier) {
        for (i in 1..5) {
            val isFilled = i <= rating

            if (isFilled) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star $i",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(starSize)
                        .padding(end = spacing)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ){ onRatingChanged(i) }
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.star_outline),
                    contentDescription = "Star $i",
                    modifier = Modifier
                        .size(starSize)
                        .padding(end = spacing)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { onRatingChanged(i) }
                )
            }
        }
    }
}

