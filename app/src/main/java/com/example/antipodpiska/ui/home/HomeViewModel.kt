package com.example.antipodpiska.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.antipodpiska.data.repositories.UserRepository
import com.example.antipodpiska.utils.startLoginActivity
import com.example.antipodpiska.utils.startPhoneLoginActivity


class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazy {
        repository.currentUser()
    }

    fun logout(view: View){
        repository.logout()
        view.context.startPhoneLoginActivity()
    }
}