package com.edsusantoo.core.data.source.local

import com.edsusantoo.core.data.source.local.dao.CastDao
import com.edsusantoo.core.data.source.local.dao.MovieDao
import com.edsusantoo.core.data.source.local.dao.UserDao
import com.edsusantoo.core.data.source.local.entity.CastEntity
import com.edsusantoo.core.data.source.local.entity.FavoriteEntity
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.UserEntity
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import com.edsusantoo.core.domain.model.movie.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val movieDao: MovieDao,
    private val castDao:CastDao
) {

    fun searchMovie(query: String): Flowable<List<MovieEntity>> = movieDao.searchMovie(query)

    fun getAllMovieWhereType(typeMovie: String) = movieDao.getAllMovieWhereType(typeMovie)

    fun getDetailMovie(idMovie: String): Flowable<MovieEntity> = movieDao.getDetailMovie(idMovie)

    fun insertMovie(movie: List<MovieEntity>): Completable = movieDao.insertMovie(movie)

    fun setFavoriteMovie(favorite: FavoriteEntity) =
        movieDao.insertFavorite(favorite)


    fun updateDetailMovie(movieEntity: MovieEntity, movieResponse: Movie): Completable {
        movieEntity.budget = movieResponse.budget.toString()
        movieEntity.genres = movieResponse.genres
        movieEntity.productionCompanies = movieResponse.productionCompanies
        movieEntity.productionCountries = movieResponse.productionCountries
        movieEntity.runtime = movieResponse.runtime.toString()
        movieEntity.tagline = movieResponse.tagline
        return movieDao.updateMovie(movieEntity)
    }

    fun getFavoriteDetailMovie(idMovie: String): Flowable<MovieFavorite> =
        movieDao.getFavoriteDetailMovie(idMovie)

    fun getFavoriteMovie(): Flowable<List<MovieFavorite>> = movieDao.getFavoriteMovie()

    fun getCastMovie(idMovie: String): Flowable<List<CastEntity>> = castDao.getCastMovie(idMovie)

    fun insertCast(cast: List<CastEntity>): Completable = castDao.insertCast(cast)

    fun getUser(email: String): Single<UserEntity> = userDao.getUser(email)

    fun insertUser(userEntity: UserEntity): Single<Long> = userDao.insert(userEntity)

    fun getAllUser(): Flowable<List<UserEntity>> = userDao.getAllUser()

    fun updateUser(userEntity: UserEntity): Single<Int> = userDao.updateUser(userEntity)

    fun isLogin(): Single<UserEntity> = userDao.isLogin()
}