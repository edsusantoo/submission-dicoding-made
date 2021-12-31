package com.edsusantoo.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import com.edsusantoo.core.data.source.remote.config.ApiService
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
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
                Log.e("Remote Data Source",error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getNowPlayingMovie():Flowable<ApiResponse<ListMovieResponse>>{
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()

        val client = apiService.getNowPlayingMovie()
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            },{error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source",error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getUpComingMovie():Flowable<ApiResponse<ListMovieResponse>>{
        val result = PublishSubject.create<ApiResponse<ListMovieResponse>>()

        val client = apiService.getUpComingMovie()
        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                result.onNext(if (response != null) ApiResponse.Success(response) else ApiResponse.Empty)
            },{error ->
                result.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("Remote Data Source",error.toString())
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailMovie(id:String):Single<ApiResponse<DetailMovieResponse>>{
        val result = SingleSubject.create<ApiResponse<DetailMovieResponse>>()
        result.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                if(response!=null) ApiResponse.Success(response) else ApiResponse.Empty
            },{error ->
                ApiResponse.Error(error.message.toString())
                Log.e("Remote Data Source",error.toString())
            })
        return result
    }
}