package com.example.booktracker.presentation.screen.Discover.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.booktracker.R
import com.example.booktracker.data.model.Series
import com.example.booktracker.presentation.component.icon.Add_box
import com.example.booktracker.presentation.component.icon.Close
import com.example.ui.theme.AppTypography

@Composable
fun SeriesListItem(series: Series, onItemClick: (Series) -> Unit, onFollowSeries: () -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onItemClick(series) },
        headlineContent = { Text(text = series.title, style = AppTypography.titleMedium) },
        leadingContent = {
            AsyncImage(
                model = series.main_cover_url,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        trailingContent = {
            IconButton(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
            ) {
                if (series.isFollowing) Icon(
                    painterResource(R.drawable.check_box),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                else IconButton(onClick = onFollowSeries) {
                    Icon(
                        imageVector = Add_box,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

            }
        }
    )
}