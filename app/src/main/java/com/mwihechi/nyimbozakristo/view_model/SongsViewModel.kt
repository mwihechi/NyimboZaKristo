package com.mwihechi.nyimbozakristo.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mwihechi.nyimbozakristo.item.Songs
import com.mwihechi.nyimbozakristo.storage.NyimboDb
import com.mwihechi.nyimbozakristo.storage.PreferencesManager
import com.mwihechi.nyimbozakristo.storage.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class SongsViewModel @Inject constructor(
    private val db: NyimboDb,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    private val sortOrderPreferences = preferencesManager.preferencesFlow

    private val songFlow = combine(
        searchQuery,
        sortOrderPreferences
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }
        .flatMapLatest { (query, filterPreferences) ->
            db.songsDao().getAllSongs(query, filterPreferences.sortOrder)
        }

    fun sortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }


    fun onLikeChecked(song: Songs) = viewModelScope.launch {
        when (song.like) {
            true -> db.songsDao().update(song.copy(like = false))
            false -> db.songsDao().update(song.copy(like = true))
        }
    }

    val songs = songFlow.asLiveData()

    val lovedSongs = db.songsDao().getLikedSongs()

    val songSorted = sortOrderPreferences.asLiveData()

}