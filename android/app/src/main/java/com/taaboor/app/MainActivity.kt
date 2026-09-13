package com.taaboor.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.taaboor.app.navigation.TaaboorNavGraph
import com.taaboor.app.ui.theme.TaaboorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaaboorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TaaboorNavGraph()
                }
            }
        }
    }
}
