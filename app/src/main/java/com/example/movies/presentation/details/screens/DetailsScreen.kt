package com.example.movies.presentation.details.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.R
import com.example.movies.domain.entities.Movie
import com.example.movies.presentation.common.SectionMovieDetails
import com.example.movies.ui.theme.strongPink
import com.example.movies.ui.theme.transparent
import com.example.movies.ui.theme.white
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    movie: Movie?,
    onAddToFavoriteClick: (Movie) -> Unit,
    navigateToMoviePlayerScreen: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                colors = topAppBarColors(
                    containerColor = transparent,
                    navigationIconContentColor = white
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.spacing_small),
                ),
                containerColor = transparent
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = buttonColors(
                        containerColor = strongPink
                    ),
                    shape = RoundedCornerShape(
                        corner = CornerSize(
                            size = 0.dp
                        )
                    ),
                    onClick = { movie?.let { onAddToFavoriteClick(it) } }
                ) {
                    Text(text = stringResource(R.string.add_to_favorites).uppercase(Locale.ROOT))
                }
            }
        }
    ) { innerPadding ->
        SectionMovieDetails(
            movie = movie,
            navigateToMoviePlayerScreen = navigateToMoviePlayerScreen
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        movie = Movie(
            pk = 1,
            id = 533535,
            posterPath = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
            title = "Deadpool & Wolverine",
            popularity = 1636.995,
            genres = null,
            originalLanguage = "en",
            overview = "A listless Wade Wilson toils away in civilian life with his days as the " +
                    "morally flexible mercenary, Deadpool, behind him. But when his homeworld " +
                    "faces an existential threat, Wade must reluctantly suit-up again with an " +
                    "even more reluctant Wolverine.",
            releaseDate = "2024-07-24",
            originalTitle = "Deadpool & Wolverine",
            revenue = 1336816112,
            isMovie = true
        ),
        onAddToFavoriteClick = {},
        navigateToMoviePlayerScreen = {},
        navigateBack = {}
    )
}