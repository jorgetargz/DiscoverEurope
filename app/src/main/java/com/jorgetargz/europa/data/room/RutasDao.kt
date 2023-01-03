package com.jorgetargz.europa.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.RutaConCiudadesYEmpresa

@Dao
interface RutasDao {

    @Query("SELECT * FROM rutas")
    fun getAllWithRelations(): List<RutaConCiudadesYEmpresa>

    @Query("SELECT * FROM rutas WHERE id = :id")
    fun findById(id: Int): RutaConCiudadesYEmpresa

    @Insert
    fun insert(ruta: RutaEntity)

    @Update
    fun update(ruta: RutaEntity)

    @Delete
    fun delete(ruta: RutaEntity)

}