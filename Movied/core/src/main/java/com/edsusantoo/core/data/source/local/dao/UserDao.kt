package com.edsusantoo.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edsusantoo.core.data.source.local.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username")
    fun getUser(username:String): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity):Completable
}