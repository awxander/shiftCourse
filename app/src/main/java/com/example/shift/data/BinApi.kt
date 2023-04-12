package com.example.shift.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {

    @GET("{binNum}")
    suspend fun getByNum(@Path("binNum") binNum : Long): BinInfoModel

}