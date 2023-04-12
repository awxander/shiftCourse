package com.example.shifttest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class CardNumberModel(
    @SerializedName("length" ) var length : Int?     = null,
    @SerializedName("luhn"   ) var luhn   : Boolean? = null
) : Parcelable
