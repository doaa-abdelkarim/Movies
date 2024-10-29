package com.example.movies.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.models.LocalReview

@Dao
interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reviews: List<LocalReview>)

    @Query("SELECT * FROM reviews_table WHERE movieId = :id")
    fun getReviews(id: Int): PagingSource<Int, LocalReview>

    @Query("DELETE FROM reviews_table WHERE movieId = :id")
    suspend fun clearReviews(id: Int)

}