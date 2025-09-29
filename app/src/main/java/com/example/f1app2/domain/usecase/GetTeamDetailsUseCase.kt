package com.example.f1app2.domain.usecase

import com.example.f1app2.domain.model.*
import com.example.f1app2.domain.repository.F1Repository
import javax.inject.Inject

data class TeamDetails(
    val team: TeamInfo,
    val points: Int,
    val drivers: List<DriverSimple>
)

class GetTeamDetailsUseCase @Inject constructor(
    private val repository: F1Repository
) {
    suspend operator fun invoke(teamId: String): TeamDetails? {
        return try {
            val teamsResponse = repository.getTeams()
            val team = teamsResponse.teams.find { it.teamId == teamId }
                ?: return null

            val constructorStandings = repository.getConstructorStandings()
            val teamStanding = constructorStandings.constructors_championship.find {
                it.team.teamName.contains(team.teamName, ignoreCase = true)
            }
            val points = teamStanding?.points ?: 0

            val driversStandings = repository.getDriverStandings()
            val teamDrivers = driversStandings.drivers_championship.filter { driverStanding ->
                driverStanding.team?.teamName?.contains(team.teamName, ignoreCase = true) == true
            }.map { driverStanding ->
                DriverSimple(
                    driverId = driverStanding.driverId,
                    name = driverStanding.driver.name,
                    surname = driverStanding.driver.surname
                )
            }

            TeamDetails(
                team = team,
                points = points,
                drivers = teamDrivers
            )
        } catch (e: Exception) {
            null
        }
    }
}
