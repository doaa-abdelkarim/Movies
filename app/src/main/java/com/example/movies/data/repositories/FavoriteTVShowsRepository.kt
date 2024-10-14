package com.example.movies.data.repositories

import com.example.movies.data.local.datasources.BaseFavoriteTVShowsLocalDataSource
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository

class FavoriteTVShowsRepository(
    private val baseFavoriteTVShowsLocalDataSource: BaseFavoriteTVShowsLocalDataSource
) : BaseFavoriteRepository {

    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        return baseFavoriteTVShowsLocalDataSource.cacheFavorites(baseLocalFavorite)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return baseFavoriteTVShowsLocalDataSource.getAllFavorites()
    }

}

