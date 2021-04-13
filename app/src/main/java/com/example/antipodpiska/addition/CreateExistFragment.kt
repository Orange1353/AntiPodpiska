package com.example.antipodpiska.addition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.ExistSub
import com.example.antipodpiska.data.Sub
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View =  inflater.inflate(R.layout.fragment_create_exist, container, false)

        val subsAdapter = ExistSubAdapter { sub -> adapterOnClick1(sub) }
        var t = listOf( ExistSub(1, "one", R.drawable.img), ExistSub(1, "two", R.drawable.logo_mail))
        subsAdapter.submitList(t)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_menu)
        recyclerView.adapter = subsAdapter
        return view
    }


    private fun adapterOnClick1(sub: ExistSub) {
        var context: Context?= getContext()
        Log.e("44444", sub.name)
       // val intent = Intent(context, SubDetailActivity()::class.java)
    //    intent.putExtra(SUB_ID, sub.id)
      //  startActivity(intent)
    }
}