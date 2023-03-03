package com.example.vkrecorder.presentation.record_play

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.vkrecorder.common.Constants.PADDING_SCREEN_HORIZONTAL
import com.example.vkrecorder.common.Constants.SPACE_BETWEEN_ITEMS
import com.example.vkrecorder.common.Constants.YOUR_RECORDS
import com.example.vkrecorder.presentation.components.IconPauseComponent
import com.example.vkrecorder.presentation.components.IconPlayComponent
import com.example.vkrecorder.presentation.components.RecordFABComponent
import com.example.vkrecorder.presentation.record_play.components.CardItem

@Composable
fun RecordPlayScreen(
    //navController: NavController,
    viewModel: RecordPlayViewModel = hiltViewModel(),
    context: Context
    //roomViewModel: RoomViewModel = hiltViewModel()
) {
    val listRecords = viewModel.listRecordState

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when(event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        if (viewModel.isRecordedState) viewModel.stopRecording()
                    }
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    Scaffold (
        content = {
            LazyColumn(
                modifier = Modifier.padding(horizontal = PADDING_SCREEN_HORIZONTAL),
                verticalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ITEMS),
                content = {
                    item { Text(text = YOUR_RECORDS, fontSize = 24.sp, modifier = Modifier.padding(vertical = 10.dp), fontWeight = FontWeight.Bold) }
                    items(listRecords.sortedByDescending { it.lastModified() }) { recordFile ->
                        CardItem(
                            file = recordFile,
                            isPlaying = viewModel.fileState.file == recordFile,
                            onItemClick = {
                                viewModel.onPlayStopClick(recordFile)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.size(60.dp)) }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            RecordFABComponent(
                isRecorded = viewModel.isRecordedState,
                onClick = {
                    viewModel.onRecordClick()
                }
            )
        }
    )
//{
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            content = {
//                Button(onClick = {
//                    startRecording()
//                }) {
//                    Text(text = "Start recording")
//                }
//                Button(onClick = {
//                    stopRecording()
//                }) {
//                    Text(text = "Stop recording")
//                }
//                Button(onClick = {
//                    getFileList(applicationContext)
//                    playRecord(getFileForPlaying())
//                }) {
//                    Text(text = "Play")
//                }
//                Button(onClick = {
//                    stopPlaying()
//                }) {
//                    Text(text = "Stop playing")
//                }
//            }
//        )
   // }
}