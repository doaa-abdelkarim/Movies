package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.favorites.DatabaseFavoriteMovie
import com.example.movies.data.local.models.videos.movie.DatabaseMovie

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseFavoriteMovie: DatabaseFavoriteMovie)

    @Query("SELECT m.id, m.posterPath, m.title  FROM movie_table m JOIN favorite_movies_table f ON m.id = f.videoId ORDER BY f.id")
    fun getAllFavoritesMovie(): List<DatabaseMovie>

}