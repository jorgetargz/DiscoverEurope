package com.jorgetargz.europa.ui.list_empresas

import com.jorgetargz.europa.domain.modelo.Empresa

interface ListEmpresasActions {

    fun onEmpresaClicked(id: Int)
    fun onEmpresaSwipedLeft(empresa: Empresa)
}