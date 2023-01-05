package com.jorgetargz.europa.ui.edit_empresa

import com.jorgetargz.europa.domain.modelo.Empresa

sealed class EditEmpresaEvent {
    class LoadEmpresa(val id: Int) : EditEmpresaEvent()
    class EditEmpresa(val empresa: Empresa) : EditEmpresaEvent()
    object UndoEditEmpresa : EditEmpresaEvent()
    object ClearState : EditEmpresaEvent()
}