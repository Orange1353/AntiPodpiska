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
import com.example.antipodpiska.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateCardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCardFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_create_card, container, false)

        var context: Context? = getContext()

        var addCard: EditText = view.findViewById(R.id.add_card)

        val btnContinue: Button = view.findViewById(R.id.button_continue)

        var pushEnabled : SwitchCompat= view.findViewById(R.id.switch_enabled)

        pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены  "
            else
                pushEnabled.text = "Выключены"
        }

        val push = pushEnabled.isChecked()

        communicator = activity as Communicator
        btnContinue.setOnClickListener {
            communicator.cardFragmentToListSub(addCard.text.toString(), push)
        }

        return view
    }

}