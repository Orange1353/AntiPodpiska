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



import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.antipodpiska.R
import com.example.antipodpiska.addition.EditSubActivity
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.subList.getRGB
import com.example.antipodpiska.utils.startSubListActivity
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_detail_base.*
import kotlinx.android.synthetic.main.element_detail_any_text.view.*
import kotlinx.android.synthetic.main.element_detail_with_free.view.*
import kotlinx.android.synthetic.main.element_next_pay.view.*
import java.lang.invoke.ConstantCallSite
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class SubDetailActivity : AppCompatActivity() {

    private var summCost: Int = 0
    private val subDetailViewModel by viewModels<SubDetailViewModel> {
        SubDetailViewModelFactory(this)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_detail_base)

        var currentSubId: Long? = null

        /* Connect variables to UI elements. */
        val subName: TextView = findViewById(R.id.flower_detail_name)
        val subImage: ImageView = findViewById(R.id.flower_detail_image)
        val backButton: Button = findViewById(R.id.button_back)
        val unSubButton: Button = findViewById(R.id.button_unSub)
        val mainLayout: ConstraintLayout = findViewById(R.id.main_lay_detail)
        var textFree: TextView = findViewById(R.id.text_before_free)
        var textCost: TextView = findViewById(R.id.text_cost_details)
        var nextPay: TextView = findViewById(R.id.nextPay)
        var paidSumm: TextView = findViewById(R.id.paid_summ)
        var tiedCard: TextView = findViewById(R.id.detail_card)
        var timeStart: TextView = findViewById(R.id.time_start)
        var progressBar: ProgressBar = findViewById(R.id.progress)
        var layFree: LinearLayout = findViewById(R.id.frameLayoutFree)
        var layCost: LinearLayout = findViewById(R.id.frameLayoutCost)
        var textFreeUp: TextView = findViewById(R.id.text_free)
        var textCostUp: TextView = findViewById(R.id.text_cost)
        var dayBeforePay: TextView = findViewById(R.id.day_before_pay)

        var laydelete1: LinearLayout = findViewById(R.id.delete_archive1)
        var laydelete2: LinearLayout = findViewById(R.id.delete_archive2)

        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()?.setTitle(null)

        val menuBuilder = toolbar.menu as MenuBuilder
        menuBuilder.setOptionalIconsVisible(true)

        val inflater = applicationContext.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val container = findViewById<View>(R.id.base_lay) as LinearLayout

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentSubId = bundle.getLong(SUB_ID)
        }

        var useBlack = true

        currentSubId?.let {
            val currentSub = subDetailViewModel.getFlowerForId(it)
            subName.text = currentSub?.name


            if ( currentSub!!.color!= 0 &&  currentSub.color != -1)
                useBlack = getRGB(this.getResources().getString( currentSub.color))
            else
                useBlack = getRGB("#FFFAFBFC")

            if (useBlack)
            { subName.setTextColor(this.resources.getColor(R.color.black_light))
                dayBeforePay.setTextColor(this.resources.getColor(R.color.black_light))
            }
            else {
                subName.setTextColor(this.resources.getColor(R.color.white))
                dayBeforePay.setTextColor(this.resources.getColor(R.color.white))
            }
            val dateNearestPay: View = inflater.inflate(R.layout.element_next_pay, null)
            val container = findViewById<View>(R.id.base_lay) as LinearLayout

            mainLayout.setBackgroundColor(this.getColor(currentSub!!.color))
            window.setStatusBarColor(getResources().getColor(currentSub!!.color))


            if (currentSub!!.imageDrawable != -1)
                subImage.setImageResource(currentSub.imageDrawable)

            timeStart.text = currentSub.datePay

           /* else {
                val radius: Float = 20F
                val shapeAppearanceModel = ShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED, radius)
                    .build()
                val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                var t = Color.parseColor("#" + currentSub!!.image)
                shapeDrawable.setTint(t)
                ViewCompat.setBackground(subImage, shapeDrawable)
                subImage.setGravity(Gravity.CENTER)
                var firstLetter: String = currentSub!!.name.substring(0, 1)
                firstLetter = firstLetter.toUpperCase()
                subImage.setText(firstLetter)
            }*/

            if(currentSub?.status == "Архив")
            {
                layCost.isVisible = false
                layFree.isVisible = false
                laydelete1.isVisible = false
                laydelete2.isVisible = false

                dateNearestPay.nextPaytext.text = "Подписка находится в архиве"
                dateNearestPay.nextPaytext.setTextColor(getResources().getColor(R.color.grey2_light))
                container.addView(dateNearestPay)


                if (currentSub?.costSub != "") {


                if (currentSub?.periodPay != "") {



                    val tmp = rusification(currentSub?.periodPay, currentSub?.periodTypePay)


                    textCost.text =
                        currentSub?.costSub + " " + currentSub?.costCurr + " " + rusificationEvery(tmp) +" " + currentSub?.periodPay + " " + tmp

                } else {

                    textCost.text =
                        currentSub?.costSub + " " + currentSub?.costCurr
                }

            }

                val description: View = inflater.inflate(R.layout.element_detail_any_text, null)
                description.name_field.text = "Описание"
                description.value_field.text = currentSub.description
                container.addView(description)


                unSubButton.text = "Возобновить учёт"

            if (currentSub?.card != "") {


                tiedCard.text = "*" + currentSub?.card

            }
        }



else {
                var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                var dateEndFree = LocalDate.parse(currentSub?.datePay, formatter)
                       if (currentSub?.datePay != null && currentSub?.datePay != "") {

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
                               "Месяцев" -> dateEnd = dateEnd.plusDays(30 * currentSub?.periodFree.toLong())
                           }
                           dateEndFree = dateEnd


                       if (currentSub?.periodPay != "")
                       while (dateEnd < dateNow) {
                           summCost += costSub
                           when (currentSub?.periodTypePay) {
                               "Дней" -> dateEnd = dateEnd.plusDays(currentSub?.periodPay.toLong())
                               "Недель" -> dateEnd =
                                   dateEnd.plusWeeks(currentSub?.periodPay.toLong())
                               "Месяцев" -> dateEnd =
                                   dateEnd.plusDays(30 * currentSub?.periodPay.toLong())
                           }
                       }


                           if(currentSub.datePay != dateEnd.format(formatter).toString())
                               nextPay.text = dateEnd.format(formatter).toString()



                   }


                   if (currentSub?.periodFree != "")
                   {
                       if (currentSub?.costSub != "") {

                           if (currentSub?.periodPay != "") {

                               var tmp = rusification(currentSub?.periodPay, currentSub?.periodTypePay)
                               var tmp1 = tmp
                               if((currentSub?.periodPay + tmp).length > 8)
                                   when (tmp.substring(0, 1)) {
                                       "д" -> tmp1 = "дн."
                                       "н" -> tmp1 = "нед."
                                       "м" -> tmp1 = "мес."
                                   }
                               textCost.text = currentSub?.costSub + " " + currentSub?.costCurr + " " + "\n"+rusificationEvery(tmp) +" " + currentSub?.periodPay + " " + tmp1



                           }
                           else
                           {
                               textCost.text = currentSub?.costSub + " " + currentSub?.costCurr

                           }

                         var dateAdd = LocalDate.parse(currentSub?.datePay, formatter)
                         var progress = ChronoUnit.DAYS.between(dateAdd, dateEndFree)
                         dateAdd.plusDays(progress)
                          // val tmp = rusification(currentSub?.periodFree, currentSub?.periodTypeFree)
                           var t = "до "+ dateAdd.format(formatter).toString()
                           textFree.text = t //currentSub?.periodFree + " " + tmp             //DateAdd + FreePeriod
                           progressBar.progress = progress.toInt()
                           progressBar.max = currentSub.periodPay.toInt()
                           dayBeforePay.text = progress.toString()+" " + rusification(progress.toString(), currentSub.periodTypeFree).toUpperCase() +" до оплаты"
                       }

                       else{
                           layCost.isVisible = false
                           var dateAdd = LocalDate.parse(currentSub?.datePay, formatter)
                           var progress = ChronoUnit.DAYS.between(dateAdd, dateEndFree)
                           dateAdd.plusDays(progress)
                           var t = "до "+ dateAdd.format(formatter).toString()
                           textFree.text = t
                           //val tmp = rusification(currentSub?.periodFree, currentSub?.periodTypeFree)
                          // costPlusPeriod.text_before_free.text = currentSub?.periodFree + " " + tmp
                          // container.addView(costPlusPeriod)

                           progressBar.progress = progress.toInt()
                           progressBar.max = currentSub.periodPay.toInt()

                           dayBeforePay.text = progress.toString()+" " + rusification(progress.toString(), currentSub.periodTypePay).toUpperCase() +" до оплаты"
                       }

                   }
                   else
                   if (currentSub?.costSub != "") {
                       if (currentSub?.periodPay != "") {
                           layFree.visibility = GONE
                           val tmp = rusification(currentSub?.periodPay, currentSub?.periodTypePay)
                           textCost.text = currentSub?.costSub + " " + currentSub?.costCurr + " " + rusificationEvery(tmp) +" " + currentSub?.periodPay + " " + tmp
                       }
                       else {
                           layFree.visibility = GONE
                           textCost.text = currentSub?.costSub + " " + currentSub?.costCurr
                       }
                   }

                 if (currentSub?.card != "") {

                     tiedCard.text = "*" + currentSub?.card

                 }

                   paidSumm.text = summCost.toString() + " " + currentSub?.costCurr


             /*   val description: View = inflater.inflate(R.layout.element_detail_any_text, null)
                description.name_field.text = "Описание"
                description.value_field.text = currentSub.description
                container.addView(description)*/
            }

            backButton.setOnClickListener {
                onBackPressed()
                finish()
            }

            unSubButton.setOnClickListener {

                if (currentSub.status == "Архив"){
                    val newSub = clone(currentSub)
                    newSub!!.status = "Активна"

                    subDetailViewModel.editSub(currentSub, newSub!!, this)
                    Toast.makeText(
                        this,
                        "Готово, вы можете отредактировать подписку при необходимости",
                        Toast.LENGTH_LONG
                    ).show()

                }
                else
                {
                val newSub = clone(currentSub)
                newSub?.status = "Архив"

                   // var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    var timeNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    newSub?.datePay = timeNow


                 subDetailViewModel.editSub(currentSub, newSub!!, this)

                Toast.makeText(this, "Подписка в Архиве", Toast.LENGTH_SHORT).show()
            }
                finish()
                startSubListActivity()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actions_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.edit_sub -> {
            val bundle: Bundle? = intent.extras
            val currentSubId = bundle?.getLong(SUB_ID)
            val currentSub = subDetailViewModel.getFlowerForId(currentSubId!!)

            if (currentSub != null) {

                Intent(this, EditSubActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    it.putExtra(SUB_ID, currentSubId)
                    startActivity(it)
                }
            }
            finish()

            true
        }

        R.id.delete_sub -> {
            val bundle: Bundle? = intent.extras
            val currentSubId = bundle?.getLong(SUB_ID)
            val currentSub = subDetailViewModel.getFlowerForId(currentSubId!!)
            if (currentSub != null) {
                subDetailViewModel.removeFlower(currentSub, this)
            }
            finish()
            true
        }

        else -> {

            super.onOptionsItemSelected(item)
        }


    }



    fun rusification(period: String, periodType: String): String {

        var tmp: String = ""
        if (periodType == "Месяцев") {
            when
            {
                period.toInt() % 10 == 1 -> tmp = "месяц"
              //  period.substring(period.length-3, period.length-1)
                period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0-> tmp = "месяцев"
                period.toInt() % 10 in 2..4 -> tmp = "месяца"
            }
        }
       else if (periodType == "Дней") {
            when
            {
                period.toInt() % 10 == 1 -> tmp = "день"
                //  period.substring(period.length-3, period.length-1)
                period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 -> tmp = "дней"
                period.toInt() % 10 in 2..4 -> tmp = "дня"
            }
        }
        else if (periodType == "Недель") {
            when
            {
                period.toInt() % 10 == 1 -> tmp = "неделю"
                //  period.substring(period.length-3, period.length-1)
                period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 -> tmp = "недель"
                period.toInt() % 10 in 2..4 -> tmp = "недели"
            }
        }

        return tmp
    }

    fun rusificationEvery(tmp: String): String{

        when{

           tmp == "день" || tmp == "месяц"-> return "каждый"
           tmp == "дня"  || tmp == "недели"|| tmp == "месяца" || tmp == "дней"|| tmp == "недель" || tmp == "месяцев"-> return "каждые"
           tmp == "недели"|| tmp == "месяца"-> return "каждые"
           tmp == "неделю"-> return "каждую"
            else-> return ""
        }

    }

    fun clone(sub: Sub): Sub? {
        val stringSub = Gson().toJson(sub, Sub::class.java)
        return Gson().fromJson<Sub>(stringSub, Sub::class.java)
    }



}