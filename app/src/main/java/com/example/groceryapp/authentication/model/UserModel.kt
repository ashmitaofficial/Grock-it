package com.example.groceryapp.authentication.model

data class UserModel(
    val uid: Int?,
    val mobile: String?,
    val countryCode: String?,
    val email: String?,
    val username: String?,
    val password: String?,
    val address: String?
)