package com.edsusantoo.movied.ui.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovie =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getLocalFavoriteMovie())
}