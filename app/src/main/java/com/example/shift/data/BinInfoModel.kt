package com.example.shift.data

import com.google.gson.annotations.SerializedName


data class BinInfoModel(
    @SerializedName("scheme") var scheme: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("brand") var brand: String? = null,
    @SerializedName("prepaid") var prepaid: Boolean? = null,
    @SerializedName("bank") var bankModel: BankModel? = null,
    @SerializedName("number") var cardNumber: CardNumberModel? = null,
    @SerializedName("country") var countryModel: CountryModel? = null,
)
