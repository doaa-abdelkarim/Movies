package com.example.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import com.example.movies.presentation.navigation.SetupNavGraph
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.ui.theme.black
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = black.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = black.toArgb()
            )
        )

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = false
        setContent {
            MoviesTheme(dynamicColor = false) {
                SetupNavGraph()
            }
        }
    }
}