package com.example.f1app2.domain.usecase

import com.example.f1app2.domain.model.ConstructorListResponse
import com.example.f1app2.domain.repository.F1Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetConstructorStandingsUseCase @Inject constructor(
    private val repository: F1Repository
) {
    suspend operator fun invoke(): ConstructorListResponse {
        return repository.getConstructorStandings()
    }
}
