package com.parkshare.app.feature_auth

/**
 * User roles available within the Parkshare platform.
 * DRIVER: Users seeking and booking parking spaces.
 * HOST: Users listing and managing parking spaces.
 */
enum class UserRole {
    DRIVER,
    HOST
}

/**
 * Data class representing the active user session state.
 *
 * @property email The authenticated user's email address.
 * @property role The current active role (DRIVER or HOST).
 * @property isAuthenticated Flag indicating whether the user is successfully authenticated.
 */
data class UserSession(
    val email: String = "",
    val role: UserRole = UserRole.DRIVER,
    val isAuthenticated: Boolean = false
)
