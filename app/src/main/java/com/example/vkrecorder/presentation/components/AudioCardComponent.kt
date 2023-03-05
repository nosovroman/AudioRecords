package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vkrecorder.common.Constants
import com.example.vkrecorder.common.Constants.SPACER_SIZE
import com.example.vkrecorder.domain.extensions.toHintTimeRecordString
import com.example.vkrecorder.presentation.audio.RecorderViewModel
import java.io.File

@Composable
fun CardItem(
    file: File,
    isPlaying: Boolean,
    onPlayClick: (File) -> Unit,
    onLoadClick: (File) -> Unit,
    shape: CornerBasedShape = RoundedCornerShape(Constants.CORNER_SIZE),
    viewModel: RecorderViewModel = hiltViewModel(),
) {
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
                            TextHint(text = file.lastModified().toHintTimeRecordString())
                        }
                    )
                    Spacer(modifier = Modifier.size(SPACER_SIZE))
                    if (isPlaying) {
                        TextHint(text = "${viewModel.counterState.progressStr} / $durationStr")
                    } else {
                        TextHint(text = durationStr)
                    }
                    Spacer(modifier = Modifier.size(SPACER_SIZE))
                    IconButton(
                        onClick = { onPlayClick(file) },
                        content = {
                            with(viewModel.fileState) {
                                if (this.file == file && playing) IconPauseComponent() else IconPlayComponent()
                            }
                        }
                    )
                    if (viewModel.authStatusState.success) {
                        Spacer(modifier = Modifier.size(SPACER_SIZE))
                        IconButton(
                            onClick = { onLoadClick(file) },
                            content = {
                                if (viewModel.saveDocState.file == file) {
                                    with(viewModel.saveDocState) {
                                        when {
                                            loading -> CircularProgressIndicator()
                                            success -> IconSuccessComponent()
                                            failed -> IconReloadComponent()
                                        }
                                    }
                                }
                                else {
                                    Icon(
                                        imageVector = Icons.Default.UploadFile,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                }
                            }
                        )
                    }
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