package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.common.Constantes
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class EmpresaConRutas(

    @Embedded
    val empresa: EmpresaEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID,
        entityColumn = Constantes.COLUM_NAME_ID_EMPRESA,
    )

    val rutas: List<RutaEntity>,
)