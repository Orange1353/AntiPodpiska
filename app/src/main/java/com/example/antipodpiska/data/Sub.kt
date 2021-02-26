package com.example.antipodpiska.data


import androidx.annotation.DrawableRes

data class Sub(
        val id: Long = 0,
        var name: String = "",
        val image: String ="#FFFFFF",
        var description: String= "",
    //дата окончания подписки
        var typeSub: String= "",
        //Дата начала подписки
        var datePay: String= "",
        var periodFree: String= "",
        var costSub: String= "",
        var costCurr: String= "",
        var periodPay: String= "",
        var periodTypeFree: String= "",
        var periodTypePay: String= "",
        var card: String= "",
        var pushEnabled: Boolean= false,
        val date_add: String = ""

)
