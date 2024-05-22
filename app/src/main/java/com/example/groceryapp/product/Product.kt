package com.example.groceryapp.product

data class Product(
    val id: Int?,
    val image: String?,
    val price: String?,
    val currency: String?,
    val description: String?,
    val title: String?,
    val detail: String?,
    val nutrition: String?,
    val review: Int?
)