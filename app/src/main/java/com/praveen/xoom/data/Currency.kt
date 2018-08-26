package com.praveen.xoom.data

import com.google.gson.annotations.SerializedName

data class Currency(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null
)