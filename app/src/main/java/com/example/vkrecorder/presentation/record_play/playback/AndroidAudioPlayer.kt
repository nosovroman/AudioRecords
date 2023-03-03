package com.example.vkrecorder.presentation.record_play.playback

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File
import javax.inject.Inject

class AndroidAudioPlayer @Inject constructor(
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
        //MediaPlayer.create(context, file.toUri()).apply {
        //    player = this
            player?.start()
        //}
        player?.setOnCompletionListener {
            onComplete()
            player?.release()
            player = null
        }
    }

//    override fun onComplete(onComplete: () -> Unit) {
//        player?.setOnCompletionListener {
//            onComplete()
//        }
//    }
    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}