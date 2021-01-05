package com.duglasher.conductorviewmodel_sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*


class SampleViewModel : ViewModel() {

    private val liveData = MutableLiveData<String>()

    init {
        liveData.value = Date().toString()
    }

    fun getLiveData(): LiveData<String> = liveData

}