package com.parkshare.app.feature_discovery

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Module 2: Discovery & Search System
 * Assigned Developer: Sudhanshu
 * Entry-point Composable for parking discovery and spot search.
 */
@Composable
fun DiscoveryPlaceholder(
    modifier: Modifier = Modifier,
    onSpotSelected: (ParkingSpot) -> Unit = {}
) {
    DiscoveryScreen(
        modifier = modifier,
        onSpotSelected = onSpotSelected
    )
}
