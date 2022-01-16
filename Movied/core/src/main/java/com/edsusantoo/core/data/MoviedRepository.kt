package com.edsusantoo.core.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.edsusantoo.core.data.source.local.LocalDataSource
import com.edsusantoo.core.data.source.remote.RemoteDataSource
import com.edsusantoo.core.data.source.remote.config.ApiResponse
import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.core.domain.repository.IMoviedRepository
import com.edsusantoo.core.utils.AppExecutors
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.core.utils.DataMapper
import com.edsusantoo.core.utils.MoviedUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviedRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext private val context: Context,
    private val appExecutors: AppExecutors,
    private val compositeDisposable: CompositeDisposable
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

    override fun getDetailMovie(id: String, type: String): Flowable<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, Movie>() {
            override fun loadFromDB(): Flowable<Movie> {
                return localDataSource.getDetailMovie(id)
                    .map { DataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: Movie?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<Movie>> {
                return remoteDataSource.getDetailMovie(id, type)
            }

            override fun saveCallResultToDB(data: Movie) {
                val mapper = DataMapper.mapMovieResponseToEntities(data, type)
                localDataSource.updateDetailMovie(mapper, data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getCastMovie(id: String): Flowable<Resource<List<Cast>>> {
        return object : NetworkBoundResource<List<Cast>, CastResponse>() {
            override fun loadFromDB(): Flowable<List<Cast>> {
                return localDataSource.getCastMovie(id)
                    .map { DataMapper.mapListCastEntitiesToDomain(it) }
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
            .doOnComplete { compositeDisposable.dispose() }
            .subscribe { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val mapper = DataMapper.mapListMovieResponseToDomain(response.data)
                        result.onNext(Resource.Success(mapper))
                    }
                    is ApiResponse.Error -> {
                        result.onNext(Resource.Error(response.error))
                    }
                    else -> Log.e("Network Error", "ERROR YA GES YA!!")
                }
            }
        compositeDisposable.add(client)
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun setFavoriteMovie(favorite: Favorite) {
        val mapper = DataMapper.mapFavoriteDomainToEntities(favorite)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(mapper) }
    }

    override fun getLocalDetailFavoriteMovie(id: String): Flowable<MovieFavorite> {
        return localDataSource.getFavoriteDetailMovie(id)
            .map { DataMapper.mapMovieFavoriteEntitiesToDomain(it) }
    }

    override fun searchMovie(query: String): Flowable<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, ListMovieResponse>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.searchMovie(query)
                    .map { DataMapper.mapListMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<ListMovieResponse>> {
                return remoteDataSource.searchMovie(query)
            }

            override fun saveCallResultToDB(data: ListMovieResponse) {
                val mapper = DataMapper.mapListMovieResponseToEntities(data, "")
                localDataSource.insertMovie(mapper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getLocalFavoriteMovie(): Flowable<List<MovieFavorite>> {
        return localDataSource.getFavoriteMovie()
            .map { DataMapper.mapListMovieFavoriteEntitiesToDomain(it) }
    }

    @SuppressLint("CheckResult")
    override fun insertUser(
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<Long>> {
        val result = PublishSubject.create<Resource<Long>>()
        val mapper = DataMapper.mapUserDomainToEntities(
            User(
                userId = 0,
                username = username,
                email = email,
                password = password,
                isLogin = false
            )
        )
        localDataSource.insertUser(mapper)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ insert ->
                result.onNext(Resource.Success(insert))
            }, { error ->
                result.onNext(Resource.Error(error.message))
            })

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun getUser(email: String): Single<Resource<User>> {
        val result = SingleSubject.create<Resource<User>>()
        localDataSource.getUser(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                val mapper = DataMapper.mapUserEntitiesToDomain(user)
                result.onSuccess(Resource.Success(mapper))
            }, { error ->
                result.onSuccess(Resource.Error(error.message))
            })
        return result
    }

    override fun getAllUser(): Flowable<Resource<List<User>>> {
        val result = PublishSubject.create<Resource<List<User>>>()
        val client = localDataSource.getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ user ->
                val mapper = DataMapper.mapListUserEntitiesToDomain(user)
                result.onNext(Resource.Success(mapper))
            }, { error ->
                result.onNext(Resource.Error(error.message))
            })
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    override fun updateUser(user: User): Flowable<Int> {
        val result = PublishSubject.create<Int>()
        val mapper = DataMapper.mapUserDomainToEntities(user)
        val client = localDataSource.updateUser(mapper)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.onNext(it)
            }, {})
        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    override fun isLogin(): Flowable<User> {
        val result = PublishSubject.create<User>()
        localDataSource.isLogin()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                val mapper = DataMapper.mapUserEntitiesToDomain(user)
                result.onNext(mapper)
            }

        return result.toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun setMovieNetworkBoundResource(typeMovie: String): Flowable<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, ListMovieResponse>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getAllMovieWhereType(typeMovie)
                    .map { DataMapper.mapListMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return MoviedUtils.checkForInternet(context)
            }

            override fun createCallNetwork(): Flowable<ApiResponse<ListMovieResponse>> {
                return when (typeMovie) {
                    Constants.TYPE_MOVIE_POPULAR -> {
                        remoteDataSource.getMoviePopular()
                    }
                    Constants.TYPE_MOVIE_NOW_PLAYING -> {
                        remoteDataSource.getNowPlayingMovie()
                    }
                    Constants.TYPE_MOVIE_UPCOMING -> {
                        remoteDataSource.getUpComingMovie()
                    }
                    else -> throw IllegalStateException("Invalid rating param value")
                }
            }

            override fun saveCallResultToDB(data: ListMovieResponse) {
                val mapper = DataMapper.mapListMovieResponseToEntities(data, typeMovie)

                localDataSource.insertMovie(mapper)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }
}
