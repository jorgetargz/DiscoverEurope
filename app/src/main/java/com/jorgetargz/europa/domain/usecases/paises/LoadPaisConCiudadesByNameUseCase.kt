package com.jorgetargz.europa.domain.usecases.paises

import com.jorgetargz.europa.data.RepositorioPaises
import com.jorgetargz.europa.domain.modelo.Pais
import javax.inject.Inject

class LoadPaisConCiudadesByNameUseCase @Inject constructor(
    private val repository: RepositorioPaises
) {
    suspend operator fun invoke(nombre: String): Pais =
        repository.getByNameWithCities(nombre)
}