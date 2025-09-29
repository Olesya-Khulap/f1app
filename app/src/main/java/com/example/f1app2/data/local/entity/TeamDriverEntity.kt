package com.example.f1app2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_drivers")
data class TeamDriverEntity(
    @PrimaryKey
    val id: String,
    val teamId: String,
    val driverId: String?,
    val driverName: String?,
    val driverSurname: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)
