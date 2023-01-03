package com.jorgetargz.europa.data.room.utils

import com.jorgetargz.europa.data.room.modelo.EmpresaEntity
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.data.room.modelo.RutaEntity
import com.jorgetargz.europa.data.room.modelo.relacciones.PaisConCiudades
import com.jorgetargz.europa.data.room.modelo.relacciones.RutaConCiudadesYEmpresa
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.modelo.Pais
import com.jorgetargz.europa.domain.modelo.Ruta

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

fun Empresa.toEmpresaEntity() = EmpresaEntity(
    id = id,
    name = name,
    phone = phone,
    email = email,
    website = website,
)

fun EmpresaEntity.toEmpresa() = Empresa(
    id = id,
    name = name,
    phone = phone,
    email = email,
    website = website,
)

fun RutaConCiudadesYEmpresa.toRuta() = Ruta(
    id = ruta.id,
    ciudadInicio = ciudadInicio.toCiudad(),
    ciudadFin = ciudadFin.toCiudad(),
    empresa = empresa.toEmpresa(),
    precio = ruta.precio,
    duracionMin = ruta.duracion,
    comentario = ruta.comentario,
)

fun Ruta.toRutaEntity() = RutaEntity(
    id = id,
    idCiudadInicio = ciudadInicio.id,
    idCiudadFin = ciudadFin.id,
    idEmpresa = empresa.id,
    precio = precio,
    duracion = duracionMin,
    comentario = comentario,
)

