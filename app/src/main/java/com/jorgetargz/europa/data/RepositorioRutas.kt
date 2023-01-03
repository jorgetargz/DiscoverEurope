package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.RutasDao
import com.jorgetargz.europa.data.room.utils.toRuta
import javax.inject.Inject

class RepositorioRutas @Inject constructor(
    private val rutasDao: RutasDao
) {
    suspend fun getRutas() = rutasDao.getAllWithRelations().map { it.toRuta() }
}