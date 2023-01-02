package com.jorgetargz.europa.domain.modelo

data class Empresa(
    var id: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val website: String,
)