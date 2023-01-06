package com.jorgetargz.europa.ui.edit_ruta

import com.jorgetargz.europa.domain.modelo.Ruta

sealed class EditRutaEvent {
    class LoadRuta(val id: Int) : EditRutaEvent()
    class EditRuta(val ruta: Ruta) : EditRutaEvent()
    object UndoEditRuta : EditRutaEvent()
    object LoadDropdownLists : EditRutaEvent()
    object ClearState : EditRutaEvent()
}