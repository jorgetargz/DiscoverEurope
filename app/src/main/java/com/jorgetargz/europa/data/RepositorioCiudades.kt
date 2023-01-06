package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.CiudadesDao
import com.jorgetargz.europa.data.room.utils.toCiudad
import com.jorgetargz.europa.data.room.utils.toCiudadEntity
import com.jorgetargz.europa.domain.modelo.Ciudad
import javax.inject.Inject

class RepositorioCiudades @Inject constructor(
    private val ciudadesDao: CiudadesDao
) {
    suspend fun getAll(): List<Ciudad> = ciudadesDao.getAll().map { it.toCiudad() }
    suspend fun insert(ciudad: Ciudad) = ciudadesDao.insert(ciudad.toCiudadEntity())
    suspend fun update(ciudad: Ciudad) = ciudadesDao.update(ciudad.toCiudadEntity())
    suspend fun delete(ciudad: Ciudad) = ciudadesDao.delete(ciudad.toCiudadEntity())
}