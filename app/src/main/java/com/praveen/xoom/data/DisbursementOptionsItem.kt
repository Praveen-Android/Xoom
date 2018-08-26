package com.praveen.xoom.data

import com.google.gson.annotations.SerializedName

data class DisbursementOptionsItem(

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("country")
	val country: Country? = null,

	@field:SerializedName("currency")
	val currency: Currency? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("disbursement_type")
	val disbursementType: String? = null
)