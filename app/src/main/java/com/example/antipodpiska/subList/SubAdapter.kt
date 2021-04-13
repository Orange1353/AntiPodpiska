package com.example.antipodpiska.subList


//import android.widget.ListAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.SharedPrefSource
import com.example.antipodpiska.data.Sub
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue


class SubAdapter(private val onClick: (Sub) -> Unit) :
    ListAdapter<Sub, SubAdapter.SubViewHolder>(FlowerDiffCallback) {


    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class SubViewHolder(itemView: View, val onClick: (Sub) -> Unit, context: Context) :
        RecyclerView.ViewHolder(itemView) {
           private val subName: TextView = itemView.findViewById(R.id.flower_text)
        private val subImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private val addDatePay: TextView =  itemView.findViewById(R.id.day_pay_calulat)
        private val status: EditText = itemView.findViewById(R.id.text_status)
        private val subDateUntil: TextView = itemView.findViewById(R.id.textViewUntil)
        private val sharedPrefSource: SharedPrefSource = SharedPrefSource(context)
        private val context: Context = context

        private var currentSub: Sub? = null

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

            currentSub = sub

            if (sub.name.length > 12)
            subName.text = sub.name.substring(0, 12)+".."
            else
                subName.text = sub.name
//            if (sub.card != "")
            //  addDatePay.text = sub.datePay


            if (currentSub?.status == "Архив")
            {
                addDatePay.text = "Отписались " + currentSub?.datePay
                status.setText("Архив")
                //    status.setBackgroundResource(R.drawable.shape_text_input)

               // val wrappedDrawable = DrawableCompat.wrap(status.getBackground())
              //      DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#737679"))
              //     status.setBackgroundDrawable(wrappedDrawable)
            }

            else
            if (currentSub?.datePay != null && currentSub?.datePay !="")
         {
            var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var datePay = LocalDate.parse(currentSub?.datePay, formatter)

            var dateNow = LocalDate.now()



             if (currentSub?.periodFree != "") {
                 when (currentSub?.periodTypeFree) {
                     "Дней" -> datePay = datePay.plusDays(currentSub?.periodFree!!.toLong())
                     "Недель" -> datePay = datePay.plusWeeks(currentSub?.periodFree!!.toLong())
                     "Месяцев" -> datePay = datePay.plusDays(30 * currentSub?.periodFree!!.toLong())
                 }
      /*           if (datePay > dateNow) {
                     status.setText("Бесплатно")
                     status.setBackgroundResource(R.drawable.shape_text_input)
                     val wrappedDrawable = DrawableCompat.wrap(status.getBackground())
                     DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#9934BD"))
                     status.setBackgroundDrawable(wrappedDrawable)


                 }*/
             }


                //   while (datePay < dateNow)
                if (currentSub?.periodPay != "") {
                    while (datePay <= dateNow)
                        when (currentSub?.periodTypePay) {
                            "Дней" -> datePay = datePay.plusDays(currentSub?.periodPay!!.toLong())
                            "Недель" -> datePay =  datePay.plusWeeks(currentSub?.periodPay!!.toLong())
                            "Месяцев" -> datePay =  datePay.plusDays(30 * currentSub?.periodPay!!.toLong())
                        }
                }


                    if (datePay!!.format(formatter) != sub.datePay){

                        if (sub.periodPay != "")
                        addDatePay.text = "Следующий платёж " + datePay.format(formatter).toString() //+ " стоимость " + sub.costSub + " " + sub.costCurr //+ "/ " + sub.periodPay + " " + tmp
                       // else
                      //      addDatePay.text = "Следующий платёж " + datePay.format(formatter).toString()// + " стоимость " + sub.costSub + " " + sub.costCurr

                }

        //       subDateUntil.text = "до "+ datePay.format(formatter).toString()
             if (sub.periodPay !="")
                 subDateUntil.text = "за\n"+ sub.periodPay+" "+ rusification(sub.periodPay, sub.periodTypePay)


         //      sharedPrefSource.setNeardayCost(context, sub.id, sub.costSub)
               sharedPrefSource.setNeardayPayDate(context, sub.id, datePay.format(formatter).toString())

        if (sub.costSub != "")
        { when(sub.costCurr){
                     "RUB" -> status.setText(sub.costSub +  "₽")
                     "EUR" -> status.setText(sub.costSub + "€")
                     "USD" -> status.setText(sub.costSub + "$")
                     "GPB" -> status.setText(sub.costSub + "£")
                     "CNY" -> status.setText(sub.costSub + "Ұ")
                     "CHF" -> status.setText(sub.costSub + "₣")
                     "JPY" -> status.setText(sub.costSub + "¥")
                     "BTC" -> status.setText(sub.costSub + "₿")
                     "OTHER" -> status.setText(sub.costSub + "")
             }


             status.setTextColor(Color.parseColor("#BF2222"))}
             val circularProgressBar = itemView.findViewById<CircularProgressBar>(R.id.circularProgressBar)

             circularProgressBar.apply {
                 // Set Progress
                 roundBorder = true
                 startAngle = 0f
                 progressBarColor = resources.getColor(R.color.blue_light)

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
                       "Дней"   -> {
                           progressMax = period
                           countDays.text = Math.round(progress).toString()+"\n"+rusification((Math.round(progress).absoluteValue).toString(), "Дней")
                               //(Math.round(progressMax) - Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                       }
                        "Недель"   -> {
                            progressMax = period*14
                            countDays.text = Math.round(progress).absoluteValue.toString()+"\n"+rusification((Math.round(progress).absoluteValue).toString(), "Дней")
                                //(Math.round(progressMax)- Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                        }
                        "Месяцев"   -> {
                            progressMax = period*30
                            countDays.text = Math.round(progress).absoluteValue.toString()+"\n"+rusification((Math.round(progress).absoluteValue).toString(), "Дней")
                                //( Math.round(progressMax)- Math.round(progress)).absoluteValue.toString()+"\n"+rusification((Math.round(progressMax) -  Math.round(progress)).toString(), "Дней")
                        }
                    }

                     if(progress < 4) {
                         countDays.setTextColor(resources.getColor(R.color.orange_light))
                         progressBarColor = resources.getColor(R.color.orange_light)
                     }
                 }



                /* progressBarColorStart = Color.parseColor("#EC8074")
                 progressBarColorEnd = Color.parseColor("#48B2FF")
                 progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
*/


         //        progressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM
                 // Set background ProgressBar Color
                 backgroundProgressBarColor = resources.getColor(R.color.grey_progress_bar)
                 // or with gradient
                 /*backgroundProgressBarColorStart = Color.WHITE
                 backgroundProgressBarColorEnd = Color.RED
                 backgroundProgressBarColorDirection = CircularProgressBar.GradientDirection.TOP_TO_BOTTOM*/

                 progressBarWidth = 3f // in DP
                 backgroundProgressBarWidth = 2f // in DP
             }




         }


         /* if (sub.image != null) {
           //     subImageView.setImageResource(R.drawable.shape_initial_item)
           //     subImageView.setGravity(Gravity.CENTER)
           //     subImageView.setBackgroundColor(Color.BLUE)
           //      subImageView.setText("F");

                val radius: Float = 20F
                val shapeAppearanceModel = ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, radius)
                        .build()
                val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                var t = Color.parseColor("#" + sub.image)
                shapeDrawable.setTint(t)
                ViewCompat.setBackground(subImageView, shapeDrawable)
                subImageView.setGravity(Gravity.CENTER)
               var firstLetter: String = sub.name.substring(0, 1)
                firstLetter=firstLetter.toUpperCase()
                subImageView.setText(firstLetter)



            } else {
                subImageView.setBackgroundResource(R.drawable.img)
            }*/


        subImageView.setImageResource( R.drawable.img)
        }


        fun rusification(period: String, periodType: String): String {

            var tmp: String = ""
            if (periodType == "Месяцев") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "месяц"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0-> tmp = "месяцев"
                    period.toInt() % 10 in 2..4 -> tmp = "месяца"
                }
            }
            else if (periodType == "Дней") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "день"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 -> tmp = "дней"
                    period.toInt() % 10 in 2..4 -> tmp = "дня"
                }
            }
            else if (periodType == "Недель") {
                when
                {
                    period.toInt() % 10 == 1 -> tmp = "неделю"
                    //  period.substring(period.length-3, period.length-1)
                    period.toInt() % 100 in 5..20 || period.toInt() % 10 == 0 -> tmp = "недель"
                    period.toInt() % 10 in 2..4 -> tmp = "недели"
                }
            }

            return tmp
        }


    }





    /* Creates and inflates view and return FlowerViewHolder. */
    /*первоначально создание ViewHolders. parent - это и есть recycleView*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)
        return SubViewHolder(view, onClick, parent.context)
    }

    /* Gets current flower and uses it to bind view. */
    /*Изменение значения существующих ViewHolders при прокрутке*/
    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        var sub = getItem(position)
      /*  if(sub.status== "Архив") {
            holder.itemView.layoutParams = LinearLayout.LayoutParams(0, 0)
        }
*/


        val totalItems = itemCount
        val view: View = holder.itemView

        if (position === totalItems - 1) { // final position will equal total items - 1
            view.scaleX = 1f
            view.scaleY = 1f
        } else if (position === totalItems - 2) {
            view.scaleX = 0.8f
            view.scaleY = 0.8f
        } else if (position === totalItems - 3) {
            view.scaleX = 0.6f
            view.scaleY = 0.6f
        } else {
            view.scaleX = 0.4f
            view.scaleY = 0.4f
        }


        holder.bind(sub)
    }


}

object FlowerDiffCallback : DiffUtil.ItemCallback<Sub>() {
    override fun areItemsTheSame(oldItem: Sub, newItem: Sub): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Sub, newItem: Sub): Boolean {
        return oldItem.id == newItem.id
    }



}