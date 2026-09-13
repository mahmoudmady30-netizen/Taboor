package com.taaboor.app.ui.screens.owner.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun QRScreen(placeName: String) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(placeName, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier.size(220.dp).background(SurfaceSoft, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) { Text("QR", style = MaterialTheme.typography.headlineLarge) }
        Spacer(Modifier.height(16.dp))
        Text("اطبع هذا الكود وضعه عند المدخل — العملاء يمسحونه لأخذ دورهم مباشرة",
            color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}
