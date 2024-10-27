package com.example.movies.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.movies.data.local.models.videos.movies.LocalMovie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<LocalMovie>)

    @Update
    suspend fun update(localMovie: LocalMovie)

    @Query("SELECT * FROM movies_table Order by pk asc")
    fun getAllMovies(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies_table where id = :id")
    suspend fun getMovieById(id: Int): LocalMovie

    @Query("DELETE FROM movies_table")
    suspend fun clearMovies()
}