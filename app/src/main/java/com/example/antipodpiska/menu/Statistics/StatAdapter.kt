package com.example.antipodpiska.menu.Statistics

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subList.FlowerDiffCallback
import com.example.antipodpiska.subList.SubAdapter

class StatAdapter (private val onClick: (CategoryWithValue) -> Unit) :
    ListAdapter<CategoryWithValue, StatAdapter.StatViewHolder>(com.example.antipodpiska.menu.Statistics.FlowerDiffCallback) {


    class StatViewHolder(itemView: View, val onClick: (CategoryWithValue) -> Unit) :
            RecyclerView.ViewHolder(itemView) {

        private val imgSubs: ImageView = itemView.findViewById(R.id.image_rect)
        private val typeSubs: TextView = itemView.findViewById(R.id.name_category)
        private val valueSubs: TextView = itemView.findViewById(R.id.cost_category)
        private var categoryWithValue: CategoryWithValue? = null

        init {
            itemView.setOnClickListener {
                categoryWithValue?.let {
                onClick(it)
            } }
        }

        fun bind(categoryWithValue1: CategoryWithValue) {
            categoryWithValue = categoryWithValue1
            typeSubs.text = categoryWithValue1.type

            valueSubs.text = String.format("%.2f", categoryWithValue1.value ) + " ₽"
            imgSubs.background.setTint(categoryWithValue1.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.element_stat_categories, parent, false)
        return StatAdapter.StatViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        val type = getItem(position)
        holder.bind(type)
    }



}

object FlowerDiffCallback : DiffUtil.ItemCallback<CategoryWithValue>() {
    override fun areItemsTheSame(oldItem: CategoryWithValue, newItem: CategoryWithValue): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryWithValue, newItem: CategoryWithValue): Boolean {
        return oldItem.id == newItem.id
    }



}