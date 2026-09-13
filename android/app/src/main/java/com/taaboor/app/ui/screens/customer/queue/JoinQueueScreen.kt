package com.taaboor.app.ui.screens.customer.queue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SurfaceSoft

@Composable
fun JoinQueueScreen(placeId: String, onTokenIssued: () -> Unit) {
    val place = MockDataRepository.findPlace(placeId) ?: return
    var selectedServiceId by remember { mutableStateOf(place.services.firstOrNull()?.id) }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("ماذا تريد؟", place.name)
        LazyColumn(Modifier.weight(1f)) {
            items(place.services) { service ->
                val selected = service.id == selectedServiceId
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedServiceId = service.id },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) SurfaceSoft else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = if (selected) androidx.compose.foundation.BorderStroke(2.dp, PrimaryBlue) else null
                ) {
                    Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(service.name, style = MaterialTheme.typography.titleMedium)
                            Text("${service.durationMinutes} دقيقة", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("${service.price} ${service.currency}", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
        PrimaryButton("🎫 احجز دوري الآن") { onTokenIssued() }
    }
}
