package com.example.booktracker.utils

import android.content.Context

class SharedPreferenceHelper(private val context: Context) {

    companion object{
        private const val MY_PREF_KEY = "MY_PREF"
    }

    fun saveStringData(key: String, data: String) {
        val sharedPreferenceHelper = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferenceHelper.edit().putString(key, data).apply()
    }

    fun getStringData(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun clearPreferences() {
        val sharedPreferenceHelper = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferenceHelper.edit().clear().apply()
    }
}