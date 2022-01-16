package com.edsusantoo.movied.ui.search

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun searchMovie(query: String) =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.searchMovie(query))
}
