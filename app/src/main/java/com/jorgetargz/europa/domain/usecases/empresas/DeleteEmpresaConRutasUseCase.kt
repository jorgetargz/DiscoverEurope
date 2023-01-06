package com.jorgetargz.europa.domain.usecases.empresas

import com.jorgetargz.europa.data.RepositorioEmpresas
import com.jorgetargz.europa.domain.modelo.Empresa
import javax.inject.Inject

class DeleteEmpresaConRutasUseCase @Inject constructor(
    private val repository: RepositorioEmpresas
) {
    suspend operator fun invoke(empresa: Empresa) = repository.deleteEmpresaConRutas(empresa)
}