package com.taaboor.app.data.repository

import com.taaboor.app.data.model.*

/**
 * Mock in-memory data source standing in for the real backend (see /docs/architecture.md
 * for the recommended Firebase/Supabase + Realtime design to replace this).
 */
object MockDataRepository {

    val categories = listOf(
        "💈" to "حلاق", "💇" to "صالون", "🩺" to "عيادات", "🍽️" to "مطاعم",
        "☕" to "كافيهات", "🚗" to "غسيل سيارات", "🔧" to "صيانة",
        "🛍️" to "محلات", "✂️" to "خياطة", "🏪" to "خدمات أخرى"
    )

    private val barberServices = listOf(
        Service("s1", "قص شعر", 20, 30),
        Service("s2", "قص + ذقن", 35, 50),
        Service("s3", "ذقن", 15, 20)
    )

    private val cafeServices = listOf(
        Service("s4", "قهوة مختصة", 8, 18),
        Service("s5", "فطار", 15, 35)
    )

    val places = listOf(
        Place("p1", "Barber House", "حلاق", "💈", 4.8, true, 1.2, 7, 25, barberServices),
        Place("p2", "Cedar Barber", "حلاق", "💈", 4.6, true, 2.4, 3, 12, barberServices),
        Place("p3", "Cloud Coffee", "كافيهات", "☕", 4.7, true, 0.6, 4, 10, cafeServices),
        Place("p4", "Dr. Layla Clinic", "عيادات", "🩺", 4.9, false, 3.1, 0, 0, barberServices),
        Place("p5", "Shine Car Wash", "غسيل سيارات", "🚗", 4.5, true, 1.8, 9, 30, barberServices)
    )

    val queueLines = listOf(
        QueueLine("q1", "قص شعر", "A", 12, QueueStatus.OPEN),
        QueueLine("q2", "ذقن", "B", 4, QueueStatus.OPEN),
        QueueLine("q3", "سريع", "C", 2, QueueStatus.ALMOST_FULL)
    )

    val employees = listOf(
        Employee("e1", "أحمد", listOf("قص شعر", "قص + ذقن"), true, true, 14),
        Employee("e2", "محمد", listOf("ذقن"), true, true, 9),
        Employee("e3", "علي", listOf("قص شعر"), false, false, 0)
    )

    val myBookings = listOf(
        Booking("b1", "Barber House", "قص شعر", "السبت", "10:30")
    )

    val dashboardStats = DashboardStats(
        customersToday = 87,
        turnsToday = 73,
        bookingsToday = 14,
        avgWaitMinutes = 16,
        activeEmployees = 3
    )

    fun findPlace(id: String): Place? = places.find { it.id == id }
}
