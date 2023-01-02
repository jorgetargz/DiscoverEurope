package com.jorgetargz.europa.domain.usecases.empresas

import com.jorgetargz.europa.data.RepositorioEmpresas
import com.jorgetargz.europa.domain.modelo.Business
import javax.inject.Inject

class DeleteEmpresaUseCase @Inject constructor(
    private val repository: RepositorioEmpresas
) {
    suspend operator fun invoke(business: Business) = repository.delete(business)
}