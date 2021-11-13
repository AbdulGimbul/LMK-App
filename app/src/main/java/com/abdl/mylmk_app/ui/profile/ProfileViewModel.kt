package com.abdl.mylmk_app.ui.profile

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}