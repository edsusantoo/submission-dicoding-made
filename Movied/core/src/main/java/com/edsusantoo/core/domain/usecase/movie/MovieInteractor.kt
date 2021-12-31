package com.edsusantoo.core.domain.usecase.movie

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.repository.IMoviedRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val moviedRepository: IMoviedRepository) :
    MovieUseCase {
    override fun getMovies(type: String): Flowable<Resource<List<Movie>>> {
        return moviedRepository.getMovies(type)
    }

    override fun getDetailMovie(id: String, type: String): Flowable<Resource<Movie>> {
        return moviedRepository.getDetailMovie(id, type)
    }


}