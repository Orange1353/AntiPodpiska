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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreatePediodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreatePediodFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_create_pediod, container, false)

        val context: Context? = getContext()
        val spinner_free_period: Spinner = view.findViewById(R.id.spinner_free_period_type)
        var spinner_period_pay: Spinner = view.findViewById(R.id.spinner_period_pay)
        val adapter_free_period: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_period,
            R.layout.spinner_dropdown_text)
        adapter_free_period.setDropDownViewResource(R.layout.spinner_dropdown_text);

        spinner_free_period.setAdapter(adapter_free_period);
        spinner_period_pay.setAdapter(adapter_free_period);

        val spinner_cost: Spinner = view.findViewById(R.id.spinner_curr_cost)
        val adapter_cost: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_curr_cost,
            R.layout.spinner_dropdown_text)
        adapter_cost.setDropDownViewResource(R.layout.spinner_dropdown_text);
        spinner_cost.setAdapter(adapter_cost);

        return view
    }


}