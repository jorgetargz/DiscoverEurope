package com.jorgetargz.europa.ui.view_pais

sealed class ViewPaisEvent {
    class LoadPais(val nombre: String): ViewPaisEvent()
    class OnFavClick(val nombre: String) : ViewPaisEvent() {

    }
}