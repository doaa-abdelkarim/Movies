package com.example.movies.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.movies.MoviesApp
import com.example.movies.presentation.navigation.MoviesApp
import com.example.movies.ui.theme.MoviesTheme
import com.example.movies.ui.theme.black
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    @ApplicationContext
//    lateinit var appContext: Context
//    private val mainActivityViewModel: MainActivityViewModel by viewModels()

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
                MoviesApp()
            }
        }

//        observeState()
    }

//    private fun observeState() {
//        if ((appContext as MoviesApp).isLargeScreen)
//            lifecycleScope.launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    mainActivityViewModel.favorites.collect {
//                        favoritesItem.isVisible = it.isNotEmpty()
//                    }
//                }
//            }
//    }
}