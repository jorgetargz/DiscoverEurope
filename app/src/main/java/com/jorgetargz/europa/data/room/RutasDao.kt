package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.RutaConCiudadesYEmpresa
import com.jorgetargz.europa.data.room.utils.SQLQueries

@Dao
interface RutasDao {

    @Transaction
    @Query(SQLQueries.SELECT_ALL_RUTAS)
    suspend fun getAllWithRelations(): List<RutaConCiudadesYEmpresa>

    @Transaction
    @Query(SQLQueries.SELECT_RUTA_BY_ID)
    suspend fun findById(id: Int): RutaConCiudadesYEmpresa

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ruta: RutaEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(ruta: RutaEntity)

    @Delete
    suspend fun delete(ruta: RutaEntity)

}