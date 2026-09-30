package com.parkshare.app.feature_discovery

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkshare.app.ui.theme.ParkshareTheme

/**
 * Driver Discovery & Search Screen.
 * Displays searchable parking spots, filter chips for EV charging and budget,
 * and cards for each available spot with selection callback.
 *
 * Assigned Developer: Sudhanshu (Module 2)
 */
@Composable
fun DiscoveryScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoveryViewModel = viewModel(),
    onSpotSelected: (ParkingSpot) -> Unit = {},
    onBackToAuth: () -> Unit = {}
) {
    val spots by viewModel.parkingSpots
    val searchQuery by viewModel.searchQuery
    val onlyEv by viewModel.onlyEv
    val under50 by viewModel.under50

    BackHandler {
        onBackToAuth()
    }

    DiscoveryContent(
        spots = spots,
        searchQuery = searchQuery,
        onlyEv = onlyEv,
        under50 = under50,
        onSearchChanged = { viewModel.searchByQuery(it) },
        onEvToggled = { viewModel.toggleEvFilter(it) },
        onUnder50Toggled = { viewModel.toggleUnder50Filter(it) },
        onSpotSelected = onSpotSelected,
        onBackToAuth = onBackToAuth,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryContent(
    spots: List<ParkingSpot>,
    searchQuery: String,
    onlyEv: Boolean,
    under50: Boolean,
    onSearchChanged: (String) -> Unit,
    onEvToggled: (Boolean) -> Unit,
    onUnder50Toggled: (Boolean) -> Unit,
    onSpotSelected: (ParkingSpot) -> Unit,
    onBackToAuth: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                tonalElevation = 3.dp,
                shadowElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Find Parking",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )

                        IconButton(onClick = onBackToAuth) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile / Account",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Location Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChanged,
                        placeholder = { Text("Search locality, street, or landmark...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchChanged("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear Search"
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Filter Chips Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = onlyEv,
                            onClick = { onEvToggled(!onlyEv) },
                            label = { Text("EV Charging ⚡") },
                            leadingIcon = {
                                if (onlyEv) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        )

                        FilterChip(
                            selected = under50,
                            onClick = { onUnder50Toggled(!under50) },
                            label = { Text("Under ₹50/hr") },
                            leadingIcon = {
                                if (under50) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.CurrencyRupee,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (spots.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "No Results",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No Parking Spots Found",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Try adjusting your search query or filter tags.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "${spots.size} spots available near you",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                items(items = spots, key = { it.id }) { spot ->
                    ParkingSpotCard(
                        spot = spot,
                        onClick = { onSpotSelected(spot) }
                    )
                }
            }
        }
    }
}

/**
 * Material 3 Card component representing an individual parking spot.
 */
@Composable
fun ParkingSpotCard(
    spot: ParkingSpot,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = spot.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                // Availability Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (spot.isAvailable) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                ) {
                    Text(
                        text = if (spot.isAvailable) "Available" else "Occupied",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                        color = if (spot.isAvailable) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Address formatted badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = spot.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Badges row: Price Tag and EV Support Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Price Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "₹${spot.pricePerHour.toInt()}/hr",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    // EV Badge
                    if (spot.hasEvCharging) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = "EV Charging",
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "EV ⚡",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Select Spot →",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoveryScreenPreview() {
    ParkshareTheme {
        DiscoveryContent(
            spots = listOf(
                ParkingSpot(
                    id = "preview-1",
                    title = "Dadar Flower Market Covered Parking",
                    address = "Senapati Bapat Marg, Dadar West, Mumbai",
                    pricePerHour = 40.0,
                    hasEvCharging = true,
                    isAvailable = true
                ),
                ParkingSpot(
                    id = "preview-2",
                    title = "Matunga Central Residency Bay",
                    address = "Lakhamsi Napoo Rd, Matunga East, Mumbai",
                    pricePerHour = 35.0,
                    hasEvCharging = false,
                    isAvailable = true
                )
            ),
            searchQuery = "",
            onlyEv = false,
            under50 = false,
            onSearchChanged = {},
            onEvToggled = {},
            onUnder50Toggled = {},
            onSpotSelected = {}
        )
    }
}
