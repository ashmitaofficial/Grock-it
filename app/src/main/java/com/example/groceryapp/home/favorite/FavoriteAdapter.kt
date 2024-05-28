package com.example.groceryapp.home.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductdetailActivity
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val list: ArrayList<Product>,val context: FragmentActivity): RecyclerView.Adapter<FavoriteAdapter.MyViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Picasso.get().load(list[position].image).into(holder.productImage)
        holder.productName.text= list[position].title
        holder.product_desc.text= list[position].description
        holder.price.text= "\u20B9"+list[position].price

        holder.right_icon.setOnClickListener {
            val productListIntent =Intent(context,ProductdetailActivity::class.java)
            productListIntent.putExtra("id", list[position].id)
            context.startActivity(productListIntent)
        }

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_image)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var product_desc = view.findViewById<TextView>(R.id.product_desc)
        var price = view.findViewById<TextView>(R.id.price)
        var right_icon = view.findViewById<ImageButton>(R.id.right_icon)
    }
}