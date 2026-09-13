package com.taaboor.app.ui.screens.customer.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.PlaceCard
import com.taaboor.app.ui.components.ScreenTitle

@Composable
fun SearchScreen(onPlaceClick: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    val results = MockDataRepository.places.filter {
        query.isBlank() || it.name.contains(query, ignoreCase = true) || it.category.contains(query)
    }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("ابحث")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("ابحث عن مكان أو خدمة...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(results) { place ->
                PlaceCard(place = place, onClick = { onPlaceClick(place.id) })
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
