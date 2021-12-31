package com.edsusantoo.core.data.source.local.dao

import androidx.room.*
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE type_movie = :typeMovie")
    fun getAllMovieWhereType(typeMovie: String): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE favorite = 1")
    fun getFavoriteMovie(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id_movie = :idMovie")
    fun getDetailMovie(idMovie: String): Flowable<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>): Completable

    @Update
    fun updateMovie(movie:MovieEntity)
}