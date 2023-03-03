package com.example.vkrecorder.domain.model

import android.util.Log
import com.example.vkrecorder.common.Constants.K_FREQ
import java.io.File

data class CounterState(
    var file: File? = null,
    var duration: Int = 0,
    var progress: Int = 0,
    var inProgress: Boolean = false,
    var paused: Boolean = false,
    var stopped: Boolean = false,
) {
    val progressPercent: Float = if (duration > 0) {
        //Log.d("qdsdadsf", "$progress, ${duration*K_FREQ}, ${(progress.toFloat()*100/(duration*K_FREQ))/100}")
        ((progress.toFloat()*100/(duration*K_FREQ))/100)
    } else {0f}
    val progressStr = "${progress/(60*K_FREQ)}:${(progress/K_FREQ)%60}"
}
