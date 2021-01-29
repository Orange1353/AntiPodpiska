package com.example.antipodpiska.data


import androidx.annotation.DrawableRes

data class Sub(
        val id: Long = 0,
        val name: String = "",
        @DrawableRes
        val image: Int? =0,
        val description: String= "",
    //дата окончания подписки
        val dateEnd: String= "",
        //Дата начала подписки
        val datePay: String= "",
        val periodFree: String= "",
        val costSub: String= "",
        val costCurr: String= "",
        val periodPay: String= "",
        val periodTypeFree: String= "",
        val periodTypePay: String= "",
        val card: String= "",
        var pushEnabled: Boolean= false
)
