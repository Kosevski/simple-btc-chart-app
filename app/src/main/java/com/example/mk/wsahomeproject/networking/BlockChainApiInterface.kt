package com.example.mk.wsahomeproject.networking

import com.example.mk.wsahomeproject.model.BlockChainResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockChainApiInterface {

    @GET("charts/market-price")
    fun getBitcoinPriceHistory(@Query("timespan") timespan: String, @Query("rollingAverage") rollingAverage: String, @Query("format") format: String): Call<BlockChainResponse>

}