package com.edsusantoo.core.domain.usecase.cast

import com.edsusantoo.core.data.MoviedRepository
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.cast.Cast
import io.reactivex.Flowable
import javax.inject.Inject

class CastInteractor @Inject constructor(private val moviedRepository: MoviedRepository) : CastUseCase {
    override fun getCastMovie(idMovie: String): Flowable<Resource<List<Cast>>> {
        return moviedRepository.getCastMovie(idMovie)
    }
}