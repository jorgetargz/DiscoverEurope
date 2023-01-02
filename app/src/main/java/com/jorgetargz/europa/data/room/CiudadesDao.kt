package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.CiudadEntity

@Dao
interface CiudadesDao {

    @Query("SELECT * FROM ciudades WHERE nombre = :name LIMIT 1")
    suspend fun getByName(name: String): CiudadEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ciudad: CiudadEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(ciudad: CiudadEntity)
    @Delete
    suspend fun delete(ciudad: CiudadEntity)

}