package com.tansoften.nyimbozakristo.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Songs(
    @PrimaryKey
    val songs_id: Int,
    val title: String,
    val verse_text: String,
    val like: Boolean = false
)

