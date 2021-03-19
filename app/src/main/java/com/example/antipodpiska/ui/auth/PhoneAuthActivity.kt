package com.example.antipodpiska.ui.auth


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R
import com.example.antipodpiska.generated.callback.OnClickListener
import com.example.antipodpiska.subList.SubListActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

/*
class PhoneAuthActivity : AppCompatActivity() {
    private var spinner: Spinner? = null
    private var editText: EditText? = null
    private var buttonContinue: Button?= null


    lateinit var storedVerificationId:String

    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_login_phone)
        /*  spinner= findViewById(R.id.spinnerCountries)
   //       buttonContinue=findViewById(R.id.buttonContinue)
          spinner?.setAdapter(ArrayAdapter<String>(
                  this,
                  R.layout.support_simple_spinner_dropdown_item,
                  CountryDataDelete.countryNames
              )
          )
          editText = findViewById(R.id.editTextPhone)

  */

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, SubListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }


        val Login=findViewById<Button>(R.id.buttonContinue)

        Login.setOnClickListener{
            login()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, SubListActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                Log.d("Failed",e.toString())
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext,VerifyPhoneActivity::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }
    }

    private fun login() {
        val mobileNumber=findViewById<EditText>(R.id.editTextPhone)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+7"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }



    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun onClickContinue(v: View?) {
        val code: String = CountryDataDelete.countryAreaCodes.get(spinner!!.getSelectedItemPosition())
        val number = editText?.getText().toString().trim { it <= ' ' }
        if (number.isEmpty() || number.length < 10) {
            editText?.setError("Valid number is required")
            editText?.requestFocus()
            return
        }
        val phoneNumber = "+$code$number"
        val intent = Intent(this@PhoneAuthActivity, VerifyPhoneActivity::class.java)
        intent.putExtra("phonenumber", phoneNumber)
        startActivity(intent)
    }
}


*/








class PhoneAuthActivity : AppCompatActivity() {
    private var spinner: Spinner? = null
    private var editText: EditText? = null
    private var buttonContinue: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_login_phone)
        spinner= findViewById(R.id.spinnerCountries)
        buttonContinue=findViewById(R.id.buttonContinue)
        spinner?.setAdapter(ArrayAdapter<String>(
                this,
                R.layout.spinner_dropdown_dark,
                CountryDataDelete.countryAreaCodes
            )
        )
        editText = findViewById(R.id.editTextPhone)

        val btnBack: Button = findViewById(R.id.button_back)
        btnBack.setOnClickListener {
            onBackPressed()
        }

    }


    fun onClickContinue(v: View?) {
        val code: String = spinner!!.selectedItem.toString()
        val number = editText?.getText().toString().trim { it <= ' ' }
        if (number.isEmpty() || number.length < 10) {
            editText?.setError("Valid number is required")
            editText?.requestFocus()
            return
        }



        val phoneNumber = "$code$number"
        val intent = Intent(this@PhoneAuthActivity, VerifyPhoneActivity::class.java)
        intent.putExtra("phonenumber", phoneNumber)
        startActivity(intent)
    }
}