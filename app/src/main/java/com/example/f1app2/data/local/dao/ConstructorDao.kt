package com.example.f1app2.data.local.dao

import androidx.room.*
import com.example.f1app2.data.local.entity.ConstructorStandingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConstructorDao {

    @Query("SELECT * FROM constructor_standings ORDER BY points DESC")
    fun getAllConstructorStandings(): Flow<List<ConstructorStandingEntity>>

    @Query("SELECT * FROM constructor_standings WHERE teamId = :teamId")
    suspend fun getConstructorStandingById(teamId: String): ConstructorStandingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructorStandings(standings: List<ConstructorStandingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructorStanding(standing: ConstructorStandingEntity)

    @Delete
    suspend fun deleteConstructorStanding(standing: ConstructorStandingEntity)

    @Query("DELETE FROM constructor_standings")
    suspend fun deleteAllConstructorStandings()

    @Query("SELECT * FROM constructor_standings WHERE lastUpdated > :timestamp")
    suspend fun getConstructorStandingsUpdatedAfter(timestamp: Long): List<ConstructorStandingEntity>
}
