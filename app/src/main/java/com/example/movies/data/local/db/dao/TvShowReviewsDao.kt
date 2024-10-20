package com.example.movies.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

@Dao
interface TvShowReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<LocalTVShowReview>)

    @Query("SELECT * FROM tv_show_reviews_table WHERE videoId = :tvShowId")
    suspend fun getReviews(tvShowId: Int): List<LocalTVShowReview>

    @Query("DELETE FROM tv_show_reviews_table")
    suspend fun clearReviews()

}