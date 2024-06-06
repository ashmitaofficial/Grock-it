package com.example.groceryapp.product

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

class ProductAdapter(private val list: MutableList<Product>, val context: FragmentActivity) :
    RecyclerView.Adapter<ProductAdapter.MyViewholder>() {
    companion object {
        const val ADD_BTN = "add"
        const val PLUS_BTN = "plus"
    }


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
        holder.productPrice.text = "\u20B9" + list[position].price
        Picasso.get().load(list[position].image).into(holder.productImage)

        holder.cardview.setOnClickListener {
            val productDetailIntent = Intent(context, ProductdetailActivity::class.java)
            productDetailIntent.putExtra("id", list[position].id)
            context.startActivity(productDetailIntent)
        }


        holder.addButton.setOnClickListener {
            val map = HashMap<String, Any>()
            list[position].id?.let { it1 -> map.put("id", it1.toInt()) }
            map.put("image", list[position].image.toString())
            map.put("price", list[position].price.toString())
            map.put("currency", list[position].currency.toString())
            map.put("title", list[position].title.toString())
            map.put("description", list[position].description.toString())

            addCart(context, map, holder, ADD_BTN)
        }


        holder.plusbtn.setOnClickListener {
            val map = HashMap<String, Any>()
            map.put("id", list[position].id!!)
            map.put("image", list[position].image.toString())
            map.put("price", list[position].price.toString())
            map.put("currency", list[position].currency.toString())
            map.put("description", list[position].description.toString())
            map.put("title", list[position].title.toString())
            addCart(context, map, holder, PLUS_BTN)
        }

        holder.minusbtn.setOnClickListener {
            removeCartMinus(context, list[position].id!!, holder)
        }

    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage = view.findViewById<ImageView>(R.id.product_img)
        var productName = view.findViewById<TextView>(R.id.product_name)
        var productDesc = view.findViewById<TextView>(R.id.product_desc)
        var productPrice = view.findViewById<TextView>(R.id.totalAmout)
        var cardview = view.findViewById<MaterialCardView>(R.id.cardView_category)
        var addButton = view.findViewById<ImageButton>(R.id.add_btn)
        var count_container = view.findViewById<LinearLayout>(R.id.count_container)
        var price_container = view.findViewById<LinearLayout>(R.id.price_cl)
        var plusbtn = view.findViewById<ImageButton>(R.id.plusbtn)
        var minusbtn = view.findViewById<ImageButton>(R.id.minusbtn)
        var quantity = view.findViewById<TextView>(R.id.quantity)
        var filter_btn=view.findViewById<ImageButton>(R.id.filter_icon)

    }

    fun addCart(context: Context, map: HashMap<String, Any>, holder: MyViewholder, from: String) {
        RetrofitBuilder.build().create(ApiInterface::class.java).addCart(map)
            .enqueue(object : Callback<Response> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        if (from.equals(ADD_BTN)) {
                            holder.count_container.visibility = View.VISIBLE
                            holder.addButton.visibility = View.GONE
                            val params = holder.price_container.layoutParams as MarginLayoutParams
                            params.bottomMargin += 40
                            holder.price_container.layoutParams = params
                        } else {
                            holder.quantity.text =
                                (holder.quantity.text.toString().toInt() + 1).toString()
                        }
                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun removeCartMinus(context: Context, id: Int, holder: MyViewholder) {
        RetrofitBuilder.build().create(ApiInterface::class.java).removeCartMinus(id)
            .enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>, response: retrofit2.Response<Response>
                ) {
                    if (response.body()?.status == 200) {
                        if(holder.quantity.text.toString().toInt() >0){
                        holder.quantity.text= (holder.quantity.text.toString().toInt() - 1).toString()
                        }
                        else{
                            holder.addButton.visibility=View.VISIBLE
                            holder.count_container.visibility=View.GONE
                            val params = holder.price_container.layoutParams as MarginLayoutParams
                            params.bottomMargin -= 40
                            holder.price_container.layoutParams = params
                        }

                    } else {
                        Toast.makeText(context, response.body()?.error?.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
                }
            })
    }
}