package com.example.movies.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movies.data.local.models.LocalMovie

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<LocalMovie>)

    @Update
    suspend fun update(localMovie: LocalMovie)

    @Query("SELECT * FROM movies_table WHERE isMovie = 1 ORDER BY pk ASC")
    fun getAllMovies(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies_table WHERE isMovie = 0 ORDER BY pk ASC")
    fun getAllTVShows(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies_table where id = :id")
    suspend fun getMovieById(id: Int): LocalMovie

    @Query("DELETE FROM movies_table WHERE isMovie = 1")
    suspend fun clearMovies()

    @Query("DELETE FROM movies_table WHERE isMovie = 0")
    suspend fun clearTVShows()
}