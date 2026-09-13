package com.taaboor.app.ui.screens.customer.booking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun BookingConfirmationScreen(onDone: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✓", style = MaterialTheme.typography.headlineLarge, color = SuccessTeal)
        Spacer(Modifier.height(12.dp))
        Text("تم الحجز بنجاح ✓", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        Text("الخدمة: قص شعر", color = TextSecondary)
        Text("التاريخ: السبت", color = TextSecondary)
        Text("الوقت: 10:30", color = TextSecondary)
        Text("Booking ID: B017", color = TextSecondary)
        Spacer(Modifier.height(28.dp))
        PrimaryButton("تم") { onDone() }
    }
}
