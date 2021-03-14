package com.example.antipodpiska.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.antipodpiska.R


class MenuAboutFragment : Fragment() {

   // private lateinit var communicator: CommunicatorMenu

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



        /*val btnProfile: Button = view.findViewById(R.id.button_profile)
        communicator = activity as CommunicatorMenu
        btnProfile.setOnClickListener {


            communicator.replaceFragment(NavigationMenuFragment())
        }*/

        return view
    }

}