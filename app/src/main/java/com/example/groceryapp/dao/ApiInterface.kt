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
    @GET("product")
    fun searchItem(
        @Query("search") search: String
    ): Call<Response>

    @GET("addWishlist/{wishId}")
    fun addWishlist(@Path("wishId")wishId:Int): Call<Response>

    @GET("removeWishlist/{id}")
    fun removeWishlist(@Path("id")id:Int): Call<Response>

    @GET("getWishlist")
    fun getWishList(): Call<Response>

    @GET("cart")
    fun getCart():Call<Response>
    @POST("cart")
    fun addCart(@Body map: HashMap<String, Any>?): Call<Response>

    @GET("cart/remove/{id}")
    fun removeCartItem(@Path("id")id:Int): Call<Response>

    @GET("cart/removeItem/{id}")
    fun removeCartMinus(@Path("id")id:Int): Call<Response>

    @GET("category/search")
    fun searchCategory(
        @Query("txt") txt: String
    ): Call<Response>

    @GET("filters")
    fun getFilterItems(): Call<Response>

    @GET("available-methods")
    fun getPaymentMethod(): Call<Response>

}