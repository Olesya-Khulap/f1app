package com.example.f1app2.data.local.dao

import androidx.room.*
import com.example.f1app2.data.local.entity.TeamInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Query("SELECT * FROM teams ORDER BY teamName")
    fun getAllTeams(): Flow<List<TeamInfoEntity>>

    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: String): TeamInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeams(teams: List<TeamInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: TeamInfoEntity)

    @Delete
    suspend fun deleteTeam(team: TeamInfoEntity)

    @Query("DELETE FROM teams")
    suspend fun deleteAllTeams()

    @Query("SELECT * FROM teams WHERE lastUpdated > :timestamp")
    suspend fun getTeamsUpdatedAfter(timestamp: Long): List<TeamInfoEntity>
}
