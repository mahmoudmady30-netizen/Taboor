package com.taaboor.app.ui.screens.owner.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taaboor.app.ui.theme.PrimaryBlue

@Composable
fun QueueDisplayScreen() {
    Column(
        Modifier.fillMaxSize().background(Color(0xFF10182B)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("A027", fontSize = 96.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text("توجه إلى كرسي 2", color = Color(0xFFB7C3E0), fontSize = 20.sp)
        Spacer(Modifier.height(32.dp))
        Text("التالي", color = Color(0xFF6E7BA6), fontSize = 14.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            listOf("A028", "A029", "A030").forEach {
                Text(it, color = Color.White, fontSize = 28.sp)
            }
        }
    }
}
