package com.taaboor.app.ui.screens.owner.addcustomer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.ScreenTitle

@Composable
fun AddCustomerScreen(onAdded: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var service by remember { mutableStateOf("قص شعر") }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("إضافة عميل")
        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("اسم العميل (اختياري)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = service, onValueChange = { service = it },
            label = { Text("الخدمة") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton("إضافة للطابور") { onAdded("A031") }
    }
}
