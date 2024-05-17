package com.example.groceryapp.utils

import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

object Validation {
    fun String.isValidEmail(): Boolean {
        val userEmail: String = this.trim()
        var pattern: Pattern
        var matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(userEmail);

        return matcher.matches()
    }

    fun String.isValidNumber(): Boolean {
        val mobileNum = this.trim()
        if (mobileNum.length < 13) {
            return false
        }
        return true
    }
}