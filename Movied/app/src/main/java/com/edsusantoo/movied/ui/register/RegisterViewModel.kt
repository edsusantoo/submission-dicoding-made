package com.edsusantoo.movied.ui.register

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModel() {
    fun insertUser(username: String, email: String, password: String) =
        LiveDataReactiveStreams.fromPublisher(userUseCase.insertUser(username, email, password))

}