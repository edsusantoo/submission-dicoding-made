package com.edsusantoo.core.data

import android.annotation.SuppressLint
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = PublishSubject.create<Resource<ResultType>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(value)) {
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.Success(value))
                }
            }
        compositeDisposable.add(db)
    }

    protected open fun onFetchFailed() {}
    protected abstract fun loadFromDB(): Flowable<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCallNetwork(): Flowable<ApiResponse<RequestType>>
    protected abstract fun saveCallResultToDB(data: RequestType)

    @SuppressLint("CheckResult")
    private fun fetchFromNetwork() {
        val apiResponse = createCallNetwork()
        result.onNext(Resource.Loading())
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete { compositeDisposable.dispose() }
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val dbSource = loadFromDB()

                        saveCallResultToDB(response.data)
                        dbSource.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDB()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            ?.take(1)?.subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(it))
                            }
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(Resource.Error(response.error, null))
                    }
                }
            }
        compositeDisposable.addAll(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}
