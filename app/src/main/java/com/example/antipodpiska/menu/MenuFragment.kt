package com.example.antipodpiska.menu

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.AddSubActivityFragments
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.*


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


class MenuFragment : Fragment() {

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

        val subsAdapter = SubAdapter { sub -> adapterOnClick(sub) }
        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)

        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view_menu)
        recyclerView.adapter = subsAdapter
        var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

       // (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.my_toolbar))

        var searchView: SearchView = view.findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                if(s!= "") {
                    var list1: List<Sub> = ArrayList<Sub>()
                    for (i in subsListViewModel.subsLiveData.value!!.indices) {
                        if (subsListViewModel.subsLiveData.value!![i].name.contains(s, false))
                            list1 = list1.plus(subsListViewModel.subsLiveData.value!![i])
                    }
                    subsAdapter.submitList(list1)
                }
                else {
                    var list1:List<Sub> = ArrayList<Sub>()
                    for (i in subsListViewModel.subsLiveData.value!!.indices) {
                        if(subsListViewModel.subsLiveData.value!![i].status == "Активна")
                            list1 = list1.plus(subsListViewModel.subsLiveData.value!![i])
                    }
                    subsAdapter.submitList(list1)
                }


              // Toast.makeText(context, "0" + s + "0", Toast.LENGTH_SHORT).show()
                return true
            }
        })


        subsListViewModel.subsLiveData.observe(this) {
            it?.let {

                var list1:List<Sub> = ArrayList<Sub>()
                for (i in it.indices) {
                    if(it[i].status == "Активна")
                    list1 = list1.plus(it[i])
                }

                subsAdapter.submitList(list1)
           //     subsAdapter.submitList(it as MutableList<Sub>)
                //              headerAdapter.updateSubCount(it.size)
            }

        }



        return view
    }



   private fun fabOnClick() {
       val intent = Intent(context, AddSubActivityFragments::class.java)
       startActivityForResult(intent, newSubActivityRequestCode)
   }


  /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.searchView)
        val searchView = searchItem.actionView as SearchView
       //var context: Context? = getContext()
       //var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

       searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
           override fun onQueryTextChange(newText: String?): Boolean {
               //      if (newText != null )
               //          subsListViewModel.***
               return true
           }

           override fun onQueryTextSubmit(query: String?): Boolean {
               return true
           }

       })

       super.onCreateOptionsMenu(menu, inflater)
      // return true
    }*/

    fun onQueryTextChange(query: String?): Boolean {
        // Here is where we are going to implement the filter logic
        return false
    }

    fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }



//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu, menu)
//        val searchItem = menu.findItem(R.id.action_search)
//        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        if (searchItem != null) {
//            searchView = searchItem.actionView as SearchView
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
//            queryTextListener = object : SearchView.OnQueryTextListener {
//                override fun onQueryTextChange(newText: String): Boolean {
//                    Log.i("onQueryTextChange", newText)
//                    return true
//                }
//
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    Log.i("onQueryTextSubmit", query)
//                    return true
//                }
//            }
//            searchView.setOnQueryTextListener(queryTextListener)
//        }
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_search ->                 // Not implemented here
//                return false
//            else -> {
//            }
//        }
//        searchView.setOnQueryTextListener(queryTextListener)
//        return super.onOptionsItemSelected(item)
//    }




    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(sub: Sub) {

        var context: Context? = getContext()
        val intent = Intent(context, SubDetailActivity()::class.java)
        intent.putExtra(SUB_ID, sub.id)
        startActivity(intent)
    }

}