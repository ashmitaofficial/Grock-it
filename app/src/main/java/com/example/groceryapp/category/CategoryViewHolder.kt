package com.example.groceryapp.category

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.product.ProductListingFragment
import com.google.android.material.card.MaterialCardView

class CategoryViewHolder(itemView: View,val context: FragmentActivity) : BaseViewHolder(itemView) {

    override fun bind(item: DynamicItem) {
        val data = item.data as ArrayList<Category>
        val recycler_view = itemView.findViewById<RecyclerView>(R.id.home_grocery_recycler_view)
        recycler_view.adapter = CategoryAdapter(data,context,context)



    }
}