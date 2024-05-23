package com.example.groceryapp.product

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.home.productList.ProductListViewModel
import com.squareup.picasso.Picasso

class ProductdetailActivity : AppCompatActivity() {

    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var image:ImageView
    private lateinit var fav_btn:ImageButton
    private lateinit var product_label:TextView
    private lateinit var product_desc:TextView
    private lateinit var price:TextView
    private lateinit var details:TextView
    private lateinit var nutrition_details:TextView
    private lateinit var rating_txt:TextView

//    private lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productdetail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id:Int= intent.getIntExtra("id",-1)

        product_label= findViewById(R.id.product_label)
        image= findViewById(R.id.product_img)
        product_desc= findViewById(R.id.product_desc)
        price= findViewById(R.id.price)
        details= findViewById(R.id.details)
        nutrition_details= findViewById(R.id.nutrition_details)
        rating_txt= findViewById(R.id.rating_txt)


        viewModel.getProductById(this,id)
        viewModel.productDetailLiveData.observe(this){
            product_label.text= it.title
            product_desc.text = it.description
            price.text= "\u20B9" + it.price
            details.text= it.detail
            nutrition_details.text= it.nutrition
            rating_txt.text= it.review.toString()
            Picasso.get().load(it.image).into(image)
        }

    }
}