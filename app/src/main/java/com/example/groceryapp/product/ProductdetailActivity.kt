package com.example.groceryapp.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.groceryapp.R
import com.example.groceryapp.home.HomeActivity
import com.example.groceryapp.home.productList.ProductListViewModel
import com.squareup.picasso.Picasso


class ProductdetailActivity : AppCompatActivity() {

    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var image: ImageView
    private lateinit var fav_btn: ImageButton
    private lateinit var product_label: TextView
    private lateinit var product_desc: TextView
    private lateinit var price: TextView
    private lateinit var details: TextView
    private lateinit var nutrition_details: TextView
    private lateinit var rating_txt: TextView
    private lateinit var fav_btn_selected: ImageButton
    private lateinit var minusbtn: ImageButton
    private lateinit var plusbtn: ImageButton
    private lateinit var quantity: TextView
    private lateinit var expand_btn: ImageButton
    private lateinit var less_btn: ImageButton
    private lateinit var nutrition_expand_btn: ImageButton
    private lateinit var nutrition_less_btn: ImageButton
    lateinit var progress_bar: ProgressBar
    lateinit var ScrollView: ScrollView
    lateinit var AddBasket_btn: AppCompatButton
    private var map = HashMap<String, Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productdetail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id: Int = intent.getIntExtra("id", -1)


        product_label = findViewById(R.id.product_label)
        image = findViewById(R.id.product_img)
        product_desc = findViewById(R.id.product_desc)
        price = findViewById(R.id.price)
        details = findViewById(R.id.details)
        nutrition_details = findViewById(R.id.nutrition_details)
        rating_txt = findViewById(R.id.rating_txt)
        fav_btn = findViewById(R.id.fav_btn)
        fav_btn_selected = findViewById(R.id.fav_btn_selected)
        minusbtn = findViewById(R.id.minusbtn)
        plusbtn = findViewById(R.id.plusbtn)
        quantity = findViewById(R.id.quantity)
        expand_btn = findViewById(R.id.expand_btn)
        less_btn = findViewById(R.id.less_btn)
        nutrition_expand_btn = findViewById(R.id.nutrition_expand_btn)
        nutrition_less_btn = findViewById(R.id.nutrition_less_btn)
        progress_bar = findViewById(R.id.progress_bar)
        ScrollView = findViewById(R.id.scrollView)
        AddBasket_btn = findViewById(R.id.checkOut_btn)

        progress_bar.visibility = View.VISIBLE
        ScrollView.visibility = View.GONE
        viewModel.getProductById(this, id)



        viewModel.productDetailLiveData.observe(this) {
            map.put("id", it.id.toString())
            map.put("image", it.image.toString())
            map.put("price", it.price.toString())
            map.put("currency", it.currency.toString())
            map.put("description", it.description.toString())
            map.put("title", it.title.toString())

            product_label.text = it.title
            product_desc.text = it.description
            price.text = "\u20B9" + it.price
            details.text = it.detail
            nutrition_details.text = it.nutrition
            rating_txt.text = it.review.toString()
            Picasso.get().load(it.image).into(image)

            if (it.favorite == false) {
                fav_btn.setVisibility(View.VISIBLE)
                fav_btn_selected.setVisibility(View.INVISIBLE)
            } else {
                fav_btn.setVisibility(View.INVISIBLE)
                fav_btn_selected.setVisibility(View.VISIBLE)
            }

        }


        viewModel.wishlistLivedata.observe(this) {
            if (it == true) {
                if (fav_btn.isVisible) {
                    fav_btn.visibility = View.INVISIBLE
                    fav_btn_selected.visibility = View.VISIBLE
                } else {
                    fav_btn.visibility = View.VISIBLE
                    fav_btn_selected.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.cartLivedata.observe(this) {
            if (it == true) {
                quantity.text = (quantity.text.toString().toInt() + 1).toString()
            }
        }

        viewModel.cartLivedataMinus.observe(this) {
            if (it == true) {
                quantity.text = (quantity.text.toString().toInt() - 1).toString()
            }
        }

        fav_btn.setOnClickListener {
            viewModel.addWishlist(this, id)
        }
        fav_btn_selected.setOnClickListener {
            viewModel.removeWishlist(this, id)
        }

        minusbtn.setOnClickListener {
            if (quantity.text.toString().toInt() > 0) {
                viewModel.removeCartMinus(this, id)
            }
        }


        plusbtn.setOnClickListener {
            viewModel.addCart(this, map)

        }

        expand_btn.setOnClickListener {
            less_btn.visibility = View.VISIBLE
            expand_btn.visibility = View.INVISIBLE
            details.visibility = View.VISIBLE
        }

        less_btn.setOnClickListener {
            expand_btn.visibility = View.VISIBLE
            less_btn.visibility = View.INVISIBLE
            details.visibility = View.GONE
        }

        nutrition_expand_btn.setOnClickListener {
            nutrition_less_btn.visibility = View.VISIBLE
            nutrition_expand_btn.visibility = View.INVISIBLE
            nutrition_details.visibility = View.VISIBLE
        }

        nutrition_less_btn.setOnClickListener {
            nutrition_expand_btn.visibility = View.VISIBLE
            nutrition_less_btn.visibility = View.INVISIBLE
            nutrition_details.visibility = View.GONE
        }

        viewModel.addBasketLiveData.observe(this) {
            val homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

        AddBasket_btn.setOnClickListener{
            progress_bar.visibility = View.VISIBLE
            ScrollView.visibility = View.GONE
            viewModel.addBasket(this, map,this)
        }


    }


}