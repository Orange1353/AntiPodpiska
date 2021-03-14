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
import com.example.antipodpiska.addition.AddSubActivityFragments
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

    private val newSubActivityRequestCode = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_menu, container, false)

        val subsAdapter = SubAdapter { sub -> adapterOnClick(sub) }
        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view_menu)
        recyclerView.adapter = subsAdapter
        var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)


        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
                subsAdapter.submitList(it as MutableList<Sub>)
                //              headerAdapter.updateSubCount(it.size)
            }

        }


    /*    val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener {
         fabOnClick()
        }
*/


        return view
    }



   /* <ImageButton
    android:id="@+id/fab"
    android:layout_width="42dp"
    android:layout_height="42dp"


    android:background="@drawable/gradient_float_button"

    android:layout_marginBottom="50dp"
    android:layout_marginEnd="42dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:tint="#FFFFFF" />
    private fun fabOnClick() {
        val intent = Intent(context, AddSubActivityFragments::class.java)
        startActivityForResult(intent, newSubActivityRequestCode)
    }*/
   private fun fabOnClick() {
       val intent = Intent(context, AddSubActivityFragments::class.java)
       startActivityForResult(intent, newSubActivityRequestCode)
   }



    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {

        var context: Context? = getContext()
        val intent = Intent(context, SubDetailActivity()::class.java)
        intent.putExtra(SUB_ID, sub.id)
        startActivity(intent)
    }

}