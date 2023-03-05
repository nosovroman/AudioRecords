package com.example.vkrecorder.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable

@Composable
fun IconSuccessComponent() {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null,
        tint = MaterialTheme.colors.primary
    )
}

@Composable
fun IconReloadComponent() {
    Icon(
        imageVector = Icons.Default.Update,
        contentDescription = null,
        tint = MaterialTheme.colors.primaryVariant
    )
}