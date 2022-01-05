package com.edsusantoo.core.data.source.local.dao

import androidx.room.*
import com.edsusantoo.core.data.source.local.entity.UserEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = :email")
    fun getUser(email: String): Single<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity): Single<Long>

    @Query("SELECT * FROM user")
    fun getAllUser(): Flowable<List<UserEntity>>

    @Update
    fun updateUser(userEntity: UserEntity): Single<Int>
}