package com.edsusantoo.movied.ui.upcoming

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import com.edsusantoo.core.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpComingViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val movieUpComing =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getMovies(Constants.TYPE_MOVIE_UPCOMING))
}