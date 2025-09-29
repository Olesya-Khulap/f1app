package com.example.f1app2.domain.model

data class CalendarResponse(
    val races: List<Race>
)

data class Race(
    val raceId: String,
    val raceName: String,
    val circuit: CircuitFull,
    val schedule: Schedule,
    val laps: Int,
    val winner: Winner?
)

data class CircuitFull(
    val circuitId: String,
    val circuitName: String,
    val country: String,
    val city: String,
    val circuitLength: String
)

data class Circuit(
    val circuitName: String,
    val city: String
)

data class Schedule(
    val fp1: RaceSession?,
    val fp2: RaceSession?,
    val fp3: RaceSession?,
    val qualy: RaceSession?,
    val sprintQualy: RaceSession?,
    val sprintRace: RaceSession?,
    val race: RaceSession?
)

data class RaceSession(
    val date: String,
    val time: String? = null
)

data class Winner(
    val driverId: String,
    val name: String,
    val surname: String
)

data class ConstructorListResponse(
    val constructors_championship: List<ConstructorStanding>
)

data class ConstructorStanding(
    val teamId: String,
    val points: Int,
    val team: Team
)

data class Team(
    val teamName: String,
    val team_color: String? = null
)

data class DriverStandingsResponse(
    val drivers_championship: List<DriverStanding>
)

data class DriverStanding(
    val driver: Driver,
    val teamId: String,
    val points: Int,
    val position: Int,
    val wins: Int,
    val driverId: String,
    val classificationId: Int,
    val team: Team? = null
)

data class Driver(
    val name: String,
    val surname: String,
    val nationality: String,
    val birthday: String,
    val number: Int,
    val shortName: String,
    val url: String
)

data class TeamsResponse(
    val teams: List<TeamInfo>
)

data class TeamInfo(
    val teamId: String,
    val teamName: String,
    val teamNationality: String,
    val firstAppeareance: Int,
    val constructorsChampionships: Int,
    val driversChampionships: Int,
    val url: String
)

data class TeamDriversResponse(
    val drivers: List<DriverWrapper>
)

data class DriverWrapper(
    val driver: DriverSimple
)

data class DriverSimple(
    val driverId: String?,
    val name: String?,
    val surname: String?
)
