package com.example.f1app2.data.local.mapper

import com.example.f1app2.data.local.entity.*
import com.example.f1app2.domain.model.*

// Race mappers
fun Race.toEntity(): RaceEntity {
    return RaceEntity(
        raceId = this.raceId,
        raceName = this.raceName,
        circuit = this.circuit.toEntity(),
        schedule = this.schedule.toEntity(),
        laps = this.laps,
        winner = this.winner?.toEntity()
    )
}

fun RaceEntity.toDomain(): Race {
    return Race(
        raceId = this.raceId,
        raceName = this.raceName,
        circuit = this.circuit.toDomain(),
        schedule = this.schedule.toDomain(),
        laps = this.laps,
        winner = this.winner?.toDomain()
    )
}

// Circuit mappers
fun CircuitFull.toEntity(): CircuitFullEntity {
    return CircuitFullEntity(
        circuitId = this.circuitId,
        circuitName = this.circuitName,
        country = this.country,
        city = this.city,
        circuitLength = this.circuitLength
    )
}

fun CircuitFullEntity.toDomain(): CircuitFull {
    return CircuitFull(
        circuitId = this.circuitId,
        circuitName = this.circuitName,
        country = this.country,
        city = this.city,
        circuitLength = this.circuitLength
    )
}

// Schedule mappers
fun Schedule.toEntity(): ScheduleEntity {
    return ScheduleEntity(
        fp1Date = this.fp1?.date,
        fp1Time = this.fp1?.time,
        fp2Date = this.fp2?.date,
        fp2Time = this.fp2?.time,
        fp3Date = this.fp3?.date,
        fp3Time = this.fp3?.time,
        qualyDate = this.qualy?.date,
        qualyTime = this.qualy?.time,
        sprintQualyDate = this.sprintQualy?.date,
        sprintQualyTime = this.sprintQualy?.time,
        sprintRaceDate = this.sprintRace?.date,
        sprintRaceTime = this.sprintRace?.time,
        raceDate = this.race?.date,
        raceTime = this.race?.time
    )
}

fun ScheduleEntity.toDomain(): Schedule {
    return Schedule(
        fp1 = if (fp1Date != null) RaceSession(fp1Date, fp1Time) else null,
        fp2 = if (fp2Date != null) RaceSession(fp2Date, fp2Time) else null,
        fp3 = if (fp3Date != null) RaceSession(fp3Date, fp3Time) else null,
        qualy = if (qualyDate != null) RaceSession(qualyDate, qualyTime) else null,
        sprintQualy = if (sprintQualyDate != null) RaceSession(sprintQualyDate, sprintQualyTime) else null,
        sprintRace = if (sprintRaceDate != null) RaceSession(sprintRaceDate, sprintRaceTime) else null,
        race = if (raceDate != null) RaceSession(raceDate, raceTime) else null
    )
}

// Winner mappers
fun Winner.toEntity(): WinnerEntity {
    return WinnerEntity(
        driverId = this.driverId,
        name = this.name,
        surname = this.surname
    )
}

fun WinnerEntity.toDomain(): Winner {
    return Winner(
        driverId = this.driverId,
        name = this.name,
        surname = this.surname
    )
}

// DriverStanding mappers
fun DriverStanding.toEntity(): DriverStandingEntity {
    return DriverStandingEntity(
        driverId = this.driverId,
        driver = this.driver.toEntity(),
        teamId = this.teamId,
        points = this.points,
        position = this.position,
        wins = this.wins,
        classificationId = this.classificationId,
        team = this.team?.toEntity()
    )
}

fun DriverStandingEntity.toDomain(): DriverStanding {
    return DriverStanding(
        driverId = this.driverId,
        driver = this.driver.toDomain(),
        teamId = this.teamId,
        points = this.points,
        position = this.position,
        wins = this.wins,
        classificationId = this.classificationId,
        team = this.team?.toDomain()
    )
}

// Driver mappers
fun Driver.toEntity(): DriverEntity {
    return DriverEntity(
        name = this.name,
        surname = this.surname,
        nationality = this.nationality,
        birthday = this.birthday,
        number = this.number,
        shortName = this.shortName,
        url = this.url
    )
}

fun DriverEntity.toDomain(): Driver {
    return Driver(
        name = this.name,
        surname = this.surname,
        nationality = this.nationality,
        birthday = this.birthday,
        number = this.number,
        shortName = this.shortName,
        url = this.url
    )
}

// Team mappers
fun Team.toEntity(): TeamEntity {
    return TeamEntity(
        teamName = this.teamName,
        teamColor = this.team_color
    )
}

fun TeamEntity.toDomain(): Team {
    return Team(
        teamName = this.teamName,
        team_color = this.teamColor
    )
}

// TeamInfo mappers
fun TeamInfo.toEntity(): TeamInfoEntity {
    return TeamInfoEntity(
        teamId = this.teamId,
        teamName = this.teamName,
        teamNationality = this.teamNationality,
        firstAppeareance = this.firstAppeareance,
        constructorsChampionships = this.constructorsChampionships,
        driversChampionships = this.driversChampionships,
        url = this.url
    )
}

fun TeamInfoEntity.toDomain(): TeamInfo {
    return TeamInfo(
        teamId = this.teamId,
        teamName = this.teamName,
        teamNationality = this.teamNationality,
        firstAppeareance = this.firstAppeareance,
        constructorsChampionships = this.constructorsChampionships,
        driversChampionships = this.driversChampionships,
        url = this.url
    )
}

// ConstructorStanding mappers
fun ConstructorStanding.toEntity(): ConstructorStandingEntity {
    return ConstructorStandingEntity(
        teamId = this.teamId,
        points = this.points,
        team = this.team.toEntity()
    )
}

fun ConstructorStandingEntity.toDomain(): ConstructorStanding {
    return ConstructorStanding(
        teamId = this.teamId,
        points = this.points,
        team = this.team.toDomain()
    )
}

// Collection mappers
fun List<Race>.toEntityList(): List<RaceEntity> = this.map { it.toEntity() }
fun List<RaceEntity>.toDomainList(): List<Race> = this.map { it.toDomain() }

fun List<DriverStanding>.toDriverEntityList(): List<DriverStandingEntity> = this.map { it.toEntity() }
fun List<DriverStandingEntity>.toDriverDomainList(): List<DriverStanding> = this.map { it.toDomain() }

fun List<TeamInfo>.toTeamEntityList(): List<TeamInfoEntity> = this.map { it.toEntity() }
fun List<TeamInfoEntity>.toTeamDomainList(): List<TeamInfo> = this.map { it.toDomain() }

fun List<ConstructorStanding>.toConstructorEntityList(): List<ConstructorStandingEntity> = this.map { it.toEntity() }
fun List<ConstructorStandingEntity>.toConstructorDomainList(): List<ConstructorStanding> = this.map { it.toDomain() }
