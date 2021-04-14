package com.example.antipodpiska.addition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.ExistSub
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.existSubList
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.ExistSubAdapter
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.subList.SubAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateExistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateExistFragment : Fragment() {
    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View =  inflater.inflate(R.layout.fragment_create_exist, container, false)
        var context: Context?= getContext()
        var searchView: SearchView = view.findViewById(R.id.search_view)
        val buttonNew: Button = view.findViewById(R.id.btn_add_new_sub)
        val buttonBack: Button= view.findViewById(R.id.button_back)

        val subsAdapter = ExistSubAdapter { sub -> adapterOnClick1(sub) }
        var t = existSubList(resources, context!!)
        subsAdapter.submitList(t)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_menu)
        recyclerView.adapter = subsAdapter


        buttonNew.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(context, R.anim.grow_on_click_btn)
            buttonNew.startAnimation(animation)
            btnNewClick()
        }
        buttonBack.setOnClickListener {
            communicator= activity as Communicator
            communicator.onBackPressedInFragm()
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                if(s!= "") {
                    var list1: List<ExistSub> = ArrayList<ExistSub>()
                    for (i in t.indices) {
                        if (t[i].name.toLowerCase().contains(s.toLowerCase(), false) || t[i].name.toLowerCase().contains(s.toLowerCase(), false))//В в Ф= ф
                            list1 = list1.plus(t[i])
                    }
                    subsAdapter.submitList(list1)
                }
                else {
                    var list1:List<ExistSub> = ArrayList<ExistSub>()
                    for (i in t.indices) {
                            list1 = list1.plus(t[i])
                    }
                    subsAdapter.submitList(list1)
                }


                // Toast.makeText(context, "0" + s + "0", Toast.LENGTH_SHORT).show()
                return true
            }
        })






        return view
    }

    private fun btnNewClick() {

        communicator = activity as Communicator
                communicator.existFragmentToNameFragment(-1)
    }

    private fun adapterOnClick1(sub: ExistSub) {

        communicator = activity as Communicator
        communicator.existFragmentToNameFragment( sub.id)

    }
}