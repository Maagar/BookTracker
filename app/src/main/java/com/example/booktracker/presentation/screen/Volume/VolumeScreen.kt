package com.example.booktracker.presentation.screen.Volume

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.presentation.screen.Volume.component.VolumeChangeRow
import com.example.booktracker.ui.viewmodel.SeriesViewModel
import com.example.ui.theme.AppTypography

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VolumeScreen(
    seriesViewModel: SeriesViewModel
) {
    val volume by seriesViewModel.volume.collectAsState()
    val volumeList by seriesViewModel.volumes.collectAsState()

    var volumeIndex by remember { mutableIntStateOf(volumeList.indexOf(volume)) }

    val isLast = volumeList.lastIndex == volumeIndex
    val isFirst = volumeIndex == 0

    var animationDirection by remember { mutableIntStateOf(0) }

    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {
            AnimatedContent(
                targetState = volumeIndex,
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { animationDirection * 2000 },
                        animationSpec = tween(300)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { animationDirection * -2000 },
                        animationSpec = tween(300)
                    )
                }, label = ""
            ) { targetIndex ->
                val currentVolume = volumeList.getOrNull(targetIndex)
                currentVolume?.let { volume ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = volume.cover_url,
                            contentDescription = null,
                            modifier = Modifier
                                .weight(0.4f)
                                .size(width = 150.dp, height = 200.dp),
                            fallback = painterResource(R.drawable.no_image_placeholder)
                        )
                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(start = 8.dp)
                        ) {
                            Text(text = volume.title, style = AppTypography.titleLarge)
                            Row {
                                Icon(
                                    painterResource(R.drawable.calendar_month),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(text = "${volume.release_date ?: stringResource(R.string.release_date_not_yet_announced)}")
                            }
                            Row {
                                Icon(
                                    painterResource(R.drawable.book),
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(text = "${volume.read_date ?: stringResource(R.string.not_read_yet)}")
                            }
                        }
                    }
                }
            }

            if (volumeList.size > 1)
                AnimatedContent(
                    targetState = volumeIndex,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
                    }, label = ""
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
            AnimatedContent(targetState = volumeIndex, label = "") { targetIndex ->
                val currentVolume = volumeList.getOrNull(targetIndex)
                Column {
                    Card(modifier = Modifier.padding(8.dp)) {
                        Text(
                            if (currentVolume?.synopsis.isNullOrEmpty()) {
                                stringResource(R.string.no_synopsis_available)
                            } else {
                                currentVolume?.synopsis
                                    ?: stringResource(R.string.no_synopsis_available)
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            style = AppTypography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}
