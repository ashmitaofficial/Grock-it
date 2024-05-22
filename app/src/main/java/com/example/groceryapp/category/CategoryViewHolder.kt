package com.example.groceryapp.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem

class CategoryViewHolder(itemView: View) : BaseViewHolder(itemView) {

    override fun bind(item: DynamicItem) {
        val data = item.data as ArrayList<Category>
        val recycler_view = itemView.findViewById<RecyclerView>(R.id.home_grocery_recycler_view)
        recycler_view.adapter = CategoryAdapter(data)
    }
}