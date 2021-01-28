package com.example.antipodpiska.addition

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.repositories.UserRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val END_DATE = "date"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"
const val CARD = "card"

class AddSubActivity : AppCompatActivity() {
    private lateinit var addSubName: TextInputEditText
    private lateinit var addSubDescription: TextInputEditText
    private lateinit var addSubEndDate: TextInputEditText
    private lateinit var addDatePay: TextInputEditText
    private lateinit var addPeriodFree: TextInputEditText
    private lateinit var addCostSub: TextInputEditText
    private lateinit var addPeriodPay: TextInputEditText
    private lateinit var addCard: TextInputEditText
    private lateinit var addPeriodTypeFree: Spinner
    private lateinit var addCostCurr: Spinner
    private lateinit var addPeriodTypePay: Spinner




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub)


        findViewById<Button>(R.id.done_button).setOnClickListener {
            addSub()
        }
        addSubName = findViewById(R.id.add_flower_name)
        addSubDescription = findViewById(R.id.add_flower_description)
        addSubEndDate = findViewById(R.id.add_end_date)
        addDatePay =  findViewById(R.id.day_pay)
        addPeriodFree =  findViewById(R.id.add_free_days)
        addCostSub =  findViewById(R.id.add_cost)
        addPeriodPay =  findViewById(R.id.add_period)
        addPeriodTypeFree = findViewById(R.id.spinner_free_period_type)
        addCostCurr = findViewById(R.id.spinner_curr_cost)
        addPeriodTypePay = findViewById(R.id.spinner_period_pay)
        addCard = findViewById(R.id.card)
        /*addSubName
        addSubDescription
        addSubEndDate
        addDatePay
        addPeriodFree
        addCostSub
        addPeriodPay
        addPeriodTypeFree
        addCostCurr
        addPeriodTypePay*/



        //SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListenerEndDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            addSubEndDate.setText(sdf.format(cal.time))

        }
        val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            addDatePay.setText(sdf.format(cal.time))
        }


        addSubEndDate.setOnClickListener {
            DatePickerDialog(this@AddSubActivity, dateSetListenerEndDate,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        addDatePay.setOnClickListener {
            DatePickerDialog(this@AddSubActivity, dateSetListenerDatePay,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }


    }


    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addSub() {
        val resultIntent = Intent()

        if (addSubName.text.isNullOrEmpty() || addSubDescription.text.isNullOrEmpty() || addSubEndDate.text.isNullOrEmpty()) {
           Toast.makeText(this@AddSubActivity, "Заполните перые 3 строки!",  Toast.LENGTH_SHORT).show()
        } else {

            //Sub() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            val name = addSubName.text.toString()
            val description = addSubDescription.text.toString()
            val date= addSubEndDate.text.toString()
            val datePay = addDatePay.text.toString()
            val freePeriod = addPeriodFree.text.toString()
            val cost = addCostSub.text.toString()
            val period = addPeriodPay.text.toString()
            val typeFree = addPeriodTypeFree.selectedItem.toString()
            val currCost = addCostCurr.selectedItem.toString()
            val typePeriod = addPeriodTypePay.selectedItem.toString()
            val card = addCard.text.toString()

            resultIntent.putExtra(SUB_NAME, name)
            resultIntent.putExtra(SUB_DESCRIPTION, description)
            resultIntent.putExtra(END_DATE, date)
            resultIntent.putExtra(DATE_PAY, datePay)
            resultIntent.putExtra(FREE_PERIOD, freePeriod)
            resultIntent.putExtra(COST, cost)
            resultIntent.putExtra(PERIOD, period)
            resultIntent.putExtra(TYPE_FREE, typeFree)
            resultIntent.putExtra(CURR_COST, currCost)
            resultIntent.putExtra(TYPE_PERIOD, typePeriod)
            resultIntent.putExtra(CARD, card)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }



    }


}