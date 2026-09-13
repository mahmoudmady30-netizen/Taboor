package com.taaboor.app.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1200)
        onFinished()
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎫", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp))
        Text("طابور", style = MaterialTheme.typography.headlineLarge, color = PrimaryBlue)
        Spacer(Modifier.height(6.dp))
        Text("احجز دورك.. وكمّل يومك", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
    }
}
