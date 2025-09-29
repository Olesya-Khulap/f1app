package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetConstructorStandingsUseCase
import com.example.f1app2.domain.model.ConstructorListResponse
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsStandingsViewModel @Inject constructor(
    private val getConstructorStandingsUseCase: GetConstructorStandingsUseCase
) : ViewModel() {

    private val _teamsState = MutableStateFlow<UiState<ConstructorListResponse>>(UiState.Loading)
    val teamsState: StateFlow<UiState<ConstructorListResponse>> = _teamsState.asStateFlow()

    init {
        loadTeamStandings()
    }

    private fun loadTeamStandings() {
        viewModelScope.launch {
            _teamsState.value = UiState.Loading
            try {
                val standings = getConstructorStandingsUseCase()
                _teamsState.value = UiState.Success(standings)
            } catch (e: Exception) {
                _teamsState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
