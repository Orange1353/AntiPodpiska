package com.example.antipodpiska.addition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.antipodpiska.R


class AddSubActivityFragments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub_fragments)

        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val fragmentTransaction: FragmentTransaction = fragmentManager
            .beginTransaction()

        // добавляем фрагмент

        // добавляем фрагмент
        val myFragment = CreateNameAndTypeFragment()
        fragmentTransaction.add(R.id.container, myFragment)
        fragmentTransaction.commit()


    }
}