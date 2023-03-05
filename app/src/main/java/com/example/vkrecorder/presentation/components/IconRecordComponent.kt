package com.example.vkrecorder.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable

@Composable
fun IconRecordComponent() {
    Icon(
        imageVector = Icons.Filled.MicNone,
        contentDescription = "record",
        tint = MaterialTheme.colors.background
    )
}

@Composable
fun IconStopComponent() {
    Icon(
        imageVector = Icons.Filled.Stop,
        contentDescription = "stop",
        tint = MaterialTheme.colors.background
    )
}