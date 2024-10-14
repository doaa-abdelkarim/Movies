package com.example.movies.data.repositories

import com.example.movies.data.local.db.dao.FavoriteMoviesDao
import com.example.movies.data.local.models.favorites.BaseLocalFavorite
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.videos.movies.asDomainModel
import com.example.movies.domain.entities.Video
import com.example.movies.domain.repositories.BaseOfflineFavoriteMoviesRepository
import com.example.movies.domain.repositories.BaseOnlineFavoriteMoviesRepository

class OnlineFavoriteMoviesRepository(
    private val baseOfflineFavoriteMoviesRepository: BaseOfflineFavoriteMoviesRepository
) : BaseOnlineFavoriteMoviesRepository {
    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        return baseOfflineFavoriteMoviesRepository.cacheFavorites(baseLocalFavorite)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return baseOfflineFavoriteMoviesRepository.getAllFavorites()
    }
}

class OfflineFavoriteMoviesRepository(
    private val favoriteMoviesDao: FavoriteMoviesDao
) : BaseOfflineFavoriteMoviesRepository {

    override suspend fun cacheFavorites(baseLocalFavorite: BaseLocalFavorite) {
        favoriteMoviesDao.insert(baseLocalFavorite as LocalFavoriteMovie)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteMoviesDao.getAllFavoritesMovie().asDomainModel()
    }

}