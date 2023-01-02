package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.EmpresasDao
import com.jorgetargz.europa.data.room.utils.toEmpresa
import com.jorgetargz.europa.data.room.utils.toEmpresaEntity
import com.jorgetargz.europa.domain.modelo.Empresa
import javax.inject.Inject

class RepositorioEmpresas @Inject constructor(
    private val empresasDao: EmpresasDao
) {
    suspend fun getBusiness() = empresasDao.getAll().map { it.toEmpresa() }
    suspend fun getBusinessById(id: Int) = empresasDao.getById(id).toEmpresa()
    suspend fun insert(empresa: Empresa) = empresasDao.insert(empresa.toEmpresaEntity())
    suspend fun delete(empresa: Empresa) = empresasDao.delete(empresa.toEmpresaEntity())
}