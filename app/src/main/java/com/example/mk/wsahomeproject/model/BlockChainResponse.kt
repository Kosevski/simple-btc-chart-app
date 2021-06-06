package com.example.mk.wsahomeproject.model

import com.google.gson.annotations.SerializedName

class BlockChainResponse {

    @SerializedName("status")
    var status: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("unit")
    var unit: String? = null
    @SerializedName("period")
    var period: String? = null

    @SerializedName("values")
    var values: ArrayList<XYValues>? = null

}