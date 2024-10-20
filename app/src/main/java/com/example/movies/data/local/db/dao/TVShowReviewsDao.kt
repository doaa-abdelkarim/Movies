package com.example.movies.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.videos.tvshows.LocalTVShowReview

@Dao
interface TVShowReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<LocalTVShowReview>)

    @Query("SELECT * FROM tv_show_reviews_table WHERE videoId = :tvShowId")
    fun getReviews(tvShowId: Int): PagingSource<Int, LocalTVShowReview>

    @Query("DELETE FROM tv_show_reviews_table")
    suspend fun clearReviews()

}