package com.jorgetargz.europa.domain.modelo

data class Ruta(
    val id: Int,
    val ciudadInicio: Ciudad,
    val ciudadFin: Ciudad,
    val empresa: Empresa,
    val precio: Double,
    val duracionMin: Int,
    val comentario: String,
)
