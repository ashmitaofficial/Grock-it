package com.example.groceryapp.category

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.product.ProductListingFragment
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class CategoryAdapter(private val list: ArrayList<Category>,val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.MyViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grocery_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Picasso.get().load(list[position].image).into(holder.categoryImage)
        holder.category_name.text = list[position].title

        holder.cardView.setCardBackgroundColor(Color.parseColor(list[position].background))

        holder.cardView.setOnClickListener {
            (context as HomeActivity).bottomNavigationView.selectedItemId = R.id.explore_txt        }

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryImage = view.findViewById<ImageView>(R.id.category_img)
        var category_name = view.findViewById<TextView>(R.id.category_name)
        var cardView = view.findViewById<CardView>(R.id.cardView_category)

    }


}