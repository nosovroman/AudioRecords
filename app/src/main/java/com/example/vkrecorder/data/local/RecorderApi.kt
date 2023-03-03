package com.example.vkrecorder.data.local

import java.io.File

interface RecorderApi {
    suspend fun getRecordList(file: File): List<File> {
        return file.listFiles()?.toList() ?: emptyList()
    }
}