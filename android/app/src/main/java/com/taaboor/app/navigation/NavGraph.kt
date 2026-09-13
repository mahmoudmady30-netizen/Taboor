package com.taaboor.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.taaboor.app.ui.components.NavItem
import com.taaboor.app.ui.components.TaaboorBottomBar
import com.taaboor.app.ui.screens.customer.booking.BookingConfirmationScreen
import com.taaboor.app.ui.screens.customer.booking.BookingScreen
import com.taaboor.app.ui.screens.customer.favorites.FavoritesScreen
import com.taaboor.app.ui.screens.customer.home.HomeScreen
import com.taaboor.app.ui.screens.customer.mybookings.MyBookingsScreen
import com.taaboor.app.ui.screens.customer.myqueue.MyQueueScreen
import com.taaboor.app.ui.screens.customer.place.PlaceDetailsScreen
import com.taaboor.app.ui.screens.customer.profile.ProfileScreen
import com.taaboor.app.ui.screens.customer.queue.JoinQueueScreen
import com.taaboor.app.ui.screens.customer.queue.LiveQueueScreen
import com.taaboor.app.ui.screens.customer.queue.TokenScreen
import com.taaboor.app.ui.screens.customer.search.SearchScreen
import com.taaboor.app.ui.screens.onboarding.OnboardingScreen
import com.taaboor.app.ui.screens.owner.addcustomer.AddCustomerScreen
import com.taaboor.app.ui.screens.owner.analytics.AnalyticsScreen
import com.taaboor.app.ui.screens.owner.createplace.CreatePlaceWizardScreen
import com.taaboor.app.ui.screens.owner.dashboard.OwnerDashboardScreen
import com.taaboor.app.ui.screens.owner.display.QueueDisplayScreen
import com.taaboor.app.ui.screens.owner.employeemode.EmployeeScreen
import com.taaboor.app.ui.screens.owner.employees.EmployeeManagementScreen
import com.taaboor.app.ui.screens.owner.qr.QRScreen
import com.taaboor.app.ui.screens.owner.queue.QueueManagementScreen
import com.taaboor.app.ui.screens.owner.services.ServicesManagementScreen
import com.taaboor.app.ui.screens.owner.settings.OwnerSettingsScreen
import com.taaboor.app.ui.screens.roleselect.RoleSelectScreen
import com.taaboor.app.ui.screens.splash.SplashScreen

private val customerNavItems = listOf(
    NavItem("الرئيسية", Icons.Default.Home, Routes.HOME),
    NavItem("دورك", Icons.Default.ConfirmationNumber, Routes.MY_QUEUE),
    NavItem("حجوزاتي", Icons.Default.CalendarMonth, Routes.MY_BOOKINGS),
    NavItem("المفضلة", Icons.Default.Favorite, Routes.FAVORITES),
    NavItem("حسابي", Icons.Default.Person, Routes.PROFILE)
)

private val ownerNavItems = listOf(
    NavItem("الرئيسية", Icons.Default.Home, Routes.OWNER_DASHBOARD),
    NavItem("الطوابير", Icons.Default.List, Routes.QUEUE_MANAGEMENT),
    NavItem("العملاء", Icons.Default.Group, Routes.EMPLOYEE_MANAGEMENT),
    NavItem("الإحصائيات", Icons.Default.BarChart, Routes.ANALYTICS),
    NavItem("الإعدادات", Icons.Default.Settings, Routes.OWNER_SETTINGS)
)

@Composable
fun TaaboorNavGraph(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomBarRoutes = (customerNavItems + ownerNavItems).map { it.route }.toSet()
    val showCustomerBar = currentRoute in customerNavItems.map { it.route }
    val showOwnerBar = currentRoute in ownerNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            when {
                showCustomerBar -> TaaboorBottomBar(customerNavItems, currentRoute) { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                showOwnerBar -> TaaboorBottomBar(ownerNavItems, currentRoute) { route ->
                    navController.navigate(route) {
                        popUpTo(Routes.OWNER_DASHBOARD) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Routes.SPLASH) {
                SplashScreen(onFinished = { navController.navigate(Routes.ONBOARDING) { popUpTo(Routes.SPLASH) { inclusive = true } } })
            }
            composable(Routes.ONBOARDING) {
                OnboardingScreen(onDone = { navController.navigate(Routes.ROLE_SELECT) { popUpTo(Routes.ONBOARDING) { inclusive = true } } })
            }
            composable(Routes.ROLE_SELECT) {
                RoleSelectScreen(
                    onCustomer = { navController.navigate(Routes.HOME) { popUpTo(Routes.ROLE_SELECT) { inclusive = true } } },
                    onOwner = { navController.navigate(Routes.OWNER_DASHBOARD) { popUpTo(Routes.ROLE_SELECT) { inclusive = true } } }
                )
            }

            // Customer flow
            composable(Routes.HOME) {
                HomeScreen(
                    onPlaceClick = { id -> navController.navigate(Routes.placeDetails(id)) },
                    onSearchClick = { navController.navigate(Routes.SEARCH) },
                    onCategoryClick = { }
                )
            }
            composable(Routes.SEARCH) {
                SearchScreen(onPlaceClick = { id -> navController.navigate(Routes.placeDetails(id)) })
            }
            composable(Routes.PLACE_DETAILS) { backStack ->
                val placeId = backStack.arguments?.getString("placeId") ?: ""
                PlaceDetailsScreen(
                    placeId = placeId,
                    onJoinQueue = { navController.navigate(Routes.joinQueue(placeId)) },
                    onBookAppointment = { navController.navigate(Routes.booking(placeId)) }
                )
            }
            composable(Routes.JOIN_QUEUE) {
                val placeId = it.arguments?.getString("placeId") ?: ""
                JoinQueueScreen(placeId = placeId, onTokenIssued = { navController.navigate(Routes.TOKEN) })
            }
            composable(Routes.TOKEN) {
                TokenScreen(onTrackLive = { navController.navigate(Routes.LIVE_QUEUE) }, onShare = {})
            }
            composable(Routes.LIVE_QUEUE) { LiveQueueScreen() }
            composable(Routes.BOOKING) {
                BookingScreen(onConfirm = { navController.navigate(Routes.BOOKING_CONFIRMATION) })
            }
            composable(Routes.BOOKING_CONFIRMATION) {
                BookingConfirmationScreen(onDone = { navController.popBackStack(Routes.HOME, inclusive = false) })
            }
            composable(Routes.MY_QUEUE) {
                MyQueueScreen(hasActiveTurn = true, onFindPlace = { navController.navigate(Routes.HOME) }, onOpenTurn = {})
            }
            composable(Routes.MY_BOOKINGS) {
                MyBookingsScreen(onNewBooking = { navController.navigate(Routes.HOME) })
            }
            composable(Routes.FAVORITES) {
                FavoritesScreen(
                    onPlaceClick = { id -> navController.navigate(Routes.placeDetails(id)) },
                    onFindPlace = { navController.navigate(Routes.HOME) }
                )
            }
            composable(Routes.PROFILE) {
                ProfileScreen(onSwitchToOwner = { navController.navigate(Routes.OWNER_DASHBOARD) })
            }

            // Owner flow
            composable(Routes.OWNER_DASHBOARD) {
                OwnerDashboardScreen(onOpenQueueManagement = { navController.navigate(Routes.QUEUE_MANAGEMENT) })
            }
            composable(Routes.CREATE_PLACE) {
                CreatePlaceWizardScreen(onFinished = { navController.navigate(Routes.OWNER_DASHBOARD) })
            }
            composable(Routes.SERVICES_MANAGEMENT) {
                ServicesManagementScreen(onAddService = {})
            }
            composable(Routes.QUEUE_MANAGEMENT) {
                QueueManagementScreen(onAddCustomer = { navController.navigate(Routes.ADD_CUSTOMER) })
            }
            composable(Routes.EMPLOYEE_MANAGEMENT) { EmployeeManagementScreen() }
            composable(Routes.ADD_CUSTOMER) {
                AddCustomerScreen(onAdded = { navController.popBackStack() })
            }
            composable(Routes.EMPLOYEE_MODE) { EmployeeScreen() }
            composable(Routes.QUEUE_DISPLAY) { QueueDisplayScreen() }
            composable(Routes.QR_SCREEN) { QRScreen(placeName = "Barber House") }
            composable(Routes.ANALYTICS) { AnalyticsScreen() }
            composable(Routes.OWNER_SETTINGS) { OwnerSettingsScreen() }
        }
    }
}
