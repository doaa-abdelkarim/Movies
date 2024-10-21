package com.example.movies.data.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movies.data.local.models.videos.tvshows.LocalTVShow

@Dao
interface TVShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShows: List<LocalTVShow>)

    @Update
    suspend fun update(localTVShow: LocalTVShow)

    /*
    I added this field to fix "RemoteMediator calls API again and again" issue according to answer
    suggested in this link until I find better solution to this issue
    https://stackoverflow.com/a/76556967
     */
    @Query("SELECT * FROM tv_shows_table Order by createdAt ASC")
    fun getAllTVShows(): PagingSource<Int, LocalTVShow>

    @Query("SELECT * FROM tv_shows_table where id = :id")
    suspend fun getTVShowById(id: Int): LocalTVShow

    @Query("DELETE FROM tv_shows_table")
    suspend fun clearTVShows()
}