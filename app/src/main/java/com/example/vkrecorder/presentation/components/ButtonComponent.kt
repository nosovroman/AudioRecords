package com.example.vkrecorder.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkrecorder.common.Constants.CORNER_SIZE_ACTIVE

@Preview
@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
    shape: CornerBasedShape = RoundedCornerShape(CORNER_SIZE_ACTIVE),
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        content = { text() },
        shape = shape,
        enabled = enabled
    )
}