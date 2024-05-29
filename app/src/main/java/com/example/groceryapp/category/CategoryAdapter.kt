package com.example.groceryapp.category

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.authentication.fragment.ForgotPasswordFragment
import com.example.groceryapp.base.utils.AppConstants
import com.example.groceryapp.dao.ApiInterface
import com.example.groceryapp.dao.Response
import com.example.groceryapp.dao.RetrofitBuilder
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.home.productList.ProductListViewModel
import com.example.groceryapp.product.Product
import com.example.groceryapp.product.ProductListingFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

class CategoryAdapter(private val list: ArrayList<Category>,val context: Context, val activity: FragmentActivity) :
    RecyclerView.Adapter<CategoryAdapter.MyViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
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
            activity.supportFragmentManager.beginTransaction()
                .add(R.id.container, ProductListingFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }
    }

    class MyViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryImage = view.findViewById<ImageView>(R.id.category_img)
        var category_name = view.findViewById<TextView>(R.id.category_name)
        var cardView = view.findViewById<CardView>(R.id.cardView_category)

    }
}