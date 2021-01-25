package com.example.antipodpiska.subDetails


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.data.Sub
import com.example.recyclersample.data.DataSource


class SubDetailViewModel(private val datasource: DataSource) : ViewModel() {

    /* Queries datasource to returns a flower that corresponds to an id. */
    fun getFlowerForId(id: Long) : Sub? {
        return datasource.getSubForId(id)
    }

    /* Queries datasource to remove a flower. */
    fun removeFlower(flower: Sub) {
        datasource.removeSub(flower)
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