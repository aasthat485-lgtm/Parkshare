# Parkshare - Peer-to-Peer Parking Android Application

Parkshare is a modern Android application built using **Kotlin**, **Jetpack Compose (Material Design 3)**, and Android Architecture Components, designed to facilitate peer-to-peer parking reservations and host listings.

## Modular Architecture & Developer Assignments

The project is structured into modular feature packages under `com.parkshare.app`:

- `com.parkshare.app.feature_auth` - **Module 1: Auth & User Profile System** *(Assigned: Abhijay)*
  - `AuthModels.kt`: Role definitions (`DRIVER`, `HOST`) and `UserSession` state model.
  - `AuthViewModel.kt`: StateFlow-driven session lifecycle, credentials validation, and authentication handlers.
  - `AuthScreen.kt`: Material 3 Compose UI with role selection `FilterChip` components, credential inputs, and authenticated session dashboard.
- `com.parkshare.app.feature_discovery` - **Module 2: Discovery & Search System** *(Assigned: Sudhanshu)*
  - Discovery and map-based parking spot locator.
- `com.parkshare.app.feature_host` - **Module 3: Host Listing & Management** *(Assigned: Aastha)*
  - Parking spot listing, pricing, and availability management.
- `com.parkshare.app.feature_booking` - **Module 4: Booking & QR Ticket System** *(Assigned: Ashmit)*
  - Reservation checkout and QR generation powered by ZXing.

## Tech Stack & Dependencies

- **Language:** Kotlin 1.9.24
- **UI Toolkit:** Jetpack Compose with Material Design 3 (`androidx.compose.material3:material3`)
- **Architecture:** MVVM / MVI with `StateFlow` and Compose Lifecycle (`androidx.lifecycle:lifecycle-viewmodel-compose`)
- **Navigation:** Jetpack Navigation Compose (`androidx.navigation:navigation-compose`)
- **QR Code Engine:** ZXing Core (`com.google.zxing:core:3.5.3`)
- **Build System:** Gradle (Kotlin DSL) with Android Gradle Plugin 8.4+

## Getting Started

1. Open this project directory in **Android Studio Hedgehog / Iguana / Jellyfish** or newer.
2. Allow Gradle sync to resolve all dependencies.
3. Run on an Android device or emulator running API 26 or higher.
