package com.edsusantoo.core.data.source.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.edsusantoo.core.data.source.local.dao.MovieDao
import com.edsusantoo.core.data.source.local.dao.UserDao
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.UserEntity
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.utils.MoviedUtils
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource private constructor(
    private val userDao: UserDao,
    private val movieDao: MovieDao
){
    fun getAllMovie(): Flowable<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie():Flowable<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun getDetailMovie(idMovie:String):Flowable<MovieEntity> = movieDao.getDetailMovie(idMovie)

    fun insertMovie(movie:List<MovieEntity>): Completable = movieDao.insertMovie(movie)

    fun setFavoriteMovie(movie:MovieEntity,newState:Boolean){
        movie.isFavorite = newState
        movieDao.updateMovie(movie)
    }

    fun updateDetailMovie(movieEntity:MovieEntity,movieResponse: DetailMovieResponse){
        movieEntity.budget = movieResponse.budget.toString()
        movieEntity.genres = MoviedUtils.convertToStringJson(movieResponse.genres)
        movieEntity.productionCompanies = MoviedUtils.convertToStringJson(movieResponse.productionCompanies)
        movieEntity.productionCountries = MoviedUtils.convertToStringJson(movieResponse.productionCountries)
        movieEntity.runtime = movieResponse.runtime.toString()
        movieEntity.tagline = movieResponse.tagline
        movieDao.updateMovie(movieEntity)
    }

    fun getUser(username:String): Single<UserEntity> = userDao.getUser(username)

    fun insert(userEntity: UserEntity):Completable = userDao.insert(userEntity)
}