package com.example.vkrecorder.presentation.record_play.record

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}