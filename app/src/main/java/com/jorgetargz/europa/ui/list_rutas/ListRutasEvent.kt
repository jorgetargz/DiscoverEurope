package com.jorgetargz.europa.ui.list_rutas

import com.jorgetargz.europa.domain.modelo.Ruta

sealed class ListRutasEvent {
    class FiltrarRutas(val nombre: String) : ListRutasEvent()
    class DeleteRuta(val ruta: Ruta) : ListRutasEvent()
    class UndoDeleteRuta(val ruta: Ruta) : ListRutasEvent()
    object LoadRutas : ListRutasEvent()
    object ClearState : ListRutasEvent()
}