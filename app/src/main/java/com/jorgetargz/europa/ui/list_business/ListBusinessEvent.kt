package com.jorgetargz.europa.ui.list_business

import com.jorgetargz.europa.domain.modelo.Business

sealed class ListBusinessEvent {
    class FiltrarBusiness(val nombre: String) : ListBusinessEvent()
    class DeleteCiudad(val business: Business) : ListBusinessEvent()
    class UndoDeleteCiudad(val business: Business) : ListBusinessEvent()
    object LoadBusiness : ListBusinessEvent()
    object ClearState : ListBusinessEvent()
}