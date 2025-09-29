package com.example.f1app2.domain.repository

import com.example.f1app2.domain.model.*

interface F1Repository {
    suspend fun getCalendar(): CalendarResponse
    suspend fun getDriverStandings(): DriverStandingsResponse
    suspend fun getConstructorStandings(): ConstructorListResponse
    suspend fun getTeams(): TeamsResponse
    suspend fun getTeamDrivers(teamId: String): TeamDriversResponse
    suspend fun findRaceById(raceId: String): Race?
    suspend fun findDriverById(driverId: String): DriverStanding?
}
