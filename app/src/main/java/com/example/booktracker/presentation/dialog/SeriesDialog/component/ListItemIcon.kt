package com.example.booktracker.presentation.dialog.SeriesDialog.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ListItemIcon(
    painterResource: Int,
    backgroundColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    val iconSize = 30.dp
    Box(
        modifier = Modifier
            .size(44.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painterResource(painterResource),
            tint = iconColor,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center)
        )
    }
}