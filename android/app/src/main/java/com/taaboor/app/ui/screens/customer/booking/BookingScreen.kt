package com.taaboor.app.ui.screens.customer.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SurfaceSoft

private val timeSlots = listOf("09:00", "09:30", "10:00", "10:30", "11:00")

@Composable
fun BookingScreen(onConfirm: () -> Unit) {
    var selected by remember { mutableStateOf(timeSlots[3]) }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("احجز موعد")
        Text("اختر الوقت", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(timeSlots) { slot ->
                FilterChip(
                    selected = slot == selected,
                    onClick = { selected = slot },
                    label = { Text(slot) },
                    colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SurfaceSoft, selectedLabelColor = PrimaryBlue)
                )
            }
        }
        Spacer(Modifier.weight(1f))
        PrimaryButton("تأكيد الحجز") { onConfirm() }
    }
}
