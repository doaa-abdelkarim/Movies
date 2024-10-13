package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.movie.DatabaseMovieReview

@Dao
interface MovieReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<DatabaseMovieReview>)

    @Query("SELECT * FROM movie_review_table WHERE videoId = :movieId")
    fun getAllReviews(movieId: Int): List<DatabaseMovieReview>

}