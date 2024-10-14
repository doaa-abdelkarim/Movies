package com.example.movies.data.local.db.dao

import androidx.room.*
import com.example.movies.data.local.models.videos.movies.LocalMovie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<LocalMovie>)

    @Update
    suspend fun update(localMovie: LocalMovie)

    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies(): List<LocalMovie>

    @Query("SELECT * FROM movie_table where id = :id")
    suspend fun getMovieById(id: Int): LocalMovie
}