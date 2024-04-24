package com.tansoften.nyimbozakristo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tansoften.nyimbozakristo.dao.SongsDao
import com.tansoften.nyimbozakristo.item.Songs


@Database(entities = [Songs::class], version = 2, exportSchema = false)
abstract class NyimboDb : RoomDatabase() {
    abstract fun songsDao(): SongsDao
}