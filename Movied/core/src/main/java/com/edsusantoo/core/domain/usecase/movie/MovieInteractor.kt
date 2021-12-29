package com.edsusantoo.core.domain.usecase.movie

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.repository.IMoviedRepository
import io.reactivex.Flowable
import io.reactivex.Single

class MovieInteractor(private val moviedRepository:IMoviedRepository):MovieUseCase {
    override fun getMovies(type: String): Flowable<Resource<List<Movie>>> {
        return moviedRepository.getMovies(type)
    }

    override fun getDetailMovie(id: String, type: String): Flowable<Resource<Movie>> {
        return moviedRepository.getDetailMovie(id,type)
    }


}