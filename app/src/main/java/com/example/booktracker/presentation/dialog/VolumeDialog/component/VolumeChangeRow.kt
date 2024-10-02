package com.example.booktracker.presentation.dialog.VolumeDialog.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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
                .clickable {
                    onClickBack()
                },
            horizontalAlignment = Alignment.End
        ) {
            if (previousVolume != null) {
                Text("Previous volume")
                AsyncImage(
                    previousVolume.cover_url,
                    null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        VerticalDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(8.dp)
                .clickable {
                    onClickNext()
                },
            horizontalAlignment = Alignment.Start
        ) {
            if (nextVolume != null) {
                Text("Next volume")
                AsyncImage(
                    nextVolume.cover_url,
                    null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }

    }
}