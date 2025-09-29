package com.example.f1app2.data.local.dao

import androidx.room.*
import com.example.f1app2.data.local.entity.RaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {

    @Query("SELECT * FROM races ORDER BY raceId")
    fun getAllRaces(): Flow<List<RaceEntity>>

    @Query("SELECT * FROM races WHERE raceId = :raceId")
    suspend fun getRaceById(raceId: String): RaceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<RaceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRace(race: RaceEntity)

    @Delete
    suspend fun deleteRace(race: RaceEntity)

    @Query("DELETE FROM races")
    suspend fun deleteAllRaces()

    @Query("SELECT * FROM races WHERE lastUpdated > :timestamp")
    suspend fun getRacesUpdatedAfter(timestamp: Long): List<RaceEntity>
}
