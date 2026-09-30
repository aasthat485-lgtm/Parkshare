package com.parkshare.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkshare.app.feature_auth.AuthScreen
import com.parkshare.app.feature_auth.AuthViewModel
import com.parkshare.app.feature_auth.UserRole
import com.parkshare.app.feature_discovery.DiscoveryScreen
import com.parkshare.app.feature_host.HostScreen
import com.parkshare.app.ui.theme.ParkshareTheme

/**
 * Main application navigation destinations.
 */
enum class AppDestination {
    AUTH,
    DISCOVERY,
    HOST_DASHBOARD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParkshareTheme {
                val context = LocalContext.current
                val authViewModel: AuthViewModel = viewModel()
                var currentDestination by remember { mutableStateOf(AppDestination.AUTH) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentDestination) {
                        AppDestination.AUTH -> {
                            AuthScreen(
                                viewModel = authViewModel,
                                onAuthSuccess = { session ->
                                    // Role-based routing: HOST routes to HostScreen, DRIVER routes to DiscoveryScreen
                                    currentDestination = if (session.role == UserRole.HOST) {
                                        AppDestination.HOST_DASHBOARD
                                    } else {
                                        AppDestination.DISCOVERY
                                    }
                                }
                            )
                        }
                        AppDestination.DISCOVERY -> {
                            DiscoveryScreen(
                                onSpotSelected = { spot ->
                                    Toast.makeText(
                                        context,
                                        "Selected: ${spot.title} (₹${spot.pricePerHour.toInt()}/hr)",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onBackToAuth = {
                                    currentDestination = AppDestination.AUTH
                                }
                            )
                        }
                        AppDestination.HOST_DASHBOARD -> {
                            HostScreen(
                                onBackToAuth = {
                                    currentDestination = AppDestination.AUTH
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
