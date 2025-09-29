package com.example.f1app2.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "driver_standings")
data class DriverStandingEntity(
    @PrimaryKey
    val driverId: String,
    @Embedded(prefix = "driver_")
    val driver: DriverEntity,
    val teamId: String,
    val points: Int,
    val position: Int,
    val wins: Int,
    val classificationId: Int,
    @Embedded(prefix = "team_")
    val team: TeamEntity?,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class DriverEntity(
    val name: String,
    val surname: String,
    val nationality: String,
    val birthday: String,
    val number: Int,
    val shortName: String,
    val url: String
)
