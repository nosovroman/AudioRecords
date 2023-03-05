package com.example.vkrecorder.domain.model

data class AuthState(
    val token: String? = null,
    var loading: Boolean = false,
    var success: Boolean = false,
    var failed: Boolean = false,
)
