package com.example.antipodpiska.menu.Statistics

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subList.FlowerDiffCallback
import com.example.antipodpiska.subList.SubAdapter

class StatAdapter (private val onClick: (position: Int) -> Unit) :
        RecyclerView.Adapter<StatAdapter.StatViewHolder>() {

    private var mAllRepositories: List<CategoryWithValue>? = null

    class StatViewHolder(itemView: View, val onClick: (position: Int) -> Unit) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener  {

        private val typeSubs: TextView = itemView.findViewById(R.id.name_category)
        private val valueSubs: TextView = itemView.findViewById(R.id.cost_category)


        init {
            itemView.setOnClickListener (this)
        }

        fun bind(categoryWithValue: CategoryWithValue) {
            typeSubs.text = categoryWithValue.type
            valueSubs.text = categoryWithValue.value.toString()
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onClick(position)
        }

    }

    fun setmAllRepositories(repositories: List<CategoryWithValue>?) {
        mAllRepositories = repositories
        Log.e("kkkkkkkkkkkkkkkk", mAllRepositories.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.element_stat_categories, parent, false)
        return StatAdapter.StatViewHolder(view, onClick)
    }


    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(mAllRepositories!![position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}