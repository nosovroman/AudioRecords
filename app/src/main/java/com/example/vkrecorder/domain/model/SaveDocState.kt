package com.example.vkrecorder.domain.model

import java.io.File

data class SaveDocState(
    var file: File? = null,
    var loading: Boolean = false,
    var success: Boolean = false,
    var failed: Boolean = false,
)
