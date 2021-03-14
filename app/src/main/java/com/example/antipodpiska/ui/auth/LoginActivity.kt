package com.example.antipodpiska.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.antipodpiska.FirebaseApplication
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.databinding.ActivityLoginBinding
import com.example.antipodpiska.utils.startSubListActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance


//https://stackoverflow.com/questions/65561826/how-can-firebase-phone-auth-be-implemented-in-view-model

class LoginActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by lazy { (applicationContext as FirebaseApplication).kodein }
    private val factory : AuthViewModelFactory by instance()


    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.deleteSharedAfterSignIn(this)

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