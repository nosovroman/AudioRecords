package com.example.vkrecorder.domain.repository

import com.example.vkrecorder.data.remote.dto.RespFileDto
import com.example.vkrecorder.data.remote.dto.RespDocUrlDto
import com.example.vkrecorder.data.remote.dto.RespDocSaveDto
import okhttp3.MultipartBody
import retrofit2.Response

interface VkRepository {
    suspend fun getDocsUploadServer(token: String): Response<RespDocUrlDto>
    suspend fun saveDoc(token: String, file: String): Response<RespDocSaveDto>
    suspend fun uploadFile(uploadUrl: String, token: String, file:  MultipartBody.Part): Response<RespFileDto>
}