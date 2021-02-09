package com.example.antipodpiska.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.antipodpiska.FirebaseApplication
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.databinding.ActivitySignupBinding
import com.example.antipodpiska.subList.SubListActivity
import com.example.antipodpiska.ui.home.HomeActivity
import com.example.antipodpiska.utils.startHomeActivity
import com.example.antipodpiska.utils.startSubListActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class SignupActivity : AppCompatActivity(), AuthListener, KodeinAware {

    override val kodein by lazy { (applicationContext as FirebaseApplication).kodein }
    private val factory : AuthViewModelFactory by instance()
    private lateinit var tempEmail: EditText
    private lateinit var tempPassword: EditText
    private lateinit var tempNickname: EditText


    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
       val buttonReg: Button = findViewById(R.id.button3)
       tempEmail = findViewById(R.id.text_email)
       tempPassword = findViewById(R.id.edit_text_password)
       tempNickname = findViewById(R.id.edit_text_nickname)

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

    }

fun onClick(view: View){

    tempEmail = findViewById(R.id.text_email)
    tempPassword = findViewById(R.id.edit_text_password)
    tempNickname = findViewById(R.id.edit_text_nickname)
    val shared: SharedPrefSource = SharedPrefSource(this)

    shared.addTempUser(tempEmail.text.toString(), tempPassword.text.toString(), tempNickname.text.toString(), this)

}

    override fun onStarted() {

        tempEmail = findViewById(R.id.text_email)
        tempPassword = findViewById(R.id.edit_text_password)
        tempNickname = findViewById(R.id.edit_text_nickname)
        val shared: SharedPrefSource = SharedPrefSource(this)

        shared.addTempUser(tempEmail.text.toString(), tempPassword.text.toString(), tempNickname.text.toString(), this)

        progressbar.visibility = View.VISIBLE
        Intent(this, SubListActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    override fun onSuccess() {

     /*   tempEmail = findViewById(R.id.text_email)
        tempPassword = findViewById(R.id.edit_text_password)
        tempNickname = findViewById(R.id.edit_text_nickname)
        val shared: SharedPrefSource = SharedPrefSource(this)

        shared.addTempUser(tempEmail.text.toString(), tempPassword.text.toString(), tempNickname.text.toString(), this)*/
        progressbar.visibility = View.GONE
        startSubListActivity()
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun addUserInFirebase(email: String, password: String, nickname: String) {
        val firebaseFirestore: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }

        val userItem = HashMap<String, String>()
        val token =  FirebaseInstanceId.getInstance().token
        if (token != null) {
            userItem.put("token", token)
        }

        userItem.put("email", email)
        userItem.put("password", password)
        userItem.put("nickname", nickname)
        var checkAvailabilityId = 0




        while (checkAvailabilityId != 1) {

            if (FirebaseAuth.getInstance().currentUser?.uid.toString() != "null") {


                firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userItem)
                    .addOnCompleteListener {

                        if (it.isSuccessful)
                        {

                        }
                        else{

                        }
                    }
                checkAvailabilityId += 1
            }
        }
    }
}

