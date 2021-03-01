package com.example.antipodpiska.addition

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.antipodpiska.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.*


class CreateNameAndTypeFragment : Fragment() {

    private lateinit var communicator: Communicator

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
        var spinner_typeSub: Spinner = view.findViewById(R.id.spinner_type_sub)
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_tupe_sub,
            R.layout.spinner_dropdown_text)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_text);
        spinner_typeSub.setAdapter(adapter);

        var addSubName: EditText = view.findViewById(R.id.add_flower_name)
        var addSubDescription: EditText= view.findViewById(R.id.add_flower_description)



        val btnContinue: Button = view.findViewById(R.id.button_continue)
        communicator = activity as Communicator
        btnContinue.setOnClickListener {
            if(addSubName.text.toString() != "" && addSubDescription.text.toString() != "")
            communicator.nameFragmentToPeriodFragment(addSubName.text.toString(), addSubDescription.text.toString(), spinner_typeSub.selectedItem.toString())
            else
                Toast.makeText(context, "Заполните обязательные поля!",  Toast.LENGTH_SHORT).show()
        }

        return view
    }

}