package com.example.antipodpiska.addition


import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subDetails.SubDetailViewModel
import com.example.antipodpiska.subDetails.SubDetailViewModelFactory
import com.example.antipodpiska.subList.SUB_ID
import com.example.antipodpiska.utils.startSubListActivity
import com.google.firebase.database.collection.LLRBNode
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_sub.*
import java.text.SimpleDateFormat
import java.util.*

class EditSubActivity : AppCompatActivity() {
    private lateinit var addSubName: EditText
    private lateinit var addSubDescription: EditText
    private lateinit var addTypeSub: Spinner
    private lateinit var addDatePay: EditText
    private lateinit var addPeriodFree: EditText
    private lateinit var addCostSub: EditText
    private lateinit var addPeriodPay: EditText
    private lateinit var addCard: EditText
    private lateinit var addPeriodTypeFree: Spinner
    private lateinit var addCostCurr: Spinner
    private lateinit var addPeriodTypePay: Spinner
    private lateinit var pushEnabled: SwitchCompat
    private lateinit var buttonSave: Button
    private lateinit var buttonBack: Button
    private val Shared: SharedPrefSource by lazy{ SharedPrefSource(this) }
    private val subDetailViewModel by viewModels<SubDetailViewModel> {
        SubDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sub)

        var currentSubId: Long? = null

        /* Connect variables to UI elements. */
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
        buttonBack = findViewById(R.id.button_back)
        val buttonCalendar: ImageView = findViewById(R.id.imageViewCalendar)
        val saveSubButton: Button = findViewById(R.id.done_button)


        var spinner_typeSub: Spinner = findViewById(R.id.spinner_type_sub)
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_array_tupe_sub,
            R.layout.spinner_dropdown_dark
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_dark)
        spinner_typeSub.setAdapter(adapter)



        val spinner_free_period: Spinner = findViewById(R.id.spinner_free_period_type)
        var spinner_period_pay: Spinner = findViewById(R.id.spinner_period_pay)
        val adapter_free_period: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_array_period,
            R.layout.spinner_dropdown_text
        )
        adapter_free_period.setDropDownViewResource(R.layout.spinner_dropdown_text);

        spinner_free_period.setAdapter(adapter_free_period);
        spinner_period_pay.setAdapter(adapter_free_period);

        val spinner_cost: Spinner = findViewById(R.id.spinner_curr_cost)
        val adapter_cost: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_array_curr_cost,
            R.layout.spinner_dropdown_text
        )

        adapter_cost.setDropDownViewResource(R.layout.spinner_dropdown_text);
        spinner_cost.setAdapter(adapter_cost);




        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentSubId = bundle.getLong(SUB_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentSubId?.let {
            val currentSub = subDetailViewModel.getFlowerForId(it)

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
                addDate(this, cal, dateSetListenerDatePay)
            }
            buttonCalendar.setOnClickListener{
                addDate(this, cal, dateSetListenerDatePay)
            }

            saveSubButton.setOnClickListener {
                if (currentSub != null) {

                    var newSub = clone(currentSub)
                    newSub = fillNewSub(newSub!!)
                    subDetailViewModel.editSub(currentSub, newSub, this)

                }
                finish()
                startSubListActivity()
            }

            buttonBack.setOnClickListener {
                onBackPressed()
            }

            pushEnabled.setOnCheckedChangeListener { buttonView, isChecked ->

    //            if (currentSub != null)
   //                 subDetailViewModel.pushAboutSub(currentSub)

                if( pushEnabled.text == "Выключены")
                    pushEnabled.text = "Включены"
                else
                    pushEnabled.text = "Выключены"

            }


        }



    }

    override fun onBackPressed() {
      startSubListActivity()
    }

    fun fillNewSub(currentSub: Sub): Sub {
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
        return currentSub
    }

    fun fillFields(currentSub: Sub){
        addSubName.setText(currentSub?.name.toString())
        addSubDescription.setText(currentSub?.description.toString())

        when(currentSub?.typeSub) {
            "Музыка" -> addTypeSub.setSelection(1)
            "Приложения" -> addTypeSub.setSelection(2)
            "Смарт-Тв" -> addTypeSub.setSelection(3)
            "Связь" -> addTypeSub.setSelection(4)
            "Облака" -> addTypeSub.setSelection(5)
            "Иное" -> addTypeSub.setSelection(6)
        }
        addDatePay.setText(currentSub?.datePay)
        addPeriodFree.setText(currentSub?.periodFree)
        addCostSub.setText(currentSub?.costSub)
        addPeriodPay.setText(currentSub?.periodPay)

        when(currentSub?.periodTypeFree){
            "Дней" -> addPeriodTypeFree.setSelection(0)
            "Недель" -> addPeriodTypeFree.setSelection(1)
            "Месяцев" -> addPeriodTypeFree.setSelection(2)
        }
        when(currentSub?.costCurr){
            "RUB" -> addCostCurr.setSelection(0)
            "USD" -> addCostCurr.setSelection(1)
            "EUR" -> addCostCurr.setSelection(2)
            "GBR" -> addCostCurr.setSelection(3)
            "CNY" -> addCostCurr.setSelection(4)
            "CHF" -> addCostCurr.setSelection(5)
            "JPY" -> addCostCurr.setSelection(6)
            "BTC" -> addCostCurr.setSelection(7)
            "OTHER" -> addCostCurr.setSelection(8)
        }
        //  addCostCurr
        when(currentSub?.periodTypePay){
            "Дней" -> addPeriodTypePay.setSelection(0)
            "Недель" -> addPeriodTypePay.setSelection(1)
            "Месяцев" -> addPeriodTypePay.setSelection(2)
        }
        //
        addCard.setText(currentSub?.card)
        pushEnabled.isChecked = currentSub?.pushEnabled!!

        if (pushEnabled.isChecked)
            pushEnabled.text = "Включены"




    }
    fun addDate(
        context: Context,
        cal: Calendar,
        dateSetListenerDatePay: DatePickerDialog.OnDateSetListener
    )
    {
        DatePickerDialog(
            context, dateSetListenerDatePay,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

fun clone(sub: Sub): Sub? {
    val stringSub = Gson().toJson(sub, Sub::class.java)
    return Gson().fromJson<Sub>(stringSub, Sub::class.java)
}

}