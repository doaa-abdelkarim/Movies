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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.ui.theme.darkGrayishRed
import com.example.movies.ui.theme.darkerGray
import com.example.movies.ui.theme.teal700
import com.example.movies.ui.theme.white
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navigateToDetailsScreen: (Movie) -> Unit) {
    val tabsTitles = stringArrayResource(R.array.tab_layout_home_titles)
    val pagerState = rememberPagerState(pageCount = { tabsTitles.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            HomeTabs(
                tabsTitles = tabsTitles,
                selectedTabIndex = selectedTabIndex.value,
                pagerState = pagerState
            )
            HomeHorizontalPager(
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value,
                navigateToDetailsScreen = navigateToDetailsScreen
            )
        }
    }
}

@Composable
fun HomeTabs(
    tabsTitles: Array<String>,
    selectedTabIndex: Int,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        containerColor = darkerGray,
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = teal700
                )
            }
        },
        divider = {}
    ) {
        tabsTitles.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = white,
                unselectedContentColor = darkGrayishRed,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = currentTab,
                    )
                },
            )
        }
    }
}

@Composable
fun HomeHorizontalPager(
    pagerState: PagerState,
    selectedTabIndex: Int,
    navigateToDetailsScreen: (Movie) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
//            .weight(1f)
    ) {
        if (selectedTabIndex == 0)
            MoviesScreen(navigateToDetailsScreen = navigateToDetailsScreen)
        else if(selectedTabIndex == 1)
            TVShowsScreen(navigateToDetailsScreen = navigateToDetailsScreen)
    }
}