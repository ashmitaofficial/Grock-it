package com.example.groceryapp.location

import android.view.View
import android.widget.TextView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem

class LocationViewHolder(itemView: View):BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {
        val data = item.data as String
        val textView= itemView.findViewById<TextView>(R.id.location_txt)
        textView.text=data.substring(0,6)+"..."

    }
}