package com.example.cardingo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cardingo.data.entity.Words

class MainViewModel : ViewModel() {
    val shuffledData : MutableLiveData<List<Words>> = MutableLiveData()
}