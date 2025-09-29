package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetDriverByIdUseCase
import com.example.f1app2.domain.model.DriverStanding
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor(
    private val getDriverByIdUseCase: GetDriverByIdUseCase
) : ViewModel() {

    private val _driverState = MutableStateFlow<UiState<DriverStanding>>(UiState.Loading)
    val driverState: StateFlow<UiState<DriverStanding>> = _driverState.asStateFlow()

    fun loadDriver(driverId: String) {
        viewModelScope.launch {
            _driverState.value = UiState.Loading
            try {
                val driver = getDriverByIdUseCase(driverId)
                if (driver != null) {
                    _driverState.value = UiState.Success(driver)
                } else {
                    _driverState.value = UiState.Error("Driver not found")
                }
            } catch (e: Exception) {
                _driverState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
