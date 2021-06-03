package com.example.antipodpiska.subList


//import android.widget.ListAdapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.lang.Math.pow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue


class SubAdapter(private val onClick: (Sub) -> Unit) :

    ListAdapter<Sub, SubAdapter.SubViewHolder>(FlowerDiffCallback){
    private var lastPosition = -1


    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class SubViewHolder(itemView: View, val onClick: (Sub) -> Unit, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val subName: TextView = itemView.findViewById(R.id.flower_text)
        private val subImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private val addDatePay: TextView =  itemView.findViewById(R.id.day_pay_calulat)
        private val status: EditText = itemView.findViewById(R.id.text_status)
        private val subDateUntil: TextView = itemView.findViewById(R.id.textViewUntil)
        private val addDayPayText: TextView = itemView.findViewById(R.id.day_pay_calulat_text)
        private lateinit var layout: CardView
        private val sharedPrefSource: SharedPrefSource = SharedPrefSource(context)
        private val context: Context = context
        private var currentSub: Sub? = null
       private val circularProgressBar = itemView.findViewById<CircularProgressBar>(R.id.circularProgressBar)
        init {
            itemView.setOnClickListener {
                currentSub?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image.
        * изменяет значение у уже существующего Viewholder*/
        fun bind(sub: Sub) {

            try {
               layout = itemView.findViewById(R.id.layout_back)
            }
            catch (e: NullPointerException)
            {
            }
            currentSub = sub

            var useBack = false

            if (sub.color!= null) {
              var t = context.resources.getIdentifier(sub.color, "color", "com.example.antipodpiska")
                useBack = getRGB(context.getResources().getString(t))
            }
else
    useBack = getRGB("#FFFAFBFC")


            subName.text = sub.name
//            if (sub.card != "")
            //  addDatePay.text = sub.datePay
            if (useBack)
                subName.setTextColor(context.resources.getColor(R.color.black_light))

            else
                subName.setTextColor(context.resources.getColor(R.color.white))

            val face: Typeface = Typeface.createFromAsset(
                context.assets, "font/sf_display_medium.ttf"
            )

            subName.setTypeface(face)

            if (currentSub?.status == "Архив" || currentSub?.status == "Удалена")
            {

                if (currentSub?.status == "Архив")
                addDatePay.text = "Отписались " + currentSub?.dateOfDelete
                else
                addDatePay.text = "Удалили " + currentSub?.dateOfDelete

                status.setText("Архив")
                if (useBack) {
                    status.setTextColor(context.resources.getColor(R.color.black_light))
                    addDatePay.setTextColor(context.resources.getColor(R.color.black_light))
                }
                    else {
                    status.setTextColor(context.resources.getColor(R.color.white))
                    addDatePay.setTextColor(context.resources.getColor(R.color.white))
                }
                val face: Typeface = Typeface.createFromAsset(
                    context.assets, "font/sf_display_bold.ttf"
                )

                status.setTypeface(face)
                addDayPayText.text = ""
            }

            else

            if (currentSub?.datePay != null && currentSub?.datePay !="")
         {
            var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var datePay = LocalDate.parse(currentSub?.datePay, formatter)

            var dateNow = LocalDate.now()
             var dateEndFree = LocalDate.parse(currentSub?.datePay, formatter)



             if (currentSub?.periodFree != "") {
                 when (currentSub?.periodTypeFree) {
                     "Дней" -> datePay = datePay.plusDays(currentSub?.periodFree!!.toLong())
                     "Недель" -> datePay = datePay.plusWeeks(currentSub?.periodFree!!.toLong())
                     "Месяцев" -> datePay = datePay.plusMonths(currentSub?.periodFree!!.toLong())
                 }
                 dateEndFree = datePay

             }

                //   while (datePay < dateNow)
                if (currentSub?.periodPay != "") {
                    while (datePay <= dateNow)
                        when (currentSub?.periodTypePay) {
                            "Дней" -> datePay = datePay.plusDays(currentSub?.periodPay!!.toLong())
                            "Недель" -> datePay =
                                datePay.plusWeeks(currentSub?.periodPay!!.toLong())
                            "Месяцев" -> datePay =
                                datePay.plusMonths(currentSub?.periodPay!!.toLong())
                        }
                }
             else if(currentSub?.periodFree == "")
                    circularProgressBar.visibility = View.INVISIBLE


             if (datePay!!.format(formatter) != sub.datePay){

                        if (sub.periodPay != "")
                        addDatePay.text =datePay.format(formatter).toString() //+ " стоимость " + sub.costSub + " " + sub.costCurr //+ "/ " + sub.periodPay + " " + tmp

                }

             if (sub.periodPay !="")
                 subDateUntil.text = "оплата за "+ sub.periodPay+" "+ rusification(
                     sub.periodPay,
                     sub.periodTypePay
                 )

             if (useBack) {
                 subDateUntil.setTextColor(context.resources.getColor(R.color.other_grey))
                 addDayPayText.setTextColor(context.resources.getColor(R.color.other_grey))
                 addDatePay.setTextColor(context.resources.getColor(R.color.black_light))
             }
             else {
                 subDateUntil.setTextColor(context.resources.getColor(R.color.other_grey))
                 addDayPayText.setTextColor(context.resources.getColor(R.color.other_grey))
                 addDatePay.setTextColor(context.resources.getColor(R.color.white))
             }
               sharedPrefSource.setNeardayPayDate(
                   context,
                   sub.id,
                   datePay.format(formatter).toString()
               )

             val face: Typeface = Typeface.createFromAsset(
                 context.assets, "font/sf_display_medium.ttf"
             )

             subDateUntil.setTypeface(face)
             addDayPayText.setTypeface(face)
             addDatePay.setTypeface(face)


        if (sub.costSub != "")
        { when(sub.costCurr){
            "RUB" -> status.setText("₽ " + sub.costSub)
            "EUR" -> status.setText("€ " + sub.costSub)
            "USD" -> status.setText("$ " + sub.costSub)
            "GBP" -> status.setText("£ " + sub.costSub)
            "CNY" -> status.setText("Ұ " + sub.costSub)
            "CHF" -> status.setText("₣ " + sub.costSub)
            "JPY" -> status.setText("¥ " + sub.costSub)
            "BTC" -> status.setText("₿ " + sub.costSub)
            "OTHER" -> status.setText(sub.costSub)
             }

            if (useBack)
                status.setTextColor(context.resources.getColor(R.color.black_light))
            else
                status.setTextColor(context.resources.getColor(R.color.white))
            val face: Typeface = Typeface.createFromAsset(
                context.assets, "font/sf_display_bold.ttf"
            )

            status.setTypeface(face)
        }

             circularProgressBar.apply {
                 // Set Progress
                 roundBorder = true
               //  startAngle = 0f


                 if (sub.datePay == datePay.format(formatter).toString())
                 {     progressMax = 1f
                 progress = 0f
                 }

                 else {
                     var countDays = itemView.findViewById<TextView>(R.id.countDays)
                     var period: Float

                     progress = ChronoUnit.DAYS.between(dateNow, datePay).toFloat()



if(sub.periodPay != "")
  period = sub.periodPay.toFloat()
else
    period= sub.periodFree.toFloat()

                     when (sub.periodTypePay) {
                         "Дней" -> {
                             progressMax = period
                             countDays.text = Math.round(progress).toString() + "\n" + rusification(
                                 (Math.round(
                                     progress
                                 ).absoluteValue).toString(), "Дней"
                             )
                             //(Math.round(progressMax) - Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                         }
                         "Недель" -> {
                             progressMax = period * 14
                             countDays.text =
                                 Math.round(progress).absoluteValue.toString() + "\n" + rusification(
                                     (Math.round(
                                         progress
                                     ).absoluteValue).toString(), "Дней"
                                 )
                             //(Math.round(progressMax)- Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                         }
                         "Месяцев" -> {
                             progressMax = period * 30
                             countDays.text =
                                 Math.round(progress).absoluteValue.toString() + "\n" + rusification(
                                     (Math.round(
                                         progress
                                     ).absoluteValue).toString(), "Дней"
                                 )
                             //( Math.round(progressMax)- Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                         }
                    }

                      //Если сейчас бесплатный период
                    if(currentSub?.periodFree != "" && dateNow < dateEndFree && currentSub?.periodFree != "0")
                {
                    var progress2 = ChronoUnit.DAYS.between(dateNow, dateEndFree)
                    progressMax = sub.periodFree.toFloat()
                }

                     if (useBack)
                     {   progressBarColor = resources.getColor(R.color.black_light)
                         countDays.setTextColor(resources.getColor(R.color.black_light))
                     }
                     else {
                         progressBarColor = resources.getColor(R.color.white)
                         countDays.setTextColor(resources.getColor(R.color.white))
                     }


                     if(progress < 4) {
                         countDays.setTextColor(resources.getColor(R.color.orange_light))
        //                 progressBarColor = resources.getColor(R.color.orange_light)
                        status.setTextColor(resources.getColor(R.color.red_light))
                         val face: Typeface = Typeface.createFromAsset(
                             context.assets, "font/sf_display_bold.ttf"
                         )

                         status.setTypeface(face)

                     }

                     val face2: Typeface = Typeface.createFromAsset(
                             context.assets, "font/sf_display_medium.ttf"
                     )

                 }



                /* progressBarColorStart = Color.parseColor("#EC8074")
                 progressBarColorEnd = Color.parseColor("#48B2FF")
                 progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
*/


         //        progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
                 // Set background ProgressBar Color


                 if (useBack)
                     backgroundProgressBarColor = resources.getColor(R.color.grey_progress_bar_back)
                 else
                     backgroundProgressBarColor = resources.getColor(R.color.white)



                 // or with gradient
                 /*backgroundProgressBarColorStart = Color.WHITE
                 backgroundProgressBarColorEnd = Color.RED
                 backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM*/

                 progressBarWidth = 5f // in DP
                 backgroundProgressBarWidth = 3f // in DP
             }




         }


if (sub.imageDrawable != "menu_subs_foreground") {
 //   subImageView.setImageResource(sub.imageDrawable)
     var t: Int = context.resources.getIdentifier(sub.imageDrawable,"drawable", "com.example.antipodpiska")
     subImageView.setImageResource(t)


    if(layout!=null)
    {
       t =  context.resources.getIdentifier(sub.color, "color", "com.example.antipodpiska")
        layout.setCardBackgroundColor(context.getColor(t))
    }
}
            else{
                subImageView.setImageResource(R.drawable.menu_subs_foreground)
    layout.setCardBackgroundColor(context.getColor(R.color.light_back))
            }
        }


        fun rusification(period: String, periodType: String): String {

            var tmp: String = ""
            if (periodType == "Месяцев") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "месяц"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 || period.toInt() % 10 in 5..9 -> tmp = "месяцев"
                    period.toInt() % 10 in 2..4 -> tmp = "месяца"
                }
            }
            else if (periodType == "Дней") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "день"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 || period.toInt() % 10 in 5..9 -> tmp = "дней"
                    period.toInt() % 10 in 2..4 -> tmp = "дня"
                }
            }
            else if (periodType == "Недель") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "неделю"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 || period.toInt() % 10 in 5..9 -> tmp = "недель"
                    period.toInt() % 10 in 2..4 -> tmp = "недели"
                }
            }

            return tmp
        }


    }


fun onItemInit(viewHolder: RecyclerView.ViewHolder){

}
    fun onItemBigResize(viewHolder: RecyclerView.ViewHolder?, position: Int, dyAbs: Int) {}

    fun onItemSmallResize(viewHolder: RecyclerView.ViewHolder?, position: Int, dyAbs: Int) {}

    fun onItemBigResizeScrolled(viewHolder: RecyclerView.ViewHolder?, position: Int, dyAbs: Int) {}

    fun onItemSmallResizeScrolled(
        viewHolder: RecyclerView.ViewHolder?,
        position: Int,
        dyAbs: Int
    ) {
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    /*первоначально создание ViewHolders. parent - это и есть recycleView*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_sub, parent, false)
        return SubViewHolder(view, onClick, parent.context)
    }

    /* Gets current flower and uses it to bind view. */
    /*Изменение значения существующих ViewHolders при прокрутке*/
    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        var sub = getItem(position)

        holder.bind(sub)
    }





}

fun getRGB(rgb: String): Boolean {
    val r = rgb.substring(2, 4).toInt(16) // 16 for hex
    val g = rgb.substring(4, 6).toInt(16) // 16 for hex
    val b = rgb.substring(6, 8).toInt(16) // 16 for hex


    val L =( 0.2126 * pow(r.toDouble(), 2.2)
    + 0.7152 * pow(g.toDouble(), 2.2)
    + 0.0722 * pow(b.toDouble(), 2.2))/100000

    //> 0.5
    val use_black = L > 1.4
    return use_black
}


object FlowerDiffCallback : DiffUtil.ItemCallback<Sub>() {
    override fun areItemsTheSame(oldItem: Sub, newItem: Sub): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Sub, newItem: Sub): Boolean {
        return oldItem.id == newItem.id
    }

}