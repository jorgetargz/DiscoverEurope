package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.PaisEntity

data class PaisConCiudades(

    @Embedded
    val pais: PaisEntity,

    @Relation(
        parentColumn = "nombre",
        entityColumn = "pais"
    )
    val ciudades: List<CiudadEntity>,
)