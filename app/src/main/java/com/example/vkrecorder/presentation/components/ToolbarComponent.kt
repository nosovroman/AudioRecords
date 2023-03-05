package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Input
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vkrecorder.common.Constants
import com.example.vkrecorder.common.Constants.TOOLBAR_HEIGHT
import com.example.vkrecorder.presentation.audio.RecorderViewModel

@Composable
fun ToolbarComponent(
    onIconClick: () -> Unit,
    viewModel: RecorderViewModel = hiltViewModel(),
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(TOOLBAR_HEIGHT)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Text(
                text = Constants.YOUR_RECORDS,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            with (viewModel.authStatusState) {
                when {
                    loading -> {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
                    success -> {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Вы авторизованы",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                    failed -> {
                        IconButton(
                            onClick = { onIconClick() },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Input,
                                    contentDescription = "Авторизоваться",
                                    tint = MaterialTheme.colors.primary
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}