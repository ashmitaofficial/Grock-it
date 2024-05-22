package com.example.groceryapp.label

import android.view.View
import android.widget.TextView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem

class LabelViewHolder(itemView: View): BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {
        val data = item.data as String
        val textView= itemView.findViewById<TextView>(R.id.label)
        textView.text=data

    }
}