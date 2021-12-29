package com.edsusantoo.core.domain.repository

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.domain.model.movie.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface IMoviedRepository {
    fun getMovies(type:String):Flowable<Resource<List<Movie>>>
    fun getDetailMovie(id:String,type:String):Flowable<Resource<Movie>>
}