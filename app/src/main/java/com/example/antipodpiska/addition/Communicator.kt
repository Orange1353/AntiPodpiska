package com.example.antipodpiska.addition

interface Communicator {

    fun onBackPressedInFragms23()
    fun onBackPressedInFragm()
    fun nameFragmentToPeriodFragment(nameSub: String, descriptionSub: String, typeSub: String)
    fun periodFragmentToCardFragment(freePeriod: String, periodTypeFree: String,costSub: String, costCurr: String, periodPay: String, periodTypePay: String, dateSub: String)
    fun cardFragmentToListSub(cardNumber:String, push: Boolean)
    fun existFragmentToNameFragment(indexExistSub: Int)
}