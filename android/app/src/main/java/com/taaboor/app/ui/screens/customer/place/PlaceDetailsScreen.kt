package com.taaboor.app.ui.screens.customer.place

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.model.Service
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun PlaceDetailsScreen(placeId: String, onJoinQueue: (String) -> Unit, onBookAppointment: (String) -> Unit) {
    val place = MockDataRepository.findPlace(placeId) ?: return
    LazyColumn(Modifier.fillMaxSize().padding(20.dp)) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(64.dp).clip(RoundedCornerShape(18.dp)).background(SurfaceSoft),
                    contentAlignment = Alignment.Center
                ) { Text(place.emoji, style = MaterialTheme.typography.headlineLarge) }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(place.name, style = MaterialTheme.typography.headlineMedium)
                    Text("⭐ ${place.rating}   ${if (place.isOpen) "🟢 مفتوح الآن" else "🔴 مغلق"}   📍 ${place.distanceKm} كم",
                        style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                }
            }
            Spacer(Modifier.height(20.dp))
            Text("الخدمات", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
        }
        items(place.services) { service -> ServiceRow(service) }
        item {
            Spacer(Modifier.height(16.dp))
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = SurfaceSoft)) {
                Column(Modifier.padding(16.dp)) {
                    Text("حالة الطابور الآن", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(6.dp))
                    Text("آخر رقم يتم خدمته: A021", color = TextSecondary)
                    Text("${place.waitingCount} أشخاص منتظرين", color = TextSecondary)
                    Text("متوسط الانتظار ${place.estimatedWaitMinutes} دقيقة", color = PrimaryBlue)
                }
            }
            Spacer(Modifier.height(20.dp))
            PrimaryButton("🎫 احجز دوري الآن") { onJoinQueue(placeId) }
            Spacer(Modifier.height(10.dp))
            OutlinedButtonRow("📅 احجز موعد") { onBookAppointment(placeId) }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ServiceRow(service: Service) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(service.name, style = MaterialTheme.typography.titleMedium)
                Text("${service.durationMinutes} دقيقة", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
            }
            Text("${service.price} ${service.currency}", color = SuccessTeal, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun OutlinedButtonRow(text: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp)) {
        Text(text)
    }
}
