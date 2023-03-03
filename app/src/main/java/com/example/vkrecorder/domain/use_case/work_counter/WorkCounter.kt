package com.example.vkrecorder.domain.use_case.work_counter

import android.app.Application
import com.example.vkrecorder.common.Constants.K_FREQ
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkCounter @Inject constructor() {
    fun getCount(limit: Int, startTime: Int = 1): Flow<Int> = flow {
        for (i in startTime..limit*K_FREQ) {
            delay(1000/K_FREQ.toLong())
            emit(i)
        }
    }
}