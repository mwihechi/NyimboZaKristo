package com.mwihechi.nyimbozakristo.di

import android.app.Application
import androidx.room.Room
import com.mwihechi.nyimbozakristo.storage.NyimboDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): NyimboDb =
        Room.databaseBuilder(app, NyimboDb::class.java, "songsDb")
            .createFromAsset("songs_db.db")
            .fallbackToDestructiveMigration()
            .build()
}