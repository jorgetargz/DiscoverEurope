package com.jorgetargz.europa.ui.list_business

import com.jorgetargz.europa.domain.modelo.Business

data class ListBusinessState(
    val lista: List<Business>?,
    val listaFiltrada: List<Business>?,
    val empresaEliminada: Business?,
)