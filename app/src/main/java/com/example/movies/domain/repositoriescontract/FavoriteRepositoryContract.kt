package com.example.movies.domain.repositoriescontract

import com.example.movies.data.local.models.favorites.DatabaseFavorite
import com.example.movies.domain.models.Video

interface FavoriteRepositoryContract {
    suspend fun cacheFavorites(databaseFavorite: DatabaseFavorite)
    suspend fun getAllFavorites(): List<Video>
}

interface FavoriteMoviesRepositoryContract: FavoriteRepositoryContract
interface OfflineFavoriteMovies : FavoriteMoviesRepositoryContract

interface FavoriteTVShowsRepositoryContract: FavoriteRepositoryContract
interface OfflineFavoriteTVShows : FavoriteTVShowsRepositoryContract