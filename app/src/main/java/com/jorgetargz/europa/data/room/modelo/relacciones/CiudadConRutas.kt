package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class CiudadConRutas(

    @Embedded
    val ciudad: CiudadEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_ciudad_inicio",
    )
    val rutasDeSalida: List<RutaEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_ciudad_fin",
    )
    val rutasDeLlegada: List<RutaEntity>,
)