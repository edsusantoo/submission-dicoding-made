package com.edsusantoo.core.data.source.local.dao

import androidx.room.*
import com.edsusantoo.core.data.source.local.entity.FavoriteEntity
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE type_movie = :typeMovie")
    fun getAllMovieWhereType(typeMovie: String): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE original_title LIKE '%' || :originalTitle || '%'")
    fun searchMovie(originalTitle: String): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id_movie = :idMovie")
    fun getDetailMovie(idMovie: String): Flowable<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: List<MovieEntity>): Completable

    @Update
    fun updateMovie(movie: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM movie INNER JOIN favorite ON favorite.id_favorite = id_movie WHERE id_movie = :idMovie")
    fun getFavoriteDetailMovie(idMovie: String):Flowable<MovieFavorite>

    @Query("SELECT * FROM movie INNER JOIN favorite ON favorite.id_favorite = id_movie WHERE favorite = 1")
    fun getFavoriteMovie():Flowable<List<MovieFavorite>>
}