package com.example.vkrecorder.di

import com.example.vkrecorder.presentation.record_play.playback.AndroidAudioPlayer
import com.example.vkrecorder.presentation.record_play.playback.AudioPlayer
import com.example.vkrecorder.presentation.record_play.record.AndroidAudioRecorder
import com.example.vkrecorder.presentation.record_play.record.AudioRecorder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AudioModule {
//    @Binds
//    @Singleton
//    abstract fun bindAudioRecorder(
//        androidAudioRecorder: AndroidAudioRecorder
//    ): AudioRecorder

    @Binds
    @Singleton
    abstract fun bindAudioPlayer(
        androidAudioPlayer: AndroidAudioPlayer
    ): AudioPlayer

    @Binds
    @Singleton
    abstract fun bindAudioRecorder(
        androidAudioRecorder: AndroidAudioRecorder
    ): AudioRecorder
}