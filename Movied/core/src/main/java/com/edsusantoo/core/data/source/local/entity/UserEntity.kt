package com.edsusantoo.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user", indices = [Index(value = ["email"], unique = true)])
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_user")
    var userId: Int = 0,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "is_login")
    var isLogin: Boolean
) : Parcelable
