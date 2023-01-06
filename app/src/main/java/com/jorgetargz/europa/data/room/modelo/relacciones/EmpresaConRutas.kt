package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class EmpresaConRutas(

    @Embedded
    val empresa: EmpresaEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_empresa"
    )

    val rutas: List<RutaEntity>,
)