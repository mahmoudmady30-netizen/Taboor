package com.taaboor.app.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val ROLE_SELECT = "role_select"

    // Customer
    const val HOME = "home"
    const val SEARCH = "search"
    const val PLACE_DETAILS = "place_details/{placeId}"
    fun placeDetails(placeId: String) = "place_details/$placeId"
    const val JOIN_QUEUE = "join_queue/{placeId}"
    fun joinQueue(placeId: String) = "join_queue/$placeId"
    const val TOKEN = "token"
    const val LIVE_QUEUE = "live_queue"
    const val BOOKING = "booking/{placeId}"
    fun booking(placeId: String) = "booking/$placeId"
    const val BOOKING_CONFIRMATION = "booking_confirmation"
    const val MY_QUEUE = "my_queue"
    const val MY_BOOKINGS = "my_bookings"
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"

    // Owner
    const val OWNER_DASHBOARD = "owner_dashboard"
    const val CREATE_PLACE = "create_place"
    const val SERVICES_MANAGEMENT = "services_management"
    const val QUEUE_MANAGEMENT = "queue_management"
    const val EMPLOYEE_MANAGEMENT = "employee_management"
    const val ADD_CUSTOMER = "add_customer"
    const val EMPLOYEE_MODE = "employee_mode"
    const val QUEUE_DISPLAY = "queue_display"
    const val QR_SCREEN = "qr_screen"
    const val ANALYTICS = "analytics"
    const val OWNER_SETTINGS = "owner_settings"
}
