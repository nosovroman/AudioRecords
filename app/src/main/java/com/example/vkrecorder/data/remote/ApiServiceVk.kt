package com.example.vkrecorder.data.remote

import com.example.vkrecorder.common.Constants.END_URL
import com.example.vkrecorder.data.remote.dto.RespFileDto
import com.example.vkrecorder.data.remote.dto.RespDocUrlDto
import com.example.vkrecorder.data.remote.dto.RespDocSaveDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceVk {
//    @Headers("X-Yandex-API-Key: be652545-e359-404a-8a62-704222ebe7b7")
    @GET("docs.getUploadServer?${END_URL}")
    suspend fun docsGetUploadServer(
        @Query("access_token") token: String,
        @Query("v") version: String = "5.131"
    ): Response<RespDocUrlDto>

    @Multipart
    @POST
    suspend fun uploadFile(
        @Url upload_url: String,
        @Part file: MultipartBody.Part
    ): Response<RespFileDto>

    @GET("docs.save?${END_URL}")
    suspend fun docsSave(
        @Query("access_token") token: String,
        @Query("file") file: String,
        @Query("v") version: String = "5.131",
    ): Response<RespDocSaveDto>
}