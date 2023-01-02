package com.jorgetargz.europa.ui.add_empresa

import com.jorgetargz.europa.domain.modelo.Empresa

sealed class AddEmpresaEvent {
    class AddEmpresa(val empresa: Empresa) : AddEmpresaEvent()
    class DeleteEmpresa(val empresa: Empresa) : AddEmpresaEvent()
    object ClearState : AddEmpresaEvent()
}