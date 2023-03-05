package com.example.vkrecorder.presentation.audio

import android.app.Application
import android.media.MediaMetadataRetriever
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkrecorder.common.Constants.AUDIO_FORMAT
import com.example.vkrecorder.common.Constants.AUDIO_FORMAT_REPLACED
import com.example.vkrecorder.common.NetState
import com.example.vkrecorder.domain.audio_tools.AudioPlayer
import com.example.vkrecorder.domain.audio_tools.AudioRecorder
import com.example.vkrecorder.domain.model.AuthState
import com.example.vkrecorder.domain.model.CounterState
import com.example.vkrecorder.domain.model.FileState
import com.example.vkrecorder.domain.model.SaveDocState
import com.example.vkrecorder.domain.use_case.get_doc_upload_server.GetUploadSrvUseCase
import com.example.vkrecorder.domain.use_case.get_records.GetRecordsUseCase
import com.example.vkrecorder.domain.use_case.save_doc.SaveDocUseCase
import com.example.vkrecorder.domain.use_case.upload_file.UploadFileUseCase
import com.example.vkrecorder.domain.use_case.work_counter.WorkCounterUseCase
import com.vk.api.sdk.auth.VKScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*
import javax.inject.Inject


@HiltViewModel
class RecorderViewModel @Inject constructor(
    private val player: AudioPlayer,
    private val recorder: AudioRecorder,
    private val dir: File,
    private val context: Application,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val workCounter: WorkCounterUseCase,
    private val getDocUploadSrvUseCase: GetUploadSrvUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val saveDocUseCase: SaveDocUseCase
) : ViewModel() {

    var listRecordState: List<File> by mutableStateOf(emptyList())
        private set

    var fileState by mutableStateOf(FileState(stopped = true))
        private set
    fun onAudioBtnClick(file: File) {
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
                runCounter(file)
            }
        }
        else if (fileState.stopped) {
            initFile(file)
            startPlaying(file)
            runCounter(file)
        }
        else if (fileState.paused) {
            if (fileState.file == file) {
                startPlaying(file)
                runCounter(file, counterState.progress)
            } else {
                stopPlaying()
                stopCounter()

                initFile(file)
                startPlaying(file)
                runCounter(file)
            }
        }
    }

    private fun initFile(file: File) {
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

    fun stopPlaying() {
        fileState = FileState(stopped = true)
        player.stop()
    }


    var isRecordedState by mutableStateOf(false)
        private set



    fun onRecordBtnClick() {
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

    private fun createFileName() = "record_${Calendar.getInstance().timeInMillis}.$AUDIO_FORMAT"

    fun getRecords() {
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

    private fun getDurationSec(file: File): Int {
        val metaRetriever = MediaMetadataRetriever()
        metaRetriever.setDataSource(file.absolutePath)
        val dur = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!.toLong()

        return (dur/1000).toInt()
    }


    var counterState by mutableStateOf(CounterState(stopped = true))
        private set
    private fun resetCounterState() {
        counterState = CounterState()
    }
    private var jobState: Job? by mutableStateOf(null)

    private fun runCounter(file: File, startTime: Int = 1) {
        val limit = getDurationSec(file)
        jobState = workCounter.getCount(limit, startTime).onEach { result ->
            counterState = CounterState(duration = limit, progress = result)
        }.launchIn(viewModelScope)
    }

    private fun pauseCounter() {
        jobState?.cancel()
    }
    fun stopCounter() {
        resetCounterState()
        jobState?.cancel()
    }

    var authStatusState by mutableStateOf(AuthState(failed = true))
        private set
    fun setAuthStatus(newState: AuthState) {
        authStatusState = newState
    }
    fun authVk(authLauncher: ActivityResultLauncher<Collection<VKScope>>) {
        authStatusState = AuthState(loading = true)
        authLauncher.launch(arrayListOf(VKScope.DOCS))
    }

    var saveDocState by mutableStateOf(SaveDocState())
        private set

    fun onLoadAudioClock(file: File) {
        if (!saveDocState.loading) {
            getDocUploadServer(file)
        }
    }

    private fun getDocUploadServer(file: File) {
        getDocUploadSrvUseCase.getDocsUploadServer(
            token = authStatusState.token!!
        ).onEach { result ->
            when (result) {
                is NetState.Loading -> saveDocState = SaveDocState(loading = true, file = file)
                is NetState.Success -> uploadFile(uploadUrl = result.data!!.uploadUrl, file = file)
                is NetState.Error -> saveDocState = SaveDocState(failed = true, file = file)
            }
        }.launchIn(viewModelScope)
    }

    private fun uploadFile(uploadUrl: String, file: File) {
        uploadFileUseCase.uploadFile(
            uploadUrl = uploadUrl,
            token = authStatusState.token!!,
            file = getMultipartReplacedMp3(file)
        ).onEach { result ->
            when (result) {
                is NetState.Success -> saveDoc(uploadUrl = result.data!!.file, file = file)
                is NetState.Error -> saveDocState = SaveDocState(failed = true, file = file)
            }
        }.launchIn(viewModelScope)
    }
    private fun getMultipartReplacedMp3(file: File) = MultipartBody.Part.createFormData(
        name = "file",
        filename = file.name.replace(AUDIO_FORMAT, AUDIO_FORMAT_REPLACED),
        body = file.asRequestBody()
    )

    private fun saveDoc(uploadUrl: String, file: File) {
        saveDocUseCase.saveDoc(
            token = authStatusState.token!!,
            file = uploadUrl
        ).onEach { result ->
            when (result) {
                is NetState.Success -> saveDocState = saveDocState.copy(success = true, loading = false)
                is NetState.Error -> saveDocState = SaveDocState(failed = true, file = file)
            }
        }.launchIn(viewModelScope)
    }
}