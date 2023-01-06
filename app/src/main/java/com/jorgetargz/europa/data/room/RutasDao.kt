package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.RutaConCiudadesYEmpresa

@Dao
interface RutasDao {

    @Transaction
    @Query("SELECT * FROM rutas")
    suspend fun getAllWithRelations(): List<RutaConCiudadesYEmpresa>

    @Transaction
    @Query("SELECT * FROM rutas WHERE id = :id")
    suspend fun findById(id: Int): RutaConCiudadesYEmpresa

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ruta: RutaEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(ruta: RutaEntity)

    @Delete
    suspend fun delete(ruta: RutaEntity)

}