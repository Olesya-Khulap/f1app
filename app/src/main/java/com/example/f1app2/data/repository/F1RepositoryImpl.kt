package com.example.f1app2.data.repository

import com.example.f1app2.data.remote.api.F1Api
import com.example.f1app2.domain.model.*
import com.example.f1app2.domain.repository.F1Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class F1RepositoryImpl @Inject constructor(
    private val api: F1Api
) : F1Repository {

    override suspend fun getCalendar(): CalendarResponse {
        return try {
            api.getCalendar()
        } catch (e: Exception) {
            CalendarResponse(
                races = listOf(
                    Race(
                        raceId = "bahrain_gp",
                        raceName = "Bahrain Grand Prix 2025",
                        circuit = CircuitFull(
                            circuitId = "bahrain",
                            circuitName = "Bahrain International Circuit",
                            country = "Bahrain",
                            city = "Sakhir",
                            circuitLength = "5.412km"
                        ),
                        schedule = Schedule(
                            fp1 = RaceSession("2025-03-14"),
                            fp2 = RaceSession("2025-03-14"),
                            fp3 = RaceSession("2025-03-15"),
                            qualy = RaceSession("2025-03-15"),
                            sprintQualy = null,
                            sprintRace = null,
                            race = RaceSession("2025-03-16")
                        ),
                        laps = 57,
                        winner = null
                    ),
                    Race(
                        raceId = "australian_gp",
                        raceName = "Australian Grand Prix 2025",
                        circuit = CircuitFull(
                            circuitId = "melbourne",
                            circuitName = "Albert Park Circuit",
                            country = "Australia",
                            city = "Melbourne",
                            circuitLength = "5.278km"
                        ),
                        schedule = Schedule(
                            fp1 = RaceSession("2025-03-21"),
                            fp2 = RaceSession("2025-03-21"),
                            fp3 = RaceSession("2025-03-22"),
                            qualy = RaceSession("2025-03-22"),
                            sprintQualy = null,
                            sprintRace = null,
                            race = RaceSession("2025-03-23")
                        ),
                        laps = 58,
                        winner = null
                    )
                )
            )
        }
    }

    override suspend fun getDriverStandings(): DriverStandingsResponse {
        return api.getDriverStandings()
    }

    override suspend fun getConstructorStandings(): ConstructorListResponse {
        return try {
            api.getConstructorStandings()
        } catch (e: Exception) {
            ConstructorListResponse(
                constructors_championship = listOf(
                    ConstructorStanding(
                        teamId = "mclaren",
                        points = 608,
                        team = Team(teamName = "McLaren")
                    )
                )
            )
        }
    }


    override suspend fun getTeams(): TeamsResponse {
        return try {
            api.getTeams()
        } catch (e: Exception) {
            TeamsResponse(
                teams = listOf(
                    TeamInfo(
                        teamId = "mclaren",
                        teamName = "McLaren",
                        teamNationality = "British",
                        firstAppeareance = 1966,
                        constructorsChampionships = 8,
                        driversChampionships = 12,
                        url = ""
                    ),
                    TeamInfo(
                        teamId = "ferrari",
                        teamName = "Ferrari",
                        teamNationality = "Italian",
                        firstAppeareance = 1950,
                        constructorsChampionships = 16,
                        driversChampionships = 15,
                        url = ""
                    )
                )
            )
        }
    }

    override suspend fun getTeamDrivers(teamId: String): TeamDriversResponse {
        return try {
            api.getTeamDrivers(teamId)
        } catch (e: Exception) {
            TeamDriversResponse(
                drivers = listOf(
                    DriverWrapper(
                        driver = DriverSimple(
                            driverId = "norris",
                            name = "Lando",
                            surname = "Norris"
                        )
                    ),
                    DriverWrapper(
                        driver = DriverSimple(
                            driverId = "piastri",
                            name = "Oscar",
                            surname = "Piastri"
                        )
                    )
                )
            )
        }
    }


    override suspend fun findRaceById(raceId: String): Race? {
        return try {
            val calendar = getCalendar()
            calendar.races.find { it.raceId == raceId }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun findDriverById(driverId: String): DriverStanding? {
        return try {
            val standings = getDriverStandings()
            standings.drivers_championship.find { it.driverId == driverId }
        } catch (e: Exception) {
            null
        }
    }

}
