package com.jorgetargz.europa.data.room

import androidx.room.*
import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.PaisConCiudades
import com.jorgetargz.europa.data.room.utils.SQLQueries

@Dao
interface PaisesDao {

    @Query(SQLQueries.SELECT_ALL_PAISES)
    suspend fun getAll(): List<PaisEntity>

    @Query(SQLQueries.SELECT_PAISES_FAVORITOS)
    suspend fun getFavoritos(): List<PaisEntity>

    @Query(SQLQueries.SELECT_PAIS_BY_NOMBRE)
    suspend fun getByName(nombre: String): PaisEntity

    @Transaction
    @Query(SQLQueries.SELECT_PAIS_BY_NOMBRE)
    suspend fun getByNameWithCities(nombre: String): PaisConCiudades

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pais: PaisEntity)

}