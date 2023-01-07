package com.jorgetargz.europa.data.room.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.jorgetargz.europa.data.room.common.Constantes
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity

data class RutaConCiudadesYEmpresa(
    @Embedded
    val ruta: RutaEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID_EMPRESA,
        entityColumn = Constantes.COLUM_NAME_ID,
    )
    val empresa: EmpresaEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID_CIUDAD_INICIO,
        entityColumn = Constantes.COLUM_NAME_ID,
    )
    val ciudadInicio: CiudadEntity,

    @Relation(
        parentColumn = Constantes.COLUM_NAME_ID_CIUDAD_FIN,
        entityColumn = Constantes.COLUM_NAME_ID,
    )
    val ciudadFin: CiudadEntity,
)