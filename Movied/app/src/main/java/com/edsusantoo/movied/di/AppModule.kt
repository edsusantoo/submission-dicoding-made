package com.edsusantoo.movied.di

import com.edsusantoo.core.domain.usecase.cast.CastInteractor
import com.edsusantoo.core.domain.usecase.cast.CastUseCase
import com.edsusantoo.core.domain.usecase.movie.MovieInteractor
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    abstract fun provideCastUseCase(castInteractor:CastInteractor): CastUseCase

}