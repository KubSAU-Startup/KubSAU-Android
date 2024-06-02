package com.example.diploma.common.storage

import android.content.Context
import com.example.diploma.common.BASE_API_URL
import com.example.diploma.common.EMPTY_TOKEN
import kotlin.properties.Delegates

object NetworkConfig {
    private var context: Context by Delegates.notNull()

    private const val PREF_NAME = "network_config"
    private const val AUTH_TOKEN = "token"
    private const val URL_KEY = "url"

    fun attachContext(context: Context) {
        NetworkConfig.context = context
    }

    private val data by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun logout() {
        data.edit().remove(AUTH_TOKEN).apply()
    }

    fun clearUrl() {
        data.edit().remove(URL_KEY).apply()
    }

    val isTokenEmpty: Boolean
        get() {
            return data.getString(AUTH_TOKEN, EMPTY_TOKEN) == EMPTY_TOKEN
        }

    var token
        set(value) = data.edit().putString(AUTH_TOKEN, value).apply()
        get() = data.getString(AUTH_TOKEN, EMPTY_TOKEN).toString()

    var url
        set(value) = data.edit().putString(URL_KEY, value).apply()
        get() = data.getString(URL_KEY, BASE_API_URL).toString()

    val isUrlEmpty get() = data.getString(URL_KEY, BASE_API_URL) == BASE_API_URL
}