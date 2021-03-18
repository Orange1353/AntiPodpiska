package com.example.antipodpiska.addition

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.antipodpiska.R


class CreateCardFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_create_card, container, false)

        var context: Context? = getContext()

        var addCard: EditText = view.findViewById(R.id.add_card)

        val btnContinue: Button = view.findViewById(R.id.button_continue)
        val btnReady:Button = view.findViewById(R.id.button_ready)
        var lay :ConstraintLayout = view.findViewById(R.id.color)


        var pushEnabled : SwitchCompat= view.findViewById(R.id.switch_enabled)

        pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены  "
            else
                pushEnabled.text = "Выключены"
        }

    //    val push = pushEnabled.isChecked()

        communicator = activity as Communicator

        btnContinue.setOnClickListener {
            btnReady.isVisible = true
            lay.isVisible = false
        }

        btnReady.setOnClickListener {
            communicator.cardFragmentToListSub(addCard.text.toString(), pushEnabled.isChecked())
        }

        val buttonBack: Button= view.findViewById(R.id.button_back)

        buttonBack.setOnClickListener {
            communicator.onBackPressedInFragms23()
        }

        return view
    }

}