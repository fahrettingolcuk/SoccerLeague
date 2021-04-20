package com.requarter.soccer.utils

import android.content.Context
import androidx.preference.PreferenceManager

class LocalStorages(context: Context?) {
    companion object {
        private const val DARK_THEME = "DARK_THEME"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkTheme = preferences.getInt(DARK_THEME,0)
    set(value) = preferences.edit().putInt(DARK_THEME,value).apply()
}