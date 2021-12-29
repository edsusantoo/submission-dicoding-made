package com.edsusantoo.core.di

import com.edsusantoo.core.data.MoviedRepository
import com.edsusantoo.core.domain.repository.IMoviedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class,DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(moviedRepository: MoviedRepository):IMoviedRepository
}