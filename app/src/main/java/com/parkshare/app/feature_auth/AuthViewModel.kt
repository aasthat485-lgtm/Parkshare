package com.parkshare.app.feature_auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel managing authentication state and actions for the Parkshare application.
 * Developer: Abhijay (Module 1 - Auth & User Profile System)
 */
class AuthViewModel : ViewModel() {

    private val _sessionState = MutableStateFlow(UserSession())
    val sessionState: StateFlow<UserSession> = _sessionState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * Authenticates the user with email, password, and chosen role.
     *
     * @param email User's email address
     * @param password User's password
     * @param role User's role (DRIVER or HOST)
     * @return Boolean indicating whether authentication was accepted
     */
    fun login(email: String, password: String, role: UserRole): Boolean {
        if (email.isBlank() || !email.contains("@")) {
            _errorMessage.value = "Please enter a valid email address."
            return false
        }
        if (password.length < 6) {
            _errorMessage.value = "Password must be at least 6 characters."
            return false
        }

        _errorMessage.value = null
        _sessionState.update {
            it.copy(
                email = email.trim(),
                role = role,
                isAuthenticated = true
            )
        }
        return true
    }

    /**
     * Logs out the current user, clearing session state.
     */
    fun logout() {
        _errorMessage.value = null
        _sessionState.value = UserSession(
            email = "",
            role = UserRole.DRIVER,
            isAuthenticated = false
        )
    }

    /**
     * Clears any active error message.
     */
    fun clearError() {
        _errorMessage.value = null
    }
}
