package com.jorgetargz.europa.domain.usecases.paises

import com.jorgetargz.europa.data.RepositorioPaises
import com.jorgetargz.europa.domain.modelo.Pais
import javax.inject.Inject

class ChangePaisFavoritoStateUseCase @Inject constructor(
    private val repository: RepositorioPaises
) {
    suspend operator fun invoke(pais: Pais) {
        val updatedPais = pais.copy(favorito = !pais.favorito)
        repository.update(updatedPais)
    }

}