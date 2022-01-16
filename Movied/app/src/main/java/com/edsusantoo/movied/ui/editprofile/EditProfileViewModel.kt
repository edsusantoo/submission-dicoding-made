package com.edsusantoo.movied.ui.editprofile

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {
    val isLogin =
        LiveDataReactiveStreams.fromPublisher(userUseCase.isLogin())

    fun updateUser(user: User) =
        LiveDataReactiveStreams.fromPublisher(userUseCase.updateUser(user))
}
