package com.jorgetargz.europa.domain.modelo

data class Ruta(
    var id: Int = 0,
    val ciudadInicio: Ciudad,
    val ciudadFin: Ciudad,
    val empresa: Empresa,
    val precio: Double,
    val duracionMin: Int,
    val comentario: String,
)
