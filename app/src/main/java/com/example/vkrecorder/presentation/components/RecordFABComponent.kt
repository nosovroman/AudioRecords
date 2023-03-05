package com.example.vkrecorder.presentation.components

import android.Manifest
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordFABComponent(
    isRecorded: Boolean,
    onClick: () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.RECORD_AUDIO
        )
    )
    FloatingActionButton(
        onClick = {
            permissionsState.launchMultiplePermissionRequest()

            permissionsState.permissions.forEach { perm ->
                when(perm.permission) {
                    Manifest.permission.RECORD_AUDIO -> {
                        when {
                            perm.hasPermission -> {
                                onClick()
                            }
                        }
                    }
                }
            }
        },
        content = {
            if (isRecorded) IconStopComponent() else IconRecordComponent()
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}