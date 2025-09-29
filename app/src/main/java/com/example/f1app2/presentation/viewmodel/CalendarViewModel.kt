package com.example.f1app2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1app2.domain.usecase.GetCalendarUseCase
import com.example.f1app2.domain.model.CalendarResponse
import com.example.f1app2.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getCalendarUseCase: GetCalendarUseCase
) : ViewModel() {

    private val _calendarState = MutableStateFlow<UiState<CalendarResponse>>(UiState.Loading)
    val calendarState: StateFlow<UiState<CalendarResponse>> = _calendarState.asStateFlow()

    init {
        loadCalendar()
    }

    private fun loadCalendar() {
        viewModelScope.launch {
            _calendarState.value = UiState.Loading
            try {
                val calendar = getCalendarUseCase()
                _calendarState.value = UiState.Success(calendar)
            } catch (e: Exception) {
                _calendarState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
