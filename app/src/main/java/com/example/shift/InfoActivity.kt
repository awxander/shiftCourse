package com.example.shifttest

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.shifttest.databinding.ActivityInfoBinding
import com.example.shifttest.domain.BinInfo

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val binInfo = intent.parcelable<BinInfo>(BIN_INFO)
        if (binInfo != null) {
            setInfo(binInfo)
        }
    }

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    private fun setInfo(binInfo: BinInfo) {
        binding.apply {
            tvScheme.text = binInfo.scheme ?: getString(R.string.not_mentioned)
            tvType.text = binInfo.type ?: getString(R.string.not_mentioned)
            tvBrand.text = binInfo.brand ?: getString(R.string.not_mentioned)
            tvPrepaid.text = getPrepaidText(binInfo)
            tvCountry.text = binInfo.countryModel?.name ?: getString(R.string.not_mentioned)
            setBankInfo(binInfo)
            setCardNumInfo(binInfo)
        }
    }


    private fun getPrepaidText(binInfo: BinInfo) =
        when(binInfo.prepaid){
            null -> getString(R.string.not_mentioned)
            true -> getString(R.string.yes)
            false -> getString(R.string.no)
        }

    private fun setBankInfo(binInfo: BinInfo){
        binding.apply {
            tvBankName.text = binInfo.bankModel?.name ?: getString(R.string.not_mentioned)
            tvBankSite.text = binInfo.bankModel?.url ?: getString(R.string.not_mentioned)
            tvBankPhoneNum.text = binInfo.bankModel?.phone ?: getString(R.string.not_mentioned)
        }
    }

    private fun setCardNumInfo(binInfo: BinInfo){
        binding.tvCardNumLen.text = if (binInfo.cardNumber?.length != null) {
            binInfo.cardNumber?.length.toString()
        }else{
            getString(R.string.not_mentioned)
        }
        binding.tvCardNumLuhn.text = when(binInfo.cardNumber?.luhn){
            null -> getString(R.string.not_mentioned)
            true -> getString(R.string.yes)
            false -> getString(R.string.no)
        }
    }


}