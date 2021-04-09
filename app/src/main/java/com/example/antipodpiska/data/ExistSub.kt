package com.example.antipodpiska.data

import com.example.antipodpiska.R

class ExistSub(var id: Int, var name: String, var logoId: Int)

private var subExist: List<ExistSub>? = null

// This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
private fun initializeData() {
    subExist = ArrayList()
    (subExist as ArrayList<ExistSub>).add(ExistSub(1, "Emma Wilson", R.drawable.img))
    (subExist as ArrayList<ExistSub>).add(ExistSub(2, "Lavery Maiss",  R.drawable.img))
    (subExist as ArrayList<ExistSub>).add(ExistSub(3, "Lillie Watts",  R.drawable.img))
}