package com.edsusantoo.movied.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getLocalFavoriteMovie())
}
