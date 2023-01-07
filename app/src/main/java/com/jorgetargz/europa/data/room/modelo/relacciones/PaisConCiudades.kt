package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.common.Constantes
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.PaisEntity

data class PaisConCiudades(

    @Embedded
    val pais: PaisEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_NOMBRE,
        entityColumn = Constantes.COLUM_NAME_PAIS,
    )
    val ciudades: List<CiudadEntity>,
)