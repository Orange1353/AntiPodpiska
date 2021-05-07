package com.example.antipodpiska.addition

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.User
import com.example.antipodpiska.data.firebase.FirebaseSource
import com.example.antipodpiska.data.repositories.UserRepository
import com.example.recyclersample.data.DataSource
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.activity_add_sub.*
import kotlinx.android.synthetic.main.activity_sub_detail.*
import java.text.SimpleDateFormat
import java.util.*


const val SUB_NAME = "name"
const val SUB_DESCRIPTION = "description"
const val TYPE = "typeSub"
const val DATE_PAY = "pay"
const val FREE_PERIOD = "freePeriod"
const val COST = "cost"
const val PERIOD = "Period"
const val TYPE_FREE = "typeFreePeriod"
const val CURR_COST = "typeCost"
const val TYPE_PERIOD = "typePeriod"
const val CARD = "card"
const val PUSH = "push"
const val DATE_ADD = "dateAdd"
const val SUB_IMAGE = "image"
const val SUB_COLOR = "color"

class AddSubActivity : AppCompatActivity() {

    private lateinit var addSubName: TextInputEditText
    private lateinit var addSubDescription: TextInputEditText
    private lateinit var addTypeSub: Spinner
    private lateinit var addDatePay: TextInputEditText
    private lateinit var addPeriodFree: TextInputEditText
    private lateinit var addCostSub: TextInputEditText
    private lateinit var addPeriodPay: TextInputEditText
    private lateinit var addCard: TextInputEditText
    private lateinit var addPeriodTypeFree: Spinner
    private lateinit var addCostCurr: Spinner
    private lateinit var addPeriodTypePay: Spinner
    private lateinit var pushEnabled: SwitchCompat




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub)


        findViewById<Button>(R.id.done_button).setOnClickListener {
            addSub()
        }
        addSubName = findViewById(R.id.add_flower_name)
        addSubDescription = findViewById(R.id.add_flower_description)
        addTypeSub = findViewById(R.id.spinner_type_sub)
        addDatePay =  findViewById(R.id.day_pay)
        addPeriodFree =  findViewById(R.id.add_free_days)
        addCostSub =  findViewById(R.id.add_cost)
        addPeriodPay =  findViewById(R.id.add_period)
        addPeriodTypeFree = findViewById(R.id.spinner_free_period_type)
        addCostCurr = findViewById(R.id.spinner_curr_cost)
        addPeriodTypePay = findViewById(R.id.spinner_period_pay)
        addCard = findViewById(R.id.card)
        pushEnabled = findViewById(R.id.switch_enabled)
val buttonBack: Button = findViewById(R.id.button_back)

        checkUserCloud()

        var cal = Calendar.getInstance()

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(getResources().getColor(R.color.blue_back))

        val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            addDatePay.setText(sdf.format(cal.time))
        }



        addDatePay.setOnClickListener {
            DatePickerDialog(this@AddSubActivity, dateSetListenerDatePay,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }



        pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

            if( pushEnabled.text == "Выключены")
                pushEnabled.text = "Включены  "
            else
                pushEnabled.text = "Выключены"
        }

        buttonBack.setOnClickListener {
            onBackPressed()
        }

    }


    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */
    private val Shared: SharedPrefSource by lazy{ SharedPrefSource(this) }

    private val firebaseFirestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun checkUserCloud(){

            firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
                    .addOnSuccessListener { document ->
                        if (document.data != null )
                        {
                            val user = document.toObject(User::class.java)
                            if (user?.token != FirebaseInstanceId.getInstance().token.toString()) {
                                user?.token = FirebaseInstanceId.getInstance().token.toString()
                               val firebase = FirebaseSource()
                                if (user != null) {
                                    firebase.addUserInFirebase(user)
                                }
                            }
                            Log.d("!!!!!", "1 DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("!!!!!", "2 DocumentSnapshot data: ${document.data}")
                            var listUser = Shared.getTempUser(this)
                            Log.d("!!!!!", "listUser: $listUser")
                            val firebase = FirebaseSource()
                            firebase.addUserInFirebaseWithCheck(listUser[0], listUser[1], listUser[2])
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("!!!!!", "get failed with ", exception)
                    }
            var listUser = Shared.getTempUser(this)
            val firebase = FirebaseSource()
            firebase.addUserInFirebaseWithCheck(listUser[0], listUser[1], listUser[2])
    }


    private fun addSub() {

     //   checkUserCloud()


        val resultIntent = Intent()

        if (addSubName.text.isNullOrEmpty() || addSubDescription.text.isNullOrEmpty() || addDatePay.text.isNullOrEmpty()) {
           Toast.makeText(this@AddSubActivity, "Заполните перые 3 строки!",  Toast.LENGTH_SHORT).show()
        } else {

            //Sub() !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            val name = addSubName.text.toString()
            val description = addSubDescription.text.toString()
            val typeSub= addTypeSub.selectedItem.toString()
            val datePay = addDatePay.text.toString()
            val freePeriod = addPeriodFree.text.toString()
            val cost = addCostSub.text.toString()
            val period = addPeriodPay.text.toString()
            val typeFree = addPeriodTypeFree.selectedItem.toString()
            val currCost = addCostCurr.selectedItem.toString()
            val typePeriod = addPeriodTypePay.selectedItem.toString()
            val card = addCard.text.toString()
            val push = pushEnabled.isChecked()

            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val cal: Calendar = GregorianCalendar()

            val dateAdd = dateFormat.format(cal.getTime()).toString()


            resultIntent.putExtra(SUB_NAME, name)
            resultIntent.putExtra(SUB_DESCRIPTION, description)
            resultIntent.putExtra(TYPE, typeSub)
            resultIntent.putExtra(DATE_PAY, datePay)
            resultIntent.putExtra(FREE_PERIOD, freePeriod)
            resultIntent.putExtra(COST, cost)
            resultIntent.putExtra(PERIOD, period)
            resultIntent.putExtra(TYPE_FREE, typeFree)
            resultIntent.putExtra(CURR_COST, currCost)
            resultIntent.putExtra(TYPE_PERIOD, typePeriod)
            resultIntent.putExtra(CARD, card)
            resultIntent.putExtra(PUSH, push)
            resultIntent.putExtra(DATE_ADD, dateAdd)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }

}