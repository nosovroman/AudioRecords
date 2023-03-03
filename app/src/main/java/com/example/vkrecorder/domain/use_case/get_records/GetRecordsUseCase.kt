package com.example.vkrecorder.domain.use_case.get_records

import android.app.Application
import java.io.File
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor() {
    fun getRecordList(dir: File): List<File> {
        return dir.listFiles()?.toList() ?: emptyList()
    }
}