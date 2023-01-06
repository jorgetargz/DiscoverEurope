package com.jorgetargz.europa.ui.add_ruta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.R
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.domain.usecases.ciudades.LoadAllCiudadesUseCase
import com.jorgetargz.europa.domain.usecases.empresas.LoadAllEmpresasUseCase
import com.jorgetargz.europa.domain.usecases.rutas.AddRutaUseCase
import com.jorgetargz.europa.domain.usecases.rutas.DeleteRutaUseCase
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddRutaViewModel @Inject constructor(
    private val addRutaUseCase: AddRutaUseCase,
    private val deleteRutaUseCase: DeleteRutaUseCase,
    private val loadAllCiudadesUseCase: LoadAllCiudadesUseCase,
    private val loadAllEmpresasUseCase: LoadAllEmpresasUseCase,
    @Named(Constantes.NAMED_INJECT_STRING_PROVIDER) private val stringProvider: StringProvider,
) : ViewModel() {

    private val _state = MutableLiveData(AddRutaState(null, null))
    val state: LiveData<AddRutaState> = _state

    private fun deleteRuta(ruta: Ruta) {
        viewModelScope.launch {
            try {
                deleteRutaUseCase.invoke(ruta)
                _state.value = _state.value?.copy(
                    mensaje = stringProvider.getString(R.string.ruta_borrada),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addRuta(ruta: Ruta) {
        if (ruta.ciudadInicio != ruta.ciudadFin) {
            viewModelScope.launch {
                try {
                    val rutaConId = addRutaUseCase.invoke(ruta)
                    _state.value = _state.value?.copy(
                        rutaAdded = rutaConId
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
            rutaAdded = null,
        )
    }

    fun handleEvent(event: AddRutaEvent) {
        when (event) {
            is AddRutaEvent.AddRuta -> addRuta(event.ruta)
            is AddRutaEvent.DeleteRuta -> deleteRuta(event.ruta)
            AddRutaEvent.ClearState -> clearState()
            AddRutaEvent.LoadDropdownLists -> loadDropdownList()
        }

    }
}