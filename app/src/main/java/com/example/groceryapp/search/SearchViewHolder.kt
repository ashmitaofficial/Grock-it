package com.example.groceryapp.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.example.groceryapp.R
import com.example.groceryapp.base.BaseViewHolder
import com.example.groceryapp.base.DynamicItem
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.product.ProductListingFragment


class SearchViewHolder(itemView: View,val context: FragmentActivity) : BaseViewHolder(itemView) {
    lateinit var search_bar:SearchView

    override fun bind(item: DynamicItem) {
        search_bar=itemView.findViewById<SearchView>(R.id.search_bar)

        search_bar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle=Bundle()
                bundle.putString(AppConstants.SEARCHED_ITEM,query)
                context.supportFragmentManager.beginTransaction()
                    .add(R.id.container, ProductListingFragment::class.java, bundle)
                    .addToBackStack(null)
                    .commit()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }
}