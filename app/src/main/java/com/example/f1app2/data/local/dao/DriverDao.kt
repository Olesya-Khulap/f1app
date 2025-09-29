package com.example.f1app2.data.local.dao

import androidx.room.*
import com.example.f1app2.data.local.entity.DriverStandingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DriverDao {

    @Query("SELECT * FROM driver_standings ORDER BY position ASC")
    fun getAllDriverStandings(): Flow<List<DriverStandingEntity>>

    @Query("SELECT * FROM driver_standings WHERE driverId = :driverId")
    suspend fun getDriverStandingById(driverId: String): DriverStandingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverStandings(standings: List<DriverStandingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDriverStanding(standing: DriverStandingEntity)

    @Delete
    suspend fun deleteDriverStanding(standing: DriverStandingEntity)

    @Query("DELETE FROM driver_standings")
    suspend fun deleteAllDriverStandings()

    @Query("SELECT * FROM driver_standings WHERE lastUpdated > :timestamp")
    suspend fun getDriverStandingsUpdatedAfter(timestamp: Long): List<DriverStandingEntity>
}
