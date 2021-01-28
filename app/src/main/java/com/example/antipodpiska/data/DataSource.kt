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
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.subList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource(resources: Resources, context: Context) {
    private val initialSubList = subList(resources, context)
    private val subLiveData = MutableLiveData(initialSubList)
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
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

        val  Shared: SharedPrefSource = SharedPrefSource(context)
        Shared.saveToShared(sub)

    }

    /* Removes flower from liveData and posts value. */
    fun removeSub(sub: Sub, context: Context) {
        val currentList = subLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(sub)
            subLiveData.postValue(updatedList)

            val  Shared: SharedPrefSource = SharedPrefSource(context)
            Shared.deleteShared(sub)
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
        return subLiveData
    }

    /* Returns a random flower asset for flowers that are added. */
    fun getRandomSubImageAsset(): Int? {
        val randomNumber = (initialSubList.indices).random()
        return initialSubList[randomNumber].image
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
        val subItem = HashMap<String, String>()

        subItem.put("image", sub.image.toString())
        subItem.put("description", sub.description.toString())
        subItem.put("name", sub.name.toString())
        subItem.put("card", sub.card.toString())
        subItem.put("dateEnd", sub.dateEnd.toString())
        subItem.put("datePay", sub.datePay.toString())
        subItem.put("periodFree", sub.periodPay.toString())
        subItem.put("costSub", sub.costSub.toString())
        subItem.put("costCurr", sub.costCurr.toString())
        subItem.put("periodPay", sub.periodPay.toString())
        subItem.put("periodTypeFree", sub.periodTypeFree.toString())
        subItem.put("periodTypePay", sub.periodTypePay.toString())
        subItem.put("id_user", FirebaseAuth.getInstance().currentUser?.uid.toString())

        val cal: Calendar = GregorianCalendar()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        subItem.put("date_add", dateFormat.format(cal.getTime()).toString())

        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).set(subItem).addOnSuccessListener { Log.d("AddSub", "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w("AddSub", "Error writing document", e) }
    }


    fun removeFromFirebase(sub: Sub)
    {
        firebaseFirestore.collection("Subscriptions").document(sub.id.toString()).delete()
                .addOnSuccessListener { Log.d("DeleteSub", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("DeleteSub", "Error deleting document", e) }

    }

/*   fun getFromShared(sub: Sub, context: Context): Sub {
/   val  Shared: SharedPrefSource = SharedPrefSource(context)
     return Shared.getFromShared(sub)
  }*/
}


