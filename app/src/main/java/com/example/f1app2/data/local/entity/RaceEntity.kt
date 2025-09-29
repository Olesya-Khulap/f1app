package com.example.f1app2.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "races")
data class RaceEntity(
    @PrimaryKey
    val raceId: String,
    val raceName: String,
    @Embedded(prefix = "circuit_")
    val circuit: CircuitFullEntity,
    @Embedded(prefix = "schedule_")
    val schedule: ScheduleEntity,
    val laps: Int,
    @Embedded(prefix = "winner_")
    val winner: WinnerEntity?,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class CircuitFullEntity(
    val circuitId: String,
    val circuitName: String,
    val country: String,
    val city: String,
    val circuitLength: String
)

data class ScheduleEntity(
    val fp1Date: String?,
    val fp1Time: String?,
    val fp2Date: String?,
    val fp2Time: String?,
    val fp3Date: String?,
    val fp3Time: String?,
    val qualyDate: String?,
    val qualyTime: String?,
    val sprintQualyDate: String?,
    val sprintQualyTime: String?,
    val sprintRaceDate: String?,
    val sprintRaceTime: String?,
    val raceDate: String?,
    val raceTime: String?
)

data class WinnerEntity(
    val driverId: String,
    val name: String,
    val surname: String
)
