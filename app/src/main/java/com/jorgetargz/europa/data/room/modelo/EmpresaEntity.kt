package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "businesses")
data class EmpresaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "website")
    val website: String,

)