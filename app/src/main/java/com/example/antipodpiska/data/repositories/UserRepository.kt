package com.example.antipodpiska.data.repositories

import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.firebase.FirebaseCloud
import com.example.antipodpiska.data.firebase.FirebaseSource


class UserRepository (
    private val firebase: FirebaseSource
){
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String, nickname: String) = firebase.register(email, password, nickname)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()

    fun addUserInFirebase(email: String, password: String, nickname: String) = firebase.addUserInFirebase(email, password, nickname)

}