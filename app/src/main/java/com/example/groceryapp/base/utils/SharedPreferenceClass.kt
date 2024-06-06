package com.example.groceryapp.base.utils

import android.content.Context

object SharedPreferenceClass {

    fun savedLogin(context: Context, email: String) {
        val sharedPreferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("email", email) 
        editor.apply()
    }

    //Check whether the user is logged in or not
    fun isLoggedIn(context: Context): Boolean {
        val preferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        return preferences.getBoolean("isLoggedIn", false)
    }

    fun getEmail(context: Context): String? {
        val preferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        return preferences.getString("email", "")
    }
}