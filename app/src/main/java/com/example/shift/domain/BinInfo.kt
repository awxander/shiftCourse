package com.example.shift.domain

import android.os.Parcelable
import com.example.shift.data.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class BinInfo(
    var binNum : Long,
    var scheme: String? = null,
    var type: String? = null,
    var brand: String? = null,
    var prepaid: Boolean? = null,
    var cardNumber: CardNumberModel? = null,
    var bankModel: BankModel? = null,
    var countryModel: CountryModel? = null,
) : Parcelable
{
    constructor(
        binNum: Long,
        binInfoModel: BinInfoModel
    ) : this(
        binNum,
        binInfoModel.scheme,
        binInfoModel.type,
        binInfoModel.brand,
        binInfoModel.prepaid,
        binInfoModel.cardNumber,
        binInfoModel.bankModel,
        binInfoModel.countryModel,
    )
}