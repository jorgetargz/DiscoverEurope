package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.CiudadConRutas

@Dao
interface CiudadesDao {

    @Query("SELECT * FROM ciudades")
    suspend fun getAll(): List<CiudadEntity>

    @Transaction
    @Query("SELECT * FROM ciudades WHERE id = :id")
    suspend fun getCiudadConRutas(id : Int): CiudadConRutas

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ciudad: CiudadEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(ciudad: CiudadEntity)

    @Delete
    suspend fun delete(ciudad: CiudadEntity)

    @Delete
    suspend fun deleteCiudadYRutas(rutas: List<RutaEntity>, ciudad: CiudadEntity)

}