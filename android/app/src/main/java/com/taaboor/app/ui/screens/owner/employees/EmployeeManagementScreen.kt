package com.taaboor.app.ui.screens.owner.employees

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
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun EmployeeManagementScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("الموظفين")
        LazyColumn {
            items(MockDataRepository.employees) { emp ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp)) {
                    Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(emp.name, style = MaterialTheme.typography.titleMedium)
                            Text(emp.servicesHandled.joinToString(" • "), color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                            Text("${emp.customersServedToday} عميل اليوم", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                        }
                        Text(if (emp.isWorkingNow) "🟢 يعمل الآن" else "⚪ غير متاح",
                            color = if (emp.isWorkingNow) SuccessTeal else TextSecondary)
                    }
                }
            }
        }
    }
}
