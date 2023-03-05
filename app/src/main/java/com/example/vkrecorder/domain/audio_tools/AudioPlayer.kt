package com.example.vkrecorder.domain.audio_tools

import android.media.MediaPlayer
import java.io.File

interface AudioPlayer {
    fun initFile(file: File)
    fun playFile(onComplete: () -> Unit)
    fun pause()
    fun stop()
    fun getPlayerStatus(): MediaPlayer?
}