package com.example.antipodpiska.data

data class User (
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var password: String ="",
    var nickname: String="",
    var token: String = "",
    var pushAll: Boolean = true,
    var beginPush: Int = 2,
    var periodPush: Boolean = true //true = каждый день
        )