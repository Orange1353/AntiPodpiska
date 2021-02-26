package com.example.antipodpiska.addition

interface Communicator {

    fun nameFragmentToPeriodFragment(nameSub: String, descriptionSub: String, typeSub: String)
    fun periodFragmentToCardFragment(freePeriod: String, periodTypeFree: String,costSub: String, costCurr: String, periodPay: String, periodTypePay: String, dateSub: String)
}