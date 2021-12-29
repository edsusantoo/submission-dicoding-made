package com.edsusantoo.core.data.source.local.config

import androidx.room.Database
import com.edsusantoo.core.data.source.local.dao.MovieDao
import com.edsusantoo.core.data.source.local.dao.UserDao
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class,MovieEntity::class],version = 1,exportSchema = false)
abstract class MoviedDatabase {
    abstract fun userDao():UserDao
    abstract fun movieDao():MovieDao
}