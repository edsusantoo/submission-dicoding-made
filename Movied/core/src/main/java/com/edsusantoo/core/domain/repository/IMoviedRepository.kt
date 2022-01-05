package com.edsusantoo.core.domain.repository

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite
import com.edsusantoo.core.domain.model.user.User
import io.reactivex.Flowable
import io.reactivex.Single

interface IMoviedRepository {
    fun getMovies(type: String): Flowable<Resource<List<Movie>>>
    fun getDetailMovie(id: String, type: String): Flowable<Resource<Movie>>
    fun getCastMovie(id: String): Flowable<Resource<List<Cast>>>
    fun getRelatedMovie(id: String): Flowable<Resource<List<Movie>>>
    fun setFavoriteMovie(favorite: Favorite)
    fun getLocalDetailFavoriteMovie(id: String): Flowable<MovieFavorite>
    fun searchMovie(query: String): Flowable<Resource<List<Movie>>>
    fun getLocalFavoriteMovie(): Flowable<List<MovieFavorite>>
    fun insertUser(username: String, email: String, password: String): Flowable<Resource<Long>>
    fun getUser(email: String): Single<User>
    fun getAllUser(): Flowable<Resource<List<User>>>
    fun updateUser(user: User): Flowable<Int>
}