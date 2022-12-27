package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ciudades",
    foreignKeys = [ForeignKey(
        entity = PaisEntity::class,
        parentColumns = ["nombre"],
        childColumns = ["pais"]
    )])
data class CiudadEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "nombre")
    val nombre: String,

    @ColumnInfo(name = "pais")
    val pais: String,
)