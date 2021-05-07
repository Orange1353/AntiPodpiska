package com.example.antipodpiska.menu

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R


class MenuAboutFragment : Fragment() {

    private lateinit var buttonBack: Button
    private lateinit var buttonBackO: Button
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
        val view: View = inflater.inflate(R.layout.fragment_menu_about, container, false)
        
        communicator = activity as CommunicatorMenu
        buttonBack = view.findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            communicator.onBackPressedMenuItem()
        }
        buttonBackO = view.findViewById(R.id.button_menu_back)
        val context: Context? = getContext()
        val face: Typeface = Typeface.createFromAsset(
            context?.assets, "font/sf_display_bold.ttf"
        )
        buttonBackO.setTypeface(face)

        /*val btnProfile: Button = view.findViewById(R.id.button_profile)
        communicator = activity as CommunicatorMenu
        btnProfile.setOnClickListener {


            communicator.replaceFragment(NavigationMenuFragment())
        }*/

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