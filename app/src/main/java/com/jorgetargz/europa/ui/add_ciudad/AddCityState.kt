package com.jorgetargz.europa.ui.add_ciudad

import com.jorgetargz.europa.domain.modelo.Ciudad

data class AddCityState(
    val mensaje: String? = null,
    val ciudadAdded: Ciudad? = null,
)
