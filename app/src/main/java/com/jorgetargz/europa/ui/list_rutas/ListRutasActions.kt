package com.jorgetargz.europa.ui.list_rutas

import com.jorgetargz.europa.domain.modelo.Ruta

interface ListRutasActions {

    fun onRutaClicked(ruta: Ruta)
    fun onRutaSwipedLeft(ruta: Ruta)
}