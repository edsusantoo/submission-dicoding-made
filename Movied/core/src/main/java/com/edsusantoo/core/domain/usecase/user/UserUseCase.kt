package com.edsusantoo.core.domain.usecase.user

import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.user.User
import io.reactivex.Flowable

interface UserUseCase {
    fun getUser(email: String): Flowable<Resource<User>>
    fun insertUser(username: String, email: String, password: String): Flowable<Resource<Long>>
    fun getAllUser(): Flowable<Resource<List<User>>>
    fun updateUser(user: User): Flowable<Int>
    fun isLogin(): Flowable<User>
}