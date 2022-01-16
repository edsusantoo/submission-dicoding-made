package com.edsusantoo.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy
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

    @Query("SELECT * FROM user WHERE is_login = 1")
    fun isLogin(): Single<UserEntity>
}
