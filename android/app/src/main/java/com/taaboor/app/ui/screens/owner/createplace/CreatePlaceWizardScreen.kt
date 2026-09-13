package com.taaboor.app.ui.screens.owner.createplace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.ScreenTitle

private val steps = listOf("اسم المكان", "نوع النشاط", "الموقع", "ساعات العمل", "الخدمات", "طريقة الطابور")

@Composable
fun CreatePlaceWizardScreen(onFinished: () -> Unit) {
    var stepIndex by remember { mutableIntStateOf(0) }
    var text by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(20.dp)) {
        LinearProgressIndicator(
            progress = { (stepIndex + 1) / steps.size.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        ScreenTitle("الخطوة ${stepIndex + 1}", steps[stepIndex])
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(steps[stepIndex]) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        PrimaryButton(if (stepIndex == steps.lastIndex) "ابدأ استقبال العملاء" else "التالي") {
            if (stepIndex == steps.lastIndex) onFinished() else stepIndex++
            text = ""
        }
    }
}
