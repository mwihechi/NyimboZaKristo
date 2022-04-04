package com.tansoften.nyimbozakristo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tansoften.nyimbozakristo.view_model.SettingViewModel

@Composable
fun SettingScreen(viewModel: SettingViewModel = hiltViewModel(), navController: NavController) {
    val setting = viewModel.fontStylePreferences.observeAsState().value
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Setting Screen", modifier = Modifier.clickable { navController.popBackStack() })
    }
}