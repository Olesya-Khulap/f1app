package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetTeamDetailsUseCase
import com.example.f1app2.domain.usecase.TeamDetails
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val getTeamDetailsUseCase: GetTeamDetailsUseCase
) : ViewModel() {

    private val _teamState = MutableStateFlow<UiState<TeamDetails>>(UiState.Loading)
    val teamState: StateFlow<UiState<TeamDetails>> = _teamState.asStateFlow()

    fun loadTeam(teamId: String) {
        viewModelScope.launch {
            _teamState.value = UiState.Loading
            try {
                val team = getTeamDetailsUseCase(teamId)
                if (team != null) {
                    _teamState.value = UiState.Success(team)
                } else {
                    _teamState.value = UiState.Error("Team not found")
                }
            } catch (e: Exception) {
                _teamState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
