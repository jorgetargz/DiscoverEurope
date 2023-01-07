package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jorgetargz.europa.data.room.common.Constantes

@Entity(
    tableName = Constantes.TABLE_NAME_RUTAS,
    foreignKeys = [ForeignKey(
        entity = CiudadEntity::class,
        parentColumns = [Constantes.COLUM_NAME_ID],
        childColumns = [Constantes.COLUM_NAME_ID_CIUDAD_INICIO],
    ),ForeignKey(
        entity = CiudadEntity::class,
        parentColumns = [Constantes.COLUM_NAME_ID],
        childColumns = [Constantes.COLUM_NAME_ID_CIUDAD_FIN],
    ),ForeignKey(
        entity = EmpresaEntity::class,
        parentColumns = [Constantes.COLUM_NAME_ID],
        childColumns = [Constantes.COLUM_NAME_ID_EMPRESA],
    )]
)
data class RutaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_ID_CIUDAD_INICIO)
    val idCiudadInicio: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_ID_CIUDAD_FIN)
    val idCiudadFin: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_ID_EMPRESA)
    val idEmpresa: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_PRECIO)
    val precio: Double,

    @ColumnInfo(name = Constantes.COLUM_NAME_DURACION)
    val duracion: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_COMENTARIO)
    val comentario: String,
)
