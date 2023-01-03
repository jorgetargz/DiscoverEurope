package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.RutaConCiudadesYEmpresa

@Dao
interface RutasDao {

    @Query("SELECT * FROM rutas")
    fun getAllWithRelations(): List<RutaConCiudadesYEmpresa>

    @Query("SELECT * FROM rutas WHERE id = :id")
    fun findById(id: Int): RutaConCiudadesYEmpresa

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ruta: RutaEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(ruta: RutaEntity)

    @Delete
    fun delete(ruta: RutaEntity)

}