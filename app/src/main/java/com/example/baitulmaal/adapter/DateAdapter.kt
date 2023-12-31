package com.example.baitulmaal.adapter

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baitulmaal.R
import com.example.baitulmaal.model.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DecimalFormat

class DateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null
    var items: MutableList<DataItem> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val moneyText: TextView = itemView.findViewById(R.id.moneyRincian)
        val noteText: TextView = itemView.findViewById(R.id.noteRincian)
        val categoryText: TextView = itemView.findViewById(R.id.categoryRincian)
        val imageCategory: ImageView = itemView.findViewById(R.id.img)

        fun bind(item: DataItem.DataList) {
            moneyText.text = setTotal(item.items.moneySaving)
            noteText.text = item.items.noteSaving
            categoryText.text = item.items.categorySaving
            if (item.items.categorySaving == "Uang Keluar") {
                imageCategory.background = itemView.resources.getDrawable(R.drawable.out_money)
            } else {
                imageCategory.background = itemView.resources.getDrawable(R.drawable.entry_money)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.tanggalView)

        fun bind(item: DataItem.DataTitle) {
            dateText.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(DataItem.ViewType.values()[viewType]) {
            DataItem.ViewType.TITLE -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rincian_layout, parent, false))
            DataItem.ViewType.LIST -> ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_value, parent, false))

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> {
                holder.bind(items[position] as DataItem.DataTitle)
            }
            is ListViewHolder -> {
                val item = items[position] as DataItem.DataList
                holder.bind(items[position] as DataItem.DataList)
                holder.itemView.setOnClickListener { 
                    if (onClickListener != null) {
                        onClickListener!!.onClick(position, item.items)
                    }
                }
            }
        }
    }

    private fun setTotal(amount: Double) : String {
        val spannableString: SpannableString = SpannableString(
            "Rp." +
                    " " +
                    DecimalFormat("#,###").format(amount) +
                    ",-"
        )
        spannableString.setSpan(RelativeSizeSpan(0.6f), 1, 1, 0)
        return spannableString.toString()
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Savings)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun getItemCount(): Int {
        return items.size
    }
}