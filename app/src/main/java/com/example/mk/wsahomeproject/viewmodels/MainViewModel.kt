package com.example.mk.wsahomeproject.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mk.wsahomeproject.model.BlockChainResponse
import com.example.mk.wsahomeproject.repo.Repo
import com.example.mk.wsahomeproject.util.DataListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val networkRepo = Repo
    val responseData = MutableLiveData<BlockChainResponse>()
    val errorResponse = MutableLiveData<String>()

    fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            networkRepo.getData(object : DataListener<BlockChainResponse> {
                override fun onSuccess(t: BlockChainResponse) {
                    responseData.postValue(t)
                }
                override fun onError(t: Throwable) {
                    errorResponse.postValue(t.localizedMessage)
                }
            })
        }
    }
}