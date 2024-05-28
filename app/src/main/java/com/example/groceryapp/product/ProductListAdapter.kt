package com.example.groceryapp.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ProductListAdapter(private val list: ArrayList<Product>,context:FragmentActivity) : RecyclerView.Adapter<ProductListAdapter.MyViewholder>() {
    private val myList: ArrayList<Product>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_listing_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.productName.text = list[position].title
        holder.productDesc.text = list[position].description
        Picasso.get().load(list[position].image).into(holder.productImage)


    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_img)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var productDesc = view.findViewById<TextView>(R.id.product_desc)
        var productPrice = view.findViewById<TextView>(R.id.price)
        var cardview = view.findViewById<MaterialCardView>(R.id.cardView_category)
        var fav_btn=view.findViewById<ImageButton>(R.id.fav_btn)


//        var addButton = view.findViewById<ImageButton>(R.id.add_btn)
//        var countContainer = view.findViewById<LinearLayout>(R.id.count_container)
    }
}