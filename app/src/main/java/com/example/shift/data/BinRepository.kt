package com.example.shift.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BinRepository {

    companion object{
        const val BASE_URL = "https://lookup.binlist.net/"
        const val READ_TIMEOUT_SECONDS = 5L
        const val CONNECT_TIMEOUT_SECONDS = 5L
        const val WRITE_TIMEOUT_SECONDS = 5L
    }

    private val gson = GsonBuilder()
        .create()

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val binApi by lazy{
        retrofit.create(BinApi::class.java)
    }

    private fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    suspend fun getByNum(binNum : Long): BinInfoModel =
        binApi.getByNum(binNum)
}