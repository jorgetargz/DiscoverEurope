package com.jorgetargz.europa.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jorgetargz.europa.data.room.modelo.BusinessEntity
import com.jorgetargz.europa.data.room.modelo.CiudadEntity
import com.jorgetargz.europa.data.room.modelo.PaisEntity
import com.jorgetargz.europa.data.room.utils.Converters

@Database(
    entities = [PaisEntity::class, CiudadEntity::class, BusinessEntity::class],
    version = 3, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paisesDao(): PaisesDao
    abstract fun ciudadesDao(): CiudadesDao
    abstract fun businessDao(): BusinessDao

}
