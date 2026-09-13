package com.taaboor.app.ui.screens.owner.queue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.model.QueueStatus
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.ErrorRed
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary
import com.taaboor.app.ui.theme.WarningOrange

@Composable
fun QueueManagementScreen(onAddCustomer: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("إدارة الطابور")
        LazyColumn(Modifier.weight(1f)) {
            items(MockDataRepository.queueLines) { line ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${line.prefix} — ${line.name}", style = MaterialTheme.typography.titleMedium)
                            val (label, color) = when (line.status) {
                                QueueStatus.OPEN -> "🟢 مفتوح" to SuccessTeal
                                QueueStatus.ALMOST_FULL -> "🟡 قريب من الامتلاء" to WarningOrange
                                QueueStatus.FULL -> "🔴 ممتلئ" to ErrorRed
                                QueueStatus.CLOSED -> "⚫ مغلق" to TextSecondary
                            }
                            Text(label, color = color)
                        }
                        Text("${line.waitingCount} منتظر", color = TextSecondary)
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(onClick = {}, label = { Text("التالي") })
                            AssistChip(onClick = {}, label = { Text("تخطي") })
                            AssistChip(onClick = {}, label = { Text("استدعاء مرة أخرى") })
                        }
                    }
                }
            }
        }
        Button(onClick = onAddCustomer, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp)) {
            Text("+ إضافة عميل")
        }
    }
}
