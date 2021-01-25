package com.example.antipodpiska.subList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.antipodpiska.R


import android.app.Activity
import android.content.Context
import android.content.Intent

import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.lifecycle.observe

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.addition.AddSubActivity




import com.example.antipodpiska.data.Sub

import com.example.antipodpiska.subDetails.SubDetailActivity



import com.example.antipodpiska.subList.SubListViewModel

const val SUB_ID = "sub id"
const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val END_DATE = "date"
const val CARD = "card"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"


class SubListActivity : AppCompatActivity() {
    private val newSubActivityRequestCode = 1
    private val subsListViewModel by viewModels<SubListViewModel> {
        SubListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /* Instantiates headerAdapter and flowersAdapter. Both adapters are added to concatAdapter.
        which displays the contents sequentially */
        val headerAdapter = HeaderAdapter()
        val subsAdapter = SubAdapter { sub -> adapterOnClick(sub) }
        val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
                subsAdapter.submitList(it as MutableList<Sub>)
                headerAdapter.updateSubCount(it.size)
            }
        }

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {
        val intent = Intent(this, SubDetailActivity()::class.java)
        intent.putExtra(SUB_ID, sub.id)
        startActivity(intent)
    }

    override fun onBackPressed() {

    }

    /* Adds flower to flowerList when FAB is clicked. */
    private fun fabOnClick() {
        val intent = Intent(this, AddSubActivity::class.java)
        startActivityForResult(intent, newSubActivityRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newSubActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subName = data.getStringExtra(SUB_NAME)
                val subDescription = data.getStringExtra(SUB_DESCRIPTION)
                val dateEnd = data.getStringExtra(END_DATE)

                var dayPay = data.getStringExtra(DATE_PAY)
                if (dayPay == null)
                    dayPay = ""
                var periodFree = data.getStringExtra(FREE_PERIOD)
                if (periodFree == null)
                    periodFree =""
                var cost = data.getStringExtra(COST)
                if ( cost == null)
                    cost =""
                var currCost = data.getStringExtra(CURR_COST)
                if ( currCost == null)
                currCost = ""
                var periodPay = data.getStringExtra(PERIOD)
                if ( periodPay == null)
                    periodPay = ""
                var peroidTypeFree = data.getStringExtra(TYPE_FREE)
                if ( peroidTypeFree == null)
                    peroidTypeFree = ""
                var peroidTypePay = data.getStringExtra(TYPE_PERIOD)
                if ( peroidTypePay == null)
                    peroidTypePay = ""
                var card = data.getStringExtra(CARD)
                if ( card == null)
                    card = ""

                subsListViewModel.insertSub(subName, subDescription, dateEnd, dayPay,  periodFree, cost, currCost, periodPay, peroidTypeFree, peroidTypePay, card)
            }
        }
    }

}