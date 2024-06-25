package com.example.diploma.common.storage

import android.content.SharedPreferences
import kotlin.properties.Delegates

object AccountConfig {

    private const val DEPARTMENT_KEY = "department"

    private var preferences: SharedPreferences by Delegates.notNull()

    fun init(preferences: SharedPreferences) {
        this.preferences = preferences
    }

    fun logout() = preferences.edit().clear().commit()

    var departmentList
        set(value) = preferences.edit().putString(DEPARTMENT_KEY, value).apply()
        get() = preferences.getString(DEPARTMENT_KEY, "")
}
