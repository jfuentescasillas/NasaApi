package com.example.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// La lógica debe estar siempre en el ViewModel ****

class ListViewModel: ViewModel() {
    // Create a LiveData with a String
    private val _response: MutableLiveData<String> = MutableLiveData()
    val response: LiveData<String> = _response


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue("Hola")
        }
    }
}