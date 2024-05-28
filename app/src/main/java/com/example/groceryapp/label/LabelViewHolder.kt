package com.example.groceryapp.label

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.product.ProductListingFragment

class LabelViewHolder(itemView: View, val context: FragmentActivity) : BaseViewHolder(itemView) {
    override fun bind(item: DynamicItem) {
        val data = item.data as String
        val textView = itemView.findViewById<TextView>(R.id.label)
        textView.text = data

        val seeAll_label = itemView.findViewById<TextView>(R.id.see_all_label)


        seeAll_label.setOnClickListener {
            if (!data.lowercase().contains("categor")) {
                context.supportFragmentManager.beginTransaction()
                    .add(R.id.container, ProductListingFragment::class.java, null)
                    .addToBackStack(null)
                    .commit()
            }else{
                (context as HomeActivity).bottomNavigationView.selectedItemId = R.id.explore_txt
            }
        }

    }
}