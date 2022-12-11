package com.jorgetargz.europa.data.room.utils

import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.domain.modelo.Pais

fun Pais.toPaisEntity() = PaisEntity(
    nombre = nombre,
    nombreLocal = nombreLocal,
    capital = capital,
    urlBandera = urlBandera,
    idiomas = idiomas,
    favorito = favorito,
)

fun PaisEntity.toPais() = Pais(
    nombre = nombre,
    nombreLocal = nombreLocal,
    capital = capital,
    urlBandera = urlBandera,
    idiomas = idiomas,
    favorito = favorito,
)
