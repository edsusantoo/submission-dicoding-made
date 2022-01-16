package com.edsusantoo.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edsusantoo.core.data.source.local.entity.CastEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CastDao {
    @Query("SELECT * FROM `cast` WHERE id_movie = :idMovie")
    fun getCastMovie(idMovie: String): Flowable<List<CastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCast(movie: List<CastEntity>): Completable
}
