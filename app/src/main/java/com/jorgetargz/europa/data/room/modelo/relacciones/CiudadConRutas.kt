package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.common.Constantes
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class CiudadConRutas(

    @Embedded
    val ciudad: CiudadEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID,
        entityColumn = Constantes.COLUM_NAME_ID_CIUDAD_INICIO,
    )
    val rutasDeSalida: List<RutaEntity>,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID,
        entityColumn = Constantes.COLUM_NAME_ID_CIUDAD_FIN,
    )
    val rutasDeLlegada: List<RutaEntity>,
)