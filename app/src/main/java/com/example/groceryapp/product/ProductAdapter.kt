package com.example.groceryapp.product

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.home.productList.ProductListViewModel
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class ProductAdapter(private val list: ArrayList<Product>,val context: FragmentActivity) : RecyclerView.Adapter<ProductAdapter.MyViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.productName.text = list[position].title
        holder.productDesc.text = list[position].description
        holder.productPrice.text = "\u20B9 " + list[position].price
        Picasso.get().load(list[position].image).into(holder.productImage)

        holder.cardview.setOnClickListener {
           val productDetailIntent= Intent(context,ProductdetailActivity::class.java)
            productDetailIntent.putExtra("id",list[position].id)
            context.startActivity(productDetailIntent)
        }



    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_img)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var productDesc = view.findViewById<TextView>(R.id.product_desc)
        var productPrice = view.findViewById<TextView>(R.id.price)
        var cardview = view.findViewById<MaterialCardView>(R.id.cardView)

//        var addButton = view.findViewById<ImageButton>(R.id.add_btn)
//        var countContainer = view.findViewById<LinearLayout>(R.id.count_container)
    }
}