package com.example.booktracker.presentation.screen.Discover.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.booktracker.presentation.component.icon.Close
import com.example.booktracker.presentation.component.icon.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        windowInsets = WindowInsets(top = 0.dp),
        leadingIcon = { Icon(imageVector = Search, contentDescription = null)},
        trailingIcon =  {
            if (query.isNotEmpty()) { IconButton(onClick = onClose) {
                Icon(imageVector = Close, contentDescription = null)
            }} else null
        }
    ) {

    }
}