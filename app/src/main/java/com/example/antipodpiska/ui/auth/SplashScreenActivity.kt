package com.example.antipodpiska.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.antipodpiska.R
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SUB_ID

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val buttonIn : Button = findViewById(R.id.button_in)

        buttonIn.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity()::class.java)
            startActivity(intent)
        }
    }
}