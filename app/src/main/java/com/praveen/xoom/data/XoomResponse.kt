package com.praveen.xoom.data

import com.google.gson.annotations.SerializedName

data class XoomResponse(

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("total_items")
	val totalItems: Int? = null,

	@field:SerializedName("items")
	val items: List<CountryItem?>? = null
)