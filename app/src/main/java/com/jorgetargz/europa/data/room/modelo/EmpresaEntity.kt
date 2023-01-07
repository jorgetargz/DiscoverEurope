package com.jorgetargz.europa.data.room.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jorgetargz.europa.data.room.common.Constantes

@Entity(tableName = Constantes.TABLE_NAME_EMPRESAS)
data class EmpresaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = Constantes.COLUM_NAME_NAME)
    val name: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_PHONE)
    val phone: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_EMAIL)
    val email: String,

    @ColumnInfo(name = Constantes.COLUM_NAME_WEBSITE)
    val website: String,

)