package com.example.mk.wsahomeproject.model

import com.anychart.chart.common.dataentry.ValueDataEntry

class CustomDataEntry(
    x: String?,
    value: Number?
) : ValueDataEntry(x, value) {

    init {
        setValue("value", value)
    }
}