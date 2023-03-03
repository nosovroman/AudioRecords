package com.example.vkrecorder.presentation.record_play

import android.app.Application
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkrecorder.domain.model.CounterState
import com.example.vkrecorder.domain.model.FileState
import com.example.vkrecorder.domain.use_case.get_records.GetRecordsUseCase
import com.example.vkrecorder.domain.use_case.work_counter.WorkCounter
import com.example.vkrecorder.presentation.record_play.playback.AudioPlayer
import com.example.vkrecorder.presentation.record_play.record.AudioRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecordPlayViewModel @Inject constructor(
    private val player: AudioPlayer,
    private val recorder: AudioRecorder,
    private val dir: File,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val workCounter: WorkCounter,
    private val context: Application
) : ViewModel() {

    var listRecordState: List<File> by mutableStateOf(emptyList())
        private set

    init {
        getRecords()
    }

    var fileState by mutableStateOf(FileState(stopped = true))
        private set
    fun onPlayStopClick(file: File) {
        if (isRecordedState) return

        if (fileState.playing) {
            if (fileState.file == file) {
                pausePlaying(file)
                pauseCounter()
            } else {
                stopPlaying()
                stopCounter()

                initFile(file)
                startPlaying(file)
                startCounter(file)
            }
        }
        else if (fileState.stopped) {
            initFile(file)
            startPlaying(file)
            startCounter(file)
        }
        else if (fileState.paused) {
            if (fileState.file == file) {     // снова включаем то же
                startPlaying(file)
                continueCounter(file, counterState.progress)
            } else {    // включаем новое
                stopPlaying()
                stopCounter()

                initFile(file)
                startPlaying(file)
                startCounter(file)
            }
        }
    }

    private fun initFile(file: File) {
        Log.d("qwerteq", "initFile")
        player.initFile(file)
    }

    private fun startPlaying(file: File) {
        fileState = FileState(file = file, playing = true)
        player.playFile {
            fileState = FileState(stopped = true)
            stopCounter()
        }
    }

    private fun pausePlaying(file: File) {
        fileState = FileState(file = file, paused = true)
        player.pause()
    }

    private fun stopPlaying() {
        fileState = FileState(stopped = true)
        player.stop()
    }

    var isRecordedState by mutableStateOf(false)
        private set
    fun onRecordClick() {
        if (fileState.file != null) return
        if (!isRecordedState) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    private fun startRecording() {
        File(context.cacheDir, createFileName()).also {
            recorder.start(it)
            isRecordedState = true
        }
    }

    fun stopRecording() {
        recorder.stop()
        isRecordedState = false
        getRecords()
    }

    private fun createFileName() = "record_${Calendar.getInstance().timeInMillis}.mp3"

    private fun getRecords() {
        dir.exists().let {
            listRecordState = getRecordsUseCase.getRecordList(dir)
        }
    }

    fun getDurationStr(file: File): String {
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(file.absolutePath)

        val dur = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()
        val seconds = ((dur % 60000)/1000).toString()
        val minutes = (dur / 60000).toString()
        return "$minutes:$seconds"
    }

    fun getDurationSec(file: File): Int {
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(file.absolutePath)
        val dur = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()

        return (dur/1000).toInt()
    }


    var counterState by mutableStateOf(CounterState(stopped = true))
        private set
    fun resetCounterState() {
        counterState = CounterState()
    }
    var jobState: Job? by mutableStateOf(null)
        private set

    fun startCounter(file: File) {
        val limit = getDurationSec(file)
        jobState = workCounter.getCount(limit).onEach { result ->
            counterState = CounterState(duration = limit, progress = result)
        }.launchIn(viewModelScope)
    }
    fun continueCounter(file: File, startTime: Int) {
        val limit = getDurationSec(file)
        jobState = workCounter.getCount(limit, startTime).onEach { result ->
            counterState = CounterState(duration = limit, progress = result)
        }.launchIn(viewModelScope)
    }

    fun pauseCounter() {
        jobState?.cancel()
    }
    fun stopCounter() {
        resetCounterState()
        jobState?.cancel()
    }
}