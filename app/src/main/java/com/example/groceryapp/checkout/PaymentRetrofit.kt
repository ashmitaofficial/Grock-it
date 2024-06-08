package com.example.groceryapp.checkout

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PaymentRetrofit {
    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.razorpay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}