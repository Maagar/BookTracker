package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    sortByDate: Boolean,
    showFinished: Boolean,
    onSortByChange: () -> Unit,
    onShowFinishedChange: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Text(
            stringResource(R.string.manage_series_display),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp)
        )

        ListItem(
            headlineContent = {
                Text(
                    if (sortByDate) stringResource(R.string.sort_alphabetically) else stringResource(
                        R.string.sort_by_follow_date
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.clickable { onSortByChange() }
        )

        HorizontalDivider()

        ListItem(headlineContent = {
            Text(
                if (showFinished) stringResource(R.string.hide_finished) else stringResource(R.string.show_finished),
                style = MaterialTheme.typography.bodyMedium
            )
        },
            modifier = Modifier.clickable { onShowFinishedChange() }
        )
    }
}