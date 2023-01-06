package com.jorgetargz.europa.domain.usecases.ciudades

import com.jorgetargz.europa.data.RepositorioCiudades
import javax.inject.Inject

class LoadAllCiudadesUseCase @Inject constructor(
    private val repository: RepositorioCiudades
) {
    suspend operator fun invoke() = repository.getAll()
}