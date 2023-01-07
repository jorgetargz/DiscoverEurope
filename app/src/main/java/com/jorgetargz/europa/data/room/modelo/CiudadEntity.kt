package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jorgetargz.europa.data.room.common.Constantes

@Entity(
    tableName = Constantes.TABLE_NAME_CIUDADES,
    foreignKeys = [ForeignKey(
        entity = PaisEntity::class,
        parentColumns = [Constantes.COLUM_NAME_NOMBRE],
        childColumns = [Constantes.COLUM_NAME_PAIS]
    )])
data class CiudadEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_NOMBRE)
    val nombre: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_PAIS)
    val pais: String,
)