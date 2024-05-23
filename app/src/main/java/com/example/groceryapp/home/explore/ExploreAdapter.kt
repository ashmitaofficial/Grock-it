package com.example.groceryapp.home.explore

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.category.Category
import com.example.groceryapp.product.ProductListingFragment
import com.example.groceryapp.product.ProductdetailActivity
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ExploreAdapter(private val list: ArrayList<Category>,val context:FragmentActivity) :
    RecyclerView.Adapter<ExploreAdapter.MyViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Picasso.get().load(list[position].image).into(holder.productImage)
        holder.productName.text= list[position].title
        holder.cardView.setCardBackgroundColor(Color.parseColor(list[position].background))
        holder.cardView.setStrokeColor(Color.parseColor(list[position].border))

        holder.cardView.setOnClickListener {
            val bundle= Bundle()
            list[position].id?.let {cid ->
                bundle.putInt(AppConstants.CATEGORY_ID,cid)
                context.supportFragmentManager.beginTransaction()
                    .add(R.id.container, ProductListingFragment::class.java, bundle)
                    .addToBackStack(null)
                    .commit()
            }

        }

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_img)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var cardView = view.findViewById<MaterialCardView>(R.id.cardView)


    }

}