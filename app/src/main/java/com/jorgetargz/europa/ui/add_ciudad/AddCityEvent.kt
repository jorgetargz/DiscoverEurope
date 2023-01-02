package com.jorgetargz.europa.ui.add_ciudad

import com.jorgetargz.europa.domain.modelo.Ciudad

sealed class AddCityEvent {
    class AddCiudad(val ciudad: Ciudad): AddCityEvent()
    class DeleteCiudad(val ciudad: Ciudad) : AddCityEvent()
    object ClearState : AddCityEvent()
}