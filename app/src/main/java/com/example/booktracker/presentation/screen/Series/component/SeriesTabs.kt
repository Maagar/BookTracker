package com.example.booktracker.presentation.screen.Series.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesTabs(state: Int, titles: List<String>, onTabClick: (Int) -> Unit) {
    SecondaryTabRow(
        selectedTabIndex = state,
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        titles.forEachIndexed { index, title ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onTabClick(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = title, maxLines = 1)
            }
        }
    }
}