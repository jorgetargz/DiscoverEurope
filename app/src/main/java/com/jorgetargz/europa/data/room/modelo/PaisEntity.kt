package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paises")
data class PaisEntity(

    @PrimaryKey(autoGenerate = false)
    val nombre: String,

    @ColumnInfo(name = "nombre_local")
    var nombreLocal: String,

    @ColumnInfo(name = "capital")
    val capital: String,

    @ColumnInfo(name = "url_bandera")
    val urlBandera: String,

    @ColumnInfo(name = "idiomas")
    val idiomas: String,

    @ColumnInfo(name = "favorito")
    val favorito: Boolean,
)