package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetDriverStandingsUseCase
import com.example.f1app2.domain.model.DriverStandingsResponse
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversStandingsViewModel @Inject constructor(
    private val getDriverStandingsUseCase: GetDriverStandingsUseCase
) : ViewModel() {

    private val _driversState = MutableStateFlow<UiState<DriverStandingsResponse>>(UiState.Loading)
    val driversState: StateFlow<UiState<DriverStandingsResponse>> = _driversState.asStateFlow()

    init {
        loadDriverStandings()
    }

    private fun loadDriverStandings() {
        viewModelScope.launch {
            _driversState.value = UiState.Loading
            try {
                val standings = getDriverStandingsUseCase()
                _driversState.value = UiState.Success(standings)
            } catch (e: Exception) {
                _driversState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
