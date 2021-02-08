package com.example.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.ui.data.NasaRepository
import com.example.nasaapi.ui.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// La lógica debe estar siempre en el ViewModel ****

class ListViewModel: ViewModel() {
    // Create a LiveData with a String
    private val _response: MutableLiveData<List<Item>> = MutableLiveData()
    val response: LiveData<List<Item>> = _response


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = NasaRepository().requestNasaPictures("sun")
            _response.postValue(items)
        }
    }
}