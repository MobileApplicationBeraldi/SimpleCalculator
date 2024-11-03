package com.example.mycalculator.data.remote

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FactorDBClient {
    private const val BASE_URL = "http://factordb.com/api/"

    init {
        Log.d("FactorDBClient", "FactorDBClient initialized")
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        }

    val api: FactorDBApi by lazy {
        retrofit.create(FactorDBApi::class.java)
       }
}
