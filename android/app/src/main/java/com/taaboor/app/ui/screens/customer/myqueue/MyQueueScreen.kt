package com.taaboor.app.ui.screens.customer.myqueue

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.EmptyState

@Composable
fun MyQueueScreen(hasActiveTurn: Boolean, onFindPlace: () -> Unit, onOpenTurn: () -> Unit) {
    if (hasActiveTurn) {
        com.taaboor.app.ui.screens.customer.queue.LiveQueueScreen()
    } else {
        EmptyState(
            message = "لسه ما حجزتش دور",
            actionLabel = "ابحث عن مكان",
            onAction = onFindPlace
        )
    }
}
