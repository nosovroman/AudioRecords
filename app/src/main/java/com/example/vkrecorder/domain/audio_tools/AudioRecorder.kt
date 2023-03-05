package com.example.vkrecorder.domain.audio_tools

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}