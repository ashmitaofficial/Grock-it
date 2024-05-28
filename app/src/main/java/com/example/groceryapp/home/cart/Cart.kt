package com.example.groceryapp.home.cart

import com.example.groceryapp.product.Product

data class Cart (
    val total:Double,
    val cgst: Double,
    val sgst: Double,
    val productList: ArrayList<Product>
)