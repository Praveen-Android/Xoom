package com.praveen.xoom.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelperImpl @Inject constructor(context: Context): PreferenceHelper {

    companion object {
        const val APPLICATION_PREFERENCES_KEY = "xoom_prefs"
        const val PREF_KEY_FAVORITES = "PREF_KEY_FAVORITES"
    }

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(APPLICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    override fun saveFavorite(countryCode: String) {
        val favorites = mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
        favorites.add(countryCode)
        mPrefs.edit().putStringSet(PREF_KEY_FAVORITES, favorites).apply()
    }

    override fun fetchFavorites(): Set<String> {
        return mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
    }

    override fun deleteFavorite(countryCode: String) {
        val favorites = mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
        favorites.remove(countryCode)
        mPrefs.edit().putStringSet(PREF_KEY_FAVORITES, favorites).apply()
    }
}