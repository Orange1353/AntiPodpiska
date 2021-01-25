package com.example.antipodpiska.subList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R

/* A list always displaying one element: the number of flowers. */

class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    private var subCount: Int = 0

    /* ViewHolder for displaying header. */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val subNumberTextView: TextView = itemView.findViewById(R.id.flower_number_text)

        fun bind(subCount: Int) {
            subNumberTextView.text = subCount.toString()
        }
    }

    /* Inflates view and returns HeaderViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.header_item, parent, false)
        return HeaderViewHolder(view)
    }

    /* Binds number of flowers to the header. */
    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(subCount)
    }

    /* Returns number of items, since there is only one item in the header return one  */
    override fun getItemCount(): Int {
        return 1
    }

    /* Updates header to display number of flowers when a flower is added or subtracted. */
    fun updateSubCount(updatedSubCount: Int) {
        subCount = updatedSubCount
        notifyDataSetChanged()
    }
}