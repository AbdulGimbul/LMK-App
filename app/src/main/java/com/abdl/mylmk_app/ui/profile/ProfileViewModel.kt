package com.abdl.mylmk_app.ui.profile

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}