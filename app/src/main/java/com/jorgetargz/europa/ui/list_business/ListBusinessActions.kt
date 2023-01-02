package com.jorgetargz.europa.ui.list_business

import com.jorgetargz.europa.domain.modelo.Business

interface ListBusinessActions {

    fun onBusinessClicked(nombre: String)
    fun onBusinessSwipedLeft(empresa: Business)
}