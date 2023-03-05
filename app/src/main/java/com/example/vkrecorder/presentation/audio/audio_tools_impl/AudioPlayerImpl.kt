package com.example.vkrecorder.presentation.audio.audio_tools_impl

import android.app.Application
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.vkrecorder.domain.audio_tools.AudioPlayer
import java.io.File
import javax.inject.Inject

class AudioPlayerImpl @Inject constructor(
    private val context: Application
): AudioPlayer {

    private var player: MediaPlayer? = null
    override fun getPlayerStatus() = player

    override fun initFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
        }
    }
    override fun playFile(onComplete: () -> Unit) {
        player?.apply {
            start()
            setOnCompletionListener {
                onComplete()
                player?.release()
                player = null
            }
        }
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}