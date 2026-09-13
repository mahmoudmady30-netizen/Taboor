package com.taaboor.app.ui.screens.owner.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

private data class Metric(val label: String, val value: String)

@Composable
fun AnalyticsScreen() {
    val metrics = listOf(
        Metric("اليوم", "87 عميل"),
        Metric("هذا الأسبوع", "423 عميل"),
        Metric("متوسط الانتظار", "16 دقيقة"),
        Metric("أكثر خدمة طلبًا", "قص شعر"),
        Metric("أكثر وقت ازدحامًا", "6:00 PM – 8:00 PM"),
        Metric("نسبة الحجوزات", "32%")
    )
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("الإحصائيات")
        metrics.forEach { m ->
            Card(
                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceSoft)
            ) {
                Row(Modifier.padding(14.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(m.label, color = TextSecondary)
                    Text(m.value, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
