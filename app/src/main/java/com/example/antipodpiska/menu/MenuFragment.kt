package com.example.antipodpiska.menu

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.AddSubActivity
import com.example.antipodpiska.addition.DATE_ADD
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.*
import com.example.antipodpiska.ui.home.HomeActivity

const val SUB_ID = "sub id"
const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val TYPE = "typeSub"
const val CARD = "card"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"
const val PUSH = "push"


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val newSubActivityRequestCode = 1

    var viewModelRoutesFragment = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

    //private val subsListViewModel by viewModels<SubListViewModel()
    private val EditActivity = com.example.antipodpiska.addition.EditActivity()


    private val CHANNEL_ID = "channel"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)

        val subsAdapter = SubAdapter { sub -> adapterOnClick(sub) }
        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view)
        recyclerView.adapter = subsAdapter

        viewModelRoutesFragment.subsLiveData.observe(this) {
            it?.let {
                subsAdapter.submitList(it as MutableList<Sub>)
                //              headerAdapter.updateSubCount(it.size)
            }

        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                MenuFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }






    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {
  //      val intent = Intent(this, SubDetailActivity()::class.java)
    //    intent.putExtra(SUB_ID, sub.id)
  //      startActivity(intent)
    }


    /* Adds flower to flowerList when FAB is clicked. */


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newSubActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subName = data.getStringExtra(SUB_NAME)
                val subDescription = data.getStringExtra(SUB_DESCRIPTION)

                var typeSub = data.getStringExtra(TYPE)
                if (typeSub == null || typeSub == "Выберите тип подписки")
                    typeSub = "Иное"
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
                var push = data.getBooleanExtra(PUSH, false)
                val dateAdd = data.getStringExtra(DATE_ADD)


//image?
                viewModelRoutesFragment.insertSub(
                    subName,
                    subDescription,
                    typeSub,
                    dayPay,
                    periodFree,
                    cost,
                    currCost,
                    periodPay,
                    peroidTypeFree,
                    peroidTypePay,
                    card,
                    push,
                    dateAdd!!,
                    context!!
                )
            }
        }
    }





}