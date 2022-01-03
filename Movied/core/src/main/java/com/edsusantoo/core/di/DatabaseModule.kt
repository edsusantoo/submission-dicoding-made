package com.edsusantoo.core.di

import android.content.Context
import androidx.room.Room
import com.edsusantoo.core.data.source.local.config.MoviedDatabase
import com.edsusantoo.core.data.source.local.dao.CastDao
import com.edsusantoo.core.data.source.local.dao.MovieDao
import com.edsusantoo.core.data.source.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviedDatabase =
        Room.databaseBuilder(
            context,
            MoviedDatabase::class.java, "movied.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: MoviedDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideUserDao(database: MoviedDatabase): UserDao = database.userDao()

    @Provides
    fun provideCastDao(database: MoviedDatabase):CastDao = database.castDao()
}