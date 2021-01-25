package com.example.antipodpiska.data


import androidx.annotation.DrawableRes

data class Sub(
        val id: Long,
        val name: String,
        @DrawableRes
        val image: Int?,
        val description: String,
    //дата окончания подписки
        val dateEnd: String,
        val datePay: String,
        val periodFree: String,
        val costSub: String,
        val costCurr: String,
        val periodPay: String,
        val periodTypeFree: String,
        val periodTypePay: String,
        val card: String
)