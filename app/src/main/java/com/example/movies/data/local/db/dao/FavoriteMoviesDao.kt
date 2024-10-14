package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.favorites.LocalFavoriteMovie
import com.example.movies.data.local.models.videos.movies.LocalMovie

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseFavoriteMovie: LocalFavoriteMovie)

    @Query("SELECT m.id, m.posterPath, m.title  FROM movie_table m JOIN favorite_movies_table f ON m.id = f.videoId ORDER BY f.id")
    suspend fun getAllFavoritesMovie(): List<LocalMovie>

}