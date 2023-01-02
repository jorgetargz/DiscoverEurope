package com.jorgetargz.europa.ui.add_empresa

import com.jorgetargz.europa.domain.modelo.Empresa

data class AddEmpresaState(
    val mensaje: String? = null,
    val empresaAdded: Empresa? = null,
)
