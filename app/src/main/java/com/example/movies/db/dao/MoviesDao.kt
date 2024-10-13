package com.example.movies.db.dao

import androidx.room.*
import com.example.movies.data.local.models.videos.movie.DatabaseMovie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<DatabaseMovie>)

    @Update
    suspend fun update(databaseMovie: DatabaseMovie)

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): List<DatabaseMovie>

    @Query("SELECT * FROM movie_table where id = :id")
    fun getMovieById(id: Int): DatabaseMovie
}