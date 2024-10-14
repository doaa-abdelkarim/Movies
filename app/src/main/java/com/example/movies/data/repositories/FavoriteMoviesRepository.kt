package com.example.movies.data.repositories

import com.example.movies.data.local.datasources.BaseFavoriteMoviesLocalDataSource
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository

class FavoriteMoviesRepository(
    private val baseFavoriteMoviesLocalDataSource: BaseFavoriteMoviesLocalDataSource
) : BaseFavoriteRepository {
    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        return baseFavoriteMoviesLocalDataSource.cacheFavorites(baseLocalFavorite)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return baseFavoriteMoviesLocalDataSource.getAllFavorites()
    }
}

