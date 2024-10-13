package com.example.movies.data.repositoriesimpl

import com.example.movies.data.local.models.favorites.DatabaseFavorite
import com.example.movies.data.local.models.favorites.DatabaseFavoriteMovie
import com.example.movies.data.local.models.videos.movie.asDomainModel
import com.example.movies.db.dao.FavoriteMoviesDao
import com.example.movies.domain.models.Video
import com.example.movies.domain.repositoriescontract.FavoriteMoviesRepositoryContract
import com.example.movies.domain.repositoriescontract.OfflineFavoriteMovies

class FavoriteMoviesRepositoryImpl(private val offlineFavoriteMovies: OfflineFavoriteMovies) :
    FavoriteMoviesRepositoryContract {
    override suspend fun cacheFavorites(databaseFavorite: DatabaseFavorite){
        return offlineFavoriteMovies.cacheFavorites(databaseFavorite)
    }
    override suspend fun getAllFavorites(): List<Video> {
        return offlineFavoriteMovies.getAllFavorites()
    }
}

class OfflineFavoriteMoviesImpl(private val favoriteMoviesDao: FavoriteMoviesDao) :
    OfflineFavoriteMovies {

    override suspend fun cacheFavorites(databaseFavorite: DatabaseFavorite) {
        favoriteMoviesDao.insert(databaseFavorite as DatabaseFavoriteMovie)
    }

    override suspend fun getAllFavorites(): List<Video> {
        return favoriteMoviesDao.getAllFavoritesMovie().asDomainModel()
    }

}