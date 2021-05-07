package com.example.antipodpiska.data

import android.content.Context
import android.content.res.Resources
import com.example.antipodpiska.R
import com.example.antipodpiska.subList.ListExistSubs
import kotlinx.android.synthetic.main.fragment_create_exist.view.*

fun existSubList(resources: Resources, context: Context): List<ExistSub> {

    val names = ListExistSubs.subNames
    val images =  ListExistSubs.subImages
    val description = ListExistSubs.subDescriptions
    val type =  ListExistSubs.subTypes
    val colors = ListExistSubs.subColors
    var subExistList: ArrayList<ExistSub> = ArrayList()

    var index = 0
    for (name in names){
        subExistList.add(ExistSub(index, name, images[index], description[index], type[index], colors[index]))
        index ++
    }

  /*  return listOf(ExistSub(1, "YouTube Premium", R.drawable.logo_youtube_premium, ""), ExistSub(2,"Amazon music", R.drawable.logo_amazon_music, ""),
        ExistSub(3,"Apple Music", R.drawable.logo_apple_music, ""))
*/
return  subExistList

}