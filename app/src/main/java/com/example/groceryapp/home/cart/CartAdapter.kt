package com.example.groceryapp.home.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.product.Product
import com.squareup.picasso.Picasso

class CartAdapter(private val list: ArrayList<Product>, val plusClicked: (HashMap<String, Any>) -> Unit,val removeCartItem: (Int) ->Unit,val minusClicked:(Int) ->Unit):
    RecyclerView.Adapter<CartAdapter.MyViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return MyViewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Picasso.get().load(list[position].image).into(holder.productImage)
        holder.productName.text= list[position].title
        holder.product_desc.text= list[position].description
        holder.price.text= "\u20B9"+ list[position].price
        holder.quantity.text=list[position].quantity.toString()

        holder.plusbtn.setOnClickListener {
            val map = HashMap<String, Any>()
            map.put("id", list[position].id!!)
            map.put("image", list[position].image.toString())
            map.put("price", list[position].price.toString())
            map.put("currency", list[position].currency.toString())
            map.put("description", list[position].description.toString())
            map.put("title", list[position].title.toString())
            plusClicked(map)
        }

        holder.close_btn.setOnClickListener {
            removeCartItem(list[position].id!!)
        }

        holder.minusbtn.setOnClickListener {
            minusClicked(list[position].id!!)
        }

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_image)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var product_desc = view.findViewById<TextView>(R.id.product_desc)
        var price = view.findViewById<TextView>(R.id.price)
        var quantity = view.findViewById<TextView>(R.id.quantity)
        var plusbtn = view.findViewById<ImageButton>(R.id.plusbtn)
        var close_btn = view.findViewById<ImageButton>(R.id.close_btn)
        var minusbtn = view.findViewById<ImageButton>(R.id.minusbtn)

    }
}