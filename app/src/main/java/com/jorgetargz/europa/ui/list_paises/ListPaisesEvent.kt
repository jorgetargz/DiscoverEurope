package com.jorgetargz.europa.ui.list_paises


sealed class ListPaisesEvent {
    class FiltrarPaises(val nombre: String) : ListPaisesEvent()
    object LoadPaises: ListPaisesEvent()
}