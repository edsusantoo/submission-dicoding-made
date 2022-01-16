package com.edsusantoo.movied.ui.welcomescreen

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.edsusantoo.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeLiveData @Inject constructor(userUseCase: UserUseCase) : ViewModel() {
    val getAllUser = LiveDataReactiveStreams.fromPublisher(userUseCase.getAllUser())
}
