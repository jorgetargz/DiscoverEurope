package com.jorgetargz.europa.domain.modelo

data class Ciudad(
    var id: Int = 0,
    val nombre: String,
    val pais: String,
) {
    override fun toString(): String {
        return "$pais - $nombre"
    }
}
