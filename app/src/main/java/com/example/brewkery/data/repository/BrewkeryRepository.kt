package com.example.brewkery.data.repository

import com.example.brewkery.data.model.MenuResponse
import com.example.brewkery.data.model.Product
import com.example.brewkery.data.remote.BrewkeryApi

class BrewkeryRepository(private val api: BrewkeryApi) {
    suspend fun getMenu(): MenuResponse = api.getMenu()
    suspend fun getItem(id: Int): Product = api.getItem(id)
}
