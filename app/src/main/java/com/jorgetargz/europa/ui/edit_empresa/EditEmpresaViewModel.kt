package com.jorgetargz.europa.ui.edit_empresa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.R
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.usecases.empresas.LoadEmpresaByIdUseCase
import com.jorgetargz.europa.domain.usecases.empresas.UpdateEmpresaUseCase
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EditEmpresaViewModel @Inject constructor(
    private val loadEmpresaByIdUseCase: LoadEmpresaByIdUseCase,
    private val updateEmpresaUseCase: UpdateEmpresaUseCase,
    @Named(Constantes.NAMED_INJECT_STRING_PROVIDER) private val stringProvider: StringProvider,
) : ViewModel() {

    private val _state = MutableLiveData(EditEmpresaState())
    val state: LiveData<EditEmpresaState> = _state

    private lateinit var empresaSinEditar: Empresa

    private fun loadEmpresa(id: Int) {
        viewModelScope.launch {
            try {
                empresaSinEditar = loadEmpresaByIdUseCase.invoke(id)
                _state.value = _state.value?.copy(
                    empresaMostrar = empresaSinEditar,
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun editEmpresa(empresa: Empresa) {
        if (empresa.name.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    updateEmpresaUseCase.invoke(empresa)
                    _state.value = _state.value?.copy(
                        empresaEditada = true,
                        empresaMostrar = empresa,
                    )
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        } else {
            _state.value = _state.value?.copy(
                mensaje = stringProvider.getString(R.string.nombre_ciudad_vacio),
            )
        }
    }

    private fun undoEditEmpresa() {
        viewModelScope.launch {
            try {
                updateEmpresaUseCase.invoke(empresaSinEditar)
                _state.value = _state.value?.copy(
                    empresaMostrar = empresaSinEditar,
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            empresaEditada = false,
        )
    }

    fun handleEvent(event: EditEmpresaEvent) {
        when (event) {
            is EditEmpresaEvent.LoadEmpresa -> loadEmpresa(event.id)
            is EditEmpresaEvent.EditEmpresa -> editEmpresa(event.empresa)
            EditEmpresaEvent.UndoEditEmpresa -> undoEditEmpresa()
            EditEmpresaEvent.ClearState -> clearState()
        }
    }
}