package com.example.vkrecorder.presentation.record_play.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vkrecorder.common.Constants
import com.example.vkrecorder.extensions.toHintTimeRecordString
import com.example.vkrecorder.presentation.components.IconPauseComponent
import com.example.vkrecorder.presentation.components.IconPlayComponent
import com.example.vkrecorder.presentation.record_play.RecordPlayViewModel
import java.io.File

@Composable
fun CardItem(
    file: File,
    isPlaying: Boolean,
    //duration: String,
    onItemClick: (File) -> Unit,
    shape: CornerBasedShape = RoundedCornerShape(Constants.CORNER_SIZE_ACTIVE),
    //icon: @Composable () -> Unit = {},
    viewModel: RecordPlayViewModel = hiltViewModel(),
) {
    val hintTime = file.lastModified().toHintTimeRecordString()
    val durationStr = viewModel.getDurationStr(file)

    Column(
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = shape
                    )
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        modifier = Modifier.weight(1f),
                        content = {
                            Text(text = "record_${file.name.substring(16)}", fontWeight = FontWeight.Bold)
                            TextHint(text = hintTime)
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    if (isPlaying) {
                        TextHint(text = "${viewModel.counterState.progressStr} / $durationStr")
                    } else {
                        TextHint(text = durationStr)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    IconButton(
                        onClick = { onItemClick(file) },
                        content = {
                            with(viewModel.fileState) {
                                if (this.file == file && playing) IconPauseComponent() else IconPlayComponent()
                            }
                        }
                    )
                }
            )
            if (viewModel.fileState.file == file) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.primary,
                    progress = viewModel.counterState.progressPercent
                )
            }
        }
    )

}

@Composable
fun TextHint(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onSurface,
        fontSize = 14.sp
    )
}