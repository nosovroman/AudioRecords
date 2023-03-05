package com.example.vkrecorder.data.remote.dto

import com.example.vkrecorder.domain.model.UploadUrl
import com.squareup.moshi.Json

data class UrlDto(
    @field:Json(name = "upload_url")
    val uploadUrl: String
)

fun UrlDto.toUploadUrl(): UploadUrl {
    return UploadUrl(uploadUrl = uploadUrl)
}