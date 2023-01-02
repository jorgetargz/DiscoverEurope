package com.jorgetargz.europa.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jorgetargz.europa.data.room.modelo.BusinessEntity

@Dao
interface BusinessDao {

    @Query("SELECT * FROM businesses")
    suspend fun getAll(): List<BusinessEntity>

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): BusinessEntity

    @Insert
    suspend fun insert(business: BusinessEntity) : Long

    @Delete
    suspend fun delete(business: BusinessEntity)
}