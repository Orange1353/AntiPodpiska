package com.example.antipodpiska.menu

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R


class MenuSettingFragment : Fragment() {

    private lateinit var buttonBack: Button
    private lateinit var communicator: CommunicatorMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view: View = inflater.inflate(R.layout.fragment_menu_setting, container, false)

        communicator = activity as CommunicatorMenu
        buttonBack = view.findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            communicator.onBackPressedMenuItem()
        }

        val btnPush: Button = view.findViewById(R.id.button_push)
        communicator = activity as CommunicatorMenu
        btnPush.setOnClickListener {
            communicator.replaceFragment(MenuPushFragment())
        }

        val btnProfile: Button = view.findViewById(R.id.button_edit_profile)
        communicator = activity as CommunicatorMenu

        btnProfile.setOnClickListener {
            var fragment = MenuProfileFragment()
             val arguments = Bundle()
             arguments.putString("key_from","edit")
             fragment.arguments = arguments
            communicator.replaceFragment(fragment)
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
                communicator.onBackPressedMenuItem()
                true
            } else false
        }
    }
}