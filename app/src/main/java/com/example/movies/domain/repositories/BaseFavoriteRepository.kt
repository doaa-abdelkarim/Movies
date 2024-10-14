package com.example.movies.domain.repositories

import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.domain.entities.Video

interface BaseFavoriteRepository {
    suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite)
    suspend fun getAllFavorites(): List<Video>
}

interface BaseOnlineFavoriteMoviesRepository: BaseFavoriteRepository
interface BaseOfflineFavoriteMoviesRepository : BaseFavoriteRepository

interface BaseOnlineFavoriteTVShowsRepository: BaseFavoriteRepository
interface BaseOfflineFavoriteTVShowsRepository : BaseFavoriteRepository