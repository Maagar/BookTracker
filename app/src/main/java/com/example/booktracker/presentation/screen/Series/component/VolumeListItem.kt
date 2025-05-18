package com.example.booktracker.presentation.screen.Series.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume
import com.example.booktracker.presentation.component.VolumeStatusButtons
import com.example.ui.theme.AppTypography

@Composable
fun VolumeListItem(
    volume: Volume,
    onItemClick: (Volume) -> Unit = {},
    onOwnedVolumeClick: () -> Unit,
    onReadClick: () -> Unit,
    isSingleVolume: Boolean,

) {
    ListItem(
        modifier = Modifier.let { modifier ->
            if (!isSingleVolume) modifier.clickable { onItemClick(volume) } else modifier
        },
        headlineContent = { Text(text = volume.title, style = AppTypography.titleMedium) },
        leadingContent = {
            AsyncImage(
                model = volume.cover_url,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                fallback = painterResource(R.drawable.no_image_placeholder)
            )
        },
        trailingContent = {
            VolumeStatusButtons(
                volume = volume,
                onOwnedVolumeClick = onOwnedVolumeClick,
                onReadClick = onReadClick
            )
        }
    )
}
