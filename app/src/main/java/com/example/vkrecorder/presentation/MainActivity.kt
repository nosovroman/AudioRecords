package com.example.vkrecorder.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.core.app.ActivityCompat
import com.example.vkrecorder.presentation.record_play.RecordPlayScreen
import com.example.vkrecorder.presentation.ui.theme.VkRecorderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            VkRecorderTheme {
                RecordPlayScreen()
            }
        }
    }

//    private fun startRecording() {
//        File(cacheDir, createFileName()).also {
//            recorder.start(it)
//            audioFile = it
//        }
//    }
//
//    private fun createFileName() = "record_${Calendar.getInstance().timeInMillis}.mp3"

//    private fun stopRecording() {
//        recorder.stop()
//    }

//    private fun getFileForPlaying(): File {
//        return audioFile ?: File(cacheDir, File(applicationContext.cacheDir.absolutePath).listFiles()!![0].name)
//    }
//    private fun playRecord(file: File) {
//        //player.playFile(audioFile ?: File(cacheDir, "audio.mp3")) // null!!
//        player.playFile(file) // null!!
//    }

//    private fun stopPlaying() {
//        player.stop()
//    }
}
