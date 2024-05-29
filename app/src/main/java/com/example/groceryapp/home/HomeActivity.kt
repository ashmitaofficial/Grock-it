package com.example.groceryapp.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.groceryapp.R
import com.example.groceryapp.home.cart.CartFragment
import com.example.groceryapp.home.explore.ExploreFragment
import com.example.groceryapp.home.favorite.FavoriteFragment
import com.example.groceryapp.home.shop.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

     lateinit var bottomNavigationView: BottomNavigationView
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_nav)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (p0.itemId == R.id.shop_txt) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, ShopFragment::class.java, null)
                        .commit()
                    return true
                }
               else if (p0.itemId == R.id.explore_txt) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, ExploreFragment::class.java, null)
                        .commit()
                    return true
                }
                else if(p0.itemId == R.id.favorite_txt){
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, FavoriteFragment::class.java, null)
                        .commit()
                    return true
                }
                else if(p0.itemId == R.id.cart_txt) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, CartFragment::class.java, null)
                        .commit()
                    return true
                }
                return false
            }
        })
        bottomNavigationView.selectedItemId = R.id.shop_txt

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        bottomNavigationView.selectedItemId = R.id.cart_txt

    }

//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed()
//            return
//        }
//
//        this.doubleBackToExitPressedOnce = true
//        Toast.makeText(this, "Are you sure you want to exit?", Toast.LENGTH_SHORT).show()
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
//    }



}