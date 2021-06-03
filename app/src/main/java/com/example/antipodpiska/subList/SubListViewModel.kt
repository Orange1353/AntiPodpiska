package com.example.antipodpiska.subList


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.User
import com.example.recyclersample.data.DataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import java.util.HashMap
import kotlin.math.log

import kotlin.random.Random

class SubListViewModel(val dataSource: DataSource, context: Context) : ViewModel() {
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    var subsLiveData = dataSource.getSubList(context)
   // var viewModelRoutesFragment = ViewModelProvider(SubListActivity()).get(SubListViewModel::class.java)
    /* If the name and description are present, create new Flower and add it to the datasource */


    fun insertSub(subName: String?, subColor: String, subDescription: String?,  typeSub: String, datePay: String?, periodFree: String, costSub: String,
                  costCurr: String, periodPay: String, periodTypeFree: String, periodTypePay: String,card: String, pushEnabled:Boolean, dateAdd:String, image0: String, context: Context
    ) {
        if (subName == null || subDescription == null || datePay == null) {
            return
        }

       var imageDrawable = image0
       var image: String = ""

       /*if (image0 == -1)
       {
           image0 = getRe
       }*/

        val newSub = Sub(
            id = Random.nextLong(),
            name = subName,
            color = subColor,
            description = subDescription,
            typeSub = typeSub,
            datePay = datePay,
            periodFree = periodFree,
            costSub = costSub,
            costCurr = costCurr,
            periodPay = periodPay,
            periodTypeFree = periodTypeFree,
            periodTypePay = periodTypePay,
            card = card,
            pushEnabled = pushEnabled,
            date_add = dateAdd,
            imageDrawable = imageDrawable
        )
/*
val  Shared: SharedPrefSource = SharedPrefSource(context)
val si:Sub = Shared.getFromShared(newSub)
*/
        dataSource.addSub(newSub, context)

        dataSource.addSubInFirebase(newSub)
    }

    fun editUser(userNew: User, context: Context){
       dataSource.editUserProfile(userNew, context)
    }

    fun sendSupportInFirebase(text: String, email: String, theme: String){
        dataSource.sendSupportInFirebase(text, email, theme)
    }


}



class SubListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubListViewModel(
                dataSource = DataSource.getDataSource(context.resources, context), context = context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}