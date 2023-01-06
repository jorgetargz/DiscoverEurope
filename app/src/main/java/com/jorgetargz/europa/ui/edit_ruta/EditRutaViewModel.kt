package com.jorgetargz.europa.ui.edit_ruta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.R
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.domain.usecases.ciudades.LoadAllCiudadesUseCase
import com.jorgetargz.europa.domain.usecases.empresas.LoadAllEmpresasUseCase
import com.jorgetargz.europa.domain.usecases.rutas.LoadRutaByIdUseCase
import com.jorgetargz.europa.domain.usecases.rutas.UpdateRutaUseCase
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class EditRutaViewModel @Inject constructor(
    private val loadRutaByIdUseCase: LoadRutaByIdUseCase,
    private val updateRutaUseCase: UpdateRutaUseCase,
    private val loadAllCiudadesUseCase: LoadAllCiudadesUseCase,
    private val loadAllEmpresasUseCase: LoadAllEmpresasUseCase,
    @Named(Constantes.NAMED_INJECT_STRING_PROVIDER) private val stringProvider: StringProvider,
) : ViewModel() {

    private val _state = MutableLiveData(EditRutaState())
    val state: LiveData<EditRutaState> = _state

    private lateinit var rutaSinEditar: Ruta

    private fun loadRuta(id: Int) {
        viewModelScope.launch {
            try {
                rutaSinEditar = loadRutaByIdUseCase.invoke(id)
                _state.value = _state.value?.copy(
                    rutaMostrar = rutaSinEditar,
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun editRuta(ruta: Ruta) {
        if (ruta.ciudadInicio != ruta.ciudadFin) {
            viewModelScope.launch {
                try {
                    updateRutaUseCase.invoke(ruta)
                    _state.value = _state.value?.copy(
                        rutaEditada = true,
                        rutaMostrar = ruta,
                    )
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        } else {
            _state.value = _state.value?.copy(
                mensaje = stringProvider.getString(R.string.ciudades_iguales_error),
            )
        }
    }

    private fun undoEditRuta() {
        viewModelScope.launch {
            try {
                updateRutaUseCase.invoke(rutaSinEditar)
                _state.value = _state.value?.copy(
                    rutaMostrar = rutaSinEditar,
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun loadDropdownList() {
        viewModelScope.launch {
            try {
                val ciudades = loadAllCiudadesUseCase.invoke()
                val empresas = loadAllEmpresasUseCase.invoke()
                _state.value = _state.value?.copy(
                    listaCiudades = ciudades,
                    listaEmpresas = empresas
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun clearState() {
        _state.value = _state.value?.copy(
            rutaEditada = false,
        )
    }

    fun handleEvent(event: EditRutaEvent) {
        when (event) {
            is EditRutaEvent.EditRuta -> editRuta(event.ruta)
            is EditRutaEvent.LoadRuta -> loadRuta(event.id)
            EditRutaEvent.UndoEditRuta -> undoEditRuta()
            EditRutaEvent.LoadDropdownLists -> loadDropdownList()
            EditRutaEvent.ClearState -> clearState()
        }
    }
}