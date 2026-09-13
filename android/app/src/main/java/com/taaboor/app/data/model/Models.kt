package com.taaboor.app.data.model

data class Place(
    val id: String,
    val name: String,
    val category: String,
    val emoji: String,
    val rating: Double,
    val isOpen: Boolean,
    val distanceKm: Double,
    val waitingCount: Int,
    val estimatedWaitMinutes: Int,
    val services: List<Service> = emptyList(),
    val isFavorite: Boolean = false
)

data class Service(
    val id: String,
    val name: String,
    val durationMinutes: Int,
    val price: Int,
    val currency: String = "AED",
    val description: String = ""
)

enum class QueueStatus { OPEN, ALMOST_FULL, FULL, CLOSED }

data class QueueLine(
    val id: String,
    val name: String,
    val prefix: String,
    val waitingCount: Int,
    val status: QueueStatus = QueueStatus.OPEN
)

data class QueueToken(
    val tokenNumber: String,
    val serviceName: String,
    val placeName: String,
    val peopleAhead: Int,
    val estimatedWaitMinutes: Int,
    val currentNumberBeingServed: String
)

data class Booking(
    val id: String,
    val placeName: String,
    val serviceName: String,
    val date: String,
    val time: String,
    val status: String = "confirmed"
)

data class Employee(
    val id: String,
    val name: String,
    val servicesHandled: List<String>,
    val isAvailable: Boolean,
    val isWorkingNow: Boolean,
    val customersServedToday: Int
)

data class DashboardStats(
    val customersToday: Int,
    val turnsToday: Int,
    val bookingsToday: Int,
    val avgWaitMinutes: Int,
    val activeEmployees: Int
)
