package com.example.mk.wsahomeproject.repo

import android.util.Log
import com.example.mk.wsahomeproject.model.BlockChainResponse
import com.example.mk.wsahomeproject.networking.RetrofitClient
import com.example.mk.wsahomeproject.util.Constants.FORMAT
import com.example.mk.wsahomeproject.util.Constants.ROLLING_AVERAGE
import com.example.mk.wsahomeproject.util.Constants.TIMESPAN
import com.example.mk.wsahomeproject.util.DataListener
import retrofit2.Call
import retrofit2.Response

object Repo {

    private val retrofit = RetrofitClient.retrofitService

    fun getData(listener: DataListener<BlockChainResponse>) {
        val call = retrofit.getBitcoinPriceHistory(TIMESPAN, ROLLING_AVERAGE, FORMAT)
        call.enqueue(object : retrofit2.Callback<BlockChainResponse> {
            override fun onResponse(
                call: Call<BlockChainResponse>,
                response: Response<BlockChainResponse>
            ) {
                if (response.isSuccessful) {
                    val blockChainResponse = response.body() as BlockChainResponse
                    listener.onSuccess(blockChainResponse)

                    Log.d("Andrej", "onResponse: status " + blockChainResponse.status)
                    Log.d(
                        "Andrej",
                        "onResponse: values - x: " + blockChainResponse.values?.get(0)?.x.toString() + " y: " + blockChainResponse.values?.get(
                            0
                        )?.y.toString()
                    )
                } else {
                    Log.d("Andrej", "onResponse: " + response.body())
                }
            }

            override fun onFailure(call: Call<BlockChainResponse>, t: Throwable) {
                listener.onError(t)
                Log.d("Andrej", "onResponse: failed ${t.localizedMessage}")
            }
        })
    }
}