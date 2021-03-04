package com.example.antipodpiska.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.antipodpiska.FirebaseApplication
import com.example.antipodpiska.R
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.utils.startSubListActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity() , AuthListener, KodeinAware {

    private lateinit var viewModel: AuthViewModel
    private val factory : AuthViewModelFactory by instance()
    override val kodein by lazy { (applicationContext as FirebaseApplication).kodein }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)

        viewModel.authListener = this



        val buttonIn : Button = findViewById(R.id.button_in)

        buttonIn.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity()::class.java)
            startActivity(intent)
        }
    }
    override fun onStarted() {

        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        startSubListActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.user?.let {
            startSubListActivity()
        }
    }


}