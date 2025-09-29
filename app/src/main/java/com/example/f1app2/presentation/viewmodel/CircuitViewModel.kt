package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetRaceByIdUseCase
import com.example.f1app2.domain.model.Race
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CircuitViewModel @Inject constructor(
    private val getRaceByIdUseCase: GetRaceByIdUseCase
) : ViewModel() {

    private val _raceState = MutableStateFlow<UiState<Race>>(UiState.Loading)
    val raceState: StateFlow<UiState<Race>> = _raceState.asStateFlow()

    fun loadRace(raceId: String) {
        viewModelScope.launch {
            _raceState.value = UiState.Loading
            try {
                val race = getRaceByIdUseCase(raceId)
                if (race != null) {
                    _raceState.value = UiState.Success(race)
                } else {
                    _raceState.value = UiState.Error("Race not found")
                }
            } catch (e: Exception) {
                _raceState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
