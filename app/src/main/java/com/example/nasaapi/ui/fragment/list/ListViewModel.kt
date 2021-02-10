package com.example.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.ui.data.NasaRepository
import com.example.nasaapi.ui.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

// La lógica debe estar siempre en el ViewModel ****

class ListViewModel: ViewModel() {
    // Create a LiveData with a String
    private val response = MutableLiveData<List<Item>>()
    fun getResponse(): LiveData<List<Item>> = response

    private val error = MutableLiveData<String>()
    fun getError(): LiveData<String> = error

    private val loading = MutableLiveData<Boolean>()
    fun isLoading(): LiveData<Boolean> = loading


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loading.postValue(true)

                val items = NasaRepository().requestNasaPictures("sun")
                response.postValue(items)

                loading.postValue(false)
            } catch(e: Exception) {
                loading.postValue(false)

                when (e) {
                    is HttpException -> {
                        error.postValue("Fatal Error: " + e.code().toString())
                    }
                    is UnknownHostException -> {
                        error.postValue("No internet connection")
                    }
                }
            }
        }
    }
}