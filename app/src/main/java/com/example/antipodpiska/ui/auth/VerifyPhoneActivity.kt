package com.example.antipodpiska.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R
import com.example.antipodpiska.subList.SubListActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

/*
class VerifyPhoneActivity : AppCompatActivity() {
    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var editText: EditText? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressbar)
        editText = findViewById(R.id.editTextCode)
        /*    val phonenumber = intent.getStringExtra("phonenumber")

        sendVerificationCode(phonenumber)
        findViewById<View>(R.id.buttonSignIn).setOnClickListener(View.OnClickListener {
            val code = editText?.getText().toString().trim { it <= ' ' }
            if (code.isEmpty() || code.length < 6) {
                editText?.setError("Enter code...")
                editText?.requestFocus()
                return@OnClickListener
            }
            verifyCode(code)
        })*/
        auth = FirebaseAuth.getInstance()

        val storedVerificationId = intent.getStringExtra("storedVerificationId")

//        Reference
        val verify = findViewById<Button>(R.id.buttonSignIn)
        val otpGiven = findViewById<EditText>(R.id.editTextCode)


        verify.setOnClickListener {
            var otp = otpGiven.text.toString().trim()
            if (!otp.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@VerifyPhoneActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                                this@VerifyPhoneActivity,
                                task.exception!!.message,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(applicationContext, SubListActivity::class.java))
                        finish()
// ...
                    } else {
// Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
    }
}

*/


class VerifyPhoneActivity : AppCompatActivity() {
    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null
    private var progressBar: ProgressBar? = null
    private var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        mAuth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressbar)
        editText = findViewById(R.id.editTextCode)
        val phonenumber = intent.getStringExtra("phonenumber")
        sendVerificationCode(phonenumber)
        findViewById<View>(R.id.buttonSignIn).setOnClickListener(View.OnClickListener {
            val code = editText?.getText().toString().trim { it <= ' ' }
            if (code.isEmpty() || code.length < 6) {
                editText?.setError("Enter code...")
                editText?.requestFocus()
                return@OnClickListener
            }
            verifyCode(code)
        })
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@VerifyPhoneActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@VerifyPhoneActivity,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun sendVerificationCode(number: String?) {
        progressBar!!.visibility = View.VISIBLE
     /*   val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(number!!)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)*/
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number!!,
            60,
            TimeUnit.SECONDS,
            this,
            mCallBack
        )
    }

    private val mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    editText!!.setText(code)
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@VerifyPhoneActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
}