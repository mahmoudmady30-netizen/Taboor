package com.taaboor.app.ui.screens.owner.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun OwnerDashboardScreen(onOpenQueueManagement: () -> Unit) {
    val stats = MockDataRepository.dashboardStats
    LazyColumn(Modifier.fillMaxSize().padding(20.dp)) {
        item { ScreenTitle("اليوم") }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatBox("${stats.customersToday}", "عميل")
                StatBox("${stats.turnsToday}", "دور")
                StatBox("${stats.bookingsToday}", "حجز")
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatBox("${stats.avgWaitMinutes} د", "متوسط انتظار")
                StatBox("${stats.activeEmployees}", "موظفين نشطين")
            }
            Spacer(Modifier.height(24.dp))
            Text("الطوابير الحالية", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
        }
        items(MockDataRepository.queueLines) { line ->
            Card(
                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceSoft)
            ) {
                Row(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${line.prefix} — ${line.name}", style = MaterialTheme.typography.titleMedium)
                    Text("${line.waitingCount} منتظر", color = PrimaryBlue)
                }
            }
        }
        item {
            Spacer(Modifier.height(20.dp))
            androidx.compose.material3.Button(
                onClick = onOpenQueueManagement,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("إدارة الطابور") }
        }
    }
}

@Composable
private fun StatBox(value: String, label: String) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = SurfaceSoft)) {
        Column(Modifier.padding(14.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}
