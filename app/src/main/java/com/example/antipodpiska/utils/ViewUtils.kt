package com.example.antipodpiska.utils

import android.content.Context
import android.content.Intent
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SubListActivity
import com.example.antipodpiska.ui.auth.LoginActivity
import com.example.antipodpiska.ui.auth.PhoneAuthActivity
import com.example.antipodpiska.ui.auth.SignupActivity
import com.example.antipodpiska.ui.auth.SplashScreenActivity
import com.example.antipodpiska.ui.home.HomeActivity

fun Context.startHomeActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startSignupActivity() =
    Intent(this, SignupActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startSubListActivity() =
    Intent(this, SubListActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startPhoneLoginActivity() =
    Intent(this, PhoneAuthActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startSplashScreenActivity() =
        Intent(this, SplashScreenActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
fun Context.startSubDetailActivity() =
    Intent(this, SubDetailActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }