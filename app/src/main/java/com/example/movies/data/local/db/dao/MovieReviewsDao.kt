package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.movies.LocalMovieReview

@Dao
interface MovieReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<LocalMovieReview>)

    @Query("SELECT * FROM movie_review_table WHERE videoId = :movieId")
    suspend fun getAllReviews(movieId: Int): List<LocalMovieReview>

}