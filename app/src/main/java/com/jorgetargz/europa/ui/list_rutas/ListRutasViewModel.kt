package com.jorgetargz.europa.ui.list_rutas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.domain.usecases.rutas.AddRutaUseCase
import com.jorgetargz.europa.domain.usecases.rutas.DeleteRutaUseCase
import com.jorgetargz.europa.domain.usecases.rutas.LoadAllRutasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListRutasViewModel @Inject constructor(
    private val loadAllRutasUseCase: LoadAllRutasUseCase,
    private val addRutaUseCase: AddRutaUseCase,
    private val deleteRutaUseCase: DeleteRutaUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListRutasState(null, null, null))
    val state: LiveData<ListRutasState> = _state

    private fun loadRutas() {
        viewModelScope.launch {
            try {
                val lista = loadAllRutasUseCase.invoke()
                _state.value = _state.value?.copy(
                    lista = lista,
                    listaFiltrada = lista
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun filtrarRutas(nombre: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(
                    listaFiltrada = _state.value?.lista?.filter {
                        it.ciudadInicio.nombre.contains(nombre, true) ||
                                it.ciudadFin.nombre.contains(nombre, true)
                    }
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun deleteRuta(ruta: Ruta) {
        viewModelScope.launch {
            try {
                deleteRutaUseCase.invoke(ruta)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.filter { it.id != ruta.id },
                    listaFiltrada = _state.value?.listaFiltrada?.filter { it.id != ruta.id },
                    rutaEliminada = ruta
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addRuta(ruta: Ruta) {
        viewModelScope.launch {
            try {
                val rutaConId = addRutaUseCase.invoke(ruta)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.plus(rutaConId),
                    listaFiltrada = _state.value?.listaFiltrada?.plus(rutaConId),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            rutaEliminada = null,
        )
    }

    fun handleEvent(event: ListRutasEvent) {
        when (event) {
            is ListRutasEvent.LoadRutas -> loadRutas()
            is ListRutasEvent.FiltrarRutas -> filtrarRutas(event.nombre)
            is ListRutasEvent.DeleteRuta -> deleteRuta(event.ruta)
            is ListRutasEvent.UndoDeleteRuta -> addRuta(event.ruta)
            ListRutasEvent.ClearState -> clearState()
        }
    }
}