package com.example.mk.wsahomeproject.util

interface DataListener<T> {

    fun onSuccess(t: T)

    fun onError(t: Throwable)
}