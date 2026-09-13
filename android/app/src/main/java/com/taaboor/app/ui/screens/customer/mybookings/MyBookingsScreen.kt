package com.taaboor.app.ui.screens.customer.mybookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.EmptyState
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun MyBookingsScreen(onNewBooking: () -> Unit) {
    val bookings = MockDataRepository.myBookings
    if (bookings.isEmpty()) {
        EmptyState("مفيش حجوزات لحد دلوقتي", "احجز موعد", onNewBooking)
        return
    }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("حجوزاتي")
        LazyColumn {
            items(bookings) { booking ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(booking.placeName, style = MaterialTheme.typography.titleMedium)
                        Text("${booking.serviceName} • ${booking.date} ${booking.time}", color = TextSecondary)
                        Text("مؤكد", color = SuccessTeal)
                    }
                }
            }
        }
    }
}
