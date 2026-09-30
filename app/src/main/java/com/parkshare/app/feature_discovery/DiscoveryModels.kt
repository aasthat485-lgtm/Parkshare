package com.parkshare.app.feature_discovery

/**
 * Data model representing a parking spot available for booking in Parkshare.
 * Assigned Developer: Sudhanshu (Module 2 - Driver Discovery & Search System)
 *
 * @property id Unique identifier for the parking spot.
 * @property title Friendly title or name of the spot.
 * @property address Physical street address or location description.
 * @property pricePerHour Rate per hour in INR (₹).
 * @property hasEvCharging Flag indicating if EV charging infrastructure is available.
 * @property isAvailable Flag indicating if the spot is currently free and open for reservation.
 */
data class ParkingSpot(
    val id: String,
    val title: String,
    val address: String,
    val pricePerHour: Double,
    val hasEvCharging: Boolean,
    val isAvailable: Boolean = true
)
