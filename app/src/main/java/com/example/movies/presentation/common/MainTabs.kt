package com.example.movies.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.movies.ui.theme.darkGrayishRed
import com.example.movies.ui.theme.darkerGray
import com.example.movies.ui.theme.teal700
import com.example.movies.ui.theme.white
import kotlinx.coroutines.launch

@Composable
fun MainTabs(
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