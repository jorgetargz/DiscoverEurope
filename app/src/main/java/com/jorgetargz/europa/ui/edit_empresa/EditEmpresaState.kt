package com.jorgetargz.europa.ui.edit_empresa

import com.jorgetargz.europa.domain.modelo.Empresa

data class EditEmpresaState(
    val mensaje: String? = null,
    val empresaMostrar: Empresa? = null,
    val empresaEditada: Boolean = false,
)
