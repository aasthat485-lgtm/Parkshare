package com.parkshare.app.feature_host

/**
 * Data model representing a parking space listed by a host on Parkshare.
 * Assigned Developer: Aastha (Module 3 - Host Listing & Space Management System)
 *
 * @property id Unique identifier for the listing.
 * @property title Friendly title or name of the parking space (e.g. Covered Driveway).
 * @property address Physical street address of the parking property.
 * @property hourlyRate Listing rate in INR (₹) per hour.
 * @property totalSlots Number of vehicle slots available at this space.
 * @property hasEvCharging Indicates whether EV charging equipment is provided.
 * @property isActive Status indicating if the property is currently accepting bookings.
 */
data class HostListing(
    val id: String,
    val title: String,
    val address: String,
    val hourlyRate: Double,
    val totalSlots: Int = 1,
    val hasEvCharging: Boolean = false,
    val isActive: Boolean = true
)
