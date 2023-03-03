package com.example.vkrecorder.domain.model

import java.io.File

data class FileState(
    var file: File? = null,
    var playing: Boolean = false,
    var paused: Boolean = false,
    var stopped: Boolean = false,
)
