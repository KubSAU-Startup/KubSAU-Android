package com.kubsau.regrab.common.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.Delegates

object AccountConfig {

    private const val DEPARTMENT_ID_KEY = "department_id"
    private const val DEPARTMENT_NAME_KEY = "department_name"
    private const val FULL_NAME_KEY = "full_name"
    private const val FIRST_LAUNCH_KEY = "first_launch"
    private const val GREEN_THEME = "green_theme"

    private var preferences: SharedPreferences by Delegates.notNull()

    fun init(preferences: SharedPreferences) {
        this.preferences = preferences
    }

    fun logout() {
        preferences.edit {
            remove(DEPARTMENT_ID_KEY)
            remove(DEPARTMENT_NAME_KEY)
            remove(FULL_NAME_KEY)
        }
    }

    var departmentId: Int
        set(value) = preferences.edit().putInt(DEPARTMENT_ID_KEY, value).apply()
        get() = preferences.getInt(DEPARTMENT_ID_KEY, -1)

    var fullName: String
        set(value) = preferences.edit().putString(FULL_NAME_KEY, value).apply()
        get() = preferences.getString(FULL_NAME_KEY, "").orEmpty()

    var departmentName: String
        set(value) = preferences.edit().putString(DEPARTMENT_NAME_KEY, value).apply()
        get() = preferences.getString(DEPARTMENT_NAME_KEY, "").orEmpty()

    var isFirstLaunch: Boolean
        set(value) = preferences.edit { putBoolean(FIRST_LAUNCH_KEY, value) }
        get() = preferences.getBoolean(FIRST_LAUNCH_KEY, true)

    var greenThemeEnabled: Boolean
        set(value) = preferences.edit { putBoolean(GREEN_THEME, value) }
        get() = preferences.getBoolean(GREEN_THEME, true)
}
