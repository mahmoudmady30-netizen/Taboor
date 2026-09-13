package com.taaboor.app.ui.screens.owner.services

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun ServicesManagementScreen(onAddService: () -> Unit) {
    val services = MockDataRepository.places.first().services
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddService) { Icon(Icons.Default.Add, contentDescription = null) }
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            ScreenTitle("الخدمات")
            LazyColumn {
                items(services) { service ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp)) {
                        Row(
                            Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(service.name, style = MaterialTheme.typography.titleMedium)
                                Text("${service.durationMinutes} دقيقة • ${service.price} ${service.currency}", color = TextSecondary)
                            }
                            Row {
                                IconButton(onClick = {}) { Icon(Icons.Default.Edit, contentDescription = "تعديل") }
                                IconButton(onClick = {}) { Icon(Icons.Default.Delete, contentDescription = "حذف", tint = SuccessTeal) }
                            }
                        }
                    }
                }
            }
        }
    }
}
