package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.RutasDao
import com.jorgetargz.europa.data.room.utils.toRuta
import com.jorgetargz.europa.data.room.utils.toRutaEntity
import com.jorgetargz.europa.domain.modelo.Ruta
import javax.inject.Inject

class RepositorioRutas @Inject constructor(
    private val rutasDao: RutasDao
) {
    suspend fun getRutas() = rutasDao.getAllWithRelations().map { it.toRuta() }
    suspend fun insert(ruta: Ruta) = rutasDao.insert(ruta.toRutaEntity())
    suspend fun update(ruta: Ruta) = rutasDao.update(ruta.toRutaEntity())
    suspend fun delete(ruta: Ruta) = rutasDao.delete(ruta.toRutaEntity())
}