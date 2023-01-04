package com.jorgetargz.europa.ui.add_ciudad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.R
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.domain.usecases.ciudades.AddCiudadUseCase
import com.jorgetargz.europa.domain.usecases.ciudades.DeleteCiudadUseCase
import com.jorgetargz.europa.ui.common.Constantes
import com.jorgetargz.europa.ui.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val deleteCiudadUseCase: DeleteCiudadUseCase,
    private val addCiudadUseCase: AddCiudadUseCase,
    @Named(Constantes.NAMED_INJECT_STRING_PROVIDER)
    private val stringProvider: StringProvider,
) : ViewModel() {

    private val _state = MutableLiveData(AddCityState(null, null))
    val state: LiveData<AddCityState> = _state

    private fun deleteCiudad(ciudad: Ciudad) {
        viewModelScope.launch {
            try {
                deleteCiudadUseCase.invoke(ciudad)
                _state.value = _state.value?.copy(
                    mensaje = stringProvider.getString(R.string.ciudad_borrada),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun addCiudad(ciudad: Ciudad) {
        if (ciudad.nombre.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val ciudadConId = addCiudadUseCase.invoke(ciudad)
                    _state.value = _state.value?.copy(
                        ciudadAdded = ciudadConId
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
            ciudadAdded = null,
        )
    }

    fun handleEvent(event: AddCityEvent) {
        when (event) {
            is AddCityEvent.DeleteCiudad -> deleteCiudad(event.ciudad)
            is AddCityEvent.AddCiudad -> addCiudad(event.ciudad)
            is AddCityEvent.ClearState -> clearState()
        }
    }
}