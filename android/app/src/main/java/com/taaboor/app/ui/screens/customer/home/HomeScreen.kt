package com.taaboor.app.ui.screens.customer.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.repository.MockDataRepository
import com.taaboor.app.ui.components.PlaceCard
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun HomeScreen(onPlaceClick: (String) -> Unit, onSearchClick: () -> Unit, onCategoryClick: (String) -> Unit) {
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        item { Spacer(Modifier.height(20.dp)) }
        item {
            Text("أهلاً بك 👋", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(4.dp))
            Text("عايز تخلص إيه النهارده؟", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Spacer(Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("ابحث عن مكان أو خدمة...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSearchClick() },
                enabled = false,
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(Modifier.height(20.dp))
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(MockDataRepository.categories) { (emoji, name) ->
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceSoft)
                            .clickable { onCategoryClick(name) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(emoji, style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(4.dp))
                        Text(name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
        item {
            Text("الأماكن القريبة منك", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
        }
        items(MockDataRepository.places) { place ->
            PlaceCard(place = place, onClick = { onPlaceClick(place.id) })
            Spacer(Modifier.height(12.dp))
        }
        item { Spacer(Modifier.height(20.dp)) }
    }
}
