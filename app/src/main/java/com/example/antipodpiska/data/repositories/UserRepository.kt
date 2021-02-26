package com.example.antipodpiska.data.repositories

import android.content.Context
import android.content.res.Resources
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.firebase.FirebaseCloud
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.google.firebase.auth.PhoneAuthCredential


class UserRepository (
    private val firebase: FirebaseSource
){
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String, nickname: String) = firebase.register(email, password, nickname)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

}