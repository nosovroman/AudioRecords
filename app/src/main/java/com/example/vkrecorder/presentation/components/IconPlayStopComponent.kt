package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IconPlayComponent() {
    Icon(
        modifier = Modifier.size(45.dp),
        imageVector = Icons.Filled.PlayCircle,
        contentDescription = "play",
        tint = MaterialTheme.colors.primary
    )
}

@Composable
fun IconPauseComponent() {
    Icon(
        modifier = Modifier.size (45.dp),
        imageVector = Icons.Filled.PauseCircle,
        contentDescription = "pause",
        tint = MaterialTheme.colors.primaryVariant
    )
}