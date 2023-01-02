package com.jorgetargz.europa.ui.list_business

import com.jorgetargz.europa.domain.modelo.Empresa

interface ListEmpresasActions {

    fun onEmpresaClicked(nombre: String)
    fun onEmpresaSwipedLeft(empresa: Empresa)
}