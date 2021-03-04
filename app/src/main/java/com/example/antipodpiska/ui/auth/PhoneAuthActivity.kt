package com.example.antipodpiska.ui.auth


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R
import com.example.antipodpiska.generated.callback.OnClickListener
import com.google.firebase.auth.FirebaseAuth


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