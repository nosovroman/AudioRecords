package com.example.vkrecorder.di

import com.example.vkrecorder.presentation.audio.audio_tools_impl.AudioPlayerImpl
import com.example.vkrecorder.domain.audio_tools.AudioPlayer
import com.example.vkrecorder.presentation.audio.audio_tools_impl.AudioRecorderImpl
import com.example.vkrecorder.domain.audio_tools.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AudioModule {

    @Binds
    @Singleton
    abstract fun bindAudioPlayer(
        androidAudioPlayer: AudioPlayerImpl
    ): AudioPlayer

    @Binds
    @Singleton
    abstract fun bindAudioRecorder(
        androidAudioRecorder: AudioRecorderImpl
    ): AudioRecorder
}