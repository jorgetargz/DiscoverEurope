package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgetargz.europa.data.room.common.Constantes

@Entity(tableName = Constantes.TABLE_NAME_PAISES)
data class PaisEntity(

    @PrimaryKey(autoGenerate = false)
    val nombre: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_NOMBRE_LOCAL)
    var nombreLocal: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_CAPITAL)
    val capital: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_URL_BANDERA)
    val urlBandera: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_IDIOMAS)
    val idiomas: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_FAVORITO)
    val favorito: Boolean,
)