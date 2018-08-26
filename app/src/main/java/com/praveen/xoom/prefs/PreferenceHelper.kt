package com.praveen.xoom.prefs

interface PreferenceHelper {

    fun saveFavorite(countryCode: String)

    fun fetchFavorites(): Set<String>

    fun deleteFavorite(countryCode: String)

}