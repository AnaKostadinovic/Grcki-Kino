package com.anak.grckikino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.anak.grckikino.ui.GameApp
import com.anak.grckikino.ui.theme.GrckiKinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrckiKinoTheme {
                GameApp()
            }
        }
    }
}
