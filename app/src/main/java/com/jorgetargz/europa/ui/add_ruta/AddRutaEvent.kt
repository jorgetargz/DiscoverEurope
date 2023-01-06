package com.jorgetargz.europa.ui.add_ruta

import com.jorgetargz.europa.domain.modelo.Ruta

sealed class AddRutaEvent {
    class AddRuta(val ruta: Ruta) : AddRutaEvent()
    class DeleteRuta(val ruta: Ruta) : AddRutaEvent()
    object LoadDropdownLists : AddRutaEvent()
    object ClearState : AddRutaEvent()
}