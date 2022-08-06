package org.techtown.showbook.bookinfo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.techtown.showbook.bookinfo.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM review WHERE id==:id")
    fun getOnReview(id:Int): Review

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun saveReview(review: Review)
}