package com.jorgetargz.europa.domain.usecases.ciudades

import com.jorgetargz.europa.data.RepositorioCiudades
import com.jorgetargz.europa.domain.modelo.Ciudad
import javax.inject.Inject

class DeleteCiudadUseCase @Inject constructor(
    private val repository: RepositorioCiudades
) {
    suspend operator fun invoke(ciudad: Ciudad) = repository.delete(ciudad)
}