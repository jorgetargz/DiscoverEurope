package com.jorgetargz.europa.data.room.utils

object SQLQueries {

    const val SELECT_ALL_CIUDADES = "SELECT * FROM ciudades"
    const val SELECT_ALL_PAISES = "SELECT * FROM paises ORDER BY nombre"
    const val SELECT_ALL_EMPRESAS = "SELECT * FROM businesses"
    const val SELECT_ALL_RUTAS = "SELECT * FROM rutas"

    const val SELECT_CIUDAD_BY_ID = "SELECT * FROM ciudades WHERE id = :id LIMIT 1"
    const val SELECT_PAIS_BY_NOMBRE = "SELECT * FROM paises WHERE nombre = :nombre LIMIT 1"
    const val SELECT_EMPRESA_BY_ID = "SELECT * FROM businesses WHERE id = :id LIMIT 1"
    const val SELECT_RUTA_BY_ID = "SELECT * FROM rutas WHERE id = :id LIMIT 1"

    const val SELECT_PAISES_FAVORITOS = "select * from paises where favorito = 1 order by nombre"

}