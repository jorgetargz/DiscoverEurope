package com.jorgetargz.europa.domain.usecases.rutas

import com.jorgetargz.europa.data.RepositorioRutas
import javax.inject.Inject

class LoadRutaByIdUseCase @Inject constructor(
    private val repository: RepositorioRutas
) {
    suspend operator fun invoke(id: Int) = repository.getRutaById(id)
}