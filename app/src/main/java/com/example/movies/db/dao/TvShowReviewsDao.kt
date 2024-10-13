package com.example.movies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.tvshow.DatabaseTVShowReview

@Dao
interface TvShowReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<DatabaseTVShowReview>)

    @Query("SELECT * FROM tv_show_review_table WHERE videoId = :tvShowId")
    fun getAllReviews(tvShowId: Int): List<DatabaseTVShowReview>

}