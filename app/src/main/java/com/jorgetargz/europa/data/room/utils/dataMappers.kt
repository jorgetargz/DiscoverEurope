package com.jorgetargz.europa.data.room.utils

import com.jorgetargz.europa.data.room.modelo.BusinessEntity
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.PaisConCiudades
import com.jorgetargz.europa.domain.modelo.Business
import com.jorgetargz.europa.domain.modelo.Ciudad
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

fun Ciudad.toCiudadEntity() = CiudadEntity(
    id = id,
    nombre = nombre,
    pais = pais,
)

fun CiudadEntity.toCiudad() = Ciudad(
    id = id,
    nombre = nombre,
    pais = pais,
)

fun PaisConCiudades.toPais() = Pais(
    nombre = pais.nombre,
    nombreLocal = pais.nombreLocal,
    capital = pais.capital,
    urlBandera = pais.urlBandera,
    idiomas = pais.idiomas,
    favorito = pais.favorito,
    ciudades = ciudades.map { it.toCiudad() }
)

fun Business.toBusinessEntity() = BusinessEntity(
    id = id,
    name = name,
    phone = phone,
    email = email,
    website = website,
)

fun BusinessEntity.toBusiness() = Business(
    id = id,
    name = name,
    phone = phone,
    email = email,
    website = website,
)
