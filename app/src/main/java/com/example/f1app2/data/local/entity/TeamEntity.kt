package com.example.f1app2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamInfoEntity(
    @PrimaryKey
    val teamId: String,
    val teamName: String,
    val teamNationality: String,
    val firstAppeareance: Int,
    val constructorsChampionships: Int,
    val driversChampionships: Int,
    val url: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class TeamEntity(
    val teamName: String,
    val teamColor: String? = null
)
