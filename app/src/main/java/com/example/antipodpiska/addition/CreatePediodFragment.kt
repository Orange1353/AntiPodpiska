package com.example.antipodpiska.addition

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.antipodpiska.R
import java.text.SimpleDateFormat
import java.util.*


class CreatePediodFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view: View = inflater.inflate(R.layout.fragment_create_pediod, container, false)

        val context: Context? = getContext()


        val spinner_free_period: Spinner = view.findViewById(R.id.spinner_free_period_type)
        var spinner_period_pay: Spinner = view.findViewById(R.id.spinner_period_pay)
        val adapter_free_period: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_period,
            R.layout.spinner_dropdown_text)
        adapter_free_period.setDropDownViewResource(R.layout.spinner_dropdown_text);

        spinner_free_period.setAdapter(adapter_free_period);
        spinner_period_pay.setAdapter(adapter_free_period);

        val spinner_cost: Spinner = view.findViewById(R.id.spinner_curr_cost)
        val adapter_cost: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            context!!,
            R.array.spinner_array_curr_cost,
            R.layout.spinner_dropdown_text)
        adapter_cost.setDropDownViewResource(R.layout.spinner_dropdown_text);
        spinner_cost.setAdapter(adapter_cost);


        val freePeriod: EditText = view.findViewById(R.id.add_free_days)
        val costSub: EditText = view.findViewById(R.id.add_cost)
        val periodPay: EditText = view.findViewById(R.id.add_period)
        val addDatePay: EditText = view.findViewById(R.id.day_pay)
        val buttonBack: Button= view.findViewById(R.id.button_back)
        val buttonCalendar: ImageView = view.findViewById(R.id.imageViewCalendar)
        val btnContinue: Button = view.findViewById(R.id.button_continue)

        var cal = Calendar.getInstance()

        val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            addDatePay.setText(sdf.format(cal.time))
        }


        addDatePay.setOnClickListener {
        addDate(context, cal, dateSetListenerDatePay)
        }
        buttonCalendar.setOnClickListener{
        addDate(context, cal, dateSetListenerDatePay)
        }

        addDatePay.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(addDatePay.text.toString() != "ДД.ММ.ГГГГ")
                    btnContinue.setBackgroundTintList(context.getResources().getColorStateList(R.color.blue_light))
                else
                    btnContinue.setBackgroundTintList(context.getResources().getColorStateList(R.color.border_light))
            }
        })


        communicator = activity as Communicator
        btnContinue.setOnClickListener {

            if(/*costSub.text.toString() != "" &&*/ addDatePay.text.toString()!= "ДД.ММ.ГГГГ")
            communicator.periodFragmentToCardFragment(freePeriod.text.toString(), spinner_free_period.selectedItem.toString(), costSub.text.toString(), spinner_cost.selectedItem.toString(), periodPay.text.toString(), spinner_period_pay.selectedItem.toString(), addDatePay.text.toString())
            else
                Toast.makeText(context, "Заполните дату начала подписки!",  Toast.LENGTH_SHORT).show()

        }


        buttonBack.setOnClickListener {
            communicator.onBackPressedInFragms23()
        }



        return view
    }

    fun addDate(context: Context, cal: Calendar, dateSetListenerDatePay: DatePickerDialog.OnDateSetListener )
    {
        //вид календаря в AlertDialog(просто удалить)
        DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, dateSetListenerDatePay,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }


}