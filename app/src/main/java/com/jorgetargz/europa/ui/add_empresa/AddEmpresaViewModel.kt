package com.jorgetargz.europa.ui.add_empresa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.R
import com.jorgetargz.europa.domain.modelo.Empresa
import com.jorgetargz.europa.domain.usecases.empresas.AddEmpresaUseCase
import com.jorgetargz.europa.domain.usecases.empresas.DeleteEmpresaUseCase
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddEmpresaViewModel @Inject constructor(
    private val addEmpresaUseCase: AddEmpresaUseCase,
    private val deleteEmpresaUseCase: DeleteEmpresaUseCase,
    @Named(Constantes.NAMED_INJECT_STRING_PROVIDER) private val stringProvider: StringProvider,
) : ViewModel() {

    private val _state = MutableLiveData(AddEmpresaState(null, null))
    val state: LiveData<AddEmpresaState> = _state

    private fun deleteEmpresa(empresa: Empresa) {
        viewModelScope.launch {
            try {
                deleteEmpresaUseCase.invoke(empresa)
                _state.value = _state.value?.copy(
                    mensaje = stringProvider.getString(R.string.empresa_borrada),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addEmpresa(empresa: Empresa) {
        if (empresa.name.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val empresaConId = addEmpresaUseCase.invoke(empresa)
                    _state.value = _state.value?.copy(
                        empresaAdded = empresaConId
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

    private fun clearState() {
        _state.value = _state.value?.copy(
            empresaAdded = null,
        )
    }

    fun handleEvent(event: AddEmpresaEvent) {
        when (event) {
            is AddEmpresaEvent.DeleteEmpresa -> deleteEmpresa(event.empresa)
            is AddEmpresaEvent.AddEmpresa -> addEmpresa(event.empresa)
            is AddEmpresaEvent.ClearState -> clearState()
        }
    }
}