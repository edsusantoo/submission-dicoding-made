package com.edsusantoo.core.domain.repository

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import io.reactivex.Flowable

interface IMoviedRepository {
    fun getMovies(type:String):Flowable<Resource<List<Movie>>>
    fun getDetailMovie(id:String,type:String):Flowable<Resource<Movie>>
    fun getCastMovie(id:String):Flowable<Resource<List<Cast>>>
    fun getRelatedMovie(id:String):Flowable<Resource<List<Movie>>>
    fun setFavoriteMovie(favorite: Favorite)
    fun getLocalFavoriteMovie(id: String): Flowable<Resource<MovieFavorite>>
    fun searchMovie(query: String): Flowable<Resource<List<Movie>>>
}