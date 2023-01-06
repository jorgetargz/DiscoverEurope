package com.jorgetargz.europa.ui.list_ciudades

import com.jorgetargz.europa.domain.modelo.Ciudad

sealed class ListCiudadesEvent {
    class FiltrarCiudades(val nombre: String) : ListCiudadesEvent()
    class LoadCiudades(val pais: String) : ListCiudadesEvent()
    class DeleteCiudad(val ciudad: Ciudad) : ListCiudadesEvent()
    class DeleteCiudadConRutas(val ciudad: Ciudad) : ListCiudadesEvent()
    class UndoDeleteCiudad(val ciudad: Ciudad) : ListCiudadesEvent()
    object ClearState : ListCiudadesEvent()
}