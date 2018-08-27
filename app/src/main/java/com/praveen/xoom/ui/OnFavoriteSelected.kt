package com.praveen.xoom.ui

interface OnFavoriteSelected {

    fun saveFavorite(countryCode: String?)

    fun removeFavorite(countryCode: String?)
}
