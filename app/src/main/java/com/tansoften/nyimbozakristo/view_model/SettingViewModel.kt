package com.tansoften.nyimbozakristo.view_model

import androidx.lifecycle.ViewModel
import com.tansoften.nyimbozakristo.storage.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(preferencesManager: PreferencesManager) : ViewModel() {
    val sortOrderPreferences = preferencesManager.preferencesFlow
}