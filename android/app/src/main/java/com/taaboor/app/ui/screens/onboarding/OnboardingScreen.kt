package com.taaboor.app.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taaboor.app.ui.components.PrimaryButton
import com.taaboor.app.ui.theme.TextSecondary

private data class OnboardPage(val emoji: String, val title: String, val desc: String)

private val pages = listOf(
    OnboardPage("🔍", "دور مكانك", "ابحث عن أي مكان قريب منك يقدم الخدمة اللي محتاجها"),
    OnboardPage("🎫", "خد دورك في ثواني", "احجز دورك أونلاين أو بمسح QR من داخل المكان"),
    OnboardPage("🔔", "تابع دورك لحظيًا", "هنبعتلك إشعار قبل ما يجيلك الدور عشان متستناش واقف")
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val p = pages[page]
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(p.emoji, style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(16.dp))
                Text(p.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text(p.desc, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            }
        }
        PrimaryButton(if (pagerState.currentPage == pages.lastIndex) "ابدأ الآن" else "التالي") {
            if (pagerState.currentPage == pages.lastIndex) onDone()
        }
    }
}
