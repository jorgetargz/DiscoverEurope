package com.jorgetargz.europa.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity

@Dao
interface EmpresasDao {

    @Query("SELECT * FROM businesses")
    suspend fun getAll(): List<EmpresaEntity>

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EmpresaEntity

    @Insert
    suspend fun insert(business: EmpresaEntity) : Long

    @Delete
    suspend fun delete(business: EmpresaEntity)
}