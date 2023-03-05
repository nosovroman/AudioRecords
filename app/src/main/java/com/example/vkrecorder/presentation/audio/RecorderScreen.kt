package com.example.vkrecorder.presentation.audio

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.vkrecorder.common.Constants.PADDING_SCREEN_HORIZONTAL
import com.example.vkrecorder.common.Constants.SPACE_ABOVE_FAB
import com.example.vkrecorder.common.Constants.SPACE_BETWEEN_ITEMS
import com.example.vkrecorder.presentation.components.CardItem
import com.example.vkrecorder.presentation.components.RecordFABComponent
import com.example.vkrecorder.presentation.components.ToolbarComponent
import com.vk.api.sdk.auth.VKScope

@Composable
fun RecordPlayScreen(
    viewModel: RecorderViewModel = hiltViewModel(),
    authLauncher: ActivityResultLauncher<Collection<VKScope>>
) {
    val listRecords = viewModel.listRecordState

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when(event) {
                    Lifecycle.Event.ON_CREATE -> viewModel.getRecords()
                    Lifecycle.Event.ON_PAUSE -> {
                        if (viewModel.isRecordedState) viewModel.stopRecording()
                        if (viewModel.fileState.file != null) viewModel.stopPlaying().also { viewModel.stopCounter() }
                    }
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }
    )

    Scaffold (
        topBar = {
            ToolbarComponent(onIconClick = { viewModel.authVk(authLauncher) })
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(horizontal = PADDING_SCREEN_HORIZONTAL),
                verticalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ITEMS),
                content = {
                    items(listRecords.sortedByDescending { it.lastModified() }) { recordFile ->
                        CardItem(
                            file = recordFile,
                            isPlaying = viewModel.fileState.file == recordFile,
                            onPlayClick = { viewModel.onAudioBtnClick(recordFile) },
                            onLoadClick = { viewModel.onLoadAudioClock(recordFile) }
                        )
                    }
                    item { Spacer(modifier = Modifier.size(SPACE_ABOVE_FAB)) }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            RecordFABComponent(
                isRecorded = viewModel.isRecordedState,
                onClick = {
                    viewModel.onRecordBtnClick()
                }
            )
        }
    )
}