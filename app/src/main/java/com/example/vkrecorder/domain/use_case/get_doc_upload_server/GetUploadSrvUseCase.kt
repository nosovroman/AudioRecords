package com.example.vkrecorder.domain.use_case.get_doc_upload_server

import com.example.vkrecorder.common.Constants
import com.example.vkrecorder.common.NetState
import com.example.vkrecorder.data.remote.dto.toUploadUrl
import com.example.vkrecorder.domain.model.UploadUrl
import com.example.vkrecorder.domain.repository.VkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUploadSrvUseCase @Inject constructor(
    private val repository: VkRepository
) {
    fun getDocsUploadServer(token: String): Flow<NetState<UploadUrl>> = flow {
        try {
            emit(NetState.Loading())
            val response = repository.getDocsUploadServer(token)
            when {
                response.isSuccessful -> emit(NetState.Success(response.body()!!.response.toUploadUrl()))
                else -> emit(NetState.Error( "${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(NetState.Error(e.localizedMessage ?: Constants.UE_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(NetState.Error(Constants.IOException_MESSAGE))
        }
    }
}