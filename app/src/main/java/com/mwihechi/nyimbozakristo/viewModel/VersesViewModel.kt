package com.mwihechi.nyimbozakristo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mwihechi.nyimbozakristo.item.Songs
import com.mwihechi.nyimbozakristo.storage.NyimboDb
import com.mwihechi.nyimbozakristo.storage.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class VersesViewModel @Inject constructor(
    private val db: NyimboDb,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val sortOrderPreferences = preferencesManager.preferencesFlow
    private val versesFlow = combine(
        sortOrderPreferences
    ) { filterPreferences ->
        filterPreferences
    }
        .flatMapLatest { (filterPreferences) ->
            db.songsDao().getAllVerse(filterPreferences.sortOrder)
        }

    val verses = versesFlow.asLiveData()
    val lovedVerses = db.songsDao().getLikedSongs()
    val fontSize = preferencesManager.fontSizeFlow.asLiveData()
    val sortOrder = sortOrderPreferences.asLiveData()
    val keepScreenOn = preferencesManager.keepScreenOnFlow.asLiveData()

    fun onLikeChecked(song: Songs) = viewModelScope.launch {
        when (song.like) {
            true -> db.songsDao().update(song.copy(like = false))
            false -> db.songsDao().update(song.copy(like = true))
        }
    }

    //update font size
    fun updateFontSize(fontSize: Float) = viewModelScope.launch {
        preferencesManager.updateFontSize(fontSize)
    }
}