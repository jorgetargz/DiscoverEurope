package com.jorgetargz.europa.ui.list_business

import com.jorgetargz.europa.domain.modelo.Empresa

data class ListEmpresasState(
    val lista: List<Empresa>?,
    val listaFiltrada: List<Empresa>?,
    val empresaEliminada: Empresa?,
)