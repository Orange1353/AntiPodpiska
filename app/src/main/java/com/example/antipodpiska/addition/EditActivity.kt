package com.example.antipodpiska.addition

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.get
import androidx.lifecycle.MutableLiveData
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.data.subList
import com.example.antipodpiska.subDetails.SubDetailViewModel
import com.example.antipodpiska.subDetails.SubDetailViewModelFactory
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.utils.startSubListActivity
import com.example.recyclersample.data.DataSource
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

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
    private lateinit var buttonSave: Button
    private lateinit var textCreate: TextView
    private val Shared: SharedPrefSource by lazy{ SharedPrefSource(this) }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_sub)

        buttonSave = findViewById(R.id.done_button)
        buttonSave.text = "Сохранить"

textCreate = findViewById(R.id.create)
        textCreate.text = "Изменить данные подписки"

        val dataSource: DataSource = DataSource(resources, this)

        var currentSubId: Long? = null
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentSubId = bundle.getLong(SUB_ID)
        }

            val currentSub = currentSubId?.let { dataSource.getSubForId(it) }
val oldSub = currentSub
            addSubName = findViewById(R.id.add_flower_name)
            addSubDescription = findViewById(R.id.add_flower_description)
            addTypeSub = findViewById(R.id.spinner_type_sub)
            addDatePay = findViewById(R.id.day_pay)
            addPeriodFree = findViewById(R.id.add_free_days)
            addCostSub = findViewById(R.id.add_cost)
            addPeriodPay = findViewById(R.id.add_period)
            addPeriodTypeFree = findViewById(R.id.spinner_free_period_type)
            addCostCurr = findViewById(R.id.spinner_curr_cost)
            addPeriodTypePay = findViewById(R.id.spinner_period_pay)
            addCard = findViewById(R.id.card)
            pushEnabled = findViewById(R.id.switch_enabled)



            if (currentSub != null) {
                fillFields(currentSub)
            }



        var cal = Calendar.getInstance()


        val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            addDatePay.setText(sdf.format(cal.time))
        }

        addDatePay.setOnClickListener {
            DatePickerDialog(this@EditActivity, dateSetListenerDatePay,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        buttonSave.setOnClickListener {
            if (currentSub != null) {

                if (oldSub != null) {
                    dataSource.removeSub(oldSub, this)
                }
                fillCurrentSub(currentSub)
                dataSource.addSubInFirebase(currentSub)
                Shared.saveToShared(currentSub)
                dataSource.addSub(currentSub, this)

                val resultIntent = Intent()
                resultIntent.putExtra(SUB_NAME, currentSub.name)
                resultIntent.putExtra(SUB_DESCRIPTION, currentSub.description)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

           startSubListActivity()
        }
    }


    fun fillCurrentSub(currentSub: Sub){
        currentSub.name= addSubName.text.toString()
        currentSub.description= addSubDescription.text.toString()
        currentSub.typeSub= addTypeSub.selectedItem.toString()
        currentSub.datePay = addDatePay.text.toString()
        currentSub.periodFree = addPeriodFree.text.toString()
        currentSub.costSub = addCostSub.text.toString()
        currentSub.periodPay= addPeriodPay.text.toString()
        currentSub.periodTypeFree = addPeriodTypeFree.selectedItem.toString()
        currentSub.costCurr = addCostCurr.selectedItem.toString()
        currentSub.periodTypePay = addPeriodTypePay.selectedItem.toString()
        currentSub.card = addCard.text.toString()
        currentSub.pushEnabled = pushEnabled.isChecked
    }

    fun fillFields(currentSub: Sub){
        addSubName.setText(currentSub.name.toString())
        addSubDescription.setText(currentSub.description.toString())

        when(currentSub.typeSub) {
            "Музыка" -> addTypeSub.setSelection(1)
            "Приложения" -> addTypeSub.setSelection(2)
            "Смарт-Тв"-> addTypeSub.setSelection(3)
            "Связь"-> addTypeSub.setSelection(4)
            "Облака"-> addTypeSub.setSelection(5)
            "Иное"-> addTypeSub.setSelection(6)
        }
        addDatePay.setText(currentSub.datePay)
        addPeriodFree.setText(currentSub.periodFree)
        addCostSub.setText(currentSub.costSub)
        addPeriodPay.setText(currentSub.periodPay)

        when(currentSub.periodTypeFree){
            "Дней"->  addPeriodTypeFree.setSelection(0)
            "Недель"-> addPeriodTypeFree.setSelection(1)
            "Месяцев"->  addPeriodTypeFree.setSelection(2)
        }
        when(currentSub.costCurr){
            "RUB" -> addCostCurr.setSelection(0)
            "USD"-> addCostCurr.setSelection(1)
            "EUR"-> addCostCurr.setSelection(2)
            "GBR"-> addCostCurr.setSelection(3)
            "CNY"-> addCostCurr.setSelection(4)
            "CHF"-> addCostCurr.setSelection(5)
            "JPY"-> addCostCurr.setSelection(6)
            "OTHER"-> addCostCurr.setSelection(7)
        }
      //  addCostCurr
        when(currentSub.periodTypePay){
            "Дней"->  addPeriodTypePay.setSelection(0)
            "Недель"-> addPeriodTypePay.setSelection(1)
            "Месяцев"->  addPeriodTypePay.setSelection(2)
        }
      //
        addCard.setText(currentSub.card)
        pushEnabled.isChecked = currentSub.pushEnabled

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startSubListActivity()
    }


}