/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.recyclersample.data

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.User
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.example.antipodpiska.data.subList
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.ui.auth.SignupActivity
import com.example.antipodpiska.utils.startSignupActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource(resources: Resources, context: Context) {
    val resForFunDel = resources
    val conForFunDel = context
    private var initialSubList = subList(resources, context)
    private var subLiveData = MutableLiveData(initialSubList)
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    //??????????
    private val Shared: SharedPrefSource by lazy{ SharedPrefSource(context)}


    /* Adds flower to liveData and posts value. */
    fun addSub(sub: Sub, context: Context) {
        val currentList = subLiveData.value
        if (currentList == null) {
            subLiveData.postValue(listOf(sub))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, sub)
            subLiveData.postValue(updatedList)
        }
        Shared.saveToShared(sub)
    }

    fun deleteLiveData(){
      initialSubList = subList(resForFunDel, conForFunDel)
      subLiveData = MutableLiveData(initialSubList)

    }

    fun editSub(sub: Sub, editedSub:Sub, context: Context) {
        val currentList = subLiveData.value
        if (currentList == null) {
            subLiveData.postValue(listOf(sub))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.replaceAll { sub->editedSub }
            subLiveData.postValue(updatedList)
        }
        Shared.saveToShared(editedSub)

    }

    /* Removes flower from liveData and posts value. */
    fun removeSub(sub: Sub, context: Context) {
        val currentList = subLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(sub)
            subLiveData.postValue(updatedList)

            Shared.deleteShared(sub)
            Shared.deleteNeardayPayDate(context, sub.id)
        }
    }

    /* Returns flower given an ID. */
    fun getSubForId(id: Long): Sub? {

        subLiveData.value?.let { sub ->
            return sub.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getSubList(): LiveData<List<Sub>> {
        deleteLiveData()
     /*   var listSubLive = ArrayList<Sub>()
        for(i in 1..subLiveData.value!!.size)
        {
            if ()
        }
*/

        return subLiveData
    }

    fun editUserProfile(userNew: User, context: Context){
        val Shared2: SharedPrefSource = SharedPrefSource((context))
        Shared2.saveUserToShared(userNew, context)
        userNew.token = FirebaseInstanceId.getInstance().token.toString()
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userNew)
            .addOnCompleteListener {

                if (it.isSuccessful)
                {

                }
                else{

                }

            }
    }


    /* Returns a random flower asset for flowers that are added. */
    fun getRandomSubImageAsset(): String {
      //  val randomNumber = (initialSubList.indices).random()
     //   return initialSubList[randomNumber].image

        val listColor= listOf("FAB328", "EF5F72", "00B8E2", "7E3390", "1D6BF0")
        val randomColor = listColor.random()
        return randomColor
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources, context: Context): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }


    fun addSubInFirebase(sub: Sub)
    {
        val subItem = HashMap<String, Any>()

        subItem.put("id", sub.id)
        subItem.put("color", sub.color)
        subItem.put("description", sub.description.toString())
        subItem.put("name", sub.name.toString())
        subItem.put("card", sub.card.toString())
        subItem.put("typeSub", sub.typeSub.toString())
        subItem.put("datePay", sub.datePay.toString())
        subItem.put("periodFree", sub.periodPay.toString())
        subItem.put("costSub", sub.costSub.toString())
        subItem.put("costCurr", sub.costCurr.toString())
        subItem.put("periodPay", sub.periodPay.toString())
        subItem.put("periodTypeFree", sub.periodTypeFree.toString())
        subItem.put("periodTypePay", sub.periodTypePay.toString())
        subItem.put("id_user", FirebaseAuth.getInstance().currentUser?.uid.toString())
        subItem.put("push", sub.pushEnabled)
        subItem.put("status", sub.status)
        subItem.put("imageDrawable", sub.imageDrawable)


        if (sub.date_add != "" && sub.date_add != null)
            subItem.put("date_add", sub.date_add)
        else {
            val cal: Calendar = GregorianCalendar()
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            subItem.put("date_add", dateFormat.format(cal.getTime()).toString())
        }

        if (sub?.datePay != null && sub?.datePay !="")
        {
            subItem.put("nearDayPay", calcNearDayPay(sub))
        }



        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).set(subItem).addOnSuccessListener { Log.d("AddSub", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("AddSub", "Error writing document", e) }

        if (sub.card != "") {
            val subItemCard = HashMap<String, Any>()
            subItemCard.put(sub.card.toString(), "something")
            firebaseFirestore.collection("Users_cards").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(subItemCard).addOnSuccessListener { Log.d("AddCard", "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w("AddCard", "Error writing document", e) }
        }

    }

    fun checkUserCloud(context: Context){
       if(firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()) == null)
       {
           var listUser = Shared.getTempUser(context)
           val firebase = FirebaseSource()
           firebase.addUserInFirebaseWithCheck(listUser[0], listUser[1], listUser[2])
       }
    }


    fun calcNearDayPay(sub: Sub): String {

            var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var datePay = LocalDate.parse(sub?.datePay, formatter)

            var dateNow = LocalDate.now()

            if (sub?.periodFree != "")
                when (sub?.periodTypeFree) {
                    "Дней" -> datePay = datePay.plusDays(sub?.periodFree!!.toLong())
                    "Недель" -> datePay = datePay.plusWeeks(sub?.periodFree!!.toLong())
                    "Месяцев" -> datePay = datePay.plusDays(30 * sub?.periodFree!!.toLong())
                }

            if (sub?.periodPay != "") {
                while (datePay < dateNow)
                    when (sub?.periodTypePay) {
                        "Дней" -> datePay = datePay.plusDays(sub?.periodPay!!.toLong())
                        "Недель" -> datePay = datePay.plusWeeks(sub?.periodPay!!.toLong())
                        "Месяцев" -> datePay =
                            datePay.plusDays(30 * sub?.periodPay!!.toLong())
                    }
            }


          return datePay.format(formatter).toString()

    }


    fun removeFromFirebase(sub: Sub)
    {
        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).delete()
                .addOnSuccessListener { Log.d("DeleteSub", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("DeleteSub", "Error deleting document", e) }

    }



    fun pushAboutSub(sub:Sub){
        sub.pushEnabled = !sub.pushEnabled
        Shared.saveToShared(sub)
        addSubInFirebase(sub)
    }

    fun sendSupportInFirebase(text: String, email: String, theme: String){
        var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        var message: HashMap<String, String> = HashMap()

        message.put("email", email)
        message.put("message", text)
        val cal: Calendar = GregorianCalendar()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        database.child(theme + " " + dateFormat.format(cal.getTime()).toString()).setValue(message)

    }

}


