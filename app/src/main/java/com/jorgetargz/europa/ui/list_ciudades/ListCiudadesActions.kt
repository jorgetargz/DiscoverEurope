package com.jorgetargz.europa.ui.list_ciudades

import com.jorgetargz.europa.domain.modelo.Ciudad

interface ListCiudadesActions {

    fun onCityClicked(nombre: String)
    fun onCitySwipedLeft(ciudad: Ciudad)
}