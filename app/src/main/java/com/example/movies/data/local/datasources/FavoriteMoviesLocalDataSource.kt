package com.example.movies.data.local.datasources

import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseFavoriteRepository

interface BaseFavoriteMoviesLocalDataSource : BaseFavoriteRepository

class FavoriteMoviesLocalDataSource(
    private val favoriteMoviesDao: FavoriteMoviesDao
) : BaseFavoriteMoviesLocalDataSource {

    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        favoriteMoviesDao.insert(baseLocalFavorite as LocalFavoriteMovie)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteMoviesDao.getAllFavoritesMovie().asDomainModel()
    }

}