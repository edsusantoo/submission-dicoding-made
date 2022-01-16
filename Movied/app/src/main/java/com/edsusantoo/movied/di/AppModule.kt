package com.edsusantoo.movied.di

import com.edsusantoo.core.domain.usecase.cast.CastInteractor
import com.edsusantoo.core.domain.usecase.cast.CastUseCase
import com.edsusantoo.core.domain.usecase.movie.MovieInteractor
import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import com.edsusantoo.core.domain.usecase.user.UserInteractor
import com.edsusantoo.core.domain.usecase.user.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    abstract fun provideCastUseCase(castInteractor: CastInteractor): CastUseCase

    @Binds
    abstract fun provideUserUseCase(userInteractor: UserInteractor): UserUseCase
}
