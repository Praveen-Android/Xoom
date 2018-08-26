package com.praveen.xoom.ui

import com.praveen.xoom.database.CountryDetails

interface OnFavoriteSelected {

    fun saveFavorite(details: CountryDetails)

    fun removeFavorite(details: CountryDetails)
}
