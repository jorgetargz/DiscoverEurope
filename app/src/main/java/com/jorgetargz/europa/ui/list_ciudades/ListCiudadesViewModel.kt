package com.jorgetargz.europa.ui.list_ciudades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.usecases.paises.LoadPaisConCiudadesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListCiudadesViewModel @Inject constructor(
    private val loadPaisConCiudadesByNameUseCase: LoadPaisConCiudadesByNameUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListCiudadesState(null, null))
    val state: LiveData<ListCiudadesState> = _state

    private fun loadCiudades(pais: String) {
        viewModelScope.launch {
            try {
                val lista = loadPaisConCiudadesByNameUseCase.invoke(pais).ciudades
                _state.value = _state.value?.copy(
                    lista = lista,
                    listaFiltrada = lista
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun filtrarCiudades(nombre: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(
                    listaFiltrada = _state.value?.lista?.filter { it.nombre.contains(nombre, true) }
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun handleEvent(event: ListCiudadesEvent) {
        when (event) {
            is ListCiudadesEvent.LoadCiudades -> loadCiudades(event.pais)
            is ListCiudadesEvent.FiltrarCiudades -> filtrarCiudades(event.nombre)
        }
    }
}