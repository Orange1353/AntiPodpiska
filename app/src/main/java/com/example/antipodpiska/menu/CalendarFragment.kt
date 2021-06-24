package com.example.antipodpiska.menu

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import org.mylibrary.librarycalendar.CalenderEvent
import org.mylibrary.librarycalendar.model.Event
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CalendarFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)



      var events: List<EventDay> = ArrayList()
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.DAY_OF_MONTH, 10)


 /*
        // Пусть bmp1 и bmp2 -  картинки, которые нужно склеить
// Предположим, что вторую нужно нарисовать справа от первой
// Создаём изображение нужных размеров
        // Пусть bmp1 и bmp2 -  картинки, которые нужно склеить
// Предположим, что вторую нужно нарисовать справа от первой
// Создаём изображение нужных размеров
        val bmp1 = BitmapFactory.decodeResource(resources, R.drawable.ic_cross)
        val bmp2 = BitmapFactory.decodeResource(resources, R.drawable.ic_menu_calendar)

        val bmp = Bitmap.createBitmap(
            bmp1.getWidth() + bmp2.getWidth(),
            Math.max(bmp1.getHeight(), bmp2.getHeight()),
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(bmp) // Создаём canvas, на котором будем рисовать

        // Рисуем bmp1
        // Рисуем bmp1
        c.drawBitmap(bmp1, 0f, 0f, Paint())
        // Рисуем bmp2 справа от bmp1
        // Рисуем bmp2 справа от bmp1
        c.drawBitmap(bmp2, bmp1.getWidth().toFloat(), 0f, Paint())

*/

        val calendar2: CalenderEvent = view.findViewById(R.id.yyyy)
        calendar2.addEvent(Event(2062021, "4444"))


        calendar2.addEvent(Event(2062021, "444554"))

        return view

    }

    fun chartValueSelected(chartView: PieChart, entry: PieEntry, highlight: Highlight) {
        chartView.data?.setDrawValues(true)
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
                        "OTHER" -> tmp = ""
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

    fun summByTypeValues(context: Context){

   /*     var sharedPrefSource= SharedPrefSource(context)
        var allSharedDate: Map<String, *>? = sharedPrefSource.getNeardayPayDate(context)
        var allSharedCost: Map<String, *>? = sharedPrefSource.getNeardayCost(context)
        var summ: Double = 0.0

        if (allSharedDate!= null)
        for (i in allSharedDate)
        {
            var mounthNow = String.format("%02d", LocalDate.now().monthValue)
            if(mounthNow == i.value.toString().substring(3,5))
                summ += allSharedCost!![i.key].toString().toDouble()
        }
*/
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

    Log.e("8888", allSummAndCountsByTypes.toString())

        return allSummAndCountsByTypes
    }




}

