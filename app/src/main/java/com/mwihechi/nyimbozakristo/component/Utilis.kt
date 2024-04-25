package com.mwihechi.nyimbozakristo.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun KeepScreenOn() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

@Composable
fun SetBarColors() {
    val statusBarLight = Color.parseColor("#FF0C61A4")
    val statusBarDark = Color.parseColor("#FF0C61A4")/*
    val navigationBarLight = Color.parseColor("#FF0C61A4")
    val navigationBarDark = Color.parseColor("#FF0C61A4")*/
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current as ComponentActivity

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    statusBarLight,
                    statusBarDark
                )
            } else {
                SystemBarStyle.dark(
                    statusBarDark
                )
            },
           /* navigationBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    navigationBarLight,
                    navigationBarDark
                )
            } else {
                SystemBarStyle.dark(navigationBarDark)
            }*/
        )

        onDispose { }
    }
}