package com.jorgetargz.europa.ui.list_rutas

import com.jorgetargz.europa.domain.modelo.Ruta

data class ListRutasState(
    val lista: List<Ruta>?,
    val listaFiltrada: List<Ruta>?,
    val rutaEliminada: Ruta?,
)