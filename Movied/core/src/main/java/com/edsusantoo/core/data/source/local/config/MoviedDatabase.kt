package com.edsusantoo.core.data.source.local.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edsusantoo.core.data.source.local.dao.CastDao
import com.edsusantoo.core.data.source.local.dao.MovieDao
import com.edsusantoo.core.data.source.local.dao.UserDao
import com.edsusantoo.core.data.source.local.entity.CastEntity
import com.edsusantoo.core.data.source.local.entity.FavoriteEntity
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.UserEntity

@Database(entities = [
    UserEntity::class,
    MovieEntity::class,
    CastEntity::class,
    FavoriteEntity::class],version = 1,exportSchema = false)
abstract class MoviedDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun movieDao():MovieDao
    abstract fun castDao():CastDao
}