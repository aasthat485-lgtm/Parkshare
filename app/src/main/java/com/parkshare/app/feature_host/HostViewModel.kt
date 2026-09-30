package com.parkshare.app.feature_host

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.UUID

/**
 * ViewModel managing host space listings, registration of new parking properties,
 * and real-time status toggling.
 *
 * Assigned Developer: Aastha (Module 3 - Host Listing & Space Management System)
 */
class HostViewModel : ViewModel() {

    // Dynamic list of listings owned by the host, backed by Compose observable state
    val listings = mutableStateListOf<HostListing>(
        HostListing(
            id = "host-spot-01",
            title = "Bandra West Covered Driveway",
            address = "Pali Hill, Bandra West, Mumbai",
            hourlyRate = 60.0,
            totalSlots = 2,
            hasEvCharging = true,
            isActive = true
        ),
        HostListing(
            id = "host-spot-02",
            title = "Dadar East Residential Garage",
            address = "Hindu Colony, Dadar East, Mumbai",
            hourlyRate = 45.0,
            totalSlots = 1,
            hasEvCharging = false,
            isActive = true
        ),
        HostListing(
            id = "host-spot-03",
            title = "Andheri Lokhandwala Open Bay",
            address = "Link Road, Lokhandwala, Andheri West, Mumbai",
            hourlyRate = 50.0,
            totalSlots = 3,
            hasEvCharging = false,
            isActive = false
        )
    )

    private val _statusMessage = mutableStateOf<String?>(null)
    val statusMessage: State<String?> = _statusMessage

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    /**
     * Registers a new parking space into the host's active inventory.
     *
     * @param title Title or descriptor of the parking spot
     * @param address Street address of the property
     * @param rateStr Rate per hour string
     * @param slotsStr Number of vehicle slots available
     * @param hasEv Whether EV charging is supported
     * @return Boolean indicating success or failure of validation
     */
    fun addListing(
        title: String,
        address: String,
        rateStr: String,
        slotsStr: String,
        hasEv: Boolean
    ): Boolean {
        if (title.isBlank()) {
            _errorMessage.value = "Please provide a space title."
            _statusMessage.value = null
            return false
        }
        if (address.isBlank()) {
            _errorMessage.value = "Please enter the full address."
            _statusMessage.value = null
            return false
        }

        val rate = rateStr.toDoubleOrNull()
        if (rate == null || rate <= 0.0) {
            _errorMessage.value = "Please enter a valid hourly rate (e.g. 50)."
            _statusMessage.value = null
            return false
        }

        val slots = slotsStr.toIntOrNull() ?: 1
        if (slots <= 0) {
            _errorMessage.value = "Total slots must be at least 1."
            _statusMessage.value = null
            return false
        }

        val newListing = HostListing(
            id = "host-spot-${UUID.randomUUID().toString().take(6)}",
            title = title.trim(),
            address = address.trim(),
            hourlyRate = rate,
            totalSlots = slots,
            hasEvCharging = hasEv,
            isActive = true
        )

        listings.add(0, newListing)
        _errorMessage.value = null
        _statusMessage.value = "Space \"${newListing.title}\" published successfully!"
        return true
    }

    /**
     * Toggles the active/inactive booking status for a specific listing in real time.
     *
     * @param listingId Identifier of the host listing
     */
    fun toggleListingStatus(listingId: String) {
        val index = listings.indexOfFirst { it.id == listingId }
        if (index != -1) {
            val current = listings[index]
            val updated = current.copy(isActive = !current.isActive)
            listings[index] = updated
            _statusMessage.value = "${updated.title} is now ${if (updated.isActive) "Active" else "Inactive"}."
        }
    }

    /**
     * Clears banner alerts.
     */
    fun clearFeedback() {
        _errorMessage.value = null
        _statusMessage.value = null
    }
}
