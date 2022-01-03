package com.edsusantoo.core.data.source.remote.config

import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import io.reactivex.Flowable
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
        @Path("id_movie") id:String
    ):Flowable<DetailMovieResponse>

    @GET("{id_movie}/credits")
    fun getCastMovie(
        @Path("id_movie") id:String
    ):Flowable<CastResponse>

    @GET("{id_movie}/similar")
    fun getRelatedMovie(
        @Path("id_movie") id:String
    ):Flowable<ListMovieResponse>
}