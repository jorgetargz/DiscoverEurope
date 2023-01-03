package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity

@Dao
interface EmpresasDao {

    @Query("SELECT * FROM businesses")
    suspend fun getAll(): List<EmpresaEntity>

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EmpresaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(business: EmpresaEntity) : Long

    @Delete
    suspend fun delete(business: EmpresaEntity)
}