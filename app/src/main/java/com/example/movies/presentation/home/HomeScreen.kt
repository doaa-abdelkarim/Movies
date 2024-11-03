package com.example.movies.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.domain.entities.Favorite
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.MainTabs

@Composable
fun HomeScreen(
    favorites: List<Favorite>,
    navigateToDetailsScreen: (Movie) -> Unit
) {
    val tabsTitles = mutableListOf(
        stringResource(R.string.movies),
        stringResource(R.string.tv_shows),
    ).apply {
        if (favorites.isNotEmpty())
            this.add(stringResource(R.string.favorites))
    }
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            MainTabs(
                tabsTitles = tabsTitles,
                selectedTabIndex = selectedTabIndex.value,
                pagerState = pagerState
            )
            HomeHorizontalPager(
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value,
                favorites = favorites,
                navigateToDetailsScreen = navigateToDetailsScreen
            )
        }
    }
}

@Composable
fun HomeHorizontalPager(
    pagerState: PagerState,
    selectedTabIndex: Int,
    navigateToDetailsScreen: (Movie) -> Unit,
    favorites: List<Favorite>
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) {
        when (selectedTabIndex) {
            0 -> Movies(navigateToDetailsScreen = navigateToDetailsScreen)
            1 -> TVShows(navigateToDetailsScreen = navigateToDetailsScreen)
            else -> Favorites()
        }
    }
}