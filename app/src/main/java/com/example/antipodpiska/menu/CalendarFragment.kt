package com.example.antipodpiska.menu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subList.SubListViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry



class CalendarFragment : Fragment() {

    var subsListViewModel = ViewModelProvider(activity!!).get(SubListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

        var pieChart: PieChart = view.findViewById(R.id.piechart)



        var colorClassArray: List<Int> = listOf(
            resources.getColor(R.color.blue), resources.getColor(
                R.color.white
            ), resources.getColor(R.color.orange), resources.getColor(R.color.pink)
        )

        var pieDataset: PieDataSet = PieDataSet(dataValues(), "")
        pieDataset.setColors(colorClassArray)
        pieDataset.sliceSpace=2f
        pieDataset.setDrawValues(false)
        var data: PieData = PieData(pieDataset)
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.setDrawEntryLabels(false)
        pieChart.holeRadius = 67F
        pieChart.minAngleForSlices = 50f
        //закругления
  //      pieChart.setDrawRoundedSlices(true)
        pieChart.setHoleColor(resources.getColor(R.color.darkBack))
        pieChart.setNoDataText("Добавьте подписки!")
        pieChart.setTransparentCircleColor(resources.getColor(R.color.darkBack))
        pieChart.setTransparentCircleAlpha(0)
        pieChart.description = null

        val legend: Legend = pieChart.getLegend()
        legend.textColor = resources.getColor(R.color.transparent_grey)
        legend.textSize = 11f
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.formSize = 10f
        legend.form = Legend.LegendForm.CIRCLE
        legend.formToTextSpace = 7f
        legend.yEntrySpace = 4f

        var context: Context? = getContext()
        val tf = Typeface.createFromAsset(context!!.getAssets(), "font/sf_display_medium.ttf")
        legend.typeface = tf



        return view
    }

    fun dataValues():ArrayList<PieEntry> {

        val dataVals: ArrayList<PieEntry> =  ArrayList<PieEntry>()

        dataVals.add(PieEntry(10f, "Музыка"))
        dataVals.add(PieEntry(20f, "Приложения"))
        dataVals.add(PieEntry(50f, "Смарт-Тв"))
        dataVals.add(PieEntry(50f, "Связь"))
        dataVals.add(PieEntry(50f, "Облака"))
        dataVals.add(PieEntry(50f, "Иное"))
        dataVals.add(PieEntry(50f, "Не определено"))

        return dataVals
    }

    fun summByTypeValues(){

        subsListViewModel.subsLiveData.value

     /*   var list1: List<Sub> = ArrayList<Sub>()
        for (i in subsListViewModel.subsLiveData.value!!.indices) {
            if (subsListViewModel.subsLiveData.value!![i].name.contains(s, false))
                list1 = list1.plus(subsListViewModel.subsLiveData.value!![i])
        }
*/
    }



}

