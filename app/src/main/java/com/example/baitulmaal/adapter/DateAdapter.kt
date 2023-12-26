package com.example.baitulmaal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baitulmaal.CategorySource
import com.example.baitulmaal.R

class DateAdapter(val dateList: Array<String>) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    val array = arrayOf("Uang Keluar", "Uang Keluar")

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val dateTextView: TextView = itemView.findViewById(R.id.tanggalView)
            val itemsRincian: RecyclerView = itemView.findViewById(R.id.itemsRincian)

            fun bind(word: String) {
                dateTextView.text = word
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rincian_layout, parent, false)

        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dateList[position])

        holder.itemsRincian.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = CategoryAdapter(CategorySource(this).getCategoryList())
        holder.itemsRincian.adapter = adapter
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}