package com.example.vkrecorder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.vkrecorder.domain.model.AuthState
import com.example.vkrecorder.presentation.audio.RecordPlayScreen
import com.example.vkrecorder.presentation.audio.RecorderViewModel
import com.example.vkrecorder.presentation.ui.theme.VkRecorderTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<RecorderViewModel>()
    private val authLauncher = VK.login(this) { result : VKAuthenticationResult ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                viewModel.setAuthStatus(AuthState(success = true, token = result.token.accessToken))
            }
            is VKAuthenticationResult.Failed -> {
                viewModel.setAuthStatus(AuthState(failed = true))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkRecorderTheme(darkTheme = false) {
                RecordPlayScreen(authLauncher = authLauncher)
            }
        }
    }
}
