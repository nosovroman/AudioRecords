package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecordFABComponent(isRecorded: Boolean, onClick: () -> Unit) {
    FloatingActionButton(
//        modifier = Modifier
//            .background(MaterialTheme.colors.primaryVariant),
//            //.padding(horizontal = 15.dp),
//            //.fillMaxWidth(),
        onClick = onClick,
        content = {
            if (isRecorded) IconStopComponent() else IconRecordComponent()
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}