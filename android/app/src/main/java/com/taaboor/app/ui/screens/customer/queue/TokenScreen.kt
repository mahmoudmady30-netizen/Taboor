package com.taaboor.app.ui.screens.customer.queue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.SecondaryButton
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun TokenScreen(onTrackLive: () -> Unit, onShare: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceSoft),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("A027", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
                Spacer(Modifier.height(4.dp))
                Text("قص شعر", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("أمامك", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text("5 أشخاص", style = MaterialTheme.typography.titleMedium)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("الرقم الحالي", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text("A022", style = MaterialTheme.typography.titleMedium)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("الوقت المتوقع", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Text("18 دقيقة", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
        Spacer(Modifier.height(28.dp))
        PrimaryButton("تابع دورك مباشرة") { onTrackLive() }
        Spacer(Modifier.height(10.dp))
        SecondaryButton("مشاركة الدور") { onShare() }
    }
}
