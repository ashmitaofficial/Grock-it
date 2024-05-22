package com.example.groceryapp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
abstract fun bind(item: DynamicItem)
}

