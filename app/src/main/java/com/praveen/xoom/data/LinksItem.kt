package com.praveen.xoom.data

import com.google.gson.annotations.SerializedName

data class LinksItem(

	@field:SerializedName("rel")
	val rel: String? = null,

	@field:SerializedName("href")
	val href: String? = null
)