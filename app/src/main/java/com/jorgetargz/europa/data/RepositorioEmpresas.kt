package com.jorgetargz.europa.data

import com.jorgetargz.europa.data.room.BusinessDao
import com.jorgetargz.europa.data.room.utils.toBusiness
import com.jorgetargz.europa.data.room.utils.toBusinessEntity
import com.jorgetargz.europa.domain.modelo.Business
import javax.inject.Inject

class RepositorioEmpresas @Inject constructor(
    private val businessDao: BusinessDao
) {
    suspend fun getBusiness() = businessDao.getAll().map { it.toBusiness() }
    suspend fun getBusinessById(id: Int) = businessDao.getById(id).toBusiness()
    suspend fun insert(business: Business) = businessDao.insert(business.toBusinessEntity())
    suspend fun delete(business: Business) = businessDao.delete(business.toBusinessEntity())
}