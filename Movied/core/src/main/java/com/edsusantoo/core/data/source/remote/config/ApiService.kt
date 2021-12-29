package com.edsusantoo.core.data.source.remote.config

import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("popular")
    fun getMoviePopular():Flowable<ListMovieResponse>

    @GET("now_playing")
    fun getNowPlayingMovie():Flowable<ListMovieResponse>

    @GET("upcoming")
    fun getUpComingMovie():Flowable<ListMovieResponse>

    @GET("{id_movie}")
    fun getDetailMovie(
        @Path("id") id:String
    ):Single<DetailMovieResponse>
}