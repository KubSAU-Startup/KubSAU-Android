package com.example.diploma.common

import android.content.Context
import kotlin.properties.Delegates

object NetworkConfig {
    private var context: Context by Delegates.notNull()

    private const val PREF_NAME = "network_config"
    private const val AUTH_TOKEN = "token"

    fun attachContext(context: Context) {
        this.context = context
    }

    private val data by lazy { context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE) }

    fun logout() = data.edit().clear().commit()

    val isEmpty get() = data.getString(AUTH_TOKEN, EMPTY_TOKEN) == EMPTY_TOKEN

    var token
        set(value) = data.edit().putString(AUTH_TOKEN, value).apply()
        get() = data.getString(AUTH_TOKEN, EMPTY_TOKEN).toString()
}