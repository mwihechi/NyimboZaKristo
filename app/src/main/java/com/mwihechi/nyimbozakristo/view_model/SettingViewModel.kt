package com.mwihechi.nyimbozakristo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mwihechi.nyimbozakristo.storage.FontStyle
import com.mwihechi.nyimbozakristo.storage.PreferencesManager
import com.mwihechi.nyimbozakristo.storage.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val preferencesManager: PreferencesManager) :
    ViewModel() {

    val fontSize = preferencesManager.fontSizeFlow.asLiveData()
    fun updateFontSize(fontSize: Float) = viewModelScope.launch {
        preferencesManager.updateFontSize(fontSize = fontSize)
    }


    val fontStylePreferences = preferencesManager.fontPreferencesFlow.asLiveData()
    fun updateFontStyle(fontStyle: FontStyle) = viewModelScope.launch {
        preferencesManager.updateFontStyle(fontStyle)
    }

    val isScreenOn = preferencesManager.isScreenOnFlow.asLiveData()
    fun updateIsScreenOn(isScreenOn: Boolean) = viewModelScope.launch {
        preferencesManager.updateScreenIsOn(isScreenOn = isScreenOn)
    }

    val sortOrder = preferencesManager.preferencesFlow.asLiveData()
    fun updateSortOrder(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder = sortOrder)
    }
}