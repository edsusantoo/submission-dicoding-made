package com.edsusantoo.core.data

import android.content.Context
import android.util.Log
import com.edsusantoo.core.data.source.local.LocalDataSource
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import com.edsusantoo.core.data.source.remote.RemoteDataSource
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.repository.IMoviedRepository
import com.edsusantoo.core.utils.AppExecutors
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.core.utils.DataMapper
import com.edsusantoo.core.utils.MoviedUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviedRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext private val context: Context,
    private val appExecutors: AppExecutors,
) : IMoviedRepository {
    private val compositeDisposableRemote = CompositeDisposable()
    private val compositeDisposableLocal = CompositeDisposable()
    override fun getMovies(type: String): Flowable<Resource<List<Movie>>> {
        return when (type) {
            Constants.TYPE_MOVIE_POPULAR -> {
                setMovieNetworkBoundResource(Constants.TYPE_MOVIE_POPULAR)
            }
            Constants.TYPE_MOVIE_NOW_PLAYING -> {
                setMovieNetworkBoundResource(Constants.TYPE_MOVIE_NOW_PLAYING)
            }
            Constants.TYPE_MOVIE_UPCOMING -> {
                setMovieNetworkBoundResource(Constants.TYPE_MOVIE_UPCOMING)
            }
            else -> throw IllegalStateException("Invalid rating param value")
        }
    }

    override fun getDetailMovie(id: String,type: String): Flowable<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, DetailMovieResponse>(){
            override fun loadFromDB(): Flowable<Movie> {
                return localDataSource.getDetailMovie(id).map { DataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(id)
            }

            override fun saveCallResultToDB(data: DetailMovieResponse) {
                val mapper = DataMapper.mapMovieResponseToEntities(data,type)
                localDataSource.updateDetailMovie(mapper,data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getCastMovie(id: String): Flowable<Resource<List<Cast>>> {
        return object : NetworkBoundResource<List<Cast>, CastResponse>(){
            override fun loadFromDB(): Flowable<List<Cast>> {
                return localDataSource.getCastMovie(id).map { DataMapper.mapListCastEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Cast>?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<CastResponse>> {
                return remoteDataSource.getCastMovie(id)
            }

            override fun saveCallResultToDB(data: CastResponse) {
                val mapper = DataMapper.mapCastResponseToEntities(data)
                localDataSource.insertCast(mapper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }.asFlowable()
    }

    override fun getRelatedMovie(id: String): Flowable<Resource<List<Movie>>> {
        val result = PublishSubject.create<Resource<List<Movie>>>()
        result.onNext(Resource.Loading())
        val client = remoteDataSource.getRelatedMovie(id)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete{compositeDisposableRemote.dispose()}
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val mapper = DataMapper.mapListMovieResponseToDomain(response.data)
                        result.onNext(Resource.Success(mapper))
                    }
                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(response.error))
                    }
                    else -> Log.e("Network Error","ERROR YA GES YA!!")
                }

            }
        compositeDisposableRemote.add(client)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun setFavoriteMovie(favorite: Favorite){
        val mapper = DataMapper.mapFavoriteMovieDomainToEntities(favorite)
        appExecutors.diskIO().execute {  localDataSource.setFavoriteMovie(mapper) }
    }

    override fun getLocalFavoriteMovie(id: String): Flowable<Resource<MovieFavorite>> {
        val result = PublishSubject.create<Resource<MovieFavorite>>()
        val local = localDataSource.getFavoriteDetailMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete{compositeDisposableLocal.dispose()}
            .subscribe({movie->
                result.onNext(Resource.Success(movie))
            },{error ->
                result.onNext(Resource.Error(error.message))
            })
        compositeDisposableLocal.add(local)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun setMovieNetworkBoundResource(typeMovie:String):Flowable<Resource<List<Movie>>>{
        return object : NetworkBoundResource<List<Movie>,ListMovieResponse>(){
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getAllMovieWhereType(typeMovie)
                    .map { DataMapper.mapListMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<ListMovieResponse>> {
                return when(typeMovie){
                    Constants.TYPE_MOVIE_POPULAR -> {
                        remoteDataSource.getMoviePopular()
                    }
                    Constants.TYPE_MOVIE_NOW_PLAYING -> {
                        remoteDataSource.getNowPlayingMovie()
                    }
                    Constants.TYPE_MOVIE_UPCOMING -> {
                        remoteDataSource.getUpComingMovie()
                    }
                    else  -> throw IllegalStateException("Invalid rating param value")
                }
            }

            override fun saveCallResultToDB(data: ListMovieResponse) {
                val mapper = DataMapper.mapListMovieResponseToEntities(data,typeMovie)

                localDataSource.insertMovie(mapper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }.asFlowable()
    }

}