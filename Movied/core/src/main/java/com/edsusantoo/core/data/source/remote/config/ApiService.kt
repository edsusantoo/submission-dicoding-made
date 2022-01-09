package com.edsusantoo.core.data.source.remote.config

import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.video.VideoResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMoviePopular():Flowable<ListMovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovie():Flowable<ListMovieResponse>

    @GET("movie/upcoming")
    fun getUpComingMovie():Flowable<ListMovieResponse>

    @GET("movie/{id_movie}")
    fun getDetailMovie(
        @Path("id_movie") id:String
    ):Flowable<DetailMovieResponse>

    @GET("movie/{id_movie}/credits")
    fun getCastMovie(
        @Path("id_movie") id: String
    ): Flowable<CastResponse>

    @GET("movie/{id_movie}/similar")
    fun getRelatedMovie(
        @Path("id_movie") id: String
    ): Flowable<ListMovieResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("query") query: String
    ): Flowable<ListMovieResponse>

    @GET("movie/{id_movie}/videos")
    fun getVideoMovie(
        @Path("id_movie") id: String
    ): Flowable<VideoResponse>
}