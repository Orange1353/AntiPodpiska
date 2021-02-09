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



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.EditActivity
import com.example.antipodpiska.addition.EditSubActivity

import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.subList.SubListActivity
import com.example.antipodpiska.utils.startSubListActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class SubDetailActivity : AppCompatActivity() {

    private val subDetailViewModel by viewModels<SubDetailViewModel> {
        SubDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_detail)

        var currentSubId: Long? = null

        /* Connect variables to UI elements. */
        val subName: TextView = findViewById(R.id.flower_detail_name)
        val subImage: ImageView = findViewById(R.id.flower_detail_image)
        val subDescription: TextView = findViewById(R.id.flower_detail_description)
        val removeSubButton: Button = findViewById(R.id.remove_button)
        val costPlusPeriod: TextView =  findViewById(R.id.cost_plus_period)
        val dateNearestPay: TextView = findViewById(R.id.data_nearest_pay)
        val card: TextView = findViewById(R.id.card_pay)
        val freePeriod:TextView = findViewById(R.id.free_period)
        val paidSumm: TextView = findViewById(R.id.paid_summ)
        val pushEnabled: SwitchCompat = findViewById(R.id.switch_enabled)
        val editButton: Button = findViewById(R.id.edit_button)




        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentSubId = bundle.getLong(SUB_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentSubId?.let {
            val currentSub = subDetailViewModel.getFlowerForId(it)
            subName.text = currentSub?.name
            if (currentSub?.image == null) {
                subImage.setImageResource(R.drawable.img)
            } else {
                subImage.setImageResource(currentSub.image)
            }

            subDescription.text = currentSub?.description
            if (currentSub?.costSub != "" && currentSub?.periodPay != "")
                costPlusPeriod.text = "Оплата " + currentSub?.costSub + " " + currentSub?.costCurr + " каждые " + currentSub?.periodPay + " " + currentSub?.periodTypePay

            if (currentSub?.datePay != null && currentSub?.datePay != "") {
                var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                var dateEnd = LocalDate.parse(currentSub?.datePay, formatter)

                var dateNow = LocalDate.now()

                var costSub = 0
                var summCost = 0

                if (currentSub?.costSub != "")
                    costSub = currentSub?.costSub.toInt()

                if (currentSub?.periodFree != "")
                    when (currentSub?.periodTypeFree) {
                        "Days" -> dateEnd = dateEnd.plusDays(currentSub?.periodFree.toLong())
                        "Weeks" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodFree.toLong())
                        "Mounths" -> dateEnd = dateEnd.plusMonths(currentSub?.periodFree.toLong())
                    }


                if (currentSub?.periodPay != "")
                while (dateEnd < dateNow) {
                    summCost += costSub
                    when (currentSub?.periodTypePay) {
                        "Days" -> dateEnd = dateEnd.plusDays(currentSub?.periodPay.toLong())
                        "Weeks" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodPay.toLong())
                        "Mounths" -> dateEnd = dateEnd.plusMonths(currentSub?.periodPay.toLong())
                    }
                }
                dateNearestPay.text = "Следующий платёж " + dateEnd.format(formatter).toString()
                paidSumm.text = "Оплачено уже " + summCost.toString() + " " + currentSub?.costCurr
            }

            if (currentSub?.card != "")
                card.text = "Привязанная карта " + "*" + currentSub?.card

            if (currentSub?.periodFree != "")
                freePeriod.text = "Бесплатный период " + currentSub?.periodFree + " " + currentSub?.periodTypeFree
            //paidSumm.text = currentSub?.

            if (currentSub?.pushEnabled == true) {
                pushEnabled.text = "Включены  "
                pushEnabled.isChecked = true
            } else {
                pushEnabled.text = "Выключены"
                pushEnabled.isChecked = false
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


            pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

                if (currentSub != null)
                    subDetailViewModel.pushAboutSub(currentSub)

                if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены"
                else
                    pushEnabled.text = "Выключены"

            }


        }

    }
}