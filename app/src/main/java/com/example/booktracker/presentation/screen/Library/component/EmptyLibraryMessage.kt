package com.example.booktracker.presentation.screen.Library.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booktracker.R

@Composable
fun EmptyLibraryMessage() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {
        Text(
            stringResource(R.string.Empty_library_message),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge
        )
    }
}