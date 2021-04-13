package com.example.antipodpiska.data

import android.content.Context
import android.content.res.Resources
import com.example.antipodpiska.R
import kotlinx.android.synthetic.main.fragment_create_exist.view.*

fun existSubList(resources: Resources, context: Context): List<ExistSub> {

    return listOf(ExistSub(1, "kk", R.drawable.logo_mail) )

}