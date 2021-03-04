package com.example.antipodpiska.subDetails

/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.EditActivity
import com.example.antipodpiska.addition.EditSubActivity

import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.subList.SubListActivity
import com.example.antipodpiska.utils.startSubListActivity
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.element_detail_any_text.view.*
import kotlinx.android.synthetic.main.element_detail_cost_only.view.*
import kotlinx.android.synthetic.main.element_detail_cost_only.view.text_cost_details
import kotlinx.android.synthetic.main.element_detail_with_free.view.*
import kotlinx.android.synthetic.main.element_next_pay.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class SubDetailActivity : AppCompatActivity() {

    private var summCost: Int = 0
    private val subDetailViewModel by viewModels<SubDetailViewModel> {
        SubDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_detail_base)

        var currentSubId: Long? = null

        /* Connect variables to UI elements. */
        val subName: TextView = findViewById(R.id.flower_detail_name)
        val subImage: TextView = findViewById(R.id.flower_detail_image)
        val backButton: Button = findViewById(R.id.button_back)
       // val subDescription: TextView = findViewById(R.id.flower_detail_description)
        val removeSubButton: Button = findViewById(R.id.remove_button)
        val editButton: Button = findViewById(R.id.edit_button)

   //     val dateNearestPay: TextView = findViewById(R.id.data_nearest_pay)
   /*     val costPlusPeriod: TextView =  findViewById(R.id.cost_plus_period)
        val paidSumm: TextView = findViewById(R.id.paid_summ)
        val card: TextView = findViewById(R.id.card_pay)
        val freePeriod:TextView = findViewById(R.id.free_period)

        val pushEnabled: SwitchCompat = findViewById(R.id.switch_enabled)
*/

        val inflater = applicationContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val container = findViewById<View>(R.id.base_lay) as LinearLayout


        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentSubId = bundle.getLong(SUB_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentSubId?.let {
            val currentSub = subDetailViewModel.getFlowerForId(it)
            subName.text = currentSub?.name


                val radius: Float = 20F
                val shapeAppearanceModel = ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, radius)
                        .build()
                val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                var t = Color.parseColor("#"+ currentSub!!.image)
                shapeDrawable.setTint(t)
                ViewCompat.setBackground(subImage, shapeDrawable)
                subImage.setGravity(Gravity.CENTER)
                var firstLetter: String = currentSub!!.name.substring(0, 1)
                firstLetter=firstLetter.toUpperCase()
                subImage.setText(firstLetter)
           // subImage.setBackgroundColor(Color.parseColor("#" + currentSub.image))





            if (currentSub?.datePay != null && currentSub?.datePay != "") {
                var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                var dateEnd = LocalDate.parse(currentSub?.datePay, formatter)

                var dateNow = LocalDate.now()

                var costSub = 0
                summCost = 0

                if (currentSub?.costSub != "")
                    costSub = currentSub?.costSub.toInt()

                if (currentSub?.periodFree != "")
                    when (currentSub?.periodTypeFree) {
                        "Дней" -> dateEnd = dateEnd.plusDays(currentSub?.periodFree.toLong())
                        "Недель" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodFree.toLong())
                        "Месяцев" -> dateEnd = dateEnd.plusMonths(currentSub?.periodFree.toLong())
                    }


                if (currentSub?.periodPay != "")
                while (dateEnd < dateNow) {
                    summCost += costSub
                    when (currentSub?.periodTypePay) {
                        "Дней" -> dateEnd = dateEnd.plusDays(currentSub?.periodPay.toLong())
                        "Недель" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodPay.toLong())
                        "Месяцев" -> dateEnd = dateEnd.plusMonths(currentSub?.periodPay.toLong())
                    }
                }



                val dateNearestPay: View = inflater.inflate(R.layout.element_next_pay, null)
                val container = findViewById<View>(R.id.base_lay) as LinearLayout
                dateNearestPay.nextPay.text = "Следующий платёж " + dateEnd.format(formatter).toString()
                container.addView(dateNearestPay)

            }


            if (currentSub?.periodFree != "")
            {
                if (currentSub?.costSub != "") {
                    val costPlusPeriod: View = inflater.inflate(R.layout.element_detail_with_free, null)

                    if (currentSub?.periodPay != "") {
                        costPlusPeriod.text_cost_details.text = currentSub?.costSub + " " + currentSub?.costCurr + " каждые " + currentSub?.periodPay + " " + currentSub?.periodTypePay
                        container.addView(costPlusPeriod)
                    }
                    else
                    {
                        costPlusPeriod.text_cost_details.text = currentSub?.costSub + " " + currentSub?.costCurr
                        container.addView(costPlusPeriod)
                    }
                    costPlusPeriod.text_before_free.text = currentSub?.periodFree + " " + currentSub?.periodTypeFree

                }

            }
            else
            if (currentSub?.costSub != "") {

                if (currentSub?.periodPay != "") {
                    val costPlusPeriod: View = inflater.inflate(R.layout.element_detail_cost_only, null)
                    costPlusPeriod.text_cost_details.text = currentSub?.costSub + " " + currentSub?.costCurr + " каждые " + currentSub?.periodPay + " " + currentSub?.periodTypePay
                    container.addView(costPlusPeriod)
                }
                else
                {
                    val costPlusPeriod: View = inflater.inflate(R.layout.element_detail_cost_only, null)
                    costPlusPeriod.text_cost_details.text = currentSub?.costSub + " " + currentSub?.costCurr
                    container.addView(costPlusPeriod)
                }

            }





          if (currentSub?.card != "") {
              val card: View = inflater.inflate(R.layout.element_detail_any_text, null)
              card.name_field.text = "Привязанная карта"
              card.value_field.text = "*" + currentSub?.card
              container.addView(card)
          }

            val paidSumm: View = inflater.inflate(R.layout.element_detail_any_text, null)
            paidSumm.name_field.text = "Оплачено уже"
            paidSumm.value_field.text = summCost.toString() + " " + currentSub?.costCurr
            container.addView(paidSumm)

    /*

            if (currentSub?.pushEnabled == true) {
                pushEnabled.text = "Включены  "
                pushEnabled.isChecked = true
            } else {
                pushEnabled.text = "Выключены"
                pushEnabled.isChecked = false
            }
*/
            backButton.setOnClickListener {
                onBackPressed()
                finish()
            }

            editButton.setOnClickListener {
                if (currentSub != null) {

                    Intent(this, EditSubActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        it.putExtra(SUB_ID, currentSubId)
                        startActivity(it)
                    }
                }
                finish()
            }

            removeSubButton.setOnClickListener {
                if (currentSub != null) {
                    subDetailViewModel.removeFlower(currentSub, this)
                }
                finish()
            }


       /*     pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

                if (currentSub != null)
                    subDetailViewModel.pushAboutSub(currentSub)

                if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены"
                else
                    pushEnabled.text = "Выключены"

            }
*/

        }

    }
}