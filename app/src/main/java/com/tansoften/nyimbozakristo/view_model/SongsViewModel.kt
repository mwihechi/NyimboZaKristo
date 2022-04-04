package com.tansoften.nyimbozakristo.view_model


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tansoften.nyimbozakristo.item.Songs
import com.tansoften.nyimbozakristo.storage.NyimboDb
import com.tansoften.nyimbozakristo.storage.PreferencesManager
import com.tansoften.nyimbozakristo.storage.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SongsViewModel @Inject constructor(
    private val db: NyimboDb,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrderPreferences = preferencesManager.preferencesFlow

    private val songFlow = combine(
        searchQuery,
        sortOrderPreferences
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }
        .flatMapLatest { (_query, filterPreferences) ->
            db.songsDao().getAllSongs(_query, filterPreferences.sortOrder)
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

    //verses
    private val versesFlow = combine(
        sortOrderPreferences
    ) { filterPreferences ->
        filterPreferences
    }
        .flatMapLatest { (filterPreferences) ->
            db.songsDao().getAllVerse(filterPreferences.sortOrder)
        }

    val verses = versesFlow.asLiveData()
}