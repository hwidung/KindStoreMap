package com.konkuk.kindmap.ui.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Context.HandleDoubleBackToExit() {
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime <= 2000) {
            (this as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
            Toast.makeText(this, "📍뒤로가기를 한 번 더 누르면 종료됩니다!", Toast.LENGTH_SHORT).show()
        }
    }
}
