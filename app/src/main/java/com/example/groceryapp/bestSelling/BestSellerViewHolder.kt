package com.example.groceryapp.bestSelling

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductAdapter

class BestSellerViewHolder(itemView: View,val context: FragmentActivity) : BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {
        val data = item.data as ArrayList<Product>
        val recycler_view = itemView.findViewById<RecyclerView>(R.id.home_bestseller_recycler_view)
        recycler_view.adapter = ProductAdapter(data,context)
    }
}