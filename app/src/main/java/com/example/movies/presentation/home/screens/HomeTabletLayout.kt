package com.example.movies.presentation.home.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.movies.R
import com.example.movies.domain.entities.Favorite
import com.example.movies.presentation.home.widgets.Movies
import com.example.movies.presentation.home.widgets.PageFavorites
import com.example.movies.ui.theme.gray200
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.regularSize14White
import com.example.movies.ui.theme.transparent
import com.example.movies.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabletLayout(
    favorites: List<Favorite>,
    navigateToMoviePlayerScreen: (String) -> Unit,
) {
    val drawerMenu = listOf(
        stringResource(R.string.popular_movies),
        stringResource(R.string.popular_tv_shows),
        stringResource(R.string.favorites),
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = gray700,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f)
                        .background(gray200)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(all = dimensionResource(R.dimen.spacing_normal))
                            .align(Alignment.BottomStart),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = stringResource(R.string.app_logo)
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = dimensionResource(R.dimen.spacing_small)),
                            text = stringResource(R.string.app_name),
                            style = regularSize14White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_normal)))
                drawerMenu.forEachIndexed { index, menuItem ->
                    if(index == 2)
                        Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_large)))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding), //padding between items
                        label = {
                            Text(
                                text = menuItem,
                                style = regularSize14White
                            )
                        },
                        icon = if (index == 2) {
                            {
                                Image(
                                    painter = painterResource(R.drawable.ic_star),
                                    contentDescription = menuItem
                                )
                            }
                        } else null,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = gray200,
                            unselectedContainerColor = transparent
                        ),
                        selected = index == selectedItemIndex,
                        onClick = {
                            //  navController.navigate(menuItem.route)

                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = { //TopBar to show title
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.movies))
                    },
                    colors = topAppBarColors(
                        containerColor = gray700,
                        titleContentColor = white,
                        navigationIconContentColor = white
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(  //Show Menu Icon on TopBar
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            when (selectedItemIndex) {
                0 -> Movies(
                    innerPadding = innerPadding,
                    navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
                )

                1 -> Text("")
                else -> PageFavorites(favorites = favorites)
            }
        }
    }
}