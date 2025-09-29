package com.example.f1app2.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "constructor_standings")
data class ConstructorStandingEntity(
    @PrimaryKey
    val teamId: String,
    val points: Int,
    @Embedded(prefix = "team_")
    val team: TeamEntity,
    val lastUpdated: Long = System.currentTimeMillis()
)
