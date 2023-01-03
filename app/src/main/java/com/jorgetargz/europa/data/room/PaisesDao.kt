package com.jorgetargz.europa.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.PaisConCiudades

@Dao
interface PaisesDao {

    @Query("select * from paises order by nombre")
    suspend fun getAll(): List<PaisEntity>

    @Query("select * from paises where favorito = 1 order by nombre")
    suspend fun getFavoritos(): List<PaisEntity>

    @Query("select * from paises where nombre = :nombre limit 1")
    suspend fun getByName(nombre: String): PaisEntity

    @Transaction
    @Query("select * from paises where nombre = :nombre limit 1")
    suspend fun getByNameWithCities(nombre: String): PaisConCiudades

    @Update
    suspend fun update(pais: PaisEntity)

}