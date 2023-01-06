package com.jorgetargz.europa.ui.edit_ruta

import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.modelo.Ruta

data class EditRutaState(
    val mensaje: String? = null,
    val rutaMostrar: Ruta? = null,
    val rutaEditada: Boolean = false,
    val listaCiudades: List<Ciudad>? = null,
    val listaEmpresas: List<Empresa>? = null,
)
