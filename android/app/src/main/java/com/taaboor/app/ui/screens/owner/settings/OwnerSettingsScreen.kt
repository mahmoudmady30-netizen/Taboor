package com.taaboor.app.ui.screens.owner.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.ScreenTitle

private val settingsItems = listOf(
    "اسم الطوابير و Prefix الأرقام", "الخدمات", "ألوان المكان و Logo", "رسالة الترحيب",
    "ساعات العمل", "الموظفين", "عدد الطوابير", "السماح بالحجز Online",
    "السماح بالحجز من QR", "الحد الأقصى للطابور", "رسائل Notifications", "شاشة الانتظار",
    "الاشتراك (Free / Pro / Premium)", "المساعدة"
)

@Composable
fun OwnerSettingsScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("الإعدادات")
        LazyColumn {
            items(settingsItems) { item ->
                ListItem(headlineContent = { Text(item) })
                Divider()
            }
        }
    }
}
