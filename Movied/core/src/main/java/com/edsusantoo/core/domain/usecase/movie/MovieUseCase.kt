package com.edsusantoo.core.domain.usecase.movie

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import io.reactivex.Flowable

interface MovieUseCase {
    fun getMovies(type:String):Flowable<Resource<List<Movie>>>
    fun getDetailMovie(id:String,type:String):Flowable<Resource<Movie>>
    fun getRelateMovie(id:String):Flowable<Resource<List<Movie>>>
    fun setFavoriteMovie(favorite: Favorite)
    fun getLocalFavoriteMovie(id:String):Flowable<Resource<MovieFavorite>>
}