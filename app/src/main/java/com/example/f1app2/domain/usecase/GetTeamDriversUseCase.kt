package com.example.f1app2.domain.usecase

import com.example.f1app2.domain.model.TeamDriversResponse
import com.example.f1app2.domain.repository.F1Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTeamDriversUseCase @Inject constructor(
    private val repository: F1Repository
) {
    suspend operator fun invoke(teamId: String): TeamDriversResponse {
        return repository.getTeamDrivers(teamId)
    }
}
