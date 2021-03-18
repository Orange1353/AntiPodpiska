package com.example.antipodpiska.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.antipodpiska.R
import com.example.antipodpiska.ui.home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NavigationMenuFragment : Fragment() {

    private lateinit var communicator: CommunicatorMenu

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       var view: View = inflater.inflate(R.layout.fragment_navigation_menu, container, false)

       val btnLogOut: Button = view.findViewById(R.id.button_logout)
       btnLogOut.setOnClickListener {
           logout(view)
       }

        val btnProfile: Button = view.findViewById(R.id.button_profile)
        communicator = activity as CommunicatorMenu
        btnProfile.setOnClickListener {
           communicator.replaceFragment(MenuProfileFragment())
        }
        val btnAbout: Button = view.findViewById(R.id.button_about_app)
        communicator = activity as CommunicatorMenu
        btnAbout.setOnClickListener {
            communicator.replaceFragment(MenuAboutFragment())
        }
        val btnSetting: Button = view.findViewById(R.id.button_setting)
        communicator = activity as CommunicatorMenu
        btnSetting.setOnClickListener {
            communicator.replaceFragment(MenuSettingFragment())
        }
        val btnSupport: Button = view.findViewById(R.id.button_support)
        communicator = activity as CommunicatorMenu
        btnSupport.setOnClickListener {
            communicator.replaceFragment(MenuSupportFragment())
        }

       return view
    }


    fun logout(view: View)
    {
        var context: Context? = getContext()
        var t = Intent(context, HomeActivity::class.java)
        startActivity(t)
    }

}