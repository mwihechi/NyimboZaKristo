package com.mwihechi.nyimbozakristo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mwihechi.nyimbozakristo.dao.SongsDao
import com.mwihechi.nyimbozakristo.item.Songs


@Database(entities = [Songs::class], version = 2, exportSchema = false)
abstract class NyimboDb : RoomDatabase() {
    abstract fun songsDao(): SongsDao
}