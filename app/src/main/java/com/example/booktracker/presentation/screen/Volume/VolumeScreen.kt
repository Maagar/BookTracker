package com.example.booktracker.presentation.screen.Volume

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.data.model.VolumeToUpdate
import com.example.booktracker.presentation.component.VolumeBottomSheet
import com.example.booktracker.presentation.component.VolumeStatusButtons
import com.example.booktracker.presentation.screen.Volume.component.VolumeChangeRow
import com.example.booktracker.presentation.screen.Volume.component.VolumeHeader
import com.example.booktracker.presentation.screen.Volume.component.VolumeRating
import com.example.booktracker.presentation.screen.Volume.component.VolumeSynopsis
import com.example.booktracker.presentation.ui.viewmodel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeScreen(seriesViewModel: SeriesViewModel) {
    val volume by seriesViewModel.volume.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()

    var volumeIndex by remember { mutableIntStateOf(volumeList.indexOf(volume)) }
    val isLast = volumeList.lastIndex == volumeIndex
    val isFirst = volumeIndex == 0

    var animationDirection by remember { mutableIntStateOf(0) }

    var showOwnedBottomSheet by remember { mutableStateOf(false) }
    var showReadBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    VolumeBottomSheet(
        showOwnedBottomSheet = showOwnedBottomSheet,
        showReadBottomSheet = showReadBottomSheet,
        onDismiss = {
            showOwnedBottomSheet = false
            showReadBottomSheet = false
        },
        sheetState = sheetState,
        bottomSheetVolume = volume
    )

    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = volumeIndex,
                transitionSpec = {
                    slideInHorizontally { animationDirection * 2000 } + fadeIn() togetherWith
                            slideOutHorizontally { animationDirection * -2000 } + fadeOut()
                },
                label = ""
            ) { targetIndex ->
                volumeList.getOrNull(targetIndex)?.let {
                    VolumeHeader(it)
                }
            }

            HorizontalDivider(Modifier.fillMaxWidth())

            volume?.let {
                VolumeStatusButtons(
                    volume = it,
                    onOwnedVolumeClick = { showOwnedBottomSheet = true },
                    onReadClick = { showReadBottomSheet = true },
                    equalWeight = true
                )
            }

            HorizontalDivider()

            if (volumeList.size > 1) {
                AnimatedContent(
                    targetState = volumeIndex,
                    transitionSpec = { fadeIn(tween(500)) togetherWith fadeOut(tween(500)) },
                    label = ""
                ) { targetIndex ->
                    VolumeChangeRow(
                        onClickNext = {
                            if (!isLast) {
                                animationDirection = 1
                                volumeIndex += 1
                                seriesViewModel.selectVolume(targetIndex + 1)
                            }
                        },
                        onClickBack = {
                            if (!isFirst) {
                                animationDirection = -1
                                volumeIndex -= 1
                                seriesViewModel.selectVolume(targetIndex - 1)
                            }
                        },
                        nextVolume = if (!isLast) volumeList[volumeIndex + 1] else null,
                        previousVolume = if (!isFirst) volumeList[volumeIndex - 1] else null
                    )
                }
            }

            HorizontalDivider()

            volume?.let { v ->
                if (v.times_read > 0) {
                    VolumeRating(v.rating ?: 0, {
                        Log.d("test", "${v}")
                        seriesViewModel.onUserVolumeUpdate(
                            VolumeToUpdate(
                                id = v.user_volume_id!!,
                                volume_id = v.id,
                                times_read = v.times_read,
                                owned = v.owned,
                                rating = it
                            )
                        )
                    })
                }
            }

            HorizontalDivider()

            AnimatedContent(targetState = volumeIndex, label = "") { targetIndex ->
                val currentVolume = volumeList.getOrNull(targetIndex)
                VolumeSynopsis(currentVolume?.synopsis)
            }
        }
    }
}
