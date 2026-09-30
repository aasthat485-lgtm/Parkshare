package com.parkshare.app.feature_discovery

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * ViewModel managing driver parking discovery, search query state, and filter criteria.
 * Assigned Developer: Sudhanshu (Module 2 - Driver Discovery & Search System)
 */
class DiscoveryViewModel : ViewModel() {

    // Master dataset of available parking spots across key Mumbai localities
    private val masterSpots: List<ParkingSpot> = listOf(
        ParkingSpot(
            id = "spot-101",
            title = "Dadar Flower Market Covered Parking",
            address = "Senapati Bapat Marg, Dadar West, Mumbai",
            pricePerHour = 40.0,
            hasEvCharging = true,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-102",
            title = "Matunga Central Residency Bay",
            address = "Lakhamsi Napoo Rd, Matunga East, Mumbai",
            pricePerHour = 35.0,
            hasEvCharging = false,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-103",
            title = "Andheri Metro Station Hub Parking",
            address = "Andheri-Kurla Road, Andheri East, Mumbai",
            pricePerHour = 60.0,
            hasEvCharging = true,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-104",
            title = "BKC Financial Center Multi-Level Lot",
            address = "G Block, Bandra Kurla Complex, Mumbai",
            pricePerHour = 75.0,
            hasEvCharging = true,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-105",
            title = "Powai Hiranandani Galleria Garage",
            address = "Central Avenue, Hiranandani Gardens, Powai, Mumbai",
            pricePerHour = 45.0,
            hasEvCharging = false,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-106",
            title = "Dadar Shivaji Park Dedicated Bay",
            address = "Cadell Road, Shivaji Park, Dadar West, Mumbai",
            pricePerHour = 50.0,
            hasEvCharging = true,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-107",
            title = "Matunga King's Circle Safe Bay",
            address = "Dr. Ambedkar Road, Matunga, Mumbai",
            pricePerHour = 30.0,
            hasEvCharging = false,
            isAvailable = true
        ),
        ParkingSpot(
            id = "spot-108",
            title = "Andheri West Infinity Plaza Parking",
            address = "Link Road, Andheri West, Mumbai",
            pricePerHour = 65.0,
            hasEvCharging = true,
            isAvailable = true
        )
    )

    // Observable Compose state for the filtered spots
    private val _parkingSpots = mutableStateOf(masterSpots)
    val parkingSpots: State<List<ParkingSpot>> = _parkingSpots

    // Search query state
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    // Filter states
    private val _onlyEv = mutableStateOf(false)
    val onlyEv: State<Boolean> = _onlyEv

    private val _under50 = mutableStateOf(false)
    val under50: State<Boolean> = _under50

    /**
     * Updates the search query and recalculates matching parking spots.
     */
    fun searchByQuery(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    /**
     * Toggles the Electric Vehicle (EV) charging station filter.
     */
    fun toggleEvFilter(onlyEv: Boolean) {
        _onlyEv.value = onlyEv
        applyFilters()
    }

    /**
     * Toggles the budget filter for spots under ₹50/hr.
     */
    fun toggleUnder50Filter(under50: Boolean) {
        _under50.value = under50
        applyFilters()
    }

    /**
     * Combines query and all active filters to update the visible list.
     */
    private fun applyFilters() {
        val query = _searchQuery.value.trim().lowercase()
        val evOnly = _onlyEv.value
        val max50 = _under50.value

        _parkingSpots.value = masterSpots.filter { spot ->
            val matchesQuery = query.isEmpty() ||
                spot.title.lowercase().contains(query) ||
                spot.address.lowercase().contains(query)

            val matchesEv = !evOnly || spot.hasEvCharging
            val matchesPrice = !max50 || spot.pricePerHour <= 50.0

            matchesQuery && matchesEv && matchesPrice
        }
    }
}
