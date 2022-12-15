package com.jorgetargz.europa.ui.list_paises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.usecases.paises.LoadAllPaisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListPaisesViewModel @Inject constructor(
    private val loadAllPaisesUseCase: LoadAllPaisesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListPaisesState(null, null))
    val state: LiveData<ListPaisesState> = _state

    private fun loadPaises() {
        viewModelScope.launch {
            try {
                val lista = loadAllPaisesUseCase()
                _state.value = _state.value?.copy(
                    lista = lista,
                    listaFiltrada = lista
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun filtrarPaises(nombre: String) {
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

    fun handleEvent(event: ListPaisesEvent) {
        when (event) {
            is ListPaisesEvent.LoadPaises -> loadPaises()
            is ListPaisesEvent.FiltrarPaises -> filtrarPaises(event.nombre)
        }
    }
}