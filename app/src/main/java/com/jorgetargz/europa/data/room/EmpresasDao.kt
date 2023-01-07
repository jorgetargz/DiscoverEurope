package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.EmpresaConRutas
import com.jorgetargz.europa.data.room.utils.SQLQueries

@Dao
interface EmpresasDao {

    @Query(SQLQueries.SELECT_ALL_EMPRESAS)
    suspend fun getAll(): List<EmpresaEntity>

    @Query(SQLQueries.SELECT_EMPRESA_BY_ID)
    suspend fun getById(id: Int): EmpresaEntity

    @Query(SQLQueries.SELECT_EMPRESA_BY_ID)
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