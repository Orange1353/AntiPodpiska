package com.example.antipodpiska.subDetails


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.recyclersample.data.DataSource
import kotlin.random.Random


class SubDetailViewModel(private val datasource: DataSource) : ViewModel() {

    /* Queries datasource to returns a flower that corresponds to an id. */
    fun getFlowerForId(id: Long) : Sub? {
        return datasource.getSubForId(id)
    }

    /* Queries datasource to remove a flower. */
    fun removeFlower(sub: Sub, context: Context) {

 /*       val  Shared: SharedPrefSource = SharedPrefSource(context)
        Shared.deleteShared(sub)
   */   datasource.removeSub(sub, context)
        datasource.removeFromFirebase(sub)
    }

    fun pushAboutSub(sub: Sub){
        datasource.pushAboutSub(sub)
    }

    fun editSub(sub:Sub, editedSub: Sub, context: Context){
        datasource.editSub(sub, editedSub, context)
        datasource.addSubInFirebase(editedSub)
    }

    fun insertSub(newSub: Sub, context: Context){

        datasource.addSub(newSub, context)
        datasource.addSubInFirebase(newSub)

    }

    fun insertSub(subName: String?, subColor: Int, subDescription: String?,  typeSub: String, datePay: String?, periodFree: String, costSub: String,
                  costCurr: String, periodPay: String, periodTypeFree: String, periodTypePay: String,card: String, pushEnabled:Boolean, dateAdd:String, image0: Int, context: Context, status: String
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
                imageDrawable = imageDrawable,
                dateOfDelete = "",
                status = status
        )
/*
val  Shared: SharedPrefSource = SharedPrefSource(context)
val si:Sub = Shared.getFromShared(newSub)
*/
        datasource.addSub(newSub, context)

        datasource.addSubInFirebase(newSub)
    }


}



class SubDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubDetailViewModel(
                datasource = DataSource.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}