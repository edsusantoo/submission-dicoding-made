package com.edsusantoo.core.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
) : Parcelable
