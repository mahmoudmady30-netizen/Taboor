package com.taaboor.app.ui.screens.customer.queue

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.taaboor.app.ui.theme.PrimaryBlue
import com.taaboor.app.ui.theme.SuccessTeal
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun LiveQueueScreen() {
    var peopleAhead by remember { mutableIntStateOf(5) }

    LaunchedEffect(Unit) {
        while (peopleAhead > 0) {
            delay(2500)
            peopleAhead -= 1
        }
    }

    val progress by animateFloatAsState(
        targetValue = 1f - (peopleAhead / 5f),
        animationSpec = tween(600), label = "progress"
    )

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            when {
                peopleAhead == 0 -> "دورك الآن 🎉"
                peopleAhead == 1 -> "أنت التالي 🔔"
                else -> "باقي $peopleAhead أشخاص قبلك"
            },
            style = MaterialTheme.typography.headlineMedium,
            color = if (peopleAhead <= 1) SuccessTeal else PrimaryBlue
        )
        Spacer(Modifier.height(24.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(10.dp),
            color = PrimaryBlue
        )
        Spacer(Modifier.height(16.dp))
        Text("A022 → A023 → A024 → A025 → A026 → A027", color = TextSecondary, fontSize = 13.sp)
    }
}
