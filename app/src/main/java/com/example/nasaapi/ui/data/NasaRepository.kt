package com.example.nasaapi.ui.data

import com.example.nasaapi.ui.data.model.Item
import com.example.nasaapi.ui.data.network.NasaNetwork


class NasaRepository {
    fun requestNasaPictures(pictureType: String): List<Item> {
        return NasaNetwork().requestNasaImages(pictureType).collection.items
    }
}