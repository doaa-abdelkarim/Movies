package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository

class FavoriteMoviesRepository(
    private val favoriteMoviesDao: FavoriteMoviesDao
) : BaseFavoriteRepository {
    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        favoriteMoviesDao.insert(baseLocalFavorite as LocalFavoriteMovie)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteMoviesDao.getAllFavoritesMovie().asDomainModel()
    }
}

