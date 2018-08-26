package com.praveen.xoom.data

import com.google.gson.annotations.SerializedName

data class CountryItem(

	@field:SerializedName("features")
	val features: List<String?>? = null,

	@field:SerializedName("disbursement_options")
	val disbursementOptions: List<DisbursementOptionsItem?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("links")
	val links: List<LinksItem?>? = null,

	@field:SerializedName("residence")
	val residence: Boolean? = null,

	@field:SerializedName("suggested_source_currency")
	val suggestedSourceCurrency: SuggestedSourceCurrency? = null
)