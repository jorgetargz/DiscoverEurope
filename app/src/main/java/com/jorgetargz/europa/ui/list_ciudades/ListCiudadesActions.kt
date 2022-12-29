package com.jorgetargz.europa.ui.list_ciudades

interface ListCiudadesActions {

    fun onCityClicked(nombre: String)
    fun onCitySwipedLeft(position: Int)
}