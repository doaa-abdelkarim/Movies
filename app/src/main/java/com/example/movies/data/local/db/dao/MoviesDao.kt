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

    /*
    I added this field to fix "RemoteMediator calls API again and again" issue according to answer
    suggested in this link until I find better solution to this issue
    https://stackoverflow.com/a/76556967
     */
    @Query("SELECT * FROM movies_table Order by createdAt ASC")
    fun getAllMovies(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM movies_table where id = :id")
    suspend fun getMovieById(id: Int): LocalMovie

    @Query("DELETE FROM movies_table")
    suspend fun clearMovies()
}