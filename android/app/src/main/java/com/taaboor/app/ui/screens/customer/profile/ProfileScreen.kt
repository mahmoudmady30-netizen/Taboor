package com.taaboor.app.ui.screens.customer.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.ScreenTitle
import com.taaboor.app.ui.components.SecondaryButton
import com.taaboor.app.ui.theme.SurfaceSoft
import com.taaboor.app.ui.theme.TextSecondary

@Composable
fun ProfileScreen(onSwitchToOwner: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        ScreenTitle("حسابي")
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = SurfaceSoft)) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).background(androidx.compose.ui.graphics.Color.White, RoundedCornerShape(50)))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("زائر", style = MaterialTheme.typography.titleMedium)
                    Text("سجّل الدخول لحفظ حجوزاتك ومفضلتك", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        SecondaryButton("لدي مكان عمل؟ انتقل لوضع صاحب المكان") { onSwitchToOwner() }
    }
}
