package com.example.vkrecorder.data.remote.dto

import com.example.vkrecorder.domain.model.ContainerFile

data class RespFileDto(
    val file: String
)

fun RespFileDto.toContainerFile(): ContainerFile {
    return ContainerFile(file = file)
}