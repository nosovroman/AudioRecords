package com.example.vkrecorder.domain.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toHintTimeRecordString(): String {
    val currentTime = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(Calendar.getInstance().time)
    val recordDateTime = SimpleDateFormat("dd.MM.yyyy в HH:mm", Locale.ENGLISH).format(Date(this))

    return if (currentTime == recordDateTime.substring(0, 10))
        recordDateTime.replaceBefore(' ', "Сегодня")
    else recordDateTime
}