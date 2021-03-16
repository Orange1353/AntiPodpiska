package com.example.antipodpiska.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.Communicator


class MenuAboutFragment : Fragment() {

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

        val view: View = inflater.inflate(R.layout.fragment_menu_about, container, false)
        
        communicator = activity as CommunicatorMenu
        buttonBack = view.findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            communicator.onBackPressedMenuItem()
        }

        /*val btnProfile: Button = view.findViewById(R.id.button_profile)
        communicator = activity as CommunicatorMenu
        btnProfile.setOnClickListener {


            communicator.replaceFragment(NavigationMenuFragment())
        }*/

        return view
    }

}