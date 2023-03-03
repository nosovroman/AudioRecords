package com.example.vkrecorder.di

import android.app.Application
import android.content.Context
import com.example.vkrecorder.presentation.record_play.playback.AndroidAudioPlayer
import com.example.vkrecorder.presentation.record_play.playback.AudioPlayer
import com.example.vkrecorder.presentation.record_play.record.AndroidAudioRecorder
import com.example.vkrecorder.presentation.record_play.record.AudioRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecorderDir(context: Application): File {
        return File(context.cacheDir.absolutePath)
    }

//    @Provides
//    @Singleton
//    fun provideAudioRecorder(@ApplicationContext context: Application): AudioRecorder {
//        return AndroidAudioRecorder(context)
//    }

//    @Provides
//    @Singleton
//    fun provideAudioPlayer(context: Application): AudioPlayer {
//        return AndroidAudioPlayer(context)
//    }

//    @Provides
//    @Singleton
//    fun provideDB(@ApplicationContext context: Context): CalendarDatabase {
//        return CalendarDatabase.getDBInstance(context)
//    }
//
//    @Provides
//    @Singleton
//    fun provideTaskDao(calendarDB: CalendarDatabase): TaskDao {
//        return calendarDB.taskDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
//        return TaskRepositoryImpl(taskDao)
//    }
}