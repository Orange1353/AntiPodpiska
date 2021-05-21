package com.example.antipodpiska.menu

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View = inflater.inflate(R.layout.fragment_statistics, container, false)


        var pieChart: PieChart = view.findViewById(R.id.piechart)
        val dateFrom: EditText = view.findViewById(R.id.state_date_from)
        val dateUntil: EditText = view.findViewById(R.id.state_date_until)
        var cal = Calendar.getInstance()
        var context: Context? = getContext()


         val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateFrom.setText(sdf.format(cal.time))
        }
        val dateSetListenerDatePay2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateUntil.setText(sdf.format(cal.time))
        }

        dateFrom.setOnClickListener {
        addDate(context!!, cal, dateSetListenerDatePay)
        }
        dateUntil.setOnClickListener {
            addDate(context!!, cal, dateSetListenerDatePay2)
        }


        var colorClassArray: List<Int> = listOf(
            resources.getColor(R.color.calendar_green),
            resources.getColor(R.color.calendar_orange),
            resources.getColor(
                R.color.calendar_pink
            ),
            resources.getColor(R.color.calendar_blue),
            resources.getColor(R.color.calendar_blue2),
            resources.getColor(R.color.calendar_fiolet),
            resources.getColor(
                R.color.calendar_grey
            )
        )






        var moneyMap: HashMap<String, Double> =  getMoney(costOfAllSubsByTypes(context!!))
        var pieDataset: PieDataSet = PieDataSet(dataValues(costOfAllSubsByTypes(context)), "")




        pieDataset.setColors(colorClassArray)
        pieDataset.sliceSpace=2f

        pieDataset.setDrawValues(false)
        var data: PieData = PieData(pieDataset)

        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data

        pieChart.extraLeftOffset = -70f
        pieChart.setDrawEntryLabels(false)
        pieChart.holeRadius = 67F
        //максимальная строчка возможная
        //  pieChart.minAngleForSlices = 60f
        //закругления
        //      pieChart.setDrawRoundedSlices(true)
        pieChart.setHoleColor(resources.getColor(R.color.menu_back_light))

        pieChart.setTransparentCircleColor(resources.getColor(R.color.menu_back_light))
        pieChart.setTransparentCircleAlpha(0)
        pieChart.description = null
        pieChart.invalidate()
        //https://medium.com/androiddevelopers/spantastic-text-styling-with-spans-17b0c16b4568   SpannableString


        //      if (moneyMap.get("₽")!! % 1 == 0.0)
//        pieChart.setCenterText(moneyMap.get("₽").toString().substring(0, moneyMap.get("₽").toString().length -2) + "₽")
        //   else

        //Значения
        pieChart.setCenterText( getAllMoneyInString(moneyMap))

        pieChart.setCenterTextColor(resources.getColor(R.color.black_light))

    pieChart.setCenterTextColor(R.color.black_light)

        val legend: Legend = pieChart.getLegend()
        legend.textColor = resources.getColor(R.color.grey_legend)
        legend.textSize = 11f
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.formSize = 10f
        legend.form = Legend.LegendForm.CIRCLE
        legend.formToTextSpace = 7f
        legend.yEntrySpace = 4f
        legend.xOffset = 20f
        val tf = Typeface.createFromAsset(context!!.getAssets(), "font/sf_display_medium.ttf")
        legend.typeface = tf

        pieChart.setNoDataText("Добавьте подписки и их стоимость!")

        return view
    }


    fun getAllMoneyInString(moneyMap: HashMap<String, Double>): String {

        var stringMoney = ""

        moneyMap.forEach { k, v ->
            stringMoney = stringMoney + v + " " + k + "\n"
        }

        return stringMoney
    }


    fun getMoney(mapCountsByType: HashMap<String, Double>?): HashMap<String, Double> {

        val dataVals: HashMap<String, Double> =  HashMap<String, Double>()

        if (mapCountsByType != null && mapCountsByType.size != 0)
            mapCountsByType.forEach { (key, value) ->
                if (key.length < 4) {
                    var tmp = ""
                    when(key.toString()){
                        "RUB" -> tmp = "₽"
                        "USD" -> tmp = "$"
                        "EUR" -> tmp = "€"
                        "GPB" -> tmp = "£"
                        "CNY" -> tmp = "Ұ"
                        "CHF" -> tmp = "₣"
                        "JPY" -> tmp = "¥"
                        "BTC" -> tmp = "₿"
                        "OTHER" -> tmp = "?"
                    }
                    dataVals.put(tmp, value)
                }

            }
        return dataVals
    }


    fun dataValues(mapCountsByType: HashMap<String, Double>?):ArrayList<PieEntry> {

        val dataVals: ArrayList<PieEntry> =  ArrayList<PieEntry>()

        if (mapCountsByType != null && mapCountsByType.size != 0)
            mapCountsByType.forEach { (key, value) ->
                if (key.length > 3) {
                    if (key == "Тип подписки")
                        dataVals.add(PieEntry(value.toFloat(), "Не определено"))
                    else
                        dataVals.add(PieEntry(value.toFloat(), key))
                }
                /* else{
                     pieChart.setCenterText("$value $key")
                     pieChart.setCenterTextColor(resources.getColor(R.color.white))
                 }*/
            }
        else
            dataVals.add(PieEntry(10f, "Нет платежей"))

        /* dataVals.add(PieEntry(mapCountsByType.get, "Музыка"))
         dataVals.add(PieEntry(10f, "Приложения"))
         dataVals.add(PieEntry(10f, "Смарт-Тв"))
         dataVals.add(PieEntry(10f, "Связь"))
         dataVals.add(PieEntry(10f, "Облака"))
         dataVals.add(PieEntry(100f, "Иное"))
         dataVals.add(PieEntry(50f, "Не определено"))*/

        return dataVals
    }
    fun costOfAllSubsByTypes(context: Context): HashMap<String, Double>? {

        val sharedPrefSource= SharedPrefSource(context)
        val allSharedDate: Map<String, *>? = sharedPrefSource.getNeardayPayDate(context)
        var allSummAndCountsByTypes: HashMap<String, Double>? =  HashMap<String, Double>()

        var sub: Sub
        var summ: Double = 0.0

        if (allSharedDate!= null)
            for (i in allSharedDate)
            {
                sub = sharedPrefSource.getFromShared(i.key)

                //Common Summ
                var mounthNow = String.format("%02d", LocalDate.now().monthValue)
                //if pay in this mounth
                if(mounthNow == i.value.toString().substring(3, 5))
                {
                    if (sub.status == "Активна") {
                        try {
                            if (sub.costSub != "")
                            {   if (allSummAndCountsByTypes?.get(sub.costCurr) == null)
                                allSummAndCountsByTypes?.put(sub.costCurr, 0.0)
                                allSummAndCountsByTypes?.put(
                                    sub.costCurr, allSummAndCountsByTypes.get(
                                        sub.costCurr
                                    )!! + sub.costSub.toDouble()
                                )
                            }
                            if (allSummAndCountsByTypes?.get(sub.typeSub) == null)
                                allSummAndCountsByTypes?.put(sub.typeSub, 0.0)
                            allSummAndCountsByTypes?.put(
                                sub.typeSub, allSummAndCountsByTypes.get(
                                    sub.typeSub
                                )!! + 1.0
                            )
                        }
                        catch (e: NumberFormatException)
                        {
                        }
                        catch (e: NullPointerException)
                        {
                        }
                    }
                }
            }

   //     Log.e("8888", allSummAndCountsByTypes.toString())

        return allSummAndCountsByTypes
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