package com.example.yummy_bites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.yummy_bites.ui.screens.MainScreen
import com.example.yummy_bites.ui.theme.YummyBitesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YummyBitesTheme {
                MainScreen()
            }
        }
    }
}
