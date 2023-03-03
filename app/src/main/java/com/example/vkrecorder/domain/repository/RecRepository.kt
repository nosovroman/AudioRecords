package com.example.vkrecorder.domain.repository

import java.io.File

interface RecRepository {
    suspend fun getRecordList(): List<File>
}