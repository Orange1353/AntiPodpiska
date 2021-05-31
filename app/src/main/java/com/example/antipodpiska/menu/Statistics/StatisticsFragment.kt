package com.example.antipodpiska.menu.Statistics

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.menu.SUB_ID
import com.example.antipodpiska.subDetails.SubDetailActivity
import com.example.antipodpiska.subList.SubAdapter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.element_stat_categories.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StatisticsFragment : Fragment() {

    private  var allSummByTypes: HashMap<String, Double> =  HashMap<String, Double>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        //      val concatAdapter = ConcatAdapter(headerAdapter, subsAdapter)
        var view: View = inflater.inflate(R.layout.fragment_statistics, container, false)

        recyclerView = view!!.findViewById(R.id.list_types)

        var adapterStat: StatAdapter = StatAdapter { position -> onListItemClick(position) }

        recyclerView.adapter = adapterStat


        val textAllSumms: TextView = view.findViewById(R.id.stat_summs)

        var pieChart: PieChart = view.findViewById(R.id.piechart)
        val dateFrom: EditText = view.findViewById(R.id.state_date_from)
        val dateUntil: EditText = view.findViewById(R.id.state_date_until)

        var cal = Calendar.getInstance()
        var context: Context? = getContext()

        dateFrom.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        dateUntil.setText(
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        )

        var colorClassArray: List<Int> = listOf(
                resources.getColor(R.color.calendar_blue),
                resources.getColor(R.color.calendar_green),
                resources.getColor(R.color.calendar_orange),
                resources.getColor(R.color.calendar_pink),
                resources.getColor(R.color.calendar_blue2),
                resources.getColor(R.color.calendar_fiolet),
                resources.getColor(R.color.calendar_grey)
        )

        var container: LinearLayout = view.findViewById(R.id.container)
        val fragmentManager = fragmentManager
  //      fragmentManager!!.beginTransaction().add(R.id.container, Element_stat()).commit()

//       fragmentManager!!.beginTransaction().add(R.id.container, Element_stat()).commit()
//        for (fragment in fragmentManager.fragments) {
//            if (fragment is Element_stat) {
//                continue
//            } else if (fragment != null) {
//                fragmentManager.beginTransaction().remove(fragment).commit()
//            }
//        }





        val dateSetListenerDatePay = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateFrom.setText(sdf.format(cal.time))
            fillPieChart(
                    pieChart,
                    colorClassArray,
                    dateFrom.text.toString(),
                    dateUntil.text.toString(),
                    textAllSumms,
                    context!!
            )

        }
        val dateSetListenerDatePay2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateUntil.setText(sdf.format(cal.time))
            fillPieChart(
                    pieChart,
                    colorClassArray,
                    dateFrom.text.toString(),
                    dateUntil.text.toString(),
                    textAllSumms,
                    context!!
            )

        }


        dateFrom.setOnClickListener {
        addDate(context!!, cal, dateSetListenerDatePay)
        }
        dateUntil.setOnClickListener {
            addDate(context!!, cal, dateSetListenerDatePay2)
        }




        fillPieChart(
                pieChart,
                colorClassArray,
                dateFrom.text.toString(),
                dateUntil.text.toString(),
                textAllSumms,
                context!!
        )

if(allSummByTypes!= null)
        fillBottomStat(view, allSummByTypes)

        var allSummByTypesList: List<CategoryWithValue> =  ArrayList<CategoryWithValue>()
        var t : CategoryWithValue = CategoryWithValue("kk", 0.555)
        var t2 : CategoryWithValue = CategoryWithValue("kk", 0.556)

        allSummByTypesList =  allSummByTypesList.plus(t)
        allSummByTypesList = allSummByTypesList.plus(t2)
        adapterStat.setmAllRepositories(allSummByTypesList)
        recyclerView.adapter = adapterStat
        return view
    }
    private fun onListItemClick(position: Int) {
        Toast.makeText(context, recyclerView[position].id.toString(), Toast.LENGTH_SHORT).show()
    }

    fun fillPieChart(
            pieChart: PieChart,
            colorClassArray: List<Int>,
            dateFrom: String,
            dateUntil: String,
            textAllSumms: TextView,
            context: Context
    ){


        var pieDataset: PieDataSet = PieDataSet(
                dataValues(
                        costOfAllSubsByTypes(
                                context,
                                dateFrom,
                                dateUntil
                        )
                ), ""
        )
        var moneyMap: HashMap<String, Double> =  getMoney(
                costOfAllSubsByTypes(
                        context,
                        dateFrom,
                        dateUntil
                )
        )

        pieDataset.setColors(colorClassArray)
        pieDataset.sliceSpace=2f

        pieDataset.setDrawValues(false)
        var data: PieData = PieData(pieDataset)

        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data

        pieChart.extraLeftOffset = -70f
        pieChart.setDrawEntryLabels(false)
        pieChart.holeRadius = 80F
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

        textAllSumms.text = getAllMoneyInString(moneyMap)
        //Значения

   //     pieChart.setCenterText( getAllMoneyInString(moneyMap))

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
        //visible видимость
        // legend.isEnabled = false
        pieChart.setNoDataText("Добавьте подписки и их стоимость!")

    }

    fun getAllMoneyInString(moneyMap: HashMap<String, Double>): String {

        var stringMoney = ""

        moneyMap.forEach { k, v ->
            stringMoney = stringMoney + v + " " + k + "\t"
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
                        "GBP" -> tmp = "£"
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

    fun costOfAllSubsByTypes(context: Context, dateFrom0: String, dateUntil0: String): HashMap<String, Double>? {

        val sharedPrefSource = SharedPrefSource(context)
        val allSharedDate: Map<String, *>? = sharedPrefSource.getNeardayPayDate(context)
        var allSummAndCountsByTypes: HashMap<String, Double>? =  HashMap<String, Double>()

        var curr: Currencies = sharedPrefSource.getFromSharedCurr(context)

        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        var dateFrom = LocalDate.parse(dateFrom0, formatter).minusDays(1)
        var dateUntil = LocalDate.parse(dateUntil0, formatter).plusDays(1)

        var sub: Sub
        var summ: Double = 0.0

        if (allSharedDate!= null)
            for (i in allSharedDate)
            {
                sub = sharedPrefSource.getFromShared(i.key)

//                //Common Summ
//                var mounthNow = String.format("%02d", LocalDate.now().monthValue)
//                //if pay in this mounth
//                if(mounthNow == i.value.toString().substring(3, 5))
//                {

                    if (sub.status == "Активна") {
                        if(LocalDate.parse(i.value.toString(), formatter).isBefore(dateUntil) && LocalDate.parse(
                                        i.value.toString(),
                                        formatter
                                ).isAfter(dateFrom))
                        try {
                            //Map стоимости по валютам
                            if (sub.costSub != "")
                            {
                                if (allSummAndCountsByTypes?.get(sub.costCurr) == null)
                                allSummAndCountsByTypes?.put(sub.costCurr, 0.0)
                                allSummAndCountsByTypes?.put(
                                        sub.costCurr, allSummAndCountsByTypes.get(
                                        sub.costCurr
                                )!! + sub.costSub.toDouble()
                                )
                            }

                            if (allSummAndCountsByTypes?.get(sub.typeSub) == null)
                            {
                                allSummAndCountsByTypes?.put(sub.typeSub, 0.0)
                                allSummByTypes?.put(sub.typeSub, 0.0)
                            }
                            //Map количества по типам
                            allSummAndCountsByTypes?.put(
                                    sub.typeSub, allSummAndCountsByTypes.get(
                                    sub.typeSub
                            )!! + 1.0
                            )
                            //Map цен по типам
                            allSummByTypes?.put(
                                    sub.typeSub,  allSummByTypes!!.get(
                                    sub.typeSub
                            )!! + translateInRUB(sub.costSub, sub.costCurr, curr)
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


        return allSummAndCountsByTypes
    }


    fun fillBottomStat(view: View, allSummByTypes: HashMap<String, Double>?){

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var flContainer = view.findViewById(R.id.container) as LinearLayout

        if (!flContainer.isEmpty())
            flContainer.removeAllViews()

        if(allSummByTypes!=null)
        for (i in allSummByTypes.keys)
        {
            val myView: View = inflater.inflate(R.layout.element_stat_categories, null)
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var flContainer = view.findViewById(R.id.container) as LinearLayout
            flContainer.addView(myView)
        myView.name_category.text= allSummByTypes!!.getValue(i).toString()
        }
    }

    fun translateInRUB(subCost: String, subCurr: String, curr: Currencies): Double {
        var result: Double = 0.0

        when(subCurr)    {
        "RUB" -> result = subCost.toDouble() / curr.RUB
        "USD" -> result = subCost.toDouble() / curr.USD
        "EUR" -> result = subCost.toDouble() / curr.EUR
        "GBP" -> result = subCost.toDouble() / curr.GBP
        "JPI" -> result = subCost.toDouble() / curr.JPI
        "CNY" -> result = subCost.toDouble() / curr.CNY
        "CHF" -> result = subCost.toDouble() / curr.CHF
        }
        return result
    }

    fun addDate(
            context: Context,
            cal: Calendar,
            dateSetListenerDatePay: DatePickerDialog.OnDateSetListener
    )
    {
        //вид календаря в AlertDialog(просто удалить)
        DatePickerDialog(
                context, AlertDialog.THEME_HOLO_LIGHT, dateSetListenerDatePay,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun adapterOnClick(categoryWithValue: CategoryWithValue) {

        var context: Context? = getContext()
      /*  val options: ActivityOptionsCompat? = activity?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it,
                    recyclerView,  // Starting view
                    "anim" // The String
            )
        }*/

     //   val intent = Intent(context, SubDetailActivity()::class.java)
      //  intent.putExtra(SUB_ID, position)
      //  startActivity(intent, options!!.toBundle())

    }

}