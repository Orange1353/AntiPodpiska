package com.example.antipodpiska.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.AddSubActivityFragments
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SubAdapter
import com.example.antipodpiska.subList.SubAdapterArchive
import com.example.antipodpiska.subList.SubListViewModel

class ArchiveFragment : Fragment() {

    private val newSubActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_menu, container, false)

        val subsAdapter = SubAdapterArchive { sub -> adapterOnClick(sub) }
        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view_menu)
        recyclerView.adapter = subsAdapter
        var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

        subsListViewModel.subsLiveData.observe(this) {
            it?.let {
                Log.e("55555 ARC", it.toString())
                var list1:List<Sub> = ArrayList<Sub>()
                for (i in it.indices) {
                    if(it[i].status == "Архив")
                        list1 = list1.plus(it[i])
                }
                subsAdapter.submitList(list1)

            // subsAdapter.submitList(it as MutableList<Sub>)
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