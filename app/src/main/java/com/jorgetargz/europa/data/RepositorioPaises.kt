package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.PaisesDao
import com.jorgetargz.europa.data.room.utils.toPais
import com.jorgetargz.europa.data.room.utils.toPaisEntity
import com.jorgetargz.europa.domain.modelo.Pais
import javax.inject.Inject

class RepositorioPaises @Inject constructor(
    private val paisesDao: PaisesDao
) {
    suspend fun getAll(): List<Pais> = paisesDao.getAll().map { it.toPais() }
    suspend fun getFavoritos(): List<Pais> = paisesDao.getFavoritos().map { it.toPais() }
    suspend fun getByName(nombre: String): Pais = paisesDao.getByName(nombre).toPais()
    suspend fun getByNameWithCities(nombre: String): Pais =
        paisesDao.getByNameWithCities(nombre).toPais()

    suspend fun update(pais: Pais) = paisesDao.update(pais.toPaisEntity())
}