package com.example.f1app2.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.f1app2.data.local.dao.*
import com.example.f1app2.data.local.entity.*

@Database(
    entities = [
        RaceEntity::class,
        DriverStandingEntity::class,
        TeamInfoEntity::class,
        ConstructorStandingEntity::class,
        TeamDriverEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class F1Database : RoomDatabase() {

    abstract fun raceDao(): RaceDao
    abstract fun driverDao(): DriverDao
    abstract fun teamDao(): TeamDao
    abstract fun constructorDao(): ConstructorDao
    abstract fun teamDriverDao(): TeamDriverDao

    companion object {
        const val DATABASE_NAME = "f1_database"
    }
}
