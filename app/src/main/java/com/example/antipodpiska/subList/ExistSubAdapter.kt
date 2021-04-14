package com.example.antipodpiska.subList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.ExistSub


class ExistSubAdapter(private val onClick: (ExistSub) -> Unit) :
    ListAdapter<ExistSub, ExistSubAdapter.ExistSubViewHolder>(FlowerDiffCallback) {

    class ExistSubViewHolder(itemView: View, val onClick: (ExistSub) -> Unit, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val subName: TextView = itemView.findViewById(R.id.flower_text)
        private val subImage: ImageView = itemView.findViewById((R.id.flower_image))
        private val context: Context = context

        private var currentExistSub: ExistSub? = null

        init {
            itemView.setOnClickListener {
                currentExistSub?.let {
                    onClick(it)
                }
            }
        }

        fun bind(sub: ExistSub) {

            currentExistSub = sub
            subName.text = sub.name
            subImage.setImageResource(sub.logoId)
        }


    }

    object FlowerDiffCallback : DiffUtil.ItemCallback<ExistSub>() {
        override fun areItemsTheSame(oldItem: ExistSub, newItem: ExistSub): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExistSub, newItem: ExistSub): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistSubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item_add_subs, parent, false)
        return ExistSubViewHolder(view, onClick, parent.context)
    }

    override fun onBindViewHolder(holder: ExistSubViewHolder, position: Int) {
        var sub = getItem(position)

        holder.bind(sub)    }
}