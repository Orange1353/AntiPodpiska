package com.example.antipodpiska.data

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.example.antipodpiska.R
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.example.recyclersample.data.DataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

fun subList(resources: Resources,  context: Context): List<Sub> {

/*val sub = Sub(
        id = 10,
        name = "Тест",
        image = R.drawable.img,
        description = resources.getString(R.string.flower1_description),
        card = "",
        dateEnd = "30.01.2021",
        datePay= "",
        periodFree= "",
        costSub = "",
        costCurr = "",
        periodPay= "",
        periodTypeFree = "",
        periodTypePay = ""
)*/

    var subListFromPref: ArrayList<Sub> = ArrayList()

    val sharedPreference =  context.getSharedPreferences(FirebaseAuth.getInstance().currentUser?.uid.toString(),Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()
    val gson = Gson()
    var obj: Sub
    var json: String

    var map: Map<String, *> = sharedPreference.getAll()
    var keyList = ArrayList(map.keys)
    var valueList = ArrayList(map.values)

    for(i in keyList.indices) {

        json = valueList[i].toString()
        obj = gson.fromJson(json, Sub::class.java)
        subListFromPref?.add(obj)
    }

    var list1:List<Sub> = ArrayList<Sub>()

    for(i in keyList.indices)
        list1= list1.plus(subListFromPref[i])

    if (subListFromPref.size == 0)
    {

        val t: FirebaseSource = FirebaseSource()
        subListFromPref = t.getFromFirebase(context)

        //хз, надо бы разблочить
      /*  map= sharedPreference.getAll()
        keyList = ArrayList(map.keys)
        valueList = ArrayList(map.values)

        for(i in keyList.indices) {

            json = valueList[i].toString()
            obj = gson.fromJson(json, Sub::class.java)
            subListFromPref?.add(obj)
        }*/

        for (i in subListFromPref.indices) {
       //     var data : DataSource = DataSource(resources, context)
      //      data.addSub(subListFromPref[i], context)
            list1 = list1.plus(subListFromPref[i])
        }
    }

    return list1

    /*    return listOf(
        Sub(
                id = 1,
                name = resources.getString(R.string.flower1_name),
                image = R.drawable.img,
                description = resources.getString(R.string.flower1_description),
                card = "",
                dateEnd = "30.01.2021",
                datePay= "",
                periodFree= "",
                costSub = "",
                costCurr = "",
                periodPay= "",
                periodTypeFree = "",
                periodTypePay = ""
        ),
        Sub(
                id = 2,
                name = resources.getString(R.string.flower2_name),
                image = R.drawable.img,
                description = resources.getString(R.string.flower2_description),
                card = "5555",
                dateEnd = "29.01.2021",
                datePay= "24.11.2020",
                periodFree= "",
                costSub = "100",
                costCurr = "RUB",
                periodPay= "1",
                periodTypeFree = "Weeks",
                periodTypePay = "Mounths"
        )
                subListFromPref[0]
        )*/

}
