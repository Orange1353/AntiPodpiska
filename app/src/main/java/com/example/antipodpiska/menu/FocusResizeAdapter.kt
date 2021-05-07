package com.example.antipodpiska.menu

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.antipodpiska.R
import com.example.antipodpiska.data.Sub
import com.example.antipodpiska.subList.SubAdapter
import com.example.library.Utils


abstract class FocusResizeAdapter<T : RecyclerView.ViewHolder?>(private val context: Context, val height: Int) :
    RecyclerView.Adapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return if (viewType == VIEW_TYPE_FOOTER) {
            FooterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false)
            ) as T
        } else {
            onCreateFooterViewHolder(parent, viewType)
        }
    }



    override fun getItemCount(): Int {
        return if (footerItemCount == 0) {
            0
        } else footerItemCount + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && position == itemCount - 1) {
            VIEW_TYPE_FOOTER
        } else getItemFooterViewType(position)
    }

    abstract fun onItemBigResize(viewHolder: RecyclerView.ViewHolder?, position: Int, dyAbs: Int)
    abstract fun onItemBigResizeScrolled(
        viewHolder: RecyclerView.ViewHolder?,
        position: Int,
        dyAbs: Int
    )

    abstract fun onItemSmallResizeScrolled(
        viewHolder: RecyclerView.ViewHolder?,
        position: Int,
        dyAbs: Int
    )

    abstract fun onItemSmallResize(viewHolder: RecyclerView.ViewHolder?, position: Int, dyAbs: Int)
    abstract fun onItemInit(viewHolder: RecyclerView.ViewHolder?)
    fun getItemFooterViewType(position: Int): Int {
        return 0
    }

    abstract val footerItemCount: Int

    abstract fun onCreateFooterViewHolder(parent: ViewGroup?, viewType: Int): T
    abstract fun onBindFooterViewHolder(holder: T, position: Int)
    inner class FooterViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        init {
            v.layoutParams.height = Utils.getFooterHeight(
                context as Activity,
                height * 3
            )
        }
    }

    companion object {
        private const val VIEW_TYPE_FOOTER = 1
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