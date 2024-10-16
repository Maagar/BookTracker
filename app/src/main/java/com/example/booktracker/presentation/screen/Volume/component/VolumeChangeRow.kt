package com.example.booktracker.presentation.screen.Volume.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Volume

@Composable
fun VolumeChangeRow(
    onClickNext: () -> Unit,
    onClickBack: () -> Unit,
    nextVolume: Volume?,
    previousVolume: Volume?
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(8.dp)
                .let { modifier ->
                    if (previousVolume != null) modifier.clickable { onClickBack() } else modifier
                },
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            if (previousVolume != null) {
                Text(stringResource(R.string.previous_volume))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.next_volume),
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsyncImage(
                        previousVolume.cover_url,
                        null,
                        modifier = Modifier.size(80.dp)
                    )
                }

            } else {
                Text(stringResource(R.string.first_volume_msg))
            }
        }

        VerticalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(8.dp)
                .let { modifier ->
                    if (nextVolume != null) modifier.clickable { onClickNext() } else modifier
                },
            horizontalAlignment = Alignment.Start
        ) {
            if (nextVolume != null) {
                Text(stringResource(R.string.next_volume))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        nextVolume.cover_url,
                        null,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.next_volume),
                        modifier = Modifier.size(36.dp)
                    )

                }

            } else {
                Text(stringResource(R.string.last_volume_msg))
            }
        }

    }
}