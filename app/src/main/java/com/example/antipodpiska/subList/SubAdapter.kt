package com.example.antipodpiska.subList


//import android.widget.ListAdapter

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class SubAdapter(private val onClick: (Sub) -> Unit) :
    ListAdapter<Sub, SubAdapter.SubViewHolder>(FlowerDiffCallback) {

    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class SubViewHolder(itemView: View, val onClick: (Sub) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val subName: TextView = itemView.findViewById(R.id.flower_text)
        private val subImageView: TextView = itemView.findViewById(R.id.flower_image)
        private val cardNum: TextView = itemView.findViewById(R.id.card_num)
        private val addDatePay: TextView =  itemView.findViewById(R.id.day_pay_calulat)
        private val costNear: TextView =  itemView.findViewById(R.id.cost_nearest)


       /* private val addSubName: TextView = itemView.findViewById(R.id.add_flower_name)
        private val addSubDescription: TextView = itemView.findViewById(R.id.add_flower_description)
        private val addSubEndDate: TextView = itemView.findViewById(R.id.add_end_date)

        private val addPeriodFree: TextView =  itemView.findViewById(R.id.add_free_days)
        private val addCostSub: TextView =  itemView.findViewById(R.id.add_cost)
        private val addPeriodPay: TextView =  itemView.findViewById(R.id.add_period)
        private val addPeriodTypeFree: Spinner = itemView.findViewById(R.id.spinner_free_period_type)
        private val addCostCurr: Spinner = itemView.findViewById(R.id.spinner_period_pay)
        private val addPeriodTypePay: Spinner = itemView.findViewById(R.id.spinner_period_pay)
*/

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

            subName.text = sub.name

            if (sub.card != "")
                cardNum.text = "*" + sub.card
            //  addDatePay.text = sub.datePay

            if (currentSub?.datePay != null && currentSub?.datePay !="")
            {
            var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var datePay = LocalDate.parse(currentSub?.datePay, formatter)

            var dateNow = LocalDate.now()


             if (currentSub?.periodFree != "")
            when (currentSub?.periodTypeFree) {
                "Days" -> datePay = datePay.plusDays(currentSub?.periodFree!!.toLong())
                "Weeks" -> datePay = datePay.plusWeeks(currentSub?.periodFree!!.toLong())
                "Mounths" -> datePay = datePay.plusMonths(currentSub?.periodFree!!.toLong())
            }


                //   while (datePay < dateNow)
                if (currentSub?.periodPay != "") {
                    while (datePay < dateNow)
                        when (currentSub?.periodTypePay) {
                            "Days" -> datePay = datePay.plusDays(currentSub?.periodPay!!.toLong())
                            "Weeks" -> datePay = datePay.plusWeeks(currentSub?.periodPay!!.toLong())
                            "Mounths" -> datePay = datePay.plusMonths(currentSub?.periodPay!!.toLong())
                        }
                }
                addDatePay.text =datePay.format(formatter).toString()
        }



            costNear.text = sub.costSub + " " + sub.costCurr
            /*addSubName.text =
            addSubDescription.text
            addSubEndDate.text
            addDatePay.text
            addPeriodFree.text
            addCostSub.text
            addPeriodPay.text
            addPeriodTypeFree.selectedItem.toString()
            addCostCurr.selectedItem.toString()
            addPeriodTypePay.selectedItem.toString()*/


            if (sub.image != null) {
           //     subImageView.setImageResource(R.drawable.shape_initial_item)

         //       subImageView.setGravity(Gravity.CENTER)

           //     subImageView.setBackgroundColor(Color.BLUE)

          //      subImageView.setText("F");


                val radius: Float = 20F
                val shapeAppearanceModel = ShapeAppearanceModel()
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, radius)
                        .build()
                val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)

                var t = Color.parseColor("#"+ sub.image)
                shapeDrawable.setTint(t)
                ViewCompat.setBackground(subImageView, shapeDrawable)
                subImageView.setGravity(Gravity.CENTER)
               var firstLetter: String = sub.name.substring(0, 1)
                firstLetter=firstLetter.toUpperCase()
                subImageView.setText(firstLetter)



            } else {
                subImageView.setBackgroundResource(R.drawable.img)
            }
        }
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    /*первоначально создание ViewHolders. parent - это и есть recycleView*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item, parent, false)
        return SubViewHolder(view, onClick)
    }

    /* Gets current flower and uses it to bind view. */
    /*Изменение значения существующих ViewHolders при прокрутке*/
    override fun onBindViewHolder(holder: SubViewHolder, position: Int) {
        val sub = getItem(position)
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