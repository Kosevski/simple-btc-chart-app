package com.example.mk.wsahomeproject.networking

import com.example.mk.wsahomeproject.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    val retrofitService: BlockChainApiInterface by lazy {
        retrofit().create(BlockChainApiInterface::class.java)
    }

}