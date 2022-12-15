package com.jorgetargz.europa.ui.view_pais

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.usecases.paises.ChangePaisFavoritoStateUseCase
import com.jorgetargz.europa.domain.usecases.paises.LoadPaisByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ViewPaisViewModel @Inject constructor(
    private val loadPais: LoadPaisByNameUseCase,
    private val changePaisFavoritoStateUseCase: ChangePaisFavoritoStateUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(ViewPaisState(null))
    val state: LiveData<ViewPaisState> = _state

    private fun loadPais(nombre: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(
                    pais = loadPais.invoke(nombre)
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun onFavoriteClick(nombre: String) {
        viewModelScope.launch {
            try {
                val pais = loadPais.invoke(nombre)
                changePaisFavoritoStateUseCase.invoke(pais)
                if (pais.favorito) {
                    _state.value = _state.value?.copy(
                        onUnmarkedAsFavorito = true,
                        onMarkedAsFavorito = false
                    )

                } else {
                    _state.value = _state.value?.copy(
                        onMarkedAsFavorito = true,
                        onUnmarkedAsFavorito = false
                    )
                }
                loadPais(nombre)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun clearStateEvents() {
        _state.value = _state.value?.copy(
            onUnmarkedAsFavorito = false,
            onMarkedAsFavorito = false
        )
    }

    fun handleEvent(event: ViewPaisEvent) {
        when (event) {
            is ViewPaisEvent.LoadPais -> loadPais(event.nombre)
            is ViewPaisEvent.OnFavClick -> onFavoriteClick(event.nombre)
        }
    }
}