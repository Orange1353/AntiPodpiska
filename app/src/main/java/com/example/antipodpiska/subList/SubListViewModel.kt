package com.example.antipodpiska.subList


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.recyclersample.data.DataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import java.util.HashMap

import kotlin.random.Random

class SubListViewModel(val dataSource: DataSource) : ViewModel() {
    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    val subsLiveData = dataSource.getSubList()

    /* If the name and description are present, create new Flower and add it to the datasource */
    fun insertSub(subName: String?, subDescription: String?,  dateEnd: String?, datePay: String, periodFree: String, costSub: String,
                  costCurr: String, periodPay: String, periodTypeFree: String, periodTypePay: String,card: String, context: Context
    ) {
        if (subName == null || subDescription == null || dateEnd == null) {
            return
        }

        val image = dataSource.getRandomSubImageAsset()
        val newSub = Sub(
            Random.nextLong(),
            subName,
            image,
            subDescription,
            dateEnd,
            datePay,
            periodFree,
            costSub,
            costCurr,
            periodPay,
            periodTypeFree,
            periodTypePay,
            card
        )
/*
val  Shared: SharedPrefSource = SharedPrefSource(context)
val si:Sub = Shared.getFromShared(newSub)
*/


        dataSource.addSub(newSub, context)
        dataSource.addSubInFirebase(newSub)
    }


}

class SubListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubListViewModel(
                dataSource = DataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}