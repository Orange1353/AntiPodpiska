package com.example.antipodpiska.menu

import androidx.fragment.app.Fragment
import com.example.antipodpiska.data.User

interface CommunicatorMenu {
    fun replaceFragment(fragment: Fragment)
    fun editProfile(userNew: User)
    fun onBackPressed()
}