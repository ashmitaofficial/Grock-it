package com.example.groceryapp.dao

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    public fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.6:8080/grockit/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}