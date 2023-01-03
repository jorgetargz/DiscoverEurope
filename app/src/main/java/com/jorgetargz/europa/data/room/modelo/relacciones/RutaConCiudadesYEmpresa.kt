package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class RutaConCiudadesYEmpresa(
    @Embedded
    val ruta: RutaEntity,

    @Relation(
        parentColumn = "id_empresa",
        entityColumn = "id"
    )
    val empresa: EmpresaEntity,

    @Relation(
        parentColumn = "id_ciudad_inicio",
        entityColumn = "id"
    )
    val ciudadInicio: CiudadEntity,

    @Relation(
        parentColumn = "id_ciudad_fin",
        entityColumn = "id"
    )
    val ciudadFin: CiudadEntity,
)