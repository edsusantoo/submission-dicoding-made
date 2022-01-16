package com.edsusantoo.movied.ui.login

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    fun getUser(email: String) =
        LiveDataReactiveStreams.fromPublisher(userUseCase.getUser(email))

    fun updateUser(user: User) =
        LiveDataReactiveStreams.fromPublisher(userUseCase.updateUser(user))
}
