package com.tansoften.nyimbozakristo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tansoften.nyimbozakristo.storage.FontStyle
import com.tansoften.nyimbozakristo.storage.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val preferencesManager: PreferencesManager) :
    ViewModel() {
    val fontStylePreferences = preferencesManager.fontPreferencesFlow.asLiveData()
    val fontSize = preferencesManager.fontSizeFlow.asLiveData()

    fun updateFontSize(fontSize: Float) = viewModelScope.launch {
        preferencesManager.updateFontSize(fontSize = fontSize)
    }

    fun updateFontStyle(fontStyle: FontStyle) = viewModelScope.launch {
        preferencesManager.updateFontStyle(fontStyle)
    }
}