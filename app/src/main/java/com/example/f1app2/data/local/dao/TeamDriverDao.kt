package com.example.f1app2.data.local.dao

import androidx.room.*
import com.example.f1app2.data.local.entity.TeamDriverEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDriverDao {

    @Query("SELECT * FROM team_drivers WHERE teamId = :teamId")
    suspend fun getDriversByTeamId(teamId: String): List<TeamDriverEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamDrivers(teamDrivers: List<TeamDriverEntity>)

    @Query("DELETE FROM team_drivers WHERE teamId = :teamId")
    suspend fun deleteDriversByTeamId(teamId: String)

    @Query("DELETE FROM team_drivers")
    suspend fun deleteAllTeamDrivers()
}
