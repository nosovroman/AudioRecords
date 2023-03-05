package com.example.vkrecorder.presentation.audio.audio_tools_impl

import android.app.Application
import android.media.MediaRecorder
import android.os.Build
import com.example.vkrecorder.domain.audio_tools.AudioRecorder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AudioRecorderImpl @Inject constructor(
    private val context: Application
): AudioRecorder {

    private var recorder: MediaRecorder? = null

    private fun createRecorder(): MediaRecorder {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(outputFile: File) {
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop() {
        recorder?.apply {
            stop()
            reset()
        }
        recorder = null
    }
}