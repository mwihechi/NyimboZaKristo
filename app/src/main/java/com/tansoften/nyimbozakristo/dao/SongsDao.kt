package com.tansoften.nyimbozakristo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.storage.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface SongsDao {
    fun getAllSongs(query: String, sortOrder: SortOrder): Flow<List<Songs>> =
        when (sortOrder) {
            SortOrder.BY_NUMBER -> getAllSongsSortByNumber(query)
            SortOrder.BY_NAME -> getAllSongsSortByName(query)
        }

    @Query("SELECT * FROM songs WHERE title || songs_id || verse_text LIKE '%' || :query || '%' ORDER BY songs_id ASC, title")
    fun getAllSongsSortByNumber(query: String): Flow<List<Songs>>

    @Query("SELECT * FROM songs WHERE title || songs_id || verse_text LIKE '%' || :query || '%' ORDER BY title ASC, title")
    fun getAllSongsSortByName(query: String): Flow<List<Songs>>

    @Query("SELECT * FROM songs WHERE `like` = 1")
    fun getLikedSongs(): LiveData<List<Songs>>

    @Update
    suspend fun update(songs: Songs)

    //verses
    fun getAllVerse(sortOrder: SortOrder): Flow<List<Songs>> =
        when (sortOrder) {
            SortOrder.BY_NUMBER -> getAllVersesSortByNumber()
            SortOrder.BY_NAME -> getAllVerseSortByName()
        }

    @Query("SELECT * FROM songs  ORDER BY songs_id ASC")
    fun getAllVersesSortByNumber(): Flow<List<Songs>>

    @Query("SELECT * FROM songs ORDER BY title ASC")
    fun getAllVerseSortByName(): Flow<List<Songs>>
}