package com.example.vkrecorder.domain.use_case.upload_file

import com.example.vkrecorder.common.Constants.IOException_MESSAGE
import com.example.vkrecorder.common.Constants.UE_ERROR_MESSAGE
import com.example.vkrecorder.common.NetState
import com.example.vkrecorder.data.remote.dto.toContainerFile
import com.example.vkrecorder.domain.model.ContainerFile
import com.example.vkrecorder.domain.repository.VkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val repository: VkRepository
) {
    fun uploadFile(uploadUrl: String, token: String, file:  MultipartBody.Part): Flow<NetState<ContainerFile>> = flow {
        try {
            emit(NetState.Loading())
            val response = repository.uploadFile(uploadUrl = uploadUrl, token = token, file = file)
            when {
                response.isSuccessful -> emit(NetState.Success(response.body()!!.toContainerFile()))
                else -> emit(NetState.Error( "$response"))
            }
        } catch (e: HttpException) {
            emit(NetState.Error(e.localizedMessage ?: UE_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(NetState.Error(IOException_MESSAGE))
        }
    }
}