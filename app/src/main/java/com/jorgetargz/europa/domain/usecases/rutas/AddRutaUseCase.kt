package com.jorgetargz.europa.domain.usecases.rutas

import com.jorgetargz.europa.data.RepositorioRutas
import com.jorgetargz.europa.domain.modelo.Ruta
import javax.inject.Inject

class AddRutaUseCase @Inject constructor(
    private val repository: RepositorioRutas
) {
    suspend operator fun invoke(ruta: Ruta) = repository.insert(ruta)
}