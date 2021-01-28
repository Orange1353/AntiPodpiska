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



import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R

import com.example.antipodpiska.subList.SUB_ID
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

if (currentSub?.datePay!= null && currentSub?.datePay != "") {
    var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    var dateEnd = LocalDate.parse(currentSub?.datePay, formatter)

    var dateNow = LocalDate.now()

    if (currentSub?.periodFree != "")
        when (currentSub?.periodTypeFree) {
            "Days" -> dateEnd = dateEnd.plusDays(currentSub?.periodFree.toLong())
            "Weeks" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodFree.toLong())
            "Mounths" -> dateEnd = dateEnd.plusMonths(currentSub?.periodFree.toLong())
        }

    if (currentSub?.costSub != "")
    { var dateEndAfterFree = dateEnd
    var counter_Summ = -currentSub?.costSub.toInt()
    while (dateEndAfterFree < dateNow) {
        when (currentSub?.periodTypePay) {
            "Days" -> dateEndAfterFree = dateEndAfterFree.plusDays(currentSub?.periodPay.toLong())
            "Weeks" -> dateEndAfterFree = dateEndAfterFree.plusWeeks(currentSub?.periodPay.toLong())
            "Mounths" -> dateEndAfterFree =
                dateEndAfterFree.plusMonths(currentSub?.periodPay.toLong())
        }
        counter_Summ += currentSub?.costSub.toInt()
    }

        if (counter_Summ < 0)
            counter_Summ = 0
    paidSumm.text = "Оплачено уже " + counter_Summ.toString()
}

         //   while (dateEnd < dateNow)
    if (currentSub.periodPay != "") {
        when (currentSub?.periodTypePay) {
            "Days" -> dateEnd = dateEnd.plusDays(currentSub?.periodPay.toLong())
            "Weeks" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodPay.toLong())
            "Mounths" -> dateEnd = dateEnd.plusMonths(currentSub?.periodPay.toLong())
        }

        while (dateEnd < dateNow)
            when (currentSub?.periodTypePay) {
                "Days" -> dateEnd = dateEnd.plusDays(currentSub?.periodPay.toLong())
                "Weeks" -> dateEnd = dateEnd.plusWeeks(currentSub?.periodPay.toLong())
                "Mounths" -> dateEnd = dateEnd.plusMonths(currentSub?.periodPay.toLong())
            }
    }
            dateNearestPay.text = "Следующий платёж " + dateEnd.format(formatter).toString()
        }

            if (currentSub?.card != "")
            card.text = "Привязанная карта " + "*" + currentSub?.card

            if (currentSub?.periodFree != "")
            freePeriod.text = "Бесплатный период " + currentSub?.periodFree + " " + currentSub?.periodTypeFree
            //paidSumm.text = currentSub?.








            removeSubButton.setOnClickListener {
                if (currentSub != null) {
                    subDetailViewModel.removeFlower(currentSub, this)
                }
                finish()
            }
        }

    }
}