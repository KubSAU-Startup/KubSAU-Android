package com.kubsau.regrab.common.storage

import android.content.SharedPreferences
import kotlin.properties.Delegates

object NetworkConfig {

    private const val AUTH_TOKEN = "token"
    private const val URL_KEY = "url"

    private var preferences: SharedPreferences by Delegates.notNull()

    fun init(preferences: SharedPreferences) {
        NetworkConfig.preferences = preferences
    }

    fun logout() {
        preferences.edit().remove(AUTH_TOKEN).apply()
    }

    fun clearUrl() {
        preferences.edit().remove(URL_KEY).apply()
    }

    val isTokenEmpty: Boolean
        get() = preferences.getString(AUTH_TOKEN, "").orEmpty().trim().isEmpty()

    var token
        set(value) = preferences.edit().putString(AUTH_TOKEN, value).apply()
        get() = preferences.getString(AUTH_TOKEN, "").toString()

    var url
        set(value) = preferences.edit().putString(URL_KEY, value).apply()
        get() = preferences.getString(URL_KEY, "").toString()
}
