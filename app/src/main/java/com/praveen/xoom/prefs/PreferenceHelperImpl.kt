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

    /**
     * From the docs, we cannot modify the result of getStringSet() directly and consistency wont be guaranteed if we do so,
     * so using a temp variable to store the favorites and re-inserting them to preferences with the changes we made (in this case add a favorite)
     */
    override fun saveFavorite(countryCode: String) {
        val favorites = mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
        val data = favorites
        data.add(countryCode)
        mPrefs.edit().remove(PREF_KEY_FAVORITES).apply()
        mPrefs.edit().putStringSet(PREF_KEY_FAVORITES, data).apply()
    }

    override fun fetchFavorites(): Set<String> {
        return mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
    }

    /**
     * From the docs, we cannot modify the result of getStringSet() directly and consistency wont be guaranteed if we do so,
     * so using a temp variable to store the favorites and re-inserting them to preferences with the changes we made (in this case remove a favorite)
     */
    override fun deleteFavorite(countryCode: String) {
        val favorites = mPrefs.getStringSet(PREF_KEY_FAVORITES, mutableSetOf())
        val data = favorites
        data.remove(countryCode)
        mPrefs.edit().remove(PREF_KEY_FAVORITES).apply()
        mPrefs.edit().putStringSet(PREF_KEY_FAVORITES, data).apply()
    }
}