package com.edsusantoo.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import com.edsusantoo.core.data.source.remote.config.ApiService
import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.video.Result
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.MoviedUtils
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@SuppressLint("CheckResult")
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    fun getMoviePopular(): Flowable<ApiResponse<ListMovieResponse>> {
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()

        val client = apiService.getMoviePopular()
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source", error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getNowPlayingMovie(): Flowable<ApiResponse<ListMovieResponse>> {
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()

        val client = apiService.getNowPlayingMovie()
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source", error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getUpComingMovie(): Flowable<ApiResponse<ListMovieResponse>> {
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()

        val client = apiService.getUpComingMovie()
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source", error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(id: String, type: String): Flowable<ApiResponse<Movie>> {
        val result = PublishSubject.create<ApiResponse<Movie>>()
        val detail = apiService.getDetailMovie(id)
        val video = apiService.getVideoMovie(id)
        Flowable.zip(
            detail, video,
            BiFunction { detailResponse, videoResponse ->
                val videoArrayFirst = ArrayList<Result>()
                if (videoResponse.results.isNotEmpty()) {
                    videoResponse.results.mapIndexed { index, result ->
                        when (index) {
                            0 -> videoArrayFirst.add(
                                Result(
                                    id = result.id,
                                    iso6391 = result.iso6391,
                                    iso31661 = result.iso31661,
                                    name = result.name,
                                    key = result.key,
                                    official = result.official,
                                    publishedAt = result.publishedAt,
                                    site = result.site,
                                    size = result.size,
                                    type = result.site
                                )
                            )
                            else -> {
                            }
                        }
                    }
                }

                return@BiFunction Movie(
                    idMovie = detailResponse.id.toString(),
                    backdropPath = detailResponse.backdropPath,
                    budget = detailResponse.budget.toString(),
                    originalTitle = detailResponse.originalTitle,
                    overview = detailResponse.overview,
                    posterPath = detailResponse.posterPath,
                    releaseDate = detailResponse.releaseDate,
                    voteAverage = detailResponse.voteAverage.toString(),
                    typeMovie = type,
                    productionCompanies = MoviedUtils.convertToStringJson(detailResponse.productionCompanies),
                    genres = MoviedUtils.convertToStringJson(detailResponse.genres),
                    productionCountries = MoviedUtils.convertToStringJson(detailResponse.productionCountries),
                    runtime = MoviedUtils.convertToStringJson(detailResponse.runtime),
                    tagline = detailResponse.tagline,
                    video = if (videoArrayFirst.isNotEmpty()) videoArrayFirst[0].key else "",
                    typeVideo = if (videoArrayFirst.isNotEmpty()) videoArrayFirst[0].type else ""
                )
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                ApiResponse.Error(error.message.toString())
                Log.e("Remote Data Source", error.toString())
            })
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getCastMovie(id: String): Flowable<ApiResponse<CastResponse>> {
        val result = PublishSubject.create<ApiResponse<CastResponse>>()
        val client = apiService.getCastMovie(id)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                ApiResponse.Error(error.message.toString())
                Log.e("Remote Data Source", error.toString())
            })
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getRelatedMovie(id: String): Flowable<ApiResponse<ListMovieResponse>> {
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()
        val client = apiService.getRelatedMovie(id)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source", error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun searchMovie(query: String): Flowable<ApiResponse<ListMovieResponse>> {
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()
        val client = apiService.searchMovie(query)
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source", error.toString())
            })
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }
}
