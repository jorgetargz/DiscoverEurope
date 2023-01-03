package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "rutas",
    foreignKeys = [ForeignKey(
        entity = CiudadEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_ciudad_inicio"],
    ),ForeignKey(
        entity = CiudadEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_ciudad_fin"],
    ),ForeignKey(
        entity = EmpresaEntity::class,
        parentColumns = ["id"],
        childColumns = ["id_empresa"],
    )]
)
data class RutaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "id_ciudad_inicio")
    val idCiudadInicio: Int,

    @ColumnInfo(name = "id_ciudad_fin")
    val idCiudadFin: Int,

    @ColumnInfo(name = "id_empresa")
    val idEmpresa: Int,

    @ColumnInfo(name = "precio")
    val precio: Double,

    @ColumnInfo(name = "duracion")
    val duracion: Int,

    @ColumnInfo(name = "comentario")
    val comentario: String,
)
