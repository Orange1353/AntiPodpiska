package com.example.antipodpiska.data

import android.content.Context
import android.content.res.Resources
import com.example.antipodpiska.R
import com.google.gson.Gson


//private var sharedPreferencesref: SharedPreferences? = null
/* Returns initial list of flowers. */
fun subList(resources: Resources,  context: Context): List<Sub> {

val sub = Sub(
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
)
    //shared preferences
   // val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
  //  var editor = sharedPreference.edit()
  //  editor.putString("Chance","t").apply()
  //  editor.remove("username")
  //  editor.clear()

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()
    val gson = Gson()
    var json = gson.toJson(sub);
    editor.putString(sub.name, json).apply()
    var obj: Sub

//    var json1: String? = sharedPreference.getString("MyObject", "")
  //  var obj: Sub = gson.fromJson(json1, Sub::class.java)

    val map: Map<String, *> = sharedPreference.getAll()

    val keyList = ArrayList(map.keys)
    val valueList = ArrayList(map.values)

    val subListFromPref: ArrayList<Sub>? = null
    var sub1: Sub
    for(i in keyList.indices) {
        json = valueList[i].toString()
        obj = gson.fromJson(json, Sub::class.java)
        subListFromPref?.add(obj)
    }
        return listOf(
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
        ),
        Sub(
                id = 3,
                name = resources.getString(R.string.flower2_name),
                image = R.drawable.img,
                description = resources.getString(R.string.flower2_description),
                card = "5555",
                dateEnd = "29.01.2021",
                datePay= "30.10.3222",
                periodFree= "8",
                costSub = "88",
                costCurr = "RUB",
                periodPay= "2",
                periodTypeFree = "Weeks",
                periodTypePay = "Weeks"
        ))

}



