package com.jorgetargz.europa.ui.view_pais

import com.jorgetargz.europa.domain.modelo.Pais

data class ViewPaisState(
    val pais: Pais? = null,
    val onMarkedAsFavorito: Boolean = false,
    val onUnmarkedAsFavorito: Boolean = false
)
