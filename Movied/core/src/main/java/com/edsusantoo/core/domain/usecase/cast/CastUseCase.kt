package com.edsusantoo.core.domain.usecase.cast

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.cast.Cast
import io.reactivex.Flowable

interface CastUseCase {
    fun getCastMovie(idMovie: String): Flowable<Resource<List<Cast>>>
}
