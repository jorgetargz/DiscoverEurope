package com.jorgetargz.europa.ui.add_ruta

import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.modelo.Ruta

data class AddRutaState(
    val mensaje: String? = null,
    val rutaAdded: Ruta? = null,
    val listaCiudades: List<Ciudad>? = null,
    val listaEmpresas: List<Empresa>? = null,
)
