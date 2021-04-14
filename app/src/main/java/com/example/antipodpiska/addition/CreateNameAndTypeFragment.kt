package com.example.antipodpiska.addition

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.antipodpiska.R
import com.example.antipodpiska.data.existSubList
import com.example.antipodpiska.utils.startSubListActivity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.*


class CreateNameAndTypeFragment : Fragment() {

    private lateinit var communicator: Communicator
    private var param1: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("SUB")
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
            R.layout.spinner_dropdown_dark)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_dark)
        spinner_typeSub.setAdapter(adapter)

        var addSubName: EditText = view.findViewById(R.id.add_flower_name)
        var addSubDescription: EditText= view.findViewById(R.id.add_flower_description)


        var listExist = existSubList(resources, context!!)
        if(param1!= null && param1!= -1) {
            addSubName.setText(listExist[param1!!].name)
            addSubDescription.setText(listExist[param1!!].description)
            when(listExist[param1!!].type) {
                "Музыка" -> spinner_typeSub.setSelection(1)
                "Приложения" -> spinner_typeSub.setSelection(2)
                "Смарт-Тв" -> spinner_typeSub.setSelection(3)
                "Связь" -> spinner_typeSub.setSelection(4)
                "Облака" -> spinner_typeSub.setSelection(5)
                "Иное" -> spinner_typeSub.setSelection(6)
            }
        }

        val btnContinue: Button = view.findViewById(R.id.button_continue)
        communicator = activity as Communicator
        btnContinue.setOnClickListener {
            if(addSubName.text.toString() != "" && addSubDescription.text.toString() != "")
            communicator.nameFragmentToPeriodFragment(addSubName.text.toString(), addSubDescription.text.toString(), spinner_typeSub.selectedItem.toString())
            else
                Toast.makeText(context, "Заполните обязательные поля!",  Toast.LENGTH_SHORT).show()
        }

        val buttonBack: Button= view.findViewById(R.id.button_back0)

        buttonBack.setOnClickListener {
            communicator.onBackPressedInFragm()
        }


        return view
    }

}