package com.edsusantoo.core.data

import android.content.Context
import com.edsusantoo.core.data.source.local.LocalDataSource
import com.edsusantoo.core.data.source.remote.RemoteDataSource
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.repository.IMoviedRepository
import com.edsusantoo.core.utils.AppExecutors
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.core.utils.DataMapper
import com.edsusantoo.core.utils.MoviedUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviedRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    @ApplicationContext private val context: Context
) : IMoviedRepository {

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
                return data == null
            }

            override fun createCallNetwork(): Flowable<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(id).toFlowable()
            }

            override fun saveCallResultToDB(data: DetailMovieResponse) {
                val mapper = DataMapper.mapMovieResponseToEntities(data,type)
                appExecutors.diskIO().execute { localDataSource.updateDetailMovie(mapper,data) }
            }
        }.asFlowable()
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