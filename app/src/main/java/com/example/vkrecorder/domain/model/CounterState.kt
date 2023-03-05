package com.example.vkrecorder.domain.model

import com.example.vkrecorder.common.Constants.K_FREQ

data class CounterState(
    var duration: Int = 0,
    var progress: Int = 0,
    var inProgress: Boolean = false,
    var paused: Boolean = false,
    var stopped: Boolean = false,
) {
    val progressPercent: Float = if (duration > 0) ((progress.toFloat()*100/(duration*K_FREQ))/100) else 0f
    val progressStr = "${progress/(60*K_FREQ)}:${(progress/K_FREQ)%60}"
}
