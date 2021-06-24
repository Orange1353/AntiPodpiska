package com.example.antipodpiska.ui.auth

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.antipodpiska.FirebaseApplication
import com.example.antipodpiska.R
import com.example.antipodpiska.databinding.ActivityLoginBinding
import com.example.antipodpiska.subList.SubListActivity
import com.example.antipodpiska.utils.startSubListActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
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

        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        viewModel.authListener = this

        viewModel.deleteSharedAfterSignIn(this)

        val btnBack: Button = findViewById(R.id.button_back)
        btnBack.setOnClickListener {
            onBackPressed()
        }
        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getResources().getColor(R.color.blue_back))

        val layBottomSheet = findViewById<LinearLayout>(R.id.container_sheet)
        val appear: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        val gone: Animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        var bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>(
                layBottomSheet
        )
        var image:ImageView = findViewById(R.id.imageView4)
        var textSign: TextView = findViewById(R.id.text_sign)
        var imageBlue:ImageView = findViewById(R.id.pieces)
        var isReg: Boolean = false
        var view:ConstraintLayout = findViewById(R.id.relativeLayout3)

        bottomSheetBehavior.isGestureInsetBottomIgnored =true
        window.setNavigationBarColor(getResources().getColor(R.color.blue_dark))

        var value0 = 0f
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(@NonNull view: View, i: Int) {
            }

            override fun onSlide(@NonNull view: View, value: Float) {
                if (value >= 1) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.setStatusBarColor(getResources().getColor(R.color.blue_dark))
                    textSign.setVisibility(View.INVISIBLE)
                    isReg = true
                    window.setNavigationBarColor(getResources().getColor(R.color.blue_back))

                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.setStatusBarColor(getResources().getColor(R.color.blue_back))
                    textSign.setVisibility(View.VISIBLE)
                    isReg = false
                    window.setNavigationBarColor(getResources().getColor(R.color.blue_dark))
                }

                /*     if(v <=0.3)
                {
                    imageBlue.startAnimation(appear)
                    imageBlue.isVisible = true
                }*/

                image.isVisible = value <= 0.4
                textSign.isVisible = value <= 0.05
                imageBlue.isVisible = value <= 0.99


                if (value > 0.95 && value0 < value) {
                    imageBlue.startAnimation(gone)
                    imageBlue.isVisible = false
                }


                if (value > 0.01f && value < 0.1)
                    if (value0 < value)
                        textSign.startAnimation(gone)
                    else
                        textSign.startAnimation(appear)

                value0 = value
            }
        })


        getWindow().getDecorView().getRootView().viewTreeObserver.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            val r = Rect()
            getWindow().getDecorView().getRootView().getWindowVisibleDisplayFrame(r)
            val heightDiff: Int = getWindow().decorView.getRootView().getHeight() - (r.bottom - r.top)
            if (heightDiff > 300 && isReg == false) { // if more than 100 pixels, its probably a keyboard...
                //ok now we know the keyboard is up...
                layBottomSheet.setVisibility(View.INVISIBLE)
                textSign.setVisibility(View.INVISIBLE)
            } else {
                //ok now we know the keyboard is down...
                layBottomSheet.setVisibility(View.VISIBLE)
                textSign.setVisibility(View.VISIBLE)
            }
            if(isReg)
                textSign.setVisibility(View.INVISIBLE)

        })



    }


    override fun onStarted() {

        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE

        Intent(this, SubListActivity::class.java).also {
            it.putExtra("fromLogin", "login")
            startActivity(it)
        }
   //     startSubListActivity()
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