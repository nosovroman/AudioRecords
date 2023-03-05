package com.example.vkrecorder.domain.use_case.save_doc

import com.example.vkrecorder.common.Constants
import com.example.vkrecorder.common.NetState
import com.example.vkrecorder.domain.repository.VkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SaveDocUseCase @Inject constructor(
    private val repository: VkRepository
) {
    fun saveDoc(token: String, file: String): Flow<NetState<Any>> = flow {
        try {
            emit(NetState.Loading())
            val response = repository.saveDoc(token, file)
            when {
                response.isSuccessful -> emit(NetState.Success(true))
                else -> emit(NetState.Error( "${response.code()}"))
            }
        } catch (e: HttpException) {
            emit(NetState.Error(e.localizedMessage ?: Constants.UE_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(NetState.Error(Constants.IOException_MESSAGE))
        }
    }
}