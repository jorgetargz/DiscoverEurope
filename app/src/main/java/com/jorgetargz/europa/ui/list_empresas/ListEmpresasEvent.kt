package com.jorgetargz.europa.ui.list_empresas

import com.jorgetargz.europa.domain.modelo.Empresa

sealed class ListEmpresasEvent {
    class FiltrarEmpresas(val nombre: String) : ListEmpresasEvent()
    class DeleteEmpresa(val empresa: Empresa) : ListEmpresasEvent()
    class DeleteEmpresaConRutas(val empresa: Empresa) : ListEmpresasEvent()
    class UndoDeleteEmpresa(val empresa: Empresa) : ListEmpresasEvent()
    object LoadEmpresas : ListEmpresasEvent()
    object ClearState : ListEmpresasEvent()
}