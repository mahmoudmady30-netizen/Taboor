package com.taaboor.app.ui.screens.customer.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.EmptyState
import com.taaboor.app.ui.components.PlaceCard
import com.taaboor.app.ui.components.ScreenTitle

@Composable
fun FavoritesScreen(onPlaceClick: (String) -> Unit, onFindPlace: () -> Unit) {
    val favorites = MockDataRepository.places.take(2)
    if (favorites.isEmpty()) {
        EmptyState("لسه معندكش أماكن مفضلة", "ابحث عن مكان", onFindPlace)
        return
    }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("المفضلة")
        LazyColumn {
            items(favorites) { place ->
                PlaceCard(place = place, onClick = { onPlaceClick(place.id) })
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
