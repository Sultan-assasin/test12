package com.example.test12.navigation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test12.AnimatedSplashScreen
import com.example.test12.R
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController = navController)

        }
        composable(route = Screen.Test.route) {
            TestScreen()
            analytics()
        }
        composable(route = Screen.WebView.route) {
            WebViewPage(url = "https://www.google.kz")
        }
    }


}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(url: String) {

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()

                // to play video on a web view
                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {

                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        backEnabled = view.canGoBack()
                    }

                }

                loadUrl(url)
                webView = this
            }
        }, update = {
            webView = it
        })


    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

}

@Composable
fun analytics() {

    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(LocalContext.current)
}

@Composable
fun TestScreen() {
    var buttonText by remember { mutableStateOf("Hello") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = buttonText)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.image1),
            contentDescription = "Image 1",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.image1),
            contentDescription = "Image 2",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.image1),
            contentDescription = "Image 3",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { buttonText = "Hello button"  }) {
            Text(text = "Click me")
        }
    }
}

@Preview
@Composable
fun PreviewTestScreen() {
    TestScreen()
}