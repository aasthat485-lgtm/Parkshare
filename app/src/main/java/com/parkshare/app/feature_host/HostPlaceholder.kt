package com.parkshare.app.feature_host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Module 3: Host Listing & Management System
 * Assigned Developer: Aastha
 * Entry-point Composable for host space management and listing registration.
 */
@Composable
fun HostPlaceholder(
    modifier: Modifier = Modifier,
    onBackToAuth: () -> Unit = {}
) {
    HostScreen(
        modifier = modifier,
        onBackToAuth = onBackToAuth
    )
}
