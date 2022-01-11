package com.edsusantoo.movied.di

import com.edsusantoo.core.domain.usecase.movie.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    fun movieUseCase(): MovieUseCase
}