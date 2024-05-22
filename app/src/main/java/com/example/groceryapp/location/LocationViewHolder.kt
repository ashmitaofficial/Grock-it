package com.example.groceryapp.location

import android.view.View
import android.widget.TextView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem

class LocationViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {
        var data: String? = null
        var length =0
        item.data?.let {
            data = it as String
            length=it.length
        }

        val textView = itemView.findViewById<TextView>(R.id.location_txt)

        textView.text = (if (length >= 6) {
            data?.substring(0, 6) + "..."
        } else {
            data
        }).toString()


    }
}