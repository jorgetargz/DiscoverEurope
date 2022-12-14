package com.jorgetargz.europa.ui.list_empresas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.usecases.empresas.AddEmpresaUseCase
import com.jorgetargz.europa.domain.usecases.empresas.DeleteEmpresaConRutasUseCase
import com.jorgetargz.europa.domain.usecases.empresas.DeleteEmpresaUseCase
import com.jorgetargz.europa.domain.usecases.empresas.LoadAllEmpresasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListEmpresasViewModel @Inject constructor(
    private val loadAllEmpresasUseCase: LoadAllEmpresasUseCase,
    private val addEmpresaUseCase: AddEmpresaUseCase,
    private val deleteEmpresaUseCase: DeleteEmpresaUseCase,
    private val deleteEmpresaConRutasUseCase: DeleteEmpresaConRutasUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ListEmpresasState(null, null, null, null))
    val state: LiveData<ListEmpresasState> = _state

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

    private fun deleteEmpresa(empresa: Empresa) {
        viewModelScope.launch {
            try {
                deleteEmpresaUseCase.invoke(empresa)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.filter { it.id != empresa.id },
                    listaFiltrada = _state.value?.listaFiltrada?.filter { it.id != empresa.id },
                    empresaEliminada = empresa
                )
            } catch (e: Exception) {
                _state.value = _state.value?.copy(
                    onDeleteEmpresaConRutas = empresa
                )
                Timber.e(e)
            }
        }
    }

    private fun deleteEmpresaConRutas(empresa: Empresa) {
        viewModelScope.launch {
            try {
                deleteEmpresaConRutasUseCase.invoke(empresa)
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

    private fun addEmpresa(empresa: Empresa) {
        viewModelScope.launch {
            try {
                val empresaConId = addEmpresaUseCase.invoke(empresa)
                _state.value = _state.value?.copy(
                    lista = _state.value?.lista?.plus(empresaConId),
                    listaFiltrada = _state.value?.listaFiltrada?.plus(empresaConId),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            empresaEliminada = null,
            onDeleteEmpresaConRutas = null
        )
    }

    fun handleEvent(event: ListEmpresasEvent) {
        when (event) {
            is ListEmpresasEvent.LoadEmpresas -> loadEmpresas()
            is ListEmpresasEvent.FiltrarEmpresas -> filtrarEmpresas(event.nombre)
            is ListEmpresasEvent.DeleteEmpresa -> deleteEmpresa(event.empresa)
            is ListEmpresasEvent.DeleteEmpresaConRutas -> deleteEmpresaConRutas(event.empresa)
            is ListEmpresasEvent.UndoDeleteEmpresa -> addEmpresa(event.empresa)
            ListEmpresasEvent.ClearState -> clearState()
        }
    }
}