package com.example.test12.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Test : Screen("home_screen")
    object WebView : Screen("webView_screen")
}