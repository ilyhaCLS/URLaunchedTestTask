package com.youarelaunched.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.youarelaunched.challenge.ui.main.MainScreenUI
import com.youarelaunched.challenge.ui.theme.TaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTheme {
                MainScreenUI()
            }
        }
    }
}