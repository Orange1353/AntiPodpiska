package com.example.antipodpiska.addition

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.antipodpiska.R
import kotlinx.android.synthetic.*


class CreateNameAndTypeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       var view: View = inflater.inflate(R.layout.fragment_create_name_type, container, false)

        var context: Context? = getContext()
        var spinner: Spinner = view.findViewById(R.id.spinner_type_sub)
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_tupe_sub,
            R.layout.spinner_dropdown_text)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
        spinner.setAdapter(adapter);
        return view
    }

}