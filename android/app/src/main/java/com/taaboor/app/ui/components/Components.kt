package com.taaboor.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.taaboor.app.data.model.Place
import com.taaboor.app.ui.theme.*

@Composable
fun ScreenTitle(text: String, subtitle: String? = null) {
    Column(Modifier.padding(bottom = 16.dp)) {
        Text(text, style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
        if (subtitle != null) {
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}

@Composable
fun PlaceCard(place: Place, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = BgWhite)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceSoft),
                contentAlignment = Alignment.Center
            ) { Text(place.emoji, style = MaterialTheme.typography.headlineMedium) }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(place.name, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusDot(place.isOpen)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        if (place.isOpen) "مفتوح" else "مغلق",
                        color = if (place.isOpen) SuccessTeal else ErrorRed,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("⭐ ${place.rating}", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    Spacer(Modifier.width(10.dp))
                    Text("📍 ${place.distanceKm} كم", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                }
                if (place.isOpen) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "👥 ${place.waitingCount} منتظرين  ⏱️ حوالي ${place.estimatedWaitMinutes} دقيقة",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryBlue
                    )
                }
            }
        }
    }
}

@Composable
fun StatusDot(isOpen: Boolean) {
    Box(
        Modifier
            .size(8.dp)
            .clip(RoundedCornerShape(50))
            .background(if (isOpen) SuccessTeal else ErrorRed)
    )
}

@Composable
fun PrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium, color = Color.White)
    }
}

@Composable
fun SecondaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium, color = PrimaryBlue)
    }
}

@Composable
fun EmptyState(message: String, actionLabel: String, onAction: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("📭", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp))
        Text(message, style = MaterialTheme.typography.titleMedium, color = TextSecondary)
        Spacer(Modifier.height(20.dp))
        PrimaryButton(actionLabel, onClick = onAction)
    }
}

data class NavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)

@Composable
fun TaaboorBottomBar(items: List<NavItem>, currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar(containerColor = BgWhite) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryBlue,
                    selectedTextColor = PrimaryBlue,
                    indicatorColor = SurfaceSoft
                )
            )
        }
    }
}
