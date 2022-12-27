package com.jorgetargz.europa.ui.list_ciudades

sealed class ListCiudadesEvent {
    class FiltrarCiudades(val nombre: String) : ListCiudadesEvent()
    class LoadCiudades(val pais: String) : ListCiudadesEvent()
}