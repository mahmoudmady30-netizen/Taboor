package com.taaboor.app.ui.screens.owner.employeemode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun EmployeeScreen() {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("A027", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        Text("قص شعر", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
        Spacer(Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            BigActionButton("✅ تم الانتهاء")
            BigActionButton("⏭️ التالي")
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            BigActionButton("⏸️ تخطي")
            BigActionButton("🔄 استدعاء مرة أخرى")
        }
    }
}

@Composable
private fun BigActionButton(text: String) {
    Button(onClick = {}, modifier = Modifier.height(64.dp).width(150.dp)) {
        Text(text, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}
