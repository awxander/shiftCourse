package com.example.shifttest.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {

    @GET("{binNum}")
    suspend fun getByNum(@Path("binNum") binNum : Long): BinInfoModel

}