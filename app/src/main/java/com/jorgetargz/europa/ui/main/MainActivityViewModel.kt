package com.jorgetargz.europa.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgetargz.europa.domain.usecases.paises.LoadPaisesFavoritosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loadPaisesFavoritosUseCase: LoadPaisesFavoritosUseCase,
) : ViewModel() {

    private val _state = MutableLiveData(MainActivityState(null))
    val state: LiveData<MainActivityState> = _state

    private fun loadPaisesFavoritos() {
        viewModelScope.launch {
            try {
                _state.value = _state.value?.copy(
                    favoritos = loadPaisesFavoritosUseCase.invoke()
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun handleEvent(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.LoadPaisesFavoritos -> loadPaisesFavoritos()
        }
    }
}