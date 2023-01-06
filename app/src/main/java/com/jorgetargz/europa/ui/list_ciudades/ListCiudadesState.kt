package com.jorgetargz.europa.ui.list_ciudades

import com.jorgetargz.europa.domain.modelo.Ciudad

data class ListCiudadesState(
    val lista: List<Ciudad>?,
    val listaFiltrada: List<Ciudad>?,
    val ciudadEliminada: Ciudad?,
    val onDeleteCiudadConRutas: Ciudad?,
)