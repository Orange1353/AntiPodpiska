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
    ListAdapter<ExistSub, ExistSubAdapter.ExistSubViewHolder>(FlowerDiffCallback1) {


    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class ExistSubViewHolder(itemView: View, val onClick: (ExistSub) -> Unit, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val subName: TextView = itemView.findViewById(R.id.flower_text)
        private val subImageView: ImageView = itemView.findViewById(R.id.flower_image)

        private val context: Context = context

        private var currentSub: ExistSub? = null

        init {
            itemView.setOnClickListener {
                currentSub?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image.
        * изменяет значение у уже существующего Viewholder*/
        fun bind(sub: ExistSub) {
            subName.text = sub.name
            subImageView.setBackgroundResource(sub.logoId)
        }




    }





    /* Creates and inflates view and return FlowerViewHolder. */
    /*первоначально создание ViewHolders. parent - это и есть recycleView*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExistSubViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_row_item_add_subs, parent, false)
        return ExistSubViewHolder(view, onClick, parent.context)
    }

    /* Gets current flower and uses it to bind view. */
    /*Изменение значения существующих ViewHolders при прокрутке*/
    override fun onBindViewHolder(holder: ExistSubViewHolder, position: Int) {
        var subExist = getItem(position)

        holder.bind(subExist)
    }


}

object FlowerDiffCallback1 : DiffUtil.ItemCallback<ExistSub>() {
    override fun areItemsTheSame(oldItem: ExistSub, newItem:  ExistSub): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem:  ExistSub, newItem:  ExistSub): Boolean {
        return oldItem.id == newItem.id
    }



}