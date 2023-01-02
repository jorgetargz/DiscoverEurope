package com.jorgetargz.europa.ui.list_business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.modelo.Business
import com.jorgetargz.europa.domain.usecases.empresas.AddEmpresaUseCase
import com.jorgetargz.europa.domain.usecases.empresas.DeleteEmpresaUseCase
import com.jorgetargz.europa.domain.usecases.empresas.LoadAllEmpresasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListBusinessViewModel @Inject constructor(
    private val loadAllEmpresasUseCase: LoadAllEmpresasUseCase,
    private val addEmpresaUseCase: AddEmpresaUseCase,
    private val deleteEmpresaUseCase: DeleteEmpresaUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListBusinessState(null, null, null))
    val state: LiveData<ListBusinessState> = _state

    private fun loadEmpresas() {
        viewModelScope.launch {
            try {
                val lista = loadAllEmpresasUseCase.invoke()
                _state.value = _state.value?.copy(
                    lista = lista,
                    listaFiltrada = lista
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun filtrarEmpresas(nombre: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(
                    listaFiltrada = _state.value?.lista?.filter { it.name.contains(nombre, true) }
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun deleteBusiness(empresa: Business) {
        viewModelScope.launch {
            try {
                deleteEmpresaUseCase.invoke(empresa)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.filter { it.id != empresa.id },
                    listaFiltrada = _state.value?.listaFiltrada?.filter { it.id != empresa.id },
                    empresaEliminada = empresa
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addBusiness(empresa: Business) {
        viewModelScope.launch {
            try {
                val id = addEmpresaUseCase.invoke(empresa)
                empresa.id = id.toInt()
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.plus(empresa),
                    listaFiltrada = _state.value?.listaFiltrada?.plus(empresa),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            empresaEliminada = null,
        )
    }

    fun handleEvent(event: ListBusinessEvent) {
        when (event) {
            is ListBusinessEvent.LoadBusiness -> loadEmpresas()
            is ListBusinessEvent.FiltrarBusiness -> filtrarEmpresas(event.nombre)
            is ListBusinessEvent.DeleteCiudad -> deleteBusiness(event.business)
            is ListBusinessEvent.UndoDeleteCiudad -> addBusiness(event.business)
            ListBusinessEvent.ClearState -> clearState()
        }
    }
}