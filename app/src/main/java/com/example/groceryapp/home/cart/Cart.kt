package com.example.groceryapp.home.cart

import com.example.groceryapp.product.Product

data class Cart (
    val total:Double,
    val cgst: String,
    val sgst: String,
    val productList: ArrayList<Product>
)