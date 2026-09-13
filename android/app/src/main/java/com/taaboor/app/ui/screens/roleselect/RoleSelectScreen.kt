package com.taaboor.app.ui.screens.roleselect

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.components.SecondaryButton
import com.taaboor.app.ui.theme.PrimaryBlue

@Composable
fun RoleSelectScreen(onCustomer: () -> Unit, onOwner: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎫", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text("طابور", style = MaterialTheme.typography.headlineLarge, color = PrimaryBlue)
        Spacer(Modifier.height(32.dp))
        PrimaryButton("أنا عميل", onClick = onCustomer)
        Spacer(Modifier.height(12.dp))
        SecondaryButton("أنا صاحب مكان", onClick = onOwner)
    }
}
