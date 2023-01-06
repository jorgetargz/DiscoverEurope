package com.jorgetargz.europa.ui.list_ciudades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.usecases.ciudades.AddCiudadUseCase
import com.jorgetargz.europa.domain.usecases.ciudades.DeleteCiudadConRutasUseCase
import com.jorgetargz.europa.domain.usecases.ciudades.DeleteCiudadUseCase
import com.jorgetargz.europa.domain.usecases.paises.LoadPaisConCiudadesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListCiudadesViewModel @Inject constructor(
    private val loadPaisConCiudadesByNameUseCase: LoadPaisConCiudadesByNameUseCase,
    private val deleteCiudadUseCase: DeleteCiudadUseCase,
    private val deleteCiudadConRutasUseCase: DeleteCiudadConRutasUseCase,
    private val addCiudadUseCase: AddCiudadUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListCiudadesState(null, null, null, null))
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

    private fun deleteCiudad(ciudad: Ciudad) {
        viewModelScope.launch {
            try {
                deleteCiudadUseCase.invoke(ciudad)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.filter { it.id != ciudad.id },
                    listaFiltrada = _state.value?.listaFiltrada?.filter { it.id != ciudad.id },
                    ciudadEliminada = ciudad
                )
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    onDeleteCiudadConRutas = ciudad
                )
                Timber.e(e)
            }
        }
    }

    private fun deleteCiudadConRutas(ciudad: Ciudad) {
        viewModelScope.launch {
            try {
                deleteCiudadConRutasUseCase.invoke(ciudad)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.filter { it.id != ciudad.id },
                    listaFiltrada = _state.value?.listaFiltrada?.filter { it.id != ciudad.id },
                    ciudadEliminada = ciudad
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addCiudad(ciudad: Ciudad) {
        viewModelScope.launch {
            try {
                addCiudadUseCase.invoke(ciudad)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.plus(ciudad),
                    listaFiltrada = _state.value?.listaFiltrada?.plus(ciudad),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            ciudadEliminada = null,
            onDeleteCiudadConRutas = null
        )
    }

    fun handleEvent(event: ListCiudadesEvent) {
        when (event) {
            is ListCiudadesEvent.LoadCiudades -> loadCiudades(event.pais)
            is ListCiudadesEvent.FiltrarCiudades -> filtrarCiudades(event.nombre)
            is ListCiudadesEvent.DeleteCiudad -> deleteCiudad(event.ciudad)
            is ListCiudadesEvent.UndoDeleteCiudad -> addCiudad(event.ciudad)
            is ListCiudadesEvent.DeleteCiudadConRutas -> deleteCiudadConRutas(event.ciudad)
            ListCiudadesEvent.ClearState -> clearState()
        }
    }
}