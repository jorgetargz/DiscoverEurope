package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.EmpresaConRutas

@Dao
interface EmpresasDao {

    @Query("SELECT * FROM businesses")
    suspend fun getAll(): List<EmpresaEntity>

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): EmpresaEntity

    @Query("SELECT * FROM businesses WHERE id = :id LIMIT 1")
    suspend fun getEmpresaConRutasById(id: Int): EmpresaConRutas

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(business: EmpresaEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(business: EmpresaEntity)

    @Delete
    suspend fun delete(business: EmpresaEntity)

    @Delete
    suspend fun deleteEmpresaYRutas(rutas: List<RutaEntity>, empresa: EmpresaEntity)
}