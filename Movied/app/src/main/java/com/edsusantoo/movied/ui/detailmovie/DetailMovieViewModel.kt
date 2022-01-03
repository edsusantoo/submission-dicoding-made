package com.edsusantoo.movied.ui.detailmovie

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.usecase.cast.CastUseCase
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val castUseCase: CastUseCase
    ): ViewModel() {
    fun detailMovie(id:String,type:String) =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getDetailMovie(id,type))

    fun castMovie(id:String) =
        LiveDataReactiveStreams.fromPublisher(castUseCase.getCastMovie(id))

    fun relatedMovie(id:String) =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getRelateMovie(id))

    fun setFavorite(favorite: Favorite) = movieUseCase.setFavoriteMovie(favorite)

    fun getLocalFavorite(id:String) =
        LiveDataReactiveStreams.fromPublisher(movieUseCase.getLocalFavoriteMovie(id))

}