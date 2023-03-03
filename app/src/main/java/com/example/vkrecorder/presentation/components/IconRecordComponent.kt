package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconRecordComponent() {
    Icon(
        //modifier = Modifier.background(color = Color.Green),
        imageVector = Icons.Filled.MicNone,
        contentDescription = "record",
        tint = MaterialTheme.colors.background
    )
}

@Composable
fun IconStopComponent() {
    Icon(
        //modifier = Modifier.defaultMinSize (20.dp),
        imageVector = Icons.Filled.Stop,
        contentDescription = "stop",
        tint = MaterialTheme.colors.background
    )
}