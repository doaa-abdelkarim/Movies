package com.example.movies.presentation.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.TabsMain
import com.example.movies.presentation.home.widgets.PageFavorites
import com.example.movies.presentation.home.widgets.PageMovies
import com.example.movies.presentation.home.widgets.PageTVShows
import java.util.Locale

@Composable
fun HomePhoneLayout(
    favorites: List<Favorite>,
    navigateToDetailsScreen: (Movie) -> Unit
) {
    val tabsTitles = mutableListOf(
        stringResource(R.string.movies).uppercase(Locale.ROOT),
        stringResource(R.string.tv_shows).uppercase(Locale.ROOT),
    ).apply {
        if (favorites.isNotEmpty())
            this.add(stringResource(R.string.favorites).uppercase(Locale.ROOT))
    }
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabsMain(
                tabsTitles = tabsTitles,
                selectedTabIndex = selectedTabIndex,
                pagerState = pagerState
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> PageMovies(navigateToDetailsScreen = navigateToDetailsScreen)
                    1 -> PageTVShows(navigateToDetailsScreen = navigateToDetailsScreen)
                    else -> PageFavorites(favorites = favorites, innerPadding = innerPadding)
                }
            }
        }
    }
}