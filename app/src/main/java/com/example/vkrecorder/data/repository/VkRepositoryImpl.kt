package com.example.vkrecorder.data.repository

import com.example.vkrecorder.data.remote.ApiServiceVk
import com.example.vkrecorder.data.remote.dto.RespFileDto
import com.example.vkrecorder.data.remote.dto.RespDocUrlDto
import com.example.vkrecorder.data.remote.dto.RespDocSaveDto
import com.example.vkrecorder.domain.repository.VkRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class VkRepositoryImpl @Inject constructor(
    private val apiServiceVk: ApiServiceVk,
): VkRepository {
    override suspend fun getDocsUploadServer(token: String): Response<RespDocUrlDto> =
        apiServiceVk.docsGetUploadServer(token = token)

    override suspend fun uploadFile(uploadUrl: String, token: String, file:  MultipartBody.Part): Response<RespFileDto> =
        apiServiceVk.uploadFile(upload_url = uploadUrl,  file =  file)

    override suspend fun saveDoc(token: String, file: String): Response<RespDocSaveDto> =
        apiServiceVk.docsSave(token = token, file = file)
}