package com.example.antipodpiska.menu.Statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.menu.*
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SubAdapter
import com.example.antipodpiska.subList.SubListViewModel
import kotlinx.android.synthetic.main.delete_delete.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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

/*
 if(LocalDate.parse(i.value.toString(), formatter).isBefore(dateUntil) && LocalDate.parse(
                                        i.value.toString(),
                                        formatter
                                ).isAfter(dateFrom))


                                var dateFrom = LocalDate.parse(dateFrom0, formatter).minusDays(1)
        var dateUntil = LocalDate.parse(dateUntil0, formatter).plusDays(1)
* */

class SampleStatisticsFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var communicator: CommunicatorMenu
    private lateinit var type : String
    private lateinit var dateFrom: LocalDate
    private lateinit var dateUntil: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle: Bundle? = this.arguments
        if (bundle != null) {

            var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            type = bundle.getString("type", "Иное")
            dateFrom = LocalDate.parse( bundle.getString("dateFrom", "01.06.2020"), formatter).minusDays(1)
            dateUntil = LocalDate.parse(bundle.getString("dateUntil", "01.06.2025"), formatter).plusDays(1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View  = inflater.inflate(R.layout.fragment_menu_statistics, container, false)
        val context: Context? = getContext()
        val subsAdapter = SubAdapter({ sub -> adapterOnClick(sub) })

        recyclerView= view!!.findViewById(R.id.recycler_view_menu)

        var countSub = 0

        communicator = activity as CommunicatorMenu

        recyclerView.adapter = subsAdapter
        var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)


    //    var toolbar: Toolbar = view.findViewById(R.id.my_toolbar)
     //   toolbar.isVisible = false
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
                countSub = it.indices.last
                var list1:List<Sub> = ArrayList<Sub>()
                for (i in it.indices) {
                    if(it[i].typeSub == type && it[i].status == "Активна")

                        list1 = list1.plus(it[i])
                }
                subsAdapter.submitList(list1)
            }

        }

        return view
    }


    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {

        var context: Context? = getContext()
        val options: ActivityOptionsCompat? = activity?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                it,
                recyclerView,  // Starting view
                "anim" // The String
            )
        }

        val intent = Intent(context, SubDetailActivity()::class.java)
        intent.putExtra(SUB_ID, sub.id)
        startActivity(intent, options!!.toBundle())

    }

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                communicator.onBackPressedPopBackstack()
                true
            } else false
        }
    }


}


