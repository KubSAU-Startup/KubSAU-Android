package com.example.diploma.common.storage

import android.content.Context
import com.example.diploma.common.EMPTY_DEPARTMENT
import kotlin.properties.Delegates

object AccountConfig {
    private var context: Context by Delegates.notNull()

    private const val PREF_NAME = "account_config"
    private const val DEPARTMENT_KEY = "department"

    fun attachContext(context: Context) {
        this.context = context
    }

    private val data by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun logout() = data.edit().clear().commit()

    var department
        set(value) = data.edit().putInt(DEPARTMENT_KEY, value).apply()
        get() = data.getInt(DEPARTMENT_KEY, EMPTY_DEPARTMENT)
}