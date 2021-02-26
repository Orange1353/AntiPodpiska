package com.example.antipodpiska.addition

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.antipodpiska.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class AddSubActivityFragments : AppCompatActivity(), Communicator {

    private var fragmentManager : FragmentManager = getSupportFragmentManager()
    private val PREFS_NAME = FirebaseAuth.getInstance().currentUser?.uid.toString()+"1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub_fragments)

        fragmentManager.beginTransaction().add(R.id.container, CreateNameAndTypeFragment()).commit()

    }

    override fun nameFragmentToPeriodFragment(nameSub: String, descriptionSub: String, typeSub: String){

        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = preferences.edit()

        prefsEditor.putString("name", nameSub).apply();
        prefsEditor.putString("description", descriptionSub).apply();
        prefsEditor.putString("type", typeSub).apply();


        fragmentManager.beginTransaction().replace(R.id.container, CreatePediodFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun periodFragmentToCardFragment(freePeriod: String, periodTypeFree: String,costSub: String, costCurr: String, periodPay: String, periodTypePay: String, dateSub: String){

        var preferences: SharedPreferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = preferences.edit()

        prefsEditor.putString("freePeriod", freePeriod).apply();
        prefsEditor.putString("costSub", costSub).apply();
        prefsEditor.putString("periodPay", periodPay).apply();
        prefsEditor.putString("dateSub", dateSub).apply();

        fragmentManager.beginTransaction().replace(R.id.container, CreateCardFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }








}