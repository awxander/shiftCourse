package com.example.shifttest.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankModel(
    @SerializedName("name"  ) var name  : String? = null,
    @SerializedName("url"   ) var url   : String? = null,
    @SerializedName("phone" ) var phone : String? = null,
    @SerializedName("city"  ) var city  : String? = null
) : Parcelable