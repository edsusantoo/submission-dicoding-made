package com.edsusantoo.core.domain.usecase.user

import com.edsusantoo.core.data.MoviedRepository
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.user.User
import io.reactivex.Flowable
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val moviedRepository: MoviedRepository
) : UserUseCase {
    override fun getUser(email: String): Flowable<Resource<User>> =
        moviedRepository.getUser(email).toFlowable()

    override fun insertUser(
        username: String,
        email: String,
        password: String
    ): Flowable<Resource<Long>> =
        moviedRepository.insertUser(username, email, password)

    override fun getAllUser(): Flowable<Resource<List<User>>> =
        moviedRepository.getAllUser()

    override fun updateUser(user: User): Flowable<Int> = moviedRepository.updateUser(user)

    override fun isLogin(): Flowable<User> = moviedRepository.isLogin()
}