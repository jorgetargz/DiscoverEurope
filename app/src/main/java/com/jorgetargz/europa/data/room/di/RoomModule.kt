package com.jorgetargz.europa.data.room.di

import android.content.Context
import androidx.room.Room
import com.jorgetargz.europa.data.room.*
import com.jorgetargz.europa.data.room.common.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Named(Constantes.NAMED_INJECT_DB)
    fun getAssetDB(): String = Constantes.DATABASE_PATH


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(Constantes.NAMED_INJECT_DB) ruta: String
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constantes.DATABASE_NAME)
            .createFromAsset(ruta)
            .fallbackToDestructiveMigrationFrom(1)
            .build()

    @Provides
    fun providesPaisesDao(europaDB: AppDatabase): PaisesDao =
        europaDB.paisesDao()

    @Provides
    fun providesCiudadesDao(europaDB: AppDatabase): CiudadesDao =
        europaDB.ciudadesDao()

    @Provides
    fun providesBusinessDao(europaDB: AppDatabase): EmpresasDao =
        europaDB.empresasDao()

    @Provides
    fun providesRutasDao(europaDB: AppDatabase): RutasDao =
        europaDB.rutasDao()

}

