package com.example.groceryapp.dao

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @POST("signup")
    fun signup(@Body map: HashMap<String, String>?): Call<Response>

    @GET("login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<Response>

    @GET("otp")
    fun getOtp(@Query("mobile") mobile: String): Call<Response>

    @GET("login/google")
    fun loginGoogle(
        @Query("email") email: String,
        @Query("username") username: String
    ): Call<Response>

    @GET("login/mobile")
    fun loginMobile(
        @Query("mobile") mobile: String,
        @Query("countryCode") countryCode: String
    ): Call<Response>

    @GET("home")
    fun getHomeData(
        @Query("email") email: String
    ): Call<Response>

    @POST("forgot")
    fun forgot(@Body map: HashMap<String, String>?): Call<Response>

    @GET("category")
    fun getExploreData(): Call<Response>

    @GET("products")
    fun getProducts(): Call<Response>

    @GET("product/{id}")
    fun getProductById(@Path("id") id: Int): Call<Response>

    @GET("category/{id}")
    fun getProductByCategory(@Path("id") id: Int): Call<Response>
}