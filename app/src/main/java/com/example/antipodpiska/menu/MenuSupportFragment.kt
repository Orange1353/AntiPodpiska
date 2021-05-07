package com.example.antipodpiska.menu

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.antipodpiska.R


class MenuSupportFragment : Fragment() {

    private lateinit var buttonBack: Button
    private lateinit var buttonSend: Button
    private lateinit var communicator: CommunicatorMenu
    private lateinit var theme: EditText
    private lateinit var email: EditText
    private lateinit var message: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View  = inflater.inflate(R.layout.fragment_menu_support, container, false)


        buttonBack = view.findViewById(R.id.button_back)
        buttonSend = view.findViewById(R.id.button_send)

        communicator = activity as CommunicatorMenu

        theme = view.findViewById(R.id.edit_text_theme)
        email = view.findViewById(R.id.edit_text_email)
        message = view.findViewById(R.id.edit_text_message)

        val context: Context? = getContext()
        buttonSend.setOnClickListener {
            if (theme.text.toString() != "") {

                if(message.text.toString() != "" && message.text.length > 11) {
                    communicator.sendSupportMessage(
                        message.text.toString(),
                        email.text.toString(),
                        theme.text.toString()
                    )
                    message.text.clear()
                    email.text.clear()
                    theme.text.clear()


                    Toast.makeText(
                        context,
                        "Спасибо! Свяжемся с вами в ближайшее время",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else
                    Toast.makeText(context, "Опишите проблему", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(context, "Укажите тему обращения", Toast.LENGTH_SHORT).show()
        }
        buttonBack.setOnClickListener {
            communicator.onBackPressedPopBackstack()
        }
        return view
    }
    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                communicator.onBackPressedPopBackstack()
                true
            } else false
        }
    }

}