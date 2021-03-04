package com.example.antipodpiska.subDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R
import kotlinx.android.synthetic.main.element_detail_cost_only.view.*

class SubDetailActivityBase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_detail_base)

        val inflater = applicationContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val view: View = inflater.inflate(R.layout.element_next_pay, null)
        val container = findViewById<View>(R.id.base_lay) as LinearLayout
        container.addView(view)

        val view2: View = inflater.inflate(R.layout.element_detail_cost_only, null)
        container.addView(view2)
       view2.text_cost_details.text = "Оплачено уже "

        val view3: View = inflater.inflate(R.layout.element_detail_any_text, null)
        container.addView(view3)

    }
}