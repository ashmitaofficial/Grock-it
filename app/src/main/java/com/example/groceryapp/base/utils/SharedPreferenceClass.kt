package com.example.groceryapp.base.utils

import android.content.Context

object SharedPreferenceClass {

    public fun savedLogin(context: Context, email: String) {
        val sharedPreferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("email", email)
        editor.apply()
    }

    public fun isLoggedIn(context: Context): Boolean {
        val preferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        return preferences.getBoolean("isLoggedIn", false)
    }

    public fun getEmail(context: Context): String? {
        val preferences = context.getSharedPreferences("login_details", Context.MODE_PRIVATE)
        return preferences.getString("email", "")
    }


}